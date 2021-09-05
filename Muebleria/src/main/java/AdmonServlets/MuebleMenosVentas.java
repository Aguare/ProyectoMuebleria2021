package AdmonServlets;

import EntidadesVenta.Factura;
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
@WebServlet(name = "MuebleMenosVentas", urlPatterns = {"/MuebleMenosVentas"})
public class MuebleMenosVentas extends HttpServlet {

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
        ArrayList<Factura> facturas = new ArrayList<>();
        ArrayList<String[]> datos = new ArrayList<>();
        if (fechaInicial == null || fechaInicial.length() == 0 || fechaFinal == null || fechaFinal.length() == 0) {
            datos = obtenerA.muebleMasVendido(2);
        } else {
            datos = obtenerA.muebleMasVendidoSegunFecha(fechaInicial, fechaFinal, 2);
            if (datos.get(1)[0] != null) {
                facturas = obtenerA.obtenerFacturasMueblesVendidos(fechaInicial, fechaFinal, datos.get(1)[0]);
            }
        }
        request.setAttribute("facturas", facturas);
        request.setAttribute("fechaInicial", fechaInicial);
        request.setAttribute("fechaFinal", fechaFinal);
        request.setAttribute("datos", datos);
        request.getRequestDispatcher("Consultas/Admon/Reportes/MuebleMenosVentas.jsp").forward(request, response);
    }

}
