package Archivo;

import java.io.BufferedReader;
import java.util.ArrayList;

/**
 *
 * @author marco
 */
public class ControladorInsertar {

    private ArrayList<String> errores = new ArrayList<>();
    private ArrayList<String> correcto = new ArrayList<>();
    private LecturaArchivo leer = new LecturaArchivo();
    private InsertarArchivo insertar = new InsertarArchivo();

    public boolean ejecutar(BufferedReader entrada) {
        if (leer.Leer(entrada)) {
            recepcionUsuario(leer.getUsuarios());
            recepcionClientes(leer.getClientes());
            recepcionPiezas(leer.getPieza());
            recepcionMueble(leer.getMueble());
            recepcionEnsamblePiezas(leer.getEnsamble_pieza());
            recepcionEnsambleMueble(leer.getEnsamblar_mueble());
        } else {
            return false;
        }
        llenarErroresyCorrectos();
        return true;
    }

    private void llenarErroresyCorrectos() {
        errores.addAll(leer.getNo_reconocido());
        errores.addAll(insertar.getNoInsertados());
        correcto.addAll(insertar.getInsertados());
    }

    private void recepcionUsuario(ArrayList<String[]> usuarios) {
        for (String[] usuario : usuarios) {
            try {
                insertar.insertarUsuario(usuario[0], usuario[1], usuario[2]);
            } catch (Exception e) {
                insertar.errorAlInsertar(usuario);
            }
        }
    }

    private void recepcionClientes(ArrayList<String[]> clientes) {
        for (String[] cliente : clientes) {
            try {
                if (cliente.length == 5) {
                    insertar.insertarCliente(cliente[0], cliente[1], cliente[2] + " " + cliente[3] + " " + cliente[4]);
                } else {
                    insertar.insertarCliente(cliente[0], cliente[1], cliente[2]);
                }

            } catch (Exception e) {
                insertar.errorAlInsertar(cliente);
            }
        }
    }

    private void recepcionPiezas(ArrayList<String[]> piezas) {
        for (String[] pieza : piezas) {
            try {
                insertar.insertarTipo(pieza[0], pieza[1]);
                insertar.insertarPieza(pieza[0], pieza[1]);
            } catch (Exception e) {
                insertar.errorAlInsertar(pieza);
            }

        }
    }

    private void recepcionMueble(ArrayList<String[]> muebles) {
        for (String[] mueble : muebles) {
            try {
                insertar.insertarTipoMueble(mueble[0], mueble[1]);
            } catch (Exception e) {
                insertar.errorAlInsertar(mueble);
            }
        }
    }

    private void recepcionEnsamblePiezas(ArrayList<String[]> ensamble_pieza) {
        for (String[] pieza : ensamble_pieza) {
            try {
                insertar.insertarEnsamblePiezas(pieza[0], pieza[1], pieza[2]);
            } catch (Exception e) {
                insertar.errorAlInsertar(pieza);
            }
        }
    }

    private void recepcionEnsambleMueble(ArrayList<String[]> ensamblar_mueble) {
        for (String[] mueble : ensamblar_mueble) {
            try {
                insertar.insertarEnsambleMueble(mueble[0], mueble[1], mueble[2]);
            } catch (Exception e) {
                insertar.errorAlInsertar(mueble);
            }
        }
    }

    public ArrayList<String> getErrores() {
        return errores;
    }

    public ArrayList<String> getCorrecto() {
        return correcto;
    }
}
