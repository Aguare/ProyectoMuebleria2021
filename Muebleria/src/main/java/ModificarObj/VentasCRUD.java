package ModificarObj;

import EntidadesVenta.Mueble;
import ObtenerObjetos.ObtenerF;
import SQL.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author aguare
 */
public class VentasCRUD {

    private ObtenerF obtenerF = new ObtenerF();

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

}
