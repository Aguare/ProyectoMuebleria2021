package AdmonServlets;

import EntidadesFabrica.Usuario;
import ModificarObj.AdmonCRUD;
import ObtenerObjetos.ObtenerUC;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author marco
 */
@WebServlet(name = "CambioUsuario", urlPatterns = {"/CambioUsuario"})
public class CambioUsuario extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        ObtenerUC obtenerU = new ObtenerUC();
        String accion = request.getParameter("accion");
        switch (accion) {
            case "1":
                request.getRequestDispatcher("Consultas/Admon/Usuario.jsp").forward(request, response);
                break;
            case "2":
                String usuario = request.getParameter("usuario");
                if (usuario != null && usuario.length() > 0) {
                    Usuario user = obtenerU.obtenerUsuarioSegunNombreConPass(usuario);
                    request.setAttribute("usuario", user);
                    request.getRequestDispatcher("Consultas/Admon/Usuario.jsp").forward(request, response);
                }else{
                    request.setAttribute("mensaje", "EL USUARIO NO EXISTE");
                    request.getRequestDispatcher("Mensajes/ErrorGeneral.jsp").forward(request, response);
                }
                break;
            default:
                request.setAttribute("mensaje", "LA OPCIÓN NO EXISTE");
                request.getRequestDispatcher("Mensajes/ErrorGeneral.jsp").forward(request, response);
                break;
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String opcion = request.getParameter("opcion");
        String nombreUsuario = request.getParameter("nombreUsuario");
        String acceso = request.getParameter("tieneAcceso");
        int accesoNuevo = 0;
        if (acceso.equalsIgnoreCase("TIENE ACCESO")){
            accesoNuevo = 1;
        }
        String pass1 = request.getParameter("password");
        String pass2 = request.getParameter("password2");
        String departamento = request.getParameter("departamento");
        switch(opcion){
            case "1":
                if (!pass1.equals(pass2)) {
                    enviarMensaje(request, response, "¡ERROR!", "LAS CONTRASEÑAS NO COINCIDEN", 2, "Mensajes/MensajeGeneral.jsp");
                }else{
                    AdmonCRUD admon = new AdmonCRUD();
                    if (admon.modificarUsuario(nombreUsuario.trim(), pass2, departamento, accesoNuevo, 1)) {
                        enviarMensaje(request, response, "¡EXITO!", "EL USUARIO SE REGISTRÓ CORRECTAMENTE", 1, "Mensajes/MensajeGeneral.jsp");
                    }else{
                        enviarMensaje(request, response, "¡ERROR!", "EL USUARIO NO SE REGISTRÓ", 2, "Mensajes/MensajeGeneral.jsp");
                    }
                }
                break;
            case "2":
                if (!pass1.equals(pass2)) {
                    enviarMensaje(request, response, "¡ERROR!", "LAS CONTRASEÑAS NO COINCIDEN", 2, "Mensajes/MensajeGeneral.jsp");
                }else{
                    AdmonCRUD admon = new AdmonCRUD();
                    if (admon.modificarUsuario(nombreUsuario, pass2, departamento, accesoNuevo, 2)) {
                        enviarMensaje(request, response, "¡EXITO!", "EL USUARIO ACTUALIZÓ CORRECTAMENTE", 1, "Mensajes/MensajeGeneral.jsp");
                    }else{
                        enviarMensaje(request, response, "¡ERROR!", "EL USUARIO NO SE ACTUALIZÓ", 2, "Mensajes/MensajeGeneral.jsp");
                    }
                }
                break;
            default:
                request.setAttribute("mensaje", "LA OPCIÓN NO EXISTE");
                request.getRequestDispatcher("Mensajes/ErrorGeneral.jsp").forward(request, response);
                break;
        }
    }
    
    private void enviarMensaje(HttpServletRequest request,
            HttpServletResponse response, String mensaje, String mensaje2,
            int color, String url) throws ServletException, IOException {
        request.setAttribute("mensaje", mensaje);
        request.setAttribute("mensaje2", mensaje2);
        request.setAttribute("color", color);
        request.getRequestDispatcher(url).forward(request, response);
    }
}
