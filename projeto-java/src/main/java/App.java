import com.github.britooo.looca.api.core.Looca;
import database.ConexaoComBanco;

public class App {
    public static void main(String[] args) {
        Looca looca = new Looca();
        System.out.println(looca.getMemoria());
        System.out.println(looca.getProcessador());
        ConexaoComBanco conect = new ConexaoComBanco();
        conect.conectarMySQL();
        conect.selectAll("SELECT * FROM empresa");
        conect.insert("insert into `maquina` values (null,'633791bb8af21', 1,'Rh',2);");
        conect.insert("insert into `maquina` values (null,'633791bb8af21', 1,'Ambulatorio',2);");
        conect.insert("insert into `maquina` values (null,'633791bb8af21', 1,'Uti',2);");
        conect.insert("insert into `maquina` values (null,'633791bb8af21', 1,'Efermagem',2);");
        System.out.println("\n\n");
        conect.selectAll("SELECT * FROM maquina");
        
        
    }
}
