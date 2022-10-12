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
        Long memoriaTotal = looca.getMemoria().getTotal();
        Long discoTotal = looca.getGrupoDeDiscos().getTamanhoTotal();
        String processador = looca.getProcessador().getNome();
        String host_name = "asdm123as";
        String token = "138e813kj1331";
        queries.selectAll();
        //queries.selectBySetor("disco_uso", "triagem");
        queries.selectByMaquina(host_name);
        //con.insertDadosMaquina(host_name, token, memoriaTotal, discoTotal, arquitetura, sistemaOperacional,processador);

        Timer timer = new Timer("Timer");
        final long segundos = (1000 * 3);
        TimerTask task = new TimerTask() {
            Long memoriaUsada = null;
            Long cpuUsada = null;
            Long discoTotal = null;
            Long discoDisponivel = null;
            Long discoUsado = null;

            @Override
            public void run() {
                memoriaUsada = looca.getMemoria().getEmUso();
                cpuUsada = looca.getProcessador().getUso().longValue();
                discoTotal = looca.getGrupoDeDiscos().getVolumes().get(0).getTotal();
                discoDisponivel = looca.getGrupoDeDiscos().getVolumes().get(0).getDisponivel();
                discoUsado = discoTotal - discoDisponivel;
                queries.insertRegistro(memoriaUsada, cpuUsada, discoUsado, "amarelo");
                
            }
            
        };
        timer.scheduleAtFixedRate(task, 0, segundos);
    }

}
