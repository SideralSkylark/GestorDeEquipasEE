<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
    <div class="container">
        <h1> <%= "Gestor de equipes"%> </h1>
        <div class="seccao-login">
            <form action="logar" method="post">
                <label>Log in</label><br>
                <label>Usuario:</label>
                <input type="text" name="usuario"><br>
                <label>Senha:</label>
                <input type="password" name="senha"><br>
                <input type="submit">
            </form>
        </div>
    </div>
</body>
</html>