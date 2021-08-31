<%-- 
    Document   : Principal
    Created on : 23/08/2021, 00:54:34
    Author     : aguare
--%>

<%@page import="EntidadesFabrica.Usuario"%>
<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Bienvenido</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="../resources/CSS/barraMenu.css">
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
    <a href="${pageContext.request.contextPath}/Menus/Fabrica.jsp" class="navbar-brand"><b>F�BRICA</b></a>  		
    <button type="button" class="navbar-toggler" data-toggle="collapse" data-target="#navbarCollapse">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div id="navbarCollapse" class="collapse navbar-collapse justify-content-start">
        <div class="navbar-nav">
            <a href="${pageContext.request.contextPath}/Menus/Fabrica.jsp" class="nav-item nav-link">Inicio</a>
            <a href="#" class="nav-item nav-link">Ensamblar</a>			
            <div class="nav-item dropdown">
                <a href="#" data-toggle="dropdown" class="nav-item nav-link dropdown-toggle">Piezas</a>
                <div class="dropdown-menu">					
                    <a href="${pageContext.request.contextPath}/Consultas/Fabrica/EditarPieza.jsp?crear=true" class="dropdown-item">Crear</a>
                    <a href="${pageContext.request.contextPath}/Consultas/Fabrica/Piezas.jsp" class="dropdown-item">Ver Todas</a>
                    <a href="#" class="dropdown-item">Tipo de Piezas</a>
                </div>
            </div>
            <div class="nav-item dropdown">
                <a href="#" data-toggle="dropdown" class="nav-item nav-link dropdown-toggle">Ensambles</a>
                <div class="dropdown-menu">					
                    <a href="#" class="dropdown-item">Muebles Ensamblados</a>
                    <a href="#" class="dropdown-item">Registrar en Ventas</a>
                </div>
            </div>
            <a href="#" class="nav-item nav-link">Devoluciones</a>
        </div>
        <div class="navbar-nav action-buttons ml-auto">
            <a href="#" data-toggle="dropdown" class="navbar-nav mr-3"><%=usuario.getNombre_usuario()%></a>
            <div class="dropdown-menu login-form">
                <form action="${pageContext.request.contextPath}/Sesion" method="GET">
                    <input type="submit" class="btn btn-primary btn-block" value="Cerrar Sesi�n">
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
