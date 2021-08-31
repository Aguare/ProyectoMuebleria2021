<%-- 
    Document   : EditarPieza
    Created on : 29/08/2021, 23:24:21
    Author     : marco
--%>

<%@page import="EntidadesFabrica.TipoPiezas"%>
<%@page import="java.util.ArrayList"%>
<%@page import="SQL.ObtenerObj"%>
<%@page import="EntidadesFabrica.Pieza"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Editar Pieza</title>
    </head>
    <body>
        <jsp:include page="../../Menus/Fabrica.jsp"></jsp:include>
        <jsp:include page="../../resources/CSS/RecursosCSS.jsp"></jsp:include>
        <jsp:include page="../../resources/JS/RecursosJS.jsp"></jsp:include>
        <jsp:include page="../../resources/JS/RecursosJS.jsp"></jsp:include>
        <jsp:include page="../../Inicio/Redireccionar.jsp"></jsp:include>
        <%
            ObtenerObj obtener = new ObtenerObj();
            ArrayList<TipoPiezas> tipoPiezas = obtener.obtenerTipoPiezas();
            Pieza pieza = (Pieza) request.getAttribute("pieza");
        %>
        <br>
        <div class="container border" style="width: 600px">
            <br>
            <form action="${pageContext.request.contextPath}/CambioPiezas" method="POST">
                <div class="form-row">
                    <div class="form-group col-md-6">
                        <label>ID PIEZA</label>
                        <input type="number" class="form-control" id="idPieza" value="<%=pieza.getIdPieza()%>" disabled="">
                    </div>
                    <div class="form-group col-md-6">
                        <label>Precio</label>
                        <input type="number" min="0" class="form-control" id="precio" placeholder="Precio Pieza" value="<%=pieza.getPrecio()%>">
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputState">TIPO DE PIEZA</label>
                    <select id="tipoPieza" class="form-control">
                        <option selected><%=pieza.getTipoPieza().toUpperCase()%></option>
                        <%for (TipoPiezas tipoPieza : tipoPiezas) {
                                if (!tipoPieza.getNombrePieza().equalsIgnoreCase(pieza.getTipoPieza())) {%>
                        <option><%=tipoPieza.getNombrePieza().toUpperCase()%></option>
                        <%
                                }
                            }
                        %>
                    </select>
                </div>
                <div class="text-center">
                    <button type="submit" class="btn btn-primary">Actualizar</button>
                </div>

            </form>
            <br>
        </div>

    </body>
</html>
