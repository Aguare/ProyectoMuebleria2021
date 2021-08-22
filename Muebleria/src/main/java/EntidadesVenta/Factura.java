package EntidadesVenta;

import java.sql.Date;

/**
 *
 * @author aguare
 */
public class Factura {

    private int noFactura;
    private Date fecha;
    private double total;
    private Cliente cliente;

    public Factura(int noFactura, Date fecha, double total, Cliente cliente) {
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
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
