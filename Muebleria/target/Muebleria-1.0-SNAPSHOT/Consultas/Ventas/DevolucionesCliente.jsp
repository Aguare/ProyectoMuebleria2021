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
            ArrayList<Devolucion> devoluciones = obtener.obtenerDevolucionesTodas();
            String fechaInicial = "";
            String fechaFinal = "";
            String cliente = "";
            double perdida = 0;
            if (request.getAttribute("devoluciones") != null) {
                devoluciones = (ArrayList<Devolucion>) request.getAttribute("devoluciones");
                fechaInicial = String.valueOf(request.getAttribute("fechaInicial"));
                fechaFinal = String.valueOf(request.getAttribute("fechaFinal"));
                cliente = String.valueOf(request.getAttribute("cliente"));
            }
        %>
        <div class="container">
            <h1 class="text-center">DEVOLUCIONES DEL CLIENTE</h1>
            <div class="container border">
                <br>
                <p class="bg-info text-white"> Puede filtrar su búsqueda interactuando con las siguientes opciones. Las fechas vacías muestran todas las devoluciones</p>
                <form action="${pageContext.request.contextPath}/DevolucionesCliente" method="POST">
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
                            <th>NIT</th>
                            <th>CLIENTE</th>
                            <th>ID</th>
                            <th>TIPO DE MUEBLE</th>
                            <th>FECHA COMPRA</th>
                            <th>FECHA DEVOLUCIÓN</th>
                            <th>PÉRDIDA</th>
                        </tr>
                    </thead>
                    <tbody id="tabla">
                        <%if (devoluciones.isEmpty()) {%>
                        <tr class="table-warning">
                            <td colspan="7">NO HAY REGISTROS</td>
                        </tr>
                        <%}else{%>
                        <% for (Devolucion devolucion : devoluciones) {
                            ArrayList<Mueble> muebles = devolucion.obtenerMueblesDevueltos();
                            Factura factura = devolucion.getFactura();
                            perdida+= devolucion.getPerdida();
                            for (Mueble f : muebles) {%>
                        <tr>
                            <td><%=factura.getCliente().getNIT()%></td>
                            <td><%=factura.getCliente().getNombre().toUpperCase()%></td>
                            <td><%=f.getIdMueble()%></td>
                            <td><%=f.getTipoMueble().toUpperCase()%></td>
                            <td><%=factura.getFecha().toString()%></td>
                            <td><%=devolucion.getFecha().toString()%></td>
                            <td>Q.<%=devolucion.getPerdida()%></td>
                        </tr>
                        <%}%>
                        <%}%>
                        <tr class="table-danger">
                            <td colspan="6">PÉRDIDA</td>
                            <td colspan="2">Q.<%=perdida%></td>
                        </tr>
                        <%}%>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>
