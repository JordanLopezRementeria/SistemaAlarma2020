package es.jordan.sistemaalarma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Streaming extends AppCompatActivity {
private WebView webView;
private final String EXTRA_USUARIO = "";

private Toolbar toolbar;
Spinner spinner;
ImageView ver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_streaming);
        final Usuario usuarioPasado = (Usuario) getIntent().getSerializableExtra(EXTRA_USUARIO);
        //Toast toast = Toast.makeText(getApplicationContext(),usuarioPasado.toString(), Toast.LENGTH_LONG);
        //toast.show();

        xmlToJava();
        toolbar = findViewById(R.id.toolbar5);
        toolbar.setTitle("Usuario VIP - "+usuarioPasado.getNombre());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);





        configuracionWebView();
        comprobarPermisos();

        //sacamos la lista de raspberrys del usuario conectado y las mostramos en el spinner
        ArrayList<Raspberry> listaRaspberrysPropias = new ArrayList();
        listaRaspberrysPropias = mandarUsuarioYrecibirListaDeSusRaspberrys(usuarioPasado); //mando el usuario del
        //que quiero recibir raspberrys al servidor y este me contesta devolviendome la lista
        ArrayList<String> opciones4 = new ArrayList(); //en un array de string meto el modelo y la direccion
        //de cada raspB que he recibido
        for (Raspberry r : listaRaspberrysPropias) {
            opciones4.add(r.getRaspberryId()+":"+r.getModelo() + ":" + r.getDireccion());
        }

        spinner = findViewById(R.id.spinnerverstreaming);
        spinner.setPrompt("Elige webcam a visualizar");
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, opciones4);
        spinner.setAdapter(adapter2);


        //necesito obtener el id de usuario y el id de raspberry para en funcion de eso cargar una direccion




       // webView.loadUrl("http://alarmacaserajordan.ddns.net:8081/");











        ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spinner.getCount()==0) {
                    // Toast.makeText(getApplicationContext(), eleccion, Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "Sin raspberrys asignadas", Toast.LENGTH_SHORT).show();

                }
                else {
                    String ipCamaraElegida = "";
                    String eleccion = spinner.getSelectedItem().toString();
                    String[] datos = eleccion.split(":");

                    ipCamaraElegida = datos[2] + ":" + datos[3] + ":" + datos[4]; //en el array en la segunda posicion tenemos hasta los : de http
                    //y en el 3 lo q queda en la 4 el puerto los puntos los añado por el split


                    Toast toast = Toast.makeText(getApplicationContext(), ipCamaraElegida, Toast.LENGTH_LONG);
                    toast.show();

                    webView.loadUrl(ipCamaraElegida);
                    webView.setVisibility(View.VISIBLE);

                }
            }
        });
    }

    private void comprobarPermisos() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            int hasCameraPermission = checkSelfPermission(Manifest.permission.CAMERA);

            List<String> permissions = new ArrayList<String>();

            if (hasCameraPermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.CAMERA);

            }
            if (!permissions.isEmpty()) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), 111);
            }
        }
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onPermissionRequest(PermissionRequest request) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    request.grant(request.getResources());
                }
            }
        });
    }

    private void configuracionWebView() {
        webView.setWebViewClient(new WebViewClient());
        webView.clearCache(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowFileAccessFromFileURLs(true);
        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

    }


    public boolean onCreateOptionsMenu(Menu menu)
{
    MenuInflater inflater=getMenuInflater();
    inflater.inflate(R.menu.mimenu2,menu);
    return true;
}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //metodo que se encarga del toolbar
        //para que cada icono asignarle tareas diferentes
        switch (item.getItemId()) {
            case R.id.item1:
                Intent intent = new Intent(getApplicationContext(), MenuVip.class); //flechita que vuelve al
                //menu usuario pasando el usuario que esta logueado
                final Usuario usuarioPasado = (Usuario) getIntent().getSerializableExtra(EXTRA_USUARIO);
                intent.putExtra(EXTRA_USUARIO, usuarioPasado);
               // Toast toast = Toast.makeText(getApplicationContext(),usuarioPasado.toString(), Toast.LENGTH_LONG);
                //toast.show();

                startActivityForResult(intent, 0);
                return true;

            case R.id.item2:
              //  Toast toast2 = Toast.makeText(getApplicationContext(),"pincha 2", Toast.LENGTH_LONG);
              //  toast2.show();
                return true;

            case R.id.item3:
                AlertDialog.Builder alert = new AlertDialog.Builder(Streaming.this);
                alert.setTitle("Advertencia");
                alert.setCancelable(true);
                alert.setIcon(R.drawable.exit);

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

                        Intent llamada = new Intent(Streaming.this, MainActivity.class);
                        startActivity(llamada);
                    }
                });
                alert.create().show();

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }







    private void elegirRaspberry() {






    }

    private void xmlToJava() {
        webView = findViewById(R.id.webview);

        ver=findViewById(R.id.botonvisualizar);
        spinner=findViewById(R.id.spinnerverstreaming);
    }
    public ArrayList<Raspberry>mandarUsuarioYrecibirListaDeSusRaspberrys(Usuario usuarioPasado){
        ArrayList<Raspberry> listaRaspberrys = new ArrayList();
        try {

            //1º paso conectarse al servidor
            String equipoServidor = "servidorwebjordan.ddns.net";
            int puertoServidor = 30560;
            Socket socketCliente = new Socket(equipoServidor, puertoServidor);
            //2º paso mandar el usuario que esta conectado como objeto
            ObjectOutputStream objetoEntregar = new ObjectOutputStream(socketCliente.getOutputStream());
            System.out.println("El objeto que mandara el cliente al servidor es: " + usuarioPasado);
            objetoEntregar.writeObject(usuarioPasado);//el cliente manda el objeto al server
            objetoEntregar.flush();
            //3º paso recibir la lista de raspberrys que tiene ese usuario
            ObjectInputStream listaRecibida = new ObjectInputStream(socketCliente.getInputStream());//me preparo para recibir
            listaRaspberrys= (ArrayList) listaRecibida.readObject(); //objeto leido y metido en usuario1 /lo recibod


            listaRecibida.close();
            objetoEntregar.close();
            return listaRaspberrys;
        }  catch (IOException ex) {
            System.out.println(ex.getMessage());



        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return listaRaspberrys;
    }

    @Override
    public void onBackPressed() {

        if(webView.canGoBack())
        {
            webView.goBack();
        }
        else {
            super.onBackPressed();
        }
    }




}
