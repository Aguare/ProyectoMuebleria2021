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
        <br><br>
        <div class="container text-center">
            <%
                String mensaje = String.valueOf(request.getAttribute("mensaje"));
                String mensaje2 = String.valueOf(request.getAttribute("mensaje2"));
                int color = (Integer) request.getAttribute("color");
            %>
            <%
                switch (color) {
                    case 1:
            %>
            <div class="alert alert-success">
                <strong><%=mensaje%> </strong><%=mensaje2%><a class="alert-link"></a>.
            </div>
            <%
                    break;
                case 2:
            %>
            <div class="alert alert-warning">
                <strong><%=mensaje%> </strong><%=mensaje2%><a class="alert-link"></a>.
            </div>
            <%
                    break;
                default:
            %>
            <div class="alert alert-info">
                <strong><%=mensaje%> </strong><%=mensaje2%><a class="alert-link"></a>.
            </div>
            <%
                        break;
                }
            %>
        </div>
    </body>
</html>
