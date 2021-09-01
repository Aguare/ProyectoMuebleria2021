<%-- 
    Document   : SinAcceso
    Created on : 26/08/2021, 23:01:45
    Author     : marco
--%>

<%@page import="EntidadesFabrica.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error</title>
    </head>
    <body>
        <style><%@include file="../resources/CSS/RecursosCSS.jsp"%></style>
        <style><%@include file="../resources/JS/RecursosJS.jsp"%></style>
        <% int tipo = 0;
            if (request.getSession() != null) {
                tipo = ((Usuario) request.getSession().getAttribute("Usuario")).getIdDepartamento();
            }
        %>
        <%switch (tipo) {
                    case 1:%>
        <jsp:include page="../Menus/Fabrica.jsp"></jsp:include>
        <% break;
                case 2:%>
        <jsp:include page="../Menus/Venta.jsp"></jsp:include>
        <%break;
                case 3:%>
        <jsp:include page="../Menus/Financiero.jsp"></jsp:include>
        <%break;
                default:%>
        <jsp:include page="../Menus/Principal.jsp"></jsp:include>
        <%break;
            }
        %>
        <style><%@include file="../resources/CSS/barraMenu.css"%></style>
        <% String mensaje = String.valueOf(request.getAttribute("mensaje"));%>
        <div class="container p-3 my-3 bg-primary text-white">
            <center><h1>ERROR:</h1></center>
            <center><h2><%=mensaje%></h2></center>
        </div>
        <%if (request.getAttribute("cargaArchivo") != null) {%>
        <div class="container my-3 border text-center">
            <a href="${pageContext.request.contextPath}/Inicio/CargaArchivo.jsp" type="button" class="btn btn-link text-center">Reintentar Carga de Archivos</a>
        </div>
        <%}%>  
    </body>
</html>
