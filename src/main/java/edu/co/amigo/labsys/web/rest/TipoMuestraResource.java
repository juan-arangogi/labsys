package edu.co.amigo.labsys.web.rest;

import com.codahale.metrics.annotation.Timed;
import edu.co.amigo.labsys.service.TipoMuestraService;
import edu.co.amigo.labsys.web.rest.errors.BadRequestAlertException;
import edu.co.amigo.labsys.web.rest.util.HeaderUtil;
import edu.co.amigo.labsys.web.rest.util.PaginationUtil;
import edu.co.amigo.labsys.service.dto.TipoMuestraDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing TipoMuestra.
 */
@RestController
@RequestMapping("/api")
public class TipoMuestraResource {

    private final Logger log = LoggerFactory.getLogger(TipoMuestraResource.class);

    private static final String ENTITY_NAME = "tipoMuestra";

    private final TipoMuestraService tipoMuestraService;

    public TipoMuestraResource(TipoMuestraService tipoMuestraService) {
        this.tipoMuestraService = tipoMuestraService;
    }

    /**
     * POST  /tipo-muestras : Create a new tipoMuestra.
     *
     * @param tipoMuestraDTO the tipoMuestraDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tipoMuestraDTO, or with status 400 (Bad Request) if the tipoMuestra has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tipo-muestras")
    @Timed
    public ResponseEntity<TipoMuestraDTO> createTipoMuestra(@RequestBody TipoMuestraDTO tipoMuestraDTO) throws URISyntaxException {
        log.debug("REST request to save TipoMuestra : {}", tipoMuestraDTO);
        if (tipoMuestraDTO.getId() != null) {
            throw new BadRequestAlertException("A new tipoMuestra cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoMuestraDTO result = tipoMuestraService.save(tipoMuestraDTO);
        return ResponseEntity.created(new URI("/api/tipo-muestras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tipo-muestras : Updates an existing tipoMuestra.
     *
     * @param tipoMuestraDTO the tipoMuestraDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tipoMuestraDTO,
     * or with status 400 (Bad Request) if the tipoMuestraDTO is not valid,
     * or with status 500 (Internal Server Error) if the tipoMuestraDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tipo-muestras")
    @Timed
    public ResponseEntity<TipoMuestraDTO> updateTipoMuestra(@RequestBody TipoMuestraDTO tipoMuestraDTO) throws URISyntaxException {
        log.debug("REST request to update TipoMuestra : {}", tipoMuestraDTO);
        if (tipoMuestraDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoMuestraDTO result = tipoMuestraService.save(tipoMuestraDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tipoMuestraDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tipo-muestras : get all the tipoMuestras.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tipoMuestras in body
     */
    @GetMapping("/tipo-muestras")
    @Timed
    public ResponseEntity<List<TipoMuestraDTO>> getAllTipoMuestras(Pageable pageable) {
        log.debug("REST request to get a page of TipoMuestras");
        Page<TipoMuestraDTO> page = tipoMuestraService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tipo-muestras");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tipo-muestras/:id : get the "id" tipoMuestra.
     *
     * @param id the id of the tipoMuestraDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tipoMuestraDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tipo-muestras/{id}")
    @Timed
    public ResponseEntity<TipoMuestraDTO> getTipoMuestra(@PathVariable Long id) {
        log.debug("REST request to get TipoMuestra : {}", id);
        Optional<TipoMuestraDTO> tipoMuestraDTO = tipoMuestraService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoMuestraDTO);
    }

    /**
     * DELETE  /tipo-muestras/:id : delete the "id" tipoMuestra.
     *
     * @param id the id of the tipoMuestraDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tipo-muestras/{id}")
    @Timed
    public ResponseEntity<Void> deleteTipoMuestra(@PathVariable Long id) {
        log.debug("REST request to delete TipoMuestra : {}", id);
        tipoMuestraService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
