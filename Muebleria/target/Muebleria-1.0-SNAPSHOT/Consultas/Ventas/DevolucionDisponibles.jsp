<%-- 
    Document   : VentasDia
    Created on : 3/09/2021, 23:42:09
    Author     : aguare
--%>

<%@page import="java.sql.Date"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.time.LocalDate"%>
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
        <title>Compras del Cliente</title>
    </head>
    <body>
        <jsp:include page="../../resources/CSS/RecursosCSS.jsp"></jsp:include>
        <jsp:include page="../../resources/JS/RecursosJS.jsp"></jsp:include>
        <jsp:include page="../../resources/JS/RecursosJS.jsp"></jsp:include>
        <jsp:include page="../../Menus/Venta.jsp"></jsp:include>
            <br>
        <%
            ObtenerV obtener = new ObtenerV();
            LocalDate fechaActual = LocalDate.now();
            LocalDate fechaAnterior = fechaActual.minusDays(7);
            ArrayList<Factura> facturas = obtener.obtenerFacturasIntervaloFecha(fechaAnterior.toString(), fechaActual.toString());
        %>
        <div class="container">
            <h1 class="text-center">COMPRAS CON OPCIÓN A DEVOLUCIÓN</h1>
            <br>
            <p>Se muestran las compras que tienen menos de 7 días y es posible hacer la devolución.</p>
            <input class="form-control" id="myInput" type="text" placeholder="BUSCAR">
            <div class="table-responsive-lg table-hover">
                <table class="table text-center ">
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
                            <td colspan="5">NO HAY REGISTROS</td>
                        </tr>
                        <%}%>
                        <%for (Factura f : facturas) {%>
                        <tr>
                            <td><%=f.getNoFactura()%></td>
                            <td><%=f.getCliente().getNIT()%></td>
                            <td><%=f.getCliente().getNombre().toUpperCase()%></td>
                            <td>Q.<%=f.getTotal()%></td>
                            <td>
                                <a href="${pageContext.request.contextPath}/Consultas/Ventas/Devolucion.jsp?noFactura=<%=f.getNoFactura()%>" class="btn btn-info" role="button">Ver</a>
                            </td>
                        </tr>
                        <%}%>
                    </tbody>
                </table>
            </div>
        </div>
        <script>
        $(document).ready(function(){
            $("#myInput").on("keyup", function() {
                var value = $(this).val().toLowerCase();
                $("#tabla tr").filter(function() {
                $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
                });
            });
        });
        </script> 
    </body>
</html>
