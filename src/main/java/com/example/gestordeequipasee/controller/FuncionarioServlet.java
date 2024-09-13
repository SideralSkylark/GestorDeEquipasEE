package com.example.gestordeequipasee.controller;

import com.example.gestordeequipasee.dao.AtividadeDAO;
import com.example.gestordeequipasee.dao.AtividadeDAOImpl;
import com.example.gestordeequipasee.dao.DatabaseConnection;
import com.example.gestordeequipasee.model.Atividade;
import com.example.gestordeequipasee.model.Funcionario;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "FuncionarioServlet", urlPatterns = {"/telaFuncionario"})
public class FuncionarioServlet extends HttpServlet {

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
        HttpSession session = request.getSession();
        Funcionario funcionario = (Funcionario) session.getAttribute("usuarioLogado");

        if (funcionario != null) {
            List<Atividade> atividades;
            try {
                atividades = atividadeDAO.obterAtividadesPorFuncionarioAndamento(funcionario.getId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Passar dados para a página JSP
            request.setAttribute("nomeFuncionario", funcionario.getNome());
            request.setAttribute("emailFuncionario", funcionario.getEmail());
            request.setAttribute("atividades", atividades);

            request.getRequestDispatcher("telaFuncionario.jsp").forward(request, response);
        } else {
            response.sendRedirect("login.jsp");
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
