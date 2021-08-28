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

    public void insertarUsuario(String usuario, String password, String departamento) {
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
            errorAlInsertar(new String[]{"USUARIO ->", usuario, password, departamento, obtenerTipoError(ex.getErrorCode())});
        }
    }

    public void insertarCliente(String NIT, String nombre, String direccion) {
        String query = "INSERT INTO Cliente VALUES(?,?,?);";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setString(1, NIT);
            prepared.setString(2, nombre);
            prepared.setString(3, direccion);
            prepared.executeUpdate();
            insertados.add("CLIENTE ->" + NIT + " " + nombre + " " + direccion);
        } catch (SQLException ex) {
            errorAlInsertar(new String[]{"CLIENTE ->", NIT, nombre, direccion, obtenerTipoError(ex.getErrorCode())});
        }
    }

    public void insertarPieza(String tipoPieza, String precio) {
        String query = "INSERT INTO Pieza (precio,usada,TPnombre_pieza) VALUES (?,?,?);";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setString(1, precio);
            prepared.setString(2, "0");
            prepared.setString(3, tipoPieza);
            prepared.executeUpdate();
            insertados.add("PIEZA ->" + precio + " " + tipoPieza);
        } catch (SQLException ex) {
            errorAlInsertar(new String[]{"PIEZA ->", tipoPieza, precio, obtenerTipoError(ex.getErrorCode())});
        }
    }

    public void insertarTipo(String nombre_pieza, String precio) {
        String query = "INSERT INTO TipoPieza VALUES (?,?,?);";
        int cantidadExistente = obtenerCantidadExistente(nombre_pieza);
        if (cantidadExistente == -1) {
            try {
                PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
                prepared.setString(1, nombre_pieza);
                prepared.setString(2, "1");
                prepared.setString(3, "");
                prepared.executeUpdate();
            } catch (SQLException ex) {
                errorAlInsertar(new String[]{"TIPOPIEZA->", nombre_pieza, precio, obtenerTipoError(ex.getErrorCode())});
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

    public void insertarTipoMueble(String nombre_mueble, String precio) {
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
            errorAlInsertar(new String[]{"MUEBLE ->", nombre_mueble, precio, obtenerTipoError(ex.getErrorCode())});
        }
    }

    public void insertarEnsamblePiezas(String tipoMueble, String tipoPieza, String cantidad) {
        String query = "INSERT INTO PiezasTipoMueble VALUES (?,?,?);";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setString(1, tipoMueble);
            prepared.setString(2, tipoPieza);
            prepared.setString(3, cantidad);
            prepared.executeUpdate();
            insertados.add("ENSAMBLE_PIEZAS ->" + tipoMueble + " " + tipoPieza + " " + cantidad);
        } catch (SQLException ex) {
            errorAlInsertar(new String[]{"ENSAMBLE_PIEZAS ->", tipoMueble, tipoPieza, cantidad, obtenerTipoError(ex.getErrorCode())});
        }
    }

    public void insertarEnsambleMueble(String mueble, String usuario, String fecha) {
        String query = "INSERT INTO Ensamble (fecha, nombre_usuario, TipoMueble) VALUES (STR_TO_DATE(REPLACE(?,\"/\",\".\") ,GET_FORMAT(date,'EUR')),?,?);";
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
            insertarMueble("" + num, mueble);
        } catch (SQLException ex) {
            errorAlInsertar(new String[]{"ENSAMBLAR_MUEBLE ->", fecha, usuario, mueble, obtenerTipoError(ex.getErrorCode())});
        }
    }

    private void insertarMueble(String idEnsamble, String tipoMueble) {
        String query = "INSERT INTO Mueble (precio_costo, E_idEnsamble, TMnombre_mueble) VALUES (?,?,?);";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setString(1, "" + obtenerPrecioCosto(tipoMueble));
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

    private double obtenerPrecioCosto(String tipoMueble) {
        String query = "SELECT precio_venta FROM TipoMueble WHERE nombre_mueble = ?";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setString(1, tipoMueble);
            ResultSet resultado = prepared.executeQuery();
            double num = 20;
            while (resultado.next()) {
                num = resultado.getDouble("precio_venta");
            }
            return num - 20;
        } catch (Exception e) {
            return 0;
        }
    }

    public void errorAlInsertar(String[] datos) {
        String linea = "";
        for (String dato : datos) {
            linea += dato + " ";
        }
        noInsertados.add(linea);
    }

    private String obtenerTipoError(int error) {
        switch (error) {
            case 1062:
                return "<- El registro ya existe";
            case 1136:
                return "<- El ID de referencia aún no existe";
            case 1064:
                return "<- Los datos no cumplen con su formato";
            case 1366:
                return "<- Los valores numéricos contienen un error";
            case 1452:
                return "<- El usuario o Tipo de Mueble que desea ensamblar no existe";
            default:
                return "<- No se pudo cargar a la BD";
        }
    }

    public ArrayList<String> getInsertados() {
        return insertados;
    }

    public ArrayList<String> getNoInsertados() {
        return noInsertados;
    }
}
