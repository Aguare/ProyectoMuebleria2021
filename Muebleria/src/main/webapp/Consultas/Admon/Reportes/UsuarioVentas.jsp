<%-- 
    Document   : VentasDia
    Created on : 3/09/2021, 23:42:09
    Author     : aguare
--%>

<%@page import="ObtenerObjetos.ObtenerAd"%>
<%@page import="EntidadesVenta.Cliente"%>
<%@page import="ObtenerObjetos.ObtenerUC"%>
<%@page import="EntidadesVenta.Factura"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ObtenerObjetos.ObtenerV"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Compras del Cliente</title>
        <script src="${pageContext.request.contextPath}/resources/JS/Exportar.js"></script>
    </head>
    <body>
        <jsp:include page="../../../resources/CSS/RecursosCSS.jsp"></jsp:include>
        <jsp:include page="../../../resources/JS/RecursosJS.jsp"></jsp:include>
        <jsp:include page="../../../resources/JS/RecursosJS.jsp"></jsp:include>
        <jsp:include page="../../../Menus/Financiero.jsp"></jsp:include>
            <br>
        <%
            ObtenerAd obtener = new ObtenerAd();
            String fechaInicial = "";
            String fechaFinal = "";
            ArrayList<String[]> datos = obtener.obtenerUsuarioConMasVentas();
            if (request.getAttribute("datos") != null) {
                fechaInicial = String.valueOf(request.getAttribute("fechaInicial"));
                fechaFinal = String.valueOf(request.getAttribute("fechaFinal"));
                datos = (ArrayList<String[]>) request.getAttribute("datos");
            }
        %>
        <div class="container">
            <h1 class="text-center">USUARIO CON MÁS VENTAS EN UN INTERVALO DE TIEMPO</h1>
            <div class="container border text-center">
                <br>
                <p class="bg-info text-white"> Puede filtrar su búsqueda interactuando con las siguientes opciones. Las fechas vacías muestran a todos los usuarios</p>
                <p class="bg-info text-white"> Si selecciona una fecha solo se mostrará al usuario con más ventas</p>
                <form action="${pageContext.request.contextPath}/UsuarioVentas" method="POST">
                    <div class="form-check-inline">
                        <label class="form-check-label">
                            Fecha Inicial: <input type="date" class="form-group" name="fechaInicial" value="<%=fechaInicial%>">
                        </label>
                    </div>
                    <div class="form-check-inline">
                        <label class="form-check-label">
                            Fecha Final: <input type="date" class="form-group" name="fechaFinal" value="<%=fechaFinal%>">
                        </label>
                    </div>
                    <div class="custom-control custom-radio custom-control-inline">
                        <button type="submit" class="btn btn-outline-primary">Buscar</button>
                    </div>
                </form>
            </div>
            <br>
            <button type="button" class="btn btn-primary btn-block" id="botonExportar">EXPORTAR A CSV</button>
            <br>
            <div class="table-responsive-lg table-hover">
                <table class="table text-center" id="tabla">
                    <thead class="thead-dark">
                        <tr>
                            <th>USUARIO</th>
                            <th>CANTIDAD VENTAS</th>
                            <th>TOTAL GENERADO</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%if (datos.isEmpty()) {%>
                        <tr class="table-warning">
                            <td colspan="3">NO HAY REGISTROS</td>
                        </tr>
                        <%}%>
                        <%for (String[] fila : datos) {%>
                        <tr>
                            <%for (int i = 0; i < fila.length; i++) {%>
                            <%if (i == fila.length-1) {%>
                            <td>Q<%=fila[i]%></td>
                            <%}else{%>
                            <td><%=fila[i]%></td>
                            <%}%>
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
