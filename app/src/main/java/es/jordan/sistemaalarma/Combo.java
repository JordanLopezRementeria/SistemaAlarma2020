package es.jordan.sistemaalarma;


import java.io.Serializable;



public class Combo implements Serializable {
    String modelo;
    String hora;

    public Combo() {
    }

    public Combo(String modelo, String hora) {
        this.modelo = modelo;
        this.hora = hora;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    @Override
    public String toString() {
        return "Combo{" + "modelo=" + modelo + ", hora=" + hora + '}';
    }



}
