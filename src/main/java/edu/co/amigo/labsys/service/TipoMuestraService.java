package edu.co.amigo.labsys.service;

import edu.co.amigo.labsys.domain.TipoMuestra;
import edu.co.amigo.labsys.repository.TipoMuestraRepository;
import edu.co.amigo.labsys.service.dto.TipoMuestraDTO;
import edu.co.amigo.labsys.service.mapper.TipoMuestraMapper;
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
 * Service Implementation for managing TipoMuestra.
 */
@Service
@Transactional
public class TipoMuestraService {

    private final Logger log = LoggerFactory.getLogger(TipoMuestraService.class);

    private final TipoMuestraRepository tipoMuestraRepository;

    private final TipoMuestraMapper tipoMuestraMapper;

    public TipoMuestraService(TipoMuestraRepository tipoMuestraRepository, TipoMuestraMapper tipoMuestraMapper) {
        this.tipoMuestraRepository = tipoMuestraRepository;
        this.tipoMuestraMapper = tipoMuestraMapper;
    }

    /**
     * Save a tipoMuestra.
     *
     * @param tipoMuestraDTO the entity to save
     * @return the persisted entity
     */
    public TipoMuestraDTO save(TipoMuestraDTO tipoMuestraDTO) {
        log.debug("Request to save TipoMuestra : {}", tipoMuestraDTO);

        TipoMuestra tipoMuestra = tipoMuestraMapper.toEntity(tipoMuestraDTO);
        tipoMuestra = tipoMuestraRepository.save(tipoMuestra);
        return tipoMuestraMapper.toDto(tipoMuestra);
    }

    /**
     * Get all the tipoMuestras.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TipoMuestraDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TipoMuestras");
        return tipoMuestraRepository.findAll(pageable)
            .map(tipoMuestraMapper::toDto);
    }


    /**
     * Get one tipoMuestra by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<TipoMuestraDTO> findOne(Long id) {
        log.debug("Request to get TipoMuestra : {}", id);
        return tipoMuestraRepository.findById(id)
            .map(tipoMuestraMapper::toDto);
    }

    /**
     * Delete the tipoMuestra by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TipoMuestra : {}", id);
        tipoMuestraRepository.deleteById(id);
    }
}
