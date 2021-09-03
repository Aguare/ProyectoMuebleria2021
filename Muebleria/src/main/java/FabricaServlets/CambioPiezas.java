package FabricaServlets;

import Archivo.InsertarArchivo;
import EntidadesFabrica.Pieza;
import ModificarObj.FabricaCRUD;
import ObtenerObjetos.ObtenerF;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author marco
 */
@WebServlet(name = "CambioPiezas", urlPatterns = {"/CambioPiezas"})
public class CambioPiezas extends HttpServlet {

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
            out.println("<title>Servlet CambioPiezas</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CambioPiezas at " + request.getContextPath() + "</h1>");
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
        int id = 0;
        int opcion = 0;
        try{
           id = Integer.parseInt(request.getParameter("pie"));
           opcion = Integer.parseInt(request.getParameter("accion"));
        }catch(Exception e){
            request.setAttribute("mensaje", "LA OPCIÓN NO EXISTE");
            request.getRequestDispatcher("Mensajes/ErrorGeneral.jsp").forward(request, response);
        }
        ObtenerF obtener = new ObtenerF();
        Pieza pieza = obtener.obtenerPiezaSegunID(id);
        if (pieza == null) {
            request.setAttribute("mensaje", "El ID de la pieza no existe");
            request.getRequestDispatcher("Mensajes/ErrorGeneral.jsp").forward(request, response);
        } else {
            switch (opcion) {
                case 1:
                    request.setAttribute("pieza", pieza);
                    request.removeAttribute("crear");
                    request.getRequestDispatcher("Consultas/Fabrica/EditarPieza.jsp").forward(request, response);
                    break;
                case 2:
                    FabricaCRUD modificar = new FabricaCRUD();
                    if (modificar.modificarPieza(pieza, FabricaCRUD.ELIMINAR)) {
                        request.setAttribute("mensaje", "¡ÉXITO!");
                        request.setAttribute("mensaje2", "La pieza se eliminó correctamente");
                        request.setAttribute("color", 1);
                        request.getRequestDispatcher("Mensajes/MensajeGeneral.jsp").forward(request, response);
                    } else {
                        request.setAttribute("mensaje", "ERROR");
                        request.setAttribute("mensaje2", "La pieza no se pudo eliminar");
                        request.setAttribute("color", 2);
                        request.getRequestDispatcher("Mensajes/MensajeGeneral.jsp").forward(request, response);
                    }
                    break;
                default:
                    request.setAttribute("mensaje", "LA OPCIÓN NO EXISTE");
                    request.getRequestDispatcher("Mensajes/ErrorGeneral.jsp").forward(request, response);
                    break;
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
        int opcion = Integer.parseInt(request.getParameter("opcion"));
        ObtenerF obtener = new ObtenerF();
        String idPieza = "";
        double precio = 0;
        String tipoPieza = "";
        switch (opcion) {
            case 1:
                try {
                idPieza = request.getParameter("idPieza");
                precio = Double.parseDouble(request.getParameter("precio"));
                tipoPieza = request.getParameter("tipoPieza");
            } catch (Exception e) {
                request.setAttribute("mensaje", "LOS VALORES NO SON VÁLIDOS");
                request.getRequestDispatcher("Mensajes/ErrorGeneral.jsp").forward(request, response);
            }
            if (precio < 0) {
                request.setAttribute("mensaje", "El precio no puede ser menor a 0");
                request.getRequestDispatcher("Mensajes/ErrorGeneral.jsp").forward(request, response);
            } else {
                int n = Integer.parseInt(idPieza);
                Pieza pieza = obtener.obtenerPiezaSegunID(n);
                if (pieza == null) {
                    request.setAttribute("mensaje", "La pieza que intenta editar no existe");
                    request.getRequestDispatcher("Mensajes/ErrorGeneral.jsp").forward(request, response);
                } else {
                    pieza.setPrecio(precio);
                    pieza.setNuevoTipo(tipoPieza);
                    FabricaCRUD editar = new FabricaCRUD();
                    if (editar.modificarPieza(pieza, FabricaCRUD.ACTUALIZAR)) {
                        request.setAttribute("mensaje", "¡ÉXITO!");
                        request.setAttribute("mensaje2", "La pieza se actualizó correctamente");
                        request.setAttribute("color", 1);
                        request.getRequestDispatcher("Mensajes/MensajeGeneral.jsp").forward(request, response);
                    } else {
                        request.setAttribute("mensaje", "ERROR");
                        request.setAttribute("mensaje2", "La pieza no se pudo actualizar");
                        request.setAttribute("color", 2);
                        request.getRequestDispatcher("Mensajes/MensajeGeneral.jsp").forward(request, response);
                    }
                }
            }
            break;
            case 2:
                FabricaCRUD modificar = new FabricaCRUD();
                double precioC = Double.parseDouble(request.getParameter("precioC"));
                String tipo = request.getParameter("tipoPiezaC");
                String nombreNuevo = request.getParameter("nombreNuevo");
                if (nombreNuevo != null & nombreNuevo.length()>5) {
                    if (tipo.equalsIgnoreCase("Nuevo Tipo")) {
                    InsertarArchivo insertar = new InsertarArchivo();
                    insertar.insertarTipo(nombreNuevo,""+ precioC, "");
                    insertar.insertarPieza(nombreNuevo,""+ precioC, "");
                    request.setAttribute("mensaje", "ESTADO: ");
                    request.setAttribute("mensaje2", insertar.getMensaje());
                    request.setAttribute("color", 3);
                    request.getRequestDispatcher("Mensajes/MensajeGeneral.jsp").forward(request, response);
                }else{
                    Pieza pieza = new Pieza(0, precioC, false, tipo);
                    if (modificar.modificarPieza(pieza, FabricaCRUD.INSERTAR)) {
                        pieza = modificar.obtenerUltimaPieza();
                        request.setAttribute("mensaje", "¡ÉXITO!");
                        request.setAttribute("mensaje2", "La pieza se creó correctamente ID = " + pieza.getIdPieza());
                        request.setAttribute("color", 1);
                        request.getRequestDispatcher("Mensajes/MensajeGeneral.jsp").forward(request, response);
                    } else {
                        request.setAttribute("mensaje", "ERROR");
                        request.setAttribute("mensaje2", "La pieza no se pudo crear");
                        request.setAttribute("color", 2);
                        request.getRequestDispatcher("Mensajes/MensajeGeneral.jsp").forward(request, response);
                    }
                }
                }else{
                    request.setAttribute("mensaje", "ERROR");
                        request.setAttribute("mensaje2", "El nombre debe tener almenos 5 caracteres");
                        request.setAttribute("color", 2);
                        request.getRequestDispatcher("Mensajes/MensajeGeneral.jsp").forward(request, response);
                }
                break;
            default:
                request.setAttribute("mensaje", "LA OPCIÓN NO EXISTE");
                request.getRequestDispatcher("Mensajes/ErrorGeneral.jsp").forward(request, response);
                break;

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
