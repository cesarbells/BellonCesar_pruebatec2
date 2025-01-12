package com.mycompany.gestionturnos.servlets;

import com.mycompany.gestionturnos.logica.Ciudadano;
import com.mycompany.gestionturnos.logica.Controladora;
import com.mycompany.gestionturnos.logica.Turno;
import com.mycompany.gestionturnos.persistencia.ControladoraPersistencia;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "SvCrearTurno", urlPatterns = {"/SvCrearTurno"})
public class SvCrearTurno extends HttpServlet {

    // Crear una instancia del EntityManager para pasar a ControladoraPersistencia
    private EntityManager em = Persistence.createEntityManagerFactory("gestionturnosPU").createEntityManager();
    private ControladoraPersistencia controlPersis = new ControladoraPersistencia(em);
    private Controladora controlLogica = new Controladora(controlPersis);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Recuperar todos los turnos
        List<Turno> turnos = controlLogica.traerTurnos();

        // Procesar la lista de turnos usando Streams antes de pasarlos a la vista
        List<Turno> turnosFiltrados = turnos.stream()
                .filter(turno -> Optional.ofNullable(turno.getEstadoTurno()).orElse("").equals("En espera")) // Filtrar por estado "En espera"
                .collect(Collectors.toList());

        // Verificar si los turnos existen y asignarlos a la solicitud
        if (turnosFiltrados != null && !turnosFiltrados.isEmpty()) {
            request.setAttribute("turnos", turnosFiltrados);
        } else {
            System.out.println("No se encontraron turnos en espera.");
        }

        // Redirigir a la página de turno.jsp
        request.getRequestDispatcher("crearTurno.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Crear un nuevo turno con los datos del formulario
        Turno nuevoTurno = new Turno();

        // Obtener el ID del ciudadano de manera segura usando Optional
        String idCiudadanoStr = Optional.ofNullable(request.getParameter("id_ciudadano")).orElse("");
        if (idCiudadanoStr.isEmpty()) {
            response.sendRedirect("error.jsp");  // Redirigir si el ID del ciudadano no es válido
            return;
        }
        Long idCiudadano = Long.parseLong(idCiudadanoStr);
        Ciudadano ciudadano = controlLogica.buscarCiudadanoPorId(idCiudadano);

        if (ciudadano == null) {
            // Si el ciudadano no existe, manejar el error
            response.sendRedirect("error.jsp");
            return;
        }

        // Asignar el ciudadano al turno
        nuevoTurno.setCiudadano(ciudadano);

        // Obtener el número de turno
        nuevoTurno.setNumeroTurno(request.getParameter("numero_turno"));

        // Obtener la fecha y convertirla a LocalDate
        String fechaStr = request.getParameter("fecha_turno");
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate fecha = LocalDate.parse(fechaStr, formatter);
            nuevoTurno.setFecha(fecha);  // Establecer la fecha convertida
        } catch (DateTimeParseException e) {
            System.out.println("Error al parsear la fecha: " + e.getMessage());
        }

        // Obtener la descripción y estado del turno
        nuevoTurno.setDescripcion(request.getParameter("descripcion_turno"));
        nuevoTurno.setEstadoTurno(request.getParameter("estado_turno"));

        // Persistir el turno
        controlLogica.crearTurno(nuevoTurno);

        // Redirigir a la misma página para mostrar el nuevo turno
        response.sendRedirect("SvCrearTurno");
    }
}
