package es.jordan.sistemaalarma;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class ComponenteAD2 {
    private static final int VERSION = 1;
    private static final String NOMBRE_BDD = "jordan.db";

    private static final String TABLA_colmenas = "tabla_colmenas";
    private static final String COL_IDCOLMENA = "IDCOLMENA";
    private static final int NUM_COL_IDCOLMENA = 0;
    private static final String COL_IDCOLMENAR = "IDCOLMENAR";
    private static final int NUM_COL_IDCOLMENAR = 1;
    private static final String COL_NOMBRECOLMENA = "NOMBRECOLMENA";
    private static final int NUM_COL_NOMBRECOLMENA = 2;
    private static final String COL_INCIDENCIA = "INCIDENCIA";
    private static final int NUM_COL_INCIDENCIA = 3;


    private SQLiteDatabase bdd;
    private UsuarioBaseSQLite colmenas;

    public ComponenteAD2(Context context) {
        colmenas = new UsuarioBaseSQLite(context, NOMBRE_BDD, null, VERSION);
    }

    public void openForWrite() {
        bdd = colmenas.getWritableDatabase();
    }

    public void openForRead() {
        bdd = colmenas.getReadableDatabase();
    }

    public void close() {
        bdd.close();
    }

    public SQLiteDatabase getBdd() {
        return bdd;
    }

    public long insertcolmena(Colmena colmena) {
        ContentValues content = new ContentValues();
        content.put(COL_IDCOLMENAR, colmena.getIdColmenar());
        content.put(COL_NOMBRECOLMENA, colmena.getNombreColmena());
        content.put(COL_INCIDENCIA, colmena.getIncidencia());

        return bdd.insert(TABLA_colmenas, null, content);
    }

    public int modificarColmena(Colmena colmena) {  //este es para modificarse a si mismo
        ContentValues content = new ContentValues();
        content.put(COL_IDCOLMENAR, colmena.getIdColmenar());
        content.put(COL_NOMBRECOLMENA, colmena.getNombreColmena());
        content.put(COL_INCIDENCIA, colmena.getIncidencia());
        return bdd.update(TABLA_colmenas, content, COL_NOMBRECOLMENA + "=" + "'"+colmena.getNombreColmena()+"'", null);
    }
    public int modificarColmena2(String nombreOriginal,Colmena colmena) { //este es para modificar a otro por el nombre antiguo
        ContentValues content = new ContentValues();
        content.put(COL_IDCOLMENAR, colmena.getIdColmenar());
        content.put(COL_NOMBRECOLMENA, colmena.getNombreColmena());
        content.put(COL_INCIDENCIA, colmena.getIncidencia());
        return bdd.update(TABLA_colmenas, content, COL_NOMBRECOLMENA + "=" + "'"+nombreOriginal+"'", null);
    }

    public int borrarColmena(String nombre) {

        return bdd.delete(TABLA_colmenas, COL_NOMBRECOLMENA + "=" + "'"+nombre+"'", null);
    }

    public Colmena leerColmena(String nombre) {
        Cursor c = bdd.query(TABLA_colmenas, new String[]{COL_IDCOLMENA, COL_IDCOLMENAR, COL_NOMBRECOLMENA, COL_INCIDENCIA}, COL_IDCOLMENA + " LIKE \"" + COL_IDCOLMENAR
                + "\"", null, null, null, null);
        // String sql = "SELECT * FROM " + TABLA_colmenas
        //   + " WHERE " + COL_NOMBRE + " = " + "'"+nombre+"'";
        //Cursor c = bdd.rawQuery(sql, null);



        return cursorToCharter(c);

    }
    public ArrayList<Colmena> leerColmenas2 (String idColmenar)
    {
        ArrayList<Colmena> colmenaList = new ArrayList<>();


        String sql="SELECT * FROM TABLA_COLMENAS INNER JOIN TABLA_APIARIOS ON TABLA_COLMENAS.IDCOLMENA=TABLA_APIARIOS.IDCOLMENAR WHERE TABLA_APIARIOS.IDCOLMENAR=?";
       Cursor c= bdd.rawQuery(sql, new String[]{String.valueOf(idColmenar)});
// Cursor c = bdd.query(TABLA_colmenas, new String[]{COL_IDCOLMENA, COL_IDCOLMENAR, COL_NOMBRECOLMENA, COL_INCIDENCIA}, COL_IDCOLMENA=idColmenar, null, null, null, null,null);
        if (c.getCount() == 0) {

            return colmenaList;
        }

        while (c.moveToNext()) {
            Colmena colmena = new Colmena();
            colmena.setIdColmena(c.getInt(NUM_COL_IDCOLMENA));
            colmena.setIdColmenar(c.getInt(NUM_COL_IDCOLMENAR));
            colmena.setNombreColmena(c.getString(NUM_COL_NOMBRECOLMENA));
            colmena.setIncidencia(c.getString(NUM_COL_INCIDENCIA));

            colmenaList.add(colmena);
        }
        //si cierro aqui el cursor la cuenta es 0






        return colmenaList;
    }

    public Colmena cursorToCharter(Cursor c) {
        if(c.getCount()==0){
            c.close();
            return null;
        }
        Colmena colmena = new Colmena();
        colmena.setIdColmena(c.getInt(NUM_COL_IDCOLMENA));
        colmena.setIdColmenar(c.getInt(NUM_COL_IDCOLMENAR));
        colmena.setNombreColmena(c.getString(NUM_COL_NOMBRECOLMENA));
        colmena.setIncidencia(c.getString(NUM_COL_INCIDENCIA));


        return colmena;
    }

    public ArrayList<Colmena> leerColmenas() {
        ArrayList<Colmena> colmenaList = new ArrayList<>();

        Cursor c = bdd.query(TABLA_colmenas, new String[]{COL_IDCOLMENA, COL_IDCOLMENAR, COL_NOMBRECOLMENA, COL_INCIDENCIA}, null, null, null, null, null,null);
        if (c.getCount() == 0) {

            return colmenaList;
        }

        while (c.moveToNext()) {
            Colmena colmena = new Colmena();
            colmena.setIdColmena(c.getInt(NUM_COL_IDCOLMENA));
            colmena.setIdColmenar(c.getInt(NUM_COL_IDCOLMENAR));
            colmena.setNombreColmena(c.getString(NUM_COL_NOMBRECOLMENA));
            colmena.setIncidencia(c.getString(NUM_COL_INCIDENCIA));

            colmenaList.add(colmena);
        }
        //si cierro aqui el cursor la cuenta es 0

        return colmenaList;
    }

    public void borrarTodos() {
        colmenas.onUpgrade(bdd, VERSION, VERSION + 1);
    }
}
