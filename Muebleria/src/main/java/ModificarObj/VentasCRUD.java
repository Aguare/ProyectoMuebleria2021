package ModificarObj;

import Archivo.InsertarArchivo;
import EntidadesVenta.CarritoCompra;
import EntidadesVenta.Cliente;
import EntidadesVenta.Compra;
import EntidadesVenta.Factura;
import EntidadesVenta.Mueble;
import ObtenerObjetos.ObtenerF;
import ObtenerObjetos.ObtenerUC;
import ObtenerObjetos.ObtenerV;
import SQL.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author aguare
 */
public class VentasCRUD {

    private ObtenerF obtenerF = new ObtenerF();
    private ObtenerUC obtenerUC = new ObtenerUC();
    private ObtenerV obtenerV = new ObtenerV();
    private InsertarArchivo insertarAr = new InsertarArchivo();
    private String error = "";

    /**
     * Este método quita los muebles que ya estan en el carrito de compras
     *
     * @param carrito muebles en el carrito
     * @param todos todos los muebles existentes
     * @return muebles que no estan en el carrito
     */
    public ArrayList<Mueble> obtenerMueblesDisponibles(ArrayList<Mueble> carrito, ArrayList<Mueble> todos) {
        for (Mueble mueble : carrito) {
            for (Mueble todo : todos) {
                if (mueble.getIdMueble() == todo.getIdMueble()) {
                    todos.remove(todo);
                }
            }
        }
        return todos;
    }

    public ArrayList<Mueble> quitarMuebleCarrito(ArrayList<Mueble> carrito, String idMueble) {
        int id = 0;
        try {
            id = Integer.parseInt(idMueble);
        } catch (Exception e) {
            return carrito;
        }
        for (int i = 0; i < carrito.size(); i++) {
            if (carrito.get(i).getIdMueble() == id) {
                carrito.remove(i);
                break;
            }
        }
        return carrito;
    }

    /**
     * Registra la compra con el objeto carrito de compra
     *
     * @param carrito
     * @return Devuelve la compra con la factura y la lista de muebles comprados
     * para poder ser visualizado
     */
    public Compra registrarCompra(CarritoCompra carrito) {
        Cliente cn = carrito.getCliente();
        Cliente cliente = obtenerUC.obtenerClientesSegunNIT(cn.getNIT());
        if (cliente == null) {
            insertarAr.insertarCliente(cn.getNIT(), cn.getNombre(), cn.getDireccion(), "");
        }
        int noFactura = insertarFactura(carrito.obtenerTotal(), carrito.getCliente().getNIT(), carrito.getUsuario().getNombre_usuario());
        if (noFactura == -1) {
            return null;
        } else {
            Factura factura = obtenerV.obtenerFacturaSegunNo(noFactura);
            for (Mueble mueble : carrito.getCarrito()) {
                insertarCompra(noFactura, cn.getNIT(), mueble.getIdMueble(), mueble.getTipoMueble(), factura.getFecha().toString());
            }

            return new Compra(factura, obtenerV.obtenerMueblesSegunFactura(noFactura));
        }
    }

