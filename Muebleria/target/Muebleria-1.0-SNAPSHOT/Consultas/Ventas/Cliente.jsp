<%-- 
    Document   : Cliente
    Created on : 3/09/2021, 18:57:15
    Author     : aguare
--%>

<%@page import="EntidadesVenta.Mueble"%>
<%@page import="EntidadesVenta.CarritoCompra"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ObtenerObjetos.ObtenerUC"%>
<%@page import="EntidadesVenta.Cliente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cliente</title>
        <jsp:include page="../../resources/CSS/RecursosCSS.jsp"></jsp:include>
        <jsp:include page="../../resources/JS/RecursosJS.jsp"></jsp:include>
        <jsp:include page="../../resources/JS/RecursosJS.jsp"></jsp:include>
        </head>
        <body>
        <jsp:include page="../../Menus/Venta.jsp"></jsp:include>
            <br>
        <%
            ObtenerUC obtener = new ObtenerUC();
            ArrayList<Cliente> clientes = obtener.obtenerClientes();
            ArrayList<Mueble> carritoCompra = new ArrayList<>();
            Cliente clienteSelec = null;
            CarritoCompra carrito = null;
            if (request.getSession().getAttribute("carritoCompra") != null) {
                carrito = (CarritoCompra) request.getSession().getAttribute("carritoCompra");
                carritoCompra = carrito.getCarrito();
                if (carrito.getCliente() != null) {
                    clienteSelec = carrito.getCliente();
                }
            }
        %>
        <form action="${pageContext.request.contextPath}/Venta" method="POST" class="was-validated">
            <div class="container border">
                <br>
                <h4 class="text-center">CLIENTE</h4>
                <div class="form-group">
                    <label for="uname">NIT</label>
                    <%if (clienteSelec != null) {%>
                    <input type="text" class="form-control" id="nit" name="NIT" maxlength="20" value="<%=clienteSelec.getNIT()%>" required>
                    <%} else {%>
                    <input type="text" class="form-control" id="nit" name="NIT" maxlength="20" required>
                    <%}%>
                </div>
                <div class="table-responsive" style="height: 100px">
                    <table class="table table-borderless table-sm" id="tablaClientes">
                        <tbody>
                            <%for (Cliente client : clientes) {%>
                            <tr>
                                <td><%=client.getNIT()%></td>
                                <td><%=client.getNombre().toUpperCase()%></td>
                                <td><a href="${pageContext.request.contextPath}/Venta?NIT=<%=client.getNIT()%>" class="btn btn-outline-dark btn-sm" role="button">Seleccionar</a></td>
                            </tr>
                            <%}%>
                        </tbody>
                    </table>
                </div>
                <br>
                <div class="form-group">
                    <label for="uname">NOMBRE</label>
                    <%if (clienteSelec != null) {%>
                    <input type="text" class="form-control" id="uname" name="nombre" maxlength="60" value="<%=clienteSelec.getNombre()%>" required>
                    <%} else {%>
                    <input type="text" class="form-control" id="uname" name="nombre" maxlength="60" required>
                    <%}%>
                </div>
                <div class="form-group">
                    <label for="pwd">DIRECCIÓN</label>
                    <%if (clienteSelec != null) {%>
                    <input type="text" class="form-control" id="pwd" name="direccion" maxlength="100" value="<%=clienteSelec.getDireccion()%>" required>
                    <%} else {%>
                    <input type="text" class="form-control" id="pwd" name="direccion" maxlength="100" required>
                    <%}%>
                </div>
                <div class="row text-center">
                    <h4 class="text-center">PRODUCTOS</h4>
                    <div class="table-responsive">
                        <table class="table table-sm">
                            <thead class="thead-dark">
                                <tr>
                                    <th>ID</th>
                                    <th>TIPO DE MUEBLE</th>
                                    <th>PRECIO</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%if (carritoCompra.isEmpty()) {%>
                                <tr class="table-warning">
                                    <td colspan="4">CARRITO VACÍO</td>
                                </tr>
                                <%}%>
                                <%for (Mueble mueble : carritoCompra) {%>
                                <tr>
                                    <td><%=mueble.getIdMueble()%></td>
                                    <td><%=mueble.getTipoMueble()%></td>
                                    <td>Q.<%=mueble.getPrecioVenta()%></td>
                                </tr>
                                <%}%>
                                <%if (carrito !=null) {%>
                                <tr class="table-primary">
                                    <td colspan="2">TOTAL</td>
                                    <td>Q.<%=carrito.obtenerTotal()%></td>
                                </tr>
                                <%}%>
                            </tbody>
                        </table>
                    </div>
                </div>
                <br>
                <div class="text-center">
                    <button type="submit" class="btn btn-success btn-block">REGISTRAR COMPRA</button>
                </div>
                <br>
            </div>
        </form>
        <script>
        $(document).ready(function(){
            $("#nit").on("keyup", function() {
                var value = $(this).val().toLowerCase();
                $("#tablaClientes tr").filter(function() {
                $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
                });
            });
        });
        </script>
    </body>
</html>
