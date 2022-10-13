package aplication;


import com.github.britooo.looca.api.core.Looca;
import database.ConexaoComBanco;

import java.util.Scanner;

public class CLI {
    public static void main(String[] args) {
       Looca looca = new Looca();
        ConexaoComBanco con = new ConexaoComBanco();
        con.conectarMySQL();
        Scanner inNl = new Scanner(System.in);
        Scanner in = new Scanner(System.in);

        System.out.println("### ##   ### ###  ### ###  ###  ##    ####   ### ###  ### ###\n" +
                " ##  ##   ##  ##   ##  ##   ##  ##     ##     ##  ##   ##  ##\n" +
                " ##  ##   ##       ##       ##  ##     ##     ##  ##   ##\n" +
                " ## ##    ## ##    ## ##    ## ###     ##     ##  ##   ## ##\n" +
                " ##  ##   ##       ##       ##  ##     ##     ### ##   ##\n" +
                " ##  ##   ##  ##   ##  ##   ##  ##     ##      ###     ##  ##\n" +
                "### ##   ### ###  ### ###  ###  ##    ####      ##    ### ###\n" +
                "\n");


        System.out.println("Digite seu email: ");
        String email = inNl.next().toLowerCase();

        System.out.println("Digite sua senha: ");
        String senha = inNl.next().toLowerCase();

        System.out.println("Digite sua token: ");
        String token = inNl.next().toLowerCase();



    }
}
