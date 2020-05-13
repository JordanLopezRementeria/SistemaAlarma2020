package es.jordan.sistemaalarma;

import java.io.Serializable;
import java.util.ArrayList;

public class Colmena implements Serializable {

    private int idColmena;
    private int idColmenar;  //colmenar al que pertenecen fk
    private String nombreColmena;  //nombre de esa colmena
    private String incidencia;

    public Colmena() {
    }

    public Colmena(int idColmena, int idColmenar, String nombreColmena, String incidencia) {
        this.idColmena = idColmena;
        this.idColmenar = idColmenar;
        this.nombreColmena = nombreColmena;
        this.incidencia = incidencia;
    }

    public int getIdColmena() {
        return idColmena;
    }

    public void setIdColmena(int idColmena) {
        this.idColmena = idColmena;
    }

    public int getIdColmenar() {
        return idColmenar;
    }

    public void setIdColmenar(int idColmenar) {
        this.idColmenar = idColmenar;
    }

    public String getNombreColmena() {
        return nombreColmena;
    }

    public void setNombreColmena(String nombreColmena) {
        this.nombreColmena = nombreColmena;
    }

    public String getIncidencia() {
        return incidencia;
    }

    public void setIncidencia(String incidencia) {
        this.incidencia = incidencia;
    }

    @Override
    public String toString() {
        return "Colmena{" +
                "idColmena=" + idColmena +
                ", idColmenar=" + idColmenar +
                ", nombreColmena='" + nombreColmena + '\'' +
                ", incidencia='" + incidencia + '\'' +
                '}';
    }
}




