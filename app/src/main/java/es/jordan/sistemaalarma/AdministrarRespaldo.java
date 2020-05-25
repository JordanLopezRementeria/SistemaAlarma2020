package es.jordan.sistemaalarma;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class AdministrarRespaldo extends AppCompatActivity {
    Button copia,recuperar;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrar_respaldo);

        toolbar = findViewById(R.id.toolbarRespaldo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);//quitamos el titulo del toolbar


        xmlToJava();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);



        copia.setOnClickListener(new View.OnClickListener() { //on click de desconectar
            @Override
            public void onClick(View v) {

                avisarServer();
                Toast toast = Toast.makeText(getApplicationContext(),"Copia realizada con exito", Toast.LENGTH_LONG);
                toast.show();



            }
        });
        recuperar.setOnClickListener(new View.OnClickListener() { //on click de desconectar
            @Override
            public void onClick(View v) {

                recuperarServer();
                Toast toast = Toast.makeText(getApplicationContext(),"Recuperación realizada con exito", Toast.LENGTH_LONG);
                toast.show();



            }
        });










    }
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menuenbackup,menu);
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
                Intent intent2 = new Intent(getApplicationContext(), MenuAdmin.class); //volvemos de las 2 formas
                startActivityForResult(intent2, 0);

                return true;

            case R.id.item3:
                AlertDialog.Builder alert = new AlertDialog.Builder(AdministrarRespaldo.this);
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

                        Intent llamada = new Intent(AdministrarRespaldo.this, MainActivity.class);
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
        copia= findViewById(R.id.botonCopia);
        recuperar= findViewById(R.id.botonRecuperar);
    }

    public void avisarServer() {

        try {

            String equipoServidor = "servidorwebjordan.ddns.net";
            int puertoServidor = 30520;
            Socket socketCliente = new Socket(equipoServidor, puertoServidor);
            OutputStream socketSalida = socketCliente.getOutputStream();
            DataOutputStream escribir = new DataOutputStream(socketSalida);
            escribir.writeUTF("Servidor insertar copia seguridad");

            escribir.flush();
            escribir.close();

            socketCliente.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void recuperarServer()
    {
        try {

            String equipoServidor = "servidorwebjordan.ddns.net";
            int puertoServidor = 30530;
            Socket socketCliente = new Socket(equipoServidor, puertoServidor);
            OutputStream socketSalida2 = socketCliente.getOutputStream();
            DataOutputStream escribir2 = new DataOutputStream(socketSalida2);
            escribir2.writeUTF("Servidor recuperar copia de seguridad");

            escribir2.flush();
            escribir2.close();

            socketCliente.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
