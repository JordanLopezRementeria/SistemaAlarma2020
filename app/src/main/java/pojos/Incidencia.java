package pojos;
import java.io.Serializable;


public class Incidencia implements Serializable {

    int incidenciaId;
    Raspberry raspberryId;
    String hora;

    public Incidencia() {
    }

    public Incidencia(int incidenciaId, Raspberry raspberryId, String hora) {
        this.incidenciaId = incidenciaId;
        this.raspberryId = raspberryId;
        this.hora = hora;
    }

    public int getIncidenciaId() {
        return incidenciaId;
    }

    public void setIncidenciaId(int incidenciaId) {
        this.incidenciaId = incidenciaId;
    }

    public Raspberry getRaspberryId() {
        return raspberryId;
    }

    public void setRaspberryId(Raspberry raspberryId) {
        this.raspberryId = raspberryId;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    @Override
    public String toString() {
        return "Notificacion{" + "incidenciaId=" + incidenciaId + ", raspberryId=" + raspberryId + ", hora=" + hora + '}';
    }




}
