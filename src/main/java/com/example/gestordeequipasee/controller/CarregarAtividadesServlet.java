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

@WebServlet(name = "CarregarAtividadesServlet", urlPatterns = {"/carregarAtividades"})
public class CarregarAtividadesServlet extends HttpServlet {

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int funcionarioId = Integer.parseInt(request.getParameter("funcionarioId"));

        // Obter as atividades do funcionário
        List<Atividade> atividades = atividadeDAO.obterAtividadesPorFuncionario(funcionarioId);

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Gerar o HTML da lista de atividades
        for (Atividade atividade : atividades) {
            out.println("<li class='atividade-item' data-id='" + atividade.getId() + "'>");
            out.println("<span>Descrição: " + atividade.getDescricao() + "</span>");
            out.println("<span>Status: " + atividade.getStatus() + "</span>");
            out.println("</li>");
        }

        if (atividades.isEmpty()) {
            out.println("<li>Nenhuma atividade encontrada.</li>");
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
