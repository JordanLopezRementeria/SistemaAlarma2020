package es.jordan.sistemaalarma;


import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class AnadirUsuarios extends AppCompatActivity {
Button botonAñadir;
Spinner spinner1;
EditText nombreInsertar,contraseñaInsertar,direccionInsertar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_usuarios);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        xmlToJava();

        spinner1 = (Spinner) findViewById(R.id.spPais);
        spinner1.setPrompt("Selecciona un rol");
        String []opciones={"Admin","Usuario","Invitado"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, opciones);
        spinner1.setAdapter(adapter);

        String text = spinner1.getSelectedItem().toString(); //con esto obtengo el texto actual
        botonAñadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Usuario usuario1 = new Usuario();
                usuario1.setNombre(nombreInsertar.getText().toString());
                usuario1.setContraseña(contraseñaInsertar.getText().toString());
                usuario1.setEmail(direccionInsertar.getText().toString());
                //usuario1.setRol("invitado");
                insertarUsuario(usuario1);

            }

        });









    }

    private void xmlToJava() {
        botonAñadir=(Button)findViewById(R.id.botonAceptarXML2);
        spinner1 = (Spinner) findViewById(R.id.spPais);
        nombreInsertar=(EditText) findViewById(R.id.nombreInsertar);
        contraseñaInsertar=(EditText) findViewById(R.id.contraseñaInsertar);
        direccionInsertar=(EditText)findViewById(R.id.direccionInsertar);

    }

    public void insertarUsuario(Usuario usuario1) {
        try {

            String equipoServidor = "192.168.1.40";
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
