package es.jordan.sistemaalarma;


import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.StrictMode;
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


    ArrayList<DatosColmena> miLista;
    RecyclerView miRecycler;
    TextView titulillo,prueba;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_incidencias);
        xmlToJava();


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









        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar3); //poniendo toolbar cn flechita de retroceso
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

    private void xmlToJava() {

        miLista=new ArrayList<DatosColmena>();
        miRecycler=(RecyclerView)findViewById(R.id.recicler2);
        titulillo=(TextView)findViewById(R.id.titulistar2);
        prueba=(TextView)findViewById(R.id.prueba);
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
