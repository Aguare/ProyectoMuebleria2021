/* Inserta un ensamble con formato de fecha diferente al default*/
INSERT INTO Ensamble (fecha, nombre_usuario, TipoMueble) VALUES (STR_TO_DATE(REPLACE(?,"/",".") ,GET_FORMAT(date,'EUR')),?,?);

/*Consulta para obtener los muebles que no se han vendido*/
SELECT * FROM Mueble WHERE NOT EXISTS (SELECT C_idMueble FROM Compra WHERE Compra.C_idMueble = Mueble.idMueble);