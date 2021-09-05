package EntidadesVenta;

import EntidadesFabrica.Usuario;
import java.util.ArrayList;

/**
 *
 * @author aguare
 */
public class CarritoCompra {

    private ArrayList<Mueble> carrito = new ArrayList<>();
    private ArrayList<Mueble> disponibles;
    private Cliente cliente = null;
    private Usuario usuario;

    public CarritoCompra(ArrayList<Mueble> disponibles, Usuario usuario) {
        this.disponibles = disponibles;
        this.usuario = usuario;
    }

    public boolean agregarCarrito(String idMueble) {
        int id = castearId(idMueble);
        for (Mueble disponible : disponibles) {
            if (disponible.getIdMueble() == id) {
                carrito.add(disponible);
                disponibles.remove(disponible);
                return true;
            }
        }
        return false;
    }

    public boolean quitarCarrito(String idMueble) {
        int id = castearId(idMueble);
        for (Mueble mueble : carrito) {
            if (mueble.getIdMueble() == id) {
                disponibles.add(mueble);
                carrito.remove(mueble);
                return true;
            }
        }
        return false;
    }

    public double obtenerTotal() {
        double total = 0;
        for (Mueble mueble : carrito) {
            total += mueble.getPrecioVenta();
        }
        return total;
    }

    private int castearId(String id) {
        int idMueble = -1;
        try {
            idMueble = Integer.parseInt(id);
        } catch (NumberFormatException e) {
        }
        return idMueble;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public ArrayList<Mueble> getCarrito() {
        return carrito;
    }

    public void setCarrito(ArrayList<Mueble> carrito) {
        this.carrito = carrito;
    }

    public ArrayList<Mueble> getDisponibles() {
        return disponibles;
    }

    public void setDisponibles(ArrayList<Mueble> disponibles) {
        this.disponibles = disponibles;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
