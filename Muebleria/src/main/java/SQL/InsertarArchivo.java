package SQL;

import SQL.Conexion;
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
    private Conexion conexion;

    public InsertarArchivo() {
        Conexion conexion = new Conexion();
        this.conexion = conexion;
    }

    public void insertarUsuario(String usuario, String password, String departamento) {
        String query = "INSERT INTO Usuario VALUES(?,?,?);";
        try {
            PreparedStatement prepared = conexion.obtenerConexion().prepareStatement(query);
            prepared.setString(1, usuario);
            prepared.setString(2, password);
            prepared.setString(3, departamento);
            prepared.executeUpdate();

        } catch (SQLException ex) {
            errorAlInsertar(new String[]{"USUARIO ->", usuario, password, departamento});
        }
    }

    public void insertarCliente(String NIT, String nombre, String direccion) {
        String query = "INSERT INTO Cliente VALUES(?,?,?);";
        try {
            PreparedStatement prepared = conexion.obtenerConexion().prepareStatement(query);
            prepared.setString(1, NIT);
            prepared.setString(2, nombre);
            prepared.setString(3, direccion);
            prepared.executeUpdate();

        } catch (SQLException ex) {
            errorAlInsertar(new String[]{"CLIENTE ->", NIT, nombre, direccion});
        }
    }

    public void InsertarPieza(String tipoPieza, String precio) {
        String query = "INSERT INTO Pieza (precio,TPnombre_pieza) VALUES (?,?);";
        try {
            PreparedStatement prepared = conexion.obtenerConexion().prepareStatement(query);
            prepared.setString(1, precio);
            prepared.setString(2, tipoPieza);
            prepared.executeUpdate();

        } catch (SQLException ex) {
            errorAlInsertar(new String[]{"PIEZA ->", tipoPieza, precio});
        }
    }

    public void insertarTipo(String nombre_pieza, String precio) {
        String query = "INSERT INTO TipoPieza VALUES (?,?,?);";
        int cantidadExistente = obtenerCantidadExistente(nombre_pieza);
        if (cantidadExistente == -1) {
            try {
                PreparedStatement prepared = conexion.obtenerConexion().prepareStatement(query);
                prepared.setString(1, nombre_pieza);
                prepared.setString(2, "1");
                prepared.setString(3, "");
                prepared.executeUpdate();
            } catch (SQLException ex) {
                errorAlInsertar(new String[]{"TIPOPIEZA->", nombre_pieza, precio});
            }
        } else {
            aumentarPieza(nombre_pieza, cantidadExistente);
        }
    }

    public void aumentarPieza(String nombre_pieza, int cantidadExistente) {
        cantidadExistente++;
        String query = "UPDATE TipoPieza SET cantidad = ? WHERE nombre_pieza = ?";
        try {
            PreparedStatement prepared = conexion.obtenerConexion().prepareStatement(query);
            prepared.setInt(1, cantidadExistente);
            prepared.setString(2, nombre_pieza);
            prepared.executeUpdate();
        } catch (Exception e) {
        }
    }

    public int obtenerCantidadExistente(String nombre_pieza) {
        String query = "SELECT cantidad FROM TipoPieza WHERE nombre_pieza = ?";
        try {
            PreparedStatement prepared = conexion.obtenerConexion().prepareStatement(query);
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

    public void escribirNoInsertados() {
        System.out.println("\n*************NO INSERTADOS******");
        for (String noInsertado : noInsertados) {
            System.out.println(noInsertado);
        }
    }

    public void insertarTipoMueble(String nombre_mueble, String precio) {
        String query = "INSERT INTO TipoMueble VALUES (?,?,?,?);";
        try {
            PreparedStatement prepared = conexion.obtenerConexion().prepareStatement(query);
            prepared.setString(1, nombre_mueble);
            prepared.setString(2, precio);
            prepared.setString(3, "0");
            prepared.setString(4, "");
            prepared.executeUpdate();
        } catch (SQLException ex) {
            errorAlInsertar(new String[]{"MUEBLE ->", nombre_mueble, precio});
        }
    }

    public void insertarEnsamblePiezas(String tipoMueble, String tipoPieza, String cantidad) {
        String query = "INSERT INTO PiezasTipoMueble VALUES (?,?,?);";
        try {
            PreparedStatement prepared = conexion.obtenerConexion().prepareStatement(query);
            prepared.setString(1, tipoMueble);
            prepared.setString(2, tipoPieza);
            prepared.setString(3, cantidad);
            prepared.executeUpdate();
        } catch (SQLException ex) {
            errorAlInsertar(new String[]{"ENSAMBLE_PIEZAS ->", tipoMueble, tipoPieza, cantidad});
        }
    }

    public void insertarEnsambleMueble(String mueble, String usuario, String fecha) {
        String query = "INSERT INTO Ensamble (fecha, nombre_usuario, TipoMueble) VALUES (STR_TO_DATE(REPLACE(?,\"/\",\".\") ,GET_FORMAT(date,'EUR')),?,?);";
        try {
            PreparedStatement prepared = conexion.obtenerConexion().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            prepared.setString(1, fecha);
            prepared.setString(2, usuario);
            prepared.setString(3, mueble);
            prepared.executeUpdate();
            ResultSet resultado = prepared.getGeneratedKeys();
            int num = -1;
            while (resultado.next()) {
                num = resultado.getInt(1);
            }
            insertarMueble("" + num, mueble);
        } catch (SQLException ex) {
            errorAlInsertar(new String[]{"ENSAMBLAR_MUEBLE ->", fecha, usuario, mueble});
        }
    }

    private void insertarMueble(String idEnsamble, String tipoMueble) {
        String query = "INSERT INTO Mueble (precio_costo, E_idEnsamble, TMnombre_mueble) VALUES (?,?,?);";
        try {
            PreparedStatement prepared = conexion.obtenerConexion().prepareStatement(query);
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
            PreparedStatement prepared = conexion.obtenerConexion().prepareStatement(query);
            prepared.setInt(1, cantidadExistente);
            prepared.setString(2, tipoMueble);
            prepared.executeUpdate();
        } catch (Exception e) {
        }
    }

    public int obtenerCantidadExistenteMuebles(String tipoMueble) {
        String query = "SELECT cantidad FROM TipoMueble WHERE nombre_mueble = ?";
        try {
            PreparedStatement prepared = conexion.obtenerConexion().prepareStatement(query);
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
            PreparedStatement prepared = conexion.obtenerConexion().prepareStatement(query);
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
}
