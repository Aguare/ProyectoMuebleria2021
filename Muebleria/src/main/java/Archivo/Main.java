package Archivo;

import EntidadesFabrica.TipoMueble;
import EntidadesFabrica.TipoPiezas;
import SQL.ObtenerObj;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author aguare
 */
public class Main {

    public static void main(String[] args) throws IOException {
        //LecturaArchivo leer = new LecturaArchivo();
        //leer.Leer("/home/aguare/Documentos/ProyectoMuebleria2021/BD/ArchivoPrueba.txt");
        ObtenerObj obtener = new ObtenerObj();
        ArrayList<TipoPiezas> piezas = obtener.obtenerTipoPiezas();
        for (TipoPiezas pieza : piezas) {
            pieza.imprimir();
        }
        ArrayList<TipoMueble> muebles = obtener.obtenerTipoMuebles();
        for (TipoMueble mueble : muebles) {
            mueble.imprimir();
        }
    }
}
