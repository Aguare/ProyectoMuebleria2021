package SQL;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author aguare
 */
public class Conexion {

    private static final String USER = "root";
    private static final String PASSWORD = "74ef6a14";
    private static final String MYSQL = "jdbc:mysql://localhost:3306/ControlNotas";
    private Connection conecta = null;

    public void conetarBD() {
        try {
            conecta = DriverManager.getConnection(MYSQL, USER, PASSWORD);
        } catch (Exception e) {
            System.out.println("Error al Conectar a la BD");
        }
    }

    public Connection obtenerConexion() {
        return conecta;
    }

    public void cerrarConexion() {
        try {
            conecta.close();
        } catch (Exception e) {
            System.out.println("Error al cerrar la conexión");
        }

    }
}
