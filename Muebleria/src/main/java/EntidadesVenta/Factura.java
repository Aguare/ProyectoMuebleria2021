package EntidadesVenta;

import java.time.LocalDate;

/**
 *
 * @author aguare
 */
public class Factura {

    private int noFactura;
    private LocalDate fecha;
    private double total;
    private Cliente cliente;
    private String usuario;

    public Factura(int noFactura, LocalDate fecha, double total, Cliente cliente, String usuario) {
        this.noFactura = noFactura;
        this.fecha = fecha;
        this.total = total;
        this.cliente = cliente;
        this.usuario = usuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getNoFactura() {
        return noFactura;
    }

    public void setNoFactura(int noFactura) {
        this.noFactura = noFactura;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
