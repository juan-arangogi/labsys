package edu.co.amigo.labsys.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import edu.co.amigo.labsys.domain.enumeration.EstadoOrden;

/**
 * A Orden.
 */
@Entity
@Table(name = "orden")
public class Orden implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total")
    private Double total;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoOrden estado;

    @OneToMany(mappedBy = "orden")
    private Set<DetalleOrden> detalleOrdens = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("ordens")
    private Paciente paciente;

    @ManyToOne
    @JsonIgnoreProperties("ordens")
    private EntidadSalud entidadSalud;

    @ManyToOne
    @JsonIgnoreProperties("ordens")
    private Medico medico;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotal() {
        return total;
    }

    public Orden total(Double total) {
        this.total = total;
        return this;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public EstadoOrden getEstado() {
        return estado;
    }

    public Orden estado(EstadoOrden estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(EstadoOrden estado) {
        this.estado = estado;
    }

    public Set<DetalleOrden> getDetalleOrdens() {
        return detalleOrdens;
    }

    public Orden detalleOrdens(Set<DetalleOrden> detalleOrdens) {
        this.detalleOrdens = detalleOrdens;
        return this;
    }

    public Orden addDetalleOrden(DetalleOrden detalleOrden) {
        this.detalleOrdens.add(detalleOrden);
        detalleOrden.setOrden(this);
        return this;
    }

    public Orden removeDetalleOrden(DetalleOrden detalleOrden) {
        this.detalleOrdens.remove(detalleOrden);
        detalleOrden.setOrden(null);
        return this;
    }

    public void setDetalleOrdens(Set<DetalleOrden> detalleOrdens) {
        this.detalleOrdens = detalleOrdens;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public Orden paciente(Paciente paciente) {
        this.paciente = paciente;
        return this;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public EntidadSalud getEntidadSalud() {
        return entidadSalud;
    }

    public Orden entidadSalud(EntidadSalud entidadSalud) {
        this.entidadSalud = entidadSalud;
        return this;
    }

    public void setEntidadSalud(EntidadSalud entidadSalud) {
        this.entidadSalud = entidadSalud;
    }

    public Medico getMedico() {
        return medico;
    }

    public Orden medico(Medico medico) {
        this.medico = medico;
        return this;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Orden orden = (Orden) o;
        if (orden.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orden.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Orden{" +
            "id=" + getId() +
            ", total=" + getTotal() +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
