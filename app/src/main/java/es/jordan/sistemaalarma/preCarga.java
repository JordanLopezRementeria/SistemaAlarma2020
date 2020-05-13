package es.jordan.sistemaalarma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.VideoView;

public class preCarga extends AppCompatActivity {
    private VideoView video;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_carga);


        activarAnimacion();
        xmlTojava();
        reproducirVideo();



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(preCarga.this,MainActivity.class);
                startActivity(intent); //ejecutamos el intent que hemos creado despues de 4s
            }
        },1000);

    }

    private void reproducirVideo() {
        String path = "android.resource://" + getPackageName() + "/" + R.raw.corte_video; //buscamos la ruta del video dentro de nuestro package
        Uri uri=Uri.parse(path);
        video.setVideoURI(uri);
        video.start();
    }

    private void xmlTojava() {
        video=(VideoView) findViewById(R.id.videoView);

    }

    public void activarAnimacion()
    {
        ImageView miImagen = (ImageView) findViewById(R.id.imagenXML);
        Animation miAnim = AnimationUtils.loadAnimation(this, R.anim.anim2);
        miImagen.startAnimation(miAnim);
    }
}
