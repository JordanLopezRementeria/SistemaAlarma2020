package es.jordan.apiariojordan2402;

import androidx.appcompat.app.AppCompatActivity;


import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Patterns;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class MenuInvitado extends AppCompatActivity {
    private VideoView video;
    private TextView textoCorreo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_invitado);

        xmlTojava();
        reproducirVideo();
        textoSubrayado();



        textoCorreo.setOnClickListener(new View.OnClickListener() { //onclick del textview

            public void onClick(View v) {
                enlaceMail();
            }

            });

    }

    private void textoSubrayado() {
        //para subrayar el texto
        SpannableString mitextoU = new SpannableString("¿Deseas saber más?");
        mitextoU.setSpan(new UnderlineSpan(), 0, mitextoU.length(), 0);
        textoCorreo.setText(mitextoU);

    }


    private void enlaceMail() {
        //con esto mandamos emails por gmail a la direccion que queramos con el asunto y cuerpo
        Intent email= new Intent(Intent.ACTION_SENDTO);
        email.setData(Uri.parse("mailto:lopez.rementeria@gmail.com"));
        email.putExtra(Intent.EXTRA_SUBJECT, "Estoy interesado en el sistema de alarma");
        email.putExtra(Intent.EXTRA_TEXT, "Hola me gustaria adquirir mas detalles sobre el sistema de alarma.");
        startActivity(email);




        //final Intent intent = new Intent(Intent.ACTION_VIEW)
              //  .setType("plain/text")
               // .setData(Uri.parse("lopez.rementeria@gmail.com"))
                //.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail")
               // .putExtra(Intent.EXTRA_EMAIL, new String[]{"lopez.rementeria@gmail.com"})
                //.putExtra(Intent.EXTRA_SUBJECT, "Estoy interesado en el sistema de alarma")
                //.putExtra(Intent.EXTRA_TEXT, "Hola me gustaria adquirir mas detalles sobre el sistema de alarma.");
       // startActivity(intent);
    }

    private void reproducirVideo() {
        String path = "android.resource://" + getPackageName() + "/" + R.raw.corte_video; //buscamos la ruta del video dentro de nuestro package
        Uri uri=Uri.parse(path);
        video.setVideoURI(uri);
        MediaController mediacontroler=new MediaController(this); //encargado de play y stop
        video.setMediaController(mediacontroler);
        mediacontroler.setAnchorView(video); //quiero que ocupe el mismo tamaño que el video
        video.start();
    }

    private void xmlTojava() {
        video=(VideoView) findViewById(R.id.videoView3);
        textoCorreo=(TextView)findViewById(R.id.textoCorreo);


    }
}


