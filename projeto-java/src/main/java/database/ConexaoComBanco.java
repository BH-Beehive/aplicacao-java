package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexaoComBanco {
    private String url = "jdbc:mysql://localhost:3306/Beehive";
    private String user = "root";
    private String password = "";
    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet resultSet = null;
    private Statement st = null;
    public void conectarMySQL(){
        try {
            System.out.println("Abrindo conexão com o banco ...");
            con = DriverManager.getConnection(url,user,password);
            System.out.println("Conexão realizada com sucesso!");
        } catch (SQLException ex) {
            System.out.println("Falha ao conectar com o banco!" + ex.getMessage());
        }

    }
    public void selectAll(){
        try {
            resultSet = con.createStatement().executeQuery("select * from maquina");
            while(resultSet.next()){
                System.out.println("Host_name:" + resultSet.getString("host_name")+
                        "\nNivel de prioridade: "+ resultSet.getString("nivel_prioridade")
                +"\n Setor:"+ resultSet.getString("setor")
                +"\n Arquitetura: "+resultSet.getString("arquitetura")
                +"\n Token: "+ resultSet.getString("token_acesso")
                +"\n Sistema operacional: "+ resultSet.getString("sistema_operacional")
                +"\n Memória total: "+ resultSet.getString("memoria_total")
                +"\n CPU total: "+ resultSet.getString("processador")
                +"\n Disco total: "+ resultSet.getString("disco_total")
                +"\n Maquina validada: "+ resultSet.getString("token_ativo"));
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
    
     public void insertRegistro(Long memoriaUsada,Long cpuUsada,Long discoUsado,String alerta){
        try{
            
            ps = con.prepareStatement("insert into registro values (null,default,102,?,?,?,?);");
            ps.setLong(1,memoriaUsada);
            ps.setLong(2,cpuUsada);
            ps.setLong(3,discoUsado);
            ps.setString(4,alerta);
            ps.executeUpdate();
            
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
     public void insertDadosMaquina(String host_name,String token,Long memoriaTotal,Long discoTotal,String arquitetura,String so,String processador){
         try{
             ps= con.prepareStatement(" insert into maquina values (null,?,?,true,?,?,?,?,?,1,'Triagem',2)");
             ps.setString(1,host_name);
             ps.setString(2,token);
             ps.setLong(3,memoriaTotal);
             ps.setLong(4,discoTotal);
             ps.setString(5,arquitetura);
             ps.setString(6,so);
             ps.setString(7,processador);
             ps.executeUpdate();
             
             
         }catch(SQLException e){
             throw new RuntimeException(e);
         }
     }
}
