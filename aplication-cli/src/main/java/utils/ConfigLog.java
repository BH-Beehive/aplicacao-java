package utils;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;



public class ConfigLog {
    
    private String maquina;
    private LocalDate dataAgora = LocalDate.now();

    public ConfigLog(String maquina) {
        this.maquina = maquina;
    }
    Integer contador = 1;
    public void logEstadoMaquina(String estado) throws FileNotFoundException, IOException {
        
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date hora = Calendar.getInstance().getTime(); // Ou qualquer outra forma que tem
        String dataFormatada = sdf.format(hora);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-DD");
        FileOutputStream logs = new FileOutputStream("C:\\Users\\ferra\\Documents\\aplicacao-java\\aplication-cli\\src\\main\\java\\logs\\logEstadoMaquina"+formatter.format(dataAgora)+".txt", true);
        DataOutputStream gravarArq = new DataOutputStream(logs);
            
        gravarArq.writeUTF("\n" + maquina);
        gravarArq.writeUTF(estado);
        gravarArq.writeUTF(dataFormatada);
        
        logs.close();
        
        System.out.println("\n Teste \n");
        contador++;
        
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-DD");
//        DateTimeFormatter formatterTime = DateTimeFormatter.;
//        
//        FileWriter log = new FileWriter("C:\\Users\\ferra\\Documents\\aplicacao-java\\aplication-cli\\src\\main\\java\\logs\\logEstadoMaquina"
//                +formatter.format(dataAgora)+".txt", true);
//        
//        PrintWriter arquivo = new PrintWriter(log);
//        arquivo.printf("%s", estado);
        }
                  
 }
        