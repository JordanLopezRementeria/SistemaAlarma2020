
package pojos;


import java.io.Serializable;

public class Usuras implements Serializable {

    private int usurasId;
    private Usuario usuarioId;
    private Raspberry raspberryId;


    public Usuras() {
    }

    public Usuras(int usurasId, Usuario usuarioId, Raspberry raspberryId) {
        this.usurasId = usurasId;
        this.usuarioId = usuarioId;
        this.raspberryId = raspberryId;
    }



    public int getUsurasId() {
        return usurasId;
    }

    public void setUsurasId(int usurasId) {
        this.usurasId = usurasId;
    }

    public Usuario getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Usuario usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Raspberry getRaspberryId() {
        return raspberryId;
    }

    public void setRaspberryId(Raspberry raspberryId) {
        this.raspberryId = raspberryId;
    }

    @Override
    public String toString() {
        return "Usuras{" + "usurasId=" + usurasId + ", usuarioId=" + usuarioId + ", raspberryId=" + raspberryId + '}';
    }









}
