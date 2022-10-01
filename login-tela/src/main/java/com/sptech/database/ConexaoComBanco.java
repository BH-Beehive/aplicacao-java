package com.sptech.database;

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
    
    public boolean validarAcesso(String email,String senha,String token){
        try {
            resultSet = con.createStatement().executeQuery("select email, senha,tokenAcesso\n" +
"from empresa\n" +
"join maquina\n" +
"on fk_empresa = id_empresa where email ="+"'"+email+"'"+" and senha ="+"'"+senha+"'"
            + "and tokenAcesso ="+"'"+token+"';");
            while(resultSet.next()){
                System.out.println("email:" + resultSet.getString("email"));
                System.out.println("senha:" + resultSet.getString("senha"));
                System.out.println("tokenAcesso:" + resultSet.getString("tokenAcesso"));
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao executar o select!" + e.getMessage());
        }
        return false;
    }

}
