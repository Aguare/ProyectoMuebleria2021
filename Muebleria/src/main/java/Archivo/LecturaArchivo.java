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
public class LecturaArchivo {

    private final String[] condiciones = {"USUARIO" + '(', "PIEZA" + '(', "MUEBLE" + '(', "ENSAMBLE_PIEZAS" + '(',
        "ENSAMBLAR_MUEBLE" + '(', "CLIENTE" + '('};
    private ArrayList<String> lineas = new ArrayList<>();
    public ArrayList<String[]> usuarios = new ArrayList<>();
    public ArrayList<String[]> clientes = new ArrayList<>();
    public ArrayList<String[]> pieza = new ArrayList<>();
    public ArrayList<String[]> mueble = new ArrayList<>();
    public ArrayList<String[]> ensamble_pieza = new ArrayList<>();
    public ArrayList<String[]> ensamblar_mueble = new ArrayList<>();
    public ArrayList<String> no_reconocido = new ArrayList<>();

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
        ExtraccionDatos extraccion = new ExtraccionDatos(usuarios, clientes, pieza, mueble, ensamble_pieza, ensamblar_mueble, no_reconocido);
        extraccion.insertar();
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
                    no_reconocido.add((i + 1) + "|" + line);
                }
            }
        }
    }

    private boolean ubicar(String parteLinea, String linea) {
        int ubicado = coincidencia(parteLinea);
        if (ubicado != -1) {
            linea = quitarParte(linea, parteLinea);
            String[] partes = separarDatos(linea);
            switch (ubicado) {
                case 0:
                    usuarios.add(partes);
                    return true;
                case 1:
                    pieza.add(partes);
                    return true;
                case 2:
                    mueble.add(partes);
                    return true;
                case 3:
                    ensamble_pieza.add(partes);
                    return true;
                case 4:
                    ensamblar_mueble.add(partes);
                    return true;
                case 5:
                    clientes.add(partes);
                    return true;
                default:
                    return false;
            }
        }
        return false;
    }

    private String quitarParte(String linea, String parte) {
        String devolver = "";
        int cantidad = parte.length();
        devolver = linea.substring(cantidad, linea.length() - 1);
        return devolver;
    }

    private String[] separarDatos(String linea) {
        String[] partes;
        partes = linea.split(",");
        if (partes != null) {
            for (int i = 0; i < partes.length; i++) {
                String pedazo = partes[i];
                char[] caracteres = pedazo.toCharArray();
                if (caracteres[caracteres.length - 1] == '"' && caracteres[0] == '"') {
                    String extraccion = "";
                    extraccion = pedazo.substring(1, pedazo.length() - 1);
                    partes[i] = extraccion;
                } else {
                    partes[i] = pedazo.trim();
                }
            }
        }
        return partes;
    }

    private int coincidencia(String parteLinea) {
        for (int i = 0; i < condiciones.length; i++) {
            if (parteLinea.equals(condiciones[i])) {
                return i;
            }
        }
        return -1;
    }

}
