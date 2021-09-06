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
    private ObtenerV obtenerV = new ObtenerV();
    
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
     * Obtiene el precio costo de los muebles vendido en un intervalo de tiempo
     * @param fechaInicial
     * @param fechaFinal
     * @return 
     */
    public double obtenerCostoProduccionIntervaloTiempo(String fechaInicial, String fechaFinal){
        double costo = 0;
        String query = "SELECT SUM(precio_costo) AS Costo FROM Mueble WHERE EXISTS (SELECT C_idMueble FROM Compra WHERE Compra.C_idMueble = Mueble.idMueble AND Cfecha BETWEEN ? and ?);";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setString(1, fechaInicial);
            prepared.setString(2, fechaFinal);
            ResultSet resultado = prepared.executeQuery();
            while (resultado.next()) {
                costo+= resultado.getDouble("Costo");
            }
        } catch (SQLException e) {
        }
        return costo;
    }
    
    public double obtenerCostoProduccionTotal(){
        double costo = 0;
        String query = "SELECT SUM(precio_costo) AS Costo FROM Mueble WHERE EXISTS (SELECT C_idMueble FROM Compra WHERE Compra.C_idMueble = Mueble.idMueble AND devuelto = 0);";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            ResultSet resultado = prepared.executeQuery();
            while (resultado.next()) {
                costo+= resultado.getDouble("Costo");
            }
        } catch (SQLException e) {
        }
        return costo;
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

    /**
     * Devuelve datos para una tabla con el mueble más vendido en determinada fecha
     * @param fechaInicial
     * @param fechaFinal
     * @param opcion 1 orden descendiente 2 Ascendente
     * @return 
     */
    public ArrayList<String[]> muebleMasVendidoSegunFecha(String fechaInicial, String fechaFinal, int opcion){
        ArrayList<String[]> datos = new ArrayList<>();
        datos.add(new String[]{"TIPO DE MUEBLE", "CANTIDAD", "PRECIO", "TOTAL GENERADO", "DESDE", "A"});
        String query = "SELECT COUNT(C_tipoMueble) AS Cantidad, C_tipoMueble FROM Compra WHERE EXISTS (SELECT no_factura FROM Factura WHERE fecha BETWEEN ? AND ?) GROUP BY C_tipoMueble ORDER BY Cantidad DESC LIMIT 1;";
        if (opcion == 2) {
            query = "SELECT COUNT(C_tipoMueble) AS Cantidad, C_tipoMueble FROM Compra WHERE EXISTS (SELECT no_factura FROM Factura WHERE fecha BETWEEN ? AND ?) GROUP BY C_tipoMueble ORDER BY Cantidad ASC LIMIT 1;";
        }
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setString(1, fechaInicial);
            prepared.setString(2, fechaFinal);
            ResultSet resultado = prepared.executeQuery();
            while (resultado.next()) {
                double precio = obtenerPrecioTipoMueble(resultado.getString("C_tipoMueble"));
                int cantidad = resultado.getInt("Cantidad");
                double total = cantidad * precio;
                datos.add(new String[]{resultado.getString("C_tipoMueble"), "" + cantidad, "Q." + precio, "Q." + total,fechaInicial, fechaFinal});
            }
        } catch (SQLException e) {
        }
        return datos;
    }
    
    /**
     * Devuelve los muebles más vendidos de todo el tiempo
     * @param opcion 1 orden descendiente 2 Ascendente
     * @return 
     */
    public ArrayList<String[]> muebleMasVendido(int opcion){
        ArrayList<String[]> datos = new ArrayList<>();
        datos.add(new String[]{"TIPO DE MUEBLE", "CANTIDAD", "PRECIO", "TOTAL GENERADO"});
        String query = "SELECT COUNT(C_tipoMueble) AS Cantidad, C_tipoMueble FROM Compra WHERE EXISTS (SELECT no_factura FROM Factura) GROUP BY C_tipoMueble ORDER BY Cantidad DESC;";
        if (opcion == 2) {
            query = "SELECT COUNT(C_tipoMueble) AS Cantidad, C_tipoMueble FROM Compra WHERE EXISTS (SELECT no_factura FROM Factura) GROUP BY C_tipoMueble ORDER BY Cantidad ASC;";
        }
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            ResultSet resultado = prepared.executeQuery();
            while (resultado.next()) {
                double precio = obtenerPrecioTipoMueble(resultado.getString("C_tipoMueble"));
                int cantidad = resultado.getInt("Cantidad");
                double total = cantidad * precio;
                datos.add(new String[]{resultado.getString("C_tipoMueble"), "" + cantidad, "Q." + precio, "Q." + total});
            }
        } catch (SQLException e) {
        }
        return datos;
    }
    
    /**
     * Devuelve las facturas de los muebles más vendido en un intervalo de tiempo
     * @param fechaInicial
     * @param fechaFinal
     * @return 
     */
     public ArrayList<Factura> obtenerFacturasMueblesVendidos(String fechaInicial, String fechaFinal, String tipomueble){
        ArrayList<Factura> facturas = new ArrayList<>();
        String query = "SELECT Cno_factura FROM Compra WHERE Cfecha BETWEEN ? AND ? AND C_tipoMueble = ?;";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setString(1, fechaInicial);
            prepared.setString(2, fechaFinal);
            prepared.setString(3, tipomueble);
            ResultSet resultado = prepared.executeQuery();
            while (resultado.next()) {
                facturas.add(obtenerV.obtenerFacturaSegunNo(resultado.getInt("Cno_factura")));
            }
        } catch (SQLException e) {
        }
        return facturas;
    }
    
    public double obtenerPrecioTipoMueble(String tipo){
        double precio = 0;
        String query = "SELECT precio_venta FROM TipoMueble WHERE nombre_mueble = ?;";
        try {
            PreparedStatement prepared = Conexion.Conexion().prepareStatement(query);
            prepared.setString(1, tipo);
            ResultSet resultado = prepared.executeQuery();
            while (resultado.next()) {
               precio = resultado.getDouble("precio_venta");
            }
        } catch (SQLException e) {
        }
        return precio;
    }
}
