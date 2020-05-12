package es.jordan.apiariojordan2402;

import java.io.Serializable;

public class Usuario implements Serializable {
    private int id;
    private String nombre;
    private String contraseña;
    private String email;
    private String rol;
    public Usuario() {
    }

    public Usuario(int id, String nombre, String contraseña, String email, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.email = email;
        this.rol = rol;
    }

    public Usuario(String nombre, String contraseña, String email, String rol) {
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.email = email;
        this.rol = rol;
    }

    public Usuario(String nombre, String contraseña, String email) {
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.email = email;
    }

    public Usuario(int id, String name, String contraseña) {
        this.id = id;
        this.nombre = name;
        this.contraseña = contraseña;

    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Usuario(int id, String nombre, String contraseña, String email) {
        this.id = id;
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", contraseña='" + contraseña + '\'' +
                ", email='" + email + '\'' +
                ", rol='" + rol + '\'' +
                '}';
    }
    // @Override
 // public String toString() {
       // return " Nombre= | " + this.getNombre() + " |" + " Contraseña= | " + "****" + " |" +
              //  " Email= | " + this.getEmail() + " |"
                ;
    }



