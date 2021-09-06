<%-- 
    Document   : Redireccionar
    Created on : 30/08/2021, 19:53:48
    Author     : marco
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setDateHeader("Expires", -1);
    if (request.getSession().getAttribute("Usuario") == null) {
        request.getRequestDispatcher("../index.jsp").forward(request, response);
    }
%>
