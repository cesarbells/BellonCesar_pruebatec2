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

@WebServlet(name = "SvEliminarTurno", urlPatterns = {"/SvEliminarTurno"})
public class SvEliminarTurno extends HttpServlet {

    // Crear una instancia del EntityManager para pasar a ControladoraPersistencia
    private EntityManager em = Persistence.createEntityManagerFactory("gestionturnosPU").createEntityManager();
    private ControladoraPersistencia controlPersis = new ControladoraPersistencia(em);
    private Controladora controlLogica = new Controladora(controlPersis);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Captura el ID del turno a eliminar
            Long id = Long.parseLong(request.getParameter("id"));

            // Llamar al método para eliminar el turno
            eliminarTurno(id, response);
        } catch (NumberFormatException e) {
            response.sendRedirect("crearTurno.jsp?error=ID no válido");
        } catch (Exception e) {
            response.sendRedirect("crearTurno.jsp?error=Error al procesar la solicitud");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Método POST no permitido para este servlet
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Método POST no permitido para esta URL.");
    }

    // Método para eliminar un turno
    private void eliminarTurno(Long id, HttpServletResponse response) throws IOException {
        try {
            controlLogica.eliminarTurno(id);
            response.sendRedirect("crearTurno.jsp?mensaje=Turno eliminado correctamente");
        } catch (Exception e) {
            response.sendRedirect("crearTurno.jsp?error=Error al eliminar turno");
        }
    }
}
