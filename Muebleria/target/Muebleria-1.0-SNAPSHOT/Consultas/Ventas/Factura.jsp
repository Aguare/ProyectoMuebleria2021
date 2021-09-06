<%-- 
    Document   : Factura
    Created on : 3/09/2021, 20:37:27
    Author     : aguare
--%>

<%@page import="EntidadesFabrica.Usuario"%>
<%@page import="ObtenerObjetos.ObtenerV"%>
<%@page import="java.util.ArrayList"%>
<%@page import="EntidadesVenta.Mueble"%>
<%@page import="EntidadesVenta.Factura"%>
<%@page import="EntidadesVenta.Compra"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Factura</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <jsp:include page="../../resources/CSS/RecursosCSS.jsp"></jsp:include>
        <jsp:include page="../../resources/JS/RecursosJS.jsp"></jsp:include>
        <jsp:include page="../../resources/JS/RecursosJS.jsp"></jsp:include>
        <% int tipo = 0;
            if (request.getSession() != null) {
                tipo = ((Usuario) request.getSession().getAttribute("Usuario")).getIdDepartamento();
            }
        %>
        <%switch (tipo) {
                case 1:%>
        <jsp:include page="../../Menus/Fabrica.jsp"></jsp:include>
        <% break;
            case 2:%>
        <jsp:include page="../../Menus/Venta.jsp"></jsp:include>
        <%break;
            case 3:%>
        <jsp:include page="../../Menus/Financiero.jsp"></jsp:include>
        <%break;
            default:%>
        <jsp:include page="../../Menus/Principal.jsp"></jsp:include>
        <%break;
            }
        %>
        <br>
        <%
            ObtenerV obtener = new ObtenerV();
            if (request.getAttribute("noFactura") != null || request.getParameter("noFactura") != null) {
            int noFactura = -1;   
            if (request.getParameter("noFactura") != null) {
                noFactura = Integer.parseInt(request.getParameter("noFactura"));
               }else{
               noFactura = (int) request.getAttribute("noFactura");
            }
            
            Compra compra = obtener.obtenerCompraSegunFactura(noFactura);
            Factura factura = compra.getFactura();
        %>
        <div class="container p-3 my-3 bg-light" style="width: 700px">
            <div class="row">
                <div class="col-sm-4 text-right">
                    <img src="../../resources/img/icono.png" class="rounded-circle" 
                         width="50" height="50">
                </div>
                <div class="col-sm-4 text-left">
                    <h1 class="text-center">FACTURA</h1>
                </div>
            </div>
            <div class="container border"><br>
                <div class="row">
                    <div class="col-sm-3">
                        <label for="uname">NÚMERO</label>
                        <input type="number" class="form-control" name="nofactura" value="<%=factura.getNoFactura()%>" readonly>
                    </div>
                    <div class="col-sm-4">
                        <label for="uname">FECHA</label>
                        <input type="date" class="form-control" name="fecha" value="<%=factura.getFecha().toString()%>" readonly>
                    </div>
                    <div class="col">
                        <label for="uname">CAJERO</label>
                        <input type="text" class="form-control" name="cajero" value="<%=factura.getUsuario()%>" readonly>
                    </div>
                </div><br>
                <div class="row">
                    <div class="col-sm-4">
                        <label for="uname">NIT</label>
                        <input type="text" class="form-control" name="NIT" value="<%=factura.getCliente().getNIT()%>" readonly>
                    </div>
                    <div class="col-sm-8">
                        <label for="uname">NOMBRE</label>
                        <input type="text" class="form-control" name="cliente" value="<%=factura.getCliente().getNombre().toUpperCase()%>" readonly>
                    </div>
                </div>
                <div class="form-group">
                    <label for="uname">DIRECCIÓN</label>
                    <input type="text" class="form-control" name="direccion" value="<%=factura.getCliente().getDireccion().toUpperCase()%>" readonly>
                </div>
                <h4 class="text-center">PRODUCTOS</h4>
                <div class="row">
                    <div class="table-responsive">
                        <table class="table table-sm text-center">
                            <thead class="thead-dark">
                                <tr>
                                    <th>ID</th>
                                    <th>TIPO DE MUEBLE</th>
                                    <th>PRECIO</th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                <% ArrayList<Mueble> lista = compra.getMuebles(); %>
                                <%for (Mueble mueble : lista) {%>
                                <tr>
                                    <td><%=mueble.getIdMueble()%></td>
                                    <td><%=mueble.getTipoMueble().toUpperCase()%></td>
                                    <td>Q.<%=mueble.getPrecioVenta()%></td>
                                    <%if (mueble.isDevuelto()) {%>
                                    <td>DEVUELTO</td>
                                    <%}%>
                                </tr>
                                <%}%>
                                <tr class="table-dark text-dark">
                                    <td colspan="2" class="text-center">TOTAL</td>
                                    <td >Q.<%=factura.getTotal()%></td>
                                    <td></td>
                                </tr>
                                
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <br>
        </div>
        <%}%>
    </body>
</html>
