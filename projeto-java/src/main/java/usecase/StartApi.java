/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package usecase;


import com.github.britooo.looca.api.core.Looca;
import database.ConexaoComBanco;
import database.Queries;
import utils.Conversor;

import java.util.Timer;
import java.util.TimerTask;

public class StartApi {
    private String token;

    public void execute() {
        Looca looca = new Looca();
        ConexaoComBanco con = new ConexaoComBanco();
        Conversor conversor = new Conversor();
        con.conectarMySQL();
        Queries queries = new Queries(con);
        long prefixo = conversor.getMEBI();

        String arquitetura = "x" + looca.getSistema().getArquitetura().toString();
        String sistemaOperacional = looca.getSistema().getSistemaOperacional();
        Double memoriaTotal = conversor.formatarUnidades(looca.getMemoria().getTotal(), prefixo).doubleValue();
        Double discoTotal = conversor.formatarUnidades(looca.getGrupoDeDiscos().getTamanhoTotal(), prefixo).doubleValue();
        String processador = looca.getProcessador().getNome();
        String host_name = queries.selectColumn("host_name",token);
        System.out.println("xxxxxxxxxxxxxxxxxxxxx"+host_name+"XXXXXXXXXXXXXXXX");
        String token = "138e813kj1323";
        String tipo = "servidor";
        queries.update(memoriaTotal, discoTotal, arquitetura, sistemaOperacional, processador, token);
        // queries.selectAll();
        // queries.selectBySetor("disco_uso", "triagem");
        //queries.selectByMaquina(host_name);
        //queries.insertDadosMaquina(host_name, token, tipo, memoriaTotal, discoTotal, arquitetura, sistemaOperacional,processador,"cirurgia",3);

        Timer timer = new Timer("Timer");
        final long segundos = (1000 * 3);
        TimerTask task = new TimerTask() {
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
                queries.insertRegistro(100L,memoriaUsada.doubleValue(), cpuUsada.intValue(), discoUsado.doubleValue(), alert);
                System.out.println("\n-------------------------------------------");
                System.out.println("\nCPU USADA:"+cpuUsada+"%\n");
                System.out.println("\nMEMORiA USADA:"+memoriaUsada+"\n");
                System.out.println("\nDISCO USADO:"+discoUsado+"\n");
                System.out.println("-------------------------------------------\n");    
            }

        };
        timer.scheduleAtFixedRate(task, 0, segundos);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    
    
}

