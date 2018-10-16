package edu.co.amigo.labsys.service.dto;

import edu.co.amigo.labsys.domain.TipoMuestra;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Procedimiento entity.
 */
public class ProcedimientoDTO implements Serializable {

    private Long id;

    private String descripcion;

    private Double precioUnitario;

    private Long tipoMuestraId;

    private TipoMuestra tipoMuestra;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Long getTipoMuestraId() {
        return tipoMuestraId;
    }

    public void setTipoMuestraId(Long tipoMuestraId) {
        this.tipoMuestraId = tipoMuestraId;
    }

    public TipoMuestra getTipoMuestra() {
        return tipoMuestra;
    }

    public void setTipoMuestra(TipoMuestra tipoMuestra) {
        this.tipoMuestra = tipoMuestra;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProcedimientoDTO procedimientoDTO = (ProcedimientoDTO) o;
        if (procedimientoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), procedimientoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProcedimientoDTO{" +
            "id=" + getId() +
            ", descripcion='" + getDescripcion() + "'" +
            ", precioUnitario=" + getPrecioUnitario() +
            ", tipoMuestra=" + getTipoMuestraId() +
            "}";
    }
}
