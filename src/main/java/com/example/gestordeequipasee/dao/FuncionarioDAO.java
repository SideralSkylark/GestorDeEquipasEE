package com.example.gestordeequipasee.dao;

import com.example.gestordeequipasee.model.Atividade;
import com.example.gestordeequipasee.model.Funcionario;

import java.sql.SQLException;
import java.util.List;

public interface FuncionarioDAO  {
    Funcionario buscarFuncionarioPorId(int id) throws SQLException;
    List<Atividade> obterAtividadesPorFuncionario(int funcionarioId);
    void adicionarFuncionario(Funcionario funcionario);
    Funcionario buscarFuncionarioPorEmail(String email);
    List<Funcionario> listarNaoGerentes();
    List<Funcionario> listarGerentes();
}
