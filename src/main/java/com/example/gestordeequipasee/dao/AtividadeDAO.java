package com.example.gestordeequipasee.dao;

import com.example.gestordeequipasee.model.Atividade;
import com.example.gestordeequipasee.model.Status;

import java.util.List;

public interface AtividadeDAO {
    int adicionarAtividadeRetornandoId(Atividade atividade);

    void adicionarAtividade(Atividade atividade);

    Atividade buscarAtividadePorId(int id);

    List<Atividade> listarTodasAtividades();

    List<Atividade> listarAtividadesPorStatus(String status);

    void atualizarAtividade(Atividade atividade);

    void removerAtividade(int id);

    void atribuirAtividadeFuncionario(int atividadeId, int funcionarioId);

    // Adicionar novos métodos
    List<Atividade> obterAtividadesPorStatus(String status, int funcionarioId);

    List<Atividade> obterAtividadesPorFuncionario(int funcionarioId);

    void atualizarStatusAtividade(int id, Status novoStatus);
}
