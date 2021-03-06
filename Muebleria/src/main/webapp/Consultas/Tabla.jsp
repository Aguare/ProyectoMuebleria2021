<%-- 
    Document   : Tabla
    Created on : 31/08/2021, 23:36:29
    Author     : marco
--%>

<%@page import="EntidadesFabrica.Usuario"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tabla</title>
        <jsp:include page="../resources/CSS/RecursosCSS.jsp"></jsp:include>
        <jsp:include page="../resources/JS/RecursosJS.jsp"></jsp:include>
        <jsp:include page="../resources/JS/RecursosJS.jsp"></jsp:include>
            <script src="../../../resources/JS/Exportar.js"></script>
            <style><%@include file="../resources/CSS/barraMenu.css"%></style>
    </head>
    <body>
        <%
            ArrayList<String> titulos = (ArrayList<String>) request.getAttribute("titulos");
            ArrayList<String[]> contenido = (ArrayList<String[]>) request.getAttribute("contenido");
        %>
        <div class="container">
            <br>
            <button type="button" class="btn btn-primary btn-block" id="botonExportar">EXPORTAR A CSV</button>
            <br>
            <div class="table-responsive" style="height: 670px">
                <table class="table table-bordered">
                    <thead class="thead-dark">
                        <tr>
                            <c:forEach items="${titulos}" var="titulo">
                                <th class="text-center">${titulo}</th>
                                </c:forEach>
                        </tr>
                    </thead>
                    <tbody>
                        <%for (String[] linea : contenido) {%>
                        <tr>
                            <%for (int i = 0; i < linea.length; i++) {%>
                            <td class="text-center"><%=linea[i]%></td>
                            <%}%>
                        </tr>
                        <%}%>
                    </tbody>
                </table>
            </div>
        </div>
        <script>
        const dataTable = document.getElementById("tabla");
        const btnExportToCsv = document.getElementById("botonExportar");

        btnExportToCsv.addEventListener("click", () => {
            const exporter = new TableCSVExporter(dataTable);
            const csvOutput = exporter.convertToCSV();
            const csvBlob = new Blob([csvOutput], { type: "text/csv" });
            const blobUrl = URL.createObjectURL(csvBlob);
            const anchorElement = document.createElement("a");

            anchorElement.href = blobUrl;
            anchorElement.download = "UsuarioConMasVentas.csv";
            anchorElement.click();

            setTimeout(() => {
            URL.revokeObjectURL(blobUrl);
            }, 500);
        });
        </script>
    </body>
</html>
