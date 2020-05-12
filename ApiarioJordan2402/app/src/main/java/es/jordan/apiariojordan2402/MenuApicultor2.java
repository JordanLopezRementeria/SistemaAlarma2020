package es.jordan.apiariojordan2402;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MenuApicultor2 extends AppCompatActivity {

    private Button hablarAhoraBoton;
    private EditText editText;
    private ListView lv;
    TTSManager ttsManager=null;
    int posActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_apicultor2);

        textoToVoz();
        xmlToJava();

        final ArrayList<itemColmena> itemsCompra = obtenerItems();

        final ItemColmenaAdapter adapter = new ItemColmenaAdapter(this, itemsCompra);
         lv.setClickable(true); //para poder pinchar en los elementos de la lista
        lv.setAdapter(adapter);

           ItemColmenaAdapter adaptador=new ItemColmenaAdapter();
           lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                editText.setVisibility(View.INVISIBLE);
                int pos = position;                                        //item.getNombre() tb sirve
                itemColmena itemSeleccionado= (itemColmena) adapter.getItem(position);
                //Toast.makeText(getApplicationContext(), item.nombre+item.tipo, Toast.LENGTH_LONG).show();
                editText.setText(itemSeleccionado.tipo);
                String text=editText.getText().toString();
                ttsManager.initQueue(text);//quiero que se oiga ya cuando pulse

            }
        });








        hablarAhoraBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text=editText.getText().toString();
                ttsManager.initQueue(text);
            }
        });

    }



    private ArrayList<itemColmena> obtenerItems() {
        ArrayList<itemColmena> items = new ArrayList<itemColmena>();//lista con los atributos del litview
        ComponenteAD2 componente = new ComponenteAD2(getApplicationContext());
        componente.openForRead();
        componente.openForWrite();
        Colmena c1=new Colmena();
        c1.setNombreColmena("colmena");
        c1.setIncidencia("incidencia");
        c1.setIdColmenar(2);
        componente.insertcolmena(c1);
        ArrayList<Colmena>listaColmenas=new ArrayList<Colmena>(); //lista donde tengo todas las colmenas
        listaColmenas=componente.leerColmenas();
        for (Colmena aux : listaColmenas) {
             int id=aux.getIdColmena();
             String nombre=aux.getNombreColmena();
             String incidencia=aux.getIncidencia();

            items.add(new itemColmena(id,nombre,incidencia,"drawable/colmena"));

        }
        Colmena colmena1=new Colmena();


        items.add(new itemColmena(1, "Colmena Juan", "Se ha roto", "drawable/colmena"));
        items.add(new itemColmena(2,"Colmena Jose", "Esta partida", "drawable/colmena"));
        items.add(new itemColmena(3, "Colmena Jorge", "Es nueva", "drawable/colmena"));

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
        hablarAhoraBoton=findViewById(R.id.speak_now);
        lv = (ListView)findViewById(R.id.listView);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ttsManager.shutDown();
    }
}
