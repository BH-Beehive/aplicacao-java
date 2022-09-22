package database;

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
        } catch (SQLException ex) {
            System.out.println("Falha ao conectar com o banco!" + ex.getMessage());
        }

    }
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
}
