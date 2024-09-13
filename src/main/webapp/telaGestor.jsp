<%@ page import="java.util.List" %>
<%@ page import="com.example.gestordeequipasee.model.Funcionario" %>
<%@ page import="com.example.gestordeequipasee.model.Atividade" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tela do Gestor</title>
    <link rel="stylesheet" href="estilos/gestor.css">
    <script src="scripts/gestor.js" defer></script>
</head>
<body>
<div class="container">
    <h1>Painel do Gestor</h1>

    <div class="layout">
        <!-- Coluna direita: Lista de funcionários -->
        <div class="coluna-funcionarios">
            <h3>Funcionários</h3>
            <ul id="lista-funcionarios">
                <%
                    List<Funcionario> funcionarios = (List<Funcionario>) request.getAttribute("funcionarios");
                    if (funcionarios != null) {
                        for (Funcionario funcionario : funcionarios) {
                %>
                <li class="funcionario-item" data-id="<%= funcionario.getId() %>">
                    <span><%= funcionario.getNome() %></span>
                </li>
                <%
                    }
                } else {
                %>
                <li>Nenhum funcionário encontrado.</li>
                <%
                    }
                %>
            </ul>
        </div>

        <!-- Coluna central: Lista de atividades do funcionário selecionado -->
        <div class="coluna-atividades">
            <h3>Atividades de <span id="nome-funcionario">Funcionário</span></h3>
            <ul id="lista-atividades"></ul>

            <!-- Botões para adicionar, remover e editar atividades -->
            <div class="botoes-atividades">
                <button id="btn-adicionar" onclick="abrirAdicionarAtividade()">Adicionar</button>
                <button id="btn-editar" onclick="abrirEditarAtividade()" disabled>Editar</button>
                <button id="btn-remover" onclick="removerAtividade()" disabled>Remover</button>
            </div>
        </div>

        <!-- Modal para adicionar/editar atividades -->
        <div id="modal-atividade" class="modal">
            <div class="modal-content">
                <span class="close">&times;</span>
                <h2 id="modal-titulo">Adicionar Atividade</h2>
                <form id="form-atividade">
                    <input type="hidden" id="atividade-id" name="id">
                    <label for="descricao">Descrição:</label>
                    <input type="text" id="descricao" name="descricao" required>
                    <label for="prioridade">Prioridade:</label>
                    <select id="prioridade" name="prioridade">
                        <option value="ALTA">Alta</option>
                        <option value="MEDIA">Média</option>
                        <option value="BAIXA">Baixa</option>
                    </select>
                    <label for="dataInicio">Data de Início:</label>
                    <input type="date" id="dataInicio" name="dataInicio" required>
                    <label for="dataTermino">Data de Término:</label>
                    <input type="date" id="dataTermino" name="dataTermino" required>
                    <input type="hidden" id="funcionario-id" name="funcionarioId">
                    <button type="submit">Salvar</button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
