package database;

<<<<<<< HEAD
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConexaoComBanco {
    private String url = "jdbc:mysql://localhost:3306/Beehive";
    private String user = "root";
    private String password = "";
    private Connection con = null;
    private ResultSet resultSet = null;
    public void conectarMySQL(){
        try {
            System.out.println("Abrindo conexão com o banco ...");
            con = DriverManager.getConnection(url,user,password);
            System.out.println("Conexão realizada com sucesso!");
=======
import aplication.TelaLogin;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import usecase.StartApi;

public class ConexaoComBanco {

    private StartApi startApi = new StartApi();
    private String url = "jdbc:mysql://localhost:3306/Beehive";
    private String user = "root";
    private String password = "meubanco";
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
>>>>>>> master
        } catch (SQLException ex) {
            System.out.println("Falha ao conectar com o banco!" + ex.getMessage());
        }

    }
<<<<<<< HEAD
    public void selectAll(String query){
        try {
            resultSet = con.createStatement().executeQuery(query);
            while(resultSet.next()){
                System.out.println("Nome:" + resultSet.getString("nome_empresa"));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao executar o select!" + e.getMessage());
        }
    }

    public ResultSet update(String query){
        try {
         return   resultSet = con.createStatement().executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
=======

    public boolean validarAcesso(String email, String senha, String token) {
        TelaLogin telaLogin = new TelaLogin();
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
                JOptionPane.showMessageDialog(telaLogin, "Login efetuado com sucesso!");
                startApi.execute();

            }

            if (rowCount == 0) {
                JOptionPane.showMessageDialog(telaLogin, "Usu?rio n?o encontrado!",
                        "ERRO!", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao executar o select!" + e.getMessage());

        }
        return false;
    }

    public Connection getCon() {
        return con;
    }

>>>>>>> master
}
