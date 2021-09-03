package EntidadesVenta;

import EntidadesFabrica.Ensamble;

/**
 *
 * @author aguare
 */
public class Mueble {

    private int idMueble;
    private double precioCosto;
    private Ensamble ensamble;
    private String tipoMueble;
    private boolean devuelto;
    private double precioVenta;

    public Mueble(int idMueble, double precioCosto, Ensamble ensamble, String tipoMueble, boolean devuelto, double precioVenta) {
        this.idMueble = idMueble;
        this.precioCosto = precioCosto;
        this.ensamble = ensamble;
        this.tipoMueble = tipoMueble;
        this.devuelto = devuelto;
        this.precioVenta = precioVenta;
    }

    public void seDevolvio() {
        devuelto = true;
    }

    public int getIdMueble() {
        return idMueble;
    }

    public void setIdMueble(int idMueble) {
        this.idMueble = idMueble;
    }

    public double getPrecioCosto() {
        return precioCosto;
    }

    public void setPrecioCosto(double precioCosto) {
        this.precioCosto = precioCosto;
    }

    public Ensamble getEnsamble() {
        return ensamble;
    }

    public void setEnsamble(Ensamble ensamble) {
        this.ensamble = ensamble;
    }

    public String getTipoMueble() {
        return tipoMueble;
    }

    public void setTipoMueble(String tipoMueble) {
        this.tipoMueble = tipoMueble;
    }

    public boolean isDevuelto() {
        return devuelto;
    }

    public void setDevuelto(boolean devuelto) {
        this.devuelto = devuelto;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

}
