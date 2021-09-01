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

    /**
     * Se verifica si la cantidad de piezas es alcanza para ensamblar un mueble
     *
     * @param cantidadNecesaria cantidad necesaria según el tipo de pieza
     * @return retorna un booleano
     */
    public boolean verificarExistenciaSuficiente(int cantidadNecesaria) {
        if (cantidad >= cantidadNecesaria) {
            return true;
        }
        return false;
    }

    /**
     * Devuelve un arreglo con las piezas que se necesitaran para ensamblar un
     * mueble
     *
     * @param cantidadNecesaria cantidad de piezas que se extraeran
     * @return
     */
    public ArrayList<Pieza> obtenerPiezasNecesarias(int cantidadNecesaria) {
        ArrayList<Pieza> pie = new ArrayList<>();
        for (int i = 0; i < cantidadNecesaria; i++) {
            pie.add(piezas.get(0));
            piezas.remove(0);
            if (i == cantidadNecesaria) {
                return pie;
            }
        }
        return pie;
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

}
