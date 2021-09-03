package Servlets;

import EntidadesFabrica.Pieza;
import EntidadesFabrica.TipoPiezas;
import EntidadesFabrica.Usuario;
import ObtenerObjetos.ObtenerF;
import ObtenerObjetos.ObtenerUC;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author marco
 */
@WebServlet(name = "Sesion", urlPatterns = {"/Sesion"})
public class Sesion extends HttpServlet {

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
        request.getSession().removeAttribute("Usuario");
        request.getSession().invalidate();
        response.sendRedirect(request.getContextPath());
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
        String usuario = request.getParameter("usuario");
        String password = request.getParameter("password");
        ObtenerUC obtenerUC = new ObtenerUC();
        ObtenerF obtenerF = new ObtenerF();
        Usuario user = obtenerUC.obtenerUsuarioSegunNombre(usuario);
        if (user != null && obtenerUC.verificarPassword(usuario, password)) {
            if (!user.isAcceso()) {
                request.setAttribute("mensaje", "USTED YA NO TIENE ACCESO AL SISTEMA");
                request.getRequestDispatcher("Mensajes/ErrorGeneral.jsp").forward(request, response);
            } else {
                request.getSession().setAttribute("Usuario", user);
                Pieza pieza = obtenerF.obtenerPiezaSegunID(1);
                ArrayList<TipoPiezas> tipo = obtenerF.obtenerTipoPiezas(1);
                if (pieza == null && tipo.isEmpty()) {
                    response.sendRedirect("Inicio/CargaArchivo.jsp");
                } else {
                    switch (user.getIdDepartamento()) {
                        case 1:
                            request.getRequestDispatcher("Menus/Fabrica.jsp").forward(request, response);
                            break;
                        case 2:
                            request.getRequestDispatcher("Menus/Venta.jsp").forward(request, response);
                            break;
                        case 3:
                            request.getRequestDispatcher("Menus/Financiero.jsp").forward(request, response);
                            break;
                        default:
                            break;
                    }
                }
            }
        } else {
            request.getRequestDispatcher("Mensajes/Credenciales.jsp").forward(request, response);
        }
    }
}
