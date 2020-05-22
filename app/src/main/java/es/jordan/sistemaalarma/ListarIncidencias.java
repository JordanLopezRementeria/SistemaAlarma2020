package es.jordan.sistemaalarma;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ListarIncidencias extends AppCompatActivity {

Toolbar toolbar;
    ArrayList<DatosColmena> miLista;
    RecyclerView miRecycler;
    TextView titulillo,prueba;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_incidencias);
        xmlToJava();
        toolbar = findViewById(R.id.toolbarListarIncidencias);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);//quitamos el titulo del toolbar

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        miRecycler.setLayoutManager(new LinearLayoutManager(this));

        AdaptadorDatos elAdaptador=new AdaptadorDatos(miLista);
        elAdaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence texto="Pulsado"+miLista.get(miRecycler.getChildAdapterPosition(v)).getNombre();
                Toast toast = Toast.makeText(getApplicationContext(), texto, Toast.LENGTH_LONG);
                toast.show();
            }
        });
        miRecycler.setAdapter(elAdaptador);









        Toolbar toolbar = findViewById(R.id.toolbarListarIncidencias); //poniendo toolbar cn flechita de retroceso
        if (null != toolbar) {
            setSupportActionBar(toolbar);
        }

        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayShowTitleEnabled(false);//quitamos el titulo del toolbar
        actionBar.setDisplayHomeAsUpEnabled(true);//indicando en el manifest quien es el padre de esta actividad




        //cuando le de a la flecha volvera ahi
        //ArrayList<Incidencia> listaIncidencias = new ArrayList();
        //listaIncidencias=obtenerLista();
        //Toast.makeText(getApplicationContext(), listaIncidencias.toString(), Toast.LENGTH_LONG).show();
       // miLista = new ArrayList<DatosColmena>();

        ArrayList<Combo> listaCombo = new ArrayList();
        listaCombo=obtenerLista2();

             //Incidencias    //listaincidencias
        for (Combo aux: listaCombo) {

            //Toast.makeText(getApplicationContext(), aux.hora.toString(), Toast.LENGTH_LONG).show();
             //aqui sacamos datos de 2 tablas, el modelo y la hora(raspberryId) es un objeto de Raspberry
            miLista.add(new DatosColmena(aux.getModelo(), aux.getHora(), R.drawable.alarmi));
            elAdaptador = new AdaptadorDatos(miLista);
            elAdaptador.notifyDataSetChanged();
            miRecycler.setAdapter(elAdaptador);



        }







    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.mimenu2,menu);
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
                Toast toast2 = Toast.makeText(getApplicationContext(),"pincha 2", Toast.LENGTH_LONG);
                toast2.show();
                return true;

            case R.id.item3:
                AlertDialog.Builder alert = new AlertDialog.Builder(ListarIncidencias.this);
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

                        Intent llamada = new Intent(ListarIncidencias.this, MainActivity.class);
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

        miLista=new ArrayList<DatosColmena>();
        miRecycler= findViewById(R.id.recicler2);
        titulillo= findViewById(R.id.titulistar2);
        prueba= findViewById(R.id.prueba);
    }


    public ArrayList<Incidencia> obtenerLista() {
        ArrayList<Incidencia> listaIncidencias = new ArrayList();
        try {

            String equipoServidor = "192.168.1.42";
            int puertoServidor = 30509;
            Socket socketCliente = new Socket(equipoServidor, puertoServidor);

            ObjectInputStream listaRecibida = new ObjectInputStream(socketCliente.getInputStream());//me preparo para recibir
            listaIncidencias= (ArrayList) listaRecibida.readObject(); //objeto leido y metido en usuario1 /lo recibod
            socketCliente.close();
            return listaIncidencias;

        } catch (IOException ex) {
            System.out.println(ex.getMessage());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return listaIncidencias;

    }

    public ArrayList<Combo> obtenerLista2() {
        ArrayList<Combo> listaCombo = new ArrayList();
        try {

            String equipoServidor = "192.168.1.42";
            int puertoServidor = 30509;
            Socket socketCliente = new Socket(equipoServidor, puertoServidor);

            ObjectInputStream listaRecibida = new ObjectInputStream(socketCliente.getInputStream());//me preparo para recibir
            listaCombo= (ArrayList) listaRecibida.readObject(); //objeto leido y metido en usuario1 /lo recibod
            socketCliente.close();
            return listaCombo;

        } catch (IOException ex) {
            System.out.println(ex.getMessage());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return listaCombo;

    }




}
