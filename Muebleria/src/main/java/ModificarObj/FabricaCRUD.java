package ModificarObj;

import EntidadesFabrica.Pieza;
import SQL.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author marco
 */
public class FabricaCRUD {

    public static final int INSERTAR = 1;
    public static final int ACTUALIZAR = 2;
    public static final int ELIMINAR = 3;

    public boolean modificarPieza(Pieza pieza, int opcion) {
        String query = "";
        String insertar = "INSERT INTO Pieza (precio,usada,TPnombre_pieza) VALUES (?,?,?);";
        String actualizar = "UPDATE Pieza SET precio = ?, TPnombre_pieza = ? WHERE idPieza = ?;";
        String eliminar = "DELETE FROM Pieza WHERE idPieza = ?;";
        try {
            PreparedStatement prepared;
            switch (opcion) {
                case INSERTAR:
                    query = insertar;
                    prepared = Conexion.Conexion().prepareStatement(query);
                    prepared.setDouble(1, pieza.getPrecio());
                    prepared.setString(2, "0");
                    prepared.setString(3, pieza.getTipoPieza());
                    aumentarDisminuirPieza(pieza.getTipoPieza(), 1);
                    break;
                case ACTUALIZAR:
                    query = actualizar;
                    prepared = Conexion.Conexion().prepareStatement(query);
                    prepared.setDouble(1, pieza.getPrecio());
                    prepared.setString(2, pieza.getNuevoTipo());
                    prepared.setInt(3, pieza.getIdPieza());
                    if (!pieza.getTipoPieza().equalsIgnoreCase(pieza.getNuevoTipo())) {
                        aumentarDisminuirPieza(pieza.getTipoPieza(), 2);
                        aumentarDisminuirPieza(pieza.getNuevoTipo(), 1);
                    }
                    break;
                case ELIMINAR:
                    query = eliminar;
                    prepared = Conexion.Conexion().prepareStatement(query);
                    prepared.setInt(1, pieza.getIdPieza());
                    aumentarDisminuirPieza(pieza.getTipoPieza(), 2);
                    break;
                default:
                    return false;
            }
            prepared.executeUpdate();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    /**
     * Aumenta o disminuye la cantidad de pizas según el Tipo de Pieza
     *
     * @param nombre_pieza Tipo de Pieza
     * @param opcion 1 si se desea aumentar 2 para disminuir
     */
    public void aumentarDisminuirPieza(String nombre_pieza, int opcion) {
        int cantidadExistente = obtenerCantidadExistente(nombre_pieza);
        if (opcion == 1) {
            cantidadExistente++;
        } else {
            cantidadExistente--;
        }
        String query = "UPDATE TipoPieza SET cantidad = ? WHERE nombre_pieza = ?";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setInt(1, cantidadExistente);
            prepared.setString(2, nombre_pieza);
            prepared.executeUpdate();
        } catch (Exception e) {
        }
    }

    private int obtenerCantidadExistente(String nombre_pieza) {
        String query = "SELECT cantidad FROM TipoPieza WHERE nombre_pieza = ?";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setString(1, nombre_pieza);
            ResultSet resultado = prepared.executeQuery();
            int num = -1;
            while (resultado.next()) {
                num = resultado.getInt("cantidad");
            }
            return num;
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * Obtiene la última pieza insertada
     *
     * @return
     */
    public Pieza obtenerUltimaPieza() {
        String query = "SELECT * FROM Pieza WHERE usada = 0 ORDER BY idPieza DESC LIMIT 1;";
        Pieza pieza = null;
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
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
     * Cambia el estado de una pieza que no está usada a usada
     * @param pieza
     * @param idEnsamble se debe enviar el id del ensamble para ser actualizadas
     */
    public void actualizarPiezaUsada(Pieza pieza, String idEnsamble) {
        String query = "UPDATE Pieza SET usada = 1 WHERE idPieza = ?";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setInt(1, pieza.getIdPieza());
            prepared.executeUpdate();
            aumentarDisminuirPieza(pieza.getTipoPieza(), 2);
            insertarPiezaEnsamble(idEnsamble, pieza);
        } catch (Exception e) {
        }
    }
    
    /**
     * Inserta la pieza que se utilizó en el ensamble
     * @param idEnsamble
     * @param pieza 
     */
    private void insertarPiezaEnsamble(String idEnsamble, Pieza pieza) {
        String query = "INSERT INTO PiezaEnsamble VALUES (?,?,?);";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setString(1, idEnsamble);
            prepared.setInt(2, pieza.getIdPieza());
            prepared.setString(3, pieza.getTipoPieza());
            prepared.executeUpdate();
        } catch (SQLException ex) {
        }
    }

    public boolean estaEnVentas(int idEnsamble) {
        String query = "SELECT * FROM Mueble WHERE E_idEnsamble = ?;";
        int resul = 0;
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setInt(1, idEnsamble);
            ResultSet resultado = prepared.executeQuery();
            while (resultado.next()) {
                resul = resultado.getInt("E_idEnsamble");
            }
            if (resul == idEnsamble) {
                return true;
            }
        } catch (SQLException e) {
        }
        return false;
    }
    
    public boolean actualizarEnsamble(String fecha, String usuario, int idEnsamble){
        String query = "UPDATE Ensamble SET fecha = ?, nombre_usuario = ? WHERE idEnsamble = ?;";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setString(1, fecha);
            prepared.setString(2, usuario);
            prepared.setInt(3, idEnsamble);
            prepared.executeUpdate();
            return true;
        } catch (Exception e) {
        }
        return false;
    }
}
