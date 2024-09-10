package com.example.gestordeequipasee.model;

import java.time.LocalDate;

public class Atividade {
    private int id;
    private String descricao;
    private LocalDate dataInicio;
    private LocalDate dataTermino;
    private Prioridade prioridade;
    private Status status;
    private Funcionario funcionario;

    public Atividade() {}

    public Atividade(int id,
                     String descricao,
                     LocalDate dataInicio,
                     LocalDate dataTermino,
                     Prioridade prioridade,
                     Status status,
                     Funcionario funcionario) {
        this.id = id;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataTermino = dataTermino;
        this.prioridade = prioridade;
        this.status = status;
        this.funcionario = funcionario;
    }

    public Atividade(int id,
                     String descricao,
                     LocalDate dataInicio,
                     LocalDate dataTermino,
                     Prioridade prioridade,
                     Status status) {
        this(id, descricao, dataInicio, dataTermino, prioridade, status, null);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public LocalDate getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDate dataInicio) { this.dataInicio = dataInicio; }

    public LocalDate getDataTermino() { return dataTermino; }
    public void setDataTermino(LocalDate dataTermino) { this.dataTermino = dataTermino; }

    public Prioridade getPrioridade() { return prioridade; }
    public void setPrioridade(Prioridade prioridade) { this.prioridade = prioridade; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public Funcionario getFuncionario() { return funcionario;}
    public void setFuncionario(Funcionario funcionario) { this.funcionario = funcionario; }
}

