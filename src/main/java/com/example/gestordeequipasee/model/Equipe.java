package com.example.gestordeequipasee.model;

import java.util.List;

public class Equipe {
    private int id;
    private String nome;
    private List<Funcionario> membros;

    public Equipe(int id, String nome, List<Funcionario> membros) {
        this.id = id;
        this.nome = nome;
        this.membros = membros;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Funcionario> getMembros() {
        return membros;
    }

    public void setMembros(List<Funcionario> membros) {
        this.membros = membros;
    }
}
