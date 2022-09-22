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
    }
}
