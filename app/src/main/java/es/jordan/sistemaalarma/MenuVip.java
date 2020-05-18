package es.jordan.sistemaalarma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuVip extends AppCompatActivity {
Button BotonVerCamara,BotonVerFotos,BotonSos,botonListarIncidencias;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_vip);
        xmlToJava();
        BotonVerCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(v.getContext(), Streaming.class);
                startActivityForResult(intent, 0);
            }
        });

        BotonVerFotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //esto abre el email que tengamos configurado por defecto en android que sera el que utilicemos
                //para recibir las fotos
                Intent intent = getPackageManager().getLaunchIntentForPackage("com.android.email");
                startActivity(intent);
            }
        });

        BotonSos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //la diferencia que radica entre un action_dial y un action_call es que en el call
                //esq dial puedes editar el numero y el call llama directamente
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:693246245"));
                startActivity(intent);
            }
        });

        botonListarIncidencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //la diferencia que radica entre un action_dial y un action_call es que en el call
                //esq dial puedes editar el numero y el call llama directamente
                Intent intent = new Intent(v.getContext(), ListarIncidencias.class);
                startActivityForResult(intent, 0);
            }
        });
    }









    private void xmlToJava() {

        BotonVerCamara=(Button)findViewById(R.id.botonVerCamara);
        BotonVerFotos=(Button)findViewById(R.id.botonVerFotos);
        BotonSos=(Button)findViewById(R.id.botonSos);
        botonListarIncidencias=(Button)findViewById(R.id.botonListarIncidencias);
    }

}
