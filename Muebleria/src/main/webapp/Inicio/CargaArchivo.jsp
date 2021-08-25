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
        <link href="../CSS/cargaArchivo.css" rel="stylesheet" type="text/css">    
    </head>
    <body>
        <br>
        <% if (request.getAttribute("correcto") == null && request.getAttribute("errores") == null && request.getAttribute("user") == null) {%>           
        <div class="seleccion"> 
            <h1>Seleccione el archivo de carga (TXT)(UTF-8)</h1>
            <h2>El primer archivo es para la base de datos, los segundos para los exámenes de laboratorio (ordenes, resultados)</h2>
            <form action="ServletArchivo" method="POST" enctype="multipart/form-data">
                <label>Seleccione el archivo TXT:</label>
                <input type="file" name="archivoCarga" id="archivoCarga" accept=".txt" required>
                <button type="submit" value="cargar">Cargar Archivo</button>
                <br><br>
            </form> 
        </div>
        <%} else {%>
        <style><%@include file="../CSS/cargaArchivo.css"%></style>
        <br><br>
        <label>DATOS INGRESADOS CORRECTAMENTE</label><br>
        <textarea name="ingresoC" rows="20" cols="100" readonly>
            <%
                ArrayList<String> c = (ArrayList<String>) request.getAttribute("correcto");
                for (String string : c) {
            %>
            <%= string%>
            <% } %>

        </textarea><br><br>
        <label>DATOS INGRESADOS CORRECTAMENTE</label><br>
        <textarea name="ingresoC" rows="20" cols="100" readonly>
            <%
                ArrayList<String> e = (ArrayList<String>) request.getAttribute("errores");
                for (String string : e) {
            %>
            <%= string%>
            <% } %>

        </textarea><br>

        <%}%>
    </body>
</html>
