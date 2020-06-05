package es.jordan.sistemaalarma;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import adaptadores.DatosRecicler;

public class MenuUsuarioPelon extends AppCompatActivity {
ImageView imagenVer,imagenActualizar,imagenListar;
RecyclerView miRecycler;
ArrayList<DatosRecicler> miLista;
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
        imagenListar=findViewById(R.id.imagenlistarXML);
        imagenVer=findViewById(R.id.imagenver);
    }

}

