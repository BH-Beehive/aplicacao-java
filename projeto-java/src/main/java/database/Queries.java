/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package database;

import aplication.TelaLogin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;


public class Queries {    private PreparedStatement ps = null;
    private ResultSet resultSet = null;
    private Statement st = null;
    private ConexaoComBanco conexao = new ConexaoComBanco();
    
    public Queries(ConexaoComBanco conexao){
    this.conexao = conexao;
    }

  public void selectAll(String query) {
        try {
            ps = conexao.getCon().prepareStatement(query);
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
            return resultSet = conexao.getCon().createStatement().executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void selectAll() {
        try {
            resultSet = conexao.getCon().createStatement().executeQuery("select * from maquina");
            while (resultSet.next()) {
                System.out.println("Host_name:" + resultSet.getString("host_name")
                        + "\nNivel de prioridade: " + resultSet.getString("nivel_prioridade")
                        + "\n Setor:" + resultSet.getString("setor")
                        + "\n Arquitetura: " + resultSet.getString("arquitetura")
                        + "\n Token: " + resultSet.getString("token_acesso")
                        + "\n Sistema operacional: " + resultSet.getString("sistema_operacional")
                        + "\n Mem�ria total: " + resultSet.getString("memoria_total")
                        + "\n CPU total: " + resultSet.getString("processador")
                +"\n Disco total: " + resultSet.getString("disco_total")
                        + "\n Maquina validada: " + resultSet.getString("token_ativo")
            
         );
            }
        } catch (SQLException e) {
            System.out.println("Erro ao executar o select!" + e.getMessage());
        }
    }

    public void insertRegistro(Long memoriaUsada, Long cpuUsada, Long discoUsado, String alerta) {
        try {

            ps = conexao.getCon().prepareStatement("insert into registro values (null,default,102,?,?,?,?);");
            ps.setLong(1, memoriaUsada);
            ps.setLong(2, cpuUsada);
            ps.setLong(3, discoUsado);
            ps.setString(4, alerta);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertDadosMaquina(String host_name, String token, Long memoriaTotal, Long discoTotal, String arquitetura, String so, String processador) {
        try {
            ps = conexao.getCon().prepareStatement(" insert into maquina values (null,?,?,true,?,?,?,?,?,1,'Triagem',2)");
            ps.setString(1, host_name);
            ps.setString(2, token);
            ps.setLong(3, memoriaTotal);
            ps.setLong(4, discoTotal);
            ps.setString(5, arquitetura);
            ps.setString(6, so);
            ps.setString(7, processador);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
