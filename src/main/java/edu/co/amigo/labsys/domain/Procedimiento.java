package edu.co.amigo.labsys.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Procedimiento.
 */
@Entity
@Table(name = "procedimiento")
public class Procedimiento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "precio_unitario")
    private Double precioUnitario;

    @OneToMany(mappedBy = "procedimiento")
    private Set<DetalleOrden> detalleOrdens = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("procedimientos")
    private TipoMuestra tipoMuestra;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Procedimiento descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public Procedimiento precioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
        return this;
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Set<DetalleOrden> getDetalleOrdens() {
        return detalleOrdens;
    }

    public Procedimiento detalleOrdens(Set<DetalleOrden> detalleOrdens) {
        this.detalleOrdens = detalleOrdens;
        return this;
    }

    public Procedimiento addDetalleOrden(DetalleOrden detalleOrden) {
        this.detalleOrdens.add(detalleOrden);
        detalleOrden.setProcedimiento(this);
        return this;
    }

    public Procedimiento removeDetalleOrden(DetalleOrden detalleOrden) {
        this.detalleOrdens.remove(detalleOrden);
        detalleOrden.setProcedimiento(null);
        return this;
    }

    public void setDetalleOrdens(Set<DetalleOrden> detalleOrdens) {
        this.detalleOrdens = detalleOrdens;
    }

    public TipoMuestra getTipoMuestra() {
        return tipoMuestra;
    }

    public Procedimiento tipoMuestra(TipoMuestra tipoMuestra) {
        this.tipoMuestra = tipoMuestra;
        return this;
    }

    public void setTipoMuestra(TipoMuestra tipoMuestra) {
        this.tipoMuestra = tipoMuestra;
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
        Procedimiento procedimiento = (Procedimiento) o;
        if (procedimiento.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), procedimiento.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Procedimiento{" +
            "id=" + getId() +
            ", descripcion='" + getDescripcion() + "'" +
            ", precioUnitario=" + getPrecioUnitario() +
            "}";
    }
}
