package Archivo;

import java.io.IOException;

/**
 *
 * @author aguare
 */
public class Main {

    public static void main(String[] args) throws IOException {
        LecturaArchivo leer = new LecturaArchivo();
        leer.Leer("/home/aguare/Documentos/ProyectoMuebleria2021/BD/ArchivoPrueba.txt");
    }
}
