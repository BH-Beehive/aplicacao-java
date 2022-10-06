package com.sptech.database;

import com.sptech.login.tela.TelaLogin;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class ConexaoComBanco {

    private String url = "jdbc:mysql://localhost:3306/Beehive";
    private String user = "root";
    private String password = "meubanco";
    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet resultSet = null;

    public void conectarMySQL() {
        try {
            System.out.println("Abrindo conexão com o banco ...");
            con = DriverManager.getConnection(url, user, password);
            System.out.println("Conexão realizada com sucesso!");
        } catch (SQLException ex) {
            System.out.println("Falha ao conectar com o banco!" + ex.getMessage());
        }

    }

    public void selectAll(String query) {
        try {
            ps = con.prepareStatement(query);
            resultSet = ps.executeQuery(query);
            while (resultSet.next()) {
                System.out.println("Nome:" + resultSet.getString("nome_empresa"));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao executar o select!" + e.getMessage());
        }
    }

    public ResultSet update(String query) {
        try {
            return resultSet = con.createStatement().executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
   

    public boolean validarAcesso(String email, String senha, String token) {
        TelaLogin telaLogin = new TelaLogin();
        try {
            ps = con.prepareStatement("select id_empresa,email, senha,tokenAcesso\n"
                    + "from empresa\njoin maquina\non fk_empresa = id_empresa where email"
                    + " = ? and senha = ? and tokenAcesso = ?;");
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
                System.out.println("tokenAcesso:" + resultSet.getString("tokenAcesso"));
                JOptionPane.showMessageDialog(telaLogin, "Login efetuado com sucesso!");

            }
            if (rowCount == 0) {
                JOptionPane.showMessageDialog(telaLogin, "Usuário não encontrado!",
                        "ERRO!", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao executar o select!" + e.getMessage());
        }
        return false;
    }

}
