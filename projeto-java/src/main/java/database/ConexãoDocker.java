package database;

import aplication.TelaLogin;
import usecase.StartApi;

import javax.swing.*;
import java.sql.*;

public class ConexãoDocker {

    StartApi startApi = new StartApi();
    private String urlDocker = "jdbc:mysql://172.17.0.2:3306/Beehive";
    private String userDocker = "ubuntu";
    private String passwordDocker = "123456";
    private Connection conDocker = null;
    private PreparedStatement ps = null;
    private ResultSet resultSet = null;
    private Statement st = null;

    public ConexãoDocker() {
    }

    public void conectarBancoDocker() {
        try {
            System.out.println("Abrindo conexao com o banco Docker...");
            conDocker = DriverManager.getConnection(urlDocker, userDocker, passwordDocker);
            System.out.println("Conexao realizada na Docker com sucesso!");
        } catch (SQLException ex) {
            System.out.println("Falha ao conectar com o banco Docker!" + ex.getMessage());
        }
    }

    public boolean validarAcesso(String email, String senha, String token) {
        TelaLogin telaLogin = new TelaLogin();
        try {
            ps = conDocker.prepareStatement("select id_empresa,email, senha,token_acesso, token_ativo \n"
                    + "from empresa\njoin maquina\non fk_empresa = id_empresa where email"
                    + " = ? and senha = ? and token_acesso = ?;");
            ps.setString(1, email);
            ps.setString(2, senha);
            ps.setString(3, token);
            resultSet = ps.executeQuery();
            int rowCount = 0;
            startApi.returnToken(token);
            while (resultSet.next()) {
                rowCount++;
                System.out.println("COUNT::" + rowCount);
                System.out.println("email:" + resultSet.getString("email"));
                System.out.println("senha:" + resultSet.getString("senha"));
                System.out.println("token_acesso:" + resultSet.getString("token_acesso"));
                JOptionPane.showMessageDialog(telaLogin, "Login efetuado com sucesso!");
                System.out.println(token);
                if(!resultSet.getBoolean("token_ativo")) {
                    startApi.setToken(resultSet.getString("token_acesso"));
                    startApi.execute();
                }
                else{
                    JOptionPane.showMessageDialog(telaLogin, "Token inválido!",
                            "ERRO!", JOptionPane.ERROR_MESSAGE);
                }

            }

            if (rowCount == 0) {
                JOptionPane.showMessageDialog(telaLogin, "Usuario nao encontrado!",
                        "ERRO!", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao executar validação!" + e.getMessage());

        }
        return false;
    }

    public Connection getConDocker() {
        return conDocker;
    }
}
