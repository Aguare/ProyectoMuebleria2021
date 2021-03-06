package FabricaServlets;

import EntidadesFabrica.EnsamblarLogica;
import EntidadesFabrica.Ensamble;
import ModificarObj.FabricaCRUD;
import ObtenerObjetos.ObtenerF;
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
@WebServlet(name = "CambioEnsamble", urlPatterns = {"/CambioEnsamble"})
public class CambioEnsamble extends HttpServlet {

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
        request.setCharacterEncoding("UTF-8");
        try {
            int opcion = Integer.parseInt(request.getParameter("opcion"));
            String fecha = request.getParameter("fecha");
            String usuario = request.getParameter("usuarioResponsable");
            String tipoMueble = request.getParameter("tipoMueble");
            switch (opcion) {
                case 1:
                    EnsamblarLogica ensamblar = new EnsamblarLogica();
                    if (ensamblar.ensamblarMueble(tipoMueble, usuario, fecha, "") != -1) {
                        request.setAttribute("mensaje", "????XITO!");
                        request.setAttribute("mensaje2", "El mueble se ensambl?? correctamente");
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
                        request.setAttribute("mensaje", "????XITO!");
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
                    request.setAttribute("mensaje", "La opci??n no existe");
                    request.getRequestDispatcher("Mensajes/ErrorGeneral.jsp").forward(request, response);
                    break;
            }
        } catch (Exception e) {
            request.setAttribute("mensaje", "La opci??n no existe");
            request.getRequestDispatcher("Mensajes/ErrorGeneral.jsp").forward(request, response);
        }

    }
}
