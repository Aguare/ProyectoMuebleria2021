CREATE DATABASE Muebleria;
USE Muebleria;

CREATE TABLE Departamento(
	idDepartamento INT NOT NULL,
	nombre_departamento VARCHAR(20) NOT NULL,
	PRIMARY KEY (idDepartamento)
);

CREATE TABLE Usuario(
	nombre_usuario VARCHAR(20) NOT NULL,
	password VARCHAR(30) NOT NULL,
	idDepartamento INT NOT NULL,
	PRIMARY KEY (nombre_usuario),
	FOREIGN KEY (idDepartamento) REFERENCES Departamento(idDepartamento)
);

CREATE TABLE TipoPieza(
	nombre_pieza VARCHAR(50) NOT NULL,
	cantidad INT NOT NULL,
	detalles VARCHAR(100),
    PRIMARY KEY (nombre_pieza)
);

CREATE TABLE Pieza(
	idPieza INT NOT NULL,
	precio DOUBLE NOT NULL,
	TPnombre_pieza VARCHAR(50) NOT NULL,
	PRIMARY KEY (idPieza),
	FOREIGN KEY (TPnombre_pieza) REFERENCES TipoPieza(nombre_pieza)
);

CREATE TABLE Ensamble(
	idEnsamble INT NOT NULL AUTO_INCREMENT,
	fecha DATE NOT NULL,
	nombre_usuario VARCHAR(20) NOT NULL,
	PRIMARY KEY (idEnsamble),
	FOREIGN KEY (nombre_usuario) REFERENCES Usuario(nombre_usuario)
);

CREATE TABLE PiezaEnsamble(
	E_idEnsamble INT NOT NULL,
	P_idPieza INT NOT NULL,
	TPnombre_pieza VARCHAR(50) NOT NULL,
	FOREIGN KEY (E_idEnsamble) REFERENCES Ensamble(idEnsamble),
	FOREIGN KEY (P_idPieza) REFERENCES Pieza(idPieza),
	FOREIGN KEY (TPnombre_pieza) REFERENCES TipoPieza(nombre_pieza)
);

CREATE TABLE TipoMueble(
	nombre_mueble VARCHAR(50) NOT NULL,
	precio_venta DOUBLE NOT NULL,
	cantidad INT NOT NULL,
	detalles VARCHAR(100),
	PRIMARY KEY (nombre_mueble)
);

CREATE TABLE PiezasTipoMueble(
	TMnombre_mueble VARCHAR(50) NOT NULL,
	TPnombre_pieza VARCHAR(50) NOT NULL,
	cantidad INT NOT NULL,
	FOREIGN KEY (TMnombre_mueble) REFERENCES TipoMueble(nombre_mueble),
	FOREIGN KEY (TPnombre_pieza) REFERENCES TipoPieza(nombre_pieza)
);

CREATE TABLE Mueble(
	idMueble INT NOT NULL AUTO_INCREMENT,
	precio_costo DOUBLE NOT NULL,
	E_idEnsamble INT NOT NULL,
	TMnombre_mueble VARCHAR(50) NOT NULL,
	PRIMARY KEY (idMueble),
	FOREIGN KEY (E_idEnsamble) REFERENCES Ensamble(idEnsamble),
	FOREIGN KEY (TMnombre_mueble) REFERENCES TipoMueble(nombre_mueble)
);

CREATE TABLE Cliente(
	NIT VARCHAR(20) NOT NULL,
	nombre_cliente VARCHAR(60) NOT NULL,
	direccion VARCHAR(100) NOT NULL,
	PRIMARY KEY (NIT)
);

CREATE TABLE Factura(
	no_factura INT NOT NULL AUTO_INCREMENT,
	fecha DATE NOT NULL,
	total DOUBLE NOT NULL,
	Cliente_NIT VARCHAR(20) NOT NULL,
	PRIMARY KEY (no_factura),
	FOREIGN KEY (Cliente_NIT) REFERENCES Cliente(NIT)
);

CREATE TABLE Compra(
	no_factura INT NOT NULL,
	Cliente_NIT VARCHAR(20) NOT NULL,
	idMueble INT NOT NULL,
	M_idEnsamble INT NOT NULL,
	TMnombre_mueble VARCHAR(50) NOT NULL,
	devuelto TINYINT NOT NULL,
	FOREIGN KEY (no_factura) REFERENCES Factura(no_factura),
	FOREIGN KEY (Cliente_NIT) REFERENCES Cliente(NIT),
	FOREIGN KEY (idMueble) REFERENCES Mueble(idMueble),
	FOREIGN KEY (M_idEnsamble) REFERENCES Mueble(E_idEnsamble),
	FOREIGN KEY (TMnombre_mueble) REFERENCES Mueble(TMnombre_mueble)
);

CREATE TABLE Devolucion(
	idDevolucion INT NOT NULL AUTO_INCREMENT,
	fecha DATE NOT NULL,
	perdida DOUBLE NOT NULL,
	no_factura INT NOT NULL,
	Cliente_NIT VARCHAR(20) NOT NULL,
	PRIMARY KEY (idDevolucion),
	FOREIGN KEY (no_factura) REFERENCES Factura(no_factura),
	FOREIGN KEY (Cliente_NIT) REFERENCES Cliente(NIT)
);