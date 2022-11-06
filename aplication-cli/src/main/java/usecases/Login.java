package usecases;

import database.ConexaoComBanco;

import java.util.Scanner;

public class Login {

    public static String token = "";
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
        Scanner in = new Scanner(System.in);
        ConexaoComBanco conect = new ConexaoComBanco();
//        conect.conectarMySQL();
//        Queries queries = new Queries(conect);

        if (conect.conectarMySQL() == "OK") {
        do {
        System.out.println("Digite seu email: ");
        String email = inNl.next().toLowerCase();

        System.out.println("Digite sua senha: ");
        String senha = inNl.next().toLowerCase();

        System.out.println("Digite sua token: \n");
         token = inNl.next().toLowerCase();


       conect.validarAcesso(email, senha, token);
        } while (!conect.getLogado());
        }
        else {
            System.out.println("Falha ao executar aplicacao");
        }
    }
}
