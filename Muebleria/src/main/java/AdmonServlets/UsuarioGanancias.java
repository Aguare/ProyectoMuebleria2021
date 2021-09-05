package AdmonServlets;

import ObtenerObjetos.ObtenerAd;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author aguare
 */
@WebServlet(name = "UsuarioGanancias", urlPatterns = {"/UsuarioGanancias"})
public class UsuarioGanancias extends HttpServlet {

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
        ObtenerAd obtenerA = new ObtenerAd();
        String fechaInicial = request.getParameter("fechaInicial");
        String fechaFinal = request.getParameter("fechaFinal");
        ArrayList<String[]> datos = new ArrayList<>();
        if (fechaInicial == null || fechaInicial.length() == 0 || fechaFinal == null || fechaFinal.length() == 0) {
            datos = obtenerA.obtenerUsuarioConMasGanancia();
        } else {
            datos = obtenerA.obtenerUsuarioConMasGananciaFechas(fechaInicial, fechaFinal);
        }
        request.setAttribute("fechaInicial", fechaInicial);
        request.setAttribute("fechaFinal", fechaFinal);
        request.setAttribute("datos", datos);
        request.getRequestDispatcher("Consultas/Admon/Reportes/UsuarioGanancias.jsp").forward(request, response);
    }

}
