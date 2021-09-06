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

    public Mueble obtenerMuebleDevuelto(int idMueble) {
        for (Mueble mueble : muebles) {
            if (mueble.getIdMueble() == idMueble) {
                return mueble;
            }
        }
        return null;
    }

    public ArrayList<Mueble> obtenerMueblesDevueltos() {
        ArrayList<Mueble> m = new ArrayList<>();
        for (Mueble mueble : muebles) {
            if (mueble.isDevuelto()) {
                m.add(mueble);
            }
        }
        return m;
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
