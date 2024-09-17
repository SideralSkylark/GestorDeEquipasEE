package com.example.gestordeequipasee.controller;

import com.example.gestordeequipasee.dao.AtividadeDAO;
import com.example.gestordeequipasee.dao.AtividadeDAOImpl;
import com.example.gestordeequipasee.dao.DatabaseConnection;
import com.example.gestordeequipasee.model.Atividade;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(name = "ObterAtividadeServlet", urlPatterns = {"/obterAtividade"})
public class ObterAtividadeServlet extends HttpServlet {

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
        int id = Integer.parseInt(request.getParameter("id"));
        try {
            Atividade atividade = atividadeDAO.obterAtividadePorId(id);
            if (atividade != null) {
                Gson gson = new Gson();
                String atividadeJson = gson.toJson(atividade);
                response.setContentType("application/json");
                response.getWriter().write(atividadeJson);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Atividade não encontrada");
            }
        } catch (SQLException e) {
            throw new ServletException("Erro ao obter a atividade", e);
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
