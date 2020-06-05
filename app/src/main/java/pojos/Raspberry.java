/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojos;

import java.io.Serializable;

/**
 * @author jordan
 */
public class Raspberry implements Serializable {

    Integer raspberryId;
    String modelo;
    String memoria;
    String direccion;

    public Raspberry() {
    }

    public Raspberry(Integer raspberryId, String modelo, String memoria, String direccion) {
        this.raspberryId = raspberryId;
        this.modelo = modelo;
        this.memoria = memoria;
        this.direccion = direccion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }


    public Integer getRaspberryId() {
        return raspberryId;
    }

    public void setRaspberryId(Integer raspberryId) {
        this.raspberryId = raspberryId;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMemoria() {
        return memoria;
    }

    public void setMemoria(String memoria) {
        this.memoria = memoria;
    }

    @Override
    public String toString() {
        return raspberryId + "," + modelo + "," + direccion;
    }

    public String toString2() {
        return "Raspberry{" + "raspberryId=" + raspberryId + ", modelo=" + modelo + ", memoria=" + memoria + ", direccion=" + direccion + '}';

    }


}
