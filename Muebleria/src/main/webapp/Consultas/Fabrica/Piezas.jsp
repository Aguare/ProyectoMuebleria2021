<%-- 
    Document   : Piezas
    Created on : 29/08/2021, 21:40:10
    Author     : marco
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="EntidadesFabrica.Pieza"%>
<%@page import="java.util.ArrayList"%>
<%@page import="SQL.ObtenerObj"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Piezas</title>
    </head>
    <body>
        <jsp:include page="../../Menus/Fabrica.jsp" flush="false"></jsp:include>
        <jsp:include page="../../resources/CSS/RecursosCSS.jsp" flush="false"></jsp:include>
        <jsp:include page="../../resources/JS/RecursosJS.jsp" flush="false"></jsp:include>
        <jsp:include page="../../resources/JS/RecursosJS.jsp" flush="false"></jsp:include>
        <% ObtenerObj obtener = new ObtenerObj();
            ArrayList<Pieza> piezas = obtener.obtenerPiezas();
        %>
        <br>
        <div class="container">
            <h2 class="text-center">INVENTARIO DE PIEZAS</h2>
            <p class="text-center">Aquí se encuentran todas las piezas disponibles para el ensamblado de un mueble</p>
            <table class="table table-bordered">
                <thead class="thead-dark">
                    <tr>
                        <th class="text-center">ID</th>
                        <th>TIPO DE PIEZA</th>
                        <th class="text-center">PRECIO</th>
                        <th class="text-center">OPCIONES</th>
                    </tr>
                </thead>
                <tbody>
                    <%for (Pieza pieza : piezas) {%>
                    <tr>
                        <td class="text-center"><%=pieza.getIdPieza()%></td>
                        <td><%=pieza.getTipoPieza().toUpperCase()%></td>
                        <td class="text-center">Q<%=pieza.getPrecio()%></td>
                        <td class="text-center">
                            <a href="${pageContext.request.contextPath}/CambioPiezas?pie=<%=pieza.getIdPieza()%>&accion=1" class="btn btn-info" role="button">Editar</a>
                            <a href="${pageContext.request.contextPath}/CambioPiezas?pie=<%=pieza.getIdPieza()%>&accion=2" class="btn btn-danger text-center">Eliminar</a>
                        </td>
                    </tr>
                    <%}%>
                </tbody>
            </table>
    </body>
</html>
