<%-- 
    Document   : VentasDia
    Created on : 3/09/2021, 23:42:09
    Author     : aguare
--%>

<%@page import="EntidadesVenta.Mueble"%>
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
            ObtenerV obtenerV = new ObtenerV();
            ArrayList<String[]> datos = obtener.obtenerUsuarioConMasGanancia();
            ArrayList<Factura> facturas = obtenerV.obtenerFacturasTodas();
            if (request.getAttribute("datos") != null) {
                facturas = (ArrayList<Factura>) request.getAttribute("facturas");
                fechaInicial = String.valueOf(request.getAttribute("fechaInicial"));
                fechaFinal = String.valueOf(request.getAttribute("fechaFinal"));
                datos = (ArrayList<String[]>) request.getAttribute("datos");
            }
        %>
        <div class="container">
            <h2 class="text-center">USUARIO CON MÁS GANANCIAS EN UN INTERVALO DE TIEMPO</h2>
            <div class="container border text-center">
                <br>
                <p class="bg-info text-white"> Puede filtrar su búsqueda interactuando con las siguientes opciones. Las fechas vacías muestran a todos los usuarios</p>
                <p class="bg-info text-white"> Si selecciona una fecha solo se mostrará al usuario con más ganancias</p>
                <form action="${pageContext.request.contextPath}/UsuarioGanancias" method="POST">
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
                            <th>TOTAL GENERADO</th>
                            <th>DE</th>
                            <th>A</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%if (datos.isEmpty()) {%>
                        <tr class="table-warning">
                            <td colspan="4">NO HAY REGISTROS</td>
                        </tr>
                        <%}%>
                        <%for (String[] fila : datos) {%>
                        <tr>
                            <%for (int i = 0; i < fila.length; i++) {%>
                            <%if (i == 1) {%>
                            <td>Q<%=fila[i]%></td>
                            <%}else{%>
                            <td><%=fila[i]%></td>
                            <%}%>
                            <%}%>
                        </tr>
                        <%}%>
                    </tbody>
                </table>
                <table class="table text-center" id="tabla2">
                    <thead class="thead-dark">
                        <tr>
                            <th>FECHA</th>
                            <th>USUARIO</th>
                            <th>FACTURA</th>
                            <th>MUEBLE</th>
                            <th>PRECIO</th>
                            <th>GANANCIA</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%double gananciaNeta = 0;%>
                        <%for (Factura f : facturas) {%>
                        <%if (f.getUsuario().equalsIgnoreCase(datos.get(0)[0])) {%>
                        <tr class="">
                            <% ArrayList<Mueble> muebles = obtenerV.obtenerMueblesSegunFactura(f.getNoFactura()); %>
                            <%for (Mueble mueble : muebles) {%>
                            <td><%=f.getFecha().toString()%></td>
                            <td><%=f.getUsuario().toString()%></td>
                            <td><%=f.getNoFactura()%></td>
                            <td><%=mueble.getTipoMueble().toUpperCase()%></td>
                            <td><%=mueble.getPrecioVenta()%></td>
                            <%double gana = Math.round((mueble.getPrecioVenta()-mueble.getPrecioCosto())*100)/100;
                            gananciaNeta+=gana;%>
                            <td>Q<%=gana%></td>
                        </tr>
                        <%}%>
                        <%}%>
                        <%}%>
                        <tr class="table-primary">
                            <td colspan="4">GANANCIA NETA</td>
                            <td colspan="2">Q.<%=gananciaNeta%></td>
                        </tr>
                        <%if (facturas.isEmpty()) {%>
                        <tr class="table-warning">
                            <td colspan="9">NO HAY REGISTROS</td>
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
                anchorElement.download = "UsuarioMasVentas.csv";
                anchorElement.click();

                setTimeout(() => {
                    URL.revokeObjectURL(blobUrl);
                }, 500);
            });
        });
        </script>
    </body>
</html>
