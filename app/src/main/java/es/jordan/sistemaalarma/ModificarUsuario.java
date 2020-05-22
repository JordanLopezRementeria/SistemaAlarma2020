package es.jordan.sistemaalarma;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class ModificarUsuario extends AppCompatActivity {
    private final String EXTRA_USUARIO = "";
    Button botonCancelar;
    Button botonAceptar;
    EditText editNombre;

    EditText editContraseña;
    EditText editEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_usuario);
        botonCancelar = findViewById(R.id.botonCancelarXML);
        botonAceptar = findViewById(R.id.botonAceptarXML);
        editNombre = findViewById(R.id.nombre1);

        editContraseña = findViewById(R.id.contraseña1);
        editEmail = findViewById(R.id.direccion1);
        //hacemos una instancia de la clase componente para poder hacer crud



        botonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(v.getContext(), MenuUsuarioPelon.class);
                startActivityForResult(intent, 0);
            }
        });

        botonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Usuario usuario1 = new Usuario();
                usuario1.setNombre(editNombre.toString());
                usuario1.setContraseña(editContraseña.toString());
                usuario1.setEmail(editEmail.toString());

                if (editNombre.getText().toString().trim().length() == 0 || editContraseña.getText().toString().trim().length() == 0 || editEmail.getText().toString().trim().length() == 0)
                    Toast.makeText(getApplicationContext(), "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
                else {
                    //c.openForWrite();

                    Intent intent = new Intent(v.getContext(), MenuUsuarioPelon.class);
                    startActivityForResult(intent, 0);
                }

            }

        });
    }


}