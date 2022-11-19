package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class Teste {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("C:\\Users\\ferra\\Documents\\aplicacao-java\\aplication-cli\\src\\main\\java\\logs");
		
		if(!Files.exists(path)) {
			
			Files.createDirectory(path);
			
		}
                
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                System.out.println("yyyy/MM/dd HH:mm:ss-> "+dtf.format(LocalDateTime.now()));
		File log = new File("C:\\Users\\ferra\\Documents\\aplicacao-java\\aplication-cli\\src\\main\\java\\logs\\"+dtf.format(LocalDateTime.now()));
		
		if(!log.exists()) {
			
			log.createNewFile();
		
		}
		
		FileWriter fw = new FileWriter(log, true);
		BufferedWriter bw = new BufferedWriter(fw);
		
		bw.write("Verde");
		bw.newLine();

		bw.close();
		fw.close();
    }
}
