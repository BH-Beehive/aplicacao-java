package model;

import enums.Prioridade;

import java.util.List;

public class Setor {
    private Long id;
    private String nome;
    private Prioridade prioridade;
    private List<Maquina> maquinas;
}
