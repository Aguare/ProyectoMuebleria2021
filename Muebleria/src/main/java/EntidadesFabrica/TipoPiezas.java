package EntidadesFabrica;

import java.util.ArrayList;

/**
 *
 * @author aguare
 */
public class TipoPiezas {

    private String nombrePieza;
    private int cantidad;
    private String detalles;
    private ArrayList<Pieza> piezas;

    public TipoPiezas(String nombrePieza, int cantidad, String detalles, ArrayList<Pieza> piezas) {
        this.nombrePieza = nombrePieza;
        this.cantidad = cantidad;
        this.detalles = detalles;
        this.piezas = piezas;
    }

    public String getNombrePieza() {
        return nombrePieza;
    }

    public void setNombrePieza(String nombrePieza) {
        this.nombrePieza = nombrePieza;
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

    public ArrayList<Pieza> getPiezas() {
        return piezas;
    }

    public void setPiezas(ArrayList<Pieza> piezas) {
        this.piezas = piezas;
    }

    public void imprimir() {
        System.out.println("Nombre: " + nombrePieza + " Cantidad: " + cantidad);
        for (Pieza pieza : piezas) {
            pieza.imprimir();
        }
    }
}
