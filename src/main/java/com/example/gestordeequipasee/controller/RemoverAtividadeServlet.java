package com.example.gestordeequipasee.controller;

import com.example.gestordeequipasee.dao.AtividadeDAO;
import com.example.gestordeequipasee.dao.AtividadeDAOImpl;
import com.example.gestordeequipasee.dao.DatabaseConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(name = "RemoverAtividadeServlet", urlPatterns = {"/removerAtividade"})
public class RemoverAtividadeServlet extends HttpServlet {

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

        atividadeDAO.removerAtividade(id);
        response.sendRedirect("telaGestor");  // Redireciona de volta para a tela do gestor
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
