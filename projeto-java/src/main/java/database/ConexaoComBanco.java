package database;

import aplication.TelaLogin;
import com.github.britooo.looca.api.core.Looca;
import org.apache.commons.dbcp2.BasicDataSource;
import usecase.StartApi;

import javax.swing.*;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;

public class ConexaoComBanco implements Conexao{

    private StartApi startApi = new StartApi();
    private String url = "jdbc:mysql://localhost:3306/Beehive";
    private String user = "root";
    private String password = "";
    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet resultSet = null;
    private Statement st = null;

    public ConexaoComBanco() {
    }

    public void conectarBanco() {
        if (StartApi.getAmbiente().equals("producao")) {
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
                DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
                con = DriverManager.getConnection(url);
                System.out.println("Conexao realizada na Azure com sucesso!");
            } catch (SQLException ex) {
                System.out.println("Falha ao conectar com o banco Azure!" + ex.getMessage());
            }
        } else {
            try {
                System.out.println("Abrindo conexao com o banco Local...");
                DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
                con = DriverManager.getConnection(url, user, password);
                System.out.println("Conexao local realizada com sucesso!");
            } catch (SQLException ex) {
                System.out.println("Falha ao conectar localmente com o banco!" + ex.getMessage());
            }
        }
    }
    public boolean validarAcesso(String email, String senha, String token) {
        TelaLogin telaLogin = new TelaLogin();
        try {
            ps = con.prepareStatement("select id_empresa,email, senha,token_acesso, token_ativo \n"
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
                    return true;
                }
                else if(resultSet.getBoolean("token_ativo")){
                    startApi.setToken(resultSet.getString("token_acesso"));
                    startApi.execute();
                    return true;
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

public Connection getCon() {
        return con;
    }
}
