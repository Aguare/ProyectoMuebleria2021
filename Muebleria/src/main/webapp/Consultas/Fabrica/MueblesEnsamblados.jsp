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
            if (request.getAttribute("ensambles") != null) {
            ArrayList<Ensamble> ensambles = (ArrayList<Ensamble>) request.getAttribute("ensambles");
            String fechaInicial = "";
            String fechaFinal = "";
            if (request.getAttribute("fechaInicial") != null && request.getAttribute("fechaFinal") != null) {
                fechaInicial = String.valueOf(request.getAttribute("fechaInicial"));
                fechaInicial = String.valueOf(request.getAttribute("fechaFinal"));
            }
        %>
        <div class="container">
            <h2 class="text-center">MUEBLES ENSAMBLADOS</h2>
            <p class="text-center">Aquí estan todos los muebles ensamblados con su estado en el punto de venta, puede filtrar su busqueda mediante la fecha</p>
            <div class="container text-center">
                <form action="${pageContext.request.contextPath}/ConsultasFabrica" method="POST">
                    <div class="form-check-inline">
                        <label class="form-check-label">
                            Fecha Inicial: <input type="date" class="form-check-input" name="fechaInicial" value="<%=fechaInicial%>">
                        </label>
                    </div>
                    <div class="form-check-inline">
                        <label class="form-check-label">
                            Fecha Final: <input type="date" class="form-check-input" name="fechaFinal" value="<%=fechaFinal%>">
                        </label>
                    </div>
                    <div class="custom-control custom-radio custom-control-inline">
                        <button type="submit" class="btn btn-outline-primary">Buscar</button>
                    </div>
                    <div class="custom-control custom-radio custom-control-inline">
                        <button type="reset" class="btn btn-outline-secondary">Limpiar Fechas</button>
                    </div>
                </form>
            </div>
            <br>
            <table class="table table-bordered">
                <thead class="thead-dark">
                    <tr>
                        <th class="text-center">ID ENSAMBLE</th>
                        <th class="text-center">FECHA</th>
                        <th class="text-center">USUARIO</th>
                        <th class="text-center">TIPO DE MUEBLE</th>
                        <th class="text-center">OPCIONES</th>
                    </tr>
                </thead>
                <tbody>
                    <% if (ensambles.isEmpty()){%>
                    <tr class="table-info">
                        <th colspan="5" class="text-center">
                            NO HAY ENSAMBLES REGISTRADOS
                        </th>
                    </tr>
                    <%}%>
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
            <%} else {%>
            <h2 class="text-center">NO TIENE ACCESO</h2>
            <%}%>
        </div>
    </body>
</html>
