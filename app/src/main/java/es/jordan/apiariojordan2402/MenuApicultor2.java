package es.jordan.apiariojordan2402;

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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MenuApicultor2 extends AppCompatActivity {

    private Button hablarAhoraBoton;
    ImageView imagenAñadir,imagenListar, imagenVer,imagenBorrar;
    private EditText editText;
    private ListView lv;
    TTSManager ttsManager = null;
    Button cerrar5;
    int posActual;
    EditText nombreColmena;
    EditText nombreIncidencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_apicultor2);
        xmlToJava();
        textoToVoz();
        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);



        final ArrayList<itemColmena> itemsCompra = obtenerItems();

        final ItemColmenaAdapter adapter = new ItemColmenaAdapter(this, itemsCompra);
        lv.setClickable(true); //para poder pinchar en los elementos de la lista
        lv.setAdapter(adapter);

        ItemColmenaAdapter adaptador = new ItemColmenaAdapter();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                editText.setVisibility(View.INVISIBLE);
                posActual = position;                                        //item.getNombre() tb sirve
                itemColmena itemSeleccionado = (itemColmena) adapter.getItem(position);
                //Toast.makeText(getApplicationContext(), item.nombre+item.tipo, Toast.LENGTH_LONG).show();
                editText.setText(itemSeleccionado.tipo);
                String text = editText.getText().toString();
                ttsManager.initQueue(text);//quiero que se oiga ya cuando pulse


            }
        });


        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() { //pulsar largo
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int position, long arg3) {
                ComponenteAD2 componente = new ComponenteAD2(getApplicationContext());
                componente.openForWrite();
                componente.openForRead();
                //visualmente
                itemColmena itemSeleccionado2= (itemColmena) adapter.getItem(position);
                AlertDialog.Builder alert = new AlertDialog.Builder(MenuApicultor2.this);
                alert.setTitle("Advertencia");
                alert.setCancelable(true);
                alert.setIcon(R.drawable.eliminar1_foreground);
                String nombreAborrar=(itemSeleccionado2.nombre);
                alert.setMessage("Está seguro que desea borrar la colmena "+nombreAborrar+" seleccionada");

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
                        adapter.notifyDataSetChanged(); //aviso y refresco
                        lv.setVisibility(View.INVISIBLE);
                        lv.setVisibility(View.VISIBLE);
                    }
                });
                alert.create().show();



                //bd
                ArrayList<Colmena>listaColmenasBD=new ArrayList();
                listaColmenasBD=componente.leerColmenas();
                nombreAborrar=(itemSeleccionado2.nombre);
                componente.borrarColmena(nombreAborrar);


                return true;
            }
        });

        imagenListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombreColmena.setVisibility(View.INVISIBLE);
                nombreIncidencia.setVisibility(View.INVISIBLE);

                if (lv.getVisibility() == View.VISIBLE) //es como un togglebutton
                {
                    lv.setVisibility(View.INVISIBLE);
                    hablarAhoraBoton.setVisibility(View.INVISIBLE);
                } else {
                    lv.setVisibility(View.VISIBLE);
                    hablarAhoraBoton.setVisibility(View.INVISIBLE);
                }

                ArrayList<itemColmena>colmenas=obtenerItems(); //leemos de la bd e introducimos en la bd

                adapter.notifyDataSetChanged(); //aviso y refresco

                lv.setClickable(true); //para poder pinchar en los elementos de la lista
                lv.setAdapter(adapter);




            }
        });

        imagenVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


        hablarAhoraBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = editText.getText().toString();
                ttsManager.initQueue(text);
            }
        });


        cerrar5.setOnClickListener(new View.OnClickListener() { //on click de desconectar
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(MenuApicultor2.this);
                alert.setTitle("Advertencia");
                alert.setCancelable(true);
                alert.setIcon(R.drawable.exit);

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

                        Intent llamada=new Intent(MenuApicultor2.this, MainActivity.class);
                        startActivity(llamada);
                    }
                });
                alert.create().show();







            }
        });

        imagenAñadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lv.setVisibility(View.GONE);
                nombreColmena.setVisibility(View.VISIBLE);
                nombreIncidencia.setVisibility(View.VISIBLE);




                if (nombreColmena.getText().toString().trim().length() == 0){
                    Toast.makeText(getApplicationContext(), "Escribe el nombre de la colmena", Toast.LENGTH_LONG).show();


                }
                else {
                    ComponenteAD2 componente = new ComponenteAD2(getApplicationContext());
                    componente.openForWrite();
                    componente.openForRead();
                    ArrayList<Colmena> listaColmenas = componente.leerColmenas();
                    boolean existeYa = false;//determinara si esta ya en la BD o no
                    for (Colmena aux : listaColmenas) {
                        if (nombreColmena.getText().toString().equals(aux.getNombreColmena())) {
                            existeYa = true;
                            break;//queremos salir del loop si esto pasa
                        }

                    }


                    if (existeYa) {
                        Toast.makeText(getApplicationContext(), "La colmena existe ya en la BD", Toast.LENGTH_LONG).show();

                    } else {
                        Colmena colmena1 = new Colmena();

                        if (nombreIncidencia.getText().toString().trim().length() == 0) {
                            colmena1.setNombreColmena(nombreColmena.getText().toString());
                            colmena1.setIncidencia("Sin incidencias");//si no escribe nada el usuario en este campo es que no hay incidencias
                            limpiarDatos();
                        } else {
                            colmena1.setNombreColmena(nombreColmena.getText().toString());
                            colmena1.setIncidencia(nombreIncidencia.getText().toString());
                            limpiarDatos();
                        }
                        componente.insertcolmena(colmena1);
                        reiniciarIntent();
                        Toast.makeText(getApplicationContext(), "Colmena insertada con exito", Toast.LENGTH_LONG).show();

                    }


                }
            }
        });
        imagenBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombreColmena.setVisibility(View.INVISIBLE);
                nombreIncidencia.setVisibility(View.INVISIBLE);
                lv.setVisibility(View.VISIBLE);
                ComponenteAD2 componente = new ComponenteAD2(getApplicationContext());
                componente.openForWrite();
                componente.openForRead();
                //visualmente
                itemColmena itemSeleccionado2= (itemColmena) adapter.getItem(posActual);
                AlertDialog.Builder alert = new AlertDialog.Builder(MenuApicultor2.this);
                alert.setTitle("Advertencia");
                alert.setCancelable(true);
                alert.setIcon(R.drawable.eliminar1_foreground);
                String nombreAborrar=(itemSeleccionado2.nombre);
                alert.setMessage("Está seguro que desea borrar la colmena "+nombreAborrar+" seleccionada");

                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });
                alert.setPositiveButton("Si, estoy seguro", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        itemColmena itemSeleccionado2= (itemColmena) adapter.getItem(posActual);//antes de borrar preguntamos
                        itemsCompra.remove(itemSeleccionado2);
                        adapter.notifyDataSetChanged(); //aviso y refresco
                        lv.setVisibility(View.INVISIBLE);
                        lv.setVisibility(View.VISIBLE);
                    }
                });
                alert.create().show();



                //bd
                ArrayList<Colmena>listaColmenasBD=new ArrayList();
                listaColmenasBD=componente.leerColmenas();

              if(posActual==0) //comprobacion importante o peta la app
              {
                  Toast.makeText(getApplicationContext(), "No hay colmenas que borrar", Toast.LENGTH_SHORT).show();

              }
              else {
                  nombreAborrar = (itemSeleccionado2.nombre);
                  componente.borrarColmena(nombreAborrar);
              }


            }
        });


    }



    private ArrayList<itemColmena> obtenerItems() {
        ArrayList<itemColmena> items = new ArrayList<itemColmena>();//lista con los atributos del litview
        ComponenteAD2 componente = new ComponenteAD2(getApplicationContext());
        componente.openForRead();
        componente.openForWrite();
        Colmena c1 = new Colmena();
        //c1.setNombreColmena("Colmena basica"); //colmena de prueba insertada por defecto
        //c1.setIncidencia("Sin incidencia");
        //c1.setIdColmenar(2);
       // componente.insertcolmena(c1);
        ArrayList<Colmena> listaColmenas = new ArrayList<Colmena>(); //lista donde tengo todas las colmenas
        listaColmenas = componente.leerColmenas();
        for (Colmena aux : listaColmenas) {
            int id = aux.getIdColmena();
            String nombre = aux.getNombreColmena();
            String incidencia = aux.getIncidencia();

            items.add(new itemColmena(id, nombre, incidencia, "drawable/colmena"));

        }
        Colmena colmena1 = new Colmena();


        items.add(new itemColmena(1, "Colmena Jordan", "Se ha roto", "drawable/colmena"));
        //items.add(new itemColmena(2, "Colmena Jose", "Esta partida", "drawable/colmena"));
        //items.add(new itemColmena(3, "Colmena Jorge", "Es nueva", "drawable/colmena"));

        return items;
    }


    public void textoToVoz() {
        ttsManager = new TTSManager();
        ttsManager.init(this);
    }


    public void xmlToJava() {
        imagenAñadir=findViewById(R.id.imagenAñadirXML3);
        imagenBorrar=findViewById(R.id.imagenBorrarXML3);
        editText = findViewById(R.id.input_text);
        nombreColmena =(EditText) findViewById(R.id.nombreColmenaXML);
        nombreIncidencia =(EditText) findViewById(R.id.nombreIncidenciaXML);
        hablarAhoraBoton = findViewById(R.id.speak_now);
        lv = (ListView) findViewById(R.id.listView);
        imagenListar = (ImageView) findViewById(R.id.imagenlistarXML3);
        imagenVer = (ImageView) findViewById(R.id.imagenverXML3);
        cerrar5=(Button)findViewById(R.id.cerrarUsuario5);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ttsManager.shutDown();
    }

    public boolean onOptionsItemSelected(MenuItem item) { //obtiene el click de cada menu

        int id = item.getItemId();
        switch (id) {

            case R.id.item1: //al pinchar en el menu va aqui
                Intent llamada = new Intent(MenuApicultor2.this, MenuApicultor.class);
                startActivity(llamada);
                return true;


        }
        return super.onOptionsItemSelected(item);
    }

    public void iniciarToolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        toolbar.setTitle("");
        if (null != toolbar) {
            setSupportActionBar(toolbar);
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu4, menu);
        return true;
    }
    public void limpiarDatos()
    {
        nombreIncidencia.setText("");
        nombreColmena.setText("");
    }
    public void reiniciarIntent()
    {
        finish();
        overridePendingTransition(0, 0);  //reiniciamos el intent sin animacion
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }
}