<%-- 
    Document   : VentasDia
    Created on : 3/09/2021, 23:42:09
    Author     : aguare
--%>

<%@page import="EntidadesVenta.Mueble"%>
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
            ObtenerV obtenerV = new ObtenerV();
            ArrayList<Factura> facturas = obtener.obtenerFacturas();
            String fechaInicial = "";
            String fechaFinal = "";
            double perdida = obtener.obtenerPerdidaTotal();
            double costoProduccion = obtener.obtenerCostoProduccionTotal();
            double ganancia = 0;
            if (request.getAttribute("facturas") != null) {
                facturas = (ArrayList<Factura>) request.getAttribute("facturas");
                fechaInicial = String.valueOf(request.getAttribute("fechaInicial"));
                fechaFinal = String.valueOf(request.getAttribute("fechaFinal"));
                perdida = (double) request.getAttribute("perdida");
                costoProduccion = (double) request.getAttribute("costoProduccion");
            }
        %>
        <div class="container">
            <h1 class="text-center">GANANCIAS EN UN INTERVALO DE TIEMPO</h1>
            <div class="container border text-center">
                <br>
                <p class="bg-info text-white"> Puede filtrar su b??squeda interactuando con las siguientes opciones. Las fechas vac??as muestra las ganancias totales</p>
                <form action="${pageContext.request.contextPath}/Ganancias" method="POST">
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
            <div class="table-responsive-lg">
                <table class="table text-center" id="tabla">
                    <thead class="thead-dark">
                        <tr>
                            <th>FACTURA</th>
                            <th>NIT</th>
                            <th>CLIENTE</th>
                            <th>FECHA</th>
                            <th>MUEBLE</th>
                            <th>COSTO</th>
                            <th>VENTA</th>
                            <th>GANANCIA</th>
                            <th>ESTADO</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%for (Factura f : facturas) {%>
                        <tr class="">
                            <% ArrayList<Mueble> muebles = obtenerV.obtenerMueblesSegunFactura(f.getNoFactura()); %>
                            <%for (Mueble mueble : muebles) {%>
                            <td><%=f.getNoFactura()%></td>
                            <td><%=f.getCliente().getNIT()%></td>
                            <td><%=f.getCliente().getNombre().toUpperCase()%></td>
                            <td><%=f.getFecha().toString()%></td>
                            <td><%=mueble.getTipoMueble().toUpperCase()%></td>
                            <td><%=mueble.getPrecioCosto()%></td>
                            <td><%=mueble.getPrecioVenta()%></td>
                            <%double gana = Math.round((mueble.getPrecioVenta()-mueble.getPrecioCosto())*100)/100;%>
                            <td><%=gana%></td>
                            <%if (mueble.isDevuelto()) {%>
                            <td>DEVUELTO</td>
                            <%} else {%>
                            <td>VENDIDO</td>
                            <%ganancia+=mueble.getPrecioVenta();%>
                            <%}%>
                        </tr>
                        <%}%>
                        <%}%>
                        <%if (facturas.isEmpty()) {%>
                        <tr class="table-warning">
                            <td colspan="9">NO HAY REGISTROS</td>
                        </tr>
                        <%} else {%>
                        <tr class="table-danger">
                            <td colspan="7">P??RDIDA</td>
                            <td colspan="2">Q.<%=perdida%></td>
                        </tr>
                        <tr class="table-danger">
                            <td colspan="7">COSTO DE PRODUCCI??N</td>
                            <td colspan="2">Q.<%=costoProduccion%></td>
                        </tr>
                        <tr class="table-success">
                            <td colspan="7">GANANCIA</td>
                            <td colspan="2">Q.<%=ganancia%></td>
                        </tr>
                        <tr class="table-primary">
                            <td colspan="7">TOTAL</td>
                            <td colspan="2">Q.<%=(ganancia-perdida-costoProduccion)%></td>
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
            anchorElement.download = "GananciaEnIntervaloDeTiempo.csv";
            anchorElement.click();

            setTimeout(() => {
                URL.revokeObjectURL(blobUrl);
            }, 500);
        });
        </script>
    </body>
</html>
