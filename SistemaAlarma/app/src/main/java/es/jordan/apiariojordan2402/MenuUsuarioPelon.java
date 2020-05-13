package es.jordan.sistemaalarma;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
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

                if(miRecycler.getVisibility()==View.VISIBLE) //es como un togglebutton
                {
                    miRecycler.setVisibility(View.GONE);
                }
                else
                    miRecycler.setVisibility(View.VISIBLE);


                editNombre.setVisibility(View.GONE);
                editEmail.setVisibility(View.GONE);
                editContraseña.setVisibility(View.GONE);

                ComponenteAD componente = new ComponenteAD(getApplicationContext());
                componente.openForRead();
                //componente.openForWrite();
                //componente.borrarTodos();
                ArrayList<Usuario>listaUsuarios=new ArrayList<>();
                listaUsuarios=componente.leerUsuarios();


                miLista = new ArrayList<DatosColmena>();//Lista de objetos
                for(Usuario aux:listaUsuarios)
                {
                    if(aux.getRol().equals("admin"))
                    {
                        miLista.add(new DatosColmena(aux.getNombre(), "Rol admin", R.drawable.admin));
                    }
                    else if(aux.getRol().equals("apicultor"))
                    {
                        miLista.add(new DatosColmena(aux.getNombre(), "Rol apicultor", R.drawable.apicultor_foreground));
                    }
                    else
                        miLista.add(new DatosColmena(aux.getNombre(), "Rol usuario", R.drawable.usuario));

                    final AdaptadorDatos elAdaptador = new AdaptadorDatos(miLista);
                    elAdaptador.notifyDataSetChanged();
                    miRecycler.setAdapter(elAdaptador);
                }


            }
        });


        imagenVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                miRecycler.setVisibility(View.INVISIBLE);
                editNombre.setVisibility(View.INVISIBLE);
                editEmail.setVisibility(View.INVISIBLE);
                editContraseña.setVisibility(View.INVISIBLE);
                AlertDialog.Builder alert = new AlertDialog.Builder(MenuUsuarioPelon.this);
                alert.setTitle("Datos de usuario");
                ComponenteAD componente = new ComponenteAD(getApplicationContext());
                componente.openForRead();
                Usuario usuario2=new Usuario();

                usuario2.setNombre(usuarioPasado.getNombre());  //estoy poniendo en un objeto usuario los datos del login
                usuario2.setEmail(usuarioPasado.getEmail());
                usuario2.setContraseña(usuarioPasado.getContraseña());

                ArrayList<Usuario> listaUsuarios = componente.leerUsuarios();
                for (Usuario aux : listaUsuarios) {
                    if (aux.getNombre().equals(usuario2.getNombre()) && aux.getContraseña().equals(usuario2.getContraseña()) && (aux.getEmail().equals(usuario2.getEmail()))) {

                        usuario2.setNombre(aux.getNombre());
                        usuario2.setContraseña(aux.getContraseña());
                        usuario2.setEmail(aux.getEmail());
                        usuario2.setRol(aux.getRol());

                        //metemos los datos que faltan y salimos
                        break;//usuario encontrado salimos del loop
                    }

                }
                //ahora sabemos que usuario 2 tiene todos los datos metidos y esta en la bd
                alert.setCancelable(true);
                alert.setIcon(R.drawable.ab2);
                alert.setMessage("Nombre: "+usuario2.getNombre()+" Email: "+usuario2.getEmail()+" Rol: "+usuario2.getRol());
                alert.setPositiveButton("Leído", new DialogInterface.OnClickListener() {//onclick del boton del dialog
                    public void onClick(DialogInterface dialog, int which) {
                      dialog.cancel();//vuelvo a la intent sin mas
                    }
                });
                alert.create().show();
            }
        });

        imagenActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                miRecycler.setVisibility(View.INVISIBLE);
                editNombre.setVisibility(View.VISIBLE);
                editEmail.setVisibility(View.VISIBLE);
                editContraseña.setVisibility(View.VISIBLE);
                if (editNombre.getText().toString().trim().length() == 0 || editContraseña.getText().toString().trim().length() == 0 || editEmail.getText().toString().trim().length() == 0)
                    Toast.makeText(getApplicationContext(), "Escribe el nombre,contraseña y email que deseas tener", Toast.LENGTH_LONG).show();
                else {
                    ComponenteAD componente = new ComponenteAD(getApplicationContext());
                    Usuario usuarioDatosNuevos = new Usuario();

                    usuarioDatosNuevos.setNombre(editNombre.getText().toString());
                    usuarioDatosNuevos.setContraseña(editContraseña.getText().toString());
                    usuarioDatosNuevos.setEmail(editEmail.getText().toString());
                    usuarioDatosNuevos.setRol("usuario");  //el rol no lo puede cambiar siempre sera usuario
                    componente.openForWrite();
                    componente.modificarUsuario2(usuarioPasado.getNombre(),usuarioDatosNuevos);
                    //tenemos que actualizar el objeto que nos veia de la anterior pantalla con los datos nuevos
                    //por si volvemos a llamar a modificar, que modifique lo actual
                    usuarioPasado.setNombre(usuarioDatosNuevos.getNombre());
                    usuarioPasado.setEmail(usuarioDatosNuevos.getEmail());
                    usuarioPasado.setContraseña(usuarioDatosNuevos.getContraseña());

                   //limpiamos los edit
                    editNombre.setText("");
                    editEmail.setText("");
                    editContraseña.setText("");
                    Toast.makeText(getApplicationContext(), "Usuario modificado con exito", Toast.LENGTH_SHORT).show();

                }

            }
        });





    }


  public void xmlToJava() {

        imagenVer = (ImageView) findViewById(R.id.imagenverXML);
        imagenActualizar = (ImageView) findViewById(R.id.imagenmodificarXML);
        imagenListar = (ImageView) findViewById(R.id.imagenlistarXML);

        editNombre = (EditText) findViewById(R.id.nombre1);
        editContraseña = (EditText) findViewById(R.id.contraseña1);
        editEmail = (EditText) findViewById(R.id.direccion1);
        miRecycler = (RecyclerView) findViewById(R.id.miRecyclerVista2);
    }


}

