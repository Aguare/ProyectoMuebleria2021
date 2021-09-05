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
            for (Mueble mueble : carrito.getCarrito()) {
                insertarCompra(noFactura, cn.getNIT(), mueble.getIdMueble());
            }
            Factura factura = obtenerV.obtenerFacturaSegunNo(noFactura);
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
    private void insertarCompra(int noFactura, String NIT, int idMueble) {
        String query = "INSERT INTO Compra VALUES (?,?,?,?);";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setInt(1, noFactura);
            prepared.setString(2, NIT);
            prepared.setInt(3, idMueble);
            prepared.setString(4, "0");
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

    public ObtenerF getObtenerF() {
        return obtenerF;
    }

    public void setObtenerF(ObtenerF obtenerF) {
        this.obtenerF = obtenerF;
    }

    public ObtenerUC getObtenerUC() {
        return obtenerUC;
    }

    public void setObtenerUC(ObtenerUC obtenerUC) {
        this.obtenerUC = obtenerUC;
    }

    public InsertarArchivo getInsertarAr() {
        return insertarAr;
    }

    public void setInsertarAr(InsertarArchivo insertarAr) {
        this.insertarAr = insertarAr;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
