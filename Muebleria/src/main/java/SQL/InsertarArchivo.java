package SQL;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author aguare
 */
public class InsertarArchivo {

    private ArrayList<String> noInsertados = new ArrayList<>();
    private Conexion conexion;

    public InsertarArchivo(Conexion conexion) {
        this.conexion = conexion;
    }

    private void recepcionUsuario(ArrayList<String[]> usuarios) {

    }

    private void insertarUsuario(String usuario, String password, int departamento) {
        String query = "INSERT INTO Usuario VALUES(?,?,?)";
        try {
            PreparedStatement prepared = conexion.obtenerConexion().prepareStatement(query);
            prepared.setString(1, usuario);
            prepared.setString(2, password);
            prepared.setInt(3, departamento);
            prepared.executeQuery();
            
        } catch (SQLException ex) {
            noInsertados.add(usuario + " " + password + " " + departamento);
        }
    }

}
