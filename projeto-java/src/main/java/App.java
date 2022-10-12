
import com.github.britooo.looca.api.core.Looca;
import com.sptech.login.tela.TelaLogin;
import database.ConexaoComBanco;
import java.sql.Date;
import java.util.Timer;
import java.util.TimerTask;

public class App {

    public static void main(String[] args) {
        Looca looca = new Looca();
        ConexaoComBanco con = new ConexaoComBanco();
        TelaLogin login = new TelaLogin();
       
        
        con.conectarMySQL();
        String arquitetura = "x" + looca.getSistema().getArquitetura().toString();
        String sistemaOperacional = looca.getSistema().getSistemaOperacional().toString();
        Long memoriaTotal = looca.getMemoria().getTotal();
        Long discoTotal = looca.getGrupoDeDiscos().getTamanhoTotal();
        String processador = looca.getProcessador().getNome();
        String host_name = "asdm123as";
       String token = "138e813kj1331";
       con.selectAll();
        //con.insertDadosMaquina(host_name, token, memoriaTotal, discoTotal, arquitetura, sistemaOperacional,processador);

        Timer timer = new Timer("Timer");
        final long segundos = (1000 * 3);
        TimerTask task = new TimerTask() {
            Long memoriaUsada = null;
            Long cpuUsada = null;
            Long discoTotal=null;
            Long discoDisponivel = null;
            Long discoUsado = null; 
            @Override
            public void run() {
                memoriaUsada = looca.getMemoria().getEmUso();
                cpuUsada = looca.getProcessador().getUso().longValue();
                discoTotal=looca.getGrupoDeDiscos().getVolumes().get(0).getTotal();
                discoDisponivel = looca.getGrupoDeDiscos().getVolumes().get(0).getDisponivel();
                discoUsado = discoTotal - discoDisponivel; 
              con.insertRegistro(memoriaUsada, cpuUsada, discoUsado, "amarelo");

            }
        };
        timer.scheduleAtFixedRate(task, 0, segundos);
    }
}
