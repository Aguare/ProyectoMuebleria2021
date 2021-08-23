package EntidadesFabrica;

import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author aguare
 */
public class Ensamble {

    private int idEnsamble;
    private Date fecha;
    private Usuario usuario;
    private String tipoMueble;
    private ArrayList<Pieza> piezas;

    public Ensamble(int idEnsamble, Date fecha, Usuario usuario, String tipoMueble, ArrayList<Pieza> piezas) {
        this.idEnsamble = idEnsamble;
        this.fecha = fecha;
        this.usuario = usuario;
        this.tipoMueble = tipoMueble;
        this.piezas = piezas;
    }

    public int getIdEnsamble() {
        return idEnsamble;
    }

    public void setIdEnsamble(int idEnsamble) {
        this.idEnsamble = idEnsamble;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getTipoMueble() {
        return tipoMueble;
    }

    public void setTipoMueble(String tipoMueble) {
        this.tipoMueble = tipoMueble;
    }

    public ArrayList<Pieza> getPiezas() {
        return piezas;
    }

    public void setPiezas(ArrayList<Pieza> piezas) {
        this.piezas = piezas;
    }

}
