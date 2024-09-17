<%@ page import="java.util.List" %>
<%@ page import="com.example.gestordeequipasee.model.Funcionario" %>
<%@ page import="com.example.gestordeequipasee.model.Atividade" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Painel do Gestor</title>
    <style>
        /* Importando uma fonte divertida */
        @import url('https://fonts.googleapis.com/css2?family=Quicksand:wght@400;600&display=swap');

        /* Estilizando o corpo da página */
        body {
            background-color: #f7f7f7;
            color: #333;
            font-family: 'Quicksand', sans-serif;
            margin: 0;
            padding: 0;
        }

        /* Container principal */
        .container {
            display: flex;
            flex-direction: column;
            align-items: center;
            width: 100%;
            padding: 20px;
            background-color: #ffffff;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        /* Cabeçalho */
        h1 {
            background-color: #007BFF; /* Azul para o fundo do cabeçalho */
            color: #ffffff;
            padding: 10px 20px;
            border-radius: 5px;
            margin: 0;
            width: 100%;
            text-align: center;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }

        /* Layout geral */
        .layout {
            display: flex;
            width: 100%;
            margin-top: 20px;
        }

        #lista-dashboard {
            list-style-type: none;
            padding: 0;
            margin: 0;
        }
        /* Coluna do dashboard */
        .coluna-dashboard {
            flex: 0.2;
            background-color: #007BFF; /* Azul para o fundo do dashboard */
            color: #ffffff;
            padding: 0px 20px;
            border-radius: 5px;
            box-shadow: 2px 2px 8px rgba(0, 0, 0, 0.1);
            margin-right: 15px;
        }

        .dashboard-item {
            padding: 15px;
            margin-bottom: 10px;
            margin-left: auto;

            cursor: pointer;
            transition: background-color 0.3s ease;
            font-size: 16px;
            border-radius: 5px;
            background-color: cornflowerblue; /* Cinza escuro para o fundo dos itens */
        }

        .ativo {
            background: darkblue;
        }

        /* Coluna de funcionários */
        .coluna-funcionarios {
            flex: 0.3;
            border-right: 2px solid #ddd;
            padding-right: 20px;
            padding-left: 20px;
            background-color: #343a40; /* Cinza escuro para o fundo */
            color: #ffffff; /* Letras brancas */
            border-radius: 5px;
            box-shadow: 2px 0 5px rgba(0, 0, 0, 0.1);
            max-width: 300px; /* Limite máximo de largura */
            overflow-y: auto; /* Adiciona rolagem se necessário */
        }

        #lista-funcionarios {
            list-style-type: none;
            padding: 0;
            margin: 0;
        }

        .funcionario-item {
            padding: 15px;
            margin-bottom: 10px;
            cursor: pointer;
            transition: background-color 0.3s ease;
            font-size: 16px;
            border-radius: 5px;
            background-color: #495057; /* Cinza escuro para o fundo dos itens */
        }

        .funcionario-item:hover {
            background-color: #6c757d; /* Cinza mais claro ao passar o mouse */
        }

        /* Coluna de atividades */
        .coluna-atividades {
            flex: 1.5;
            padding-left: 20px;
            background-color: #f9f9f9;
            border-radius: 4px;
            box-shadow: 2px 2px 8px rgba(0, 0, 0, 0.1);
        }

        #lista-atividades {
            list-style-type: none;
            padding: 0;
        }

        .atividade-item {
            padding: 15px;
            border-bottom: 1px solid #ddd;
            transition: background-color 0.3s ease;
        }

        .atividade-item:hover {
            background-color: #f0f0f0;
        }

        /* Botões */
        .botoes-atividades {
            margin-top: 20px;
            display: flex;
            gap: 10px;
        }

        .botoes-atividades button {
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            color: #ffffff;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        .botoes-atividades #btn-adicionar {
            background-color: #28a745; /* Verde para adicionar */
        }

        .botoes-atividades #btn-adicionar:hover {
            background-color: #218838;
        }

        .botoes-atividades #btn-editar {
            background-color: #ffc107; /* Amarelo para editar */
        }

        .botoes-atividades #btn-editar:hover {
            background-color: #e0a800;
        }

        .botoes-atividades #btn-remover {
            background-color: #dc3545; /* Vermelho para remover */
        }

        .botoes-atividades #btn-remover:hover {
            background-color: #c82333;
        }

        /* Modal */
        .modal {
            display: none;
            position: fixed;
            z-index: 1;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgba(0, 0, 0, 0.4);
        }

        .modal-content {
            background-color: #ffffff;
            margin: 15% auto;
            padding: 20px;
            border: 1px solid #ddd;
            width: 80%;
            max-width: 600px;
            border-radius: 5px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        .close {
            color: #aaa;
            float: right;
            font-size: 28px;
            font-weight: bold;
        }

        .close:hover,
        .close:focus {
            color: black;
            text-decoration: none;
            cursor: pointer;
        }

        /* Estilo para o formulário do modal */
        .modal-content form {
            display: flex;
            flex-direction: column;
            gap: 15px;
        }

        .modal-content label {
            font-weight: 600;
        }

        .modal-content input[type="text"],
        .modal-content input[type="date"],
        .modal-content select {
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 16px;
            width: 100%;
            box-sizing: border-box;
        }

        .modal-content button {
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            color: #ffffff;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s ease;
            background-color: #007BFF; /* Azul para o botão de salvar */
        }

        .modal-content button:hover {
            background-color: #0056b3;
        }
    </style>
    <script src="scripts/gestor.js" defer></script>
</head>
<body>
<div class="container">
    <h1>Painel do Gestor</h1>

    <div class="layout">
        <!-- Coluna esquerda: Dashboard -->
        <div class="coluna-dashboard">
            <h3>Dashboard</h3>
            <ul id="lista-dashboard">
                <li class="dashboard-item ativo">Geral</li>
                <li class="dashboard-item">Relatório</li>
                <li class="dashboard-item">Funcionários</li>
                <li class="dashboard-item" onclick="window.location.href='logout'">Sair</li>
            </ul>
        </div>

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
