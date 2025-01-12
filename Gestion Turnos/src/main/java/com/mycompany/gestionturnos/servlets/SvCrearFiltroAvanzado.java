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
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "SvCrearFiltroAvanzado", urlPatterns = {"/SvCrearFiltroAvanzado"})
public class SvCrearFiltroAvanzado extends HttpServlet {

    private EntityManager em = Persistence.createEntityManagerFactory("gestionturnosPU").createEntityManager();
    private ControladoraPersistencia controlPersis = new ControladoraPersistencia(em);
    private Controladora controlLogica = new Controladora(controlPersis);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Obtener los parámetros de fecha y estado del formulario
            String fechaParam = request.getParameter("fecha");
            String estado = request.getParameter("estado");

            List<Turno> turnosFiltrados;

            // Si se proporciona una fecha, filtrar por fecha
            if (fechaParam != null && !fechaParam.isEmpty()) {
                Date fecha = Date.from(LocalDate.parse(fechaParam, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                        .atStartOfDay(ZoneId.systemDefault()).toInstant());

                // Filtrar los turnos por fecha
                if (estado != null && !estado.isEmpty()) {
                    // Si también se proporciona estado, filtrar por ambos
                    turnosFiltrados = controlLogica.filtrarTurnos(fecha, estado);
                } else {
                    // Si solo se proporciona fecha, filtrar solo por fecha
                    turnosFiltrados = controlLogica.filtrarTurnos(fecha);
                }
            } else {
                // Si no se proporciona fecha, filtrar solo por estado
                if (estado != null && !estado.isEmpty()) {
                    turnosFiltrados = controlLogica.filtrarTurnosPorEstado(estado);
                } else {
                    // Si no se proporciona ningún filtro, mostrar todos los turnos
                    turnosFiltrados = controlLogica.traerTurnos();
                }
            }

            // Pasar los turnos filtrados al JSP
            request.setAttribute("turnos", turnosFiltrados);
            request.getRequestDispatcher("crearFiltroAvanzado.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Ocurrió un error al filtrar los turnos.");
            request.getRequestDispatcher("crearFiltroAvanzado.jsp").forward(request, response);
        }
    }
}
