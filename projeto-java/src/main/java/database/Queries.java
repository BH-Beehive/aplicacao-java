/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import aplication.TelaLogin;
import usecase.StartApi;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Queries {

    private PreparedStatement ps = null;
    private PreparedStatement psDock = null;
    private ResultSet resultSet = null;
    private Statement st = null;
    private ConexaoComBanco conexao = null;
    private ConexaoDocker conexaoDocker = null;

    public Queries(ConexaoComBanco conexao, ConexaoDocker conexaoDocker) {
        this.conexao = conexao;
        this.conexaoDocker = conexaoDocker;
    }
    public Queries(ConexaoComBanco conexao) {
        this.conexao = conexao;
    }

    public Queries(ConexaoDocker conexaoDocker) {
        this.conexaoDocker = conexaoDocker;
    }

    private TelaLogin tela = new TelaLogin();
    public void update(Double memoriaTotal, Double discoTotal, String arquitetura, String sistemaOperacional, String processador, String tokenAcesso) {
        try {
            ps = conexao.getCon().prepareStatement("update maquina set memoria_total = ? , "
                    + "disco_total = ? , arquitetura = ? , "
                    + "sistema_operacional = ? , processador = ? "
                    + " where token_acesso = ?;");
            ps.setDouble(1, memoriaTotal);
            ps.setDouble(2, discoTotal);
            ps.setString(3, arquitetura);
            ps.setString(4, sistemaOperacional);
            ps.setString(5, processador);
            ps.setString(6, tokenAcesso);
            ps.executeUpdate();
            if (conexaoDocker != null) {
                psDock = conexaoDocker.getConDocker().prepareStatement("update maquina set memoria_total = ? , "
                        + "disco_total = ? , arquitetura = ? , "
                        + "sistema_operacional = ? , processador = ? "
                        + " where token_acesso = ?;");
                psDock.setDouble(1, memoriaTotal);
                psDock.setDouble(2, discoTotal);
                psDock.setString(3, arquitetura);
                psDock.setString(4, sistemaOperacional);
                psDock.setString(5, processador);
                psDock.setString(6, tokenAcesso);
                psDock.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void selectAll() {
        try {
            resultSet = conexao.getCon().createStatement().executeQuery("select * from maquina");
            while (resultSet.next()) {
                System.out.println("Host_name:" + resultSet.getString("host_name")
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

    public void insertRegistro(String fkMaquina, Double memoriaUsada, Integer cpuUsada, Double discoUsado, String alerta) {
        if (StartApi.getAmbiente().equals("producao")) {
            try {
                ps = conexao.getCon().prepareStatement("insert into registro values (default,?,?,?,?,?);");
                ps.setString(1, fkMaquina);
                ps.setDouble(2, memoriaUsada);
                ps.setInt(3, cpuUsada);
                ps.setDouble(4, discoUsado);
                ps.setString(5, alerta);
                ps.executeUpdate();
                if (conexaoDocker != null) {
                    String sql= "insert into registro (data_registro,memoria_uso,cpu_uso,disco_uso) values (default,?,?,?);";
                    psDock = conexaoDocker.getConDocker().prepareStatement(sql);
                    psDock.setDouble(1, memoriaUsada);
                    psDock.setInt(2, cpuUsada);
                    psDock.setDouble(3, discoUsado);

                    psDock.executeUpdate();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                ps = conexao.getCon().prepareStatement("insert into registro values (null,default,?,?,?,?,?);");
                ps.setString(1, fkMaquina);
                ps.setDouble(2, memoriaUsada);
                ps.setInt(3, cpuUsada);
                ps.setDouble(4, discoUsado);
                ps.setString(5, alerta);
                ps.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void insertDadosMaquina(String host_name, String token, String tipo, Double memoriaTotal, Double discoTotal, String arquitetura, String so, String processador, String setor, Integer prioridade) {
        if (StartApi.getAmbiente().equals("producao")) {
            try {
                ps = conexao.getCon().prepareStatement(" insert into maquina values (?,?,1,?,?,?,?,?,?,1,?,?)");
                ps.setString(1, host_name);
                ps.setString(2, token);
                ps.setString(3, tipo);
                ps.setDouble(4, memoriaTotal);
                ps.setDouble(5, discoTotal);
                ps.setString(6, arquitetura);
                ps.setString(7, so);
                ps.setString(8, processador);
                ps.setString(9, setor);
                ps.setInt(10, prioridade);
                ps.executeUpdate();
                System.out.println("Cadastrado com sucesso!");

                if (conexaoDocker != null) {
                    psDock = conexaoDocker.getConDocker().prepareStatement(" insert into maquina values (null,?,?,true,?,?,?,?,?,?,1,?,?)");
                    psDock.setString(1, host_name);
                    psDock.setString(2, token);
                    psDock.setString(3, tipo);
                    psDock.setDouble(4, memoriaTotal);
                    psDock.setDouble(5, discoTotal);
                    psDock.setString(6, arquitetura);
                    psDock.setString(7, so);
                    psDock.setString(8, processador);
                    psDock.setString(9, setor);
                    psDock.setInt(10, prioridade);
                    psDock.executeUpdate();
                    System.out.println("Cadastrado com sucesso!");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                ps = conexao.getCon().prepareStatement(" insert into maquina values (null,?,?,true,?,?,?,?,?,?,1,?,?)");
                ps.setString(1, host_name);
                ps.setString(2, token);
                ps.setString(3, tipo);
                ps.setDouble(4, memoriaTotal);
                ps.setDouble(5, discoTotal);
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

    public String selectColumn(String coluna, String token) {
        try {
            resultSet = conexao.getCon().createStatement().executeQuery("SELECT "
                    + coluna+" from maquina where token_acesso='" + token+"';");

            while (resultSet.next()) {
                String colunaResultado = resultSet.getString(coluna);
                return colunaResultado;
            }

        } catch (SQLException e) {
            System.out.println("Erro ao executar o select!" + e.getMessage() + "\n" + this.getClass());

        }
        return "Erro ao executar select";
    }

    public String selectSetorFromMaquina(String hostname) {
        try {
            resultSet = conexao.getCon().createStatement().executeQuery(
                    "select nome_setor "
                            + " from setor join maquina on id_setor = fk_setor where host_name ="
                            + "'" + hostname + "'"
                            + ";");
            while (resultSet.next()) {
                String colunaResultado = resultSet.getString("nome_setor");
                return colunaResultado;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao executar o select!" + e.getMessage());
        }
        return "Erro ao executar select";
    }
}
