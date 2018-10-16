package edu.co.amigo.labsys.service.mapper;

import edu.co.amigo.labsys.domain.*;
import edu.co.amigo.labsys.service.dto.ProcedimientoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Procedimiento and its DTO ProcedimientoDTO.
 */
@Mapper(componentModel = "spring", uses = {TipoMuestraMapper.class})
public interface ProcedimientoMapper extends EntityMapper<ProcedimientoDTO, Procedimiento> {

    @Mapping(source = "tipoMuestra.id", target = "tipoMuestraId")
    @Mapping(source = "tipoMuestra", target = "tipoMuestra")
    ProcedimientoDTO toDto(Procedimiento procedimiento);

    @Mapping(target = "detalleOrdens", ignore = true)
    @Mapping(source = "tipoMuestraId", target = "tipoMuestra")
    Procedimiento toEntity(ProcedimientoDTO procedimientoDTO);

    default Procedimiento fromId(Long id) {
        if (id == null) {
            return null;
        }
        Procedimiento procedimiento = new Procedimiento();
        procedimiento.setId(id);
        return procedimiento;
    }
}
