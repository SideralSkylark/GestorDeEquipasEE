package com.example.gestordeequipasee.dao;


import com.example.gestordeequipasee.model.Atividade;
import com.example.gestordeequipasee.model.Funcionario;
import com.example.gestordeequipasee.model.Prioridade;
import com.example.gestordeequipasee.model.Status;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAOImpl implements FuncionarioDAO {

    private Connection conexao;

    public FuncionarioDAOImpl(Connection conexao) {
        this.conexao = conexao;
    }

    @Override
    public List<Atividade> obterAtividadesPorFuncionario(int funcionarioId) {
        List<Atividade> atividades = new ArrayList<>();
        String sql = "SELECT * FROM atividades WHERE funcionario_id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, funcionarioId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Atividade atividade = new Atividade(
                        rs.getInt("id"),
                        rs.getString("descricao"),
                        rs.getDate("data_inicio").toLocalDate(),
                        rs.getDate("data_termino").toLocalDate(),
                        Prioridade.valueOf(rs.getString("prioridade")),
                        Status.valueOf(rs.getString("status")),
                        new Funcionario(rs.getInt("funcionario_id"), "", "", "", false) // Ajustar conforme necessário
                );
                atividades.add(atividade);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return atividades;
    }

    @Override
    public void adicionarFuncionario(Funcionario funcionario) {
        String sql = "INSERT INTO funcionarios (nome, email, senha, is_manager) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {

            pstmt.setString(1, funcionario.getNome());
            pstmt.setString(2, funcionario.getEmail());
            pstmt.setString(3, funcionario.getSenha());
            pstmt.setBoolean(4, funcionario.isManager());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Funcionario buscarFuncionarioPorEmail(String email) {
        String sql = "SELECT * FROM funcionarios WHERE email = ?";
        Funcionario funcionario = null;

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                funcionario = new Funcionario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getBoolean("is_manager")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return funcionario;
    }

    @Override
    public Funcionario buscarFuncionarioPorId(int id) throws SQLException {
        String sql = "SELECT * FROM funcionarios WHERE id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Funcionario(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("email"),
                            rs.getString("senha"),
                            rs.getBoolean("is_manager")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public List<Funcionario> listarNaoGerentes() {
        String sql = "SELECT * FROM funcionarios WHERE is_manager = FALSE";
        List<Funcionario> funcionarios = new ArrayList<>();

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Funcionario funcionario = new Funcionario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getBoolean("is_manager")
                );
                funcionarios.add(funcionario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return funcionarios;
    }

    @Override
    public List<Funcionario> listarGerentes() {
        String sql = "SELECT * FROM funcionarios WHERE is_manager = TRUE";
        List<Funcionario> gerentes = new ArrayList<>();

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Funcionario gerente = new Funcionario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getBoolean("is_manager")
                );
                gerentes.add(gerente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gerentes;
    }

    @Override
    public List<Funcionario> obterFuncionariosNaoGerentes() throws SQLException {
        String query = "SELECT id, nome FROM funcionarios WHERE is_manager = 0";
        List<Funcionario> funcionarios = new ArrayList<>();

        try (PreparedStatement stmt = conexao.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Funcionario funcionario = new Funcionario();
                funcionario.setId(rs.getInt("id"));
                funcionario.setNome(rs.getString("nome"));
                funcionarios.add(funcionario);
            }
        }

        return funcionarios;
    }
}
