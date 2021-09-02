<%-- 
    Document   : EditarPieza
    Created on : 29/08/2021, 23:24:21
    Author     : marco
--%>

<%@page import="ModificarObj.FabricaCRUD"%>
<%@page import="EntidadesFabrica.TipoPiezas"%>
<%@page import="java.util.ArrayList"%>
<%@page import="SQL.ObtenerObj"%>
<%@page import="EntidadesFabrica.Pieza"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Editar Pieza</title>
        <jsp:include page="../../Menus/Fabrica.jsp"></jsp:include>
        <jsp:include page="../../resources/JS/RecursosJS.jsp"></jsp:include>
        <jsp:include page="../../resources/JS/RecursosJS.jsp"></jsp:include>
        <style><%@include file="../../resources/CSS/RecursosCSS.jsp"%></style>
        <style><%@include file="../../resources/JS/RecursosJS.jsp"%></style>
    </head>
    <body>
        <%
            ObtenerObj obtener = new ObtenerObj();
            ArrayList<TipoPiezas> tipoPiezas = obtener.obtenerTipoPiezas(1);
        %>
        <br>
        <%
            if (request.getAttribute("crear") == null && request.getAttribute("pieza") != null) {
                Pieza pieza = (Pieza) request.getAttribute("pieza");
        %>
        <div class="container border" style="width: 600px">
            <br>
            <h2 class="text-center">EDITAR PIEZA</h2>
            <br>
            <form action="${pageContext.request.contextPath}/CambioPiezas" method="POST">
                <input type="hidden" name="opcion" value="1">
                <div class="form-row">
                    <div class="form-group col-md-6">
                        <label>ID PIEZA</label>
                        <input type="number" class="form-control" name="idPieza" value="<%=pieza.getIdPieza()%>" readonly>
                    </div>
                    <div class="form-group col-md-6">
                        <label>PRECIO</label>
                        <input type="number" min="0" step="0.01" class="form-control" name="precio" value="<%=pieza.getPrecio()%>">
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputState">TIPO DE PIEZA</label>
                    <select name="tipoPieza" class="form-control">
                        <option selected><%=pieza.getTipoPieza().toUpperCase()%></option>
                        <%for (TipoPiezas tipoPieza : tipoPiezas) {
                                if (!tipoPieza.getNombrePieza().equalsIgnoreCase(pieza.getTipoPieza())) {%>
                        <option><%=tipoPieza.getNombrePieza().toUpperCase()%></option>
                        <%
                                }
                            }
                        %>
                    </select>
                </div>
                <div class="text-center">
                    <button type="submit" class="btn btn-primary">Actualizar</button>
                </div>
            </form>
            <br>
        </div>
        <%} else {%>
        <div class="container border" style="width: 600px">
            <br>
            <h2 class="text-center">CREAR PIEZA</h2>
            <br>
            <form action="${pageContext.request.contextPath}/CambioPiezas" method="POST">
                <input type="hidden" name="opcion" value="2">
                <div class="form-row">
                    <div class="form-group col-md-6">
                        <label>ID PIEZA</label>
                        <input type="number" class="form-control" name="idPieza" id="idPieza"  placeholder="Se asignará después" readonly>
                    </div>
                    <div class="form-group col-md-6">
                        <label>Precio</label>
                        <input type="number" min="0" step="0.01" class="form-control" name="precioC" id="precioC" value="0">
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputState">TIPO DE PIEZA</label>
                    <select name="tipoPiezaC" id="tipoPiezaC" class="form-control">
                        <<option>NUEVO TIPO</option>
                        <%for (TipoPiezas tipoPieza : tipoPiezas) {%>
                        <option><%=tipoPieza.getNombrePieza().toUpperCase()%></option>
                        <%
                            }
                        %>
                    </select>
                </div>
                <div class="form-group" id="nuevoTipo">
                    <label>NUEVO TIPO DE PIEZA</label>
                    <input type="text"  class="form-control" name="nombreNuevo" placeholder="NOMBRE PARA EL TIPO DE PIEZA" minlength="5" maxlength="50">
                </div>
                <div class="text-center">
                    <button type="submit" class="btn btn-primary">Crear Pieza</button>
                </div>
            </form>
            <br>
        </div>
        <script>
            $("#tipoPiezaC").change(function (e) {
                e.preventDefault();
                if (this.value === "NUEVO TIPO") {
                    $("#nuevoTipo").removeClass("d-none");
                } else {
                    $("#nuevoTipo").addClass("d-none");
                }
            });
        </script>
        <%}%>
    </body>
</html>
