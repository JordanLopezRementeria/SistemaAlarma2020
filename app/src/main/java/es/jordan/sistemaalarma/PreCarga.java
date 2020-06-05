package es.jordan.sistemaalarma;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * The type Pre carga.
 */
public class PreCarga extends AppCompatActivity {
    private VideoView video;
    private TextView texto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_carga);
        xmlToJava();
        // activarAnimacion();
        //esta es la portada del aplicativo

        /**
         * similar a un hilo normal se ejecuta en segundo plano
         */
        new Handler().postDelayed(new Runnable() { //actua similar a un hilo
            @Override
            public void run() { //
                Intent intent = new Intent(PreCarga.this, MainActivity.class);
                finish();
                startActivity(intent); //ejecutamos el intent que hemos creado despues de 4s
            }
        }, 4000);

    }

    /**
     * Xml to java.
     */
    public void xmlToJava() {
        texto = findViewById(R.id.textoprecarga);
    }

}
