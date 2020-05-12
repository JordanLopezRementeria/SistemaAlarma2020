package es.jordan.apiariojordan2402;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuVip extends AppCompatActivity {
Button BotonVerCamara;
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
    }



    private void xmlToJava() {

        BotonVerCamara=(Button)findViewById(R.id.botonVerCamara);
    }

}
