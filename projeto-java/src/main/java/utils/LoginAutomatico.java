package utils;

import com.github.britooo.looca.api.core.Looca;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;


public class LoginAutomatico {
    public void criacaoArquivoLogin(String email, String password, String token) {
        Looca looca = new Looca();
        if (!(email.equals("") && password.equals("") && token.equals(""))) {
            if (looca.getSistema().getSistemaOperacional().equalsIgnoreCase("windows")) {
                Path path = Paths.get("..\\loginAutomatico");
                if (!Files.exists(path)) {

                    try {
                        Files.createDirectory(path);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    File login = new File("..\\loginAutomatico\\LOGIN-AUTOMATICO");
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
                        Files.setAttribute(path, "dos:hidden", Boolean.TRUE, LinkOption.NOFOLLOW_LINKS);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            } else {
                Path path = Paths.get("..//loginAutomatico");

                if (!Files.exists(path)) {
                    try {
                        Files.createDirectory(path);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }

                File login = new File("..//loginAutomatico//LOGIN-AUTOMATICO");

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
                    bw.write(email + "\n");
                    bw.write(password + "\n");
                    bw.write(token + "\n\n");
                    bw.newLine();
                    bw.close();
                    fw.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
