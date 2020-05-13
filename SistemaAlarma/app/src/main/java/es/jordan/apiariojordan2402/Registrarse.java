package es.jordan.sistemaalarma;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class Registrarse extends AppCompatActivity {
    private final String EXTRA_USUARIO = "";
    Button botonCancelar;
    Button botonRegistrarse;
    EditText editNombre;

    EditText editContraseña;
    EditText editEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);
        xmlToJava();
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
                usuario1.setRol("usuario");

                ComponenteAD componente = new ComponenteAD(getApplicationContext());
                componente.openForWrite();
                componente.openForRead();
                ArrayList<Usuario> listaUsuarios = componente.leerUsuarios();
                boolean encontradoUsuario=false;
                boolean encontradoVacio=false;
                if (editNombre.getText().toString().trim().length() == 0 || editContraseña.getText().toString().trim().length() == 0 || editEmail.getText().toString().trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
                    encontradoVacio=true;
                }
                   for (Usuario aux : listaUsuarios) {
                            if (aux.getNombre().equals(usuario1.getNombre())) {
                                Toast.makeText(getApplicationContext(), "El usuario ya existe", Toast.LENGTH_SHORT).show();
                                encontradoUsuario=true;
                                break;//queremos salir del loop si esto pasa
                            }

                        }

             if(encontradoVacio==false && encontradoUsuario==false) {
                 componente.insertUsuario(usuario1);//si llegamos aqui es porque podemos insertarlo

                 AlertDialog.Builder alert = new AlertDialog.Builder(Registrarse.this);//avisamos de registro exitoso
                 alert.setTitle("Registro exitoso");
                 alert.setCancelable(true);
                 alert.setIcon(R.drawable.usuario);
                 alert.setMessage("Usuario con nombre "+usuario1.getNombre()+" has sido registrado");
                 alert.setPositiveButton("Ok, volver al menú", new DialogInterface.OnClickListener() {//onclick del boton del dialog
                     public void onClick(DialogInterface dialog, int which) {
                         Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                         startActivityForResult(intent, 0);
                     }
                 });
                 alert.create().show();

             }


            }

        });
    }

    public void xmlToJava() {

        botonCancelar = (Button) findViewById(R.id.botonCancelarXML);
        botonRegistrarse = (Button) findViewById(R.id.botonRegistrarXML);
        editNombre = (EditText) findViewById(R.id.nombre1);

        editContraseña = (EditText) findViewById(R.id.contraseña1);
        editEmail = (EditText) findViewById(R.id.direccion1);
    }


}
