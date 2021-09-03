package ObtenerObjetos;

import EntidadesFabrica.TipoMueble;
import EntidadesVenta.Mueble;
import SQL.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author aguare
 */
public class ObtenerV {

    private final ObtenerF obtenerF = new ObtenerF();

    /**
     * Obtiene todos los tipos de muebles como objeto, con su receta cantida,
     * etc.
     *
     * @return Retorna un arreglo de TipoMueble
     */
    public ArrayList<TipoMueble> obtenerTipoMuebles() {
        ArrayList<TipoMueble> tipoMuebles = new ArrayList<>();
        String query = "SELECT * FROM TipoMueble;";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            ResultSet resultado = prepared.executeQuery();
            while (resultado.next()) {
                tipoMuebles.add(new TipoMueble(resultado.getString("nombre_mueble"),
                        resultado.getDouble("precio_venta"), resultado.getInt("cantidad"),
                        resultado.getString("detalles"), obtenerMuebles(resultado.getString("nombre_mueble")),
                        obtenerF.obtenerPiezasTipoMueble(resultado.getString("nombre_mueble"))));
            }
        } catch (SQLException e) {
        }
        return tipoMuebles;
    }

    /**
     * Obtiene un tipo de mueble según su nombre
     *
     * @param tipoMueble el nombre del tipo de Mueble
     * @return Devulve un TipoMueble
     */
    public TipoMueble obtenerTipoMuebleSegunNombre(String tipoMueble) {
        TipoMueble tipo = null;
        String query = "SELECT * FROM TipoMueble WHERE nombre_mueble = ?;";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setString(1, tipoMueble);
            ResultSet resultado = prepared.executeQuery();
            while (resultado.next()) {
                tipo = new TipoMueble(resultado.getString("nombre_mueble"),
                        resultado.getDouble("precio_venta"), resultado.getInt("cantidad"),
                        resultado.getString("detalles"), obtenerMuebles(resultado.getString("nombre_mueble")),
                        obtenerF.obtenerPiezasTipoMueble(resultado.getString("nombre_mueble")));
            }
        } catch (SQLException e) {
        }
        return tipo;
    }

    /**
     * Obtiene el precio de venta según el tipo de mueble
     *
     * @param tipoMueble
     * @return
     */
    public double obtenerPrecioVenta(String tipoMueble) {
        double precioV = 0;
        String query = "SELECT precio_venta FROM TipoMueble WHERE nombre_mueble = ?;";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setString(1, tipoMueble);
            ResultSet resultado = prepared.executeQuery();
            while (resultado.next()) {
                precioV = resultado.getDouble("precio_venta");
            }
        } catch (SQLException e) {
        }
        return precioV;
    }

    /**
     * Obtiene los Muebles según el tipo de mueble
     *
     * @param tipoMueble
     * @return
     */
    public ArrayList<Mueble> obtenerMuebles(String tipoMueble) {
        ArrayList<Mueble> muebles = new ArrayList<>();
        String query = "SELECT * FROM Mueble WHERE TMnombre_mueble = ?;";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setString(1, tipoMueble);
            ResultSet resultado = prepared.executeQuery();
            while (resultado.next()) {
                muebles.add(new Mueble(resultado.getInt("idMueble"), resultado.getDouble("precio_costo"),
                        obtenerF.obtenerEnsambleSegunID(resultado.getString("E_idEnsamble")), resultado.getString("TMnombre_mueble"),
                        consultarDevolucion(resultado.getInt("idMueble")), obtenerPrecioVenta(tipoMueble)));
            }
        } catch (SQLException e) {
        }
        return muebles;
    }

    /**
     * obtiene todos los muebles que han sido ensamblados junto con los que han
     * sido vendidos
     *
     * @return
     */
    public ArrayList<Mueble> obtenerMueblesTodos() {
        ArrayList<Mueble> muebles = new ArrayList<>();
        String query = "SELECT * FROM Mueble;";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            ResultSet resultado = prepared.executeQuery();
            while (resultado.next()) {
                muebles.add(new Mueble(resultado.getInt("idMueble"), resultado.getDouble("precio_costo"),
                        obtenerF.obtenerEnsambleSegunID(resultado.getString("E_idEnsamble")), resultado.getString("TMnombre_mueble"),
                        consultarDevolucion(resultado.getInt("idMueble")), obtenerPrecioVenta(resultado.getString("TMnombre_mueble"))));
            }
        } catch (SQLException e) {
        }
        return muebles;
    }

    /**
     * Devuelve la lista de muebles que están disponibles para la venta
     *
     * @return
     */
    public ArrayList<Mueble> obtenerMueblesNoVendidos() {
        ArrayList<Mueble> muebles = new ArrayList<>();
        String query = "SELECT * FROM Mueble WHERE NOT EXISTS (SELECT C_idMueble FROM Compra WHERE Compra.C_idMueble = Mueble.idMueble);";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            ResultSet resultado = prepared.executeQuery();
            while (resultado.next()) {
                muebles.add(new Mueble(resultado.getInt("idMueble"), resultado.getDouble("precio_costo"),
                        obtenerF.obtenerEnsambleSegunID(resultado.getString("E_idEnsamble")), resultado.getString("TMnombre_mueble"),
                        consultarDevolucion(resultado.getInt("idMueble")), obtenerPrecioVenta(resultado.getString("TMnombre_mueble"))));
            }
        } catch (SQLException e) {
        }
        return muebles;
    }

    /**
     * Obtiene el mueble según el ID
     *
     * @param id
     * @return
     */
    public Mueble obtenerMuebleSegunID(String id) {
        Mueble mueble = null;
        String query = "SELECT * FROM Mueble WHERE idMueble = ?;";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setString(1, id);
            ResultSet resultado = prepared.executeQuery();
            while (resultado.next()) {
                mueble = new Mueble(resultado.getInt("idMueble"), resultado.getDouble("precio_costo"),
                        obtenerF.obtenerEnsambleSegunID(resultado.getString("E_idEnsamble")), resultado.getString("TMnombre_mueble"),
                        consultarDevolucion(resultado.getInt("idMueble")), obtenerPrecioVenta(resultado.getString("TMnombre_mueble")));
            }
        } catch (SQLException e) {
        }
        return mueble;
    }

    /**
     * Este método consulta en la base de datos si el mueble ha sido devuelto o
     * no
     *
     * @param idMueble
     * @return
     */
    public boolean consultarDevolucion(int idMueble) {
        String query = "SELECT devuelto FROM Compra WHERE idMueble = ?;";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setInt(1, idMueble);
            ResultSet resultado = prepared.executeQuery();
            while (resultado.next()) {
                return resultado.getBoolean("devuelto");
            }
        } catch (SQLException e) {
        }
        return false;
    }
}
