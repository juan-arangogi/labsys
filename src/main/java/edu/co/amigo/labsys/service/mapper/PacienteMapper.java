package edu.co.amigo.labsys.service.mapper;

import edu.co.amigo.labsys.domain.*;
import edu.co.amigo.labsys.service.dto.PacienteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Paciente and its DTO PacienteDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PacienteMapper extends EntityMapper<PacienteDTO, Paciente> {


    @Mapping(target = "ordens", ignore = true)
    Paciente toEntity(PacienteDTO pacienteDTO);

    default Paciente fromId(Long id) {
        if (id == null) {
            return null;
        }
        Paciente paciente = new Paciente();
        paciente.setId(id);
        return paciente;
    }
}
