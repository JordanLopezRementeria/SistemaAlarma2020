package es.jordan.sistemaalarma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.content.Intent;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;


public class ModificarUsuario extends AppCompatActivity {
    private final String EXTRA_USUARIO = "";

    EditText editNombre;
    private ListView lv;
    EditText editContraseña;
    EditText editEmail;
    Toolbar toolbar;
    Spinner spinner1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_usuario);
        xmlToJava();
        toolbar = findViewById(R.id.tool);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);//quitamos el titulo del toolbar

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //iniciando spinner
        spinner1.setPrompt("Selecciona un rol");
        String []opciones={"Admin","Usuario","Invitado"};
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, opciones);
        spinner1.setAdapter(adapter3);

        final ArrayList<itemColmena> itemsCompra = obtenerItems();

        final ItemColmenaAdapter adapter = new ItemColmenaAdapter(this, itemsCompra);
        lv.setClickable(true); //para poder pinchar en los elementos de la lista
        lv.setAdapter(adapter);

        final ItemColmenaAdapter adaptador=new ItemColmenaAdapter();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                itemColmena itemSeleccionado= (itemColmena) adapter.getItem(position);
                editNombre.setText(itemSeleccionado.nombre);
                editEmail.setText(itemSeleccionado.tipo); // corresponde al mail




              //lo que queremos hacer al clickar

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
                startActivityForResult(intent, 0);
                return true;

            case R.id.item2:
                Toast toast2 = Toast.makeText(getApplicationContext(),"pincha 2", Toast.LENGTH_LONG);
                toast2.show();
                return true;

            case R.id.item3:
                AlertDialog.Builder alert = new AlertDialog.Builder(ModificarUsuario.this);
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

                        Intent llamada = new Intent(ModificarUsuario.this, MainActivity.class);
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

    public void xmlToJava() {

        editNombre = findViewById(R.id.nombre1);
        lv=findViewById(R.id.listView);
        editContraseña = findViewById(R.id.contraseña1);
        editEmail = findViewById(R.id.direccion1);
        spinner1=findViewById(R.id.spinnerrol);
    }

    public ArrayList<Usuario> obtenerLista() {
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

    private ArrayList<itemColmena> obtenerItems() {
        ArrayList<itemColmena> listaDelListView = new ArrayList<itemColmena>();//lista con los atributos del litview
        ArrayList<Usuario> listaUsuarios = new ArrayList();
        listaUsuarios=obtenerLista(); //recorremos la lista de usuarios y metemos la informacion que queremos
        for(Usuario usuario1:listaUsuarios)
        {

            if(usuario1.getRol().toUpperCase().equals("ADMIN")) {
                int id = usuario1.getUsuarioId();
                String nombre = usuario1.getNombre().toString();
                String correo = usuario1.getEmail().toString();
                listaDelListView.add(new itemColmena(id, nombre, correo, "drawable/admin"));
            }
            else if(usuario1.getRol().toUpperCase().equals("USUARIO"))
            {
                int id = usuario1.getUsuarioId();
                String nombre = usuario1.getNombre().toString();
                String correo = usuario1.getEmail().toString();
                listaDelListView.add(new itemColmena(id, nombre, correo, "drawable/usuario"));
            }
            else
            {
                int id = usuario1.getUsuarioId();
                String nombre = usuario1.getNombre().toString();
                String correo = usuario1.getEmail().toString();
                listaDelListView.add(new itemColmena(id, nombre, correo, "drawable/invitado"));
            }






        }




        return listaDelListView;
    }


}