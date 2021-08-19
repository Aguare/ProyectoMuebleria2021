package EFabrica;

import java.util.ArrayList;

/**
 *
 * @author aguare
 */
public class PiezasTipoMueble {

    private ArrayList<TipoPiezas> piezas;
    private ArrayList<Integer> cantidades;

    public PiezasTipoMueble(ArrayList<TipoPiezas> piezas, ArrayList<Integer> cantidades) {
        this.piezas = piezas;
        this.cantidades = cantidades;
    }
    
    /**
     * Devuelve la cantidad de piezas necesarias, según el nombre de la pieza
     *
     * @param tipoPieza nombre de la pieza
     * @return retorna -1 si el tipo de pieza no existe
     */
    public int cantidadPiezasPorPieza(String tipoPieza) {
        for (int i = 0; i < piezas.size(); i++) {
            if (tipoPieza.equalsIgnoreCase(piezas.get(i).getNombrePieza())) {
                return cantidades.get(i);
            }
        }
        return -1;
    }

    /**
     * Devuelve el objeto TipoPieza en base al nombre de la pieza
     *
     * @param tipoPieza el nombre de la pieza
     * @return retorna nulo si no se encuentra conincidencia
     */
    public TipoPiezas obtenerPieza(String tipoPieza) {
        for (int i = 0; i < piezas.size(); i++) {
            if (tipoPieza.equalsIgnoreCase(piezas.get(i).getNombrePieza())) {
                return piezas.get(i);
            }
        }
        return null;
    }

    public ArrayList<TipoPiezas> getPiezas() {
        return piezas;
    }

    public void setPiezas(ArrayList<TipoPiezas> piezas) {
        this.piezas = piezas;
    }

    public ArrayList<Integer> getCantidades() {
        return cantidades;
    }

    public void setCantidades(ArrayList<Integer> cantidades) {
        this.cantidades = cantidades;
    }

}
