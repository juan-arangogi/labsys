package edu.co.amigo.labsys.service;

import edu.co.amigo.labsys.domain.Procedimiento;
import edu.co.amigo.labsys.repository.ProcedimientoRepository;
import edu.co.amigo.labsys.service.dto.ProcedimientoDTO;
import edu.co.amigo.labsys.service.mapper.ProcedimientoMapper;
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
 * Service Implementation for managing Procedimiento.
 */
@Service
@Transactional
public class ProcedimientoService {

    private final Logger log = LoggerFactory.getLogger(ProcedimientoService.class);

    private final ProcedimientoRepository procedimientoRepository;

    private final ProcedimientoMapper procedimientoMapper;

    public ProcedimientoService(ProcedimientoRepository procedimientoRepository, ProcedimientoMapper procedimientoMapper) {
        this.procedimientoRepository = procedimientoRepository;
        this.procedimientoMapper = procedimientoMapper;
    }

    /**
     * Save a procedimiento.
     *
     * @param procedimientoDTO the entity to save
     * @return the persisted entity
     */
    public ProcedimientoDTO save(ProcedimientoDTO procedimientoDTO) {
        log.debug("Request to save Procedimiento : {}", procedimientoDTO);

        Procedimiento procedimiento = procedimientoMapper.toEntity(procedimientoDTO);
        procedimiento = procedimientoRepository.save(procedimiento);
        return procedimientoMapper.toDto(procedimiento);
    }

    /**
     * Get all the procedimientos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ProcedimientoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Procedimientos");
        return procedimientoRepository.findAll(pageable)
            .map(procedimientoMapper::toDto);
    }


    /**
     * Get one procedimiento by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ProcedimientoDTO> findOne(Long id) {
        log.debug("Request to get Procedimiento : {}", id);
        return procedimientoRepository.findById(id)
            .map(procedimientoMapper::toDto);
    }

    /**
     * Delete the procedimiento by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Procedimiento : {}", id);
        procedimientoRepository.deleteById(id);
    }
}
