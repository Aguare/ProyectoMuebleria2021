package EFabrica;

import EVenta.Mueble;
import java.util.ArrayList;

/**
 *
 * @author aguare
 */
public class TipoMueble {

    private String nombreMueble;
    private double precioVenta;
    private int cantidad;
    private String detalles;
    private ArrayList<Mueble> muebles;
    private PiezasTipoMueble piezasNecesarias;

    public TipoMueble(String nombreMueble, double precioVenta, int cantidad, String detalles, ArrayList<Mueble> muebles, PiezasTipoMueble piezasNecesarias) {
        this.nombreMueble = nombreMueble;
        this.precioVenta = precioVenta;
        this.cantidad = cantidad;
        this.detalles = detalles;
        this.muebles = muebles;
        this.piezasNecesarias = piezasNecesarias;
    }

    public String getNombreMueble() {
        return nombreMueble;
    }

    public void setNombreMueble(String nombreMueble) {
        this.nombreMueble = nombreMueble;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    public ArrayList<Mueble> getMuebles() {
        return muebles;
    }

    public void setMuebles(ArrayList<Mueble> muebles) {
        this.muebles = muebles;
    }

    public PiezasTipoMueble getPiezasNecesarias() {
        return piezasNecesarias;
    }

    public void setPiezasNecesarias(PiezasTipoMueble piezasNecesarias) {
        this.piezasNecesarias = piezasNecesarias;
    }
    
}
