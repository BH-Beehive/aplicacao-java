package model;

public class Suporte {
    private Long id;
    private String nome;
    private String email_slack;
    private String telefone;

    public Suporte(String nome, String email_slack, String telefone) {
        this.nome = nome;
        this.email_slack = email_slack;
        this.telefone = telefone;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail_slack() {
        return email_slack;
    }

    public void setEmail_slack(String email_slack) {
        this.email_slack = email_slack;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
