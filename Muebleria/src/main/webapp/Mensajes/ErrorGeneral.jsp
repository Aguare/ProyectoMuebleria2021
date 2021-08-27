<%-- 
    Document   : SinAcceso
    Created on : 26/08/2021, 23:01:45
    Author     : marco
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error</title>
    </head>
    <body>
        <style><%@include file="../resources/CSS/RecursosCSS.jsp"%></style>
        <style><%@include file="../resources/JS/RecursosJS.jsp"%></style>
        <jsp:include page="../Menus/Principal.jsp"></jsp:include>
        <style><%@include file="../resources/CSS/barraMenu.css"%></style>
        <% String mensaje = String.valueOf(request.getAttribute("mensaje"));%>
        <div class="container p-3 my-3 bg-primary text-white">
            <center><h1>ERROR:</h1></center>
            <center><h2><%=mensaje%></h2></center>
        </div>

    </body>
</html>
