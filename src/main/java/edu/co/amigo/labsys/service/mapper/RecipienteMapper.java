package edu.co.amigo.labsys.service.mapper;

import edu.co.amigo.labsys.domain.*;
import edu.co.amigo.labsys.service.dto.RecipienteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Recipiente and its DTO RecipienteDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RecipienteMapper extends EntityMapper<RecipienteDTO, Recipiente> {


    @Mapping(target = "tipoMuestras", ignore = true)
    Recipiente toEntity(RecipienteDTO recipienteDTO);

    default Recipiente fromId(Long id) {
        if (id == null) {
            return null;
        }
        Recipiente recipiente = new Recipiente();
        recipiente.setId(id);
        return recipiente;
    }
}
