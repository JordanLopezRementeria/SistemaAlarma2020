package es.jordan.sistemaalarma;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MenuUsuarioPelon extends AppCompatActivity {
ImageView imagenVer,imagenActualizar,imagenListar;
RecyclerView miRecycler;
ArrayList<DatosColmena> miLista;
TextView texto;
EditText editNombre,editContraseña,editEmail;
Button cerrar;
    final int COD_MARCADA = 10;
    final int COD_FOTO = 20;
    private final String EXTRA_USUARIO = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_usuario_pelon);
        xmlToJava();

        Intent it = getIntent();
        final Usuario usuarioPasado = (Usuario) it.getSerializableExtra(EXTRA_USUARIO);

        miLista=new ArrayList<DatosColmena>();//Lista de objetos
        miRecycler.setLayoutManager(new LinearLayoutManager(this));
        AdaptadorDatos elAdaptador=new AdaptadorDatos(miLista);
        elAdaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence texto="Pulsado"+miLista.get(miRecycler.getChildAdapterPosition(v)).getNombre();
                Toast toast = Toast.makeText(getApplicationContext(), texto, Toast.LENGTH_LONG);
                toast.show();
            }
        });




        miRecycler.setAdapter(elAdaptador);

      imagenListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





            }
        });


        imagenVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        imagenActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


        cerrar.setOnClickListener(new View.OnClickListener() { //on click de desconectar
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(MenuUsuarioPelon.this);
                alert.setTitle("Advertencia");
                alert.setCancelable(true);
                alert.setIcon(R.drawable.exit);

                alert.setMessage("¿Desea desconectarse "+usuarioPasado.getNombre()+"?");

                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });
                alert.setPositiveButton("Si", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent llamada=new Intent(MenuUsuarioPelon.this, MainActivity.class);
                        startActivity(llamada);
                    }
                });
                alert.create().show();







            }
        });


    }


  public void xmlToJava() {

        imagenVer = (ImageView) findViewById(R.id.imagenverXML);
        imagenActualizar = (ImageView) findViewById(R.id.imagenmodificarXML);
        imagenListar = (ImageView) findViewById(R.id.imagenlistarXML);
        cerrar=(Button)findViewById(R.id.cerrarUsuario);
        editNombre = (EditText) findViewById(R.id.nombre1);
        editContraseña = (EditText) findViewById(R.id.contraseña1);
        editEmail = (EditText) findViewById(R.id.direccion1);
        miRecycler = (RecyclerView) findViewById(R.id.miRecyclerVista2);
    }

    private void seleccionaFoto() {
        Intent miItem = new Intent(Intent.ACTION_GET_CONTENT,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        miItem.setType("image/");
        startActivityForResult(miItem.createChooser(miItem, "Selecciona una aplicación"),
                COD_MARCADA);

    }

    public void limpiar()
    {
        editNombre.setText("");
        editEmail.setText("");
        editContraseña.setText("");
    }
}

