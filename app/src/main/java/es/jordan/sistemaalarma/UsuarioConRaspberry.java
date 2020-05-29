package es.jordan.sistemaalarma;

import androidx.appcompat.widget.Toolbar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;


public class UsuarioConRaspberry extends AppCompatActivity implements Serializable {
    private ListView listView;
    private Button button;
    Toolbar toolbar;
    TextView textazo,textazo2;
    private final String EXTRA_USUARIO = "";
    Spinner spinner1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_con_raspberry);
        final Usuario usuarioPasado = (Usuario) getIntent().getSerializableExtra(EXTRA_USUARIO);
        toolbar = findViewById(R.id.toolbi);
        toolbar.setTitle("Administrador - "+usuarioPasado.getNombre());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        xmlToJava();

        this.listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        inicializarLista();


        ArrayList<Usuario> listaUsuarios= new ArrayList();
        listaUsuarios= obtenerListaUsuarios();
        ArrayList<String> cargarEnSpinner = new ArrayList();

        {
            for (Usuario u : listaUsuarios) {
                cargarEnSpinner.add(u.getUsuarioId().toString()+":"+u.getNombre()+":"+u.getEmail());
            }


            spinner1.setPrompt("¿A qué usuario quieres asignar raspberrys?");
            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, cargarEnSpinner);
            spinner1.setAdapter(adapter2);


        }







        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {




            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eleccion=spinner1.getSelectedItem().toString();
                String[]datos=eleccion.split(":"); //meto los datos del item seleccionado en un array
                //lo voy separando por : y cojo solo el id

                String id="";
                for(String item : datos) //solo recorremos el for una vez porque tan solo queremos hasta los dos primeros :
                {
                    id=item;//este sera el id del usuario seleccionado
                    break;
                }
                int idEntero=parseInt(id);

               // Toast toast4 = Toast.makeText(getApplicationContext(),"id seleccionado de usu: "+idEntero, Toast.LENGTH_LONG);
                // toast4.show();



                SparseBooleanArray sp = listView.getCheckedItemPositions(); //booleano para saber los checkados
                eliminarUsurasSeleccionado(idEntero); //1ºeliminamos las raspberrys q tuviera asociadas antes
                // y le ponemos las nuevas

                //2º recorremos los checkbox y vemos cuales estan marcados
                for (int i = 0; i < sp.size(); i++) {
                    if (sp.valueAt(i) == true) { //solo se recorren los seleccionados

                        Raspberry r1 = (Raspberry) listView.getItemAtPosition(i);
                        String s = ((CheckedTextView) listView.getChildAt(i)).getText().toString();

                       //3º en r1 tenemos la raspberry con r.get id obtenemos las rasp marcadas y llamamos
                        //al metodo de insercion
                        insertarRaspberryEnUsuario(idEntero,r1.getRaspberryId());

                    }
                }





                    }
                });


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
                final Usuario usuarioPasado = (Usuario) getIntent().getSerializableExtra(EXTRA_USUARIO);
                intent.putExtra(EXTRA_USUARIO, usuarioPasado);
                startActivityForResult(intent, 0);
                return true;

            case R.id.item2:
                Toast toast2 = Toast.makeText(getApplicationContext(),"pincha 2", Toast.LENGTH_LONG);
                toast2.show();
                return true;

            case R.id.item3:
                AlertDialog.Builder alert = new AlertDialog.Builder(UsuarioConRaspberry.this);
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

                        Intent llamada = new Intent(UsuarioConRaspberry.this, MainActivity.class);
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

    private void inicializarLista() {
        ArrayList<Raspberry> listaRaspberrys = new ArrayList();
        listaRaspberrys=obtenerListaRaspberry();//aqui tenemos las listas

        ArrayAdapter<Raspberry> arrayAdapter
                = new ArrayAdapter<Raspberry>(this, android.R.layout.simple_list_item_multiple_choice ,  listaRaspberrys);
        this.listView.setAdapter(arrayAdapter);
    }

    private void xmlToJava() {

        listView = (ListView)findViewById(R.id.listView);
        button = (Button)findViewById(R.id.button);
        spinner1=(Spinner)findViewById(R.id.spinliada);
        textazo=(TextView)findViewById(R.id.titulo11);
        textazo2=(TextView)findViewById(R.id.titulo12);
    }

    public ArrayList<Raspberry> obtenerListaRaspberry() {
        ArrayList<Raspberry> listaRaspberrys = new ArrayList();
        try {

            String equipoServidor = "servidorwebjordan.ddns.net";
            int puertoServidor = 30510;
            Socket socketCliente = new Socket(equipoServidor, puertoServidor);

            ObjectInputStream listaRecibida = new ObjectInputStream(socketCliente.getInputStream());//me preparo para recibir
            listaRaspberrys= (ArrayList) listaRecibida.readObject(); //objeto leido y metido en usuario1 /lo recibod
            socketCliente.close();
            return listaRaspberrys;

        } catch (IOException ex) {
            System.out.println(ex.getMessage());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return listaRaspberrys;

    }


    public ArrayList<Usuario> obtenerListaUsuarios() { //lista de todos los usuarios
        ArrayList<Usuario> listaUsuarios = new ArrayList();
        try {

            String equipoServidor = "servidorwebjordan.ddns.net";
            int puertoServidor = 30504;
            Socket socketCliente = new Socket(equipoServidor, puertoServidor);

            ObjectInputStream listaRecibida = new ObjectInputStream(socketCliente.getInputStream());//me preparo para recibir
            listaUsuarios= (ArrayList) listaRecibida.readObject(); //objeto leido y metido en usuario1 /lo recibod
            socketCliente.close();
            return listaUsuarios;

        } catch (IOException ex) {
            System.out.println(ex.getMessage());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return listaUsuarios;

    }

    public void insertarRaspberryEnUsuario(int idUsuario,int idRaspberry) {
        try {

            String equipoServidor = "servidorwebjordan.ddns.net";
            int puertoServidor = 30800;
            Socket socketCliente = new Socket(equipoServidor, puertoServidor);

           Esclavo esclavo=new Esclavo();
           esclavo.setUsuarioId(idUsuario);
           esclavo.setRaspberryId(idRaspberry);

            ObjectOutputStream objetoEntregar = new ObjectOutputStream(socketCliente.getOutputStream());
            objetoEntregar.writeObject(esclavo);//mandamos el id ras e id usuario que queremos insertar en la bd



            socketCliente.close();

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

    }


    public void eliminarUsurasSeleccionado(int idUsuario) {
        try {

            String equipoServidor = "servidorwebjordan.ddns.net";
            int puertoServidor = 30799;
            Socket socketCliente = new Socket(equipoServidor, puertoServidor);

            OutputStream socketSalida = socketCliente.getOutputStream();
            DataOutputStream escribir = new DataOutputStream(socketSalida);

            Toast toast4 = Toast.makeText(getApplicationContext(),"el usuario es "+idUsuario, Toast.LENGTH_LONG);
            toast4.show();

            escribir.writeInt(idUsuario);





            socketCliente.close();

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }


}
