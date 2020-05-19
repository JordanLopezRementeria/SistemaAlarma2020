package es.jordan.sistemaalarma;

import java.io.Serializable;


public class Usuario implements Serializable {

    private Integer usuarioId;
    private String nombre;

    private String contraseña;
    private String email;
    private String rol;
    private Raspberry raspberryId;

    public Usuario() {

    }

    public Usuario(Integer usuarioId, String nombre, String contraseña, String email,String rol,Raspberry raspberryId) {
        this.usuarioId = usuarioId;
        this.nombre = nombre;

        this.contraseña = contraseña;
        this.email = email;
        this.rol=rol;
        this.raspberryId=raspberryId;

    }

    public Raspberry getRaspberryId() {
        return raspberryId;
    }

    public void setRaspberryId(Raspberry raspberryId) {
        this.raspberryId = raspberryId;
    }



    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }





    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Usuario{" + "usuarioId=" + usuarioId + ", nombre=" + nombre + ", contrase\u00f1a=" + contraseña + ", email=" + email + ", rol=" + rol + ", raspberryId=" + raspberryId + '}';
    }






}
