package FabricaServlets;

import EntidadesFabrica.Ensamble;
import EntidadesVenta.Devolucion;
import ModificarObj.FabricaCRUD;
import ObtenerObjetos.ObtenerF;
import ObtenerObjetos.ObtenerV;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author aguare
 */
@WebServlet(name = "ReintegrarPiezas", urlPatterns = {"/ReintegrarPiezas"})
public class ReintegrarPiezas extends HttpServlet {

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
        ObtenerF obtener = new ObtenerF();
        ObtenerV obtenerV = new ObtenerV();
        String idEnsamble = request.getParameter("idMueble");
        String idDevolucion = request.getParameter("devolucion");
        if (idEnsamble == null || idDevolucion == null) {
            request.setAttribute("mensaje", "EL MUEBLE NO EXISTE");
            request.getRequestDispatcher("Mensajes/ErrorGeneral.jsp").forward(request, response);
        } else {
            Ensamble ensamble = obtener.obtenerEnsambleSegunID(idEnsamble);
            Devolucion dev = obtenerV.obtenerDevolucionSegunID(idDevolucion);
            if (ensamble != null && dev != null) {
                request.setAttribute("ensamble", ensamble);
                request.setAttribute("devolucion", dev);
                request.getRequestDispatcher("Consultas/Fabrica/Devolucion.jsp").forward(request, response);
            } else {
                request.setAttribute("mensaje", "EL MUELBE NO EXISTE");
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
        request.setCharacterEncoding("UTF-8");
        ObtenerV obtener = new ObtenerV();
        FabricaCRUD registrar = new FabricaCRUD();
        String idDevolucion = request.getParameter("idDevolucion");
        int cantidad = Integer.parseInt(request.getParameter("cantidadPiezas"));
        double precioCosto = Double.parseDouble(request.getParameter("precioCosto"));
        ArrayList<String> piezas = new ArrayList<>();
        Devolucion dev = obtener.obtenerDevolucionSegunID(idDevolucion);
        for (int i = 0; i < cantidad; i++) {
            piezas.add(request.getParameter("pieza" + (i + 1)));
        }
        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < piezas.size(); i++) {
                if (piezas.get(i) == null || piezas.get(i).equals("")) {
                    piezas.remove(i);
                    i = 0;
                }
            }
        }
        System.out.println("");
        if (registrar.reintegrarPiezas(piezas, dev, precioCosto)) {
            enviarMensaje(request, response, "¡ÉXITO!", "LAS PIEZAS SE REINTEGRARON CORRECTAMENTE", 1, "Mensajes/MensajeGeneral.jsp");
        } else {
            enviarMensaje(request, response, "¡EROR!", "LAS PIEZAS NO REINTEGRARON CORRECTAMENTE", 2, "Mensajes/MensajeGeneral.jsp");
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
