package Archivo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 *
 * @author aguare
 */
public class LecturaArchivo extends Thread {

    private final String[] condiciones = {"Usuario" + '(', "Pieza" + '(', "Mueble" + '(', "Ensamble_Piezas" + '(', "Ensamblar_Mueble" + '('};
    private ArrayList<String> lineas = new ArrayList<String>();
    public ArrayList<String> usuarios = new ArrayList<String>();
    public ArrayList<String> pieza = new ArrayList<String>();
    public ArrayList<String> mueble = new ArrayList<String>();
    public ArrayList<String> ensamble_pieza = new ArrayList<String>();
    public ArrayList<String> ensamblar_mueble = new ArrayList<String>();
    public ArrayList<String> no_reconocido = new ArrayList<String>();

    @Override
    public void run() {
        Leer("/home/aguare/Documentos/ProyectoMuebleria2021/BD/ArchivoPrueba.txt");
    }

    public void Leer(String path) {
        File fichero = new File(path);

        try {
            BufferedReader entrada = new BufferedReader(new InputStreamReader(new FileInputStream(fichero), StandardCharsets.UTF_8));

            while (entrada.ready()) {
                lineas.add(entrada.readLine());
            }
            entrada.close();

        } catch (Exception e) {
            System.out.println("ERROR EN LA LECTURA -> " + path);
        }
        separacion();
        escribirDatos();
    }

    private void separacion() {

        for (int i = 0; i < lineas.size(); i++) {
            String line = lineas.get(i).trim();
            char[] caracteres = line.toCharArray();
            String formado = "";
            for (int j = 0; j < caracteres.length; j++) {

                formado += caracteres[j];
                if (ubicar(formado, line)) {
                    break;
                } else if (j == caracteres.length - 2) {
                    no_reconocido.add((i + 1) + "| " + line);
                }

            }
        }
    }

    private boolean ubicar(String parteLinea, String linea) {
        int ubicado = coincidencia(parteLinea);
        linea = quitarParte(linea, parteLinea);
        switch (ubicado) {
            case 0:
                usuarios.add(linea);
                return true;
            case 1:
                pieza.add(linea);
                return true;
            case 2:
                mueble.add(linea);
                return true;
            case 3:
                ensamble_pieza.add(linea);
                return true;
            case 4:
                ensamblar_mueble.add(linea);
                return true;
            default:
                return false;
        }
    }

    private String quitarParte(String linea, String parte) {
        String devolver = "";
        int cantidad = parte.length();
        devolver = linea.substring(cantidad, linea.length());
        return devolver;
    }

    private int coincidencia(String parteLinea) {
        for (int i = 0; i < condiciones.length; i++) {
            if (parteLinea.equalsIgnoreCase(condiciones[i])) {
                return i;
            }
        }
        return -1;
    }

    private void escribirDatos() {
        System.out.println("-----USUARIOS -> " + usuarios.size());
        for (int i = 0; i < usuarios.size(); i++) {
            System.out.println(usuarios.get(i).toString());
        }
        System.out.println("\n-----PIEZAS -> " + pieza.size());
        for (int i = 0; i < pieza.size(); i++) {
            System.out.println(pieza.get(i).toString());
        }
        System.out.println("\n-----MUEBLES -> " + mueble.size());
        for (int i = 0; i < mueble.size(); i++) {
            System.out.println(mueble.get(i).toString());
        }
        System.out.println("\n-----PIEZAS DE ENSAMBLE -> " + ensamble_pieza.size());
        for (int i = 0; i < ensamble_pieza.size(); i++) {
            System.out.println(ensamble_pieza.get(i).toString());
        }
        System.out.println("\n-----MUEBLES ENSAMBLADOS -> " + ensamble_pieza.size());
        for (int i = 0; i < ensamblar_mueble.size(); i++) {
            System.out.println(ensamblar_mueble.get(i).toString());
        }
        System.out.println("\n*********NO RECONOCIDOS -> " + no_reconocido.size());
        for (int i = 0; i < no_reconocido.size(); i++) {
            System.out.println(no_reconocido.get(i).toString());
        }
    }
}
