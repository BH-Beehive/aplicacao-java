package database;


import usecases.InteracaoAPI;
import utils.LoginAutomatic;

import javax.swing.*;
import java.sql.*;

public class ConexaoComBanco {


    InteracaoAPI interacaoAPI = new InteracaoAPI();
    private Boolean isLogado = false;
    private String url = "jdbc:mysql://localhost:3306/Beehive";
    private String user = "root";
    private String password = "1470";
    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet resultSet = null;
    private Statement st = null;

    public ConexaoComBanco() {
    }

    public String conectarBanco() {
        if (InteracaoAPI.getAmbiente().equals("producao")) {
            url = "jdbc:sqlserver://projeto-beehive.database.windows.net:1433;" +
                    "database=bd-beehive;" +
                    "user=admin-beehive@projeto-beehive;" +
                    "password={#Gfgrupo7};" +
                    "encrypt=true;" +
                    "trustServerCertificate=false;" +
                    "hostNameInCertificate=*.database.windows.net;" +
                    "loginTimeout=30;";
            try {
                System.out.println("Abrindo conexao com o banco Azure...");
                con = DriverManager.getConnection(url);
                System.out.println("Conexao realizada na Azure com sucesso!");
                return "OK";
            } catch (SQLException ex) {
                System.out.println("Falha ao conectar com o banco Azure!" + ex.getMessage());
                return "FAILED";
            }
        } else {
            try {
                System.out.println("Abrindo conexao com o banco Local...");
                con = DriverManager.getConnection(url, user, password);
                System.out.println("Conexao local realizada com sucesso!");
                return "OK";
            } catch (SQLException ex) {
                System.out.println("Falha ao conectar localmente com o banco!" + ex.getMessage());
                return "FAILED";
            }
        }
    }


    public boolean validarAcesso(String email, String senha, String token, Boolean automaticLogin) {
        try {
            ps = con.prepareStatement("select id_empresa,email, senha,token_acesso, token_ativo \n"
                    + "from empresa\njoin maquina\non fk_empresa = id_empresa where email"
                    + " = ? and senha = ? and token_acesso = ?;");
            ps.setString(1, email);
            ps.setString(2, senha);
            ps.setString(3, token);
            resultSet = ps.executeQuery();
            int rowCount = 0;
//            interacaoAPI.returnToken(token);
            while (resultSet.next()) {
                rowCount++;
                System.out.println("COUNT::" + rowCount);
                System.out.println("email:" + resultSet.getString("email"));
                System.out.println("senha:" + resultSet.getString("senha"));
                System.out.println("token_acesso:" + resultSet.getString("token_acesso"));
                System.out.println("Login executado com sucesso!");
                isLogado = true;
                System.out.println(token);
                if(resultSet.getBoolean("token_ativo")) {
                    interacaoAPI.setToken(resultSet.getString("token_acesso"));
                    if (automaticLogin) {
                        LoginAutomatic loginAutomatic = new LoginAutomatic();
                        loginAutomatic.criacaoArquivoLogin(email, senha, token);
                    }
                    interacaoAPI.execute();
                }
                else{
                    System.out.println("Token " + token + " invalido");
                }

            }

            if (rowCount == 0) {
                System.out.println("Usuario nao encontrado!");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao executar validação!" + e.getMessage());

        }
        return false;
    }

    public Boolean getLogado() {
        return isLogado;
    }

public Connection getCon() {
        return con;
    }    

}
