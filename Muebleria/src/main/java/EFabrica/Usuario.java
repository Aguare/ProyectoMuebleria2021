package EFabrica;

/**
 *
 * @author aguare
 */
public class Usuario {

    private String nombre_usuario;
    private int idDepartamento;
    private String nombreDepartamento;

    public Usuario(String nombre_usuario, int idDepartamento, String nombreDepartamento) {
        this.nombre_usuario = nombre_usuario;
        this.idDepartamento = idDepartamento;
        this.nombreDepartamento = nombreDepartamento;
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
