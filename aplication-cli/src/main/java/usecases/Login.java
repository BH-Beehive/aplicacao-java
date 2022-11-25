package usecases;

import com.github.britooo.looca.api.core.Looca;
import database.ConexaoComBanco;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Login {

    String email = "";
    String senha = "";
    String token = "";
    public void archiveProcess(Path path, File login) {
        if (Files.exists(path) && login.exists()) {
            try {
                BufferedReader br
                        = new BufferedReader(new FileReader(login));
                String line;
                Integer contadorLinhas = 0;
                while((line = br.readLine()) != null){
                    if (contadorLinhas == 0) {
                        email = line;
                        contadorLinhas++;
                    } else if (contadorLinhas == 1) {
                        senha = line;
                        contadorLinhas++;
                    } else if (contadorLinhas == 2){
                        token = line;
                        contadorLinhas++;
                    }
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    @Override
    public String toString() {
        return String.format("Logando com as credenciais : %s, %s, %s",
                email,
                senha,
                token);
    }

    public void execute() {

        System.out.println("\n" +
                "                                                                                                                                                                                     \n" +
                "BBBBBBBBBBBBBBBBB        EEEEEEEEEEEEEEEEEEEEEE     EEEEEEEEEEEEEEEEEEEEEE     HHHHHHHHH     HHHHHHHHH     IIIIIIIIII     VVVVVVVV           VVVVVVVV     EEEEEEEEEEEEEEEEEEEEEE     \n" +
                "B::::::::::::::::B       E::::::::::::::::::::E     E::::::::::::::::::::E     H:::::::H     H:::::::H     I::::::::I     V::::::V           V::::::V     E::::::::::::::::::::E     \n" +
                "B::::::BBBBBB:::::B      E::::::::::::::::::::E     E::::::::::::::::::::E     H:::::::H     H:::::::H     I::::::::I     V::::::V           V::::::V     E::::::::::::::::::::E     \n" +
                "BB:::::B     B:::::B     EE::::::EEEEEEEEE::::E     EE::::::EEEEEEEEE::::E     HH::::::H     H::::::HH     II::::::II     V::::::V           V::::::V     EE::::::EEEEEEEEE::::E     \n" +
                "  B::::B     B:::::B       E:::::E       EEEEEE       E:::::E       EEEEEE       H:::::H     H:::::H         I::::I        V:::::V           V:::::V        E:::::E       EEEEEE     \n" +
                "  B::::B     B:::::B       E:::::E                    E:::::E                    H:::::H     H:::::H         I::::I         V:::::V         V:::::V         E:::::E                  \n" +
                "  B::::BBBBBB:::::B        E::::::EEEEEEEEEE          E::::::EEEEEEEEEE          H::::::HHHHH::::::H         I::::I          V:::::V       V:::::V          E::::::EEEEEEEEEE        \n" +
                "  B:::::::::::::BB         E:::::::::::::::E          E:::::::::::::::E          H:::::::::::::::::H         I::::I           V:::::V     V:::::V           E:::::::::::::::E        \n" +
                "  B::::BBBBBB:::::B        E:::::::::::::::E          E:::::::::::::::E          H:::::::::::::::::H         I::::I            V:::::V   V:::::V            E:::::::::::::::E        \n" +
                "  B::::B     B:::::B       E::::::EEEEEEEEEE          E::::::EEEEEEEEEE          H::::::HHHHH::::::H         I::::I             V:::::V V:::::V             E::::::EEEEEEEEEE        \n" +
                "  B::::B     B:::::B       E:::::E                    E:::::E                    H:::::H     H:::::H         I::::I              V:::::V:::::V              E:::::E                  \n" +
                "  B::::B     B:::::B       E:::::E       EEEEEE       E:::::E       EEEEEE       H:::::H     H:::::H         I::::I               V:::::::::V               E:::::E       EEEEEE     \n" +
                "BB:::::BBBBBB::::::B     EE::::::EEEEEEEE:::::E     EE::::::EEEEEEEE:::::E     HH::::::H     H::::::HH     II::::::II              V:::::::V              EE::::::EEEEEEEE:::::E     \n" +
                "B:::::::::::::::::B      E::::::::::::::::::::E     E::::::::::::::::::::E     H:::::::H     H:::::::H     I::::::::I               V:::::V               E::::::::::::::::::::E     \n" +
                "B::::::::::::::::B       E::::::::::::::::::::E     E::::::::::::::::::::E     H:::::::H     H:::::::H     I::::::::I                V:::V                E::::::::::::::::::::E     \n" +
                "BBBBBBBBBBBBBBBBB        EEEEEEEEEEEEEEEEEEEEEE     EEEEEEEEEEEEEEEEEEEEEE     HHHHHHHHH     HHHHHHHHH     IIIIIIIIII                 VVV                 EEEEEEEEEEEEEEEEEEEEEE     \n" +
                "                                                                                                                                                                                     \n");


        Scanner inNl = new Scanner(System.in);
        ConexaoComBanco conect = new ConexaoComBanco();
        Looca l = new Looca();
        String pathLinux = ".//loginAutomatico";
        String loginLinux = ".//loginAutomatico//LOGIN-AUTOMATICO";
        String loginWin = ".\\loginAutomatico\\LOGIN-AUTOMATICO";
        String pathWin = ".\\loginAutomatico";
        Path path = Paths.get(pathWin);
        File login = new File(loginWin);
        if (l.getSistema().getSistemaOperacional().equalsIgnoreCase("Linux")) {
            path = Paths.get(pathLinux);
            login = new File(loginLinux);
        }
        archiveProcess(path, login);
        Boolean automaticLogin = false;
        if (conect.conectarBanco().equals("OK")) {
        do {
            if (!(Files.exists(path) && login.exists())) {
                System.out.println("Digite seu email: ");
                email = inNl.next().toLowerCase();

                System.out.println("Digite sua senha: ");
                senha = inNl.next().toLowerCase();

                System.out.println("Digite sua token: \n");
                token = inNl.next().toLowerCase();

                System.out.println("Continar Conectado? S/N");
                String continueConected = inNl.next().toLowerCase();

                if (continueConected.equalsIgnoreCase("S")) {
                    automaticLogin = true;
                }
            } else {
                toString();
            }


       conect.validarAcesso(email, senha, token, automaticLogin);
        } while (!conect.getLogado());
        }
        else {
            System.out.println("Falha ao executar aplicacao");
        }
    }

}
