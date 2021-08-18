package SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author aguare
 */
public class Conexion {

    private static final String USER = "root";
    private static final String PASSWORD = "74ef6a14";
    private static final String MYSQL = "jdbc:mysql://localhost:3306/Muebleria";
    private static Connection conecta;

    public Conexion() {
        try {
            conecta = DriverManager.getConnection(MYSQL, USER, PASSWORD);
        } catch (SQLException e) {
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
