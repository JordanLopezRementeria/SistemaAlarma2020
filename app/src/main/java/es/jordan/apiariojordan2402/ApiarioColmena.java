package es.jordan.apiariojordan2402;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ApiarioColmena extends AppCompatActivity {
ListView lv;
 Button b1;
    TTSManager ttsManager = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apiario_colmena);
        xmlToJava();
        textoToVoz();
        final ArrayList<itemColmena> itemsCompra = obtenerItems();

        final ItemColmenaAdapter adapter = new ItemColmenaAdapter(this, itemsCompra);
        lv.setClickable(true); //para poder pinchar en los elementos de la lista
        lv.setAdapter(adapter);

        ItemColmenaAdapter adaptador = new ItemColmenaAdapter();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                itemColmena itemSeleccionado = (itemColmena) adapter.getItem(position);
              String incidencia=itemSeleccionado.tipo;
                ttsManager.initQueue(incidencia);

            }
        });
      b1.setOnClickListener(new View.OnClickListener() { //on click de desconectar
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(ApiarioColmena.this);
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

                        Intent llamada=new Intent(ApiarioColmena.this, MainActivity.class);
                        startActivity(llamada);
                    }
                });
                alert.create().show();







            }
        });
    }
    public void xmlToJava()
    {
        b1=(Button)findViewById(R.id.cerrarUsuario6);
        lv=(ListView)findViewById(R.id.listView3);
    }



    private ArrayList<itemColmena> obtenerItems() {
        ArrayList<itemColmena> items = new ArrayList<itemColmena>();
        items.add(new itemColmena(1, "Colmena 1", "Esta desgastada", "drawable/colmena"));
        items.add(new itemColmena(2, "Colmena 2", "Esta partida", "drawable/colmena"));
        items.add(new itemColmena(3, "Colmena 3", "Sin incidencias", "drawable/colmena"));
        items.add(new itemColmena(4, "Colmena 4", "Se nesita otra reina", "drawable/colmena"));
        items.add(new itemColmena(5, "Colmena 5", "Mas zánganos", "drawable/colmena"));
        return items;
    }

    public void textoToVoz() {
        ttsManager = new TTSManager();
        ttsManager.init(this);
    }
}
