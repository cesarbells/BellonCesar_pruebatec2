package com.mycompany.gestionturnos.servlets;

import com.mycompany.gestionturnos.logica.Controladora;
import com.mycompany.gestionturnos.persistencia.ControladoraPersistencia;
import java.io.IOException;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "SvEliminarCiudadano", urlPatterns = {"/SvEliminarCiudadano"})
public class SvEliminarCiudadano extends HttpServlet {

    // Crear una instancia del EntityManager para pasar a ControladoraPersistencia
    private EntityManager em = Persistence.createEntityManagerFactory("gestionturnosPU").createEntityManager();
    private ControladoraPersistencia controlPersis = new ControladoraPersistencia(em);
    private Controladora controlLogica = new Controladora(controlPersis);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Captura el ID del ciudadano a eliminar
            Long id = Long.parseLong(request.getParameter("id"));

            // Llamar al método para eliminar el ciudadano
            eliminarCiudadano(id, response);
        } catch (NumberFormatException e) {
            response.sendRedirect("CrearCiudadano.jsp?error=ID no válido");
        } catch (Exception e) {
            response.sendRedirect("CrearCiudadano.jsp?error=Error al procesar la solicitud");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Método POST no permitido para este servlet
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Método POST no permitido para esta URL.");
    }

    // Método para eliminar un ciudadano
    private void eliminarCiudadano(Long id, HttpServletResponse response) throws IOException {
        try {
            controlLogica.eliminarCiudadano(id);
            response.sendRedirect("crearCiudadano.jsp?mensaje=Ciudadano eliminado correctamente");
        } catch (Exception e) {
            response.sendRedirect("crearCiudadano.jsp?error=Error al eliminar ciudadano");
        }
    }
}
