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
                insertarUsuario(usuario1);

                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivityForResult(intent, 0);

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
}