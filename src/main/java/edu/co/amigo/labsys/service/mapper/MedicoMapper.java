package edu.co.amigo.labsys.service.mapper;

import edu.co.amigo.labsys.domain.*;
import edu.co.amigo.labsys.service.dto.MedicoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Medico and its DTO MedicoDTO.
 */
@Mapper(componentModel = "spring", uses = {EntidadSaludMapper.class})
public interface MedicoMapper extends EntityMapper<MedicoDTO, Medico> {

    @Mapping(source = "entidadSalud.id", target = "entidadSaludId")
    @Mapping(source = "entidadSalud", target = "entidadSalud")
    MedicoDTO toDto(Medico medico);

    @Mapping(target = "ordens", ignore = true)
    @Mapping(source = "entidadSaludId", target = "entidadSalud")
    Medico toEntity(MedicoDTO medicoDTO);

    default Medico fromId(Long id) {
        if (id == null) {
            return null;
        }
        Medico medico = new Medico();
        medico.setId(id);
        return medico;
    }
}

