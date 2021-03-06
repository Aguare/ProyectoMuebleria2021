DROP DATABASE IF EXISTS Muebleria ;
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
	acceso TINYINT NOT NULL,
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
	idPieza INT NOT NULL AUTO_INCREMENT,
	precio DOUBLE NOT NULL,
	usada TINYINT NOT NULL,
	TPnombre_pieza VARCHAR(50) NOT NULL,
	PRIMARY KEY (idPieza),
	FOREIGN KEY (TPnombre_pieza) REFERENCES TipoPieza(nombre_pieza)
);

CREATE TABLE TipoMueble(
	nombre_mueble VARCHAR(50) NOT NULL,
	precio_venta DOUBLE NOT NULL,
	cantidad INT NOT NULL,
	detalles VARCHAR(100),
	PRIMARY KEY (nombre_mueble)
);

CREATE TABLE Ensamble(
	idEnsamble INT NOT NULL AUTO_INCREMENT,
	fecha DATE NOT NULL,
	nombre_usuario VARCHAR(20) NOT NULL,
	TipoMueble VARCHAR(50) NOT NULL,
	reintegro TINYINT NOT NULL,
	PRIMARY KEY (idEnsamble),
	FOREIGN KEY (nombre_usuario) REFERENCES Usuario(nombre_usuario),
	FOREIGN KEY (TipoMueble) REFERENCES TipoMueble(nombre_mueble)
);

CREATE TABLE PiezaEnsamble(
	E_idEnsamble INT NOT NULL,
	P_idPieza INT NOT NULL,
	TPnombre_pieza VARCHAR(50) NOT NULL,
	FOREIGN KEY (E_idEnsamble) REFERENCES Ensamble(idEnsamble),
	FOREIGN KEY (P_idPieza) REFERENCES Pieza(idPieza),
	FOREIGN KEY (TPnombre_pieza) REFERENCES TipoPieza(nombre_pieza)
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
	Usuario_user VARCHAR(20) NOT NULL,
	PRIMARY KEY (no_factura),
	FOREIGN KEY (Cliente_NIT) REFERENCES Cliente(NIT),
	FOREIGN KEY (Usuario_user) REFERENCES Usuario(nombre_usuario)
);

CREATE TABLE Compra(
	Cno_factura INT NOT NULL,
	Cliente_NIT VARCHAR(20) NOT NULL,
	C_idMueble INT NOT NULL,
	devuelto TINYINT NOT NULL,
	C_tipoMueble VARCHAR(50) NOT NULL,
	Cfecha DATE NOT NULL,
	FOREIGN KEY (Cno_factura) REFERENCES Factura(no_factura),
	FOREIGN KEY (Cliente_NIT) REFERENCES Cliente(NIT),
	FOREIGN KEY (C_idMueble) REFERENCES Mueble(idMueble),
	FOREIGN KEY (C_tipoMueble) REFERENCES Mueble(TMnombre_mueble)
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

INSERT INTO Departamento VALUES (1,"F??brica");
INSERT INTO Departamento VALUES (2,"Punto de Venta");
INSERT INTO Departamento VALUES (3,"Financiero");
INSERT INTO Usuario VALUES("admin","5r6gXIfIDxY=",1,3);
INSERT INTO Usuario VALUES("fabrica","5r6gXIfIDxY=",1,1);
INSERT INTO Usuario VALUES("venta","5r6gXIfIDxY=",1,2);		

DROP USER IF EXISTS 'adminMuebleria'@'localhost';
CREATE USER 'adminMuebleria'@'localhost' identified by 'Aadmin_1!';
GRANT ALL PRIVILEGES ON Muebleria.* TO adminMuebleria@localhost;
FLUSH PRIVILEGES;
