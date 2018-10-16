package edu.co.amigo.labsys.service;

import edu.co.amigo.labsys.domain.DetalleOrden;
import edu.co.amigo.labsys.domain.Orden;
import edu.co.amigo.labsys.repository.DetalleOrdenRepository;
import edu.co.amigo.labsys.repository.OrdenRepository;
import edu.co.amigo.labsys.service.dto.OrdenDTO;
import edu.co.amigo.labsys.service.mapper.OrdenMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Orden.
 */
@Service
@Transactional
public class OrdenService {

    private final Logger log = LoggerFactory.getLogger(OrdenService.class);

    private final OrdenRepository ordenRepository;

    private final OrdenMapper ordenMapper;

    private final DetalleOrdenRepository detalleOrdenRepository;

    public OrdenService(OrdenRepository ordenRepository, OrdenMapper ordenMapper, DetalleOrdenRepository detalleOrdenRepository) {
        this.ordenRepository = ordenRepository;
        this.ordenMapper = ordenMapper;
        this.detalleOrdenRepository = detalleOrdenRepository;
    }

    /**
     * Save a orden.
     *
     * @param ordenDTO the entity to save
     * @return the persisted entity
     */
    public OrdenDTO save(OrdenDTO ordenDTO) {
        log.debug("Request to save Orden : {}", ordenDTO);

        Orden orden = ordenMapper.toEntity(ordenDTO);
        orden = ordenRepository.save(orden);
        return ordenMapper.toDto(orden);
    }

    /**
     * Get all the ordens.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<OrdenDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Ordens");
        return ordenRepository.findAll(pageable)
            .map(ordenMapper::toDto);
    }


    /**
     * Get one orden by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<OrdenDTO> findOne(Long id) {
        log.debug("Request to get Orden : {}", id);
        return ordenRepository.findById(id)
            .map(ordenMapper::toDto);
    }

    /**
     * Delete the orden by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Orden : {}", id);
        ordenRepository.deleteById(id);
    }

    /**
     * Recalculate orden total
     *
     * @param orden Orden instance to recalculate
     */
    public void recalculateOrdenTotal(Orden orden) {
        if(orden == null)
            throw new IllegalArgumentException();

        List<DetalleOrden> detalleOrdens = detalleOrdenRepository.findAllByOrden(orden);

        Double totalOrden = 0d;
        for(DetalleOrden detalleOrden : detalleOrdens) {
            totalOrden += detalleOrden.getTotal();
        }

        orden.setTotal(totalOrden);
        ordenRepository.save(orden);
    }
}
