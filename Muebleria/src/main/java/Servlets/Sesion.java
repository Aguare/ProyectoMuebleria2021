package Servlets;

import EntidadesFabrica.Pieza;
import EntidadesFabrica.Usuario;
import SQL.ObtenerObj;
import java.io.IOException;
import java.io.PrintWriter;
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

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Sesion</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Sesion at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

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
        ObtenerObj obtener = new ObtenerObj();
        Usuario user = obtener.obtenerUsuarioSegunNombre(usuario);
        if (user != null && obtener.verificarPassword(usuario, password)) {
            if (!user.isAcceso()) {
                request.setAttribute("mensaje", "USTED YA NO TIENE ACCESO AL SISTEMA");
                request.getRequestDispatcher("Mensajes/ErrorGeneral.jsp").forward(request, response);
            } else {
                request.getSession().setAttribute("Usuario", user);
                Pieza pieza = obtener.obtenerPiezaSegunID(1);
                if (pieza == null) {
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

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
