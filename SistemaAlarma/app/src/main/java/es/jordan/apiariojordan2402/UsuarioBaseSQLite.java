package es.jordan.sistemaalarma;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class UsuarioBaseSQLite extends SQLiteOpenHelper {
    //tabla primera
    private static final String TABLA = "tabla_usuarios";
    private static final String COL_ID = "ID";
    private static final String COL_NOMBRE = "NOMBRE";
    private static final String COL_CONTRASEÑA = "CONTRASEÑA";
    private static final String COL_EMAIL = "EMAIL";
    private static final String COL_ROL = "ROL";
    //tabla segunda
    private static final String TABLA2 = "tabla_colmenas";
    private static final String COL_ID2 = "IDCOLMENA";
    private static final String COL_FK = "IDAPIARIO";
    private static final String COL_NOMBRE2 = "NOMBRECOLMENA";
    private static final String COL_INCIDENCIA = "INCIDENCIA";
    //tabla tercera padre de colmena
    private static final String TABLA3 = "tabla_apiarios";
    private static final String COL_ID3 = "IDAPIARIO";
    private static final String COL_FK2 = "IDUSUARIO";
    private static final String COL_NOMBRE3 = "NOMBREAPIARIO";


   private static final String CREATE_TABLE = "CREATE TABLE " + TABLA + " (" +
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_NOMBRE + " TEXT NOT NULL, " +
            COL_CONTRASEÑA + " TEXT NOT NULL, " + COL_EMAIL + " TEXT NOT NULL, " + COL_ROL + " TEXT NOT NULL)";
    //nota mental:recuerda los espacios al poner mas campos o estaras 2h viendo que porque falla!!

    private static final String CREATE_TABLE2 = "CREATE TABLE " + TABLA2 + " (" +
            COL_ID2 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_FK + " INTEGER NOT NULL, " + COL_NOMBRE2 + " TEXT NOT NULL, " + COL_INCIDENCIA + " TEXT )";

    private static final String CREATE_TABLE3 = "CREATE TABLE " + TABLA3 + " (" +
            COL_ID3 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_FK2 + " INTEGER NOT NULL, " + COL_NOMBRE3 + " TEXT NOT NULL)";

    public UsuarioBaseSQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_TABLE2);
        db.execSQL(CREATE_TABLE3);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLA);
        db.execSQL("DROP TABLE " + TABLA2);
        db.execSQL("DROP TABLE " + TABLA3);
        onCreate(db);
    }
}
