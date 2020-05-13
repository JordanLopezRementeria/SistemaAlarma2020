package es.jordan.sistemaalarma;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class ComponenteAD3 {
    private static final int VERSION = 1;
    private static final String NOMBRE_BDD = "jordan.db";

    private static final String TABLA_APIARIOS = "tabla_apiarios";
    private static final String COL_IDAPIARIO = "IDAPIARIO";
    private static final int NUM_COL_IDAPIARIO = 0;
    private static final String COL_IDUSUARIO = "IDUSUARIO";
    private static final int NUM_COL_IDUSUARIO = 1;
    private static final String COL_NOMBREAPIARIO = "NOMBREAPIARIO";
    private static final int NUM_COL_NOMBREAPIARIO = 2;
    


    private SQLiteDatabase bdd;
    private UsuarioBaseSQLite apiarios;

    public ComponenteAD3(Context context) {
        apiarios = new UsuarioBaseSQLite(context, NOMBRE_BDD, null, VERSION);
    }

    public void openForWrite() {
        bdd = apiarios.getWritableDatabase();
    }

    public void openForRead() {
        bdd = apiarios.getReadableDatabase();
    }

    public void close() {
        bdd.close();
    }

    public SQLiteDatabase getBdd() {
        return bdd;
    }

    public long insertApiario(Apiario apiario) {
        ContentValues content = new ContentValues();

        content.put(COL_IDUSUARIO, apiario.getIdUsuario());
        content.put(COL_NOMBREAPIARIO, apiario.getNombreApiario());

        return bdd.insert(TABLA_APIARIOS, null, content);
    }

    public int modificarApiario(Apiario apiario) {  //este es para modificarse a si mismo
        ContentValues content = new ContentValues();
        content.put(COL_IDUSUARIO, apiario.getNombreApiario());
        content.put(COL_NOMBREAPIARIO, apiario.getNombreApiario());
        
        return bdd.update(TABLA_APIARIOS, content, COL_NOMBREAPIARIO + "=" + "'"+apiario.getNombreApiario()+"'", null);
    }
    public int modificarApiario2(String nombreOriginal,Apiario apiario) { //este es para modificar a otro por el nombre antiguo
        ContentValues content = new ContentValues();
    
        content.put(COL_IDUSUARIO, apiario.getNombreApiario());
        content.put(COL_NOMBREAPIARIO, apiario.getNombreApiario());
        return bdd.update(TABLA_APIARIOS, content, COL_NOMBREAPIARIO + "=" + "'"+nombreOriginal+"'", null);
    }

    public int borrarApiario(String nombre) {

        return bdd.delete(TABLA_APIARIOS, COL_NOMBREAPIARIO + "=" + "'"+nombre+"'", null);
    }

    public Apiario leerApiario(String nombre) {
        Cursor c = bdd.query(TABLA_APIARIOS, new String[]{COL_IDAPIARIO, COL_IDUSUARIO,COL_NOMBREAPIARIO}, COL_NOMBREAPIARIO + " LIKE \"" + nombre
                + "\"", null, null, null, null);
        // String sql = "SELECT * FROM " + TABLA_apiarios
        //   + " WHERE " + COL_NOMBRE + " = " + "'"+nombre+"'";
        //Cursor c = bdd.rawQuery(sql, null);



        return cursorToCharter(c);

    }

    public Apiario cursorToCharter(Cursor c) {
        if(c.getCount()==0){
            c.close();
            return null;
        }
        Apiario apiario = new Apiario();
        apiario.setIdApiario(c.getInt(NUM_COL_IDAPIARIO));
        apiario.setIdUsuario(c.getInt(NUM_COL_IDUSUARIO));
        apiario.setNombreApiario(c.getString(NUM_COL_NOMBREAPIARIO));
      


        return apiario;
    }

    public ArrayList<Apiario> leerApiarios() {
        ArrayList<Apiario> apiarioList = new ArrayList<>();
      Apiario a1=new Apiario();
      a1.setNombreApiario("Bilbao");
      a1.setIdUsuario(100);
      apiarioList.add(a1);
        Cursor c = bdd.query(TABLA_APIARIOS, new String[]{COL_IDAPIARIO, COL_IDUSUARIO, COL_NOMBREAPIARIO}, null, null, null, null, null,null);
        if (c.getCount() == 0) {

            return apiarioList;
        }

        while (c.moveToNext()) {
            Apiario apiario = new Apiario();
            apiario.setIdApiario(c.getInt(NUM_COL_IDAPIARIO));
            apiario.setIdUsuario(c.getInt(NUM_COL_IDUSUARIO));
            apiario.setNombreApiario(c.getString(NUM_COL_NOMBREAPIARIO));
            

            apiarioList.add(apiario);
        }
        //si cierro aqui el cursor la cuenta es 0

        return apiarioList;
    }

    public void borrarTodos() {
        apiarios.onUpgrade(bdd, VERSION, VERSION + 1);
    }
}


