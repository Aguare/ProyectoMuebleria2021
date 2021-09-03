package EntidadesVenta;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author aguare
 */
public class Devolucion {

    private int idDevolucion;
    private LocalDate fecha;
    private double perdida;
    private Factura factura;
    private ArrayList<Mueble> muebles;

    public Devolucion(int idDevolucion, LocalDate fecha, double perdida, Factura factura, ArrayList<Mueble> muebles) {
        this.idDevolucion = idDevolucion;
        this.fecha = fecha;
        this.perdida = perdida;
        this.factura = factura;
        this.muebles = muebles;
    }

    public int getIdDevolucion() {
        return idDevolucion;
    }

    public void setIdDevolucion(int idDevolucion) {
        this.idDevolucion = idDevolucion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public double getPerdida() {
        return perdida;
    }

    public void setPerdida(double perdida) {
        this.perdida = perdida;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public ArrayList<Mueble> getMuebles() {
        return muebles;
    }

    public void setMuebles(ArrayList<Mueble> muebles) {
        this.muebles = muebles;
    }
}
