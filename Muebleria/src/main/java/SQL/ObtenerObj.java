package SQL;

import EntidadesFabrica.Ensamble;
import EntidadesFabrica.Pieza;
import EntidadesFabrica.PiezasTipoMueble;
import EntidadesFabrica.TipoMueble;
import EntidadesFabrica.TipoPiezas;
import EntidadesFabrica.Usuario;
import EntidadesVenta.Mueble;
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
public class ObtenerObj {

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

    public Usuario obtenerUsuarioSegunNombre(String nombre) {
        Usuario usuario = null;
        String query = "SELECT * FROM Usuario WHERE nombre_usuario = ?;";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setString(1, nombre);
            ResultSet resultado = prepared.executeQuery();
            while (resultado.next()) {
                usuario = new Usuario(resultado.getString("nombre_usuario"), resultado.getInt("idDepartamento"),
                        resultado.getBoolean("acceso"));
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
                        resultado.getBoolean("acceso")));
            }
        } catch (SQLException e) {
        }
        return usuarios;
    }


    public ArrayList<Pieza> obtenerPiezasDeEnsamble(String idEnsamble) {
        ArrayList<Pieza> piezas = new ArrayList<>();
        String query = "SELECT * FROM PiezasEnsamble WHERE E_idEnsamble = ?;";
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
                        obtenerUsuarioSegunNombre(resultado.getString("nombre_usuario")),
                        resultado.getString("TipoMueble"), obtenerPiezasDeEnsamble(id));
            }
        } catch (SQLException e) {
        }
        return ensamble;
    }

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
                        obtenerUsuarioSegunNombre(resultado.getString("nombre_usuario")),
                        resultado.getString("TipoMueble"), obtenerPiezasDeEnsamble("" + resultado.getInt("idEnsamble"))));
            }
        } catch (SQLException e) {
        }
        return ensambles;
    }

    public ArrayList<Mueble> obtenerMuebles(String tipoMueble) {
        ArrayList<Mueble> muebles = new ArrayList<>();
        String query = "SELECT * FROM Mueble WHERE TMnombre_mueble = ?;";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setString(1, tipoMueble);
            ResultSet resultado = prepared.executeQuery();
            while (resultado.next()) {
                muebles.add(new Mueble(resultado.getInt("idMueble"), resultado.getDouble("precio_costo"),
                        obtenerEnsambleSegunID(resultado.getString("E_idEnsamble")), resultado.getString("TMnombre_mueble"),
                        consultarDevolucion(resultado.getInt("idMueble"))));
            }
        } catch (SQLException e) {
        }
        return muebles;
    }

    public ArrayList<Mueble> obtenerMueblesTodos() {
        ArrayList<Mueble> muebles = new ArrayList<>();
        String query = "SELECT * FROM Mueble;";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            ResultSet resultado = prepared.executeQuery();
            while (resultado.next()) {
                muebles.add(new Mueble(resultado.getInt("idMueble"), resultado.getDouble("precio_costo"),
                        obtenerEnsambleSegunID(resultado.getString("E_idEnsamble")), resultado.getString("TMnombre_mueble"),
                        consultarDevolucion(resultado.getInt("idMueble"))));
            }
        } catch (SQLException e) {
        }
        return muebles;
    }

    public boolean consultarDevolucion(int idMueble) {
        String query = "SELECT devuelto FROM Compra WHERE idMueble = ?;";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setInt(1, idMueble);
            ResultSet resultado = prepared.executeQuery();
            while (resultado.next()) {
                return resultado.getBoolean("devuelto");
            }
        } catch (SQLException e) {
        }
        return false;
    }

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

    /**
     * Obtiene todos los tipos de muebles como objeto, con su receta cantida,
     * etc.
     *
     * @return Retorna un arreglo de TipoMueble
     */
    public ArrayList<TipoMueble> obtenerTipoMuebles() {
        ArrayList<TipoMueble> tipoMuebles = new ArrayList<>();
        String query = "SELECT * FROM TipoMueble;";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            ResultSet resultado = prepared.executeQuery();
            while (resultado.next()) {
                tipoMuebles.add(new TipoMueble(resultado.getString("nombre_mueble"),
                        resultado.getDouble("precio_venta"), resultado.getInt("cantidad"),
                        resultado.getString("detalles"), obtenerMuebles(resultado.getString("nombre_mueble")),
                        obtenerPiezasTipoMueble(resultado.getString("nombre_mueble"))));
            }
        } catch (SQLException e) {
        }
        return tipoMuebles;
    }

    /**
     * Obtiene un tipo de mueble según su nombre
     *
     * @return Devulve un TipoMueble
     */
    public TipoMueble obtenerTipoMuebleSegunNombre(String tipoMueble) {
        TipoMueble tipo = null;
        String query = "SELECT * FROM TipoMueble WHERE nombre_mueble = ?;";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setString(1, tipoMueble);
            ResultSet resultado = prepared.executeQuery();
            while (resultado.next()) {
                tipo = new TipoMueble(resultado.getString("nombre_mueble"),
                        resultado.getDouble("precio_venta"), resultado.getInt("cantidad"),
                        resultado.getString("detalles"), obtenerMuebles(resultado.getString("nombre_mueble")),
                        obtenerPiezasTipoMueble(resultado.getString("nombre_mueble")));
            }
        } catch (SQLException e) {
        }
        return tipo;
    }
}
