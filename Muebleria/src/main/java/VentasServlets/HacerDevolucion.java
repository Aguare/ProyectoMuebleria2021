/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VentasServlets;

import EntidadesVenta.Compra;
import EntidadesVenta.Mueble;
import ModificarObj.VentasCRUD;
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
@WebServlet(name = "HacerDevolucion", urlPatterns = {"/HacerDevolucion"})
public class HacerDevolucion extends HttpServlet {

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
        try {
            ObtenerV obtenerV = new ObtenerV();
            VentasCRUD ventas = new VentasCRUD();
            int noFactura = Integer.parseInt(request.getParameter("noFactura"));
            int idMueble = Integer.parseInt(request.getParameter("idMueble"));
            Compra compra = obtenerV.obtenerCompraSegunFactura(noFactura);
            if (compra != null) {
                Mueble mueble = compra.obtenerMuebleDevuelto(idMueble);
                if (mueble != null) {
                    if (ventas.registrarDevolucion(compra, mueble)) {
                        enviarMensaje(request, response, "¡EXITO!", "LA DEVOLUCIÓN SE REGISTRÓ CORRECTAMENTE", 1, "Mensajes/MensajeGeneral.jsp");
                    }else{
                        enviarMensaje(request, response, "¡EROR!", "LA DEVOLUCIÓN NO REGISTRÓ CORRECTAMENTE", 2, "Mensajes/MensajeGeneral.jsp");
                    }
                } else {
                    enviarMensaje(request, response, "¡ERROR!", "EL MUEBLE NO EXISTE", 2, "Mensajes/MensajeGeneral.jsp");
                }
            } else {

            }
        } catch (NumberFormatException e) {
            enviarMensaje(request, response, "¡ERROR!", "DATOS INCORRECTOS", 2, "Mensajes/MensajeGeneral.jsp");
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

    private void enviarMensaje(HttpServletRequest request,
            HttpServletResponse response, String mensaje, String mensaje2,
            int color, String url) throws ServletException, IOException {
        request.setAttribute("mensaje", mensaje);
        request.setAttribute("mensaje2", mensaje2);
        request.setAttribute("color", color);
        request.getRequestDispatcher(url).forward(request, response);
    }

}
