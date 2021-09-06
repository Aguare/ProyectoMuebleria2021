<%-- 
    Document   : VentasDia
    Created on : 3/09/2021, 23:42:09
    Author     : aguare
--%>

<%@page import="EntidadesVenta.Devolucion"%>
<%@page import="EntidadesVenta.Compra"%>
<%@page import="EntidadesVenta.Mueble"%>
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
        <title>Devoluciones del Cliente</title>
        <script src="${pageContext.request.contextPath}/resources/JS/Exportar.js"></script>
    </head>
    <body>
        <jsp:include page="../../../resources/CSS/RecursosCSS.jsp"></jsp:include>
        <jsp:include page="../../../resources/JS/RecursosJS.jsp"></jsp:include>
        <jsp:include page="../../../resources/JS/RecursosJS.jsp"></jsp:include>
        <jsp:include page="../../../Menus/Financiero.jsp"></jsp:include>
            <br>
        <%
            ObtenerV obtener = new ObtenerV();
            ObtenerUC obtenerUC = new ObtenerUC();
            ArrayList<Devolucion> devoluciones = obtener.obtenerDevolucionesTodas();
            String fechaInicial = "";
            String fechaFinal = "";
            double perdida = 0;
            if (request.getAttribute("devoluciones") != null) {
                devoluciones = (ArrayList<Devolucion>) request.getAttribute("devoluciones");
                fechaInicial = String.valueOf(request.getAttribute("fechaInicial"));
                fechaFinal = String.valueOf(request.getAttribute("fechaFinal"));
            }
        %>
        <div class="container">
            <h1 class="text-center">DEVOLUCIONES EN UN INTERVALO DE TIEMPO</h1>
            <div class="container border text-center">
                <br>
                <p class="bg-info text-white"> Puede filtrar su búsqueda interactuando con las siguientes opciones. Las fechas vacías muestran todas las devoluciones</p>
                <form action="${pageContext.request.contextPath}/Devoluciones" method="POST">
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
                            <th>NIT</th>
                            <th>CLIENTE</th>
                            <th>ID</th>
                            <th>TIPO DE MUEBLE</th>
                            <th>FECHA COMPRA</th>
                            <th>FECHA DEVOLUCIÓN</th>
                            <th>PÉRDIDA</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%if (devoluciones.isEmpty()) {%>
                        <tr class="table-warning">
                            <td colspan="7">NO HAY REGISTROS</td>
                        </tr>
                        <%}else{%>
                        <% for (Devolucion devolucion : devoluciones) {
                            ArrayList<Mueble> muebles = devolucion.obtenerMueblesDevueltos();
                            Factura factura = devolucion.getFactura();
                            perdida+= devolucion.getPerdida();
                            for (Mueble f : muebles) {%>
                        <tr>
                            <td><%=factura.getCliente().getNIT()%></td>
                            <td><%=factura.getCliente().getNombre().toUpperCase()%></td>
                            <td><%=f.getIdMueble()%></td>
                            <td><%=f.getTipoMueble().toUpperCase()%></td>
                            <td><%=factura.getFecha().toString()%></td>
                            <td><%=devolucion.getFecha().toString()%></td>
                            <td>Q.<%=devolucion.getPerdida()%></td>
                        </tr>
                        <%}%>
                        <%}%>
                        <tr class="table-danger">
                            <td colspan="6">PÉRDIDA</td>
                            <td colspan="2">Q.<%=perdida%></td>
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
            anchorElement.download = "Devoluciones.csv";
            anchorElement.click();

            setTimeout(() => {
                URL.revokeObjectURL(blobUrl);
            }, 500);
        });
        </script>
    </body>
</html>
