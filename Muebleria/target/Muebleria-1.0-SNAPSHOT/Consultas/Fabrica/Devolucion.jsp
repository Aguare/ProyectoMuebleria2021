<%-- 
    Document   : EditarPieza
    Created on : 29/08/2021, 23:24:21
    Author     : marco
--%>

<%@page import="EntidadesVenta.Devolucion"%>
<%@page import="ObtenerObjetos.ObtenerV"%>
<%@page import="ObtenerObjetos.ObtenerUC"%>
<%@page import="java.time.LocalDate"%>
<%@page import="EntidadesFabrica.Usuario"%>
<%@page import="EntidadesFabrica.Ensamble"%>
<%@page import="EntidadesFabrica.TipoMueble"%>
<%@page import="ModificarObj.FabricaCRUD"%>
<%@page import="EntidadesFabrica.TipoPiezas"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ObtenerObjetos.ObtenerF"%>
<%@page import="EntidadesFabrica.Pieza"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>MUEBLE DEVUELTO</title>
        <jsp:include page="../../Menus/Fabrica.jsp"></jsp:include>
        <style><%@include file="../../resources/CSS/RecursosCSS.jsp"%></style>
        <style><%@include file="../../resources/JS/RecursosJS.jsp"%></style>
    </head>
    <body>
        <br>
        <%
            if (request.getAttribute("ensamble") != null) {
                Ensamble ensamble = (Ensamble) request.getAttribute("ensamble");
                Devolucion dev = (Devolucion) request.getAttribute("devolucion");
        %>
        <div class="container border" style="width: 600px">
            <br>
            <h2 class="text-center">MUEBLE DEVUELTO</h2>
            <br>
            <input type="hidden" name="opcion" value="2">
            <div class="form-row">
                <div class="form-group col-md-6">
                    <label>ID ENSAMBLE</label>
                    <input type="number" class="form-control" name="idEnsamble" value="<%=ensamble.getIdEnsamble()%>" readonly>
                </div>
                <div class="form-group col-md-6">
                    <label>FECHA DE ENSAMBLE</label>
                    <input type="date" class="form-control" name="fecha" value="<%=ensamble.getFecha()%>" readonly>
                </div>
            </div>
            <div class="form-group">
                <label for="inputState">USUARIO RESPONSABLE</label>
                <select name="usuarioResponsable" class="form-control">
                    <option selected><%=ensamble.getUsuario().getNombre_usuario()%></option>
                </select>
            </div>
            <div class="form-group">
                <label for="inputState">TIPO DE MUEBLE</label>
                <select name="tipoMueble" class="form-control">
                    <option selected><%=ensamble.getTipoMueble().toUpperCase()%></option>
                </select>
            </div>

            <br>
        </div>
        <br>
        <div class="container border" style="width: 600px">
            <br>
            <h2 class="text-center">PIEZAS DE ENSAMBLE</h2>
            <p>Seleccione las piezas que se reintegrarán a la fábrica</p>
            <form action="${pageContext.request.contextPath}/ReintegrarPiezas" method="POST">
                <input type="hidden" class="form-control" name="idDevolucion" value="<%=dev.getIdDevolucion()%>" readonly>
                <input type="hidden" class="form-control" name="idEnsamble" value="<%=ensamble.getIdEnsamble()%>" readonly>
                <% int contador = 0;%>
                <div class="table-responsive">
                    <table class="table table-bordered">
                        <thead class="thead-dark">
                            <tr>
                                <th class="text-center">ID</th>
                                <th>TIPO DE PIEZA</th>
                                <th>PRECIO</th>
                                <th class="text-center">¿Reintegrar?</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% 
                                ArrayList<Pieza> piezas = ensamble.getPiezas();
                                double precioCosto = 0;
                            %>
                            <%for (Pieza pieza : piezas) {%>
                            <%contador++;%>
                            <tr>
                                <td class="text-center"><%=pieza.getIdPieza()%></td>
                                <td><%=pieza.getTipoPieza().toUpperCase()%></td>
                                <td class="text-center">Q<%=pieza.getPrecio()%></td>
                                <td class="text-center"><input type="checkbox" name="pieza<%=contador%>" value="<%=pieza.getIdPieza()%>"> SI</td>
                                    <%precioCosto+= pieza.getPrecio();%>
                            </tr>
                            <%}%>
                            <tr>
                                <th class="text-center" colspan="2">PRECIO COSTO</td>
                                <th class="text-center" colspan="2">Q<%=precioCosto%></td>
                            </tr>
                        </tbody>
                    </table>
                    <input type="hidden" class="form-control" name="cantidadPiezas" value="<%=contador%>" readonly>
                    <input type="hidden" class="form-control" name="precioCosto" value="<%=precioCosto%>" readonly>
                    <div class="text-center">
                        <button type="submit" class="btn btn-success">REINTEGRAR PIEZAS</button>
                    </div>

                </div>
            </form>
            <br>
        </div>
        <%}%>
    </body>
</html>
