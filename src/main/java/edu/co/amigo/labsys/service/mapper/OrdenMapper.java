package edu.co.amigo.labsys.service.mapper;

import edu.co.amigo.labsys.domain.*;
import edu.co.amigo.labsys.service.dto.OrdenDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Orden and its DTO OrdenDTO.
 */
@Mapper(componentModel = "spring", uses = {PacienteMapper.class, EntidadSaludMapper.class, MedicoMapper.class})
public interface OrdenMapper extends EntityMapper<OrdenDTO, Orden> {

    @Mapping(source = "paciente.id", target = "pacienteId")
    @Mapping(source = "entidadSalud.id", target = "entidadSaludId")
    @Mapping(source = "medico.id", target = "medicoId")
    @Mapping(source = "medico", target = "medico")
    @Mapping(source = "entidadSalud", target = "entidadSalud")
    @Mapping(source = "paciente", target = "paciente")
    OrdenDTO toDto(Orden orden);

    @Mapping(target = "detalleOrdens", ignore = true)
    @Mapping(source = "pacienteId", target = "paciente")
    @Mapping(source = "entidadSaludId", target = "entidadSalud")
    @Mapping(source = "medicoId", target = "medico")
    Orden toEntity(OrdenDTO ordenDTO);

    default Orden fromId(Long id) {
        if (id == null) {
            return null;
        }
        Orden orden = new Orden();
        orden.setId(id);
        return orden;
    }
}
