package ObtenerObjetos;

import EntidadesFabrica.Usuario;
import EntidadesVenta.Cliente;
import SQL.Conexion;
import SQL.Encriptar;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author aguare
 */
public class ObtenerUC {
    
    /**
     * Verifica la contraseña con el método de encriptación
     * @param usuario
     * @param password
     * @return 
     */
    public boolean verificarPassword(String usuario, String password) {
        String query = "SELECT password FROM Usuario WHERE nombre_usuario = ?;";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setString(1, usuario);
            ResultSet resultado = prepared.executeQuery();
            Encriptar desencriptar = new Encriptar();
            String pass = "";
            while (resultado.next()) {
                pass = resultado.getString("password");
            }
            pass = desencriptar.desencriptarPass(pass, "ipc");
            if (pass.equals(password)) {
                return true;
            }
        } catch (SQLException e) {
        }
        return false;
    }
    
    public Usuario obtenerUsuarioSegunNombre(String nombre) {
        Usuario usuario = null;
        String query = "SELECT * FROM Usuario WHERE nombre_usuario = ?;";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setString(1, nombre);
            ResultSet resultado = prepared.executeQuery();
            while (resultado.next()) {
                usuario = new Usuario(resultado.getString("nombre_usuario"), resultado.getInt("idDepartamento"),
                        resultado.getBoolean("acceso"), resultado.getString("password"));
            }
        } catch (SQLException e) {
        }
        return usuario;
    }
    
    public Usuario obtenerUsuarioSegunNombreConPass(String nombre) {
        Usuario usuario = null;
        String query = "SELECT * FROM Usuario WHERE nombre_usuario = ?;";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setString(1, nombre);
            ResultSet resultado = prepared.executeQuery();
            while (resultado.next()) {
                usuario = new Usuario(resultado.getString("nombre_usuario"), resultado.getInt("idDepartamento"),
                        resultado.getBoolean("acceso"), resultado.getString("password"));
            }
        } catch (SQLException e) {
        }
        return usuario;
    }
    
    /**
     * Obtiene todos los usuarios de un departamento en específico
     * @param opcion 0 Todos, 1 Fábrica, 2 Ventas, 3 Financiero
     * @return
     */
    public ArrayList<Usuario> obtenerUsuariosSegunDepartamento(int opcion) {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        String query = "SELECT * FROM Usuario";
        switch(opcion){
            case 1:
                query = "SELECT * FROM Usuario WHERE idDepartamento = 1;";
                break;
            case 2:
                query = "SELECT * FROM Usuario WHERE idDepartamento = 2;";
                break;
            case 3:
                query = "SELECT * FROM Usuario WHERE idDepartamento = 3;";
                break;
            default:
            break;
        }
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            ResultSet resultado = prepared.executeQuery();
            while (resultado.next()) {
                usuarios.add(new Usuario(resultado.getString("nombre_usuario"), resultado.getInt("idDepartamento"),
                        resultado.getBoolean("acceso"), resultado.getString("password")));
            }
        } catch (SQLException e) {
        }
        return usuarios;
    }
    
    /**
     * Obtiene todos los clientes registrados
     * @return 
     */
    public ArrayList<Cliente> obtenerClientes() {
        ArrayList<Cliente> clientes = new ArrayList<>();
        String query = "SELECT * FROM Cliente;";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            ResultSet resultado = prepared.executeQuery();
            while (resultado.next()) {
                clientes.add( new Cliente(resultado.getString("NIT"), resultado.getString("nombre_cliente"),
                        resultado.getString("direccion")));
            }
        } catch (SQLException e) {
        }
        return clientes;
    }
    
    public Cliente obtenerClientesSegunNIT(String NIT) {
        Cliente cliente = null;
        String query = "SELECT * FROM Cliente WHERE NIT = ?;";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setString(1, NIT);
            ResultSet resultado = prepared.executeQuery();
            while (resultado.next()) {
                cliente= new Cliente(resultado.getString("NIT"), resultado.getString("nombre_cliente"),
                        resultado.getString("direccion"));
            }
        } catch (SQLException e) {
        }
        return cliente;
    }
}
