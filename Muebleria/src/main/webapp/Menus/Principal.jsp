<%-- 
    Document   : Principal
    Created on : 23/08/2021, 00:54:34
    Author     : aguare
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Bienvenido</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Varela+Round">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="../resources/CSS/barraMenu.css">
    <script src="../resources/JS/menu.js"></script>
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
    <style><%@include file="../resources/CSS/barraMenu.css"%></style>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a href="../index.jsp" class="navbar-brand">Muebles<b>Fiesta</b></a>  		
    <button type="button" class="navbar-toggler" data-toggle="collapse" data-target="#navbarCollapse">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div id="navbarCollapse" class="collapse navbar-collapse justify-content-start">
        <div class="navbar-nav">
            <a href="../index.jsp" class="nav-item nav-link">Inicio</a>			
            <div class="nav-item dropdown">
                <a href="#" data-toggle="dropdown" class="nav-item nav-link dropdown-toggle">Servicios</a>
                <div class="dropdown-menu">					
                    <a href="#" class="dropdown-item">Muebles</a>
                    <a href="#" class="dropdown-item">Devolución</a>
                    <a href="#" class="dropdown-item">Facturas</a>
                </div>
            </div>
            <a href="#" class="nav-item nav-link">Blog</a>
            <a href="#" class="nav-item nav-link">Contacto</a>
        </div>
        <div class="navbar-nav action-buttons ml-auto">
            <a href="#" data-toggle="dropdown" class="nav-item nav-link dropdown-toggle mr-3">Iniciar Sesión</a>
            <div class="dropdown-menu login-form">
                <form action="${pageContext.request.contextPath}/Sesion" method="post" accept-charset="utf-8">
                    <div class="form-group">
                        <label>Usuario</label>
                        <input type="text" class="form-control" name="usuario" maxlength="20" required="required">
                    </div>
                    <div class="form-group">
                        <div class="clearfix">
                            <label>Contraseña</label>
                        </div>                            
                        <input type="password" class="form-control" name="password" maxlength="30" accept-charset="utf-8" required="required">
                    </div>
                    <input type="submit" class="btn btn-primary btn-block" value="Ingresar">
                </form>					
            </div>			
        </div>
    </div>
</nav>
</head>
<body>
</body>
</html>
