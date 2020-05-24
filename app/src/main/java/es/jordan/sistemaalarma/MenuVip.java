package es.jordan.sistemaalarma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;



public class MenuVip extends AppCompatActivity {
    Button BotonVerCamara,BotonVerFotos,BotonSos,botonListarIncidencias;
    private final String EXTRA_USUARIO = "";
    Toolbar toolbar;
    Handler handler = new Handler();
    Runnable runnable;
    int intervaloDeTiempo = 15*1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_vip);
        final Usuario  usuarioPasado = (Usuario) getIntent().getSerializableExtra(EXTRA_USUARIO);
        toolbar = findViewById(R.id.toolbarMenuVip);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);//quitamos el titulo del toolbar
        xmlToJava();





        BotonVerCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(v.getContext(), Streaming.class);
                intent.putExtra(EXTRA_USUARIO, usuarioPasado);
              //  Toast toast = Toast.makeText(getApplicationContext(),usuarioPasado.toString(), Toast.LENGTH_LONG);
              //  toast.show();

                startActivityForResult(intent, 0);
            }
        });

        BotonVerFotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //esto abre el email que tengamos configurado por defecto en android que sera el que utilicemos
                //para recibir las fotos
                Intent intent = getPackageManager().getLaunchIntentForPackage("com.android.email");
                startActivity(intent);
            }
        });

        BotonSos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //la diferencia que radica entre un action_dial y un action_call es que en el call
                //esq dial puedes editar el numero y el call llama directamente
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:693246245"));
                startActivity(intent);
            }
        });

        botonListarIncidencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //la diferencia que radica entre un action_dial y un action_call es que en el call
                //esq dial puedes editar el numero y el call llama directamente
                Intent intent = new Intent(v.getContext(), ListarIncidencias.class);
                intent.putExtra(EXTRA_USUARIO, usuarioPasado);
                startActivityForResult(intent, 0);
            }
        });
    }
    @Override
    protected void onResume() { //cuando la actividad es visible
        //el handler es similar a un hilo en android

        handler.postDelayed( runnable = new Runnable() {
            public void run() {
                //do something
                Toast.makeText(getApplicationContext(), "probando cada cierto rato", Toast.LENGTH_LONG).show();

                handler.postDelayed(runnable, intervaloDeTiempo );
            }
        }, intervaloDeTiempo );

        super.onResume();
    }

// If onPause() is not included the threads will double up when you
// reload the activity

    @Override
    protected void onPause() { //cuando se quita
        handler.removeCallbacks(runnable); //stop handler when activity not visible
        super.onPause();
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
                Intent intent = new Intent(getApplicationContext(), MenuAdmin.class); //flechita que vuelve al
                startActivityForResult(intent, 0);
                return true;

            case R.id.item2:
               // Toast toast2 = Toast.makeText(getApplicationContext(),"pincha 2", Toast.LENGTH_LONG);
                //toast2.show();
                return true;

            case R.id.item3:
                AlertDialog.Builder alert = new AlertDialog.Builder(MenuVip.this);
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

                        Intent llamada = new Intent(MenuVip.this, MainActivity.class);
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







    private void xmlToJava() {

        BotonVerCamara= findViewById(R.id.botonVerCamara);
        BotonVerFotos= findViewById(R.id.botonVerFotos);
        BotonSos= findViewById(R.id.botonSos);
        botonListarIncidencias= findViewById(R.id.botonListarIncidencias);
    }


}
