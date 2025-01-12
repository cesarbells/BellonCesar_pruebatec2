package com.mycompany.gestionturnos.servlets;

import com.mycompany.gestionturnos.logica.Controladora;
import com.mycompany.gestionturnos.logica.Turno;
import com.mycompany.gestionturnos.persistencia.ControladoraPersistencia;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "SvEditarTurno", urlPatterns = {"/SvEditarTurno"})
public class SvEditarTurno extends HttpServlet {

    private EntityManager em = Persistence.createEntityManagerFactory("gestionturnosPU").createEntityManager();
    private ControladoraPersistencia controlPersis = new ControladoraPersistencia(em);
    private Controladora controlLogica = new Controladora(controlPersis);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idTurnoStr = request.getParameter("id");
        if (idTurnoStr != null) {
            try {
                long idTurno = Long.parseLong(idTurnoStr);
                Turno turno = controlLogica.buscarTurnoPorId(idTurno);

                if (turno != null) {
                    request.setAttribute("turno", turno);
                    request.getRequestDispatcher("editarTurno.jsp").forward(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Turno no encontrado");
                }
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de turno inválido");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No se proporcionó el ID del turno");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idTurnoStr = request.getParameter("id");
        String fechaTurno = request.getParameter("fecha_turno");
        String numeroTurno = request.getParameter("numero_turno");
        String descripcionTurno = request.getParameter("descripcion_turno");
        String estadoTurno = request.getParameter("estado_turno");
        String idCiudadanoStr = request.getParameter("id_ciudadano");

        if (idTurnoStr == null || idTurnoStr.isEmpty() || fechaTurno == null || fechaTurno.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Datos faltantes en el formulario");
            return;
        }

        try {
            long idTurno = Long.parseLong(idTurnoStr);
            int idCiudadano = Integer.parseInt(idCiudadanoStr);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate fecha = LocalDate.parse(fechaTurno, formatter);

            Turno turno = controlLogica.buscarTurnoPorId(idTurno);
            if (turno != null) {
                turno.setFecha(fecha);
                turno.setNumeroTurno(numeroTurno);
                turno.setDescripcion(descripcionTurno);
                turno.setEstadoTurno(estadoTurno);

                controlLogica.editarTurno(turno);

                response.sendRedirect("SvCrearTurno");
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Turno no encontrado para actualizar");
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Datos inválidos en el formulario");
        } catch (DateTimeParseException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Formato de fecha inválido. Use yyyy-MM-dd");
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al procesar la solicitud");
        }
    }
}
