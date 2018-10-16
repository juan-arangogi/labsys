package edu.co.amigo.labsys.service.mapper;

import edu.co.amigo.labsys.domain.*;
import edu.co.amigo.labsys.service.dto.TipoMuestraDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TipoMuestra and its DTO TipoMuestraDTO.
 */
@Mapper(componentModel = "spring", uses = {RecipienteMapper.class})
public interface TipoMuestraMapper extends EntityMapper<TipoMuestraDTO, TipoMuestra> {

    @Mapping(source = "recipiente.id", target = "recipienteId")
    @Mapping(source = "recipiente", target = "recipiente")
    TipoMuestraDTO toDto(TipoMuestra tipoMuestra);

    @Mapping(source = "recipienteId", target = "recipiente")
    @Mapping(target = "procedimientos", ignore = true)
    TipoMuestra toEntity(TipoMuestraDTO tipoMuestraDTO);

    default TipoMuestra fromId(Long id) {
        if (id == null) {
            return null;
        }
        TipoMuestra tipoMuestra = new TipoMuestra();
        tipoMuestra.setId(id);
        return tipoMuestra;
    }
}
