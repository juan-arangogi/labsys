package edu.co.amigo.labsys.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DetalleOrden.
 */
@Entity
@Table(name = "detalle_orden")
public class DetalleOrden implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descuento")
    private Integer descuento;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "total")
    private Double total;

    @ManyToOne
    @JsonIgnoreProperties("detalleOrdens")
    private Orden orden;

    @ManyToOne
    @JsonIgnoreProperties("detalleOrdens")
    private Procedimiento procedimiento;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDescuento() {
        return descuento;
    }

    public DetalleOrden descuento(Integer descuento) {
        this.descuento = descuento;
        return this;
    }

    public void setDescuento(Integer descuento) {
        this.descuento = descuento;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public DetalleOrden cantidad(Integer cantidad) {
        this.cantidad = cantidad;
        return this;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getTotal() {
        return total;
    }

    public DetalleOrden total(Double total) {
        this.total = total;
        return this;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Procedimiento getProcedimiento() {
        return procedimiento;
    }

    public DetalleOrden procedimiento(Procedimiento procedimiento) {
        this.procedimiento = procedimiento;
        return this;
    }

    public void setProcedimiento(Procedimiento procedimiento) {
        this.procedimiento = procedimiento;
    }

    public Orden getOrden() {
        return orden;
    }

    public DetalleOrden orden(Orden orden) {
        this.orden = orden;
        return this;
    }

    public void setOrden(Orden orden) {
        this.orden = orden;
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
        DetalleOrden detalleOrden = (DetalleOrden) o;
        if (detalleOrden.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), detalleOrden.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DetalleOrden{" +
            "id=" + getId() +
            ", descuento=" + getDescuento() +
            ", cantidad=" + getCantidad() +
            ", total=" + getTotal() +
            "}";
    }
}
