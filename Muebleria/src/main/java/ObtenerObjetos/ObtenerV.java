package ObtenerObjetos;

import EntidadesFabrica.TipoMueble;
import EntidadesVenta.Compra;
import EntidadesVenta.Devolucion;
import EntidadesVenta.Factura;
import EntidadesVenta.Mueble;
import SQL.Conexion;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author aguare
 */
public class ObtenerV {

    private final ObtenerF obtenerF = new ObtenerF();
    private final ObtenerUC obtenerUC = new ObtenerUC();

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

    public ArrayList<Mueble> obtenerMueblesSegunFactura(int noFactura) {
        ArrayList<Mueble> muebles = new ArrayList<>();
        String query = "SELECT C_idMueble FROM Compra WHERE Cno_factura = ?;";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setString(1, "" + noFactura);
            ResultSet resultado = prepared.executeQuery();
            while (resultado.next()) {
                muebles.add(obtenerMuebleSegunID(resultado.getString("C_idMueble")));
            }
        } catch (SQLException e) {
        }
        return muebles;
    }

    /**
     * Este método consulta en la base de datos si el mueble ha sido devuelto o
     * no
     *
     * @param idMueble
     * @return
     */
    public boolean consultarDevolucion(int idMueble) {
        String query = "SELECT devuelto FROM Compra WHERE C_idMueble = ?;";
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

    /**
     * Se obtiene la factura según su número de correlativo
     *
     * @param noFactura
     * @return
     */
    public Factura obtenerFacturaSegunNo(int noFactura) {
        Factura factura = null;
        String query = "SELECT * FROM Factura WHERE no_factura = ?;";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setInt(1, noFactura);
            ResultSet resultado = prepared.executeQuery();
            while (resultado.next()) {
                Date fechaSQL = resultado.getDate("fecha");
                LocalDate fecha = fechaSQL.toLocalDate();
                factura = new Factura(resultado.getInt("no_factura"), fecha,
                        resultado.getDouble("total"), obtenerUC.obtenerClientesSegunNIT(resultado.getString("Cliente_NIT")),
                        resultado.getString("Usuario_user"));
            }
        } catch (SQLException e) {
        }
        return factura;
    }

    /**
     * Devuelve las facturas que se han generado en la fecha actual
     *
     * @return
     */
    public ArrayList<Factura> obtenerFacturasDiaActual() {
        ArrayList<Factura> facturas = new ArrayList<>();
        String query = "SELECT * FROM Factura WHERE fecha = CURDATE();";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            ResultSet resultado = prepared.executeQuery();
            while (resultado.next()) {
                Date fechaSQL = resultado.getDate("fecha");
                LocalDate fecha = fechaSQL.toLocalDate();
                facturas.add(new Factura(resultado.getInt("no_factura"), fecha,
                        resultado.getDouble("total"), obtenerUC.obtenerClientesSegunNIT(resultado.getString("Cliente_NIT")),
                        resultado.getString("Usuario_user")));
            }
        } catch (SQLException e) {
        }
        return facturas;
    }

    public ArrayList<Factura> obtenerFacturasTodas() {
        ArrayList<Factura> facturas = new ArrayList<>();
        String query = "SELECT * FROM Factura;";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            ResultSet resultado = prepared.executeQuery();
            while (resultado.next()) {
                Date fechaSQL = resultado.getDate("fecha");
                LocalDate fecha = fechaSQL.toLocalDate();
                facturas.add(new Factura(resultado.getInt("no_factura"), fecha,
                        resultado.getDouble("total"), obtenerUC.obtenerClientesSegunNIT(resultado.getString("Cliente_NIT")),
                        resultado.getString("Usuario_user")));
            }
        } catch (SQLException e) {
        }
        return facturas;
    }

    /**
     * Obtiene las facturas generadas en un intervalo de tiempo
     *
     * @param fechaInicial
     * @param fechaFinal
     * @return
     */
    public ArrayList<Factura> obtenerFacturasIntervaloFecha(String fechaInicial, String fechaFinal) {
        ArrayList<Factura> facturas = new ArrayList<>();
        String query = "SELECT * FROM Factura WHERE fecha BETWEEN ? AND ?;";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setString(1, fechaInicial);
            prepared.setString(2, fechaFinal);
            ResultSet resultado = prepared.executeQuery();
            while (resultado.next()) {
                Date fechaSQL = resultado.getDate("fecha");
                LocalDate fecha = fechaSQL.toLocalDate();
                facturas.add(new Factura(resultado.getInt("no_factura"), fecha,
                        resultado.getDouble("total"), obtenerUC.obtenerClientesSegunNIT(resultado.getString("Cliente_NIT")),
                        resultado.getString("Usuario_user")));
            }
        } catch (SQLException e) {
        }
        return facturas;
    }

    /**
     * Obtiene la compra según el número de factura
     *
     * @param noFactura número de factura
     * @return
     */
    public Compra obtenerCompraSegunFactura(int noFactura) {
        Compra compra = null;
        Factura factura = obtenerFacturaSegunNo(noFactura);
        ArrayList<Mueble> muebles = obtenerMueblesSegunFactura(noFactura);
        compra = new Compra(factura, muebles);
        return compra;
    }

    /**
     * Se obtienen las compras realizadas por un cliente entre dos fechas
     *
     * @param fechaInicial
     * @param fechaFinal
     * @param NIT
     * @return
     */
    public ArrayList<Factura> obtenerFacturasClienteSegunFecha(String fechaInicial, String fechaFinal, String NIT) {
        ArrayList<Factura> facturas = new ArrayList<>();
        String query = "SELECT * FROM Factura WHERE Cliente_NIT = ? AND (fecha BETWEEN ? AND ?);";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setString(1, NIT);
            prepared.setString(2, fechaInicial);
            prepared.setString(3, fechaFinal);
            ResultSet resultado = prepared.executeQuery();
            while (resultado.next()) {
                Date fechaSQL = resultado.getDate("fecha");
                LocalDate fecha = fechaSQL.toLocalDate();
                facturas.add(new Factura(resultado.getInt("no_factura"), fecha,
                        resultado.getDouble("total"), obtenerUC.obtenerClientesSegunNIT(resultado.getString("Cliente_NIT")),
                        resultado.getString("Usuario_user")));
            }
        } catch (SQLException e) {
        }
        return facturas;
    }

    /**
     * Se obtienen las compras realizadas por un cliente
     *
     * @param NIT
     * @return
     */
    public ArrayList<Factura> obtenerFacturasCliente(String NIT) {
        ArrayList<Factura> facturas = new ArrayList<>();
        String query = "SELECT * FROM Factura WHERE Cliente_NIT = ?;";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setString(1, NIT);
            ResultSet resultado = prepared.executeQuery();
            while (resultado.next()) {
                Date fechaSQL = resultado.getDate("fecha");
                LocalDate fecha = fechaSQL.toLocalDate();
                facturas.add(new Factura(resultado.getInt("no_factura"), fecha,
                        resultado.getDouble("total"), obtenerUC.obtenerClientesSegunNIT(resultado.getString("Cliente_NIT")),
                        resultado.getString("Usuario_user")));
            }
        } catch (SQLException e) {
        }
        return facturas;
    }

    /**
     * Se obtienen las devoluciones hechas por un cliente en un intervalo de
     * tiempo
     *
     * @param fechaInicial
     * @param fechaFinal
     * @param nit
     * @return
     */
    public ArrayList<Devolucion> obtenerDevolucionesClienteSegunFechas(String fechaInicial, String fechaFinal, String nit) {
        ArrayList<Devolucion> devoluciones = new ArrayList<>();
        String query = "SELECT * FROM Devolucion WHERE  fecha BETWEEN ? AND ? AND Cliente_NIT = ?;";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setString(1, fechaInicial);
            prepared.setString(2, fechaFinal);
            prepared.setString(3, nit);
            ResultSet resultado = prepared.executeQuery();
            while (resultado.next()) {
                Date fechaSQL = resultado.getDate("fecha");
                LocalDate fecha = fechaSQL.toLocalDate();
                Factura factura = obtenerFacturaSegunNo(resultado.getInt("no_factura"));
                devoluciones.add(new Devolucion(resultado.getInt("idDevolucion"), fecha, resultado.getDouble("perdida"),
                        factura, obtenerMueblesSegunFactura(factura.getNoFactura())));
            }
        } catch (SQLException e) {
        }
        return devoluciones;
    }

    /**
     * Obtiene las devoluciones en un intervalo de tiempo de todos los clientes
     *
     * @param fechaInicial
     * @param fechaFinal
     * @return
     */
    public ArrayList<Devolucion> obtenerDevolucionesSegunFechas(String fechaInicial, String fechaFinal) {
        ArrayList<Devolucion> devoluciones = new ArrayList<>();
        String query = "SELECT * FROM Devolucion WHERE  fecha BETWEEN ? AND ?;";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setString(1, fechaInicial);
            prepared.setString(2, fechaFinal);
            ResultSet resultado = prepared.executeQuery();
            while (resultado.next()) {
                Date fechaSQL = resultado.getDate("fecha");
                LocalDate fecha = fechaSQL.toLocalDate();
                Factura factura = obtenerFacturaSegunNo(resultado.getInt("no_factura"));
                devoluciones.add(new Devolucion(resultado.getInt("idDevolucion"), fecha, resultado.getDouble("perdida"),
                        factura, obtenerMueblesSegunFactura(factura.getNoFactura())));
            }
        } catch (SQLException e) {
        }
        return devoluciones;
    }

    /**
     * Obtiene todas las devolciones realizadas
     *
     * @return
     */
    public ArrayList<Devolucion> obtenerDevolucionesTodas() {
        ArrayList<Devolucion> devoluciones = new ArrayList<>();
        String query = "SELECT * FROM Devolucion;";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            ResultSet resultado = prepared.executeQuery();
            while (resultado.next()) {
                Date fechaSQL = resultado.getDate("fecha");
                LocalDate fecha = fechaSQL.toLocalDate();
                Factura factura = obtenerFacturaSegunNo(resultado.getInt("no_factura"));
                devoluciones.add(new Devolucion(resultado.getInt("idDevolucion"), fecha, resultado.getDouble("perdida"),
                        factura, obtenerMueblesSegunFactura(factura.getNoFactura())));
            }
        } catch (SQLException e) {
        }
        return devoluciones;
    }

    public Devolucion obtenerDevolucionSegunID(String id) {
        Devolucion devoluciones = null;
        String query = "SELECT * FROM Devolucion WHERE idDevolucion = ?;";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setString(1, id);
            ResultSet resultado = prepared.executeQuery();
            while (resultado.next()) {
                Date fechaSQL = resultado.getDate("fecha");
                LocalDate fecha = fechaSQL.toLocalDate();
                Factura factura = obtenerFacturaSegunNo(resultado.getInt("no_factura"));
                devoluciones = new Devolucion(resultado.getInt("idDevolucion"), fecha, resultado.getDouble("perdida"),
                        factura, obtenerMueblesSegunFactura(factura.getNoFactura()));
            }
        } catch (SQLException e) {
        }
        return devoluciones;
    }

    public ArrayList<Devolucion> obtenerDevolucionesTodasSinReintegro() {
        ArrayList<Devolucion> devoluciones = new ArrayList<>();
        String query = "SELECT * FROM Devolucion WHERE reintegro = 0;";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            ResultSet resultado = prepared.executeQuery();
            while (resultado.next()) {
                Date fechaSQL = resultado.getDate("fecha");
                LocalDate fecha = fechaSQL.toLocalDate();
                Factura factura = obtenerFacturaSegunNo(resultado.getInt("no_factura"));
                devoluciones.add(new Devolucion(resultado.getInt("idDevolucion"), fecha, resultado.getDouble("perdida"),
                        factura, obtenerMueblesSegunFactura(factura.getNoFactura())));
            }
        } catch (SQLException e) {
        }
        return devoluciones;
    }

    public ArrayList<Devolucion> obtenerDevolucionesTodasCliente(String nit) {
        ArrayList<Devolucion> devoluciones = new ArrayList<>();
        String query = "SELECT * FROM Devolucion WHERE Cliente_NIT = ?;";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setString(1, nit);
            ResultSet resultado = prepared.executeQuery();
            while (resultado.next()) {
                Date fechaSQL = resultado.getDate("fecha");
                LocalDate fecha = fechaSQL.toLocalDate();
                Factura factura = obtenerFacturaSegunNo(resultado.getInt("no_factura"));
                devoluciones.add(new Devolucion(resultado.getInt("idDevolucion"), fecha, resultado.getDouble("perdida"),
                        factura, obtenerMueblesSegunFactura(factura.getNoFactura())));
            }
        } catch (SQLException e) {
        }
        return devoluciones;
    }
}
