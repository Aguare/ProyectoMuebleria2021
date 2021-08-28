<%-- 
    Document   : CargaArchivo
    Created on : 23/08/2021, 00:11:41
    Author     : aguare
--%>

<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Cargar Archivo</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <jsp:include page="../resources/CSS/RecursosCSS.jsp"></jsp:include>
        <style><%@include file="../resources/CSS/barraMenu.css"%></style>
        <%
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setDateHeader("Expires", -1);
            if (request.getSession().getAttribute("Usuario") == null) {
                request.getRequestDispatcher("../index.jsp").forward(request, response);
            } else {
        %>
        <% if (request.getAttribute("mostrar") == null) {%>
        <style><%@include file="../resources/CSS/barraMenu.css"%></style>
        <div class="container p-3 my-3 border"> 
            <h1 class="bg-dark text-white text-center" >Seleccione el archivo de carga (TXT)(UTF-8)</h1>
            <h2 class="text-center">El archivo debe cumplir con los requerimientos, se le mostrarán los errores y lo que ha sido cargado correctamente</h2>
            <div class="text-center">
                <form action="${pageContext.request.contextPath}/CargaArchivo-Servlet" method="POST" enctype="multipart/form-data">
                    <input type="file" class="form-control-file border mx-auto"  style="width: 450px" name="archivoCarga" id="archivoCarga" accept=".txt" required>
                    <br>
                    <button type="submit" class="btn btn-success" value="cargar">Cargar Archivo</button>
                    <br><br>
                </form>
            </div>
        </div>
        <% } else {%>
        <jsp:include page="../resources/CSS/RecursosCSS.jsp"></jsp:include>
        <style><%@include file="../resources/CSS/barraMenu.css"%></style>
        <jsp:include page="../Menus/Financiero.jsp"></jsp:include>
            <br><br>
            <div class="container col-auto text-center">
                <label class="text-center">DATOS INGRESADOS CORRECTAMENTE</label><br>
                <textarea name="ingresoC" rows="20" cols="100" readonly>
                <%
                    ArrayList<String> c = (ArrayList<String>) request.getAttribute("correcto");
                    for (String string : c) {
                %>
                <%= string%>
                <% } %>

            </textarea><br><br>
            <label class="text-center">DATOS INGRESADOS INCORRECTAMENTE</label><br>
            <textarea name="ingresoC" rows="20" cols="100" readonly>
                <%
                    ArrayList<String> e = (ArrayList<String>) request.getAttribute("noReconocido");
                    for (String string : e) {
                %>
                <%= string%>
                <% } %>

            </textarea><br>
        </div>
        <%}%>
        <%}%>
    </body>
</html>
