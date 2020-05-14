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

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar3);
        if (null != toolbar) {
            setSupportActionBar(toolbar);
        }

        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayShowTitleEnabled(false);//quitamos el titulo del toolbar
        actionBar.setDisplayHomeAsUpEnabled(true);//indicando en el manifest quien es el padre de esta actividad
        //cuando le de a la flecha volvera ahi
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
                if (usuario1.getNombre().trim().length() == 0 || usuario1.getContraseña().trim().length() == 0 || usuario1.getEmail().trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();

                }
                else {
                    insertarUsuario(usuario1);
                    limpiarCajas();
                    Toast.makeText(getApplicationContext(), "Usuario añadido con éxito", Toast.LENGTH_SHORT).show();

                }

            }

        });









    }

    private void limpiarCajas() {
        nombreInsertar.setText("");
        contraseñaInsertar.setText("");
        direccionInsertar.setText("");


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
