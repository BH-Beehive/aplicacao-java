package database;

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
    private String password = "1470";
    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet resultSet = null;
    private Statement st = null;

    public ConexaoComBanco() {
    }

    public void conectarMySQL() {
        try {
            System.out.println("Abrindo conex�o com o banco ...");
            con = DriverManager.getConnection(url, user, password);
            System.out.println("Conex�o realizada com sucesso!");
        } catch (SQLException ex) {
            System.out.println("Falha ao conectar com o banco!" + ex.getMessage());
        }

    }

    public boolean validarAcesso(String email, String senha, String token) {
        try {
            ps = con.prepareStatement("select id_empresa,email, senha,token_acesso\n"
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
                System.out.println("Login efetuado com sucesso!");
//                startApi.execute();

            }

            if (rowCount == 0) {
                System.out.println("Usu?rio n?o encontrado!" +
                        "ERRO!");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao executar o select!" + e.getMessage());

        }
        return false;
    }

    public Connection getCon() {
        return con;
    }

}
