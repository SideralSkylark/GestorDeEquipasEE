package com.example.gestordeequipasee.controller;

import com.example.gestordeequipasee.dao.AtividadeDAO;
import com.example.gestordeequipasee.dao.AtividadeDAOImpl;
import com.example.gestordeequipasee.dao.DatabaseConnection;
import com.example.gestordeequipasee.model.Atividade;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "MarcarConcluidaServlet", urlPatterns = {"/marcarConcluida"})
public class MarcarConcluidaServlet extends HttpServlet {

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
        int idAtividade = Integer.parseInt(request.getParameter("idAtividade"));

        // Marcar atividade como concluída
        atividadeDAO.marcarComoConcluida(idAtividade);

        // Obter a lista atualizada de atividades do funcionário
        int funcionarioId = Integer.parseInt(request.getParameter("funcionarioId")); // Passe o ID do funcionário
        List<Atividade> atividades = atividadeDAO.obterAtividadesPorFuncionario(funcionarioId);

        // Atualizar a lista de atividades na resposta
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Gera o novo HTML com as atividades atualizadas
        for (Atividade atividade : atividades) {
            out.println("<li class='atividade'>");
            out.println("<div class='detalhes-atividade'>");
            out.println("<span style='display:none;' data-id='" + atividade.getId() + "'></span>");
            out.println("<span>Descrição: " + atividade.getDescricao() + "</span>");
            out.println("<span>Status: " + atividade.getStatus() + "</span>");
            out.println("</div>");
            out.println("<button class='btn-concluir' " +
                    ("CONCLUIDA".equals(atividade.getStatus().name()) ? "disabled" : "") +
                    " onclick='marcarConcluida(" + atividade.getId() + ")'>Concluir</button>");
            out.println("</li>");
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