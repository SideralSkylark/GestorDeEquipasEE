package com.example.gestordeequipasee.dao;

import com.example.gestordeequipasee.model.Atividade;
import com.example.gestordeequipasee.model.Status;

import java.sql.SQLException;
import java.util.List;

public interface AtividadeDAO {
    int adicionarAtividadeRetornandoId(Atividade atividade);

    void adicionarAtividade(Atividade atividade);

    Atividade buscarAtividadePorId(int id);

    Atividade obterAtividadePorId(int id) throws SQLException;

    List<Atividade> listarTodasAtividades();

    List<Atividade> listarAtividadesPorStatus(String status);

    void atualizarAtividade(Atividade atividade);

    void editarAtividade(Atividade atividade) throws SQLException;

    void removerAtividade(int id);

    void atribuirAtividadeFuncionario(int atividadeId, int funcionarioId);

    // Novos métodos
    List<Atividade> obterAtividadesPorStatus(String status, int funcionarioId);

    List<Atividade> obterAtividadesPorFuncionario(int funcionarioId);

    List<Atividade> obterAtividadesPorFuncionarioAndamento(int funcionarioId) throws SQLException;

    void atualizarStatusAtividade(int id, Status novoStatus);

    void marcarComoConcluida(int idAtividade);
}
