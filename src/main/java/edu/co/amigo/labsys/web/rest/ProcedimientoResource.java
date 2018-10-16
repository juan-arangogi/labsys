package edu.co.amigo.labsys.web.rest;

import com.codahale.metrics.annotation.Timed;
import edu.co.amigo.labsys.service.ProcedimientoService;
import edu.co.amigo.labsys.web.rest.errors.BadRequestAlertException;
import edu.co.amigo.labsys.web.rest.util.HeaderUtil;
import edu.co.amigo.labsys.web.rest.util.PaginationUtil;
import edu.co.amigo.labsys.service.dto.ProcedimientoDTO;
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
 * REST controller for managing Procedimiento.
 */
@RestController
@RequestMapping("/api")
public class ProcedimientoResource {

    private final Logger log = LoggerFactory.getLogger(ProcedimientoResource.class);

    private static final String ENTITY_NAME = "procedimiento";

    private final ProcedimientoService procedimientoService;

    public ProcedimientoResource(ProcedimientoService procedimientoService) {
        this.procedimientoService = procedimientoService;
    }

    /**
     * POST  /procedimientos : Create a new procedimiento.
     *
     * @param procedimientoDTO the procedimientoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new procedimientoDTO, or with status 400 (Bad Request) if the procedimiento has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/procedimientos")
    @Timed
    public ResponseEntity<ProcedimientoDTO> createProcedimiento(@RequestBody ProcedimientoDTO procedimientoDTO) throws URISyntaxException {
        log.debug("REST request to save Procedimiento : {}", procedimientoDTO);
        if (procedimientoDTO.getId() != null) {
            throw new BadRequestAlertException("A new procedimiento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProcedimientoDTO result = procedimientoService.save(procedimientoDTO);
        return ResponseEntity.created(new URI("/api/procedimientos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /procedimientos : Updates an existing procedimiento.
     *
     * @param procedimientoDTO the procedimientoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated procedimientoDTO,
     * or with status 400 (Bad Request) if the procedimientoDTO is not valid,
     * or with status 500 (Internal Server Error) if the procedimientoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/procedimientos")
    @Timed
    public ResponseEntity<ProcedimientoDTO> updateProcedimiento(@RequestBody ProcedimientoDTO procedimientoDTO) throws URISyntaxException {
        log.debug("REST request to update Procedimiento : {}", procedimientoDTO);
        if (procedimientoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProcedimientoDTO result = procedimientoService.save(procedimientoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, procedimientoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /procedimientos : get all the procedimientos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of procedimientos in body
     */
    @GetMapping("/procedimientos")
    @Timed
    public ResponseEntity<List<ProcedimientoDTO>> getAllProcedimientos(Pageable pageable) {
        log.debug("REST request to get a page of Procedimientos");
        Page<ProcedimientoDTO> page = procedimientoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/procedimientos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /procedimientos/:id : get the "id" procedimiento.
     *
     * @param id the id of the procedimientoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the procedimientoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/procedimientos/{id}")
    @Timed
    public ResponseEntity<ProcedimientoDTO> getProcedimiento(@PathVariable Long id) {
        log.debug("REST request to get Procedimiento : {}", id);
        Optional<ProcedimientoDTO> procedimientoDTO = procedimientoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(procedimientoDTO);
    }

    /**
     * DELETE  /procedimientos/:id : delete the "id" procedimiento.
     *
     * @param id the id of the procedimientoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/procedimientos/{id}")
    @Timed
    public ResponseEntity<Void> deleteProcedimiento(@PathVariable Long id) {
        log.debug("REST request to delete Procedimiento : {}", id);
        procedimientoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
