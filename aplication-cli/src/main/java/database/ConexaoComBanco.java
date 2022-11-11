package database;

import usecases.InteracaoAPI;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class ConexaoComBanco {

    InteracaoAPI interacaoAPI = new InteracaoAPI();
    private Boolean isLogado = false;
    private String url = "jdbc:mysql://172.17.0.2:3306/Beehive";
    private String user = "root";
    private String password = "123456";
    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet resultSet = null;
    private Statement st = null;

    public ConexaoComBanco() {
        // TODO necessary to initialize
    }

    public String conectarMySQL() {
        try {
            System.out.println("Abrindo conexao com o banco ...");
            con = DriverManager.getConnection(url, user, password);
            System.out.println("Conexao realizada com sucesso!");
            return "OK";
        } catch (SQLException ex) {
            System.out.println("Falha ao conectar com o banco! " + ex.getMessage());
        }
        return "FAILED";
    }

    public boolean validarAcesso(String email, String senha, String token) {
        try {
            ps = con.prepareStatement("select id_empresa,email, senha,token_acesso, token_ativo \n"
                    + "from empresa\njoin maquina\non fk_empresa = id_empresa where email"
                    + " = ? and senha = ? and token_acesso = ?;");
            ps.setString(1, email);
            ps.setString(2, senha);
            ps.setString(3, token);
            resultSet = ps.executeQuery();
            int rowCount = 0;
            while (resultSet.next()) {
                rowCount++;
                System.out.println("COUNT::" + rowCount);
                System.out.println("email:" + resultSet.getString("email"));
                System.out.println("senha:" + resultSet.getString("senha"));
                System.out.println("token_acesso:" + resultSet.getString("token_acesso"));
                System.out.println("Logado com sucesso!");
                isLogado = true;
                if (resultSet.getBoolean("token_ativo")) {
                    interacaoAPI.setToken(resultSet.getString("token_acesso"));
                    interacaoAPI.execute();
                } else {
                    System.out.println("Token inválido!");
                }

            }

            if (rowCount == 0) {
                System.out.println("User not found");
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
