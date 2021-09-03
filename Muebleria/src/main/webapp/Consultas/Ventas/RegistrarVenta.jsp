<%-- 
    Document   : Venta
    Created on : 2/09/2021, 17:39:17
    Author     : aguare
--%>

<%@page import="EntidadesVenta.CarritoCompra"%>
<%@page import="EntidadesVenta.Mueble"%>
<%@page import="ObtenerObjetos.ObtenerF"%>
<%@page import="ObtenerObjetos.ObtenerV"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="EntidadesVenta.Cliente"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ObtenerObjetos.ObtenerUC"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>RegistrarVenta</title>
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
            ArrayList<Mueble> muebles = new ArrayList<>();
            ArrayList<Mueble> carritoCompra = new ArrayList<>();
            Cliente clienteSelec = null;
            if (request.getSession().getAttribute("carritoCompra") != null) {
                CarritoCompra carrito = (CarritoCompra) request.getSession().getAttribute("carritoCompra");
                if (carrito.getCliente() != null) {
                    clienteSelec = carrito.getCliente();
                }
                muebles = carrito.getDisponibles();
                carritoCompra = carrito.getCarrito();
        %>
        <div class="container border">
            <br><h2 class="text-center">REGISTRAR VENTA</h2>
            <form action="#" class="was-validated">
                <div class="row">
                    <div class="col-md-6">
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
                                        <td><a href="${pageContext.request.contextPath}/Venta?NIT=<%=client.getNIT()%>" class="btn btn-outline-dark" role="button">Seleccionar</a></td>
                                    </tr>
                                    <%}%>
                                </tbody>
                            </table>
                        </div>
                        <div class="form-group">
                            <label for="uname">NOMBRE CLIENTE</label>
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
                    </div>
                    <div class="col-md-6">
                        <h5 class="text-center">MUEBLES A LA VENTA</h5>
                        <div class="table-responsive" style="height: 325px">
                            <table class="table ">
                                <thead class="thead-light">
                                    <tr>
                                        <th>ID</th>
                                        <th>TIPO DE MUEBLE</th>
                                        <th>PRECIO</th>
                                        <th>OPCIONES</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%for (Mueble mueble : muebles) {%>
                                    <tr>
                                        <td><%=mueble.getIdMueble()%></td>
                                        <td><%=mueble.getTipoMueble()%></td>
                                        <td>Q.<%=mueble.getPrecioVenta()%></td>
                                        <td class="text-center">
                                            <a href="${pageContext.request.contextPath}/Venta?agregar=<%=mueble.getIdMueble()%>" class="btn btn-info btn-sm">AGREGAR</a>
                                        </td>
                                    </tr>
                                    <%}%>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <br>
                <div class="row text-center">
                    <h4 class="text-center">CARRITO DE COMPRA</h4>
                    <div class="table-responsive" style="height: 275px">
                        <table class="table ">
                            <thead class="thead-light">
                                <tr>
                                    <th>ID</th>
                                    <th>TIPO DE MUEBLE</th>
                                    <th>PRECIO</th>
                                    <th>OPCIONES</th>
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
                                    <td class="text-center">
                                        <a href="${pageContext.request.contextPath}/Venta?quitar=<%=mueble.getIdMueble()%>" class="btn btn-danger btn-sm">QUITAR</a>
                                    </td>
                                </tr>
                                <%}%>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="row text-center">
                    <div class="col-md-6">
                        <h1>TOTAL DE COMPRA</h1>
                    </div>
                    <div class="col-md-6">
                        <h1>Q.<%=carrito.obtenerTotal()%></h1>
                    </div>
                </div>
                <br>
                <div class="row text-center">
                    <div class="col-md-6">
                        <input type="hidden" name="carritoCompra" value="<%=carrito%>">
                        <button type="submit" class="btn btn-success">REGISTRAR</button>
                    </div>
                    <div class="col-md-6">
                        <a href="${pageContext.request.contextPath}/Venta?cancelarVenta=1" class="btn btn-danger">CANCELAR</a>
                    </div>
                </div>
            </form>
            <br>
        </div>
        <%}%>
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
