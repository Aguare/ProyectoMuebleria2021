package Archivo;

import java.io.BufferedReader;
import java.util.ArrayList;

/**
 *
 * @author aguare
 */
public class LecturaArchivo {

    private final String[] condiciones = {"USUARIO" + '(', "PIEZA" + '(', "MUEBLE" + '(', "ENSAMBLE_PIEZAS" + '(',
        "ENSAMBLAR_MUEBLE" + '(', "CLIENTE" + '('};
    private ArrayList<String> lineas = new ArrayList<>();
    private ArrayList<String[]> usuarios = new ArrayList<>();
    private ArrayList<String[]> clientes = new ArrayList<>();
    private ArrayList<String[]> pieza = new ArrayList<>();
    private ArrayList<String[]> mueble = new ArrayList<>();
    private ArrayList<String[]> ensamble_pieza = new ArrayList<>();
    private ArrayList<String[]> ensamblar_mueble = new ArrayList<>();
    private ArrayList<String> no_reconocido = new ArrayList<>();
    private ArrayList<String> no_insertados;

    /**
     * Se lee el archivo mediante el buffer
     *
     * @param entrada es el Buffer que entrega las líneas del archivo
     */
    public boolean Leer(BufferedReader entrada) {
        try {
            while (entrada.ready()) {
                lineas.add(entrada.readLine().trim());
            }
            entrada.close();
        } catch (Exception e) {
            return false;
        }
        separacion();
        return true;
    }

    /**
     * Este método lee linea por linea encontrando las coincidencias con las
     * palabra clave
     */
    private void separacion() {
        for (int i = 0; i < lineas.size(); i++) {
            String line = lineas.get(i).trim();
            char[] caracteres = line.toCharArray();
            String formado = "";
            for (int j = 0; j < caracteres.length; j++) {
                formado += caracteres[j];
                if (ubicar(formado, line, i + 1)) {
                    break;
                } else if (j == caracteres.length - 2) {
                    no_reconocido.add((i + 1) + "|" + line + " <- Sintaxis no reconocida");
                }
            }
        }
    }

    /**
     * Ubica según a la palabra que corresponde en un arreglo diferente
     *
     * @param parteLinea es la parte de la linea que indica a donde pertenece
     * @param linea es toda la linea con los datos a insertar
     * @return retorna V/F si fue ubicado correctamente
     */
    private boolean ubicar(String parteLinea, String linea, int noLinea) {
        int ubicado = coincidencia(parteLinea);
        if (ubicado != -1) {
            linea = quitarParte(linea, parteLinea);
            String[] partes = separarDatos(linea, noLinea);
            if (partes == null) {
                no_reconocido.add(noLinea + "| " + linea + " Error en la sintaxis de las comillas");
                return true;
            } else {
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
        }
        return false;
    }

    /**
     * Quita la parte de la palabra clave para obtener los datos
     *
     * @param linea la linea completa
     * @param parte la palabra clave
     * @return retorna los datos
     */
    private String quitarParte(String linea, String parte) {
        String devolver = "";
        int cantidad = parte.length();
        devolver = linea.substring(cantidad, linea.length() - 1);
        return devolver;
    }

    /**
     * separa los datos segun las (,) y quita las comillas
     *
     * @param linea linea de datos
     * @return retorna un arreglo con los datos
     */
    private String[] separarDatos(String linea, int noLinea) {
        String[] partes;
        partes = linea.split(",");
        if (partes != null) {
            for (int i = 0; i < partes.length; i++) {
                String pedazo = partes[i].trim();
                char[] caracteres = pedazo.toCharArray();
                try {
                    if (caracteres[caracteres.length - 1] == '"' && caracteres[0] == '"') {
                        String extraccion = "";
                        extraccion = pedazo.substring(1, pedazo.length() - 1);
                        partes[i] = extraccion.trim();
                    } else if (caracteres[0] == '"' && caracteres[caracteres.length - 1] != '"'
                            || caracteres[0] != '"' && caracteres[caracteres.length - 1] == '"') {
                        return null;
                    } else {
                        partes[i] = pedazo.trim();
                    }
                } catch (Exception e) {
                }
            }
        }
        return agregarNoLinea(partes, noLinea);
    }

    private String[] agregarNoLinea(String[] partes, int noLinea) {
        String[] nuev = new String[partes.length + 1];
        for (int i = 0; i < nuev.length; i++) {
            if (i == nuev.length - 1) {
                nuev[i] = "" + noLinea;
            } else {
                nuev[i] = partes[i];
            }
        }
        return nuev;
    }

    /**
     * busca si la palabra clave se encuentra en la linea
     *
     * @param parteLinea la palabra clave al inicio de la linea
     * @return retorno el número de posicion de la palabra clave en el array de
     * palabras clave
     */
    private int coincidencia(String parteLinea) {
        for (int i = 0; i < condiciones.length; i++) {
            if (parteLinea.equals(condiciones[i])) {
                return i;
            }
        }
        return -1;
    }

    public ArrayList<String[]> getUsuarios() {
        return usuarios;
    }

    public ArrayList<String[]> getClientes() {
        return clientes;
    }

    public ArrayList<String[]> getPieza() {
        return pieza;
    }

    public ArrayList<String[]> getMueble() {
        return mueble;
    }

    public ArrayList<String[]> getEnsamble_pieza() {
        return ensamble_pieza;
    }

    public ArrayList<String[]> getEnsamblar_mueble() {
        return ensamblar_mueble;
    }

    public ArrayList<String> getNo_reconocido() {
        return no_reconocido;
    }
}
