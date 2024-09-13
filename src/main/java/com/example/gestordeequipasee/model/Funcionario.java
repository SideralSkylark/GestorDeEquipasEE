package com.example.gestordeequipasee.model;

public class Funcionario {
    private int id;
    private String nome;
    private String email;
    private String senha;
    private boolean is_manager;

    public Funcionario() {}

    public Funcionario(int id, String nome, String email, String senha, boolean is_manager) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.is_manager = is_manager;
    }

    public Funcionario(int id, String nome, String email, String senha, String cargo, boolean isManager) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.is_manager = is_manager;
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

    public boolean isManager() {
        return is_manager;
    }

    public void setManager(boolean is_manager) {
        this.is_manager = is_manager;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
