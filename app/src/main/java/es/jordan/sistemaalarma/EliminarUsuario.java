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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import pojos.Raspberry;
import pojos.Usuario;

public class EliminarUsuario extends AppCompatActivity {
    ImageView eliminarUsu;
    private Toolbar toolbar;
    private final String EXTRA_USUARIO = "";
    ListView lv;
    String email = "";
    String nombre = "";
    int usuarioId;
    EditText texto2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_usuario);
        final Usuario usuarioPasado = (Usuario) getIntent().getSerializableExtra(EXTRA_USUARIO);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        xmlToJava();


        final ArrayList<ItemAlarma> itemsCompra = obtenerItems();

        final ItemAlarmaAdapter adapter = new ItemAlarmaAdapter(this, itemsCompra);
        lv.setClickable(true); //para poder pinchar en los elementos de la lista
        lv.setAdapter(adapter);

        final ItemAlarmaAdapter adaptador = new ItemAlarmaAdapter();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() { //onclick de cada elemeto de la lista
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                ItemAlarma itemSeleccionado = (ItemAlarma) adapter.getItem(position);
                texto2.setText(itemSeleccionado.nombre);
                //ponemos en la caja de texto el nombre del seleccionado
                nombre = itemSeleccionado.nombre;
                usuarioId = (int) itemSeleccionado.id;
                email = itemSeleccionado.tipo;


                //lo que queremos hacer al clickar

            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                ItemAlarma itemSeleccionado = (ItemAlarma) adapter.getItem(position);
                texto2.setText(itemSeleccionado.nombre); //ponemos en la caja de texto el nombre del seleccionado
                AlertDialog.Builder alert = new AlertDialog.Builder(EliminarUsuario.this);
                alert.setTitle("Advertencia");
                alert.setCancelable(true);
                alert.setIcon(R.drawable.eliminar1_foreground);

                alert.setMessage("¿De verdad deseas borrar el usuario " + texto2.getText().toString());

                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });
                alert.setPositiveButton("Si", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Usuario usuario1 = new Usuario();
                        usuario1.setUsuarioId(usuarioId);
                        usuario1.setNombre(nombre);
                        usuario1.setEmail(email);

                        eliminarUsu(usuario1);

                        Toast.makeText(getApplicationContext(), "Usuario eliminado con exito", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MenuAdmin.class); //flechita que vuelve al
                        final Usuario usuarioPasado = (Usuario) getIntent().getSerializableExtra(EXTRA_USUARIO);
                        intent.putExtra(EXTRA_USUARIO, usuarioPasado);
                        startActivityForResult(intent, 0);

                    }
                });
                alert.create().show();
                return false;//si ponemos a true solo este evento se producira, si ponemos a false el onclick normal tb
            }
        });


        toolbar = findViewById(R.id.toolbarEliminar);
        toolbar.setTitle("Administrador - " + usuarioPasado.getNombre());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);


        eliminarUsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder alert = new AlertDialog.Builder(EliminarUsuario.this);
                alert.setTitle("Advertencia");
                alert.setCancelable(true);
                alert.setIcon(R.drawable.eliminar1_foreground);

                alert.setMessage("¿De verdad deseas borrar el usuario " + texto2.getText().toString());

                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });
                alert.setPositiveButton("Si", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Usuario usuario1 = new Usuario();
                        usuario1.setUsuarioId(usuarioId);
                        usuario1.setNombre(nombre);
                        usuario1.setEmail(email);

                        if (mandarUsuarioYrecibirListaDeSusRaspberrys(usuario1).isEmpty()) {
                            eliminarUsu(usuario1);
                            Toast.makeText(getApplicationContext(), "Usuario eliminado con exito", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MenuAdmin.class); //flechita que vuelve al
                            final Usuario usuarioPasado = (Usuario) getIntent().getSerializableExtra(EXTRA_USUARIO);
                            intent.putExtra(EXTRA_USUARIO, usuarioPasado);
                            startActivityForResult(intent, 0);

                        } else {

                            Toast.makeText(getApplicationContext(), "Desasigna rasperries del usuario si quieres borrarlo", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
                alert.create().show();


            }

        });


    }

    private void eliminarUsu(Usuario usuario1) {
        try {

            String equipoServidor = "servidorwebjordan.ddns.net";
            int puertoServidor = 30503;
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
            System.out.println("El objeto que mandara el cliente al servidor es: " + usuario1);
            objetoEntregar.writeObject(usuario1);//el cliente manda el objeto al server
            objetoEntregar.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());

        }


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mimenu2, menu);
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
                Toast toast2 = Toast.makeText(getApplicationContext(), "pincha 2", Toast.LENGTH_LONG);
                toast2.show();
                return true;

            case R.id.item3:
                AlertDialog.Builder alert = new AlertDialog.Builder(EliminarUsuario.this);
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

                        Intent llamada = new Intent(EliminarUsuario.this, MainActivity.class);
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
        eliminarUsu = findViewById(R.id.botonEliminar);
        texto2 = findViewById(R.id.nombre2);
        lv = findViewById(R.id.lveliminar);


    }

    private ArrayList<ItemAlarma> obtenerItems() {
        ArrayList<ItemAlarma> listaDelListView = new ArrayList<ItemAlarma>();//lista con los atributos del litview
        ArrayList<Usuario> listaUsuarios = new ArrayList();
        listaUsuarios = obtenerLista(); //recorremos la lista de usuarios y metemos la informacion que queremos
        for (Usuario usuario1 : listaUsuarios) {

            if (usuario1.getRol().toUpperCase().equals("ADMIN")) {
                int id = usuario1.getUsuarioId();
                String nombre = usuario1.getNombre().toString();
                String correo = usuario1.getEmail().toString();
                listaDelListView.add(new ItemAlarma(id, nombre, correo, "drawable/adming3"));
            } else if (usuario1.getRol().toUpperCase().equals("USUARIO")) {
                int id = usuario1.getUsuarioId();
                String nombre = usuario1.getNombre().toString();
                String correo = usuario1.getEmail().toString();
                listaDelListView.add(new ItemAlarma(id, nombre, correo, "drawable/usuariog3"));
            } else {
                int id = usuario1.getUsuarioId();
                String nombre = usuario1.getNombre().toString();
                String correo = usuario1.getEmail().toString();
                listaDelListView.add(new ItemAlarma(id, nombre, correo, "drawable/invitadog3"));
            }


        }


        return listaDelListView;
    }


    public ArrayList<Usuario> obtenerLista() {
        ArrayList<Usuario> listaUsuarios = new ArrayList();
        try {

            String equipoServidor = "servidorwebjordan.ddns.net";
            int puertoServidor = 30504;
            Socket socketCliente = new Socket(equipoServidor, puertoServidor);

            ObjectInputStream listaRecibida = new ObjectInputStream(socketCliente.getInputStream());//me preparo para recibir
            listaUsuarios = (ArrayList) listaRecibida.readObject(); //objeto leido y metido en usuario1 /lo recibod
            socketCliente.close();
            return listaUsuarios;

        } catch (IOException ex) {
            System.out.println(ex.getMessage());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return listaUsuarios;

    }

    public ArrayList<Raspberry> mandarUsuarioYrecibirListaDeSusRaspberrys(Usuario usuarioPasado) {
        ArrayList<Raspberry> listaRaspberrys = new ArrayList();
        try {

            //1º paso conectarse al servidor
            String equipoServidor = "servidorwebjordan.ddns.net";
            int puertoServidor = 30560;
            Socket socketCliente = new Socket(equipoServidor, puertoServidor);
            //2º paso mandar el usuario que esta conectado como objeto
            ObjectOutputStream objetoEntregar = new ObjectOutputStream(socketCliente.getOutputStream());
            System.out.println("El objeto que mandara el cliente al servidor es: " + usuarioPasado);
            objetoEntregar.writeObject(usuarioPasado);//el cliente manda el objeto al server
            objetoEntregar.flush();
            //3º paso recibir la lista de raspberrys que tiene ese usuario
            ObjectInputStream listaRecibida = new ObjectInputStream(socketCliente.getInputStream());//me preparo para recibir
            listaRaspberrys = (ArrayList) listaRecibida.readObject(); //objeto leido y metido en usuario1 /lo recibod


            listaRecibida.close();
            objetoEntregar.close();
            return listaRaspberrys;
        } catch (IOException ex) {
            System.out.println(ex.getMessage());


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return listaRaspberrys;
    }
}
