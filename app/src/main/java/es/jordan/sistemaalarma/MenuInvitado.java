package es.jordan.sistemaalarma;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import pojos.Usuario;

public class MenuInvitado extends AppCompatActivity {
    private VideoView video;
    private ImageView textoCorreo;
    TextView titulo;
    Toolbar toolbar;
    private final String EXTRA_USUARIO = "";
    Handler handler = new Handler();
    Runnable runnable;
    int intervaloDeTiempo = 15 * 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_invitado);
        final Usuario usuarioPasado = (Usuario) getIntent().getSerializableExtra(EXTRA_USUARIO);
        toolbar = findViewById(R.id.toolbarInvitado);
        toolbar.setTitle("Invitado - " + usuarioPasado.getNombre());
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

    @Override
    protected void onResume() { //cuando la actividad es visible
        //el handler es similar a un hilo en android

        handler.postDelayed(runnable = new Runnable() {
            public void run() {

                Toast.makeText(getApplicationContext(), "Si te ha gustado el video, solicita mas información", Toast.LENGTH_LONG).show();
                handler.postDelayed(runnable, intervaloDeTiempo);
            }
        }, intervaloDeTiempo);

        super.onResume();
    }

// If onPause() is not included the threads will double up when you
// reload the activity

    @Override
    protected void onPause() { //cuando se quita
        handler.removeCallbacks(runnable); //parar handler cuando no esta visible la activity
        super.onPause();
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mimenusolosalida, menu);
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
                Toast toast2 = Toast.makeText(getApplicationContext(), "pincha 2", Toast.LENGTH_LONG);
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
        Intent email = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto","alarmajordan@outlook.com", null));
        email.putExtra(Intent.EXTRA_SUBJECT, "Estoy interesado en el sistema de alarma");
        String[] addresses={"alarmajordan@outlook.com"};
        email.putExtra(Intent.EXTRA_EMAIL, addresses);
        email.putExtra(Intent.EXTRA_TEXT, "Hola, me gustaria adquirir mas detalles sobre la adquisición del sistema de alarma.");
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
        String path = "android.resource://" + getPackageName() + "/" + R.raw.video; //buscamos la ruta del video dentro de nuestro package
        Uri uri = Uri.parse(path);
        video.setVideoURI(uri);
        MediaController mediacontroler = new MediaController(this); //encargado de play y stop
        video.setMediaController(mediacontroler);
        mediacontroler.setAnchorView(video); //quiero que ocupe el mismo tamaño que el video
        video.start();
    }

    private void xmlTojava() {
        video = findViewById(R.id.videoView3);
        textoCorreo = findViewById(R.id.imageninfo);
        titulo = findViewById(R.id.titulo1);


    }
}


