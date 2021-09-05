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
@WebServlet(name = "Ganancias", urlPatterns = {"/Ganancias"})
public class Ganancias extends HttpServlet {

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
        double perdida = 0;
        double costoProduccion = 0;
        if (fechaInicial == null || fechaInicial.length() == 0) {
            facturas = obtenerA.obtenerFacturas();
            perdida = obtenerA.obtenerPerdidaTotal();
            costoProduccion = obtenerA.obtenerCostoProduccionTotal();
        } else {
            facturas = obtenerA.obtenerFacturasSegunFecha(fechaInicial, fechaFinal);
            perdida = obtenerA.obtenerPerdidaIntervaloTiempo(fechaInicial, fechaFinal);
            costoProduccion = obtenerA.obtenerCostoProduccionIntervaloTiempo(fechaInicial, fechaFinal);
        }
        request.setAttribute("fechaInicial", fechaInicial);
        request.setAttribute("fechaFinal", fechaFinal);
        request.setAttribute("facturas", facturas);
        request.setAttribute("perdida", perdida);
        request.setAttribute("costoProduccion", costoProduccion);
        request.getRequestDispatcher("Consultas/Admon/Reportes/GananciasFecha.jsp").forward(request, response);
    }

}
