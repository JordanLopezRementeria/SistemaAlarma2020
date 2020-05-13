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
    private static final String COL_IDUSUARIO = "IDCOLMENAR";
    private static final int NUM_COL_IDUSUARIO = 1;
    private static final String COL_NOMBREAPIARIO = "NOMBRECOLMENA";
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

    public long insertApiario(Colmena apiario) {
        ContentValues content = new ContentValues();
        content.put(COL_IDAPIARIO, apiario.getIdColmenar());
        content.put(COL_IDUSUARIO, apiario.getNombreColmena());
        content.put(COL_NOMBREAPIARIO, apiario.getIncidencia());

        return bdd.insert(TABLA_apiarios, null, content);
    }

    public int modificarColmena(Colmena apiario) {  //este es para modificarse a si mismo
        ContentValues content = new ContentValues();
        content.put(COL_IDCOLMENAR, apiario.getIdColmenar());
        content.put(COL_NOMBRECOLMENA, apiario.getNombreColmena());
        content.put(COL_INCIDENCIA, apiario.getIncidencia());
        return bdd.update(TABLA_apiarios, content, COL_NOMBRECOLMENA + "=" + "'"+apiario.getNombreColmena()+"'", null);
    }
    public int modificarColmena2(String nombreOriginal,Colmena apiario) { //este es para modificar a otro por el nombre antiguo
        ContentValues content = new ContentValues();
        content.put(COL_IDCOLMENAR, apiario.getIdColmenar());
        content.put(COL_NOMBRECOLMENA, apiario.getNombreColmena());
        content.put(COL_INCIDENCIA, apiario.getIncidencia());
        return bdd.update(TABLA_apiarios, content, COL_NOMBRECOLMENA + "=" + "'"+nombreOriginal+"'", null);
    }

    public int borrarColmena(String nombre) {

        return bdd.delete(TABLA_apiarios, COL_NOMBRECOLMENA + "=" + "'"+nombre+"'", null);
    }

    public Colmena leerColmena(String nombre) {
        Cursor c = bdd.query(TABLA_apiarios, new String[]{COL_IDCOLMENA, COL_IDCOLMENAR, COL_NOMBRECOLMENA, COL_INCIDENCIA}, COL_NOMBRECOLMENA + " LIKE \"" + nombre
                + "\"", null, null, null, null);
        // String sql = "SELECT * FROM " + TABLA_apiarios
        //   + " WHERE " + COL_NOMBRE + " = " + "'"+nombre+"'";
        //Cursor c = bdd.rawQuery(sql, null);



        return cursorToCharter(c);

    }

    public Colmena cursorToCharter(Cursor c) {
        if(c.getCount()==0){
            c.close();
            return null;
        }
        Colmena apiario = new Colmena();
        apiario.setIdColmena(c.getInt(NUM_COL_IDCOLMENA));
        apiario.setIdColmenar(c.getInt(NUM_COL_IDCOLMENAR));
        apiario.setNombreColmena(c.getString(NUM_COL_NOMBRECOLMENA));
        apiario.setIncidencia(c.getString(NUM_COL_INCIDENCIA));


        return apiario;
    }

    public ArrayList<Colmena> leerColmenas() {
        ArrayList<Colmena> apiarioList = new ArrayList<>();

        Cursor c = bdd.query(TABLA_apiarios, new String[]{COL_IDCOLMENA, COL_IDCOLMENAR, COL_NOMBRECOLMENA, COL_INCIDENCIA}, null, null, null, null, null,null);
        if (c.getCount() == 0) {

            return apiarioList;
        }

        while (c.moveToNext()) {
            Colmena apiario = new Colmena();
            apiario.setIdColmena(c.getInt(NUM_COL_IDCOLMENA));
            apiario.setIdColmenar(c.getInt(NUM_COL_IDCOLMENAR));
            apiario.setNombreColmena(c.getString(NUM_COL_NOMBRECOLMENA));
            apiario.setIncidencia(c.getString(NUM_COL_INCIDENCIA));

            apiarioList.add(apiario);
        }
        //si cierro aqui el cursor la cuenta es 0

        return apiarioList;
    }

    public void borrarTodos() {
        apiarios.onUpgrade(bdd, VERSION, VERSION + 1);
    }
}


