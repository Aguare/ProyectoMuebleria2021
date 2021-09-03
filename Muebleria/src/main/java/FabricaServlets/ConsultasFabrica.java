package FabricaServlets;

import EntidadesFabrica.Ensamble;
import ObtenerObjetos.ObtenerF;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "ConsultasFabrica", urlPatterns = {"/ConsultasFabrica"})
public class ConsultasFabrica extends HttpServlet {

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
            out.println("<title>Servlet ConsultasFabrica</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ConsultasFabrica at " + request.getContextPath() + "</h1>");
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
        try {
            ObtenerF obtener = new ObtenerF();
            int opcion = Integer.parseInt(request.getParameter("orden"));
            switch (opcion) {
                case 1:
                    request.setAttribute("piezas", obtener.obtenerPiezas(1));
                    request.setAttribute("orden", 1);
                    request.getRequestDispatcher("Consultas/Fabrica/Piezas.jsp").forward(request, response);
                    break;
                case 2:
                    request.setAttribute("piezas", obtener.obtenerPiezas(1));
                    request.setAttribute("orden", 2);
                    request.getRequestDispatcher("Consultas/Fabrica/Piezas.jsp").forward(request, response);
                    break;
                case 3:
                    doPost(request, response);
                    break;
                default:
                    request.setAttribute("mensaje", "LA OPCIÓN NO EXISTE");
                    request.getRequestDispatcher("Mensajes/ErrorGeneral.jsp").forward(request, response);
                    break;
            }
        } catch (Exception e) {
            request.setAttribute("mensaje", "LA OPCIÓN NO EXISTE");
            request.getRequestDispatcher("Mensajes/ErrorGeneral.jsp").forward(request, response);
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
        ObtenerF obtener = new ObtenerF();
        String fechaInicial = request.getParameter("fechaInicial");
        String fechaFinal = request.getParameter("fechaFinal");
        if (fechaInicial == null && fechaFinal == null || fechaInicial.equals("") && fechaFinal.equals("")) {
            ArrayList<Ensamble> ensambles = obtener.obtenerEnsambles();
            request.setAttribute("ensambles", ensambles);
            request.getRequestDispatcher("Consultas/Fabrica/MueblesEnsamblados.jsp").forward(request, response);
        }else{
            ArrayList<Ensamble> ensambles = obtener.obtenerEnsamblesPorFecha(fechaInicial, fechaFinal);
            request.setAttribute("ensambles", ensambles);
            request.setAttribute("fechaInicial", fechaInicial);
            request.setAttribute("fechaFinal", fechaFinal);
            request.getRequestDispatcher("Consultas/Fabrica/MueblesEnsamblados.jsp").forward(request, response);   
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
