package com.mycompany.gestionturnos.logica;

import com.mycompany.gestionturnos.logica.Ciudadano;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Turno implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numeroTurno;

    private LocalDate fecha;
    private String descripcion;
    private String estadoTurno;

    @ManyToOne
    @JoinColumn(name = "id_ciudadano")
    private Ciudadano ciudadano;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroTurno() {
        return numeroTurno;
    }

    public void setNumeroTurno(String numeroTurno) {
        this.numeroTurno = numeroTurno;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Turno(Long id, String numeroTurno, LocalDate fecha, String descripcion, String estadoTurno, Ciudadano ciudadano) {
        this.id = id;
        this.numeroTurno = numeroTurno;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.estadoTurno = estadoTurno;
        this.ciudadano = ciudadano;
    }

    public Turno() {
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstadoTurno() {
        return estadoTurno;
    }

    public void setEstadoTurno(String estadoTurno) {
        this.estadoTurno = estadoTurno;
    }

    public Ciudadano getCiudadano() {
        return ciudadano;
    }

    public void setCiudadano(Ciudadano ciudadano) {
        this.ciudadano = ciudadano;
    }

    @Override
    public String toString() {
        return "Turno{"
                + "id=" + id
                + ", numeroTurno='" + numeroTurno + '\''
                + ", fecha=" + fecha
                + ", descripcion='" + descripcion + '\''
                + ", estadoTurno='" + estadoTurno + '\''
                + ", ciudadano=" + (ciudadano != null ? ciudadano.getNombre() + " " + ciudadano.getApellidos() : "Sin asignar")
                + '}';
    }

}
