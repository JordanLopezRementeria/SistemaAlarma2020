package es.jordan.sistemaalarma;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;

import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener {
    private final String EXTRA_USUARIO = "";
    Button botonIniciar, botonRegistrar;
    EditText nombre1, email1, contraseña1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        xmlToJava();
        activarAnimacion();
       //Toast.makeText(getApplicationContext(), "Probando versiones", Toast.LENGTH_SHORT).show();

        botonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Registrarse.class);
                startActivityForResult(intent, 0);


            }
        });


        botonIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ComponenteAD componente = new ComponenteAD(getApplicationContext());
              //  componente.openForWrite();
               // componente.openForRead();
                boolean encontradoEnBD = false;
                boolean encontradoVacio = false;

                if (nombre1.getText().toString().trim().length() == 0 || contraseña1.getText().toString().trim().length() == 0 || email1.getText().toString().trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
                    encontradoVacio = true;
                }

                Usuario usuario1 = new Usuario();
                usuario1.setNombre(nombre1.getText().toString());
                usuario1.setEmail(email1.getText().toString());
                usuario1.setContraseña(contraseña1.getText().toString());




            }
        });


    }

    public void limpiar() {
        nombre1.setText("");
        email1.setText("");
        contraseña1.setText("");
    }


    public void pantallaAdmin(Usuario usuario1) {
        usuario1.setNombre(nombre1.getText().toString());
        usuario1.setContraseña(contraseña1.getText().toString());
        usuario1.setEmail(email1.getText().toString());

        Intent intent2 = new Intent(MainActivity.this, MenuAdmin.class); //ponemos en la actividad el objeto usuario
        intent2.putExtra(EXTRA_USUARIO, usuario1);
        startActivity(intent2);
    }


    public void pantallaUsuario(Usuario usuario1) {
        Intent intent = new Intent(MainActivity.this, MenuInvitado.class); //ponemos en la actividad el objeto usuario
        intent.putExtra(EXTRA_USUARIO, usuario1);
        startActivity(intent);
    }

    public void pantallaApicultor(Usuario usuario1) {
        Intent intent = new Intent(MainActivity.this, MenuVip.class); //ponemos en la actividad el objeto usuario
        intent.putExtra(EXTRA_USUARIO, usuario1);
        startActivity(intent);
    }

    public void xmlToJava() {

        botonIniciar = (Button) findViewById(R.id.botonIniciarXML);
        botonRegistrar = (Button) findViewById(R.id.botonAceptarXML);
        nombre1 = (EditText) findViewById(R.id.nombreXML);
        contraseña1 = (EditText) findViewById(R.id.contraseñaXML);
        email1 = (EditText) findViewById(R.id.emailXML);


    }


    public void activarAnimacion()
    {
        ImageView miImagen = (ImageView) findViewById(R.id.imagenXML);
        Animation miAnim = AnimationUtils.loadAnimation(this, R.anim.anim2);
        miImagen.startAnimation(miAnim);
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), "No se ha encontrado usuario", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(getApplicationContext(), "No se ha encontrado usuario", Toast.LENGTH_SHORT).show();
    }

}