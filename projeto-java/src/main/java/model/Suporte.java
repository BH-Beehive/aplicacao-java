package model;

public class Suporte {
    private Long id;
    private String name;
    private String email_slack;
    private String telefone;

    public Suporte(String name, String email_slack, String telefone) {
        this.name = name;
        this.email_slack = email_slack;
        this.telefone = telefone;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
