<%@ page import="java.util.List" %>
<%@ page import="com.example.gestordeequipasee.model.Atividade" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tela do Funcionário</title>
    <link rel="stylesheet" href="estilos/funcionario.css">
    <script>
        function marcarConcluida(atividadeId) {
            const funcionarioId = <%= request.getAttribute("funcionarioId") %>; // Pegue o ID do funcionário do servidor

            const xhr = new XMLHttpRequest();
            xhr.open('POST', 'marcarConcluida', true); // Endpoint para marcar como concluída
            xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

            xhr.onreadystatechange = function () {
                if (xhr.readyState == 4 && xhr.status == 200) {
                    // Atualizar a lista de atividades
                    document.getElementById('lista-atividades').innerHTML = xhr.responseText;
                }
            };

            // Envia o ID da atividade e do funcionário
            xhr.send('idAtividade=' + atividadeId + '&funcionarioId=' + funcionarioId);
        }
    </script>
</head>
<body>
<div class="container">
    <h1>Bem-vindo, <%= request.getAttribute("nomeFuncionario") %></h1>
    <p>Email: <%= request.getAttribute("emailFuncionario") %></p>

    <h2>Atividades Atribuídas</h2>
    <ul id="lista-atividades" class="lista-atividades">
        <%
            List<Atividade> atividades = (List<Atividade>) request.getAttribute("atividades");
            if (atividades != null) {
                for (Atividade atividade : atividades) {
        %>
        <li class="atividade">
            <div class="detalhes-atividade">
                <!-- ID oculto, mas presente no atributo data -->
                <span style="display:none;" data-id="<%= atividade.getId() %>"></span>
                <span>Descrição: <%= atividade.getDescricao() %></span>
                <span>Status: <%= atividade.getStatus() %></span>
            </div>
            <button class="btn-concluir"
                    <%= "CONCLUIDA".equals(atividade.getStatus().name()) ? "disabled" : "" %>
                    onclick="marcarConcluida(<%= atividade.getId() %>)">
                Concluir
            </button>
        </li>
        <%
                }
            } else {
                out.println("<li>Nenhuma atividade encontrada.</li>");
            }
        %>
    </ul>
</div>
</body>
</html>
