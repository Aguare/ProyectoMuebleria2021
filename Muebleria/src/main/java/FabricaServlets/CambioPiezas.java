package FabricaServlets;

import EntidadesFabrica.Pieza;
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
@WebServlet(name = "CambioPiezas", urlPatterns = {"/CambioPiezas"})
public class CambioPiezas extends HttpServlet {

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
            out.println("<title>Servlet CambioPiezas</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CambioPiezas at " + request.getContextPath() + "</h1>");
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
        int id = Integer.parseInt(request.getParameter("pie"));
        int opcion = Integer.parseInt(request.getParameter("accion"));
        ObtenerObj obtener = new ObtenerObj();
        Pieza pieza = obtener.obtenerPiezaSegunID(id);
        if (pieza == null) {
            request.setAttribute("mensaje", "El ID de la pieza no existe");
            request.getRequestDispatcher("Mensajes/ErrorGeneral.jsp").forward(request, response);
        } else {
            switch (opcion) {
                case 1:
                    request.setAttribute("pieza", pieza);
                    request.getRequestDispatcher("Consultas/Fabrica/EditarPieza.jsp").forward(request, response);
                    break;
                case 2:
                    break;
                default:
                    request.setAttribute("mensaje", "LA OPCIÓN NO EXISTE");
                    request.getRequestDispatcher("Mensajes/ErrorGeneral.jsp").forward(request, response);
                    break;
            }
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
        processRequest(request, response);
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
