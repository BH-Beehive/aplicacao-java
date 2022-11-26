package model;

import enums.Alertas;

import java.util.Date;

public class Registro {
    private Date dataRegistro;
    private Maquina maquina;
    private Alertas alerta;

    public Date getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(Date dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public Maquina getMaquina() {
        return maquina;
    }

    public void setMaquina(Maquina maquina) {
        this.maquina = maquina;
    }

    public Alertas getAlerta() {
        return alerta;
    }

    public void setAlerta(Alertas alerta) {
        this.alerta = alerta;
    }
}
