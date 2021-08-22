package SQL;

import EntidadesFabrica.Pieza;
import EntidadesFabrica.TipoPiezas;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author aguare
 */
public class ObtenerObj {

    private Conexion conexion = new Conexion();

    public ArrayList<Pieza> obtenerPiezasSegunTipo(String tipoPieza) {
        ArrayList<Pieza> piezas = new ArrayList<>();
        String query = "SELECT * FROM Pieza WHERE TPnombre_pieza = ?;";
        try {
            PreparedStatement prepared = conexion.obtenerConexion().prepareStatement(query);
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

    public ArrayList<TipoPiezas> obtenerTipoPiezas() {
        ArrayList<TipoPiezas> tipoPiezas = new ArrayList<>();
        String query = "SELECT * FROM TipoPieza;";
        try {
            PreparedStatement prepared = conexion.obtenerConexion().prepareStatement(query);
            ResultSet resultado = prepared.executeQuery();
            while (resultado.next()) {
                tipoPiezas.add(new TipoPiezas(resultado.getString("nombre_pieza"), resultado.getInt("cantidad"),
                        resultado.getString("detalles"), obtenerPiezasSegunTipo(resultado.getString("nombre_pieza"))));
            }
        } catch (SQLException e) {
            
        }
        return tipoPiezas;
    }
}
