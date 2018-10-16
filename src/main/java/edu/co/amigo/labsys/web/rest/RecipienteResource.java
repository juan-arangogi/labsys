package edu.co.amigo.labsys.web.rest;

import com.codahale.metrics.annotation.Timed;
import edu.co.amigo.labsys.service.RecipienteService;
import edu.co.amigo.labsys.web.rest.errors.BadRequestAlertException;
import edu.co.amigo.labsys.web.rest.util.HeaderUtil;
import edu.co.amigo.labsys.web.rest.util.PaginationUtil;
import edu.co.amigo.labsys.service.dto.RecipienteDTO;
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
 * REST controller for managing Recipiente.
 */
@RestController
@RequestMapping("/api")
public class RecipienteResource {

    private final Logger log = LoggerFactory.getLogger(RecipienteResource.class);

    private static final String ENTITY_NAME = "recipiente";

    private final RecipienteService recipienteService;

    public RecipienteResource(RecipienteService recipienteService) {
        this.recipienteService = recipienteService;
    }

    /**
     * POST  /recipientes : Create a new recipiente.
     *
     * @param recipienteDTO the recipienteDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new recipienteDTO, or with status 400 (Bad Request) if the recipiente has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/recipientes")
    @Timed
    public ResponseEntity<RecipienteDTO> createRecipiente(@RequestBody RecipienteDTO recipienteDTO) throws URISyntaxException {
        log.debug("REST request to save Recipiente : {}", recipienteDTO);
        if (recipienteDTO.getId() != null) {
            throw new BadRequestAlertException("A new recipiente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RecipienteDTO result = recipienteService.save(recipienteDTO);
        return ResponseEntity.created(new URI("/api/recipientes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /recipientes : Updates an existing recipiente.
     *
     * @param recipienteDTO the recipienteDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated recipienteDTO,
     * or with status 400 (Bad Request) if the recipienteDTO is not valid,
     * or with status 500 (Internal Server Error) if the recipienteDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/recipientes")
    @Timed
    public ResponseEntity<RecipienteDTO> updateRecipiente(@RequestBody RecipienteDTO recipienteDTO) throws URISyntaxException {
        log.debug("REST request to update Recipiente : {}", recipienteDTO);
        if (recipienteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RecipienteDTO result = recipienteService.save(recipienteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, recipienteDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /recipientes : get all the recipientes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of recipientes in body
     */
    @GetMapping("/recipientes")
    @Timed
    public ResponseEntity<List<RecipienteDTO>> getAllRecipientes(Pageable pageable) {
        log.debug("REST request to get a page of Recipientes");
        Page<RecipienteDTO> page = recipienteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/recipientes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /recipientes/:id : get the "id" recipiente.
     *
     * @param id the id of the recipienteDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the recipienteDTO, or with status 404 (Not Found)
     */
    @GetMapping("/recipientes/{id}")
    @Timed
    public ResponseEntity<RecipienteDTO> getRecipiente(@PathVariable Long id) {
        log.debug("REST request to get Recipiente : {}", id);
        Optional<RecipienteDTO> recipienteDTO = recipienteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(recipienteDTO);
    }

    /**
     * DELETE  /recipientes/:id : delete the "id" recipiente.
     *
     * @param id the id of the recipienteDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/recipientes/{id}")
    @Timed
    public ResponseEntity<Void> deleteRecipiente(@PathVariable Long id) {
        log.debug("REST request to delete Recipiente : {}", id);
        recipienteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
