package com.mycompany.gestionturnos.servlets;

import com.mycompany.gestionturnos.logica.Ciudadano;
import com.mycompany.gestionturnos.logica.Controladora;
import com.mycompany.gestionturnos.persistencia.ControladoraPersistencia;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@WebServlet(name = "SvCrearCiudadano", urlPatterns = {"/SvCrearCiudadano"})
public class SvCrearCiudadano extends HttpServlet {

    // Crear una instancia del EntityManager para pasar a ControladoraPersistencia
    private EntityManager em = Persistence.createEntityManagerFactory("gestionturnosPU").createEntityManager();
    private ControladoraPersistencia controlPersis = new ControladoraPersistencia(em);
    private Controladora controlLogica = new Controladora(controlPersis);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Obtener los datos del formulario con Optional
            String nombre = Optional.ofNullable(request.getParameter("nombre_ciudadano")).orElse("").trim();
            String apellidos = Optional.ofNullable(request.getParameter("apellidos_ciudadano")).orElse("").trim();
            String dni = Optional.ofNullable(request.getParameter("dni_ciudadano")).orElse("").trim();
            String telefono = Optional.ofNullable(request.getParameter("telefono_ciudadano")).orElse("").trim();

            // Validar que los campos no están vacíos
            if (nombre.isEmpty() || apellidos.isEmpty() || dni.isEmpty() || telefono.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Todos los campos son obligatorios.");
                return;
            }

            // Crear un nuevo objeto Ciudadano
            Ciudadano ciudadano = new Ciudadano();
            ciudadano.setNombre(nombre);
            ciudadano.setApellidos(apellidos);
            ciudadano.setDni(dni);
            ciudadano.setTelefono(telefono);

            // Llamar a la lógica para crear el ciudadano
            controlLogica.crearCiudadano(ciudadano);

            // Redirigir al formulario principal
            response.sendRedirect("crearCiudadano.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al guardar el ciudadano.");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Obtener la lista de ciudadanos desde la base de datos
            List<Ciudadano> listCiudadanos = controlLogica.traerCiudadanos();

            // Usar Streams para procesar la lista de ciudadanos antes de enviarla a la vista
            List<Ciudadano> ciudadanosFiltrados = listCiudadanos.stream()
                    .filter(ciudadano -> !ciudadano.getNombre().isEmpty()) // Filtrar por nombre no vacío
                    .collect(Collectors.toList());

            // Establecer los resultados en la solicitud
            request.setAttribute("ciudadanos", ciudadanosFiltrados);

            // Redirigir al JSP correspondiente
            request.getRequestDispatcher("crearCiudadano.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al obtener los ciudadanos.");
        }
    }
}
