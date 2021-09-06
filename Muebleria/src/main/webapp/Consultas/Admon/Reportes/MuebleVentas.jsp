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
        <script src="${pageContext.request.contextPath}/resources/JS/ExportarDosT.js"></script>
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
            ArrayList<String[]> datos = obtener.muebleMasVendido(1);
            ArrayList<Factura> facturas = new ArrayList<>();
            String[] titulos = datos.get(0);
            datos.remove(0);
            if (request.getAttribute("datos") != null) {
                facturas = (ArrayList<Factura>) request.getAttribute("facturas");
                fechaInicial = String.valueOf(request.getAttribute("fechaInicial"));
                fechaFinal = String.valueOf(request.getAttribute("fechaFinal"));
                datos = (ArrayList<String[]>) request.getAttribute("datos");
                titulos = datos.get(0);
                datos.remove(0);
            }
        %>
        <div class="container">
            <h1 class="text-center">MUEBLE CON MÁS VENTAS EN UN INTERVALO DE TIEMPO</h1>
            <div class="container border text-center">
                <br>
                <p class="bg-info text-white"> Puede filtrar su búsqueda interactuando con las siguientes opciones. Las fechas vacías muestran a todos los muebles</p>
                <p class="bg-info text-white"> Si selecciona una fecha solo se mostrará el mueble con más ventas</p>
                <form action="${pageContext.request.contextPath}/MuebleVentas" method="POST">
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
                            <%for (int i = 0; i < titulos.length; i++) {%>
                            <th><%=titulos[i]%></th>
                                <%}%>
                        </tr>
                    </thead>
                    <tbody>
                        <%if (datos.isEmpty()) {%>
                        <tr class="table-warning">
                            <td colspan="<%=titulos.length%>">NO HAY REGISTROS</td>
                        </tr>
                        <%}%>
                        <%for (String[] fila : datos) {%>
                        <tr>
                            <%for (int i = 0; i < fila.length; i++) {%>
                            <td><%=fila[i]%></td>
                            <%}%>
                        </tr>
                        <%}%>
                    </tbody>
                </table>
                <table class="table text-center" id="tabla2">
                    <thead>
                        <tr class="thead-dark"><th colspan="<%=titulos.length%>">DETALLES DE VENTAS</th></tr>
                        <tr class="thead-light">
                            <th>FACTURA</th>
                            <th>NIT</th>
                            <th>CLIENTE</th>
                            <th>TOTAL</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        <%for (Factura f : facturas) {%>
                        <tr>
                            <td><%=f.getNoFactura()%></td>
                            <td><%=f.getCliente().getNIT()%></td>
                            <td><%=f.getCliente().getNombre().toUpperCase()%></td>
                            <td><%=f.getTotal()%></td>
                            <td>
                                <a href="${pageContext.request.contextPath}/Consultas/Ventas/Factura.jsp?noFactura=<%=f.getNoFactura()%>" class="btn btn-info btn-sm" role="button">Ver</a>
                            </td>
                        </tr>
                        <%}%>
                    </tbody>
                </table>
            </div>
        </div>
        <script>
        $(document).ready(function(){
            $("#search").on("keyup", function() {
                    var value = $(this).val().toLowerCase();
                    $("#tabla tbody tr").filter(function() {
                    $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
                });
            });

            // descargar multiples tablas en 1
            let dataTable = document.getElementById("tabla");
            let dataTable1 = document.getElementById("tabla2");

            new TableCSVExporter(dataTable, dataTable1, true).convertToCSV();

            $("#botonExportar").on("click", function () {
                const exporter = new TableCSVExporter(dataTable, dataTable1);
                const csvOutput = exporter.convertToCSV();
                const csvBlob = new Blob([csvOutput], {type: "text/csv"});
                const blobUrl = URL.createObjectURL(csvBlob);
                const anchorElement = document.createElement("a");

                anchorElement.href = blobUrl;
                anchorElement.download = "MueblesMasVendidos.csv";
                anchorElement.click();

                setTimeout(() => {
                    URL.revokeObjectURL(blobUrl);
                }, 500);
            });
        });
        </script>
    </body>
</html>
