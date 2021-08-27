/* Inserta un ensamble con formato de fecha diferente al default*/
INSERT INTO Ensamble (fecha, nombre_usuario, TipoMueble) VALUES (STR_TO_DATE(REPLACE(?,"/",".") ,GET_FORMAT(date,'EUR')),?,?);