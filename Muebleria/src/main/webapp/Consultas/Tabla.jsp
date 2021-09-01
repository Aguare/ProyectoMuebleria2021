<%-- 
    Document   : Tabla
    Created on : 31/08/2021, 23:36:29
    Author     : marco
--%>

<%@page import="EntidadesFabrica.Usuario"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tabla</title>
        <jsp:include page="../resources/CSS/RecursosCSS.jsp"></jsp:include>
        <jsp:include page="../resources/JS/RecursosJS.jsp"></jsp:include>
        <jsp:include page="../resources/JS/RecursosJS.jsp"></jsp:include>
        <style><%@include file="../resources/CSS/barraMenu.css"%></style>
    </head>
    <body>
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
        <%
            String titulo = String.valueOf(request.getAttribute("titulo"));
            String subtitulo = String.valueOf(request.getAttribute("subtitulo"));
            ArrayList<String> titulos = (ArrayList<String>) request.getAttribute("titulos");
            ArrayList<String[]> contenido = (ArrayList<String[]>) request.getAttribute("contenido");
        %>
        <div class="container">
            <h2 class="text-center"><%=titulo%></h2>
            <p class="text-center"><%=subtitulo%></p>
            <br>
            <div class="table-responsive" style="height: 670px">
                <table class="table table-bordered">
                    <thead class="thead-dark">
                        <tr>
                            <c:forEach items="${titulos}" var="titulo">
                                <th class="text-center">${titulo}</th>
                                </c:forEach>
                        </tr>
                    </thead>
                    <tbody>
                        <%for (String[] linea : contenido) {%>
                        <tr>
                            <%for (int i = 0; i < linea.length; i++) {%>
                            <td class="text-center"><%=linea[i]%></td>
                            <%}%>
                        </tr>
                        <%}%>
                    </tbody>
                </table>
            </div>
    </body>
</html>
