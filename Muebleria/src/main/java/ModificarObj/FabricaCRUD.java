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
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            switch (opcion) {
                case INSERTAR:
                    query = insertar;
                    prepared.setDouble(1, pieza.getPrecio());
                    prepared.setString(2, "0");
                    prepared.setString(3, pieza.getTipoPieza());
                    aumentarDisminuirPieza(pieza.getTipoPieza(), 1);
                    break;
                case ACTUALIZAR:
                    query = actualizar;
                    prepared.setDouble(1, pieza.getPrecio());
                    prepared.setString(2, pieza.getTipoPieza());
                    break;
                case ELIMINAR:
                    query = eliminar;
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
    private void aumentarDisminuirPieza(String nombre_pieza, int opcion) {
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
}
