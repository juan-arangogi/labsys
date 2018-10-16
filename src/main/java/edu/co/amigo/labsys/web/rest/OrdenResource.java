package edu.co.amigo.labsys.web.rest;

import com.codahale.metrics.annotation.Timed;
import edu.co.amigo.labsys.service.OrdenService;
import edu.co.amigo.labsys.web.rest.errors.BadRequestAlertException;
import edu.co.amigo.labsys.web.rest.util.HeaderUtil;
import edu.co.amigo.labsys.web.rest.util.PaginationUtil;
import edu.co.amigo.labsys.service.dto.OrdenDTO;
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
 * REST controller for managing Orden.
 */
@RestController
@RequestMapping("/api")
public class OrdenResource {

    private final Logger log = LoggerFactory.getLogger(OrdenResource.class);

    private static final String ENTITY_NAME = "orden";

    private final OrdenService ordenService;

    public OrdenResource(OrdenService ordenService) {
        this.ordenService = ordenService;
    }

    /**
     * POST  /ordens : Create a new orden.
     *
     * @param ordenDTO the ordenDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ordenDTO, or with status 400 (Bad Request) if the orden has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ordens")
    @Timed
    public ResponseEntity<OrdenDTO> createOrden(@RequestBody OrdenDTO ordenDTO) throws URISyntaxException {
        log.debug("REST request to save Orden : {}", ordenDTO);
        if (ordenDTO.getId() != null) {
            throw new BadRequestAlertException("A new orden cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ordenDTO.setTotal(0d);
        OrdenDTO result = ordenService.save(ordenDTO);
        return ResponseEntity.created(new URI("/api/ordens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ordens : Updates an existing orden.
     *
     * @param ordenDTO the ordenDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ordenDTO,
     * or with status 400 (Bad Request) if the ordenDTO is not valid,
     * or with status 500 (Internal Server Error) if the ordenDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ordens")
    @Timed
    public ResponseEntity<OrdenDTO> updateOrden(@RequestBody OrdenDTO ordenDTO) throws URISyntaxException {
        log.debug("REST request to update Orden : {}", ordenDTO);
        if (ordenDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrdenDTO result = ordenService.save(ordenDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ordenDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ordens : get all the ordens.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of ordens in body
     */
    @GetMapping("/ordens")
    @Timed
    public ResponseEntity<List<OrdenDTO>> getAllOrdens(Pageable pageable) {
        log.debug("REST request to get a page of Ordens");
        Page<OrdenDTO> page = ordenService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ordens");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ordens/:id : get the "id" orden.
     *
     * @param id the id of the ordenDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ordenDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ordens/{id}")
    @Timed
    public ResponseEntity<OrdenDTO> getOrden(@PathVariable Long id) {
        log.debug("REST request to get Orden : {}", id);
        Optional<OrdenDTO> ordenDTO = ordenService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ordenDTO);
    }

    /**
     * DELETE  /ordens/:id : delete the "id" orden.
     *
     * @param id the id of the ordenDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ordens/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrden(@PathVariable Long id) {
        log.debug("REST request to delete Orden : {}", id);
        ordenService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
