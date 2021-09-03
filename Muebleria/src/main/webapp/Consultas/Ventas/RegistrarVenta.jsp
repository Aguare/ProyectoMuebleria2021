<%-- 
    Document   : Venta
    Created on : 2/09/2021, 17:39:17
    Author     : aguare
--%>

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
    </head>
    <body>
        <jsp:include page="../../Menus/Venta.jsp"></jsp:include>
        <br>
        <% 
            ObtenerUC obtener = new ObtenerUC();
            ArrayList<Cliente> clientes = obtener.obtenerClientes();
            Cliente clienteSelec = null;
            if (request.getAttribute("clienteSeleccionado") != null) {
                clienteSelec = (Cliente) request.getAttribute("clienteSeleccionado");
            }
        %>
        <div class="container border">
            <h2 class="text-center">REGISTRAR VENTA</h2>
            <form action="#" class="was-validated">
                <div class="row">
                    <div class="col">
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
                    <div class="col">
                        <div class="container border">
                            <h2 class="text-center">MUEBLES A LA VENTA</h2>
                            
                        </div>
                    </div>
                </div>
                <div class="row text-center">
                    <div class="col">
                        <button type="submit" class="btn btn-primary">REGITRAR</button>
                    </div>
                    <div class="col">
                        <button type="reset" class="btn btn-danger">CANCELAR</button>
                    </div>
                </div>
            </form>
            <br>
        </div>
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
