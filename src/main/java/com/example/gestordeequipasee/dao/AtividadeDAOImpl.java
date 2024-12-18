package com.example.gestordeequipasee.dao;

import com.example.gestordeequipasee.model.Atividade;
import com.example.gestordeequipasee.model.Funcionario;
import com.example.gestordeequipasee.model.Prioridade;
import com.example.gestordeequipasee.model.Status;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AtividadeDAOImpl implements AtividadeDAO {

    private Connection conexao;

    public AtividadeDAOImpl(Connection conexao) {
        this.conexao = conexao;
    }

    @Override
    public int adicionarAtividadeRetornandoId(Atividade atividade) {
        String sql = "INSERT INTO atividades (descricao, data_inicio, data_termino, prioridade, status, funcionario_id) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, atividade.getDescricao());
            stmt.setDate(2, Date.valueOf(atividade.getDataInicio()));
            stmt.setDate(3, Date.valueOf(atividade.getDataTermino()));
            stmt.setString(4, atividade.getPrioridade().name());

            String status = (atividade.getStatus() != null) ? atividade.getStatus().name() : Status.EM_ANDAMENTO.name();
            stmt.setString(5, status);

            if (atividade.getFuncionario() != null) {
                stmt.setInt(6, atividade.getFuncionario().getId());
            } else {
                stmt.setNull(6, Types.INTEGER);
            }

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Falha ao obter o ID da nova atividade.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public void adicionarAtividade(Atividade atividade) {
        String sql = "INSERT INTO atividades (descricao, data_inicio, data_termino, prioridade, status, funcionario_id) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, atividade.getDescricao());
            stmt.setDate(2, Date.valueOf(atividade.getDataInicio()));
            stmt.setDate(3, Date.valueOf(atividade.getDataTermino()));
            stmt.setString(4, atividade.getPrioridade().name());

            String status = (atividade.getStatus() != null) ? atividade.getStatus().name() : Status.EM_ANDAMENTO.name();
            stmt.setString(5, status);

            if (atividade.getFuncionario() != null) {
                stmt.setInt(6, atividade.getFuncionario().getId());
            } else {
                stmt.setNull(6, Types.INTEGER);
            }

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Atividade buscarAtividadePorId(int id) {
        String sql = "SELECT * FROM atividades WHERE id = ?";
        Atividade atividade = null;

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                atividade = new Atividade(
                        rs.getInt("id"),
                        rs.getString("descricao"),
                        rs.getDate("data_inicio").toLocalDate(),
                        rs.getDate("data_termino").toLocalDate(),
                        Prioridade.valueOf(rs.getString("prioridade")),
                        Status.valueOf(rs.getString("status")),
                        obterFuncionarioPorId(rs.getInt("funcionario_id"))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return atividade;
    }

    @Override
    public Atividade obterAtividadePorId(int id) throws SQLException {
        String query = "SELECT * FROM atividade WHERE id = ?";
        Atividade atividade = null;

        try (PreparedStatement stmt = conexao.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                atividade = new Atividade();
                atividade.setId(rs.getInt("id"));
                atividade.setDescricao(rs.getString("descricao"));
                atividade.setDataInicio(rs.getDate("data_inicio").toLocalDate());
                atividade.setDataTermino(rs.getDate("data_termino").toLocalDate());
                atividade.setPrioridade(Prioridade.valueOf(rs.getString("prioridade")));
                atividade.setStatus(Status.valueOf(rs.getString("status")));
                Funcionario funcionario = new Funcionario();
                funcionario.setId(rs.getInt("funcionario_id"));
                atividade.setFuncionario(funcionario);
            }
        }

        return atividade;
    }

    @Override
    public List<Atividade> listarTodasAtividades() {
        String sql = "SELECT * FROM atividades";
        List<Atividade> atividades = new ArrayList<>();

        try (Statement stmt = conexao.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Atividade atividade = new Atividade(
                        rs.getInt("id"),
                        rs.getString("descricao"),
                        rs.getDate("data_inicio").toLocalDate(),
                        rs.getDate("data_termino").toLocalDate(),
                        Prioridade.valueOf(rs.getString("prioridade")),
                        Status.valueOf(rs.getString("status")),
                        obterFuncionarioPorId(rs.getInt("funcionario_id"))
                );
                atividades.add(atividade);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return atividades;
    }

    @Override
    public List<Atividade> listarAtividadesPorStatus(String status) {
        String sql = "SELECT * FROM atividades WHERE status = ?";
        List<Atividade> atividades = new ArrayList<>();

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Atividade atividade = new Atividade(
                        rs.getInt("id"),
                        rs.getString("descricao"),
                        rs.getDate("data_inicio").toLocalDate(),
                        rs.getDate("data_termino").toLocalDate(),
                        Prioridade.valueOf(rs.getString("prioridade")),
                        Status.valueOf(rs.getString("status")),
                        obterFuncionarioPorId(rs.getInt("funcionario_id"))
                );
                atividades.add(atividade);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return atividades;
    }

    @Override
    public void atualizarAtividade(Atividade atividade) {
        String sql = "UPDATE atividades SET descricao = ?, data_inicio = ?, data_termino = ?, prioridade = ?, status = ?, funcionario_id = ? WHERE id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, atividade.getDescricao());
            stmt.setDate(2, Date.valueOf(atividade.getDataInicio()));
            stmt.setDate(3, Date.valueOf(atividade.getDataTermino()));
            stmt.setString(4, atividade.getPrioridade().name());
            stmt.setString(5, atividade.getStatus().name());

            if (atividade.getFuncionario() != null) {
                stmt.setInt(6, atividade.getFuncionario().getId());
            } else {
                stmt.setNull(6, Types.INTEGER);
            }

            stmt.setInt(7, atividade.getId());
            stmt.executeUpdate();
            System.out.println("Atividade atualizada com sucesso.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void editarAtividade(Atividade atividade) throws SQLException {
        String query = "UPDATE atividades SET descricao = ?, data_inicio = ?, data_termino = ?, prioridade = ?, status = ?, funcionario_id = ? WHERE id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(query)) {
            stmt.setString(1, atividade.getDescricao());
            stmt.setDate(2, Date.valueOf(atividade.getDataInicio()));
            stmt.setDate(3, Date.valueOf(atividade.getDataTermino()));
            stmt.setString(4, atividade.getPrioridade().name());
            stmt.setString(5, atividade.getStatus().name());
            stmt.setInt(6, atividade.getFuncionario().getId());
            stmt.setInt(7, atividade.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void removerAtividade(int id) {
        String sql = "DELETE FROM atividades WHERE id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void atribuirAtividadeFuncionario(int atividadeId, int funcionarioId) {
        String sql = "UPDATE atividades SET funcionario_id = ? WHERE id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, funcionarioId);
            stmt.setInt(2, atividadeId);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Atividade atribuída ao funcionário com sucesso.");
            } else {
                System.out.println("Falha ao atribuir atividade ao funcionário.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Atividade> obterAtividadesPorStatus(String status, int funcionarioId) {
        String sql = "SELECT * FROM atividades WHERE status = ? AND funcionario_id = ?";
        List<Atividade> atividades = new ArrayList<>();

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, funcionarioId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Atividade atividade = new Atividade(
                        rs.getInt("id"),
                        rs.getString("descricao"),
                        rs.getDate("data_inicio").toLocalDate(),
                        rs.getDate("data_termino").toLocalDate(),
                        Prioridade.valueOf(rs.getString("prioridade")),
                        Status.valueOf(rs.getString("status")),
                        obterFuncionarioPorId(rs.getInt("funcionario_id"))
                );
                atividades.add(atividade);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return atividades;
    }

    @Override
    public List<Atividade> obterAtividadesPorFuncionario(int funcionarioId) {
        String sql = "SELECT * FROM atividades WHERE funcionario_id = ?";
        List<Atividade> atividades = new ArrayList<>();

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
                        obterFuncionarioPorId(rs.getInt("funcionario_id"))
                );
                atividades.add(atividade);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return atividades;
    }

    public List<Atividade> obterAtividadesPorFuncionarioAndamento(int funcionarioId) throws SQLException {
        String query = "SELECT * FROM atividades WHERE funcionario_id = ? AND status != 'CONCLUIDA'";
        List<Atividade> atividades = new ArrayList<>();
        try (PreparedStatement stmt = conexao.prepareStatement(query)) {
            stmt.setInt(1, funcionarioId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Atividade atividade = new Atividade();
                atividade.setId(rs.getInt("id"));
                atividade.setDescricao(rs.getString("descricao"));
                atividade.setStatus(Status.valueOf(rs.getString("status")));
                atividades.add(atividade);
            }
        }
        return atividades;
    }


    @Override
    public void atualizarStatusAtividade(int id, Status novoStatus) {
        String sql = "UPDATE atividades SET status = ? WHERE id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, novoStatus.name());
            stmt.setInt(2, id);

            stmt.executeUpdate();
            System.out.println("Status da atividade atualizado com sucesso.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Funcionario obterFuncionarioPorId(int funcionarioId) {
        String sql = "SELECT * FROM funcionarios WHERE id = ?";
        Funcionario funcionario = null;

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, funcionarioId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                funcionario = new Funcionario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getString("cargo"),
                        rs.getBoolean("is_manager")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return funcionario;
    }

    @Override
    public void marcarComoConcluida(int idAtividade) {
        String sql = "UPDATE atividades SET status = ? WHERE id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, Status.CONCLUIDA.name());
            stmt.setInt(2, idAtividade);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Atividade marcada como concluída com sucesso.");
            } else {
                System.out.println("Nenhuma atividade encontrada com o ID fornecido.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
