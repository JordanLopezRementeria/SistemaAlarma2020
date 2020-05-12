package es.jordan.apiariojordan2402;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class ComponenteAD {
    private static final int VERSION = 1;
    private static final String NOMBRE_BDD = "jordan.db";

    private static final String TABLA_USUARIOS = "tabla_usuarios";
    private static final String COL_ID = "ID";
    private static final int NUM_COL_ID = 0;
    private static final String COL_NOMBRE = "NOMBRE";
    private static final int NUM_COL_NOMBRE = 1;
    private static final String COL_CONTRASEÑA = "CONTRASEÑA";
    private static final int NUM_COL_CONTRASEÑA = 2;
    private static final String COL_EMAIL = "EMAIL";
    private static final int NUM_COL_EMAIL = 3;
    private static final String COL_ROL = "ROL";
    private static final int NUM_COL_ROL = 4;

    private SQLiteDatabase bdd;
    private UsuarioBaseSQLite usuarios;

    public ComponenteAD(Context context) {
        usuarios = new UsuarioBaseSQLite(context, NOMBRE_BDD, null, VERSION);
    }

    public void openForWrite() {
        bdd = usuarios.getWritableDatabase();
    }

    public void openForRead() {
        bdd = usuarios.getReadableDatabase();
    }

    public void close() {
        bdd.close();
    }

    public SQLiteDatabase getBdd() {
        return bdd;
    }

    public long insertUsuario(Usuario usuario) {
        ContentValues content = new ContentValues();
        content.put(COL_NOMBRE, usuario.getNombre());
        content.put(COL_CONTRASEÑA, usuario.getContraseña());
        content.put(COL_EMAIL, usuario.getEmail());
        content.put(COL_ROL, usuario.getRol());
        return bdd.insert(TABLA_USUARIOS, null, content);
    }

    public int modificarUsuario(Usuario usuario) {  //este es para modificarse a si mismo
        ContentValues content = new ContentValues();
        content.put(COL_NOMBRE, usuario.getNombre());
        content.put(COL_CONTRASEÑA, usuario.getContraseña());
        content.put(COL_EMAIL, usuario.getEmail());
        content.put(COL_ROL, usuario.getRol());
        return bdd.update(TABLA_USUARIOS, content, COL_NOMBRE + "=" + "'"+usuario.getNombre()+"'", null);
    }
    public int modificarUsuario2(String nombreOriginal,Usuario usuario) { //este es para modificar a otro por el nombre antiguo
        ContentValues content = new ContentValues();
        content.put(COL_NOMBRE, usuario.getNombre());
        content.put(COL_CONTRASEÑA, usuario.getContraseña());
        content.put(COL_EMAIL, usuario.getEmail());
        content.put(COL_ROL, usuario.getRol());
        return bdd.update(TABLA_USUARIOS, content, COL_NOMBRE + "=" + "'"+nombreOriginal+"'", null);
    }

    public int borrarUsuario(String nombre) {

        return bdd.delete(TABLA_USUARIOS, COL_NOMBRE + "=" + "'"+nombre+"'", null);
    }

    public Usuario leerUsuario(String nombre) {
        Cursor c = bdd.query(TABLA_USUARIOS, new String[]{COL_ID, COL_NOMBRE, COL_CONTRASEÑA, COL_EMAIL, COL_ROL}, COL_NOMBRE + " LIKE \"" + nombre
                + "\"", null, null, null, null);
       // String sql = "SELECT * FROM " + TABLA_USUARIOS
             //   + " WHERE " + COL_NOMBRE + " = " + "'"+nombre+"'";
        //Cursor c = bdd.rawQuery(sql, null);



        return cursorToCharter(c);

    }

    public Usuario cursorToCharter(Cursor c) {
        if(c.getCount()==0){
            c.close();
            return null;
        }
        Usuario usuario = new Usuario();
        usuario.setId(c.getInt(NUM_COL_ID));
        usuario.setNombre(c.getString(NUM_COL_NOMBRE));
        usuario.setContraseña(c.getString(NUM_COL_CONTRASEÑA));
        usuario.setEmail(c.getString(NUM_COL_EMAIL));
        usuario.setRol(c.getString(NUM_COL_ROL));

        return usuario;
    }

    public ArrayList<Usuario> leerUsuarios() {
        ArrayList<Usuario> usuarioList = new ArrayList<>();
        Usuario admin=new Usuario();


        //meto 1 usuario por defecto
        admin.setContraseña("admin");
        admin.setNombre("admin");
        admin.setEmail("admin");
        admin.setRol("admin");
        usuarioList.add(admin);
        Cursor c = bdd.query(TABLA_USUARIOS, new String[]{COL_ID, COL_NOMBRE, COL_CONTRASEÑA, COL_EMAIL, COL_ROL}, null, null, null, null, null,null);
        if (c.getCount() == 0) {

            return usuarioList;
        }

        while (c.moveToNext()) {
            Usuario usuario = new Usuario();
            usuario.setId(c.getInt(NUM_COL_ID));
            usuario.setNombre(c.getString(NUM_COL_NOMBRE));
            usuario.setContraseña(c.getString(NUM_COL_CONTRASEÑA));
            usuario.setEmail(c.getString(NUM_COL_EMAIL));
            usuario.setRol(c.getString(NUM_COL_ROL));

            usuarioList.add(usuario);
        }
        //si cierro aqui el cursor la cuenta es 0

        return usuarioList;
    }

    public void borrarTodos() {
        usuarios.onUpgrade(bdd, VERSION, VERSION + 1);
    }
}
