package es.jordan.sistemaalarma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class MenuInvitado extends AppCompatActivity {
    private VideoView video;
    private ImageView textoCorreo;
    TextView titulo;
    Toolbar toolbar;
    private final String EXTRA_USUARIO = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_invitado);
        final Usuario usuarioPasado = (Usuario) getIntent().getSerializableExtra(EXTRA_USUARIO);
        toolbar = findViewById(R.id.toolbarInvitado);
        toolbar.setTitle("Invitado - "+usuarioPasado.getNombre());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        xmlTojava();
        reproducirVideo();



        textoCorreo.setOnClickListener(new View.OnClickListener() { //onclick del textview

            public void onClick(View v) {
                enlaceMail();
            }

            });

    }
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.mimenusolosalida,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //metodo que se encarga del toolbar
        //para que cada icono asignarle tareas diferentes
        switch (item.getItemId()) {
            case R.id.item1:
                Intent intent = new Intent(getApplicationContext(), MainActivity.class); //flechita que vuelve al
                final Usuario usuarioPasado = (Usuario) getIntent().getSerializableExtra(EXTRA_USUARIO);
                intent.putExtra(EXTRA_USUARIO, usuarioPasado);
                startActivityForResult(intent, 0);
                return true;

            case R.id.item2:
                Toast toast2 = Toast.makeText(getApplicationContext(),"pincha 2", Toast.LENGTH_LONG);
                toast2.show();
                return true;

            case R.id.item3:
                AlertDialog.Builder alert = new AlertDialog.Builder(MenuInvitado.this);
                alert.setTitle("Advertencia");
                alert.setCancelable(true);
                alert.setIcon(R.drawable.exit);

                alert.setMessage("¿Desea desconectarse?");

                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });
                alert.setPositiveButton("Si", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent llamada = new Intent(MenuInvitado.this, MainActivity.class);
                        startActivity(llamada);
                    }
                });
                alert.create().show();

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }



    private void enlaceMail() {
        //con esto mandamos emails por gmail a la direccion que queramos con el asunto y cuerpo
        Intent email= new Intent(Intent.ACTION_SENDTO);
        email.setData(Uri.parse("mailto:lopez.rementeria@gmail.com"));
        email.putExtra(Intent.EXTRA_SUBJECT, "Estoy interesado en el sistema de alarma");
        email.putExtra(Intent.EXTRA_TEXT, "Hola me gustaria adquirir mas detalles sobre el sistema de alarma.");
        startActivity(email);




        //final Intent intent = new Intent(Intent.ACTION_VIEW)
              //  .setType("plain/text")
               // .setData(Uri.parse("lopez.rementeria@gmail.com"))
                //.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail")
               // .putExtra(Intent.EXTRA_EMAIL, new String[]{"lopez.rementeria@gmail.com"})
                //.putExtra(Intent.EXTRA_SUBJECT, "Estoy interesado en el sistema de alarma")
                //.putExtra(Intent.EXTRA_TEXT, "Hola me gustaria adquirir mas detalles sobre el sistema de alarma.");
       // startActivity(intent);
    }

    private void reproducirVideo() {
        String path = "android.resource://" + getPackageName() + "/" + R.raw.corte_video; //buscamos la ruta del video dentro de nuestro package
        Uri uri=Uri.parse(path);
        video.setVideoURI(uri);
        MediaController mediacontroler=new MediaController(this); //encargado de play y stop
        video.setMediaController(mediacontroler);
        mediacontroler.setAnchorView(video); //quiero que ocupe el mismo tamaño que el video
        video.start();
    }

    private void xmlTojava() {
        video= findViewById(R.id.videoView3);
        textoCorreo= findViewById(R.id.imageninfo);
        titulo=findViewById(R.id.titulo1);


    }
}


