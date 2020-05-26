package es.jordan.sistemaalarma;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;

import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;



public class Registrarse extends AppCompatActivity {
    private final String EXTRA_USUARIO = "";
    Button botonCancelar;
    Button botonRegistrarse;
    EditText editNombre;
    EditText editContraseña;
    EditText editEmail;
    TTSManager ttsManager = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);
        //lineas necesarias para permitir los sockets
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        xmlToJava();
        textoToVoz();

        botonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivityForResult(intent, 0);
            }
        });


        botonRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Usuario usuario1 = new Usuario();
                usuario1.setNombre(editNombre.getText().toString());
                usuario1.setContraseña(editContraseña.getText().toString());
                usuario1.setEmail(editEmail.getText().toString());
                usuario1.setRol("invitado");
                //usuario1.setRol("invitado");
                if (usuario1.getNombre().trim().length() == 0 || usuario1.getContraseña().trim().length() == 0 || usuario1.getEmail().trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();

                }
                else {
                    boolean detector=false;
                    ArrayList<Usuario> listaUsuarios = new ArrayList();
                    listaUsuarios=obtenerLista();


                    for (Usuario aux: listaUsuarios) {

                        if (aux.getNombre().equals(usuario1.getNombre()) || aux.getEmail().equals(usuario1.getEmail())) {
                            detector=true;//hemos detectado un usuario con esos datos
                            break;

                        }

                    }

                    if(detector==true)
                    {
                        Toast.makeText(getApplicationContext(), "Ya existe un usuario con ese nombre o email", Toast.LENGTH_SHORT).show();
                        limpiarCajas();
                    }
                    else {
                        insertarUsuario(usuario1);
                        ttsManager.initQueue("Bienvenido "+usuario1.getNombre().toString());
                        limpiarCajas();
                        Intent intent = new Intent(v.getContext(), MainActivity.class);
                        startActivityForResult(intent, 0);
                    }
                }

            }

        });


    }


    public void xmlToJava() {
        botonRegistrarse = findViewById(R.id.botonAceptarXML);
        botonCancelar = findViewById(R.id.botonCancelarXML);
        editNombre = findViewById(R.id.nombre1);
        editContraseña = findViewById(R.id.contraseña1);
        editEmail = findViewById(R.id.direccion1);
    }

    public void textoToVoz() {
        ttsManager = new TTSManager();
        ttsManager.init(this);
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
    private void limpiarCajas() {
        editNombre.setText("");
        editContraseña.setText("");
        editEmail.setText("");


    }




}