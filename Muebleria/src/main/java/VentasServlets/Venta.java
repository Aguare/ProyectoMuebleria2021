package VentasServlets;

import EntidadesFabrica.Usuario;
import EntidadesVenta.CarritoCompra;
import EntidadesVenta.Cliente;
import EntidadesVenta.Compra;
import ModificarObj.VentasCRUD;
import ObtenerObjetos.ObtenerUC;
import ObtenerObjetos.ObtenerV;
import java.io.IOException;
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
        request.setCharacterEncoding("UTF-8");
        if (request.getSession().getAttribute("carritoCompra") == null) {
            ObtenerV obtenerV = new ObtenerV();
            Usuario usuario = (Usuario) request.getSession().getAttribute("Usuario");
            CarritoCompra carrito = new CarritoCompra(obtenerV.obtenerMueblesNoVendidos(), usuario);
            request.getSession().setAttribute("carritoCompra", carrito);
            request.getRequestDispatcher("Consultas/Ventas/RegistrarVenta.jsp").forward(request, response);
        } else if (request.getParameter("cancelarVenta") != null) {
            request.getSession().removeAttribute("carritoCompra");
            enviarMensaje(request, response, "¡ÉXITO!", "La venta se ha cancelado exitósamente", 1, "Mensajes/MensajeGeneral.jsp");
        } else if (request.getParameter("NIT") != null) {
            ObtenerUC obtenerUC = new ObtenerUC();
            Cliente cliente = obtenerUC.obtenerClientesSegunNIT(request.getParameter("NIT"));
            if (cliente != null) {
                CarritoCompra carrito = (CarritoCompra) request.getSession().getAttribute("carritoCompra");
                carrito.setCliente(cliente);
                request.getSession().setAttribute("carritoCompra", carrito);
                request.getRequestDispatcher("Consultas/Ventas/Cliente.jsp").forward(request, response);
            } else {
                enviarMensaje(request, response, "¡ERROR!", "EL CLIENTE NO ESTÁ REGISTRADO", 2, "Mensajes/MensajeGeneral.jsp");
            }
        } else if (request.getParameter("agregar") != null) {
            CarritoCompra carrito = (CarritoCompra) request.getSession().getAttribute("carritoCompra");
            if (carrito.agregarCarrito(request.getParameter("agregar"))) {
                request.getSession().setAttribute("carritoCompra", carrito);
                request.getRequestDispatcher("Consultas/Ventas/RegistrarVenta.jsp").forward(request, response);
            } else {
                enviarMensaje(request, response, "¡ERROR!", "EL MUEBLE NO EXISTE", 2, "Mensajes/MensajeGeneral.jsp");
            }
        } else if (request.getParameter("quitar") != null) {
            CarritoCompra carrito = (CarritoCompra) request.getSession().getAttribute("carritoCompra");
            if (carrito.quitarCarrito(request.getParameter("quitar"))) {
                request.getSession().setAttribute("carritoCompra", carrito);
                request.getRequestDispatcher("Consultas/Ventas/RegistrarVenta.jsp").forward(request, response);
            } else {
                enviarMensaje(request, response, "¡ERROR!", "EL MUEBLE NO EXISTE", 2, "Mensajes/MensajeGeneral.jsp");
            }
        }else{
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
        request.setCharacterEncoding("UTF-8");
        if (request.getSession().getAttribute("carritoCompra") == null) {
            enviarMensaje(request, response, "¡ERROR!", "LA COMPRA NO SE PUDO REGISTRAR", 2, "Mensajes/MensajeGeneral.jsp");
        } else {
            String NIT = request.getParameter("NIT");
            String nombre = request.getParameter("nombre");
            String direcion = request.getParameter("direccion");
            Cliente cliente = new Cliente(NIT, nombre, direcion);
            CarritoCompra carrito = (CarritoCompra) request.getSession().getAttribute("carritoCompra");
            carrito.setCliente(cliente);
            VentasCRUD venta = new VentasCRUD();
            Compra compra = venta.registrarCompra(carrito);
            if (compra != null) {
                request.getSession().setAttribute("carritoCompra", null);
                request.setAttribute("noFactura", compra.getFactura().getNoFactura());
                request.getRequestDispatcher("Consultas/Ventas/Factura.jsp").forward(request, response);
            } else {
                enviarMensaje(request, response, "¡ERROR!", "LA COMPRA NO SE PUDO REGISTRAR", 2, "Mensajes/MensajeGeneral.jsp");
            }
        }

    }

    private void enviarMensaje(HttpServletRequest request,
            HttpServletResponse response, String mensaje, String mensaje2,
            int color, String url) throws ServletException, IOException {
        request.setAttribute("mensaje", mensaje);
        request.setAttribute("mensaje2", mensaje2);
        request.setAttribute("color", color);
        request.getRequestDispatcher(url).forward(request, response);
    }
}