    /**
     * Se inserta la compra para registrar todos los muebles de la compra
     *
     * @param noFactura factura que generó la compra
     * @param NIT NIT del cliente
     * @param idMueble Mueble que se registra
     */
    private void insertarCompra(int noFactura, String NIT, int idMueble, String tipoMueble, String fecha) {
        String query = "INSERT INTO Compra VALUES (?,?,?,?,?,?);";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setInt(1, noFactura);
            prepared.setString(2, NIT);
            prepared.setInt(3, idMueble);
            prepared.setString(4, "0");
            prepared.setString(5, tipoMueble);
            prepared.setString(6, fecha);
            prepared.executeUpdate();
        } catch (SQLException ex) {
        }
    }

    /**
     * Se ingresa una nueva factura al sistema
     *
     * @param total total de la compra con todos los muebles
     * @param NIT NIT del cliente
     * @return retorna el Número de factura creado si hay error devuelve -1
     */
    public int insertarFactura(double total, String NIT, String nombreUsuario) {
        String query = "INSERT INTO Factura (fecha, total, Cliente_NIT, Usuario_user) VALUES (?,?,?,?);";
        LocalDate fecha = LocalDate.now();
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            prepared.setString(1, fecha.toString());
            prepared.setDouble(2, total);
            prepared.setString(3, NIT);
            prepared.setString(4, nombreUsuario);
            prepared.executeUpdate();
            ResultSet resultado = prepared.getGeneratedKeys();
            int num = -1;
            while (resultado.next()) {
                num = resultado.getInt(1);
            }
            return num;
        } catch (SQLException ex) {
            return -1;
        }
    }

    public boolean registrarDevolucion(Compra compra, Mueble mueble) {
        double perdida = verificarRegistroDevolucion(compra.getFactura());
        if (perdida == 0) {
            double p = mueble.getPrecioCosto() / 3;
            p = Math.round(p * 100) / 100;
            registrarperdida(compra.getFactura(), p);
            registrarMuebleDevuelto(mueble);
            actualizarFactura(compra.getFactura(), (compra.getFactura().getTotal() - mueble.getPrecioVenta()));
            return true;
        } else {
            perdida += (mueble.getPrecioCosto() / 3);
            perdida = Math.round(perdida * 100) / 100;
            actualizarPerdida(compra.getFactura(), perdida);
            registrarMuebleDevuelto(mueble);
            actualizarFactura(compra.getFactura(), (compra.getFactura().getTotal() - mueble.getPrecioVenta()));
            return true;
        }
    }

    /**
     * Registra la cantidad que se perdió por el mueble devuelto si no se ha
     * registrado
     *
     * @param factura
     * @param precioMueble
     */
    public void registrarperdida(Factura factura, double precioMueble) {
        String query = "INSERT INTO Devolucion (fecha, perdida, no_factura, Cliente_NIT) VALUES (?,?,?,?);";
        LocalDate fecha = LocalDate.now();

        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setString(1, fecha.toString());
            prepared.setDouble(2, precioMueble);
            prepared.setInt(3, factura.getNoFactura());
            prepared.setString(4, factura.getCliente().getNIT());
            prepared.executeUpdate();
        } catch (SQLException ex) {
        }
    }

    /**
     * Actualiza el valor total de la factura para que las ganancias
     * correspondan
     *
     * @param factura
     * @param perdida
     */
    public void actualizarFactura(Factura factura, double perdida) {
        String query = "UPDATE Factura SET total = ? WHERE no_factura = ?;";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setDouble(1, perdida);
            prepared.setInt(2, factura.getNoFactura());
            prepared.executeUpdate();
        } catch (SQLException ex) {
        }
    }

    /**
     * Si ya ha sido registrado una pérdida se actualiza para conocer la pérdida
     *
     * @param factura
     * @param nuevaPerdida
     */
    public void actualizarPerdida(Factura factura, double nuevaPerdida) {
        String query = "UPDATE Devolucion SET perdida = ? WHERE no_factura = ?;";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setDouble(1, nuevaPerdida);
            prepared.setInt(2, factura.getNoFactura());
            prepared.executeUpdate();
        } catch (SQLException ex) {
        }
    }

    /**
     * Se actualiza el estado del mueble ya que ha sido devuelto
     *
     * @param mueble
     */
    private void registrarMuebleDevuelto(Mueble mueble) {
        String query = "UPDATE Compra SET devuelto = 1 WHERE C_idMueble = ?";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setString(1, "" + mueble.getIdMueble());
            prepared.executeUpdate();
        } catch (SQLException ex) {
        }
    }

    /**
     * Verifica si la devolucion está previamente registrada si sí devolverá un
     * valor mayor a 0
     *
     * @param factura
     * @return
     */
    private double verificarRegistroDevolucion(Factura factura) {
        double perdida = 0;
        String query = "SELECT * FROM Devolucion WHERE no_factura = ?";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setInt(1, factura.getNoFactura());
            ResultSet resultado = prepared.executeQuery();
            while (resultado.next()) {
                perdida = resultado.getDouble("perdida");
            }
        } catch (SQLException ex) {
        }
        return perdida;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
