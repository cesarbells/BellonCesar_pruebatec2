package com.mycompany.gestionturnos.logica;

import com.mycompany.gestionturnos.persistencia.CiudadanoJpaController;
import com.mycompany.gestionturnos.persistencia.TurnoJpaController;
import com.mycompany.gestionturnos.persistencia.ControladoraPersistencia;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Controladora {

    private ControladoraPersistencia controlPersis;

    public Controladora(ControladoraPersistencia controlPersis) {
        this.controlPersis = controlPersis;
    }

    /* CIUDADANOS */
    public void crearCiudadano(Ciudadano ciudadano) {
        controlPersis.crearCiudadano(ciudadano);
    }

    public void eliminarCiudadano(Long id) {
        controlPersis.eliminarCiudadano(id);
    }

    public List<Ciudadano> traerCiudadanos() {
        return controlPersis.traerCiudadanos();
    }

    public void editarCiudadano(Ciudadano ciudadano) {
        controlPersis.editarCiudadano(ciudadano);
    }

    public Ciudadano buscarCiudadanoPorId(Long id) {
        return controlPersis.obtenerCiudadano(id);
    }

    public Ciudadano obtenerCiudadano(Long id) {
        return controlPersis.obtenerCiudadano(id);
    }

    /* TURNOS */
    public void crearTurno(Turno turno) {
        controlPersis.crearTurno(turno);
    }

    public void eliminarTurno(Long id) {
        controlPersis.eliminarTurno(id);
    }

    public List<Turno> traerTurnos() {
        return controlPersis.traerTurnos();
    }

    public void editarTurno(Turno turno) {
        controlPersis.editarTurno(turno);
    }

    public Turno buscarTurnoPorId(Long id) {
        return controlPersis.obtenerTurno(id);
    }

    public List<Turno> filtrarTurnos(Date fecha) {
        List<Turno> turnos = controlPersis.traerTurnos();

        // Convertir la fecha proporcionada por el usuario a solo fecha (sin hora)
        LocalDate localFecha = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        // Filtrar turnos, comparando solo las fechas (sin hora)
        return turnos.stream()
                .filter(turno -> {
                    if (turno.getFecha() == null) {
                        return false; // Si la fecha del turno es nula, no lo incluyas en el resultado
                    }
                    // Convertir la fecha del turno a LocalDate
                    LocalDate turnoFecha = turno.getFecha();

                    // Comparar solo la fecha (sin hora)
                    return turnoFecha.equals(localFecha);
                })
                .collect(Collectors.toList());
    }

    public List<Turno> filtrarTurnos(Date fecha, String estado) {
        List<Turno> turnos = controlPersis.traerTurnos();

        // Convertir la fecha proporcionada por el usuario a solo fecha (sin hora)
        LocalDate localFecha = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        // Filtrar turnos, comparando la fecha y el estado
        return turnos.stream()
                .filter(turno -> {
                    if (turno.getFecha() == null || turno.getEstadoTurno() == null) {
                        return false; // Si la fecha o el estado del turno son nulos, no lo incluyas en el resultado
                    }

                    // Convertir la fecha del turno a LocalDate
                    LocalDate turnoFecha = turno.getFecha();

                    // Comparar solo la fecha (sin hora) y el estado
                    return turnoFecha.equals(localFecha) && turno.getEstadoTurno().equals(estado);
                })
                .collect(Collectors.toList());
    }

    public List<Turno> filtrarTurnosPorEstado(String estado) {
        List<Turno> turnos = controlPersis.traerTurnos();
        return turnos.stream()
                .filter(turno -> estado == null || estado.isEmpty() || turno.getEstadoTurno().equalsIgnoreCase(estado))
                .collect(Collectors.toList());
    }
}
