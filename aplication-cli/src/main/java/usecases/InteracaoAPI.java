package usecases;

import com.github.britooo.looca.api.core.Looca;
import database.ConexaoComBanco;
import database.Queries;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import utils.Conversor;

import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.ConfigLog;

public class InteracaoAPI {

    public Boolean iniciarAPI(String email, String tokenUsuario) throws FileNotFoundException, IOException {
        Boolean isEnded = false;
        Looca looca = new Looca();
        ConexaoComBanco con = new ConexaoComBanco();
        con.conectarMySQL();
        Path path = Paths.get("C:\\Users\\ferra\\Documents\\aplicacao-java\\aplication-cli\\src\\main\\java\\logs");
		
        if(!Files.exists(path)) {
			
            Files.createDirectory(path);
			
	}
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        String dataConvertida = dtf.format(LocalDateTime.now());
	File log = new File("C:\\Users\\ferra\\Documents\\aplicacao-java\\aplication-cli\\src\\main\\java\\logs\\logConexaoBanco");
		
	if(!log.exists()) {
			
            log.createNewFile();
		
	}
		
	FileWriter fw = new FileWriter(log, true);
	BufferedWriter bw = new BufferedWriter(fw);
		
        bw.write(email + "\n");
        bw.write(tokenUsuario + "\n");
        bw.write(dataConvertida + "\n\n");
        bw.newLine();

	bw.close();
	fw.close();
        Queries queries = new Queries(con);

        String arquitetura = "x" + looca.getSistema().getArquitetura().toString();
        String sistemaOperacional = looca.getSistema().getSistemaOperacional().toString();
        Long memoriaTotal = looca.getMemoria().getTotal();
        Long discoTotal = looca.getGrupoDeDiscos().getTamanhoTotal();
        String processador = looca.getProcessador().getNome();
        String host_name = "77777745454mls";
        String token = "138e813kj1323";
        String tipo = "servidor";
        queries.update(memoriaTotal, discoTotal, arquitetura, sistemaOperacional, processador, token);
        
        Timer timer = new Timer("Timer");
        final long segundos = (1000 * 3);
        Boolean finalIsEnded = isEnded;
        TimerTask task = new TimerTask() {
            Long memoriaUsada = 0L;
            Long cpuUsada = 0L;
            Long discoTotal = 0L;
            Long discoDisponivel = 0L;
            Long discoUsado = 0L;

            @Override
            public void run() {
                Conversor conversor = new Conversor();
                long prefixo = conversor.getMEBI();
                String alert = "";

                Long valorMemoriaUsada = looca.getMemoria().getEmUso();
                memoriaUsada = conversor.formatarUnidades(valorMemoriaUsada, prefixo);

                Long memoriaPercentual = 0L;
                Long memoriaTotal = conversor.formatarUnidades(looca.getMemoria().getTotal(), prefixo);
                memoriaPercentual = (memoriaUsada * 100) / memoriaTotal;

                Long valorCpuUsada = looca.getProcessador().getUso().longValue();
                cpuUsada = valorCpuUsada;

                if (cpuUsada >= 90 || memoriaPercentual >= 90) {
                    alert = "Vermelho";
                    Queries qr = new Queries(con);
                    String hostName = qr.selectColumn("host_name", tokenUsuario);
                    ConfigLog conLog = new ConfigLog(hostName);
                    try {
                        conLog.logEstadoMaquina("Vermelho");
                    } catch (IOException ex) {
                        Logger.getLogger(InteracaoAPI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                } else if (cpuUsada >= 80 || memoriaPercentual >= 80) {
                    alert = "Amarelo";
                    Queries qr = new Queries(con);
                    String hostName = qr.selectColumn("host_name", tokenUsuario);
                    ConfigLog conLog = new ConfigLog(hostName);
                    try {
                        conLog.logEstadoMaquina("Amarelo");
                    } catch (IOException ex) {
                        Logger.getLogger(InteracaoAPI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    alert = "Verde";
                    Queries qr = new Queries(con);
                    String hostName = qr.selectColumn("host_name", tokenUsuario);
                    ConfigLog conLog = new ConfigLog(hostName);
                    try {
                        conLog.logEstadoMaquina("Verde");
                    } catch (IOException ex) {
                        Logger.getLogger(InteracaoAPI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                Long valorDiscoUsado = looca.getGrupoDeDiscos().getVolumes().get(0).getTotal() - looca.getGrupoDeDiscos().getVolumes().get(0).getDisponivel();
                discoUsado = conversor.formatarUnidades(valorDiscoUsado, prefixo);
                queries.insertRegistro(100L, memoriaUsada, cpuUsada, discoUsado, alert);
            }
        };
        timer.scheduleAtFixedRate(task, 3, segundos);

        do {
            Conversor conversor = new Conversor();

            Scanner scan1 = new Scanner(System.in);
            Scanner scan2 = new Scanner(System.in);

            int escolhaMain;
            int escolhaSub;
            System.out.println("-------------------------------------------\n"
                    + "1 - RAM\n"
                    + "2 - DISCO\n"
                    + "3 - PROCESSADOR\n"
                    + "4 - SERVICOS\n"
                    + "5 - PROCESSOS\n"
                    + "6 - SISTEMA\n"
                    + "7 - FINALIZAR APLICACAO");
            System.out.println("Escolha o componente: ");
            escolhaMain = scan1.nextInt();
            long valor = 0L;
            long prefixo = conversor.getGIBI();
            String invalidOption = "\nEscolha invalida!";
            if (escolhaMain == 1) {
                System.out.println("\nMEMORIA\n"
                        + "1 - Total\n"
                        + "2 - Em uso\n"
                        + "3 - Disponivel\n");
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
            } else if (escolhaMain == 2) {
                List disco;
                Integer quantidade;
                System.out.println("\nDISCOS\n"
                        + "1 - Volumes\n"
                        + "2 - Discos\n"
                        + "3 - Quantidade de Volume\n"
                        + "4 - Quantidade de Discos\n"
                        + "5 - Tamanho total\n");
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
            } else if (escolhaMain == 3) {
                Double usoProcessador;
                System.out.println("\nPROCESSADOR\n"
                        + "1 - Uso\n"
                        + "2 - Fabricante \n"
                        + "3 - Frequencia \n"
                        + "4 - Identificador \n"
                        + "5 - Micro Arquitetura \n"
                        + "6 - Quantidade Nucleos Fisicos \n"
                        + "7 - Quantidade Nucleos Logicos \n"
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
            } else if (escolhaMain == 4) {
                List servicos;
                Integer quantidadeServicos;
                System.out.println("\nSERVICOS\n"
                        + "1 - Servicos\n"
                        + "2 - Servicos Ativos\n"
                        + "3 - Servicos Inativos\n"
                        + "4 - Total Servicos\n"
                        + "5 - Total Servicos Ativos\n"
                        + "6 - Total Servicos Inativos\n");
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
            } else if (escolhaMain == 5) {
                System.out.println("\nPROCESSOS\n"
                        + "1 - Processos\n"
                        + "2 - Total de processos\n"
                        + "3 - Total Threads\n");
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
            } else if (escolhaMain == 6) {
                System.out.println("\nSISTEMA\n"
                        + "1 - Fabricante\n"
                        + "2 - Sistema Operacional\n"
                        + "3 - Arquitetura\n"
                        + "4 - Permissoes\n"
                        + "5 - Tempo de atividade\n");
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
            } else if (escolhaMain == 7) {
                System.out.println("-------------------------------");
                System.out.println("APLICACAO ENCERRADA");
                isEnded = true;
                timer.cancel();
            } else {
                System.out.println(invalidOption);
            }
        } while (!isEnded);
        return isEnded;
    }
    
    

}


