package com.mycompany.gestionturnos.persistencia;

import com.mycompany.gestionturnos.logica.Ciudadano;
import com.mycompany.gestionturnos.logica.Turno;
import com.mycompany.gestionturnos.persistencia.CiudadanoJpaController;
import com.mycompany.gestionturnos.persistencia.TurnoJpaController;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ControladoraPersistencia {

    private EntityManager em;
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestionturnosPU");

    private CiudadanoJpaController ciudadanoJPA;
    private TurnoJpaController turnoJPA;

    public ControladoraPersistencia(EntityManager em) {
        this.em = em;
        this.ciudadanoJPA = new CiudadanoJpaController(emf);
        this.turnoJPA = new TurnoJpaController(emf);
    }

    /* CIUDADANOS */
    public void crearCiudadano(Ciudadano ciudadano) {
        ciudadanoJPA.create(ciudadano);
    }

    public void eliminarCiudadano(Long id) {
        try {
            ciudadanoJPA.destroy(id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public List<Ciudadano> traerCiudadanos() {
        return ciudadanoJPA.findCiudadanoEntities();
    }

    public void editarCiudadano(Ciudadano ciudadano) {
        try {
            em.getTransaction().begin();
            em.merge(ciudadano);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
    }

    public Ciudadano obtenerCiudadano(Long id) {
        return em.find(Ciudadano.class, id);
    }

    /* TURNOS */
    public void crearTurno(Turno turno) {
        turnoJPA.create(turno);
    }

    public void eliminarTurno(Long id) {
        try {
            turnoJPA.destroy(id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public List<Turno> traerTurnos() {
        return turnoJPA.findTurnoEntities();
    }

    public void editarTurno(Turno turno) {
        try {
            em.getTransaction().begin();
            em.merge(turno);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }

    public Turno obtenerTurno(Long id) {
        return em.find(Turno.class, id);
    }
}
