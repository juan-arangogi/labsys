package edu.co.amigo.labsys.service.dto;

import java.io.Serializable;
import java.util.Objects;
import edu.co.amigo.labsys.domain.enumeration.TipoDocumento;

/**
 * A DTO for the EntidadSalud entity.
 */
public class EntidadSaludDTO implements Serializable {

    private Long id;

    private TipoDocumento tipoDocumento;

    private String documento;

    private String razonSocial;

    private String direccion;

    private String telefono;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EntidadSaludDTO entidadSaludDTO = (EntidadSaludDTO) o;
        if (entidadSaludDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), entidadSaludDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EntidadSaludDTO{" +
            "id=" + getId() +
            ", tipoDocumento='" + getTipoDocumento() + "'" +
            ", documento='" + getDocumento() + "'" +
            ", razonSocial='" + getRazonSocial() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", telefono='" + getTelefono() + "'" +
            "}";
    }
}
