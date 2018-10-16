package edu.co.amigo.labsys.service;

import edu.co.amigo.labsys.domain.DetalleOrden;
import edu.co.amigo.labsys.repository.DetalleOrdenRepository;
import edu.co.amigo.labsys.service.dto.DetalleOrdenDTO;
import edu.co.amigo.labsys.service.mapper.DetalleOrdenMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing DetalleOrden.
 */
@Service
@Transactional
public class DetalleOrdenService {

    private final Logger log = LoggerFactory.getLogger(DetalleOrdenService.class);

    private final DetalleOrdenRepository detalleOrdenRepository;

    private final DetalleOrdenMapper detalleOrdenMapper;

    public DetalleOrdenService(DetalleOrdenRepository detalleOrdenRepository, DetalleOrdenMapper detalleOrdenMapper) {
        this.detalleOrdenRepository = detalleOrdenRepository;
        this.detalleOrdenMapper = detalleOrdenMapper;
    }

    /**
     * Save a detalleOrden.
     *
     * @param detalleOrdenDTO the entity to save
     * @return the persisted entity
     */
    public DetalleOrdenDTO save(DetalleOrdenDTO detalleOrdenDTO) {
        log.debug("Request to save DetalleOrden : {}", detalleOrdenDTO);

        DetalleOrden detalleOrden = detalleOrdenMapper.toEntity(detalleOrdenDTO);
        detalleOrden = detalleOrdenRepository.save(detalleOrden);
        return detalleOrdenMapper.toDto(detalleOrden);
    }

    /**
     * Get all the detalleOrdens.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DetalleOrdenDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DetalleOrdens");
        return detalleOrdenRepository.findAll(pageable)
            .map(detalleOrdenMapper::toDto);
    }


    /**
     * Get one detalleOrden by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<DetalleOrdenDTO> findOne(Long id) {
        log.debug("Request to get DetalleOrden : {}", id);
        return detalleOrdenRepository.findById(id)
            .map(detalleOrdenMapper::toDto);
    }

    /**
     * Delete the detalleOrden by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete DetalleOrden : {}", id);
        detalleOrdenRepository.deleteById(id);
    }
}
