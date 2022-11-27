package database;

import usecase.StartApi;

import java.sql.*;

public class ConexãoDocker {

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

    public Connection getConDocker() {
        return conDocker;
    }
}
