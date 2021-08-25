<%-- 
    Document   : Principal
    Created on : 23/08/2021, 00:54:34
    Author     : aguare
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Bienvenido</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="../CSS/barraMenu.css">
    <script src="../JS/menu.js"></script>
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a href="#" class="navbar-brand"><b>FÁBRICA</b></a>  		
    <button type="button" class="navbar-toggler" data-toggle="collapse" data-target="#navbarCollapse">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div id="navbarCollapse" class="collapse navbar-collapse justify-content-start">
        <div class="navbar-nav">
            <a href="#" class="nav-item nav-link">Inicio</a>
            <a href="#" class="nav-item nav-link">Ensamblar</a>			
            <div class="nav-item dropdown">
                <a href="#" data-toggle="dropdown" class="nav-item nav-link dropdown-toggle">Crear y Editar</a>
                <div class="dropdown-menu">					
                    <a href="#" class="dropdown-item">Tipo de Pieza</a>
                    <a href="#" class="dropdown-item">Pieza</a>
                    <a href="#" class="dropdown-item">Graphic Design</a>
                    <a href="#" class="dropdown-item">Digital Marketing</a>
                </div>
            </div>
            <div class="nav-item dropdown">
                <a href="#" data-toggle="dropdown" class="nav-item nav-link dropdown-toggle">Consulta</a>
                <div class="dropdown-menu">					
                    <a href="#" class="dropdown-item">Piezas Existentes</a>
                    <a href="#" class="dropdown-item">Muebles Creados</a>
                </div>
            </div>
            <a href="#" class="nav-item nav-link">Devoluciones</a>
        </div>
        <div class="navbar-nav action-buttons ml-auto">
            <a href="#" data-toggle="dropdown" class="navbar-nav mr-3">Nombre Usuario</a>
            <div class="dropdown-menu login-form">
                <form action="/examples/actions/confirmation.php" method="post">
                    <input type="submit" class="btn btn-primary btn-block" value="Cerrar Sesion">
                </form>					
            </div>			
        </div>
    </div>
</nav>
</head>
<body>
</body>
</html>
