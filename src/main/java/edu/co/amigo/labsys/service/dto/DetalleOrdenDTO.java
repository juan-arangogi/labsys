package edu.co.amigo.labsys.service.dto;

import edu.co.amigo.labsys.domain.Orden;
import edu.co.amigo.labsys.domain.Paciente;
import edu.co.amigo.labsys.domain.Procedimiento;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the DetalleOrden entity.
 */
public class DetalleOrdenDTO implements Serializable {

    private Long id;

    private Integer descuento;

    private Integer cantidad;

    private Double total;

    private Long ordenId;

    private Long procedimientoId;

    private Orden orden;

    private Procedimiento procedimiento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDescuento() {
        return descuento;
    }

    public void setDescuento(Integer descuento) {
        this.descuento = descuento;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Long getOrdenId() {
        return ordenId;
    }

    public void setOrdenId(Long ordenId) {
        this.ordenId = ordenId;
    }

    public Long getProcedimientoId() {
        return procedimientoId;
    }

    public void setProcedimientoId(Long procedimientoId) {
        this.procedimientoId = procedimientoId;
    }

    public Orden getOrden() {
        return orden;
    }

    public void setOrden(Orden orden) {
        this.orden = orden;
    }

    public Procedimiento getProcedimiento() {
        return procedimiento;
    }

    public void setProcedimiento(Procedimiento procedimiento) {
        this.procedimiento = procedimiento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DetalleOrdenDTO detalleOrdenDTO = (DetalleOrdenDTO) o;
        if (detalleOrdenDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), detalleOrdenDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DetalleOrdenDTO{" +
            "id=" + getId() +
            ", descuento=" + getDescuento() +
            ", cantidad=" + getCantidad() +
            ", total=" + getTotal() +
            ", orden=" + getOrdenId() +
            ", procedimiento=" + getProcedimientoId() +
            "}";
    }
}
