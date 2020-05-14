package es.jordan.sistemaalarma;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class EliminarUsuario extends AppCompatActivity {
Button eliminarUsu;
EditText texto2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_usuario);
        xmlToJava();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        if (null != toolbar) {
            setSupportActionBar(toolbar);
        }

        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayShowTitleEnabled(false);//quitamos el titulo del toolbar
        actionBar.setDisplayHomeAsUpEnabled(true);//indicando en el manifest quien es el padre de esta actividad
        //cuando le de a la flecha volvera ahi


        eliminarUsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Usuario usuario1 = new Usuario();
                usuario1.setNombre(texto2.getText().toString());

                eliminarUsu(usuario1);
                Toast.makeText(getApplicationContext(), "Usuario eliminado con exito", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), MenuAdmin.class);
                startActivityForResult(intent, 0);

            }

        });
        
        
        
        
        
    }

    private void eliminarUsu(Usuario usuario1) {
        try {

            String equipoServidor = "192.168.1.40";
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

    private void xmlToJava() {
        eliminarUsu=(Button)findViewById(R.id.botonEliminar);
        texto2=(EditText)findViewById(R.id.nombre2);
        
        
    }
}
