package es.jordan.apiariojordan2402;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private final String EXTRA_USUARIO = "";
    Button botonIniciar, botonRegistrar;
    EditText nombre1, email1, contraseña1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ComponenteAD c = new ComponenteAD(this);


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
                ComponenteAD componente = new ComponenteAD(getApplicationContext());
                componente.openForWrite();
                componente.openForRead();
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

                ArrayList<Usuario> listaUsuarios = componente.leerUsuarios(); //vamos a buscar si esta el usuario en la bd y si coinciden los datos
                for (Usuario aux : listaUsuarios) {
                    if (aux.getNombre().equals(usuario1.getNombre()) && aux.getContraseña().equals(usuario1.getContraseña()) && (aux.getEmail().equals(usuario1.getEmail()))) {
                        usuario1.setRol(aux.getRol()); //ahora ya sabemos que rol tiene ademas de haber visto que existe
                        encontradoEnBD = true;
                        break;//queremos salir del loop si esto pasa
                    }

                }


                if (encontradoEnBD && encontradoVacio == false) { //si llegamos aqui es porque 100% el usuario esta en la BD y ha metido todos los datos

                       if (usuario1.getRol().equals("admin")) { //parte admin
                       limpiar();
                        pantallaAdmin(usuario1);
                    }
                       else if (usuario1.getRol().equals("apicultor")) { //parte admin
                           limpiar();
                           pantallaApicultor(usuario1);
                    }

                       else {              //si esta en bd y no es ni admin, ni apicultor entonces es usuario
                           limpiar();
                        pantallaUsuario(usuario1);
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "El usuario no existe", Toast.LENGTH_SHORT).show();
                    limpiar();

                }


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
        usuario1.setRol("admin");
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


}