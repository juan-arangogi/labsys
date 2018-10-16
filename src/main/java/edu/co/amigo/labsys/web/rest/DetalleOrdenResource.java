package edu.co.amigo.labsys.web.rest;

import com.codahale.metrics.annotation.Timed;
import edu.co.amigo.labsys.domain.Orden;
import edu.co.amigo.labsys.domain.Procedimiento;
import edu.co.amigo.labsys.repository.OrdenRepository;
import edu.co.amigo.labsys.repository.ProcedimientoRepository;
import edu.co.amigo.labsys.service.DetalleOrdenService;
import edu.co.amigo.labsys.service.OrdenService;
import edu.co.amigo.labsys.web.rest.errors.BadRequestAlertException;
import edu.co.amigo.labsys.web.rest.util.HeaderUtil;
import edu.co.amigo.labsys.web.rest.util.PaginationUtil;
import edu.co.amigo.labsys.service.dto.DetalleOrdenDTO;
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
 * REST controller for managing DetalleOrden.
 */
@RestController
@RequestMapping("/api")
public class DetalleOrdenResource {

    private final Logger log = LoggerFactory.getLogger(DetalleOrdenResource.class);

    private static final String ENTITY_NAME = "detalleOrden";

    private final DetalleOrdenService detalleOrdenService;

    private final ProcedimientoRepository procedimientoRepository;

    private final OrdenRepository ordenRepository;

    private final OrdenService ordenService;

    public DetalleOrdenResource(DetalleOrdenService detalleOrdenService, ProcedimientoRepository procedimientoRepository, OrdenRepository ordenRepository, OrdenService ordenService) {
        this.detalleOrdenService = detalleOrdenService;
        this.procedimientoRepository = procedimientoRepository;
        this.ordenRepository = ordenRepository;
        this.ordenService = ordenService;
    }

    /**
     * POST  /detalle-ordens : Create a new detalleOrden.
     *
     * @param detalleOrdenDTO the detalleOrdenDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new detalleOrdenDTO, or with status 400 (Bad Request) if the detalleOrden has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/detalle-ordens")
    @Timed
    public ResponseEntity<DetalleOrdenDTO> createDetalleOrden(@RequestBody DetalleOrdenDTO detalleOrdenDTO) throws URISyntaxException {
        log.debug("REST request to save DetalleOrden : {}", detalleOrdenDTO);
        if (detalleOrdenDTO.getId() != null) {
            throw new BadRequestAlertException("A new detalleOrden cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Procedimiento procedimiento = procedimientoRepository.findById(detalleOrdenDTO.getProcedimientoId()).get();
        Orden orden = ordenRepository.findById(detalleOrdenDTO.getOrdenId()).get();

        Double totalDetalle = (procedimiento.getPrecioUnitario() * detalleOrdenDTO.getCantidad());
        Double descuento = (totalDetalle * (detalleOrdenDTO.getDescuento() / 100.0));

        detalleOrdenDTO.setTotal(totalDetalle - descuento);

        DetalleOrdenDTO result = detalleOrdenService.save(detalleOrdenDTO);

        ordenService.recalculateOrdenTotal(orden);

        return ResponseEntity.created(new URI("/api/detalle-ordens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /detalle-ordens : Updates an existing detalleOrden.
     *
     * @param detalleOrdenDTO the detalleOrdenDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated detalleOrdenDTO,
     * or with status 400 (Bad Request) if the detalleOrdenDTO is not valid,
     * or with status 500 (Internal Server Error) if the detalleOrdenDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/detalle-ordens")
    @Timed
    public ResponseEntity<DetalleOrdenDTO> updateDetalleOrden(@RequestBody DetalleOrdenDTO detalleOrdenDTO) throws URISyntaxException {
        log.debug("REST request to update DetalleOrden : {}", detalleOrdenDTO);
        if (detalleOrdenDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        Procedimiento procedimiento = procedimientoRepository.findById(detalleOrdenDTO.getProcedimientoId()).get();
        Orden orden = ordenRepository.findById(detalleOrdenDTO.getOrdenId()).get();

        Double totalDetalle = (procedimiento.getPrecioUnitario() * detalleOrdenDTO.getCantidad());
        Double descuento = (totalDetalle * (detalleOrdenDTO.getDescuento() / 100.0));

        detalleOrdenDTO.setTotal(totalDetalle - descuento);

        DetalleOrdenDTO result = detalleOrdenService.save(detalleOrdenDTO);

        ordenService.recalculateOrdenTotal(orden);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, detalleOrdenDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /detalle-ordens : get all the detalleOrdens.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of detalleOrdens in body
     */
    @GetMapping("/detalle-ordens")
    @Timed
    public ResponseEntity<List<DetalleOrdenDTO>> getAllDetalleOrdens(Pageable pageable) {
        log.debug("REST request to get a page of DetalleOrdens");
        Page<DetalleOrdenDTO> page = detalleOrdenService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/detalle-ordens");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /detalle-ordens/:id : get the "id" detalleOrden.
     *
     * @param id the id of the detalleOrdenDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the detalleOrdenDTO, or with status 404 (Not Found)
     */
    @GetMapping("/detalle-ordens/{id}")
    @Timed
    public ResponseEntity<DetalleOrdenDTO> getDetalleOrden(@PathVariable Long id) {
        log.debug("REST request to get DetalleOrden : {}", id);
        Optional<DetalleOrdenDTO> detalleOrdenDTO = detalleOrdenService.findOne(id);
        return ResponseUtil.wrapOrNotFound(detalleOrdenDTO);
    }

    /**
     * DELETE  /detalle-ordens/:id : delete the "id" detalleOrden.
     *
     * @param id the id of the detalleOrdenDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/detalle-ordens/{id}")
    @Timed
    public ResponseEntity<Void> deleteDetalleOrden(@PathVariable Long id) {
        log.debug("REST request to delete DetalleOrden : {}", id);
        detalleOrdenService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
