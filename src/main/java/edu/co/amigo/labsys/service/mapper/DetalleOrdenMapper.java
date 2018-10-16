package edu.co.amigo.labsys.service.mapper;

import edu.co.amigo.labsys.domain.*;
import edu.co.amigo.labsys.service.dto.DetalleOrdenDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity DetalleOrden and its DTO DetalleOrdenDTO.
 */
@Mapper(componentModel = "spring", uses = {OrdenMapper.class, ProcedimientoMapper.class})
public interface DetalleOrdenMapper extends EntityMapper<DetalleOrdenDTO, DetalleOrden> {

    @Mapping(source = "orden.id", target = "ordenId")
    @Mapping(source = "procedimiento.id", target = "procedimientoId")
    @Mapping(source = "orden", target = "orden")
    @Mapping(source = "procedimiento", target = "procedimiento")
    DetalleOrdenDTO toDto(DetalleOrden detalleOrden);

    @Mapping(source = "ordenId", target = "orden")
    @Mapping(source = "procedimientoId", target = "procedimiento")
    DetalleOrden toEntity(DetalleOrdenDTO detalleOrdenDTO);

    default DetalleOrden fromId(Long id) {
        if (id == null) {
            return null;
        }
        DetalleOrden detalleOrden = new DetalleOrden();
        detalleOrden.setId(id);
        return detalleOrden;
    }
}
