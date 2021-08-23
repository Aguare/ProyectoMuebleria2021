<%-- 
    Document   : CargaArchivo
    Created on : 23/08/2021, 00:11:41
    Author     : aguare
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Bienvenido</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Varela+Round">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="../CSS/barraMenu.css">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <a href="#" class="navbar-brand"><b>MUEBLERIA</b></a>  		
        <button type="button" class="navbar-toggler" data-toggle="collapse" data-target="#navbarCollapse">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div id="navbarCollapse" class="collapse navbar-collapse justify-content-start">
            <div class="navbar-nav">
                <a href="#" class="nav-item nav-link">Inicio</a>
                <a href="#" class="nav-item nav-link">About</a>			
                <div class="nav-item dropdown">
                    <a href="#" data-toggle="dropdown" class="nav-item nav-link dropdown-toggle">Services</a>
                    <div class="dropdown-menu">					
                        <a href="#" class="dropdown-item">Web Design</a>
                        <a href="#" class="dropdown-item">Web Development</a>
                        <a href="#" class="dropdown-item">Graphic Design</a>
                        <a href="#" class="dropdown-item">Digital Marketing</a>
                    </div>
                </div>
                <a href="#" class="nav-item nav-link active">Pricing</a>
                <a href="#" class="nav-item nav-link">Blog</a>
                <a href="#" class="nav-item nav-link">Contact</a>
            </div>
            <div class="navbar-nav action-buttons ml-auto">
                <a href="#" data-toggle="dropdown" class="nav-item nav-link dropdown-toggle mr-3">Login</a>
                <div class="dropdown-menu login-form">
                    <form action="/examples/actions/confirmation.php" method="post">
                        <div class="form-group">
                            <label>Username</label>
                            <input type="text" class="form-control" required="required">
                        </div>
                        <div class="form-group">
                            <div class="clearfix">
                                <label>Password</label>
                                <a href="#" class="float-right text-muted"><small>Forgot?</small></a>
                            </div>                            
                            <input type="password" class="form-control" required="required">
                        </div>
                        <input type="submit" class="btn btn-primary btn-block" value="Login">
                    </form>					
                </div>			
                <a href="#" class="btn btn-primary">Get Started</a>
            </div>
        </div>
    </nav>
</body>
</html>
