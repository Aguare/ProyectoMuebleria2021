package VentasServlets;

import EntidadesFabrica.Usuario;
import EntidadesVenta.CarritoCompra;
import EntidadesVenta.Cliente;
import ObtenerObjetos.ObtenerUC;
import ObtenerObjetos.ObtenerV;
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
@WebServlet(name = "Venta", urlPatterns = {"/Venta"})
public class Venta extends HttpServlet {


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
        if (request.getSession().getAttribute("carritoCompra") == null) {
            ObtenerV obtenerV = new ObtenerV();
            Usuario usuario = (Usuario) request.getSession().getAttribute("Usuario");
            CarritoCompra carrito = new CarritoCompra(obtenerV.obtenerMueblesNoVendidos(), usuario);
            request.getSession().setAttribute("carritoCompra", carrito);
            request.getRequestDispatcher("Consultas/Ventas/RegistrarVenta.jsp").forward(request, response);
        } else if (request.getParameter("cancelarVenta") != null) {
            request.getSession().removeAttribute("carritoCompra");
            request.setAttribute("mensaje", "¡ÉXITO!");
            request.setAttribute("mensaje2", "La venta se ha cancelado exitósamente");
            request.setAttribute("color", 1);
            request.getRequestDispatcher("Mensajes/MensajeGeneral.jsp").forward(request, response);
        } else if (request.getParameter("NIT") != null) {
            ObtenerUC obtenerUC = new ObtenerUC();
            Cliente cliente = obtenerUC.obtenerClientesSegunNIT(request.getParameter("NIT"));
            if (cliente != null) {
                CarritoCompra carrito = (CarritoCompra) request.getSession().getAttribute("carritoCompra");
                carrito.setCliente(cliente);
                request.getSession().setAttribute("carritoCompra", carrito);
                request.getRequestDispatcher("Consultas/Ventas/RegistrarVenta.jsp").forward(request, response);
            } else {
                request.setAttribute("mensaje", "¡ERROR!");
                request.setAttribute("mensaje2", "EL CLIENTE NO ESTÁ REGISTRADO");
                request.setAttribute("color", 1);
                request.getRequestDispatcher("Mensajes/MensajeGeneral.jsp").forward(request, response);
            }
        } else if (request.getParameter("agregar") != null) {
            CarritoCompra carrito = (CarritoCompra) request.getSession().getAttribute("carritoCompra");
            if (carrito.agregarCarrito(request.getParameter("agregar"))) {
                request.getSession().setAttribute("carritoCompra", carrito);
                request.getRequestDispatcher("Consultas/Ventas/RegistrarVenta.jsp").forward(request, response);
            } else {
                request.setAttribute("mensaje", "¡ERROR!");
                request.setAttribute("mensaje2", "EL MUEBLE NO EXISTE");
                request.setAttribute("color", 2);
                request.getRequestDispatcher("Mensajes/MensajeGeneral.jsp").forward(request, response);
            }
        } else if (request.getParameter("quitar") != null) {
            CarritoCompra carrito = (CarritoCompra) request.getSession().getAttribute("carritoCompra");
            if (carrito.quitarCarrito(request.getParameter("quitar"))) {
                request.getSession().setAttribute("carritoCompra", carrito);
                request.getRequestDispatcher("Consultas/Ventas/RegistrarVenta.jsp").forward(request, response);
            } else {
                request.setAttribute("mensaje", "¡ERROR!");
                request.setAttribute("mensaje2", "EL MUEBLE NO EXISTE");
                request.setAttribute("color", 2);
                request.getRequestDispatcher("Mensajes/MensajeGeneral.jsp").forward(request, response);
            }
        } else {
            request.getRequestDispatcher("Consultas/Ventas/RegistrarVenta.jsp").forward(request, response);
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

    }
}
