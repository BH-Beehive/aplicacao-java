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

public class Queries {

    private PreparedStatement ps = null;
    private ResultSet resultSet = null;
    private Statement st = null;
    private ConexaoComBanco conexao = new ConexaoComBanco();

    public Queries(ConexaoComBanco conexao) {
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

    public void update(Long memoriaTotal, Long discoTotal, String arquitetura, String sistemaOperacional, String processador, String tokenAcesso) {
        try {
            ps = conexao.getCon().prepareStatement("update maquina set memoria_total = ? , "
                    + "disco_total = ? , arquitetura = ? , "
                    + "sistema_operacional = ? , processador = ? "
                    + " where token_acesso = ?;");
            ps.setLong(1, memoriaTotal);
            ps.setLong(2, memoriaTotal);
            ps.setString(3, arquitetura);
            ps.setString(4, sistemaOperacional);
            ps.setString(5, processador);
            ps.setString(6, tokenAcesso);
            ps.executeUpdate();

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
                        + "\n Tipo:" + resultSet.getString("tipo")
                        + "\n Arquitetura: " + resultSet.getString("arquitetura")
                        + "\n Token: " + resultSet.getString("token_acesso")
                        + "\n Sistema operacional: " + resultSet.getString("sistema_operacional")
                        + "\n Mem�ria total: " + resultSet.getString("memoria_total")
                        + "\n CPU total: " + resultSet.getString("processador")
                        + "\n Disco total: " + resultSet.getString("disco_total")
                        + "\n Maquina validada: " + resultSet.getString("token_ativo")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erro ao executar o select!" + e.getMessage());
        }
    }

    public void insertRegistro(Long fkMaquina, Long memoriaUsada, Long cpuUsada, Long discoUsado, String alerta) {
        try {

            ps = conexao.getCon().prepareStatement("insert into registro values (null,default,?,?,?,?,?);");
            ps.setLong(1, fkMaquina);
            ps.setLong(2, memoriaUsada);
            ps.setLong(3, cpuUsada);
            ps.setLong(4, discoUsado);
            ps.setString(5, alerta);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertDadosMaquina(String host_name, String token, String tipo, Long memoriaTotal, Long discoTotal, String arquitetura, String so, String processador, String setor, Integer prioridade) {
        try {
            ps = conexao.getCon().prepareStatement(" insert into maquina values (null,?,?,true,?,?,?,?,?,?,1,?,?)");
            ps.setString(1, host_name);
            ps.setString(2, token);
            ps.setString(3, tipo);
            ps.setLong(4, memoriaTotal);
            ps.setLong(5, discoTotal);
            ps.setString(6, arquitetura);
            ps.setString(7, so);
            ps.setString(8, processador);
            ps.setString(9, setor);
            ps.setInt(10, prioridade);
            ps.executeUpdate();
            System.out.println("Cadastrado com sucesso!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void selectBySetor(String componente, String setor) {
        try {
            resultSet = conexao.getCon().createStatement().executeQuery(
                    "select "
                    + componente
                    + " ,setor from registro join maquina on id_maquina ="
                    + " fk_maquina "
                    + "where setor ="
                    + "'" + setor + "'"
                    + " order by id_registro desc limit 10;");
            while (resultSet.next()) {
                System.out.println("\nnome componente:" + componente
                        + "\nsetor: " + resultSet.getString("setor")
                        + "\nuso: " + resultSet.getString(componente));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao executar o select!" + e.getMessage());
        }

    }

    public void selectByMaquina(String hostname) {
        try {
            resultSet = conexao.getCon().createStatement().executeQuery(
                    "select host_name , memoria_uso ,"
                    + " cpu_uso, disco_uso "
                    + " from registro join maquina on id_maquina = fk_maquina where host_name ="
                    + "'" + hostname + "'"
                    + " order by id_registro desc limit 1;");
            while (resultSet.next()) {
                System.out.println("\nnome da máquina:" + hostname
                        + "\nuso disco: " + resultSet.getString("disco_uso")
                        + "\nuso cpu: " + resultSet.getString("cpu_uso")
                        + "\nuso ram: " + resultSet.getString("memoria_uso"));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao executar o select!" + e.getMessage());
        }
    }
}
