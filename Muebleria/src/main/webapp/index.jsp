<%-- 
    Document   : index
    Created on : 23/08/2021, 01:06:10
    Author     : aguare
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Bienvenido</title>
    </head>
    <body>
        <jsp:include page="resources/CSS/RecursosCSS.jsp"></jsp:include>
        <jsp:include page="Menus/Principal.jsp"></jsp:include>
        <style><%@include file="resources/CSS/barraMenu.css"%></style>
        <br>
        <div class="container border ml-auto">
            <br><center><h1 class="bg-light">ENTREGA DE PROYECTO</h1></center>
            <iframe id="oak-embed" style="display: block; margin: 1px auto; border: 0px none;
                    " src="https://embed-countdown.onlinealarmkur.com/es/#2021-09-06T00:14:00@America%2FGuatemala" width="600" 
                    height="150"></iframe>
        </div>
    </body>
</html>
