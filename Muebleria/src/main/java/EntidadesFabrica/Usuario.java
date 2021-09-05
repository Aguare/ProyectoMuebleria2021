package EntidadesFabrica;

import SQL.Encriptar;

/**
 *
 * @author aguare
 */
public class Usuario {

    private String nombre_usuario;
    private int idDepartamento;
    private String nombreDepartamento;
    private String password = "";
    private boolean acceso;
    
    public Usuario(String nombre_usuario, int idDepartamento, boolean acceso, String password) {
        this.nombre_usuario = nombre_usuario;
        this.idDepartamento = idDepartamento;
        this.acceso = acceso;
        this.password = password;
        definir();
    }
    
    private void definir() {
        switch (idDepartamento) {
            case 1:
                nombreDepartamento = "FÁBRICA";
                break;
            case 2:
                nombreDepartamento = "PUNTO DE VENTA";
                break;
            case 3:
                nombreDepartamento = "ADMINISTRACIÓN";
                break;
            default:
                break;
        }
    }

    public String getPassword() {
        Encriptar encriptar = new Encriptar();
        String p = encriptar.desencriptarPass(password, "ipc");
        return p;
    }

    public void setPassword(String password) {
        Encriptar encriptar = new Encriptar();
        String p = encriptar.encriptarPass(password);
        this.password = password;
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
