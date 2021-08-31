<%-- 
    Document   : Principal
    Created on : 23/08/2021, 00:54:34
    Author     : aguare
--%>

<%@page import="EntidadesFabrica.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Bienvenido</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="../resources/CSS/barraMenu.css">
    <script src="../resources/JS/menu.js"></script>
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
    <style><%@include file="../resources/CSS/barraMenu.css"%></style>
    <%
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setDateHeader("Expires", -1);
        if (request.getSession().getAttribute("Usuario") == null) {
            request.getRequestDispatcher("../index.jsp").forward(request, response);
        } else {
            Usuario usuario = (Usuario) request.getSession().getAttribute("Usuario");
    %>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a href="${pageContext.request.contextPath}/Menus/Financiero.jsp" class="navbar-brand"><b>FINANCIERO</b></a>  		
    <button type="button" class="navbar-toggler" data-toggle="collapse" data-target="#navbarCollapse">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div id="navbarCollapse" class="collapse navbar-collapse justify-content-start">
        <div class="navbar-nav">
            <a href="${pageContext.request.contextPath}/Menus/Financiero.jsp" class="nav-item nav-link">Inicio</a>		
            <div class="nav-item dropdown">
                <a href="#" data-toggle="dropdown" class="nav-item nav-link dropdown-toggle">Usuarios</a>
                <div class="dropdown-menu">					
                    <a href="#" class="dropdown-item">Crear</a>
                    <a href="#" class="dropdown-item">Ver Todos</a>
                </div>
            </div>
            <div class="nav-item dropdown">
                <a href="#" data-toggle="dropdown" class="nav-item nav-link dropdown-toggle">Muebles</a>
                <div class="dropdown-menu">					
                    <a href="#" class="dropdown-item">Ver Tipo de Muebles</a>
                    <a href="#" class="dropdown-item">Crear Tipo de Mueble</a>
                </div>
            </div>
            <div class="nav-item dropdown">
                <a href="#" data-toggle="dropdown" class="nav-item nav-link dropdown-toggle">Reportes</a>
                <div class="dropdown-menu">					
                    <a href="#" class="dropdown-item">Ventas</a>
                    <a href="#" class="dropdown-item">Ganancias</a>
                    <a href="#" class="dropdown-item">Devoluciones</a>
                    <a href="#" class="dropdown-item">Usuario con más Ventas</a>
                    <a href="#" class="dropdown-item">Mueble con más Ventas</a>
                    <a href="#" class="dropdown-item">Mueble con menos Ventas</a>
                </div>
            </div>
        </div>
        <div class="navbar-nav action-buttons ml-auto">
            <a href="#" data-toggle="dropdown" class="navbar-nav mr-3"><%=usuario.getNombre_usuario()%></a>
            <div class="dropdown-menu login-form">
                <form action="${pageContext.request.contextPath}/Sesion" method="GET">
                    <input type="submit" class="btn btn-primary btn-block" value="Cerrar Sesión">
                </form>					
            </div>			
        </div>
    </div>
</nav>
<%}%>
</head>
<body>
</body>
</html>
