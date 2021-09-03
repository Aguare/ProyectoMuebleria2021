package Servlets;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author marco
 */
@WebServlet(name = "EnviarTabla", urlPatterns = {"/EnviarTabla"})
public class EnviarTabla extends HttpServlet {

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
        String titulo = (String) request.getAttribute("titulo");
        String subtitulo = (String) request.getAttribute("subtitulo");
        ArrayList<String> titulos = (ArrayList<String>) request.getAttribute("titulos");
        ArrayList<String[]> contenido = (ArrayList<String[]>) request.getAttribute("titulos");
        request.setAttribute("titulo", titulo);
        request.setAttribute("subtitulo", subtitulo);
        request.setAttribute("titulos", titulos);
        request.setAttribute("contenido", contenido);
        request.getRequestDispatcher("Consultas/Tabla.jsp").forward(request, response);
    }
}
