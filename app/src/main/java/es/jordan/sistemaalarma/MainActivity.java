package es.jordan.sistemaalarma;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;


import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {
    private final String EXTRA_USUARIO = "";
    ImageView botonIniciar, botonRegistrar;
    EditText email1, contraseña1;
    TTSManager ttsManager = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        xmlToJava();
        textoToVoz();
        activarAnimacion();
       //Toast.makeText(getApplicationContext(), "Probando versiones", Toast.LENGTH_SHORT).show();

        botonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Registrarse.class);
                startActivityForResult(intent, 0);


            }
        });


        botonIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ComponenteAD componente = new ComponenteAD(getApplicationContext());
              //  componente.openForWrite();
               // componente.openForRead();
                boolean detector0=false;
                boolean detector1 = false;
                boolean detector2 = false;
                boolean detector3 = false; //nos ayudaran a saber si el usuario ya existe

                if (email1.getText().toString().trim().length() == 0 || contraseña1.getText().toString().trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
                    detector0=true;
                }
                Usuario usuario1 = new Usuario();
                // usuario1.setNombre(nombre1.getText().toString());
                usuario1.setEmail(email1.getText().toString());
                usuario1.setContraseña(contraseña1.getText().toString());
                //una vez vemos que los datos se han insertado, necesitamos saber si coinciden con la BD de ser asi
                //ira a la pantall correspondiente.

                ArrayList<Usuario> listaUsuarios = new ArrayList();
                listaUsuarios=obtenerLista();


                    for (Usuario u : listaUsuarios) {
                        if (u.getEmail().equals(usuario1.getEmail()) && (u.getContraseña().equals(usuario1.getContraseña()) && (u.getRol().toUpperCase().equals("ADMIN")))) {
                            detector1=true;

                            Toast.makeText(getApplicationContext(), "Credenciales validos, eres admin", Toast.LENGTH_LONG).show();
                            pantallaAdmin(u);
                            finish(); // es importante matar el main o de lo contrario el usuario podria volver atras
                            limpiar();


                        }
                           else if (u.getEmail().equals(usuario1.getEmail()) && (u.getContraseña().equals(usuario1.getContraseña()) && (u.getRol().toUpperCase().equals("USUARIO"))))

                           {
                               detector2=true;
                               Toast.makeText(getApplicationContext(), "Credenciales validos, eres usuario", Toast.LENGTH_LONG).show();
                               pantallaUsuario(u); //vamos a la pantalla usuario pasandole ese usu
                               finish(); // es importante matar el main o de lo contrario el usuario podria volver atras
                               limpiar(); //limpiamos datos del login



                        }
                        else if (u.getEmail().equals(usuario1.getEmail()) && (u.getContraseña().equals(usuario1.getContraseña()) && (u.getRol().toUpperCase().equals("INVITADO"))))

                        {
                            detector3=true;
                            Toast.makeText(getApplicationContext(), "Credenciales validos, eres invitado", Toast.LENGTH_LONG).show();
                            pantallaInvitado(u);
                            finish(); // es importante matar el main o de lo contrario el usuario podria volver atras
                            limpiar();


                        }



                    }
                   if(detector0==false && detector1==false && detector2==false && detector3==false)
                {
                    Toast.makeText(getApplicationContext(), "Credenciales o usuario invalido", Toast.LENGTH_LONG).show();

                }









            }
        });


    }

    public void limpiar() {
       // nombre1.setText("");
        email1.setText("");
        contraseña1.setText("");
    }


    public void pantallaAdmin(Usuario usuario1) {
//        usuario1.setNombre(nombre1.getText().toString());
        Intent intent2 = new Intent(MainActivity.this, MenuAdmin.class); //ponemos en la actividad el objeto usuario
        intent2.putExtra(EXTRA_USUARIO, usuario1);
        ttsManager.initQueue("Bienvenido "+usuario1.getNombre().toString());
        finish();
        startActivity(intent2);
    }


    public void pantallaUsuario(Usuario usuario1) {
        Intent intent = new Intent(MainActivity.this, MenuVip.class); //ponemos en la actividad el objeto usuario
        intent.putExtra(EXTRA_USUARIO, usuario1);
        ttsManager.initQueue("Bienvenido "+usuario1.getNombre().toString());
        finish();
        startActivity(intent);
    }

    public void pantallaInvitado(Usuario usuario1) {
        Intent intent = new Intent(MainActivity.this, MenuInvitado.class); //ponemos en la actividad el objeto usuario
        intent.putExtra(EXTRA_USUARIO, usuario1);
        ttsManager.initQueue("Bienvenido "+usuario1.getNombre().toString());
        finish();
        startActivity(intent);
    }

    public void xmlToJava() {

        botonIniciar = findViewById(R.id.botonIniciarXML);
        botonRegistrar = findViewById(R.id.botonAceptarXML);
//        nombre1 = (EditText) findViewById(R.id.nombreXML);
        contraseña1 = findViewById(R.id.contraseñaXML);
        email1 = findViewById(R.id.emailXML);


    }


    public void activarAnimacion()
    {
        ImageView miImagen = findViewById(R.id.imagenXML);
        Animation miAnim = AnimationUtils.loadAnimation(this, R.anim.anim2);
        miImagen.startAnimation(miAnim);
    }

    public void leerUsuario(Usuario usuario1)
    {
        try {
            String equipoServidor = "192.168.1.42";
            int puertoServidor = 30501;
            Socket socketCliente = new Socket(equipoServidor, puertoServidor);
            gestionarComunicacion(socketCliente, usuario1);
            socketCliente.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public ArrayList<Usuario> obtenerLista() {
        ArrayList<Usuario> listaUsuarios = new ArrayList();
        try {

            String equipoServidor = "servidorwebjordan.ddns.net";
            int puertoServidor = 30504;
            Socket socketCliente = new Socket(equipoServidor, puertoServidor);

            ObjectInputStream listaRecibida = new ObjectInputStream(socketCliente.getInputStream());//me preparo para recibir
            listaUsuarios= (ArrayList) listaRecibida.readObject(); //objeto leido y metido en usuario1 /lo recibod
            socketCliente.close();
            return listaUsuarios;

        } catch (IOException ex) {
            System.out.println(ex.getMessage());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return listaUsuarios;

    }


    public void gestionarComunicacion(Socket socketCliente, Usuario usuario1) {

        try {

            ObjectOutputStream objetoEntregar = new ObjectOutputStream(socketCliente.getOutputStream());
            System.out.println("El objeto que mandara el cliente al servidor es: " + usuario1);
            objetoEntregar.writeObject(usuario1);//el cliente manda el objeto al server
            objetoEntregar.close();

            InputStream inputStream = socketCliente.getInputStream();
            DataInputStream leerMensaje = new DataInputStream(inputStream);
            if(leerMensaje.toString().equals("existe"))
            {

                Toast toast = Toast.makeText(getApplicationContext(),"usuario existe pasa a siguiente menu", Toast.LENGTH_LONG);
                toast.show();
            }
            else
            {
                Toast toast = Toast.makeText(getApplicationContext(),"usuario no existe", Toast.LENGTH_LONG);
            }


        } catch (IOException ex) {
            System.out.println(ex.getMessage());

        }


    }
    public void textoToVoz() {
        ttsManager = new TTSManager();
        ttsManager.init(this);
    }




}