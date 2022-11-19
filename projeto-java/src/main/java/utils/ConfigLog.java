package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConfigLog {
    private String maquina;
    private String sistemaOperacional;
    private LocalDate dataAgora = LocalDate.now();

    public ConfigLog(String maquina, String sistemaOperacional) {
        this.maquina = maquina;
        this.sistemaOperacional = sistemaOperacional;
    }
    
    public void logEstadoMaquina(String estado) throws FileNotFoundException, IOException {
        
        if(sistemaOperacional.equalsIgnoreCase("Windows")) {
            Path path = Paths.get("C:\\logs");
		
		if(!Files.exists(path)) {
			
			Files.createDirectory(path);
			
		}
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                String dataConvertida = dtf.format(LocalDateTime.now());
		File log = new File("C:\\logs\\logEstadoMaquina"+dataAgora);
		
		if(!log.exists()) {
			
			log.createNewFile();
		
		}
		
		FileWriter fw = new FileWriter(log, true);
		BufferedWriter bw = new BufferedWriter(fw);
		
                bw.write(maquina + "\n");
		bw.write(estado + "\n");
                bw.write(dataConvertida + "\n\n");
		bw.newLine();

		bw.close();
		fw.close();
        }else {
            Path path = Paths.get("home\\logs");
		
		if(!Files.exists(path)) {
			
			Files.createDirectory(path);
			
		}
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                String dataConvertida = dtf.format(LocalDateTime.now());
		File log = new File("home\\logs\\logEstadoMaquina"+dataAgora);
		
		if(!log.exists()) {
			
			log.createNewFile();
		
		}
		
		FileWriter fw = new FileWriter(log, true);
		BufferedWriter bw = new BufferedWriter(fw);
		
                bw.write(maquina + "\n");
		bw.write(estado + "\n");
                bw.write(dataConvertida + "\n\n");
		bw.newLine();

		bw.close();
		fw.close();
        }
    }
}
