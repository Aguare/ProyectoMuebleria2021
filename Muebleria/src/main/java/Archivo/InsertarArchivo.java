package Archivo;

import SQL.Conexion;
import SQL.Encriptar;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author aguare
 */
public class InsertarArchivo {

    private ArrayList<String> noInsertados = new ArrayList<>();
    private ArrayList<String> insertados = new ArrayList<>();
    private String mensaje = "";

    public void insertarUsuario(String usuario, String password, String departamento, String noLinea) {
        String query = "INSERT INTO Usuario VALUES(?,?,?,?);";
        Encriptar encriptar = new Encriptar();
        String passEncriptada = encriptar.encriptarPass(password);
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setString(1, usuario);
            prepared.setString(2, passEncriptada);
            prepared.setString(3, "1");
            prepared.setString(4, departamento);
            prepared.executeUpdate();
            insertados.add("USUARIO ->" + usuario + " " + password + " " + departamento);
        } catch (SQLException ex) {
            errorAlInsertar(new String[]{"USUARIO ->", usuario, password, departamento}, ex.getErrorCode(), noLinea);
        }
    }

    public void insertarCliente(String NIT, String nombre, String direccion, String noLinea) {
        String query = "INSERT INTO Cliente VALUES(?,?,?);";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setString(1, NIT);
            prepared.setString(2, nombre);
            prepared.setString(3, direccion);
            prepared.executeUpdate();
            insertados.add("CLIENTE ->" + NIT + " " + nombre + " " + direccion);
        } catch (SQLException ex) {
            errorAlInsertar(new String[]{"CLIENTE ->", NIT, nombre, direccion}, ex.getErrorCode(), noLinea);
        }
    }

    public void insertarPieza(String tipoPieza, String precio, String noLinea) {
        String query = "INSERT INTO Pieza (precio,usada,TPnombre_pieza) VALUES (?,?,?);";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setString(1, precio);
            prepared.setString(2, "0");
            prepared.setString(3, tipoPieza);
            prepared.executeUpdate();
            insertados.add("PIEZA ->" + precio + " " + tipoPieza);
        } catch (SQLException ex) {
            errorAlInsertar(new String[]{"PIEZA ->", tipoPieza, precio}, ex.getErrorCode(), noLinea);
        }
    }

    /**
     * Inserta un nuevo tipo de pieza que no está en la base de datos
     * @param nombre_pieza
     * @param precio
     * @param noLinea 
     */
    public void insertarTipo(String nombre_pieza, String precio, String noLinea) {
        String query = "INSERT INTO TipoPieza VALUES (?,?,?);";
        int cantidadExistente = obtenerCantidadExistente(nombre_pieza);
        if (cantidadExistente == -1) {
            try {
                PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
                prepared.setString(1, nombre_pieza);
                prepared.setString(2, "1");
                prepared.setString(3, "");
                prepared.executeUpdate();
                mensaje = "¡EL TIPO DE PIEZA SE HA CREADO EXITOSAMENTE!";
            } catch (SQLException ex) {
                mensaje = obtenerTipoError(ex.getErrorCode());
                errorAlInsertar(new String[]{"TIPOPIEZA->", nombre_pieza, precio}, ex.getErrorCode(), noLinea);
            }
        } else {
            aumentarPieza(nombre_pieza, cantidadExistente);
        }
    }

    public void aumentarPieza(String nombre_pieza, int cantidadExistente) {
        cantidadExistente++;
        String query = "UPDATE TipoPieza SET cantidad = ? WHERE nombre_pieza = ?";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setInt(1, cantidadExistente);
            prepared.setString(2, nombre_pieza);
            prepared.executeUpdate();
        } catch (Exception e) {
        }
    }

    public int obtenerCantidadExistente(String nombre_pieza) {
        String query = "SELECT cantidad FROM TipoPieza WHERE nombre_pieza = ?";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setString(1, nombre_pieza);
            ResultSet resultado = prepared.executeQuery();
            int num = -1;
            while (resultado.next()) {
                num = resultado.getInt("cantidad");
            }
            return num;
        } catch (Exception e) {
            return -1;
        }
    }

    public void insertarTipoMueble(String nombre_mueble, String precio, String noLinea) {
        String query = "INSERT INTO TipoMueble VALUES (?,?,?,?);";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setString(1, nombre_mueble);
            prepared.setString(2, precio);
            prepared.setString(3, "0");
            prepared.setString(4, "");
            prepared.executeUpdate();
            insertados.add("MUEBLE ->" + nombre_mueble + " " + precio);
        } catch (SQLException ex) {
            errorAlInsertar(new String[]{"MUEBLE ->", nombre_mueble, precio}, ex.getErrorCode(), noLinea);
        }
    }

    public void insertarEnsamblePiezas(String tipoMueble, String tipoPieza, String cantidad, String noLinea) {
        String query = "INSERT INTO PiezasTipoMueble VALUES (?,?,?);";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setString(1, tipoMueble);
            prepared.setString(2, tipoPieza);
            prepared.setString(3, cantidad);
            prepared.executeUpdate();
            insertados.add("ENSAMBLE_PIEZAS ->" + tipoMueble + " " + tipoPieza + " " + cantidad);
        } catch (SQLException ex) {
            errorAlInsertar(new String[]{"ENSAMBLE_PIEZAS ->", tipoMueble, tipoPieza, cantidad}, ex.getErrorCode(), noLinea);
        }
    }

    //INICIA LAS SECUENCIAS SQL PARA ENSAMBLAR UN MUEBLE
    /**
     * Este método inserta un ensamble y pasa a ventas si el noLinea viene vacío
     *
     * @param mueble Nombre Tipo de Mueble
     * @param usuario nombre usuario
     * @param fecha fecha en tipo DATE
     * @param precioCosto precio costo del ensamble
     * @param noLinea Número de la linea si viene del archivo de entrada, si no
     * viene de archivo enviar ""
     */
    public int insertarEnsambleMueble(String mueble, String usuario, String fecha, String precioCosto, String noLinea) {
        String query = "INSERT INTO Ensamble (fecha, nombre_usuario, TipoMueble) VALUES (STR_TO_DATE(REPLACE(?,\"/\",\".\") ,GET_FORMAT(date,'EUR')),?,?);";
        if (noLinea.equalsIgnoreCase("")) {
            query = "INSERT INTO Ensamble (fecha, nombre_usuario, TipoMueble) VALUES (?,?,?);";
        }
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            prepared.setString(1, fecha);
            prepared.setString(2, usuario.trim());
            prepared.setString(3, mueble);
            prepared.executeUpdate();
            ResultSet resultado = prepared.getGeneratedKeys();
            int num = -1;
            while (resultado.next()) {
                num = resultado.getInt(1);
            }
            insertarMueble("" + num, mueble, precioCosto);
            return num;
        } catch (SQLException ex) {
            errorAlInsertar(new String[]{"ENSAMBLAR_MUEBLE ->", fecha, usuario, mueble}, ex.getErrorCode(), noLinea);
        }
        return -1;
    }

    private void insertarMueble(String idEnsamble, String tipoMueble, String precioCosto) {
        String query = "INSERT INTO Mueble (precio_costo, E_idEnsamble, TMnombre_mueble) VALUES (?,?,?);";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setString(1, precioCosto);
            prepared.setString(2, idEnsamble);
            prepared.setString(3, tipoMueble);
            prepared.executeUpdate();
            aumentarMuebles(obtenerCantidadExistenteMuebles(tipoMueble), tipoMueble);
        } catch (SQLException ex) {
        }
    }

    private void aumentarMuebles(int cantidadExistente, String tipoMueble) {
        cantidadExistente++;
        String query = "UPDATE TipoMueble SET cantidad = ? WHERE nombre_mueble = ?";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setInt(1, cantidadExistente);
            prepared.setString(2, tipoMueble);
            prepared.executeUpdate();
        } catch (Exception e) {
        }
    }

    public int obtenerCantidadExistenteMuebles(String tipoMueble) {
        String query = "SELECT cantidad FROM TipoMueble WHERE nombre_mueble = ?";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setString(1, tipoMueble);
            ResultSet resultado = prepared.executeQuery();
            int num = -1;
            while (resultado.next()) {
                num = resultado.getInt("cantidad");
            }
            return num;
        } catch (Exception e) {
            return -1;
        }
    }

    public void errorAlInsertar(String[] datos, int error, String noLinea) {
        String linea = noLinea + "| ";
        for (String dato : datos) {
            linea += dato + " ";
        }
        linea += obtenerTipoError(error);
        noInsertados.add(linea);
    }

    public void errorEnsamble(String[] datos, String error, String noLinea) {
        String linea = noLinea + "| ";
        for (String dato : datos) {
            linea += dato + " ";
        }
        linea += error;
        noInsertados.add(linea);
    }

    private String obtenerTipoError(int error) {
        switch (error) {
            case Error.REGISTRO_EXISTE:
                return "<- El registro ya existe";
            case Error.DATOS_INCOMPLETOS:
                return "<- Los datos no estan completos";
            case Error.FORMATO_INCORRECTO:
                return "<- Los datos no cumplen con su formato";
            case Error.ERROR_NUMERICO:
                return "<- Los valores numéricos contienen un error";
            case Error.ID_NOEXISTE:
                return "<- El número de ID al que desea hacer referencia no existe";
            case Error.ERROR_NIT:
                return "<- El NIT no debe contener guiones";
            case Error.ERROR_INSERTAR:
                return "<- No se pudo cargar a la BD";
            case Error.DATOS_VACIOS:
                return "<- Los datos no pueden estar vacios";
            case Error.SOBREPASA:
                return "<- El tamaño de los datos sobrepasa el límite";
            default:
                return "<- No se pudo insertar a la BD";
        }
    }

    public ArrayList<String> getInsertados() {
        return insertados;
    }

    public ArrayList<String> getNoInsertados() {
        return noInsertados;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
