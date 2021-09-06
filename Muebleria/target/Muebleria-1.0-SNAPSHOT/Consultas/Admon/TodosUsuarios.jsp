<%-- 
    Document   : VentasDia
    Created on : 3/09/2021, 23:42:09
    Author     : aguare
--%>

<%@page import="EntidadesFabrica.Usuario"%>
<%@page import="ObtenerObjetos.ObtenerUC"%>
<%@page import="EntidadesVenta.Mueble"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ObtenerObjetos.ObtenerV"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Muebles Disponibles</title>
    </head>
    <body>
        <jsp:include page="../../resources/CSS/RecursosCSS.jsp"></jsp:include>
        <jsp:include page="../../resources/JS/RecursosJS.jsp"></jsp:include>
        <jsp:include page="../../resources/JS/RecursosJS.jsp"></jsp:include>
        <jsp:include page="../../Menus/Financiero.jsp"></jsp:include>
            <br>
        <%
            ObtenerUC obtenerU = new ObtenerUC();
            ArrayList<Usuario> usuariosF = obtenerU.obtenerUsuariosSegunDepartamento(1);
            ArrayList<Usuario> usuariosV = obtenerU.obtenerUsuariosSegunDepartamento(2);
        %>
        <div class="container text-center">
            <h1>USUARIOS REGISTRADOS</h1>
            <input class="form-control" id="entrada" type="text" placeholder="BUSQUEDA">
            <br>
            <div class="table-responsive table-hover" style="height: 670px">
                <table class="table">
                    <thead class="thead-dark">
                        <tr>
                            <th>USUARIO</th>
                            <th>DEPARTAMENTO</th>
                            <th>ESTADO</th>
                            <th>OPCIONES</th>
                        </tr>
                    </thead>
                    <tbody id="tabla">
                        <%if (usuariosF.isEmpty() && usuariosV.isEmpty()) {%>
                        <tr class="table-warning">
                            <td colspan="3">NO HAY MUEBLES DISPONIBLES</td>
                        </tr>
                        <%}%>
                        <%for (Usuario u : usuariosF) {%>
                        <tr>
                            <td><%=u.getNombre_usuario()%></td>
                            <td><%=u.getNombreDepartamento()%></td>
                            <%if (u.isAcceso()) {%>
                            <td><p class="bg-success text-white">CON ACCESO</p></td>
                            <%}else{%>
                            <td><p class="bg-warning text-white">SIN ACCESO</p></td>
                            <%}%>
                            <td class="text-center">
                                <a href="${pageContext.request.contextPath}/CambioUsuario?usuario=<%=u.getNombre_usuario()%>&accion=2" class="btn btn-info" role="button">Editar</a>
                            </td>
                        </tr>
                        <%}%>
                        <%for (Usuario u : usuariosV) {%>
                        <tr>
                            <td><%=u.getNombre_usuario()%></td>
                            <td><%=u.getNombreDepartamento()%></td>
                            <%if (u.isAcceso()) {%>
                            <td><p class="bg-success text-white">CON ACCESO</p></td>
                            <%}else{%>
                            <td><p class="bg-warning text-white">SIN ACCESO</p></td>
                            <%}%>
                            <td class="text-center">
                                <a href="${pageContext.request.contextPath}/CambioUsuario?usuario=<%=u.getNombre_usuario()%>&accion=2" class="btn btn-info" role="button">Editar</a>
                            </td>
                        </tr>
                        <%}%>
                    </tbody>
                </table>
            </div>
        </div>
        <script>
            $(document).ready(function(){
                $("#entrada").on("keyup", function() {
                    var value = $(this).val().toLowerCase();
                    $("#tabla tr").filter(function() {
                    $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
                    });
                });
            });
        </script> 
    </body>
</html>
