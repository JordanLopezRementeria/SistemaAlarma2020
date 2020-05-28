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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;


public class ModificarUsuario extends AppCompatActivity {
    private final String EXTRA_USUARIO = "";
    String ultimoNombreSeleccionado="";
    String ultimoEmailSeleccionado="";
    EditText editNombre;
    private ListView lv;
    ImageView botonModificar;
    EditText editEmail;
    Toolbar toolbar;
    Spinner spinner1;
    TextView mensaje;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_usuario);
        final Usuario usuarioPasado = (Usuario) getIntent().getSerializableExtra(EXTRA_USUARIO);
        xmlToJava();
        toolbar = findViewById(R.id.tool);
        toolbar.setTitle("Administrador - "+usuarioPasado.getNombre());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

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
                editNombre.setVisibility(View.VISIBLE);
                editEmail.setVisibility(View.VISIBLE);
                spinner1.setVisibility(View.VISIBLE);
                lv.setVisibility(View.GONE);
                botonModificar.setVisibility(View.VISIBLE);
                mensaje.setVisibility(View.GONE);


                itemColmena itemSeleccionado= (itemColmena) adapter.getItem(position);
                ultimoNombreSeleccionado=(itemSeleccionado.nombre); //metemos en variables globales el usuario elegido
                ultimoEmailSeleccionado=(itemSeleccionado.tipo); //el tipo es el email


                editNombre.setText(itemSeleccionado.nombre);
                editEmail.setText(itemSeleccionado.tipo); // corresponde al mail




              //lo que queremos hacer al clickar

            }
        });


        botonModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<Usuario> listaUsuarios = new ArrayList();
                listaUsuarios=obtenerLista();

                //recorremos la lista hasta encontrar el usuario y luego lo mandamos al server para q lo modifique
            for(Usuario u:listaUsuarios)
            {
                if (u.getNombre().toString().equals(ultimoNombreSeleccionado) && u.getEmail().equals(ultimoEmailSeleccionado))
                {
                    //Toast toast2 = Toast.makeText(getApplicationContext(),"He encontrado al usuario "+ultimoNombreSeleccionado, Toast.LENGTH_LONG);
                    //toast2.show();
                    Usuario usuarioAModificar=new Usuario(); //creamos un usuario poniendo lo que pone en las cajas
                    usuarioAModificar.setUsuarioId(u.getUsuarioId());
                    usuarioAModificar.setNombre(editNombre.getText().toString());
                    usuarioAModificar.setEmail(editEmail.getText().toString());
                    usuarioAModificar.setRol(spinner1.getSelectedItem().toString());

                   modificarUsuario(usuarioAModificar);  //mando el usuario a modificar al metodo que lo envia al server
                   break;




                }
            }




                Toast toast2 = Toast.makeText(getApplicationContext(),"Usuario modificado con éxito", Toast.LENGTH_LONG);
                toast2.show();
                Intent llamada = new Intent(ModificarUsuario.this, MenuAdmin.class);
                final Usuario usuarioPasado = (Usuario) getIntent().getSerializableExtra(EXTRA_USUARIO);
                llamada.putExtra(EXTRA_USUARIO, usuarioPasado);
                startActivity(llamada);

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
        botonModificar=findViewById(R.id.botonModificar);
        editEmail = findViewById(R.id.direccion1);
        spinner1=findViewById(R.id.spinnerrol);
        mensaje=findViewById(R.id.mensaje);
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

    public void modificarUsuario(Usuario usuario1) {
        try {

            String equipoServidor = "servidorwebjordan.ddns.net";
            int puertoServidor = 30582;
            Socket socketCliente = new Socket(equipoServidor, puertoServidor);
            gestionarComunicacion(socketCliente, usuario1);

            socketCliente.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void gestionarComunicacion(Socket socketCliente, Usuario usuario1) {

        try {

            ObjectOutputStream objetoEntregar = new ObjectOutputStream(socketCliente.getOutputStream());
            objetoEntregar.writeObject(usuario1);//el cliente manda el objeto al server para que lo modifique

            objetoEntregar.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());

        }


    }


}