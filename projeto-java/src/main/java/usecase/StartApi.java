/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package usecase;

import com.github.britooo.looca.api.core.Looca;
import database.ConexaoComBanco;
import database.Queries;
import model.Maquina;

import java.util.Timer;
import java.util.TimerTask;

public class StartApi {

    public void execute() {
        Maquina maquina ;
        Looca looca = new Looca();
        ConexaoComBanco con = new ConexaoComBanco();
        con.conectarMySQL();
        Queries queries = new Queries(con);

        String arquitetura = "x" + looca.getSistema().getArquitetura().toString();
        String sistemaOperacional = looca.getSistema().getSistemaOperacional().toString();
        Double memoriaTotal = looca.getMemoria().getTotal().doubleValue();
        Double discoTotal = looca.getGrupoDeDiscos().getTamanhoTotal().doubleValue();
        String processador = looca.getProcessador().getNome();
        String host_name = queries.selectColumn("host_name");
        String token = queries.selectColumn("token_acesso");
        String tipo = queries.selectColumn("tipo");
        
        

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
            

            @Override
            public void run() {
                
                memoriaUsada = looca.getMemoria().getEmUso().doubleValue();
                cpuUsada = looca.getProcessador().getUso().intValue();
                discoTotal = looca.getGrupoDeDiscos().getVolumes().get(0).getTotal().doubleValue();
                discoDisponivel = looca.getGrupoDeDiscos().getVolumes().get(0).getDisponivel().doubleValue();
                discoUsado = discoTotal - discoDisponivel;
                queries.insertRegistro(100L,memoriaUsada, cpuUsada, discoUsado, "amarelo");
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
