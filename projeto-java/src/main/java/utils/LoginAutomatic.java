package utils;

import com.github.britooo.looca.api.core.Looca;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoginAutomatic {
    public void criacaoArquivoLogin(String email, String password, String token) {

        Looca looca = new Looca();
        if (looca.getSistema().getSistemaOperacional().equalsIgnoreCase("windows")) {
            Path path = Paths.get(".\\loginAutomatico");
            if (!Files.exists(path)) {

                try {
                    Files.createDirectory(path);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                File login = new File(".\\loginAutomatico\\LOGIN-AUTOMATICO");
                if (!login.exists()) {
                    try {
                        login.createNewFile();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                FileWriter fw = null;
                try {
                    fw = new FileWriter(login, true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write(email + "\n");
                    bw.write(password + "\n");
                    bw.write(token + "\n\n");
                    bw.newLine();
                    bw.close();
                    fw.close();
                    boolean bval = login.setReadOnly();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            Path path = Paths.get(".\\loginAutomatico");

            if (!Files.exists(path)) {

                try {
                    Files.createDirectory(path);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }

            File login = new File(".\\loginAutomatico\\LOGIN-AUTOMATICO");

            if (!login.exists()) {

                try {
                    login.createNewFile();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            try {
                FileWriter fw = new FileWriter(login, true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write("ola" + "\n");
                bw.write("tudoBem" + "\n");
                bw.write("Que bom" + "\n\n");
                bw.newLine();
                bw.close();
                fw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
