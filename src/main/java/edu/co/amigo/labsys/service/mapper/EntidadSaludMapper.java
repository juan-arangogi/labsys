package edu.co.amigo.labsys.service.mapper;

import edu.co.amigo.labsys.domain.*;
import edu.co.amigo.labsys.service.dto.EntidadSaludDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity EntidadSalud and its DTO EntidadSaludDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EntidadSaludMapper extends EntityMapper<EntidadSaludDTO, EntidadSalud> {


    @Mapping(target = "ordens", ignore = true)
    @Mapping(target = "medicos", ignore = true)
    EntidadSalud toEntity(EntidadSaludDTO entidadSaludDTO);

    default EntidadSalud fromId(Long id) {
        if (id == null) {
            return null;
        }
        EntidadSalud entidadSalud = new EntidadSalud();
        entidadSalud.setId(id);
        return entidadSalud;
    }
}
