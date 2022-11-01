/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package usecase;

import com.github.britooo.looca.api.core.Looca;
import database.ConexaoComBanco;
import database.Queries;
<<<<<<< HEAD
=======
import utils.Conversor;

>>>>>>> main
import java.util.Timer;
import java.util.TimerTask;

public class StartApi {

    public void execute() {
        Looca looca = new Looca();
        ConexaoComBanco con = new ConexaoComBanco();
<<<<<<< HEAD
        con.conectarMySQL();
        Queries queries = new Queries(con);

        String arquitetura = "x" + looca.getSistema().getArquitetura().toString();
        String sistemaOperacional = looca.getSistema().getSistemaOperacional().toString();
        Double memoriaTotal = looca.getMemoria().getTotal().doubleValue();
        Double discoTotal = looca.getGrupoDeDiscos().getTamanhoTotal().doubleValue();
=======
        Conversor conversor = new Conversor();
        con.conectarMySQL();
        Queries queries = new Queries(con);
        long prefixo = conversor.getMEBI();

        String arquitetura = "x" + looca.getSistema().getArquitetura().toString();
        String sistemaOperacional = looca.getSistema().getSistemaOperacional();
        Long memoriaTotal = conversor.formatarUnidades(looca.getMemoria().getTotal(), prefixo);
        Long discoTotal = conversor.formatarUnidades(looca.getGrupoDeDiscos().getTamanhoTotal(), prefixo);
>>>>>>> main
        String processador = looca.getProcessador().getNome();
        String host_name = "77777745454mls";
        String token = "138e813kj1323";
        String tipo = "servidor";
<<<<<<< HEAD
       // queries.update(memoriaTotal, discoTotal, arquitetura, sistemaOperacional, processador, token);
        
        queries.selectAll();
       // queries.selectBySetor("disco_uso", "triagem");
=======
        queries.update(memoriaTotal, discoTotal, arquitetura, sistemaOperacional, processador, token);
        // queries.selectAll();
        // queries.selectBySetor("disco_uso", "triagem");
>>>>>>> main
        //queries.selectByMaquina(host_name);
        //queries.insertDadosMaquina(host_name, token, tipo, memoriaTotal, discoTotal, arquitetura, sistemaOperacional,processador,"cirurgia",3);

        Timer timer = new Timer("Timer");
        final long segundos = (1000 * 3);
        TimerTask task = new TimerTask() {
<<<<<<< HEAD
            Double memoriaUsada = null;
            Integer  cpuUsada = null;
            Double discoTotal = null;
            Double discoDisponivel = null;
            Double discoUsado = null;
            

            @Override
            public void run() {
                System.out.println("------------------------------------ \n");
                System.out.println("Cpu:" +cpuUsada+"%");
                System.out.println("Memoria ram:"+memoriaUsada);
                System.out.println("Disco Total:" +discoTotal);
                System.out.println("Disco Usado:" +discoUsado);
                System.out.println("Disco Disponivel:" +discoDisponivel);
                System.out.println("------------------------------------ \n");
                memoriaUsada = looca.getMemoria().getEmUso().doubleValue();
                cpuUsada = looca.getProcessador().getUso().intValue();
                discoTotal = looca.getGrupoDeDiscos().getVolumes().get(0).getTotal().doubleValue();
                discoDisponivel = looca.getGrupoDeDiscos().getVolumes().get(0).getDisponivel().doubleValue();
                discoUsado = discoTotal - discoDisponivel;
                queries.insertRegistro(101L,memoriaUsada, cpuUsada, discoUsado, "amarelo");
                
            }
            
        };
        timer.scheduleAtFixedRate(task, 2, segundos);
    }

}
=======
            Long memoriaUsada = null;
            Long cpuUsada = null;
            Long discoTotal = null;
            Long discoDisponivel = null;
            Long discoUsado = null;
            String alert = "";
            @Override
            public void run() {
                Long valorMemoriaUsada = looca.getMemoria().getEmUso();
                memoriaUsada = conversor.formatarUnidades(valorMemoriaUsada, prefixo);

                Long memoriaPercentual = 0L;
                Long memoriaTotal = conversor.formatarUnidades(looca.getMemoria().getTotal(), prefixo);
                memoriaPercentual = (memoriaUsada * 100) / memoriaTotal;

                Long valorCpuUsada = looca.getProcessador().getUso().longValue();
                cpuUsada = valorCpuUsada;

                if (cpuUsada >= 90 || memoriaPercentual >= 90) {
                    alert = "Vermelho";
                } else if (cpuUsada >= 80 || memoriaPercentual >= 80) {
                    alert = "Amarelo";
                } else {
                    alert = "Verde";
                }

                Long valorDiscoUsado = looca.getGrupoDeDiscos().getVolumes().get(0).getTotal() - looca.getGrupoDeDiscos().getVolumes().get(0).getDisponivel();
                discoUsado = conversor.formatarUnidades(valorDiscoUsado, prefixo);

                queries.insertRegistro(memoriaUsada, cpuUsada, discoUsado, alert);

            }

        };
        timer.scheduleAtFixedRate(task, 0, segundos);
    }

}

>>>>>>> main
