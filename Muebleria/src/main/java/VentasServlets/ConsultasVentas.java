package VentasServlets;

import EntidadesVenta.Factura;
import ObtenerObjetos.ObtenerV;
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
@WebServlet(name = "ConsultasVentas", urlPatterns = {"/ConsultasVentas"})
public class ConsultasVentas extends HttpServlet {

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
        ObtenerV obtenerV = new ObtenerV();
        String fechaInicial = request.getParameter("fechaInicial");
        String fechaFinal = request.getParameter("fechaFinal");
        ArrayList<Factura> facturas;
        String cliente = request.getParameter("clienteSeleccionado");
        String[] partes = cliente.split(",");
        if (fechaInicial == null || fechaInicial.length() == 0) {
            facturas = obtenerV.obtenerFacturasCliente(partes[0].trim());
        } else {
            facturas = obtenerV.obtenerFacturasClienteSegunFecha(fechaInicial, fechaFinal, partes[0].trim());
        }
        request.setAttribute("fechaInicial", fechaInicial);
        request.setAttribute("fechaFinal", fechaFinal);
        request.setAttribute("facturas", facturas);
        request.setAttribute("cliente", cliente);
        request.getRequestDispatcher("Consultas/Ventas/ComprasCliente.jsp").forward(request, response);
    }

}
