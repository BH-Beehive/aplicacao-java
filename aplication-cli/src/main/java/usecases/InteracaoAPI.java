package usecases;

import com.github.britooo.looca.api.core.Looca;
import database.ConexaoComBanco;
import database.Queries;
import org.checkerframework.checker.units.qual.C;
import utils.Conversor;

import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class InteracaoAPI {

    String tokenFk_Maquina = "";
    public void returnToken(String token) {
        tokenFk_Maquina = token;
    }

    public Boolean iniciarAPI() {
       Boolean isEnded = false;
        Looca looca = new Looca();
        ConexaoComBanco con = new ConexaoComBanco();
        con.conectarMySQL();
        Queries queries = new Queries(con);
        Conversor conversor = new Conversor();
        long prefixoMB = conversor.getMEBI();
        String arquitetura = "x" + looca.getSistema().getArquitetura().toString();
        String sistemaOperacional = looca.getSistema().getSistemaOperacional();
        Double memoriaTotal = Double.valueOf(conversor.formatarUnidades(looca.getMemoria().getTotal(), prefixoMB));
        Double discoTotal = Double.valueOf(conversor.formatarUnidades(looca.getGrupoDeDiscos().getTamanhoTotal(), prefixoMB));
        String processador = looca.getProcessador().getNome();
        String host_name = "77777745454mls";
        String token = "138e813kj1323";
        String tipo = "servidor";
        queries.update(memoriaTotal, discoTotal, arquitetura, sistemaOperacional, processador, token);
        // queries.selectAll();
        // queries.selectBySetor("disco_uso", "triagem");
        //queries.selectByMaquina(host_name);
        //queries.insertDadosMaquina(host_name, token, tipo, memoriaTotal, discoTotal, arquitetura, sistemaOperacional,processador,"cirurgia",3);

        Timer timer = new Timer("Timer");
        final long segundos = (1000 * 3);
        Boolean finalIsEnded = isEnded;
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
                memoriaUsada = conversor.formatarUnidades(valorMemoriaUsada, prefixoMB);

                Long memoriaPercentual = 0L;
                Long memoriaTotal = conversor.formatarUnidades(looca.getMemoria().getTotal(), prefixoMB);
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
                discoUsado = conversor.formatarUnidades(valorDiscoUsado, prefixoMB);
                String fk_maquina = queries.selectFkMaquinaByToken(Login.token);
                queries.insertRegistro(fk_maquina, memoriaUsada, cpuUsada, discoUsado, alert);
            }
        };
        timer.scheduleAtFixedRate(task, 3, segundos);


        do {
            Scanner scan1 = new Scanner(System.in);
            Scanner scan2 = new Scanner(System.in);

            int escolhaMain;
            int escolhaSub;
            System.out.println("-------------------------------------------\n" +
                    "1 - RAM\n" +
                    "2 - DISCO\n" +
                    "3 - PROCESSADOR\n" +
                    "4 - SERVICOS\n" +
                    "5 - PROCESSOS\n" +
                    "6 - SISTEMA\n" +
                    "7 - FINALIZAR APLICACAO");
            System.out.println("Escolha o componente: ");
            escolhaMain = scan1.nextInt();
            long valor = 0L;
            long prefixo = conversor.getGIBI();
            String invalidOption = "\nEscolha invalida!";
            if (escolhaMain == 1) {
                System.out.println("\nMEMORIA\n" +
                        "1 - Total\n" +
                        "2 - Em uso\n" +
                        "3 - Disponivel\n");
                escolhaSub = scan2.nextInt();
                switch (escolhaSub) {
                    case 1:
                         valor = looca.getMemoria().getTotal();
                        System.out.println(conversor.formatarUnidades(valor, prefixo, "GB"));
                        break;
                    case 2:
                        valor = looca.getMemoria().getEmUso();
                        System.out.println(conversor.formatarUnidades(valor, prefixo, "GB"));
                        break;
                    case 3:
                        valor = looca.getMemoria().getDisponivel();
                        System.out.println(conversor.formatarUnidades(valor, prefixo, "GB"));
                        break;
                    default:
                        System.out.println(invalidOption);
                        break;
                }
            }
               else if (escolhaMain == 2) {
                List disco;
                Integer quantidade;
                System.out.println("\nDISCOS\n" +
                        "1 - Volumes\n" +
                        "2 - Discos\n" +
                        "3 - Quantidade de Volume\n" +
                        "4 - Quantidade de Discos\n" +
                        "5 - Tamanho total\n");
                escolhaSub = scan2.nextInt();
                switch (escolhaSub) {
                    case 1:
                        disco = looca.getGrupoDeDiscos().getVolumes();
                        System.out.println("\nVolumes: ");
                        System.out.println(disco);
                        break;
                    case 2:
                        disco = looca.getGrupoDeDiscos().getDiscos();
                        System.out.println("\nDiscos: ");
                        System.out.println(disco);
                        break;
                    case 3:
                        quantidade = looca.getGrupoDeDiscos().getQuantidadeDeVolumes();
                        System.out.println("\nQuantidade de volumes: ");
                        System.out.println(quantidade);
                        break;
                    case 4:
                        quantidade = looca.getGrupoDeDiscos().getQuantidadeDeDiscos();
                        System.out.println("\nQuantidade de Discos: ");
                        System.out.println(quantidade);
                        break;
                    case 5:
                        valor = looca.getGrupoDeDiscos().getTamanhoTotal();
                        System.out.println("\nTamanho total: ");
                        System.out.println(conversor.formatarUnidades(valor, prefixo, "GB"));
                        break;
                    default:
                        System.out.println(invalidOption);
                        break;

                }
            }
                else if (escolhaMain == 3) {
                    Double usoProcessador;
                    System.out.println("\nPROCESSADOR\n" +
                            "1 - Uso\n" +
                            "2 - Fabricante \n" +
                            "3 - Frequencia \n" +
                            "4 - Identificador \n" +
                            "5 - Micro Arquitetura \n" +
                            "6 - Quantidade Nucleos Fisicos \n" +
                            "7 - Quantidade Nucleos Logicos \n"
                    );
                    escolhaSub = scan2.nextInt();
                    switch (escolhaSub) {
                        case 1:
                            usoProcessador = looca.getProcessador().getUso();
                            System.out.println(String.format("%.1f", usoProcessador));
                            break;
                        case 2:
                            System.out.println(looca.getProcessador().getFabricante());
                            break;
                        case 3:
                            System.out.println(looca.getProcessador().getFrequencia() + "hz");
                            break;
                        case 4:
                            System.out.println(looca.getProcessador().getIdentificador());
                            break;
                        case 5:
                            System.out.println(looca.getProcessador().getMicroarquitetura());
                            break;
                        case 6:
                            System.out.println(looca.getProcessador().getNumeroCpusFisicas());
                            break;
                        case 7:
                            System.out.println(looca.getProcessador().getNumeroCpusLogicas());
                            break;
                        default:
                            System.out.println(invalidOption);
                            break;
                    }
                }
                else if (escolhaMain == 4) {
                List servicos;
                Integer quantidadeServicos;
                System.out.println("\nSERVICOS\n" +
                        "1 - Servicos\n" +
                        "2 - Servicos Ativos\n" +
                        "3 - Servicos Inativos\n" +
                        "4 - Total Servicos\n" +
                        "5 - Total Servicos Ativos\n" +
                        "6 - Total Servicos Inativos\n");
                escolhaSub = scan2.nextInt();
                switch (escolhaSub) {
                    case 1:
                        servicos = looca.getGrupoDeServicos().getServicos();
                        System.out.println("Servicos: ");
                        System.out.println(servicos);
                        break;
                    case 2:
                        servicos = looca.getGrupoDeServicos().getServicosAtivos();
                        System.out.println("Servicos ativos: ");
                        System.out.println(servicos);
                        break;
                    case 3:
                        servicos = looca.getGrupoDeServicos().getServicosInativos();
                        System.out.println("Servicos Inativos: ");
                        System.out.println(servicos);
                        break;
                    case 4:
                        quantidadeServicos = looca.getGrupoDeServicos().getTotalDeServicos();
                        System.out.println("Total de servicos: ");
                        System.out.println(quantidadeServicos);
                        break;
                    case 5:
                        quantidadeServicos = looca.getGrupoDeServicos().getTotalServicosAtivos();
                        System.out.println("Total de servicos ativos: ");
                        System.out.println(quantidadeServicos);
                        break;
                    case 6:
                        quantidadeServicos = looca.getGrupoDeServicos().getTotalServicosInativos();
                        System.out.println("Total servicos Inativos: ");
                        System.out.println(quantidadeServicos);
                        break;
                    default:
                        System.out.println(invalidOption);
                        break;
                }
            }
                else if(escolhaMain == 5) {
                System.out.println("\nPROCESSOS\n" +
                        "1 - Processos\n" +
                        "2 - Total de processos\n" +
                        "3 - Total Threads\n");
                escolhaSub = scan2.nextInt();
                switch (escolhaSub) {
                    case 1:
                        System.out.println(looca.getGrupoDeProcessos().getProcessos());
                        break;
                    case 2:
                        System.out.println(looca.getGrupoDeProcessos().getTotalProcessos());
                        break;
                    case 3:
                        System.out.println(looca.getGrupoDeProcessos().getTotalThreads());
                        break;
                    default:
                        System.out.println(invalidOption);
                        break;
                }
            }
                else if (escolhaMain == 6) {
                System.out.println("\nSISTEMA\n" +
                        "1 - Fabricante\n" +
                        "2 - Sistema Operacional\n" +
                        "3 - Arquitetura\n" +
                        "4 - Permissoes\n" +
                        "5 - Tempo de atividade\n");
                escolhaSub = scan2.nextInt();
                switch (escolhaSub) {
                    case 1:
                        System.out.println(looca.getSistema().getFabricante());
                        break;
                    case 2:
                        System.out.println(looca.getSistema().getSistemaOperacional());
                        break;
                    case 3:
                        System.out.println(looca.getSistema().getArquitetura());
                        break;
                    case 4:
                        System.out.println(looca.getSistema().getPermissao());
                        break;
                    case 5:
                        System.out.println(looca.getSistema().getTempoDeAtividade());
                        break;
                    default:
                        System.out.println(invalidOption);
                        break;
                }
            }
                else if (escolhaMain == 7) {
                System.out.println("-------------------------------");
                System.out.println("APLICACAO ENCERRADA");
                isEnded = true;
                timer.cancel();
            }
                else {
                System.out.println(invalidOption);
            }
        } while (!isEnded);
        return isEnded;
    }

}
