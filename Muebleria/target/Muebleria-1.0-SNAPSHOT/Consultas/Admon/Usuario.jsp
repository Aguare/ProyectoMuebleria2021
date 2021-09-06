<%-- 
    Document   : EditarPieza
    Created on : 29/08/2021, 23:24:21
    Author     : marco
--%>

<%@page import="ObtenerObjetos.ObtenerAd"%>
<%@page import="EntidadesFabrica.Usuario"%>
<%@page import="ObtenerObjetos.ObtenerF"%>
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
        <jsp:include page="../../Menus/Financiero.jsp"></jsp:include>
        <jsp:include page="../../resources/JS/RecursosJS.jsp"></jsp:include>
        <jsp:include page="../../resources/JS/RecursosJS.jsp"></jsp:include>
        <style><%@include file="../../resources/CSS/RecursosCSS.jsp"%></style>
        <style><%@include file="../../resources/JS/RecursosJS.jsp"%></style>
    </head>
    <body>
        <%
            ObtenerAd obtenerA = new ObtenerAd();
            ArrayList<String> departamentos = obtenerA.obtenerDepartamentos();
        %>
        <br>
        <%
            if (request.getAttribute("crear") == null && request.getAttribute("usuario") != null) {
                Usuario usuario = (Usuario) request.getAttribute("usuario");
        %>
        <div class="container border" style="width: 600px">
            <br>
            <h2 class="text-center">EDITAR USUARIO</h2>
            <br>
            <form action="${pageContext.request.contextPath}/CambioUsuario" method="POST">
                <input type="hidden" name="opcion" value="2">
                <div class="form-row">
                    <div class="form-group col-md-6">
                        <label>USUARIO</label>
                        <input type="text" class="form-control" name="nombreUsuario" value="<%=usuario.getNombre_usuario()%>" readonly>
                    </div>
                    <div class="form-group col-md-6">
                        <label>ACCESO</label>
                        <select name="tieneAcceso" class="form-control">
                            <% if (usuario.isAcceso()) {%>
                            <option selected>TIENE ACCESO</option>
                            <option>SIN ACCESO</option>
                            <%} else {%>
                            <option>TIENE ACCESO</option>
                            <option selected>SIN ACCESO</option>
                            <%}%>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <p>Click en el boton azúl para mostrar / ocultar</p>
                    <label>CONTRASEÑA</label>
                    <div class="input-group">
                        <input ID="txtPassword" type="Password" Class="form-control" value="<%=usuario.getPassword()%>" name="password" minlength="5" required>
                        <div class="input-group-append">
                            <button id="show_password" class="btn btn-primary" type="button" onclick="mostrarPassword()"> <span class="fa fa-eye-slash icon"></span> </button>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label>CONFIRMAR CONTRASEÑA</label>
                    <div class="input-group">
                        <input ID="txtPassword" type="Password" Class="form-control" value="<%=usuario.getPassword()%>" name="password2" minlength="5" required>
                        <div class="input-group-append">
                            <button id="show_password" class="btn btn-primary" type="button" onclick="mostrarPassword()"> <span class="fa fa-eye-slash icon"></span> </button>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputState">DEPARTAMENTO</label>
                    <select name="departamento" class="form-control">
                        <option selected><%=usuario.getNombreDepartamento().toUpperCase()%></option>
                        <%for (String departamento : departamentos) {
                                if (!departamento.equalsIgnoreCase(usuario.getNombreDepartamento())) {%>
                        <option><%=departamento.toUpperCase()%></option>
                        <%}}%>
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
            <h2 class="text-center">CREAR USUARIO</h2>
            <br>
            <form action="${pageContext.request.contextPath}/CambioUsuario" method="POST" class="was-validated">
                <input type="hidden" name="opcion" value="1">
                <div class="form-row">
                    <div class="form-group col-md-6">
                        <label>USUARIO</label>
                        <input type="text" class="form-control" name="nombreUsuario" minlength="5" maxlength="20" required>
                    </div>
                    <div class="form-group col-md-6">
                        <label>ACCESO</label>
                        <select name="tieneAcceso" class="form-control">
                            <option>TIENE ACCESO</option>
                            <option>SIN ACCESO</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <p>Click en el boton azúl para mostrar / ocultar</p>
                    <label>CONTRASEÑA</label>
                    <div class="input-group">
                        <input ID="txtPassword" type="Password" Class="form-control" name="password" minlength="5" required>
                        <div class="input-group-append">
                            <button id="show_password" class="btn btn-primary" type="button" onclick="mostrarPassword()">
                                <span class="fa fa-eye-slash icon"></span>
                            </button>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label>CONFIRMAR CONTRASEÑA</label>
                    <div class="input-group">
                        <input ID="txtPassword" type="Password" Class="form-control" name="password2" minlength="5" required>
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputState">DEPARTAMENTO</label>
                    <select name="departamento" class="form-control">
                        <%for (String departamento : departamentos) {%>
                        <option><%=departamento.toUpperCase()%></option>
                        <%}%>
                    </select>
                </div>
                <div class="text-center">
                    <button type="submit" class="btn btn-primary">CREAR</button>
                </div>
            </form>
            <br>
        </div>
        <%}%>
        <script type="text/javascript">
            function mostrarPassword(){
                var cambio = document.getElementById("txtPassword");
                if(cambio.type === "password"){
                    cambio.type = "text";
                    $('.icon').removeClass('fa fa-eye-slash').addClass('fa fa-eye');
                }else{
                    cambio.type = "password";
                    $('.icon').removeClass('fa fa-eye').addClass('fa fa-eye-slash');
                }
            } 
	
            $(document).ready(function () {
                //CheckBox mostrar contraseña
                $('#ShowPassword').click(function () {
                    $('#Password').attr('type', $(this).is(':checked') ? 'text' : 'password');
                });
            });
        </script>
    </body>
</html>
