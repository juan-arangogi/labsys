package edu.co.amigo.labsys.service.dto;

import java.io.Serializable;
import java.util.Objects;

import edu.co.amigo.labsys.domain.EntidadSalud;
import edu.co.amigo.labsys.domain.Medico;
import edu.co.amigo.labsys.domain.Paciente;
import edu.co.amigo.labsys.domain.enumeration.EstadoOrden;

/**
 * A DTO for the Orden entity.
 */
public class OrdenDTO implements Serializable {

    private Long id;

    private Double total;

    private EstadoOrden estado;

    private Long pacienteId;

    private Long entidadSaludId;

    private Long medicoId;

    private Paciente paciente;

    private Medico medico;

    private EntidadSalud entidadSalud;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public EstadoOrden getEstado() {
        return estado;
    }

    public void setEstado(EstadoOrden estado) {
        this.estado = estado;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }

    public Long getEntidadSaludId() {
        return entidadSaludId;
    }

    public void setEntidadSaludId(Long entidadSaludId) {
        this.entidadSaludId = entidadSaludId;
    }

    public Long getMedicoId() {
        return medicoId;
    }

    public void setMedicoId(Long medicoId) {
        this.medicoId = medicoId;
    }


    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
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

        OrdenDTO ordenDTO = (OrdenDTO) o;
        if (ordenDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ordenDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrdenDTO{" +
            "id=" + getId() +
            ", total=" + getTotal() +
            ", estado='" + getEstado() + "'" +
            ", paciente=" + getPacienteId() +
            ", entidadSalud=" + getEntidadSaludId() +
            ", medico=" + getMedicoId() +
            "}";
    }
}
