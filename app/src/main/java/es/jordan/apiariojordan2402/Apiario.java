package es.jordan.apiariojordan2402;

import java.util.ArrayList;

public class Apiario {
    private int idApiario;
    private int idUsuario; //id del usuario que tiene ese colmenar
    private String nombreApiario;

    public Apiario() {
    }

    public Apiario(int idApiario, int idUsuario, String nombreApiario) {
        this.idApiario = idApiario;
        this.idUsuario = idUsuario;
        this.nombreApiario = nombreApiario;
    }

    public int getIdApiario() {
        return idApiario;
    }

    public void setIdApiario(int idApiario) {
        this.idApiario = idApiario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreApiario() {
        return nombreApiario;
    }

    public void setNombreApiario(String nombreApiario) {
        this.nombreApiario = nombreApiario;
    }

    @Override
    public String toString() {
        return "Apiario{" +
                "idApiario=" + idApiario +
                ", idUsuario=" + idUsuario +
                ", nombreApiario='" + nombreApiario + '\'' +
                '}';
    }
}