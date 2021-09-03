<%-- 
    Document   : EditarPieza
    Created on : 29/08/2021, 23:24:21
    Author     : marco
--%>

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
        <title>Editar Pieza</title>
        <jsp:include page="../../Menus/Fabrica.jsp"></jsp:include>
        <style><%@include file="../../resources/CSS/RecursosCSS.jsp"%></style>
        <style><%@include file="../../resources/JS/RecursosJS.jsp"%></style>
    </head>
    <body>
        <%
            ObtenerV obtener = new ObtenerV();
            ObtenerUC obtenerUC = new ObtenerUC();
            ArrayList<TipoMueble> tipoMueble = obtener.obtenerTipoMuebles();
            ArrayList<Usuario> usuarios = obtenerUC.obtenerUsuariosSegunDepartamento(1);
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
        <br>
        <div class="container border" style="width: 600px">
            <br>
            <h2 class="text-center">PIEZAS DE ENSAMBLE</h2>
            <div class="table-responsive">
                <table class="table table-bordered">
                    <thead class="thead-dark">
                        <tr>
                            <th class="text-center">ID</th>
                            <th>TIPO DE PIEZA</th>
                            <th class="text-center">PRECIO</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% 
                            ArrayList<Pieza> piezas = ensamble.getPiezas();
                            double precioCosto = 0;
                        %>
                        <%for (Pieza pieza : piezas) {%>
                        <tr>
                            <td class="text-center"><%=pieza.getIdPieza()%></td>
                            <td><%=pieza.getTipoPieza().toUpperCase()%></td>
                            <td class="text-center">Q<%=pieza.getPrecio()%></td>
                            <%precioCosto+= pieza.getPrecio();%>
                        </tr>
                        <%}%>
                        <tr>
                            <th class="text-center" colspan="2">PRECIO COSTO</td>
                            <th class="text-center" colspan="2">Q<%=precioCosto%></td>
                        </tr>
                    </tbody>
                </table>
            </div>
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
