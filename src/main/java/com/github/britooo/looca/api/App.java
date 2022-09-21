package com.github.britooo.looca.api;

import com.github.britooo.looca.api.core.Looca;

public class App {
    public static void main(String[] args) {
        Looca looca = new Looca();
        System.out.println(looca.getMemoria());
        System.out.println(looca.getProcessador());
    }

}
