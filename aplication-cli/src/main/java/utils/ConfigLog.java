package utils;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class ConfigLog {
    
    private Integer contador;

    public ConfigLog(Integer contador) {
        this.contador = contador;
    }
    
    public void logEstadoMaquina(String estado) throws FileNotFoundException, IOException {
        
        contador++;
        FileOutputStream arq = new FileOutputStream("C:\\Users\\ferra\\Documents\\aplicacao-java\\aplication-cli\\src\\main\\java\\logs\\" + contador + ".txt");
        DataOutputStream gravarArq = new DataOutputStream(arq);
        Date data = new Date();
        String dataConvertida = data.toString();
  
        gravarArq.writeUTF(estado);
        gravarArq.writeChars(dataConvertida);
        
        arq.close();
        System.out.printf("\n Teste \n");
    }
}
