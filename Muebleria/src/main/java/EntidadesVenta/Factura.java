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

    public Factura(int noFactura, LocalDate fecha, double total, Cliente cliente) {
        this.noFactura = noFactura;
        this.fecha = fecha;
        this.total = total;
        this.cliente = cliente;
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
