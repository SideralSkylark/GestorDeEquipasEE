package com.example.gestordeequipasee.controller;

import com.example.gestordeequipasee.dao.FuncionarioDAO;
import com.example.gestordeequipasee.dao.FuncionarioDAOImpl;
import com.example.gestordeequipasee.dao.DatabaseConnection;
import com.example.gestordeequipasee.model.Funcionario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "GestorServlet", urlPatterns = {"/telaGestor"})
public class GestorServlet extends HttpServlet {

    private Connection connection;
    private FuncionarioDAO funcionarioDAO;

    @Override
    public void init() {
        try {
            connection = DatabaseConnection.getConnection();
            funcionarioDAO = new FuncionarioDAOImpl(connection);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Obter a lista de funcionários que não são gerentes
            List<Funcionario> funcionarios = funcionarioDAO.obterFuncionariosNaoGerentes();

            // Definir a lista de funcionários como um atributo da requisição
            request.setAttribute("funcionarios", funcionarios);

            // Encaminhar para o JSP da tela do gestor
            request.getRequestDispatcher("telaGestor.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Erro ao obter a lista de funcionários", e);
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
