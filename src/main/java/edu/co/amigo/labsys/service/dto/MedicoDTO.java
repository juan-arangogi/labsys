package edu.co.amigo.labsys.service.dto;

import java.io.Serializable;
import java.util.Objects;

import edu.co.amigo.labsys.domain.EntidadSalud;
import edu.co.amigo.labsys.domain.enumeration.TipoDocumento;
import edu.co.amigo.labsys.domain.enumeration.Especialidad;

/**
 * A DTO for the Medico entity.
 */
public class MedicoDTO implements Serializable {

    private Long id;

    private TipoDocumento tipoDocumento;

    private String documento;

    private String nombres;

    private String apellidos;

    private Especialidad especialidad;

    private String tarjetaProfesional;

    private Long entidadSaludId;

    private EntidadSalud entidadSalud;

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

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public String getTarjetaProfesional() {
        return tarjetaProfesional;
    }

    public void setTarjetaProfesional(String tarjetaProfesional) {
        this.tarjetaProfesional = tarjetaProfesional;
    }

    public Long getEntidadSaludId() {
        return entidadSaludId;
    }

    public void setEntidadSaludId(Long entidadSaludId) {
        this.entidadSaludId = entidadSaludId;
    }

    public EntidadSalud getEntidadSalud() {
        return entidadSalud;
    }

    public void setEntidadSalud(EntidadSalud entidadSalud) {
        this.entidadSalud = entidadSalud;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MedicoDTO medicoDTO = (MedicoDTO) o;
        if (medicoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), medicoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MedicoDTO{" +
            "id=" + getId() +
            ", tipoDocumento='" + getTipoDocumento() + "'" +
            ", documento='" + getDocumento() + "'" +
            ", nombres='" + getNombres() + "'" +
            ", apellidos='" + getApellidos() + "'" +
            ", especialidad='" + getEspecialidad() + "'" +
            ", tarjetaProfesional='" + getTarjetaProfesional() + "'" +
            ", entidadSalud=" + getEntidadSaludId() +
            "}";
    }
}
