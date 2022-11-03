package model;

import enums.TipoMaquina;

public class Maquina {
    private Long id;
    private String hostName;
    private String tokenAcesso;
    private Boolean isTokenAtivo;
    private TipoMaquina tipoMaquina;
    private Double memoriaTotal;
    private Double discoTotal;
    private String arquitetura;
    private String sistemaOperacional;
    private String processador;

    public Maquina() {
    }

    
    public Maquina(String hostName, String tokenAcesso, TipoMaquina tipoMaquina) {
        this.hostName = hostName;
        this.tokenAcesso = tokenAcesso;
        this.tipoMaquina = tipoMaquina;
    }

    public Long getId() {
        return id;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getTokenAcesso() {
        return tokenAcesso;
    }

    public void setTokenAcesso(String tokenAcesso) {
        this.tokenAcesso = tokenAcesso;
    }

    public Boolean getTokenAtivo() {
        return isTokenAtivo;
    }

    public void setTokenAtivo(Boolean tokenAtivo) {
        isTokenAtivo = tokenAtivo;
    }

    public TipoMaquina getTipoMaquina() {
        return tipoMaquina;
    }

    public void setTipoMaquina(TipoMaquina tipoMaquina) {
        this.tipoMaquina = tipoMaquina;
    }

    public Double getMemoriaTotal() {
        return memoriaTotal;
    }

    public void setMemoriaTotal(Double memoriaTotal) {
        this.memoriaTotal = memoriaTotal;
    }

    public Double getDiscoTotal() {
        return discoTotal;
    }

    public void setDiscoTotal(Double discoTotal) {
        this.discoTotal = discoTotal;
    }

    public String getArquitetura() {
        return arquitetura;
    }

    public void setArquitetura(String arquitetura) {
        this.arquitetura = arquitetura;
    }

    public String getSistemaOperacional() {
        return sistemaOperacional;
    }

    public void setSistemaOperacional(String sistemaOperacional) {
        this.sistemaOperacional = sistemaOperacional;
    }

    public String getProcessador() {
        return processador;
    }

    public void setProcessador(String processador) {
        this.processador = processador;
    }
}
