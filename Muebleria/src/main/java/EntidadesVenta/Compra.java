package EntidadesVenta;

import java.util.ArrayList;

/**
 *
 * @author aguare
 */
public class Compra {

    private Factura factura;
    private ArrayList<Mueble> muebles;

    public Compra(Factura factura, ArrayList<Mueble> muebles) {
        this.factura = factura;
        this.muebles = muebles;
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
