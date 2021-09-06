<%-- 
    Document   : VentasDia
    Created on : 3/09/2021, 23:42:09
    Author     : aguare
--%>

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
            ObtenerUC obtenerUC = new ObtenerUC();
            ArrayList<Cliente> clientes = obtenerUC.obtenerClientes();
            ArrayList<Factura> facturas = new ArrayList<>();
            String fechaInicial = "";
            String fechaFinal = "";
            String cliente = "";
            if (!clientes.isEmpty()) {
                facturas = obtener.obtenerFacturasCliente(clientes.get(0).getNIT());
            }
            if (request.getAttribute("facturas") != null) {
                facturas = (ArrayList<Factura>) request.getAttribute("facturas");
                fechaInicial = String.valueOf(request.getAttribute("fechaInicial"));
                fechaFinal = String.valueOf(request.getAttribute("fechaFinal"));
                cliente = String.valueOf(request.getAttribute("cliente"));
            }
        %>
        <div class="container">
            <h1 class="text-center">COMPRAS DEL CLIENTE</h1>
            <div class="container border">
                <br>
                <p class="bg-info text-white"> Puede filtrar su búsqueda interactuando con las siguientes opciones. Las fechas vacías muestran todas las compras</p>
                <form action="${pageContext.request.contextPath}/ConsultasVentas" method="POST">
                    <div class="form-check-inline">
                        <label class="form-check-label">
                            Fecha Inicial: <input type="date" class="form-group" name="fechaInicial" value="<%=fechaInicial%>">
                        </label>
                    </div>
                    <div class="form-check-inline">
                        <label class="form-check-label">
                            Fecha Final: <input type="date" class="form-group" name="fechaFinal" value="<%=fechaFinal%>">
                        </label>
                    </div>
                    <div class="custom-control custom-radio custom-control-inline">
                        <button type="submit" class="btn btn-outline-primary">Buscar</button>
                    </div>
                    <div class="form-group">
                        <select name="clienteSeleccionado" class="form-control ml-auto">
                            <%if (!cliente.equals("")) {%>
                            <option><%=cliente%></option>
                            <%}%>
                            <% 
                                for (Cliente cli : clientes) {
                                    String c = cli.getNIT() +", "+cli.getNombre().toUpperCase();
                                    if (!cliente.equalsIgnoreCase(c)) {
                            %>
                            <option><%=cli.getNIT()%>, <%=cli.getNombre().toUpperCase()%></option>
                            <%}}%>
                        </select>
                    </div>
                </form>
            </div>
            <br>
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
                                <a href="${pageContext.request.contextPath}/Consultas/Ventas/Factura.jsp?noFactura=<%=f.getNoFactura()%>" class="btn btn-info" role="button">Ver</a>
                            </td>
                        </tr>
                        <%}%>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>
