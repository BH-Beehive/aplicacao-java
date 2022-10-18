/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package usecase;

import com.github.britooo.looca.api.core.Looca;
import database.ConexaoComBanco;
import database.Queries;
import java.util.Timer;
import java.util.TimerTask;

public class StartApi {

    public void execute() {
        Looca looca = new Looca();
        ConexaoComBanco con = new ConexaoComBanco();
        con.conectarMySQL();
        Queries queries = new Queries(con);

        String arquitetura = "x" + looca.getSistema().getArquitetura().toString();
        String sistemaOperacional = looca.getSistema().getSistemaOperacional().toString();
        Double memoriaTotal = looca.getMemoria().getTotal().doubleValue();
        Double discoTotal = looca.getGrupoDeDiscos().getTamanhoTotal().doubleValue();
        String processador = looca.getProcessador().getNome();
        String host_name = "77777745454mls";
        String token = "138e813kj1323";
        String tipo = "servidor";
        
       // queries.update(memoriaTotal, discoTotal, arquitetura, sistemaOperacional, processador, token);
        
        queries.selectAll();
       // queries.selectBySetor("disco_uso", "triagem");
        //queries.selectByMaquina(host_name);
        //queries.insertDadosMaquina(host_name, token, tipo, memoriaTotal, discoTotal, arquitetura, sistemaOperacional,processador,"cirurgia",3);

        Timer timer = new Timer("Timer");
        final long segundos = (1000 * 3);
        TimerTask task = new TimerTask() {
            Double memoriaUsada = null;
            Integer  cpuUsada = null;
            Double discoTotal = null;
            Double discoDisponivel = null;
            Double discoUsado = null;
            Integer cont = 0;

            @Override
            public void run() {
               
                memoriaUsada = looca.getMemoria().getEmUso().doubleValue();
                cpuUsada = looca.getProcessador().getUso().intValue();
                discoTotal = looca.getGrupoDeDiscos().getVolumes().get(0).getTotal().doubleValue();
                discoDisponivel = looca.getGrupoDeDiscos().getVolumes().get(0).getDisponivel().doubleValue();
                discoUsado = discoTotal - discoDisponivel;
                int temperaturaAtual =looca.getTemperatura().getTemperatura().intValue();
                queries.insertRegistro(memoriaUsada, cpuUsada, discoUsado, "VERDE");
                
                
                System.out.println("------------------------------------ \n");
                System.out.println("Cpu:" +cpuUsada+"%");
                System.out.println(String.format("Memoria ram: %.2f Gi", (memoriaUsada.longValue()/1000000000.0)));
                System.out.println(String.format("Disco Total: %.2f Gi",discoTotal.longValue()/1000000000.0));
                System.out.println(String.format("Temperatura atual:"+ temperaturaAtual +"°C"));
                if(cpuUsada > 10){
                cont++;
                if(cont> 4){
                    System.out.println("Alerta: Uso de CPU acima da média" );
                }
                }
               
                System.out.println("------------------------------------ \n");
                
            }
           
        };
        timer.scheduleAtFixedRate(task, 4, segundos);
    }

}
