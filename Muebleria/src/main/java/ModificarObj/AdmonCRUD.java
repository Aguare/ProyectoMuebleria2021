package ModificarObj;

import SQL.Conexion;
import SQL.Encriptar;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author aguare
 */
public class AdmonCRUD {
    
    public static final int INSERTAR = 1;
    public static final int ACTUALIZAR = 2;
    public static final int ELIMINAR = 3;

    /**
     * Agrega un nuevo usuario a la bd
     *
     * @param usuario nombre del usuario
     * @param password contraseña
     * @param departamento id del departamento
     * @param acceso 1 si tendrá acceso, 2 si no tendrá acceso
     * @param opcion 1 para insertar, 2 para actualizar datos
     * @return
     */
    public boolean modificarUsuario(String usuario, String password, String departamento, int acceso, int opcion) {
       String query;
       String insertar = "INSERT INTO Usuario VALUES(?,?,?,?);";
       String actualizar = "UPDATE Usuario SET password = ?, acceso = ?, idDepartamento = ? WHERE nombre_usuario = ?;";
        try {
            Encriptar encriptar = new Encriptar();
            String passEncriptada = encriptar.encriptarPass(password);
            int idDep = obtenerIdDepartamento(departamento);
            PreparedStatement prepared;
            switch (opcion) {
                case INSERTAR:
                    query = insertar;
                    prepared = Conexion.Conexion().prepareStatement(query);
                    prepared.setString(1, usuario);
                    prepared.setString(2, passEncriptada);
                    prepared.setInt(3, acceso);
                    prepared.setInt(4, idDep);
                    break;
               case ACTUALIZAR:
                    query = actualizar;
                    prepared = Conexion.Conexion().prepareStatement(query);
                    prepared.setString(1, passEncriptada);
                    prepared.setInt(2, acceso);
                    prepared.setInt(3, idDep);
                    prepared.setString(4, usuario);
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

    public int obtenerIdDepartamento(String nombre) {
        if (nombre.equalsIgnoreCase("Fábrica") || nombre.equalsIgnoreCase("Fabrica")) {
            return 1;
        } else if (nombre.equalsIgnoreCase("Punto de Venta")) {
            return 2;
        } else if (nombre.equalsIgnoreCase("Administración") || nombre.equalsIgnoreCase("Administracion")) {
            return 3;
        } else {
            return -1;
        }
    }

}
