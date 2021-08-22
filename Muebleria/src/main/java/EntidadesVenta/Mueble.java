package EntidadesVenta;

import EntidadesFabrica.Ensamble;
import EntidadesFabrica.TipoMueble;

/**
 *
 * @author aguare
 */
public class Mueble {

    private int idMueble;
    private double precioCosto;
    private Ensamble ensamble;
    private TipoMueble tipoMueble;
    private boolean devuelto = false;

    public Mueble(int idMueble, double precioCosto, Ensamble ensamble, TipoMueble tipoMueble) {
        this.idMueble = idMueble;
        this.precioCosto = precioCosto;
        this.ensamble = ensamble;
        this.tipoMueble = tipoMueble;
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

    public TipoMueble getTipoMueble() {
        return tipoMueble;
    }

    public void setTipoMueble(TipoMueble tipoMueble) {
        this.tipoMueble = tipoMueble;
    }

    public boolean isDevuelto() {
        return devuelto;
    }

    public void setDevuelto(boolean devuelto) {
        this.devuelto = devuelto;
    }

}
