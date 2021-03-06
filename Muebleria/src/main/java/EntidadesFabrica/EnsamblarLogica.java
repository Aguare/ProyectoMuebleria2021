package EntidadesFabrica;

import Archivo.InsertarArchivo;
import ModificarObj.FabricaCRUD;
import ObtenerObjetos.ObtenerF;
import ObtenerObjetos.ObtenerV;
import java.util.ArrayList;

/**
 *
 * @author marco
 */
public class EnsamblarLogica {

    private final ObtenerV obtenerV = new ObtenerV();
    private ArrayList<String> erroresArchivo = new ArrayList<>();
    private String error;

    /**
     * Se ensambla un mueble con toda su lógica, si viene del archivo de entrada
     * se pasa a ventas automáticamente
     *
     * @param tipoMueble Tipo de Mueble
     * @param usuario Nombre del Usuario
     * @param fecha Fecha en tipo DATE
     * @param noLinea Número de línea del archivo, sino enviar ""
     * @return
     */
    public int ensamblarMueble(String tipoMueble, String usuario, String fecha, String noLinea) {
        TipoMueble tipo = obtenerV.obtenerTipoMuebleSegunNombre(tipoMueble);
        if (tipo != null) {
            PiezasTipoMueble piezasNecesarias = tipo.getPiezasNecesarias();
            //Se verifica que exista una receta para la creación del mueble
            if (!piezasNecesarias.getPiezas().isEmpty()) {
                ArrayList<TipoPiezas> piezas = piezasNecesarias.getPiezas();
                //Se verifica si la cantidad de piezas en el inventario es suficiente para ensamblar
                if (verificarCantidaPiezas(piezasNecesarias.getCantidades(), piezas)) {
                    ArrayList<Pieza> piezasNuevas = obtenerPiezas(piezasNecesarias.getCantidades(), piezas);
                    double precioCosto = obtenerPrecioCosto(piezasNuevas);
                    InsertarArchivo insertar = new InsertarArchivo();
                    int sePudo = insertar.insertarEnsambleMueble(tipoMueble, usuario, fecha, "" + precioCosto, noLinea);
                    if (sePudo == -1) {
                        error = "<- El usuario o Tipo de Mueble no existe";
                    } else {
                        FabricaCRUD modificar = new FabricaCRUD();
                        for (Pieza piezasNueva : piezasNuevas) {
                            modificar.actualizarPiezaUsada(piezasNueva, "" + sePudo);
                        }
                    }
                    return sePudo;
                } else {
                    error = "<- Las piezas existentes no son suficientes para el ensamble";
                    return -1;
                }
            } else {
                error = "<- No hay instrucciones para ensamblar este Mueble";
                return -1;
            }
        } else {
            error = "<- El tipo de mueble no existe";
            return -1;
        }
    }

    private boolean verificarCantidaPiezas(ArrayList<Integer> cantidades, ArrayList<TipoPiezas> piezas) {
        for (int i = 0; i < piezas.size(); i++) {
            if (!piezas.get(i).verificarExistenciaSuficiente(cantidades.get(i))) {
                return false;
            }
        }
        return true;
    }

    private ArrayList<Pieza> obtenerPiezas(ArrayList<Integer> cantidades, ArrayList<TipoPiezas> piezas) {
        ArrayList<Pieza> piezasNuevas = new ArrayList<>();
        for (int i = 0; i < piezas.size(); i++) {
            ArrayList<Pieza> temp = piezas.get(i).obtenerPiezasNecesarias(cantidades.get(i));
            for (Pieza pieza : temp) {
                piezasNuevas.add(pieza);
            }
        }
        return piezasNuevas;
    }

    private double obtenerPrecioCosto(ArrayList<Pieza> piezas) {
        double suma = 0;
        for (Pieza pieza : piezas) {
            suma += pieza.getPrecio();
        }
        return suma;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
