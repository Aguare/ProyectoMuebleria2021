package EFabrica;

/**
 *
 * @author aguare
 */
public class Pieza {

    private int idPieza;
    private double precio;
    private boolean usada;
    private String tipoPieza;

    public Pieza(int idPieza, double precio, boolean usada, String tipoPieza) {
        this.idPieza = idPieza;
        this.precio = precio;
        this.usada = usada;
        this.tipoPieza = tipoPieza;
    }

    public int getIdPieza() {
        return idPieza;
    }

    public void setIdPieza(int idPieza) {
        this.idPieza = idPieza;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public boolean isUsada() {
        return usada;
    }

    public void setUsada(boolean usada) {
        this.usada = usada;
    }

    public String getTipoPieza() {
        return tipoPieza;
    }

    public void setTipoPieza(String tipoPieza) {
        this.tipoPieza = tipoPieza;
    }

}
