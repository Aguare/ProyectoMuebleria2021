<%-- 
    Document   : VentasDia
    Created on : 3/09/2021, 23:42:09
    Author     : aguare
--%>

<%@page import="EntidadesVenta.Devolucion"%>
<%@page import="EntidadesVenta.Compra"%>
<%@page import="EntidadesVenta.Mueble"%>
<%@page import="EntidadesVenta.Cliente"%>
<%@page import="ObtenerObjetos.ObtenerUC"%>
<%@page import="EntidadesVenta.Factura"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ObtenerObjetos.ObtenerV"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>DEVOLUCIONES</title>
    </head>
    <body>
        <jsp:include page="../../resources/CSS/RecursosCSS.jsp"></jsp:include>
        <jsp:include page="../../resources/JS/RecursosJS.jsp"></jsp:include>
        <jsp:include page="../../resources/JS/RecursosJS.jsp"></jsp:include>
        <jsp:include page="../../Menus/Fabrica.jsp"></jsp:include>
            <br>
        <%
            ObtenerV obtener = new ObtenerV();
            ArrayList<Devolucion> devoluciones = obtener.obtenerDevolucionesTodasSinReintegro();
        %>
        <div class="container">
            <h1 class="text-center">DEVOLUCIONES</h1>
            <div class="table-responsive-lg table-hover">
                <table class="table text-center" id="tabla">
                    <thead class="thead-dark">
                        <tr>
                            <th>ID</th>
                            <th>TIPO DE MUEBLE</th>
                            <th>FECHA DEVOLUCIÓN</th>
                            <th>REINTEGRAR PIEZAS</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%if (devoluciones.isEmpty()) {%>
                        <tr class="table-warning">
                            <td colspan="7">NO HAY REGISTROS</td>
                        </tr>
                        <%}else{%>
                        <% for (Devolucion devolucion : devoluciones) {
                            ArrayList<Mueble> muebles = devolucion.obtenerMueblesDevueltos();
                            for (Mueble f : muebles) {%>
                        <%if (!f.getEnsamble().isReintegro()) {%>
                        <tr>
                            <td><%=f.getIdMueble()%></td>
                            <td><%=f.getTipoMueble().toUpperCase()%></td>
                            <td><%=devolucion.getFecha().toString()%></td>
                            <td>
                                <a href="${pageContext.request.contextPath}/ReintegrarPiezas?idMueble=<%=f.getIdMueble()%>&devolucion=<%=devolucion.getIdDevolucion()%>" class="btn btn-info" role="button">REINTEGRAR</a>
                            </td>
                        </tr>
                        <%}%>
                        <%}%>
                        <%}%>
                        <%}%>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>
