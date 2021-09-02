<%-- 
    Document   : MueblesEnsamblados
    Created on : 1/09/2021, 00:41:30
    Author     : marco
--%>

<%@page import="ModificarObj.FabricaCRUD"%>
<%@page import="EntidadesVenta.Mueble"%>
<%@page import="EntidadesFabrica.Ensamble"%>
<%@page import="java.util.ArrayList"%>
<%@page import="SQL.ObtenerObj"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Muebles Ensamblados</title>
        <jsp:include page="../../resources/JS/RecursosJS.jsp"></jsp:include>
        </head>
        <body>
        <jsp:include page="../../Menus/Fabrica.jsp"></jsp:include>
        <jsp:include page="../../resources/JS/RecursosJS.jsp"></jsp:include>
            <br>
        <%
            ObtenerObj obtener = new ObtenerObj();
            FabricaCRUD fabrica = new FabricaCRUD();
            ArrayList<Ensamble> ensambles = obtener.obtenerEnsambles();
        %>
        <div class="container">
            <h2 class="text-center">MUEBLES ENSAMBLADOS</h2>
            <p class="text-center">Aquí estan todos los muebles ensamblados con su estado en el punto de venta, puede filtrar su busqueda mediante la fecha</p>
            <table class="table table-bordered">
                <thead class="thead-dark">
                    <tr>
                        <th class="text-center">ID ENSAMBLE</th>
                        <th class="text-center">FECHA</th>
                        <th class="text-center">USUARIO</th>
                        <th class="text-center">TIPO DE MUEBLE</th>
                        <th class="text-center">ESTADO VENTAS</th>
                    </tr>
                </thead>
                <tbody>
                    <%for (Ensamble ensamble : ensambles) {%>
                    <tr>
                        <td class="text-center"><%=ensamble.getIdEnsamble()%></td>
                        <td class="text-center"><%=ensamble.getFecha().toString()%></td>
                        <td class="text-center"><%=ensamble.getUsuario().getNombre_usuario()%></td>
                        <td class="text-center"><%=ensamble.getTipoMueble()%></td>
                        <td class="text-center">
                            <a href="${pageContext.request.contextPath}/CambioEnsamble?ensamble=<%=ensamble.getIdEnsamble()%>" class="btn btn-info" role="button">Editar</a>
                        </td>
                    </tr>
                    <%}%>
                </tbody>
            </table>
        </div>
    </body>
</html>
