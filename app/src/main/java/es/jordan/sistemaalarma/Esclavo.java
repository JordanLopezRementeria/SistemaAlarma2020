package es.jordan.sistemaalarma;

import java.io.Serializable;

public class Esclavo implements Serializable {
//es el pojo usuras pero sin id
    private int raspberryId;
    private int usuarioId;

    public Esclavo() {
    }

    public Esclavo(int raspberryId, int usuarioId) {
        this.raspberryId = raspberryId;
        this.usuarioId = usuarioId;
    }

    public int getRaspberryId() {
        return raspberryId;
    }

    public void setRaspberryId(int raspberryId) {
        this.raspberryId = raspberryId;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }


    @Override
    public String toString() {
        return "Esclavo{" +
                "raspberryId=" + raspberryId +
                ", usuarioId=" + usuarioId +
                '}';
    }
}
