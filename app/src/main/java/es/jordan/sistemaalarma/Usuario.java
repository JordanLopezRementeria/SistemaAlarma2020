package es.jordan.sistemaalarma;

import java.io.Serializable;


public class Usuario implements Serializable {

    private Integer usuarioId;
    private String nombre;
    private String contraseña;
    private String email;

    public Usuario() {

    }

    public Usuario(Integer usuarioId, String nombre,String contraseña, String email) {
        this.usuarioId = usuarioId;
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.email = email;
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
        return "Usuario{" + "usuarioId=" + usuarioId + ", nombre=" + nombre + ", contrase\u00f1a=" + contraseña + ", email=" + email + '}';
    }


}