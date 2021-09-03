<%-- 
    Document   : Piezas
    Created on : 29/08/2021, 21:40:10
    Author     : marco
--%>

<%@page import="ObtenerObjetos.ObtenerF"%>
<%@page import="EntidadesFabrica.TipoPiezas"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="EntidadesFabrica.Pieza"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ObtenerObjetos.ObtenerF"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Piezas</title>
        <jsp:include page="../../resources/CSS/RecursosCSS.jsp"></jsp:include>
        <jsp:include page="../../resources/JS/RecursosJS.jsp"></jsp:include>
        </head>
        <body>
        <jsp:include page="../../Menus/Fabrica.jsp"></jsp:include>
        <jsp:include page="../../resources/JS/RecursosJS.jsp"></jsp:include>
        <%
            if (request.getAttribute("piezas") != null) {
                ArrayList<Pieza> piezas = (ArrayList<Pieza>) request.getAttribute("piezas");
                int orden = (int) request.getAttribute("orden");
                ObtenerF obtener = new ObtenerF();
                ArrayList<TipoPiezas> tipoPiezas = obtener.obtenerTipoPiezas(orden);
        %>
        <br>
        <div class="container">
            <h2 class="text-center">INVENTARIO DE PIEZAS</h2>
            <p class="text-center">Se ordena según la cantidad de piezas de cada tipo</p>
            <div class="container text-center">
                <form action="${pageContext.request.contextPath}/ConsultasFabrica" method="GET">
                    <div class="form-check-inline">
                        <label class="form-check-label">
                            <%if (orden == 1) {%>
                            <input type="radio" class="form-check-input" name="orden" value="1" checked>Ascendente
                            <%
                            } else {%>
                            <input type="radio" class="form-check-input" name="orden" value="1">Ascendente
                            <%}%>
                        </label>
                    </div>
                    <div class="form-check-inline">
                        <label class="form-check-label">
                            <%if (orden == 2) {%>
                            <input type="radio" class="form-check-input" name="orden" value="2" checked>Descendente
                            <%
                            } else {%>
                            <input type="radio" class="form-check-input" name="orden" value="2">Descendente
                            <%}%>
                        </label>
                    </div>
                    <div class="custom-control custom-radio custom-control-inline">
                        <button type="submit" class="btn btn-outline-primary">Ordernar</button>
                    </div>
                </form>
            </div>
            <br>
        </div>
        <div class="container">
            <table class="table table-bordered">
                <thead class="thead-dark">
                    <tr>
                        <th class="text-center">TIPO DE PIEZA</th>
                        <th class="text-center">CANTIDAD</th>
                        <th class="text-center">ESTADO</th>
                    </tr>
                </thead>
                <tbody>
                    <%for (TipoPiezas tipo : tipoPiezas) {%>
                    <tr>
                        <td><%=tipo.getNombrePieza().toUpperCase()%></td>
                        <td class="text-center"><%=tipo.getCantidad()%></td>
                        <%if (tipo.getCantidad() == 0) {%>
                        <td class="text-center"><p class="bg-danger text-white">Agotado</p></td>
                        <%} else if (tipo.getCantidad() <= 4) {%>
                        <td class="text-center"><p class="bg-warning text-white">Casi Agotado</p></td>
                        <%} else {%>
                        <td class="text-center"><p class="bg-success text-white">En existencia</p></td>
                        <%}%>
                    </tr>
                    <%}%>
                </tbody>
            </table>
            <p>BUSQUEDA</p>
            <input class="form-control" id="myInput" type="text" placeholder="BUSCAR PIEZA SEGÚN TIPO O PRECIO">
            <br>
            <div class="table-responsive" style="height: 670px">
                <table class="table table-bordered" id="tablaPiezas">
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
            </div>
            <br>
            <script>
            $(document).ready(function(){
                $("#myInput").on("keyup", function() {
                    var value = $(this).val().toLowerCase();
                    $("#tablaPiezas tr").filter(function() {
                    $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
                    });
                });
            });
            </script> 
        </div>
        <%}%>
    </body>
</html>
