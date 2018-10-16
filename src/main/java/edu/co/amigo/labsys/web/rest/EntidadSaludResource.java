package edu.co.amigo.labsys.web.rest;

import com.codahale.metrics.annotation.Timed;
import edu.co.amigo.labsys.service.EntidadSaludService;
import edu.co.amigo.labsys.web.rest.errors.BadRequestAlertException;
import edu.co.amigo.labsys.web.rest.util.HeaderUtil;
import edu.co.amigo.labsys.web.rest.util.PaginationUtil;
import edu.co.amigo.labsys.service.dto.EntidadSaludDTO;
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

/**
 * REST controller for managing EntidadSalud.
 */
@RestController
@RequestMapping("/api")
public class EntidadSaludResource {

    private final Logger log = LoggerFactory.getLogger(EntidadSaludResource.class);

    private static final String ENTITY_NAME = "entidadSalud";

    private final EntidadSaludService entidadSaludService;

    public EntidadSaludResource(EntidadSaludService entidadSaludService) {
        this.entidadSaludService = entidadSaludService;
    }

    /**
     * POST  /entidad-saluds : Create a new entidadSalud.
     *
     * @param entidadSaludDTO the entidadSaludDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new entidadSaludDTO, or with status 400 (Bad Request) if the entidadSalud has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/entidad-saluds")
    @Timed
    public ResponseEntity<EntidadSaludDTO> createEntidadSalud(@RequestBody EntidadSaludDTO entidadSaludDTO) throws URISyntaxException {
        log.debug("REST request to save EntidadSalud : {}", entidadSaludDTO);
        if (entidadSaludDTO.getId() != null) {
            throw new BadRequestAlertException("A new entidadSalud cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EntidadSaludDTO result = entidadSaludService.save(entidadSaludDTO);
        return ResponseEntity.created(new URI("/api/entidad-saluds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /entidad-saluds : Updates an existing entidadSalud.
     *
     * @param entidadSaludDTO the entidadSaludDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated entidadSaludDTO,
     * or with status 400 (Bad Request) if the entidadSaludDTO is not valid,
     * or with status 500 (Internal Server Error) if the entidadSaludDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/entidad-saluds")
    @Timed
    public ResponseEntity<EntidadSaludDTO> updateEntidadSalud(@RequestBody EntidadSaludDTO entidadSaludDTO) throws URISyntaxException {
        log.debug("REST request to update EntidadSalud : {}", entidadSaludDTO);
        if (entidadSaludDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EntidadSaludDTO result = entidadSaludService.save(entidadSaludDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, entidadSaludDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /entidad-saluds : get all the entidadSaluds.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of entidadSaluds in body
     */
    @GetMapping("/entidad-saluds")
    @Timed
    public ResponseEntity<List<EntidadSaludDTO>> getAllEntidadSaluds(Pageable pageable) {
        log.debug("REST request to get a page of EntidadSaluds");
        Page<EntidadSaludDTO> page = entidadSaludService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/entidad-saluds");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /entidad-saluds/:id : get the "id" entidadSalud.
     *
     * @param id the id of the entidadSaludDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the entidadSaludDTO, or with status 404 (Not Found)
     */
    @GetMapping("/entidad-saluds/{id}")
    @Timed
    public ResponseEntity<EntidadSaludDTO> getEntidadSalud(@PathVariable Long id) {
        log.debug("REST request to get EntidadSalud : {}", id);
        Optional<EntidadSaludDTO> entidadSaludDTO = entidadSaludService.findOne(id);
        return ResponseUtil.wrapOrNotFound(entidadSaludDTO);
    }

    /**
     * DELETE  /entidad-saluds/:id : delete the "id" entidadSalud.
     *
     * @param id the id of the entidadSaludDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/entidad-saluds/{id}")
    @Timed
    public ResponseEntity<Void> deleteEntidadSalud(@PathVariable Long id) {
        log.debug("REST request to delete EntidadSalud : {}", id);
        entidadSaludService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
