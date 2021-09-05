package ObtenerObjetos;

import EntidadesVenta.Factura;
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
public class ObtenerAd {
    
    private ObtenerUC obtenerUC = new ObtenerUC();
    
    /**
     * Devuelve los departamentos para uso del fronted
     * @return 
     */
    public ArrayList<String> obtenerDepartamentos() {
        ArrayList<String> departamentos = new ArrayList<>();
        departamentos.add("FÁBRICA");
        departamentos.add("PUNTO DE VENTA");
        departamentos.add("ADMINISTRACIÓN");
        return departamentos;
    }

    /**
     * Obtiene el id del departamento según el nombre seleccionado en el fronted
     * @param nombre
     * @return 
     */
    public int obtenerIdDepartamento(String nombre) {
        switch (nombre) {
            case "FÁBRICA":
                return 1;
            case "PUNTO DE VENTA":
                return 2;
            case "ADMINISTRACIÓN":
                return 3;
            default:
                return -1;
        }
    }
    
    /**
     * Obtiene las vetnas en un intervalo de tiempo
     * @param fechaInicial
     * @param fechaFinal
     * @return 
     */
    public ArrayList<Factura> obtenerFacturasSegunFecha(String fechaInicial, String fechaFinal) {
        ArrayList<Factura> facturas = new ArrayList<>();
        String query = "SELECT * FROM Factura WHERE fecha BETWEEN ? AND ?;";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setString(1, fechaInicial);
            prepared.setString(2, fechaFinal);
            ResultSet resultado = prepared.executeQuery();
            while (resultado.next()) {
                Date fechaSQL = resultado.getDate("fecha");
                LocalDate fecha = fechaSQL.toLocalDate();
                facturas.add(new Factura(resultado.getInt("no_factura"), fecha,
                        resultado.getDouble("total"), obtenerUC.obtenerClientesSegunNIT(resultado.getString("Cliente_NIT")),
                        resultado.getString("Usuario_user")));
            }
        } catch (SQLException e) {
        }
        return facturas;
    }
    
    /**
     * Obtiene todas las facturas
     * @return 
     */
    public ArrayList<Factura> obtenerFacturas() {
        ArrayList<Factura> facturas = new ArrayList<>();
        String query = "SELECT * FROM Factura;";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            ResultSet resultado = prepared.executeQuery();
            while (resultado.next()) {
                Date fechaSQL = resultado.getDate("fecha");
                LocalDate fecha = fechaSQL.toLocalDate();
                facturas.add(new Factura(resultado.getInt("no_factura"), fecha,
                        resultado.getDouble("total"), obtenerUC.obtenerClientesSegunNIT(resultado.getString("Cliente_NIT")),
                        resultado.getString("Usuario_user")));
            }
        } catch (SQLException e) {
        }
        return facturas;
    }
    
    /**
     * Devuelve la perdida que se ha tenido en determinada fecha
     * @param fechaInicial
     * @param fechaFinal
     * @return 
     */
    public double obtenerPerdidaIntervaloTiempo(String fechaInicial, String fechaFinal){
        double perdida = 0;
        String query = "SELECT perdida FROM Devolucion WHERE fecha BETWEEN ? AND ?;";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setString(1, fechaInicial);
            prepared.setString(2, fechaFinal);
            ResultSet resultado = prepared.executeQuery();
            while (resultado.next()) {
                perdida+= resultado.getDouble("perdida");
            }
        } catch (SQLException e) {
        }
        return perdida;
    }
    
    /**
     * Devuelve la perdida de todos los registros juntos
     * @return 
     */
    public double obtenerPerdidaTotal(){
        double perdida = 0;
        String query = "SELECT perdida FROM Devolucion;";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            ResultSet resultado = prepared.executeQuery();
            while (resultado.next()) {
                perdida+= resultado.getDouble("perdida");
            }
        } catch (SQLException e) {
        }
        return perdida;
    }
    
    /**
     * Obtiene todos los usuarios y su cantidad de ventas
     * @return 
     */
    public ArrayList<String[]> obtenerUsuarioConMasVentas(){
        ArrayList<String[]> datos = new ArrayList<>();
        String query = "SELECT COUNT(Usuario_user) AS Cantidad, Usuario_user FROM Factura GROUP BY Usuario_user ORDER BY Cantidad DESC;";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            ResultSet resultado = prepared.executeQuery();
            while (resultado.next()) {
               datos.add(new String[]{resultado.getString("Usuario_user"), resultado.getString("Cantidad"), 
                   ""+obtenerTotalGenerado(resultado.getString("Usuario_user"))});
            }
        } catch (SQLException e) {
        }
        return datos;
    }
    
    public double obtenerTotalGenerado(String nombreUsuario){
        double total = 0;
        String query = "SELECT SUM(total) AS TotalGenerado FROM Factura WHERE Usuario_user = ? GROUP BY total;";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setString(1, nombreUsuario);
            ResultSet resultado = prepared.executeQuery();
            while (resultado.next()) {
               total += resultado.getDouble("TotalGenerado");
            }
        } catch (SQLException e) {
        }
        return total;
    }
    
    /**
     * Obtiene al usuario con mas ventas en un intervalo de fechas
     * @param fechaInicial
     * @param fechaFinal
     * @return 
     */
    public ArrayList<String[]> obtenerUsuarioConMasVentasFechas(String fechaInicial, String fechaFinal){
        ArrayList<String[]> datos = new ArrayList<>();
        String query = "SELECT COUNT(Usuario_user) AS Cantidad, Usuario_user FROM Factura WHERE fecha BETWEEN ? AND ? GROUP BY Usuario_user ORDER BY Cantidad DESC LIMIT 1;";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setString(1, fechaInicial);
            prepared.setString(2, fechaFinal);
            ResultSet resultado = prepared.executeQuery();
            while (resultado.next()) {
               datos.add(new String[]{resultado.getString("Usuario_user"), resultado.getString("Cantidad"), 
                   ""+obtenerTotalGeneradoFechas(fechaInicial, fechaFinal, resultado.getString("Usuario_user"))});
            }
        } catch (SQLException e) {
        }
        return datos;
    }
    
    /**
     * Obtiene el total de venta por el usuario en determinada fecha
     * @param fechaInicial
     * @param fechaFinal
     * @param nombreUsuario
     * @return 
     */
    public double obtenerTotalGeneradoFechas(String fechaInicial, String fechaFinal, String nombreUsuario){
        double total = 0;
        String query = "SELECT SUM(total) AS TotalGenerado FROM Factura WHERE Usuario_user = ? AND fecha BETWEEN ? AND ? GROUP BY total;";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setString(1, nombreUsuario);
            prepared.setString(2, fechaInicial);
            prepared.setString(3, fechaFinal);
            ResultSet resultado = prepared.executeQuery();
            while (resultado.next()) {
               total += resultado.getDouble("TotalGenerado");
            }
        } catch (SQLException e) {
        }
        return total;
    }
    
    /**
     * Obtiene al usuario con mas ganancias en determinado intervalo de fechas
     * @param fechaInicial
     * @param fechaFinal
     * @return 
     */
    public ArrayList<String[]> obtenerUsuarioConMasGananciaFechas(String fechaInicial, String fechaFinal){
        ArrayList<String[]> datos = new ArrayList<>();
        String query = "SELECT SUM(total) AS TotalGenerado, Usuario_user FROM Factura WHERE fecha BETWEEN ? AND ? GROUP BY Usuario_user ORDER BY TotalGenerado DESC LIMIT 1;";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setString(1, fechaInicial);
            prepared.setString(2, fechaFinal);
            ResultSet resultado = prepared.executeQuery();
            while (resultado.next()) {
               datos.add(new String[]{resultado.getString("Usuario_user"), resultado.getString("TotalGenerado"),fechaInicial,fechaFinal});
            }
        } catch (SQLException e) {
        }
        return datos;
    }
    
    /**
     * Obtiene a todos los usuarios y las ganancias generadas
     * @return 
     */
    public ArrayList<String[]> obtenerUsuarioConMasGanancia(){
        ArrayList<String[]> datos = new ArrayList<>();
        String query = "SELECT SUM(total) AS TotalGenerado, Usuario_user FROM Factura GROUP BY Usuario_user ORDER BY TotalGenerado DESC;";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            LocalDate fecha = LocalDate.now();
            ResultSet resultado = prepared.executeQuery();
            while (resultado.next()) {
               datos.add(new String[]{resultado.getString("Usuario_user"), resultado.getString("TotalGenerado"),"--",fecha.toString()});
            }
        } catch (SQLException e) {
        }
        return datos;
    }
}
