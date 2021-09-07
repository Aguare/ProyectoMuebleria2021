<%-- 
    Document   : VentasDia
    Created on : 3/09/2021, 23:42:09
    Author     : aguare
--%>

<%@page import="EntidadesVenta.Factura"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ObtenerObjetos.ObtenerV"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ventas del Día</title>
    </head>
    <body>
        <jsp:include page="../../resources/CSS/RecursosCSS.jsp"></jsp:include>
        <jsp:include page="../../resources/JS/RecursosJS.jsp"></jsp:include>
        <jsp:include page="../../resources/JS/RecursosJS.jsp"></jsp:include>
        <jsp:include page="../../Menus/Venta.jsp"></jsp:include>
        <br>
        <%
            ObtenerV obtener = new ObtenerV();
            ArrayList<Factura> facturas = obtener.obtenerFacturasDiaActual();
        %>
        <div class="container text-center">
            <h1>VENTAS DEL DÍA</h1>
            <input class="form-control" id="entrada" type="text" placeholder="BUSQUEDA">
            <br>
            <div class="table-responsive-lg table-hover">
                <table class="table">
                    <thead class="thead-dark">
                        <tr>
                            <th>FACTURA</th>
                            <th>NIT</th>
                            <th>CLIENTE</th>
                            <th>TOTAL</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody id="tabla">
                        <%if (facturas.isEmpty()) {%>
                        <tr class="table-warning">
                            <td colspan="5">NO HAY VENTAS</td>
                        </tr>
                        <%}%>
                        <%for (Factura f : facturas) {%>
                        <tr>
                            <td><%=f.getNoFactura()%></td>
                            <td><%=f.getCliente().getNIT()%></td>
                            <td><%=f.getCliente().getNombre().toUpperCase()%></td>
                            <td>Q.<%=f.getTotal()%></td>
                            <td>
                                <a href="${pageContext.request.contextPath}/Consultas/Ventas/Factura.jsp?noFactura=<%=f.getNoFactura()%>" class="btn btn-info" role="button">Ver</a>
                            </td>
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
