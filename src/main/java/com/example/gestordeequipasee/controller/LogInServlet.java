package com.example.gestordeequipasee.controller;

import com.example.gestordeequipasee.dao.DatabaseConnection;
import com.example.gestordeequipasee.dao.FuncionarioDAO;
import com.example.gestordeequipasee.dao.FuncionarioDAOImpl;
import com.example.gestordeequipasee.model.Funcionario;
import com.example.gestordeequipasee.utils.PasswordUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(name = "logInServlet", urlPatterns = {"/login"})
public class LogInServlet extends HttpServlet {

    private FuncionarioDAO funcionarioDAO;
    private Connection connection;

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            Funcionario usuario = autenticarUsuario(email, password);
            if (usuario != null) {
                // Armazena o usuário autenticado na sessão
                HttpSession session = request.getSession();
                session.setAttribute("usuarioLogado", usuario);

                // Redireciona com base no tipo de usuário
                if (usuario.isManager()) {
                    abrirTelaGerente(request, response);
                } else {
                    abrirTelaFuncionario(request, response);
                }
            } else {
                // Login falhou, redireciona de volta para a página de login com uma mensagem de erro
                request.setAttribute("erroLogin", "Email ou senha incorretos.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Erro ao autenticar o usuário", e);
        }
    }

    private Funcionario autenticarUsuario(String email, String password) throws SQLException {
        Funcionario usuario = funcionarioDAO.buscarFuncionarioPorEmail(email);
        if (usuario != null && PasswordUtil.checkPassword(password, usuario.getSenha())) {
            return usuario;
        }
        return null;
    }

    private void abrirTelaFuncionario(HttpServletRequest request, HttpServletResponse response) throws IOException {


        response.sendRedirect("telaFuncionario");
    }

    private void abrirTelaGerente(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("telaGestor");
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
