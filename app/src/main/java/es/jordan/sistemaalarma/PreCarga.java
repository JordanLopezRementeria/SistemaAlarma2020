package es.jordan.sistemaalarma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

public class PreCarga extends AppCompatActivity {
    private VideoView video;
    TextView texto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_carga);
        xmlToJava();
      // activarAnimacion();





        new Handler().postDelayed(new Runnable() { //actua similar a un hilo
            @Override
            public void run() { //
                Intent intent=new Intent(PreCarga.this,MainActivity.class);
                startActivity(intent); //ejecutamos el intent que hemos creado despues de 4s
            }
        },4000);

    }

    private void xmlToJava() {
        texto=findViewById(R.id.textoprecarga);
    }

    private void reproducirVideo() {
        String path = "android.resource://" + getPackageName() + "/" + R.raw.corte_video; //buscamos la ruta del video dentro de nuestro package
        Uri uri=Uri.parse(path);
        video.setVideoURI(uri);
        video.start();
    }


    public void activarAnimacion()
    {
        ImageView miImagen = findViewById(R.id.imagenXML);
        Animation miAnim = AnimationUtils.loadAnimation(this, R.anim.anim2);
        miImagen.startAnimation(miAnim);
    }
}
