package es.jordan.apiariojordan2402;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuVip extends AppCompatActivity {
Button BotonVerCamara;
Button BotonVerFotos;
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

    }



    private void xmlToJava() {

        BotonVerCamara=(Button)findViewById(R.id.botonVerCamara);
        BotonVerFotos=(Button)findViewById(R.id.botonVerFotos);
    }

}
