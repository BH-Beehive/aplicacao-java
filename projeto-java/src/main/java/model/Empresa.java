package model;

import java.util.List;

public class Empresa {
    private Long id;
    private String nome;
    private Boolean isPlanoAtivo;
    private List<Suporte> suportes;

    public Empresa(String nome, Boolean isPlanoAtivo) {
        this.nome = nome;
        this.isPlanoAtivo = isPlanoAtivo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean getPlanoAtivo() {
        return isPlanoAtivo;
    }

    public void setPlanoAtivo(Boolean planoAtivo) {
        isPlanoAtivo = planoAtivo;
    }

    public List<Suporte> getSuportes() {
        return suportes;
    }

    public void setSuportes(List<Suporte> suportes) {
        this.suportes = suportes;
    }
    public void adicionarSuporte(Suporte suporte){
        suportes.add(suporte);
    }
}
