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
            if (!verificarDatosVacios(usuario)) {
                try {
                    insertar.insertarUsuario(usuario[0], usuario[1], usuario[2], usuario[usuario.length - 1]);
                } catch (Exception e) {
                    insertar.errorAlInsertar(usuario, Error.DATOS_INCOMPLETOS, usuario[usuario.length - 1]);
                }
            } else {
                insertar.errorAlInsertar(usuario, Error.DATOS_VACIOS, usuario[usuario.length - 1]);
            }
        }
    }

    private void recepcionClientes(ArrayList<String[]> clientes) {
        for (String[] cliente : clientes) {
            if (!verificarDatosVacios(cliente)) {
                try {
                    String nit = cliente[1].replace("-", "");
                    if (cliente.length == 5) {
                        insertar.insertarCliente(nit, cliente[0], cliente[2] + " " + cliente[3] + " " + cliente[4], cliente[cliente.length - 1]);
                    } else {
                        insertar.insertarCliente(nit, cliente[0], cliente[2], cliente[cliente.length - 1]);
                    }
                } catch (Exception e) {
                    insertar.errorAlInsertar(cliente, Error.DATOS_INCOMPLETOS, cliente[cliente.length - 1]);
                }
            } else {
                insertar.errorAlInsertar(cliente, Error.DATOS_VACIOS, cliente[cliente.length - 1]);
            }
        }
    }

    private void recepcionPiezas(ArrayList<String[]> piezas) {
        for (String[] pieza : piezas) {
            if (!verificarDatosVacios(pieza)) {
                try {
                    insertar.insertarTipo(pieza[0], pieza[1], pieza[pieza.length - 1]);
                    insertar.insertarPieza(pieza[0], pieza[1], pieza[pieza.length - 1]);
                } catch (Exception e) {
                    insertar.errorAlInsertar(pieza, Error.DATOS_INCOMPLETOS, pieza[pieza.length - 1]);
                }
            } else {
                insertar.errorAlInsertar(pieza, Error.DATOS_VACIOS, pieza[pieza.length - 1]);
            }

        }
    }

    private void recepcionMueble(ArrayList<String[]> muebles) {
        for (String[] mueble : muebles) {
            if (!verificarDatosVacios(mueble)) {
                try {
                    insertar.insertarTipoMueble(mueble[0], mueble[1], mueble[mueble.length - 1]);
                } catch (Exception e) {
                    insertar.errorAlInsertar(mueble, Error.DATOS_INCOMPLETOS, mueble[mueble.length - 1]);
                }
            } else {
                insertar.errorAlInsertar(mueble, Error.DATOS_VACIOS, mueble[mueble.length - 1]);
            }
        }
    }

    private void recepcionEnsamblePiezas(ArrayList<String[]> ensamble_pieza) {
        for (String[] pieza : ensamble_pieza) {
            if (!verificarDatosVacios(pieza)) {
                try {
                    insertar.insertarEnsamblePiezas(pieza[0], pieza[1], pieza[2], pieza[pieza.length - 1]);
                } catch (Exception e) {
                    insertar.errorAlInsertar(pieza, Error.DATOS_INCOMPLETOS, pieza[pieza.length - 1]);
                }
            } else {
                insertar.errorAlInsertar(pieza, Error.DATOS_VACIOS, pieza[pieza.length - 1]);
            }
        }
    }

    private void recepcionEnsambleMueble(ArrayList<String[]> ensamblar_mueble) {
        for (String[] mueble : ensamblar_mueble) {
            if (!verificarDatosVacios(mueble)) {
                try {
                    insertar.insertarEnsambleMueble(mueble[0], mueble[1], mueble[2], mueble[mueble.length - 1]);
                } catch (Exception e) {
                    insertar.errorAlInsertar(mueble, Error.DATOS_INCOMPLETOS, mueble[mueble.length - 1]);
                }
            } else {
                insertar.errorAlInsertar(mueble, Error.DATOS_VACIOS, mueble[mueble.length - 1]);
            }
        }
    }

    public boolean verificarDatosVacios(String[] datos) {
        for (String dato : datos) {
            if (dato.isEmpty() || dato.isBlank()) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<String> getErrores() {
        return errores;
    }

    public ArrayList<String> getCorrecto() {
        return correcto;
    }
}
