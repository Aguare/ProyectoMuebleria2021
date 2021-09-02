<%-- 
    Document   : EditarPieza
    Created on : 29/08/2021, 23:24:21
    Author     : marco
--%>

<%@page import="java.time.LocalDate"%>
<%@page import="EntidadesFabrica.Usuario"%>
<%@page import="EntidadesFabrica.Ensamble"%>
<%@page import="EntidadesFabrica.TipoMueble"%>
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
        <style><%@include file="../../resources/CSS/RecursosCSS.jsp"%></style>
        <style><%@include file="../../resources/JS/RecursosJS.jsp"%></style>
    </head>
    <body>
        <%
            ObtenerObj obtener = new ObtenerObj();
            ArrayList<TipoMueble> tipoMueble = obtener.obtenerTipoMuebles();
            ArrayList<Usuario> usuarios = obtener.obtenerUsuariosSegunDepartamento(1);
        %>
        <br>
        <%
            if (request.getAttribute("ensamblar") == null && request.getAttribute("ensamble") != null) {
                Ensamble ensamble = (Ensamble) request.getAttribute("ensamble");
        %>
        <div class="container border" style="width: 600px">
            <br>
            <h2 class="text-center">EDITAR ENSAMBLE</h2>
            <br>
            <form action="${pageContext.request.contextPath}/CambioEnsamble" method="POST">
                <input type="hidden" name="opcion" value="2">
                <div class="form-row">
                    <div class="form-group col-md-6">
                        <label>ID ENSAMBLE</label>
                        <input type="number" class="form-control" name="idEnsamble" value="<%=ensamble.getIdEnsamble()%>" readonly>
                    </div>
                    <div class="form-group col-md-6">
                        <label>FECHA DE ENSAMBLE</label>
                        <input type="date" class="form-control" name="fecha" value="<%=ensamble.getFecha()%>">
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputState">USUARIO RESPONSABLE</label>
                    <select name="usuarioResponsable" class="form-control">
                        <option selected><%=ensamble.getUsuario().getNombre_usuario()%></option>
                        <%for (Usuario user : usuarios) {
                                if (!user.getNombre_usuario().equalsIgnoreCase(ensamble.getUsuario().getNombre_usuario())) {%>
                        <option><%=user.getNombre_usuario()%></option>
                        <%
                                }
                            }
                        %>
                    </select>
                </div>
                <div class="form-group">
                    <label for="inputState">TIPO DE MUEBLE</label>
                    <select name="tipoMueble" class="form-control">
                        <option selected><%=ensamble.getTipoMueble().toUpperCase()%></option>
                    </select>
                </div>
                <div class="text-center">
                    <button type="submit" class="btn btn-primary">Actualizar</button>
                </div>
            </form>
            <br>
        </div>
        <%} else {%>
        <%
            LocalDate fecha = LocalDate.now();
            Usuario usuario = (Usuario) request.getSession().getAttribute("Usuario");
        %>
        <div class="container border" style="width: 600px">
            <br>
            <h2 class="text-center">ENSAMBLAR MUEBLE</h2>
            <br>
            <form action="${pageContext.request.contextPath}/CambioEnsamble" method="POST">
                <input type="hidden" name="opcion" value="1">
                <div class="form-row">
                    <div class="form-group col-md-6">
                        <label>ID ENSAMBLE</label>
                        <input type="number" class="form-control" name="idEnsamble" placeholder="Se asignará automáticamente" readonly>
                    </div>
                    <div class="form-group col-md-6">
                        <label>FECHA DE ENSAMBLE</label>
                        <input type="date" class="form-control" name="fecha" value="<%=fecha%>" readonly>
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputState">USUARIO RESPONSABLE</label>
                    <select name="usuarioResponsable" class="form-control">
                        <option><%=usuario.getNombre_usuario()%></option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="inputState">TIPO DE MUEBLE</label>
                    <select name="tipoMueble" class="form-control">
                        <%for (TipoMueble tM : tipoMueble) {%>
                        <option><%=tM.getNombreMueble().toUpperCase()%></option>
                        <%}%>
                    </select>
                </div>
                <div class="text-center">
                    <button type="submit" class="btn btn-primary">Ensamblar</button>
                </div>
            </form>
            <br>
        </div>
        <%}%>
    </body>
</html>
