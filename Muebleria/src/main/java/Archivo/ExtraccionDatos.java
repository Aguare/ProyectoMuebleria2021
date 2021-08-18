package Archivo;

import java.util.ArrayList;

/**
 *
 * @author aguare
 */
public class ExtraccionDatos {

    private ArrayList<String[]> usuarios;
    public ArrayList<String[]> clientes;
    private ArrayList<String[]> pieza;
    private ArrayList<String[]> mueble;
    private ArrayList<String[]> ensamble_pieza;
    private ArrayList<String[]> ensamblar_mueble;
    private ArrayList<String> no_reconocido;
    private InsertarArchivo insertar = new InsertarArchivo();

    public ExtraccionDatos(ArrayList<String[]> usuarios, ArrayList<String[]> clientes, ArrayList<String[]> pieza, ArrayList<String[]> mueble,
            ArrayList<String[]> ensamble_pieza, ArrayList<String[]> ensamblar_mueble, ArrayList<String> no_reconocido) {
        this.usuarios = usuarios;
        this.clientes = clientes;
        this.pieza = pieza;
        this.mueble = mueble;
        this.ensamble_pieza = ensamble_pieza;
        this.ensamblar_mueble = ensamblar_mueble;
        this.no_reconocido = no_reconocido;
    }

    public void insertar() {
        recepcionUsuario();
        recepcionPiezas();
        recepcionMueble();
        recepcionEnsamblePiezas();
        recepcionEnsambleMueble();
        recepcionClientes();
        insertar.escribirNoInsertados();
    }

    private void recepcionUsuario() {
        for (String[] usuario : usuarios) {
            try {
                insertar.insertarUsuario(usuario[0], usuario[1], usuario[2]);
            } catch (Exception e) {
                insertar.errorAlInsertar(usuario);
            }
        }
    }

    private void recepcionClientes() {
        for (String[] cliente : clientes) {
            try {
                insertar.insertarCliente(cliente[0], cliente[1], cliente[2]);
            } catch (Exception e) {
                insertar.errorAlInsertar(cliente);
            }
        }
    }

    private void recepcionPiezas() {
        for (String[] pieza : pieza) {
            try {
                insertar.insertarTipo(pieza[0], pieza[1]);
                insertar.InsertarPieza(pieza[0], pieza[1]);
            } catch (Exception e) {
                insertar.errorAlInsertar(pieza);
            }

        }
    }

    private void recepcionMueble() {
        for (String[] mueble : mueble) {
            try {
                insertar.insertarTipoMueble(mueble[0], mueble[1]);
            } catch (Exception e) {
                insertar.errorAlInsertar(mueble);
            }
        }
    }

    private void recepcionEnsamblePiezas() {
        for (String[] pieza : ensamble_pieza) {
            try {
                insertar.insertarEnsamblePiezas(pieza[0], pieza[1], pieza[2]);
            } catch (Exception e) {
                insertar.errorAlInsertar(pieza);
            }
        }
    }

    private void recepcionEnsambleMueble() {
        for (String[] mueble : ensamblar_mueble) {
            try {
                insertar.insertarEnsambleMueble(mueble[0], mueble[1], mueble[2]);
            } catch (Exception e) {
                insertar.errorAlInsertar(mueble);
            }
        }
    }

}
