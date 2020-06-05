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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import adaptadores.CustomAdapter;
import adaptadores.Model;
import pojos.Raspberry;
import pojos.Usuario;
import seguridad.Hashear;

public class AnadirUsuarios extends AppCompatActivity {
    ImageView botonAñadir;
    Spinner spinner1;
    EditText nombreInsertar, contraseñaInsertar, direccionInsertar;
    private final String EXTRA_USUARIO = "";
    private ArrayList<Model> modelArrayList;
    private CustomAdapter customAdapter;
    String secretKey = "enigma";

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_usuarios);
        final Usuario usuarioPasado = (Usuario) getIntent().getSerializableExtra(EXTRA_USUARIO);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        xmlToJava();


        //iniciando toolbar
        toolbar = findViewById(R.id.toolbarAñadir);
        toolbar.setTitle("Administrador - " + usuarioPasado.getNombre());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        //iniciando spinner

        spinner1.setPrompt("Selecciona un rol");
        String[] opciones = {"Admin", "Usuario", "Invitado"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, opciones);
        spinner1.setAdapter(adapter);


        ArrayList<Raspberry> listaRaspberrys = new ArrayList();
        listaRaspberrys = obtenerListaRaspberry();
        ArrayList<String> opciones3 = new ArrayList(); //en un array de string meto
        //las raspberrys disponibles de la BD y lo muestro en un spinner si fuera un array
        //de objetos no funcionaria el counstrucotr del spinner
        for (Raspberry r : listaRaspberrys) {
            opciones3.add(r.getRaspberryId() + ":" + r.getModelo());
        }


        String text = spinner1.getSelectedItem().toString(); //con esto obtengo el texto actual
        botonAñadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Hashear e = new Hashear();
                Usuario usuario1 = new Usuario();
                usuario1.setNombre(nombreInsertar.getText().toString());
                String contraseñaCodificada = e.encode(secretKey, contraseñaInsertar.getText().toString());
                usuario1.setContraseña(contraseñaCodificada);

                usuario1.setEmail(direccionInsertar.getText().toString());
                usuario1.setRol(spinner1.getSelectedItem().toString()); //obtengo la opcion que esta seleccionada
                //usuario1.setRol("invitado");
                if (usuario1.getNombre().trim().length() == 0 || usuario1.getContraseña().trim().length() == 0 || usuario1.getEmail().trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();

                } else {
                    boolean detector = false;
                    ArrayList<Usuario> listaUsuarios = new ArrayList();
                    listaUsuarios = obtenerLista();


                    for (Usuario aux : listaUsuarios) {

                        if (aux.getNombre().equals(usuario1.getNombre()) || aux.getEmail().equals(usuario1.getEmail())) {
                            detector = true;//hemos detectado un usuario con esos datos
                            break;

                        }

                    }

                    if (detector == true) {
                        Toast.makeText(getApplicationContext(), "Ya existe un usuario con ese nombre o email", Toast.LENGTH_SHORT).show();
                        limpiarCajas();
                    } else {
                        insertarUsuario(usuario1);
                        Toast.makeText(getApplicationContext(), "Usuario añadido con éxito", Toast.LENGTH_SHORT).show();
                        limpiarCajas();
                        Intent intent = new Intent(v.getContext(), MenuAdmin.class);
                        final Usuario usuarioPasado = (Usuario) getIntent().getSerializableExtra(EXTRA_USUARIO);
                        intent.putExtra(EXTRA_USUARIO, usuarioPasado);
                        startActivityForResult(intent, 0);
                    }
                }


            }

        });


    }

    private ArrayList<Model> getModel(boolean isSelect) { //aqui es donde obtengo lo que tiene seleccionado el checkbox
        ArrayList<Model> list = new ArrayList<>();
        ArrayList<Raspberry> listaRaspberrys = new ArrayList();
        listaRaspberrys = obtenerListaRaspberry();

        for (Raspberry r : listaRaspberrys) {

            Model model = new Model();
            model.setSelected(isSelect);
            model.setAnimal(r.getRaspberryId() + ":" + r.getModelo() + "esta clickado");
            // Toast toast = Toast.makeText(getApplicationContext(),r.getRaspberryId()+":"+r.getModelo(), Toast.LENGTH_LONG);
            // toast.show();
            list.add(model);
        }
        return list;
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
                AlertDialog.Builder alert = new AlertDialog.Builder(AnadirUsuarios.this);
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

                        Intent llamada = new Intent(AnadirUsuarios.this, MainActivity.class);
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


    private void limpiarCajas() {
        nombreInsertar.setText("");
        contraseñaInsertar.setText("");
        direccionInsertar.setText("");


    }

    private void xmlToJava() {
        botonAñadir = findViewById(R.id.botonAceptarXML2);
        spinner1 = findViewById(R.id.spPais);

        nombreInsertar = findViewById(R.id.nombreInsertar);
        contraseñaInsertar = findViewById(R.id.contraseñaInsertar);
        direccionInsertar = findViewById(R.id.direccionInsertar);


    }

    public void insertarUsuario(Usuario usuario1) {
        try {

            String equipoServidor = "servidorwebjordan.ddns.net";
            int puertoServidor = 30500;
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


    public ArrayList<Raspberry> obtenerListaRaspberry() {
        ArrayList<Raspberry> listaRaspberrys = new ArrayList();
        try {

            String equipoServidor = "servidorwebjordan.ddns.net";
            int puertoServidor = 30510;
            Socket socketCliente = new Socket(equipoServidor, puertoServidor);

            ObjectInputStream listaRecibida = new ObjectInputStream(socketCliente.getInputStream());//me preparo para recibir
            listaRaspberrys = (ArrayList) listaRecibida.readObject(); //objeto leido y metido en usuario1 /lo recibod
            socketCliente.close();
            return listaRaspberrys;

        } catch (IOException ex) {
            System.out.println(ex.getMessage());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return listaRaspberrys;

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


}
