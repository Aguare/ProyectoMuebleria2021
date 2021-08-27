package EntidadesFabrica;

/**
 *
 * @author aguare
 */
public class Usuario {

    private String nombre_usuario;
    private int idDepartamento;
    private String nombreDepartamento;
    private boolean acceso;

    public Usuario(String nombre_usuario, int idDepartamento, boolean acceso) {
        this.nombre_usuario = nombre_usuario;
        this.idDepartamento = idDepartamento;
        this.acceso = acceso;
        definir();
    }

    private void definir() {
        switch (idDepartamento) {
            case 1:
                nombreDepartamento = "Fábrica";
                break;
            case 2:
                nombreDepartamento = "Punto de Venta";
                break;
            case 3:
                nombreDepartamento = "Financiero";
                break;
            default:
                break;
        }
    }

    public boolean isAcceso() {
        return acceso;
    }

    public void setAcceso(boolean acceso) {
        this.acceso = acceso;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public int getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(int idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public String getNombreDepartamento() {
        return nombreDepartamento;
    }

    public void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
    }

}
