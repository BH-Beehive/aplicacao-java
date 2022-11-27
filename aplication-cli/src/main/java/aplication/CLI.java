package aplication;


import com.github.britooo.looca.api.core.Looca;
import database.ConexaoComBanco;
import usecases.Login;

import java.util.Scanner;

public class CLI {
    public static void main(String[] args) {
        Login login = new Login();
        login.execute();
    }
}
