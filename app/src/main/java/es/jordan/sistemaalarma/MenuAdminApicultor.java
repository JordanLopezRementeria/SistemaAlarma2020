package es.jordan.sistemaalarma;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MenuAdminApicultor extends AppCompatActivity  {

    private Button hablarAhoraBoton;
    ImageView imagenListar,imagenBorrar,imagenAñadir;
    private EditText editText,editNombreApicultor,editNombreApiario;
    private ListView lv;
    TTSManager ttsManager=null;
    int pos;
    Button cerrar3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_admin_apicultor);

        xmlToJava();
        textoToVoz();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        final ArrayList<itemColmena> itemsCompra = obtenerItems();

        final ItemColmenaAdapter adapter = new ItemColmenaAdapter(this, itemsCompra);
        lv.setClickable(true); //para poder pinchar en los elementos de la lista
        lv.setAdapter(adapter);



        final ItemColmenaAdapter adaptador=new ItemColmenaAdapter();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                editText.setVisibility(View.GONE);

                pos = position;                                        //item.getNombre() tb sirve
                itemColmena itemSeleccionado= (itemColmena) adapter.getItem(position);
                //Toast.makeText(getApplicationContext(), item.nombre+item.tipo, Toast.LENGTH_LONG).show();
                editText.setText(itemSeleccionado.nombre);
                String text=editText.getText().toString();
                ttsManager.initQueue(text);//quiero que se oiga ya cuando pulse




            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() { //pulsar largo
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int position, long arg3) {
                ComponenteAD3 componente = new ComponenteAD3(getApplicationContext());
                componente.openForWrite();
                componente.openForRead();
                //visualmente
                itemColmena itemSeleccionado2= (itemColmena) adapter.getItem(position);
                AlertDialog.Builder alert = new AlertDialog.Builder(MenuAdminApicultor.this);
                alert.setTitle("Advertencia");
                alert.setCancelable(true);
                alert.setIcon(R.drawable.eliminar1_foreground);
                String nombreAborrar=(itemSeleccionado2.nombre);
                alert.setMessage("Está seguro que desea borrar el apiario de nombre "+nombreAborrar+" seleccionado");

                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });
                alert.setPositiveButton("Si, estoy seguro", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        itemColmena itemSeleccionado2= (itemColmena) adapter.getItem(position);//antes de borrar preguntamos
                        itemsCompra.remove(itemSeleccionado2);
                        adaptador.notifyDataSetChanged(); //aviso y refresco
                        lv.setVisibility(View.INVISIBLE);
                        lv.setVisibility(View.VISIBLE);
 }
                });
                alert.create().show();



                //bd
                ArrayList<Apiario>listaApiariosBD=new ArrayList();
                listaApiariosBD=componente.leerApiarios();
                nombreAborrar=(itemSeleccionado2.nombre);
                componente.borrarApiario(nombreAborrar);


                return true;
            }
        });



        imagenListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editNombreApiario.setVisibility(View.INVISIBLE);
                editNombreApicultor.setVisibility(View.INVISIBLE);
                if(lv.getVisibility()==View.VISIBLE) //es como un togglebutton
                {
                    lv.setVisibility(View.INVISIBLE);

                }
                else {
                    lv.setVisibility(View.VISIBLE);

                }

                final ArrayList<itemColmena> itemsCompra = obtenerItems(); //obtenemos items de la BD

                adaptador.notifyDataSetChanged(); //aviso y refresco

                lv.setClickable(true); //para poder pinchar en los elementos de la lista
                lv.setAdapter(adapter);



            }
        });

        imagenBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ComponenteAD3 componente = new ComponenteAD3(getApplicationContext());
                componente.openForWrite();
                componente.openForRead();
                //visualmente
                itemColmena itemSeleccionado2= (itemColmena) adapter.getItem(pos); //pos variable global que setteamos on click listview
                AlertDialog.Builder alert = new AlertDialog.Builder(MenuAdminApicultor.this);//check borrar
                alert.setTitle("Advertencia");
                alert.setCancelable(true);
                alert.setIcon(R.drawable.eliminar1_foreground);
                String nombreAborrar=(itemSeleccionado2.nombre);
                alert.setMessage("Está seguro que desea borrar el apiario de nombre "+nombreAborrar+" seleccionado");

                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });
                alert.setPositiveButton("Si, estoy seguro", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        itemColmena itemSeleccionado2= (itemColmena) adapter.getItem(pos);//antes de borrar preguntamos
                        itemsCompra.remove(itemSeleccionado2);
                        adaptador.notifyDataSetChanged(); //aviso y refresco
                        lv.setVisibility(View.INVISIBLE);
                        lv.setVisibility(View.VISIBLE);
                    }
                });
                alert.create().show();



                //bd
                ArrayList<Apiario>listaApiariosBD=new ArrayList();
                listaApiariosBD=componente.leerApiarios();
                nombreAborrar=(itemSeleccionado2.nombre);
                componente.borrarApiario(nombreAborrar);

            }
        });

        imagenAñadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        lv.setVisibility(View.GONE);
        editNombreApiario.setVisibility(View.VISIBLE);
        editNombreApicultor.setVisibility(View.INVISIBLE);
                if (editNombreApiario.getText().toString().trim().length() == 0 )
                {
                    Toast.makeText(getApplicationContext(), "Escribe el nombre del apiario a crear", Toast.LENGTH_LONG).show();
                    limpiarDatos();
                }

               else {
                    ComponenteAD3 componente = new ComponenteAD3(getApplicationContext());
                    componente.openForWrite();
                    componente.openForRead();
                    boolean existeYa = false;
                    ArrayList<Apiario> listaApiarios = componente.leerApiarios();
                    Apiario apiarioInsertado = new Apiario();
                    for (Apiario aux : listaApiarios) {
                        if (editNombreApiario.getText().toString().equals(aux.getNombreApiario())) {
                            existeYa = true;//booleano para saber si esta en la bd ese apiario
                            break;//queremos salir del loop si esto pasa
                        }
                    }

                    if (existeYa) {
                        Toast.makeText(getApplicationContext(), "Apiario ya existe en la base de datos", Toast.LENGTH_LONG).show();
                        limpiarDatos();
                    } else {

                        apiarioInsertado.setNombreApiario(editNombreApiario.getText().toString());
                        ComponenteAD c = new ComponenteAD(getApplicationContext());

                        c.openForWrite();
                        c.openForRead();
                        ArrayList<Usuario> listaUsuarios = c.leerUsuarios(); //vamos a buscar si esta el usuario en la bd y si coinciden los datos
                        for (Usuario aux : listaUsuarios) {
                            if (aux.getNombre().equals(editNombreApicultor.getText().toString())) {
                                apiarioInsertado.setIdUsuario(aux.getId()); //el id de ese usuario
                                break;//queremos salir del loop si esto pasa
                            }


                        }
                        componente.insertApiario(apiarioInsertado);
                        obtenerItems(); //leo de la bd
                        //aviso y refresco
                        adaptador.notifyDataSetChanged(); //aviso y refresco

                        reiniciarIntent();
                        limpiarDatos();
                        Toast.makeText(getApplicationContext(), "Apiario insertado con exito", Toast.LENGTH_SHORT).show();

                    }

                }





            }
        });

        cerrar3.setOnClickListener(new View.OnClickListener() { //on click de desconectar
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(MenuAdminApicultor.this);
                alert.setTitle("Advertencia");
                alert.setCancelable(true);
                alert.setIcon(R.drawable.exit);//icono puertita

                alert.setMessage("¿Desea desconectarse?");

                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });
                alert.setPositiveButton("Si", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent llamada=new Intent(MenuAdminApicultor.this, MainActivity.class);
                        startActivity(llamada);
                    }
                });
                alert.create().show();







            }
        });

    }



    private ArrayList<itemColmena> obtenerItems() {
        ArrayList<itemColmena> items = new ArrayList<itemColmena>();//lista con los atributos del litview
        ComponenteAD3 componente = new ComponenteAD3(getApplicationContext());
        componente.openForRead();

        ArrayList<Apiario>listaApiarios=new ArrayList<Apiario>(); //lista donde tengo todas las colmenas
        listaApiarios=componente.leerApiarios();
        for (Apiario aux : listaApiarios) {
            int id=aux.getIdApiario();
            String nombre=aux.getNombreApiario();
            items.add(new itemColmena(id,nombre,"","drawable/bee2"));

        }


        return items;
    }




    public void textoToVoz()
    {
        ttsManager=new TTSManager();
        ttsManager.init(this);
    }



    public void xmlToJava()
    {
        editText =findViewById(R.id.input_text);
        editNombreApicultor =findViewById(R.id.nombreApicultorXML4);
        editNombreApiario =findViewById(R.id.nombreApiarioXML4);
        hablarAhoraBoton=findViewById(R.id.speak_now);
        lv = (ListView)findViewById(R.id.listView2);
        imagenAñadir = (ImageView) findViewById(R.id.imagenAñadirXML4);
        imagenListar = (ImageView) findViewById(R.id.imagenlistarXML4);
        imagenBorrar = (ImageView) findViewById(R.id.imagenBorrarXML4);
        cerrar3=(Button)findViewById(R.id.cerrarUsuario3);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ttsManager.shutDown();
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu2, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) { //obtiene el click de cada menu

        int id=item.getItemId();
        switch(id) {

            case R.id.item1: //al pinchar en el menu va aqui
                Intent llamada=new Intent(MenuAdminApicultor.this, MenuAdmin.class);
                startActivity(llamada);
                return true;


        }
        return super.onOptionsItemSelected(item);
    }
    public void iniciarToolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        if (null != toolbar) {
            setSupportActionBar(toolbar);
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
    }
    public void limpiarDatos()
    {
        editNombreApiario.setText("");
        editNombreApicultor.setText("");
    }

    public void reiniciarIntent()
    {
        finish();
        overridePendingTransition(0, 0);  //reiniciamos el intent sin animacion
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

}
