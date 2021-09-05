/* Inserta un ensamble con formato de fecha diferente al default*/
INSERT INTO Ensamble (fecha, nombre_usuario, TipoMueble) VALUES (STR_TO_DATE(REPLACE(?,"/",".") ,GET_FORMAT(date,'EUR')),?,?);

/*Consulta para obtener los muebles que no se han vendido*/
SELECT * FROM Mueble WHERE NOT EXISTS (SELECT C_idMueble FROM Compra WHERE Compra.C_idMueble = Mueble.idMueble);

/*Consulta para obtener al usuario con mayores ventas en un intervalo de tiempo*/
SELECT COUNT(Usuario_user) AS Cantidad, Usuario_user FROM Factura WHERE fecha BETWEEN "2021-09-01" AND "2021-09-26" GROUP BY Usuario_user ORDER BY Cantidad DESC; 
/*Obtener el total generado por el usuario con mayor venta*/
SELECT SUM(total) AS TotalGenerado FROM Factura WHERE Usuario_user = "aguare" AND fecha BETWEEN "2021-09-01" AND "2021-09-26" GROUP BY total;

/*Mueble más vendido en un intervalo de tiempo*/
SELECT COUNT(C_tipoMueble) AS Cantidad, C_tipoMueble FROM Compra WHERE EXISTS (SELECT no_factura FROM Factura WHERE Fecha BETWEEN "2021-01-01" AND "2021-09-04") GROUP BY C_tipoMueble ORDER BY Cantidad DESC;