package com.example.gestordeequipasee.controller;

import com.example.gestordeequipasee.dao.AtividadeDAO;
import com.example.gestordeequipasee.dao.AtividadeDAOImpl;
import com.example.gestordeequipasee.dao.DatabaseConnection;
import com.example.gestordeequipasee.model.Atividade;
import com.example.gestordeequipasee.model.Funcionario;
import com.example.gestordeequipasee.model.Prioridade;
import com.example.gestordeequipasee.model.Status;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Date;

@WebServlet(name = "EditarAtividadeServlet", urlPatterns = {"/editarAtividade"})
public class EditarAtividadeServlet extends HttpServlet {

    private AtividadeDAO atividadeDAO;
    private Connection connection;

    @Override
    public void init() {
        try {
            connection = DatabaseConnection.getConnection();
            atividadeDAO = new AtividadeDAOImpl(connection);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String descricao = request.getParameter("descricao");
        Date dataInicio = Date.valueOf(request.getParameter("dataInicio"));
        Date dataTermino = Date.valueOf(request.getParameter("dataTermino"));
        String prioridade = request.getParameter("prioridade");
        String status = request.getParameter("status");
        int funcionarioId = Integer.parseInt(request.getParameter("funcionarioId"));

        Atividade atividade = new Atividade();
        atividade.setId(id);
        atividade.setDescricao(descricao);
        atividade.setDataInicio(dataInicio.toLocalDate());
        atividade.setDataTermino(dataTermino.toLocalDate());
        atividade.setPrioridade(Prioridade.valueOf(prioridade));
        atividade.setStatus(Status.valueOf("EM_ANDAMENTO"));
        Funcionario funcionario = new Funcionario();
        funcionario.setId(funcionarioId);
        atividade.setFuncionario(funcionario);

        try {
            atividadeDAO.editarAtividade(atividade);
            response.sendRedirect("telaGestor");  // Redireciona de volta para a tela do gestor
        } catch (SQLException e) {
            throw new ServletException("Erro ao editar a atividade", e);
        }
    }

    @Override
    public void destroy() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
