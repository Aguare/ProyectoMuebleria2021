<%-- 
    Document   : VentasDia
    Created on : 3/09/2021, 23:42:09
    Author     : aguare
--%>

<%@page import="EntidadesVenta.Mueble"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ObtenerObjetos.ObtenerV"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Muebles Disponibles</title>
    </head>
    <body>
        <jsp:include page="../../resources/CSS/RecursosCSS.jsp"></jsp:include>
        <jsp:include page="../../resources/JS/RecursosJS.jsp"></jsp:include>
        <jsp:include page="../../resources/JS/RecursosJS.jsp"></jsp:include>
        <jsp:include page="../../Menus/Venta.jsp"></jsp:include>
            <br>
        <%
            ObtenerV obtener = new ObtenerV();
            ArrayList<Mueble> muebles = obtener.obtenerMueblesNoVendidos();
        %>
        <div class="container text-center">
            <h1>MUEBLES DISPONIBLES</h1>
            <input class="form-control" id="entrada" type="text" placeholder="BUSQUEDA">
            <br>
            <div class="table-responsive-lg table-hover">
                <table class="table">
                    <thead class="thead-dark">
                        <tr>
                            <th>ID</th>
                            <th>TIPO DE MUEBLE</th>
                            <th>PRECIO</th>
                        </tr>
                    </thead>
                    <tbody id="tabla">
                        <%if (muebles.isEmpty()) {%>
                        <tr class="table-warning">
                            <td colspan="3">NO HAY MUEBLES DISPONIBLES</td>
                        </tr>
                        <%}%>
                        <%for (Mueble m : muebles) {%>
                        <tr>
                            <td><%=m.getIdMueble()%></td>
                            <td><%=m.getTipoMueble().toUpperCase()%></td>
                            <td>Q.<%=m.getPrecioVenta()%></td>
                        </tr>
                        <%}%>

                    </tbody>
                </table>
            </div>
        </div>
        <script>
            $(document).ready(function(){
                $("#entrada").on("keyup", function() {
                    var value = $(this).val().toLowerCase();
                    $("#tabla tr").filter(function() {
                    $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
                    });
                });
            });
        </script> 
    </body>
</html>
