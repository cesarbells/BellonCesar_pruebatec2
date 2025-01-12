package com.mycompany.gestionturnos.servlets;

import com.mycompany.gestionturnos.logica.Controladora;
import com.mycompany.gestionturnos.logica.Turno;
import com.mycompany.gestionturnos.persistencia.ControladoraPersistencia;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "SvCrearFiltro", urlPatterns = {"/SvCrearFiltro"})
public class SvCrearFiltro extends HttpServlet {

    // Crear una instancia del EntityManager para pasar a ControladoraPersistencia
    private EntityManager em = Persistence.createEntityManagerFactory("gestionturnosPU").createEntityManager();
    private ControladoraPersistencia controlPersis = new ControladoraPersistencia(em);
    private Controladora controlLogica = new Controladora(controlPersis);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Obtener el parámetro de fecha del formulario
            String fechaParam = request.getParameter("fecha");

            List<Turno> turnosFiltrados;

            if (fechaParam != null && !fechaParam.isEmpty()) {
                // Convertir la fecha a LocalDate
                LocalDate fechaFiltro = LocalDate.parse(fechaParam, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                // Filtrar los turnos por fecha usando Streams
                turnosFiltrados = controlLogica.traerTurnos().stream()
                        .filter(turno -> turno.getFecha()
                        .equals(fechaFiltro))
                        .collect(Collectors.toList());
            } else {
                // Si no se especifica fecha, mostrar todos los turnos
                turnosFiltrados = controlLogica.traerTurnos();
            }

            // Pasar los turnos al JSP
            request.setAttribute("turnos", turnosFiltrados);
            request.getRequestDispatcher("crearFiltro.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Ocurrió un error al filtrar los turnos.");
            request.getRequestDispatcher("crearFiltro.jsp").forward(request, response);
        }
    }
}
