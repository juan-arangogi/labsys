package edu.co.amigo.labsys.service;

import edu.co.amigo.labsys.domain.EntidadSalud;
import edu.co.amigo.labsys.repository.EntidadSaludRepository;
import edu.co.amigo.labsys.service.dto.EntidadSaludDTO;
import edu.co.amigo.labsys.service.mapper.EntidadSaludMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing EntidadSalud.
 */
@Service
@Transactional
public class EntidadSaludService {

    private final Logger log = LoggerFactory.getLogger(EntidadSaludService.class);

    private final EntidadSaludRepository entidadSaludRepository;

    private final EntidadSaludMapper entidadSaludMapper;

    public EntidadSaludService(EntidadSaludRepository entidadSaludRepository, EntidadSaludMapper entidadSaludMapper) {
        this.entidadSaludRepository = entidadSaludRepository;
        this.entidadSaludMapper = entidadSaludMapper;
    }

    /**
     * Save a entidadSalud.
     *
     * @param entidadSaludDTO the entity to save
     * @return the persisted entity
     */
    public EntidadSaludDTO save(EntidadSaludDTO entidadSaludDTO) {
        log.debug("Request to save EntidadSalud : {}", entidadSaludDTO);

        EntidadSalud entidadSalud = entidadSaludMapper.toEntity(entidadSaludDTO);
        entidadSalud = entidadSaludRepository.save(entidadSalud);
        return entidadSaludMapper.toDto(entidadSalud);
    }

    /**
     * Get all the entidadSaluds.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<EntidadSaludDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EntidadSaluds");
        return entidadSaludRepository.findAll(pageable)
            .map(entidadSaludMapper::toDto);
    }


    /**
     * Get one entidadSalud by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<EntidadSaludDTO> findOne(Long id) {
        log.debug("Request to get EntidadSalud : {}", id);
        return entidadSaludRepository.findById(id)
            .map(entidadSaludMapper::toDto);
    }

    /**
     * Delete the entidadSalud by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete EntidadSalud : {}", id);
        entidadSaludRepository.deleteById(id);
    }
}
