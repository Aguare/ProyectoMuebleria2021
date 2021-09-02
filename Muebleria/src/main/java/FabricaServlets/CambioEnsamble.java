package FabricaServlets;

import EntidadesFabrica.EnsamblarLogica;
import EntidadesFabrica.Ensamble;
import ModificarObj.FabricaCRUD;
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
 * @author aguare
 */
@WebServlet(name = "CambioEnsamble", urlPatterns = {"/CambioEnsamble"})
public class CambioEnsamble extends HttpServlet {

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
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CambioEnsambles</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CambioEnsambles at " + request.getContextPath() + "</h1>");
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
        ObtenerObj obtener = new ObtenerObj();
        String idEnsamble = request.getParameter("ensamble");
        if (idEnsamble == null) {
            request.getRequestDispatcher("Consultas/Fabrica/Ensamblar.jsp").forward(request, response);
        } else {
            Ensamble ensamble = obtener.obtenerEnsambleSegunID(idEnsamble);
            if (ensamble != null) {
                request.setAttribute("ensamble", ensamble);
                request.getRequestDispatcher("Consultas/Fabrica/Ensamblar.jsp").forward(request, response);
            } else {
                request.setAttribute("mensaje", "El Ensamble no existe");
                request.getRequestDispatcher("Mensajes/ErrorGeneral.jsp").forward(request, response);
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
        try {
            int opcion = Integer.parseInt(request.getParameter("opcion"));
            String fecha = request.getParameter("fecha");
            String usuario = request.getParameter("usuarioResponsable");
            String tipoMueble = request.getParameter("tipoMueble");
            switch (opcion) {
                case 1:
                    EnsamblarLogica ensamblar = new EnsamblarLogica();
                    if (ensamblar.ensamblarMueble(tipoMueble, usuario, fecha, "") != -1) {
                        request.setAttribute("mensaje", "¡ÉXITO!");
                        request.setAttribute("mensaje2", "El mueble se ensambló correctamente");
                        request.setAttribute("color", 1);
                        request.getRequestDispatcher("Mensajes/MensajeGeneral.jsp").forward(request, response);
                    }else{
                        request.setAttribute("mensaje", "ERROR");
                        request.setAttribute("mensaje2", ensamblar.getError());
                        request.setAttribute("color", 2);
                        request.getRequestDispatcher("Mensajes/MensajeGeneral.jsp").forward(request, response);
                    }
                    break;
                case 2:
                    int idEnsamble = Integer.parseInt(request.getParameter("idEnsamble"));
                    FabricaCRUD modificar = new FabricaCRUD();
                    if (modificar.actualizarEnsamble(fecha, usuario, idEnsamble)) {
                        request.setAttribute("mensaje", "¡ÉXITO!");
                        request.setAttribute("mensaje2", "El ensamble se modifico correctamente");
                        request.setAttribute("color", 1);
                        request.getRequestDispatcher("Mensajes/MensajeGeneral.jsp").forward(request, response);
                    }else{
                        request.setAttribute("mensaje", "ERROR");
                        request.setAttribute("mensaje2", "No se pudo actualizar los datos");
                        request.setAttribute("color", 2);
                        request.getRequestDispatcher("Mensajes/MensajeGeneral.jsp").forward(request, response);
                    }
                    break;
                default:
                    request.setAttribute("mensaje", "La opción no existe");
                    request.getRequestDispatcher("Mensajes/ErrorGeneral.jsp").forward(request, response);
                    break;
            }
        } catch (Exception e) {
            request.setAttribute("mensaje", "La opción no existe");
            request.getRequestDispatcher("Mensajes/ErrorGeneral.jsp").forward(request, response);
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
