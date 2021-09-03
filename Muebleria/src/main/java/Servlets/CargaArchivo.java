package Servlets;

import Archivo.ControladorInsertar;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author marco
 */
@WebServlet(name = "CargaArchivo", urlPatterns = {"/CargaArchivo-Servlet"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 100 // 100MB
)
public class CargaArchivo extends HttpServlet {

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
        request.setCharacterEncoding("UTF-8");
        Part filePart = request.getPart("archivoCarga");
        InputStream contenido = filePart.getInputStream();
        BufferedReader buffer = new BufferedReader(new InputStreamReader(contenido, StandardCharsets.UTF_8));
        ControladorInsertar leer = new ControladorInsertar();
        String n = filePart.getSubmittedFileName();
        if (n.equalsIgnoreCase("")) {
            request.setAttribute("mensaje", "No se pudo leer el archivo, o no se selecciono ninguno");
            request.setAttribute("cargaArchivo", true);
            request.getRequestDispatcher("Mensajes/ErrorGeneral.jsp").forward(request, response);
        } else {
            leer.ejecutar(buffer);
            request.setAttribute("noReconocido", leer.getErrores());
            request.setAttribute("correcto", leer.getCorrecto());
            request.setAttribute("mostrar", true);
            request.getRequestDispatcher("Inicio/CargaArchivo.jsp").forward(request, response);
        }
    }

}
