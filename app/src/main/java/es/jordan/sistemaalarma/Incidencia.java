package es.jordan.sistemaalarma;
import java.io.Serializable;

public class Incidencia implements Serializable {

    int incidenciaId;
    int usuarioId;
    String hora;

    public Incidencia() {
    }

    public Incidencia(int incidenciaId, int usuarioId, String hora) {
        this.incidenciaId = incidenciaId;
        this.usuarioId = usuarioId;
        this.hora = hora;
    }

    public int getIncidenciaId() {
        return incidenciaId;
    }

    public void setIncidenciaId(int incidenciaId) {
        this.incidenciaId = incidenciaId;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    @Override
    public String toString() {
        return "Notificacion{" + "incidenciaId=" + incidenciaId + ", usuarioId=" + usuarioId + ", hora=" + hora + '}';
    }




}
