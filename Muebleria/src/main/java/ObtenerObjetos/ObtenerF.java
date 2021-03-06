package ObtenerObjetos;

import EntidadesFabrica.Ensamble;
import EntidadesFabrica.Pieza;
import EntidadesFabrica.PiezasTipoMueble;
import EntidadesFabrica.TipoPiezas;
import SQL.Conexion;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author aguare
 */
public class ObtenerF {
    
    private final ObtenerUC obtenerUC = new ObtenerUC();

    public ArrayList<Pieza> obtenerPiezasSegunTipo(String tipoPieza) {
        ArrayList<Pieza> piezas = new ArrayList<>();
        String query = "SELECT * FROM Pieza WHERE usada = 0 AND Tpnombre_pieza = ?;";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setString(1, tipoPieza);
            ResultSet resultado = prepared.executeQuery();
            while (resultado.next()) {
                piezas.add(new Pieza(resultado.getInt("idPieza"), resultado.getDouble("precio"),
                        resultado.getBoolean("usada"), resultado.getString("TPnombre_pieza")));
            }
        } catch (SQLException e) {
        }
        return piezas;
    }

    /**
     * Se obtienen las piezas en la base de datos
     *
     * @param orden Se ingresa 2 si se quiere que se devuelva en orden DESC
     * @return
     */
    public ArrayList<Pieza> obtenerPiezas(int orden) {
        ArrayList<Pieza> piezas = new ArrayList<>();
        String query = "SELECT * FROM Pieza WHERE usada = 0;";
        switch (orden) {
            case 2:
                query = "SELECT * FROM Pieza WHERE usada = 0 ORDER BY idPieza DESC;";
                break;
            default:
                break;
        }
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            ResultSet resultado = prepared.executeQuery();
            while (resultado.next()) {
                piezas.add(new Pieza(resultado.getInt("idPieza"), resultado.getDouble("precio"),
                        resultado.getBoolean("usada"), resultado.getString("TPnombre_pieza")));
            }
        } catch (SQLException e) {
        }
        return piezas;
    }

    public Pieza obtenerPiezaSegunID(int ID) {
        Pieza pieza = null;
        String query = "SELECT * FROM Pieza WHERE idPieza = ?;";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setInt(1, ID);
            ResultSet resultado = prepared.executeQuery();
            while (resultado.next()) {
                pieza = new Pieza(resultado.getInt("idPieza"), resultado.getDouble("precio"),
                        resultado.getBoolean("usada"), resultado.getString("TPnombre_pieza"));
            }
        } catch (SQLException e) {
        }
        return pieza;
    }

    /**
     * Obtiene los tipo de piezas
     *
     * @param opcion 1 Ascendente 2 Descendente
     * @return
     */
    public ArrayList<TipoPiezas> obtenerTipoPiezas(int opcion) {
        ArrayList<TipoPiezas> tipoPiezas = new ArrayList<>();
        String query;
        switch (opcion) {
            case 1:
                query = "SELECT * FROM TipoPieza ORDER BY cantidad ASC;";
                break;
            case 2:
                query = "SELECT * FROM TipoPieza ORDER BY cantidad DESC;";
                break;
            default:
                query = "SELECT * FROM TipoPieza;";
                break;
        }
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            ResultSet resultado = prepared.executeQuery();
            while (resultado.next()) {
                tipoPiezas.add(new TipoPiezas(resultado.getString("nombre_pieza"), resultado.getInt("cantidad"),
                        resultado.getString("detalles"), obtenerPiezasSegunTipo(resultado.getString("nombre_pieza"))));
            }
        } catch (SQLException e) {
        }
        return tipoPiezas;
    }

    public TipoPiezas obtenerTipoPiezaSegunNombre(String nombre) {
        TipoPiezas tipoPiezas = null;
        String query = "SELECT * FROM TipoPieza WHERE nombre_pieza = ?;";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setString(1, nombre);
            ResultSet resultado = prepared.executeQuery();
            while (resultado.next()) {
                tipoPiezas = new TipoPiezas(resultado.getString("nombre_pieza"), resultado.getInt("cantidad"),
                        resultado.getString("detalles"), obtenerPiezasSegunTipo(resultado.getString("nombre_pieza")));
            }
        } catch (SQLException e) {
        }
        return tipoPiezas;
    }

    /**
     * Este m??todo devuelve una lista de las piezas utilizadas en un ensamble
     * @param idEnsamble
     * @return 
     */
    public ArrayList<Pieza> obtenerPiezasDeEnsamble(String idEnsamble) {
        ArrayList<Pieza> piezas = new ArrayList<>();
        String query = "SELECT * FROM PiezaEnsamble WHERE E_idEnsamble = ?;";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setString(1, idEnsamble);
            ResultSet resultado = prepared.executeQuery();
            while (resultado.next()) {
                piezas.add(obtenerPiezaSegunID(resultado.getInt("P_idPieza")));
            }
        } catch (SQLException e) {
        }
        return piezas;
    }
    
    public Ensamble obtenerEnsambleSegunID(String id) {
        Ensamble ensamble = null;
        String query = "SELECT * FROM Ensamble WHERE idEnsamble = ?;";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setString(1, id);
            ResultSet resultado = prepared.executeQuery();
            while (resultado.next()) {
                Date fechaBD = resultado.getDate("fecha");
                LocalDate fecha = fechaBD.toLocalDate();
                ensamble = new Ensamble(resultado.getInt("idEnsamble"), fecha,
                        obtenerUC.obtenerUsuarioSegunNombre(resultado.getString("nombre_usuario")),
                        resultado.getString("TipoMueble"), obtenerPiezasDeEnsamble(id), resultado.getBoolean("reintegro"));
            }
        } catch (SQLException e) {
        }
        return ensamble;
    }

    /**
     * Obtiene todos los ensambles registrados
     * @return 
     */
    public ArrayList<Ensamble> obtenerEnsambles() {
        ArrayList<Ensamble> ensambles = new ArrayList<>();
        String query = "SELECT * FROM Ensamble;";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            ResultSet resultado = prepared.executeQuery();
            while (resultado.next()) {
                Date fechaBD = resultado.getDate("fecha");
                LocalDate fecha = fechaBD.toLocalDate();
                ensambles.add(new Ensamble(resultado.getInt("idEnsamble"), fecha,
                        obtenerUC.obtenerUsuarioSegunNombre(resultado.getString("nombre_usuario")),
                        resultado.getString("TipoMueble"), obtenerPiezasDeEnsamble("" + resultado.getInt("idEnsamble")),resultado.getBoolean("reintegro")));
            }
        } catch (SQLException e) {
        }
        return ensambles;
    }
    
    /**
     * Obtiene todos los ensambles entre dos fechas
     * @param fechaInicial
     * @param fechaFinal
     * @return 
     */
    public ArrayList<Ensamble> obtenerEnsamblesPorFecha(String fechaInicial, String fechaFinal) {
        ArrayList<Ensamble> ensambles = new ArrayList<>();
        String query = "SELECT * FROM Ensamble WHERE fecha BETWEEN ? AND ?;";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setString(1, fechaInicial);
            prepared.setString(2, fechaFinal);
            ResultSet resultado = prepared.executeQuery();
            while (resultado.next()) {
                Date fechaBD = resultado.getDate("fecha");
                LocalDate fecha = fechaBD.toLocalDate();
                ensambles.add(new Ensamble(resultado.getInt("idEnsamble"), fecha,
                        obtenerUC.obtenerUsuarioSegunNombre(resultado.getString("nombre_usuario")),
                        resultado.getString("TipoMueble"), obtenerPiezasDeEnsamble("" + resultado.getInt("idEnsamble")),resultado.getBoolean("reintegro")));
            }
        } catch (SQLException e) {
        }
        return ensambles;
    }
    
    /**
     * Obtiene las piezas que necesita un mueble seg??n su tipo ES SU RECETA
     * @param tipoMueble
     * @return 
     */
    public PiezasTipoMueble obtenerPiezasTipoMueble(String tipoMueble) {
        PiezasTipoMueble piezas = null;
        ArrayList<TipoPiezas> piezasN = new ArrayList<>();
        ArrayList<Integer> cantidades = new ArrayList<>();
        String query = "SELECT * FROM PiezasTipoMueble WHERE TMnombre_mueble = ?;";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setString(1, tipoMueble);
            ResultSet resultado = prepared.executeQuery();
            while (resultado.next()) {
                piezasN.add(obtenerTipoPiezaSegunNombre(resultado.getString("TPnombre_pieza")));
                cantidades.add(resultado.getInt("cantidad"));
            }
            piezas = new PiezasTipoMueble(piezasN, cantidades);
            return piezas;
        } catch (SQLException e) {
        }
        return piezas;
    }
}
