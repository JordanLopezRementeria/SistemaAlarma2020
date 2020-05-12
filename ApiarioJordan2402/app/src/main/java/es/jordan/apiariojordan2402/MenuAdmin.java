package es.jordan.apiariojordan2402;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
import java.util.Iterator;
import java.util.List;

public class MenuAdmin extends AppCompatActivity {
    ImageView imagenAñadir,imagenModificar,imagenListar,imagenEliminar;
    RecyclerView miRecycler;
    ArrayList<DatosColmena> miLista;
    TextView texto;
    EditText editNombre,editContraseña,editEmail,editNombreOriginal,editRol;

    public ListView listView;
    private final String EXTRA_USUARIO = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_admin);
        xmlToJava();

        Intent it = getIntent();
        final Usuario usuarioPasado = (Usuario) it.getSerializableExtra(EXTRA_USUARIO);
       // texto.setText("Bienvenid@ "+usuarioPasado.getNombre());

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
                  miRecycler.setVisibility(View.INVISIBLE);
              }
              else
                  miRecycler.setVisibility(View.VISIBLE);


                editNombreOriginal.setVisibility(View.GONE);
                editNombre.setVisibility(View.GONE);
                editEmail.setVisibility(View.GONE);
                editContraseña.setVisibility(View.GONE);
                editRol.setVisibility(View.GONE);

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
        imagenAñadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                miRecycler.setVisibility(View.INVISIBLE);
                editNombreOriginal.setVisibility(View.GONE);
                editNombre.setVisibility(View.VISIBLE);
                editEmail.setVisibility(View.VISIBLE);
                editContraseña.setVisibility(View.VISIBLE);
                editRol.setVisibility(View.VISIBLE);
                ComponenteAD componente = new ComponenteAD(getApplicationContext());
                componente.openForWrite();
                componente.openForRead();
                boolean existeYa=false;
                ArrayList<Usuario> listaUsuarios = componente.leerUsuarios(); //vamos a buscar si esta el usuario en la bd y si coinciden los datos
                for (Usuario aux : listaUsuarios) {
                    if (aux.getNombre().equals(editNombre.getText().toString()) && aux.getContraseña().equals(editContraseña.getText().toString()) && (aux.getEmail().equals(editEmail.getText().toString()))) {
                        existeYa=true;
                        break;//queremos salir del loop si esto pasa
                    }

                }

              if(existeYa)
              {
                  Toast.makeText(getApplicationContext(), "El usuario ya existe en la base de datos", Toast.LENGTH_LONG).show();

              }
                else
              {
                if (editNombre.getText().toString().trim().length() == 0 || editContraseña.getText().toString().trim().length() == 0 || editEmail.getText().toString().trim().length() == 0 || editNombreOriginal.getText().toString().length() != 0){
                    Toast.makeText(getApplicationContext(), "Escribe el nombre,contraseña,email y rol a añadir", Toast.LENGTH_LONG).show();
                    limpiarDatos();
                }
               else if (rolApropiado()==false) //si no es ninguno de los 3 roles metodo
                    {
                        Toast.makeText(getApplicationContext(), "Rol tiene que ser admin,usuario o apicultor", Toast.LENGTH_SHORT).show();
                        editRol.setText("");
                    }
                else {
                    Usuario usuarioInsertado = new Usuario();

                    usuarioInsertado.setNombre(editNombre.getText().toString());
                    usuarioInsertado.setEmail(editEmail.getText().toString());
                    usuarioInsertado.setContraseña(editContraseña.getText().toString());
                    usuarioInsertado.setRol(editRol.getText().toString());


                    long numero=componente.insertUsuario(usuarioInsertado);
                    limpiarDatos();
                    Toast.makeText(getApplicationContext(), "Usuario insertado con exito", Toast.LENGTH_SHORT).show();
                }
            }}
        });




        imagenModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                miRecycler.setVisibility(View.INVISIBLE);
                ComponenteAD componente = new ComponenteAD(getApplicationContext());
                componente.openForWrite();

                editNombreOriginal.setVisibility(View.VISIBLE);
                editNombre.setVisibility(View.VISIBLE);
                editEmail.setVisibility(View.VISIBLE);
                editContraseña.setVisibility(View.VISIBLE);
                editRol.setVisibility(View.VISIBLE);

                Usuario usuario1=new Usuario();
                usuario1.setNombre(editNombre.getText().toString());
                usuario1.setContraseña(editContraseña.getText().toString());
                usuario1.setEmail(editEmail.getText().toString());
                usuario1.setRol(editRol.getText().toString());
                if (editNombreOriginal.getText().toString().trim().length() == 0 || editNombre.getText().toString().trim().length() == 0 || editContraseña.getText().toString().trim().length() == 0 || editEmail.getText().toString().trim().length() == 0 || editRol.getText().toString().trim().length() == 0 )
                {
                    Toast.makeText(getApplicationContext(), "Escribe el nombre antiguo,nombre nuevo,contraseña,email y rol a añadir", Toast.LENGTH_LONG).show();
                    limpiarDatos();
                }
                else if(!rolApropiado())
                {
                    editRol.setText("");
                    Toast.makeText(getApplicationContext(), "El rol tiene que ser admin,apicultor o usuario", Toast.LENGTH_LONG).show();

                }
                else {

                    componente.modificarUsuario2(editNombreOriginal.getText().toString(),usuario1);
                    limpiarDatos();
                    Toast.makeText(getApplicationContext(), "Usuario modificado con exito", Toast.LENGTH_SHORT).show();

                }


            }
        });


        imagenEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                miRecycler.setVisibility(View.INVISIBLE);
                ComponenteAD componente = new ComponenteAD(getApplicationContext());
                componente.openForWrite();
                editNombreOriginal.setVisibility(View.VISIBLE);
                editNombre.setVisibility(View.INVISIBLE);
                editEmail.setVisibility(View.INVISIBLE);
                editContraseña.setVisibility(View.INVISIBLE);
                editRol.setVisibility(View.INVISIBLE);

                if (editNombreOriginal.getText().toString().trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Escribe el nombre del usuario a borrar", Toast.LENGTH_SHORT).show();
                    limpiarDatos();
                }
                else {

                    componente.borrarUsuario(editNombreOriginal.getText().toString());
                    limpiarDatos();
                    Toast.makeText(getApplicationContext(), "Usuario borrado con exito", Toast.LENGTH_SHORT).show();
 }


            }
        });

    }
    public void ventanaEmergente() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Lista de usuarios");
        alert.setIcon(R.drawable.ab2);
        ComponenteAD c = new ComponenteAD(getApplicationContext());
        c.openForRead();
        //c.openForWrite();
        //c.borrarTodos();

        ArrayList<Usuario> listaUsuarios = c.leerUsuarios();
        if(listaUsuarios.size()==0 || listaUsuarios==null)
        {
            alert.setMessage("No hay usuarios que mostrar");
            alert.create().show();
        }
            else
                {

            ArrayList<String> listaBonita = new ArrayList();
                    //for each pones primero el tipo objeto y la lista que quieres recorrer objeto por objeto
            for (Usuario aux: listaUsuarios) {

                listaBonita.add("Nombre: " + aux.getNombre() + " Email: " + aux.getEmail());//no mostramos contraseña
            }

            alert.setMessage(listaBonita.toString());
            alert.create().show();
        }
    }
    public void xmlToJava()
    {

        imagenAñadir = (ImageView) findViewById(R.id.imagenAñadirXML);
        imagenModificar = (ImageView) findViewById(R.id.imagenmodificarXML);
        imagenListar = (ImageView) findViewById(R.id.imagenlistarXML);
        imagenEliminar = (ImageView) findViewById(R.id.imageneliminarXML);
        texto = (TextView) findViewById(R.id.saludo);
        editNombreOriginal = (EditText) findViewById(R.id.nombreOriginal);
        editNombre = (EditText) findViewById(R.id.nombre1);
        editContraseña = (EditText) findViewById(R.id.contraseña1);
        editRol = (EditText) findViewById(R.id.rol1);
        editEmail = (EditText) findViewById(R.id.direccion1);
        miRecycler = (RecyclerView) findViewById(R.id.miRecyclerVista);
    }

public void limpiarDatos()
{
    editNombreOriginal.setText("");
    editNombre.setText("");
    editEmail.setText("");
    editContraseña.setText("");
    editRol.setText("");
}

public boolean rolApropiado()
{

    if(editRol.getText().toString().equals("admin") || editRol.getText().toString().equals("usuario") || editRol.getText().toString().equals("apicultor"))
    return true;
    else
    return false;

}


}

