package edu.co.amigo.labsys.service.dto;

import edu.co.amigo.labsys.domain.Recipiente;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the TipoMuestra entity.
 */
public class TipoMuestraDTO implements Serializable {

    private Long id;

    private String descripcion;

    private Long recipienteId;

    private Recipiente recipiente;

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

    public Long getRecipienteId() {
        return recipienteId;
    }

    public void setRecipienteId(Long recipienteId) {
        this.recipienteId = recipienteId;
    }

    public Recipiente getRecipiente() {
        return recipiente;
    }

    public void setRecipiente(Recipiente recipiente) {
        this.recipiente = recipiente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TipoMuestraDTO tipoMuestraDTO = (TipoMuestraDTO) o;
        if (tipoMuestraDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tipoMuestraDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TipoMuestraDTO{" +
            "id=" + getId() +
            ", descripcion='" + getDescripcion() + "'" +
            ", recipiente=" + getRecipienteId() +
            "}";
    }
}
