package com.mycompany.gestionturnos.servlets;

import com.mycompany.gestionturnos.logica.Controladora;
import com.mycompany.gestionturnos.persistencia.ControladoraPersistencia;
import com.mycompany.gestionturnos.logica.Ciudadano;
import com.mycompany.gestionturnos.logica.Controladora;
import com.mycompany.gestionturnos.persistencia.ControladoraPersistencia;
import com.mycompany.gestionturnos.logica.Ciudadano;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import javax.servlet.http.HttpServletResponse;

@WebServlet("/SvEditarCiudadano")
public class SvEditarCiudadano extends HttpServlet {

    private EntityManager em = Persistence.createEntityManagerFactory("gestionturnosPU").createEntityManager();
    private ControladoraPersistencia controlPersis = new ControladoraPersistencia(em);
    private Controladora controlLogica = new Controladora(controlPersis);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Obtener el ID del ciudadano desde los parÃ¡metros de la URL
            Long idCiudadano = Long.valueOf(request.getParameter("id"));

            // Buscar el ciudadano mediante la controladora
            Ciudadano ciudadano = controlLogica.buscarCiudadanoPorId(idCiudadano);

            if (ciudadano != null) {
                // Pasar el ciudadano al JSP
                request.setAttribute("ciudadano", ciudadano);

                // Redirigir a la pÃ¡gina de ediciÃ³n
                request.getRequestDispatcher("editarCiudadano.jsp").forward(request, response);
            } else {
                // Manejar caso de ciudadano no encontrado
                request.setAttribute("error", "Ciudadano no encontrado.");
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "ID invÃ¡lido.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al buscar el ciudadano.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Recoger los datos del formulario
            Long id = Long.valueOf(request.getParameter("id"));
            String nombre = request.getParameter("nombre_ciudadano");
            String apellidos = request.getParameter("apellidos_ciudadano");
            String dni = request.getParameter("dni_ciudadano");
            String telefono = request.getParameter("telefono_ciudadano");

            // Validar los datos (puedes extender esta lÃ³gica)
            if (nombre == null || nombre.isEmpty() || apellidos == null || apellidos.isEmpty()) {
                throw new IllegalArgumentException("Nombre y apellidos son obligatorios.");
            }

            // Buscar el ciudadano que se quiere editar
            Ciudadano ciudadano = controlLogica.buscarCiudadanoPorId(id);

            if (ciudadano != null) {
                // Actualizar los datos
                ciudadano.setNombre(nombre);
                ciudadano.setApellidos(apellidos);
                ciudadano.setDni(dni);
                ciudadano.setTelefono(telefono);

                // Guardar los cambios
                controlLogica.editarCiudadano(ciudadano);

                // Redirigir con mensaje de Ã©xito
                request.setAttribute("mensaje", "Ciudadano actualizado exitosamente.");
                request.getRequestDispatcher("crearCiudadano.jsp").forward(request, response);
            } else {
                // Manejar caso de ciudadano no encontrado
                request.setAttribute("error", "No se encuentra el ciudadano con ID: " + id);
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "ID invÃ¡lido.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("editarCiudadano.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al actualizar el ciudadano.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}
