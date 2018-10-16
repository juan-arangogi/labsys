package edu.co.amigo.labsys.web.rest;

import edu.co.amigo.labsys.LabsysApp;

import edu.co.amigo.labsys.domain.DetalleOrden;
import edu.co.amigo.labsys.repository.DetalleOrdenRepository;
import edu.co.amigo.labsys.repository.OrdenRepository;
import edu.co.amigo.labsys.repository.ProcedimientoRepository;
import edu.co.amigo.labsys.service.DetalleOrdenService;
import edu.co.amigo.labsys.service.OrdenService;
import edu.co.amigo.labsys.service.dto.DetalleOrdenDTO;
import edu.co.amigo.labsys.service.mapper.DetalleOrdenMapper;
import edu.co.amigo.labsys.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static edu.co.amigo.labsys.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DetalleOrdenResource REST controller.
 *
 * @see DetalleOrdenResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LabsysApp.class)
public class DetalleOrdenResourceIntTest {

    private static final Integer DEFAULT_DESCUENTO = 1;
    private static final Integer UPDATED_DESCUENTO = 2;

    private static final Integer DEFAULT_CANTIDAD = 1;
    private static final Integer UPDATED_CANTIDAD = 2;

    private static final Double DEFAULT_TOTAL = 1D;
    private static final Double UPDATED_TOTAL = 2D;

    @Autowired
    private DetalleOrdenRepository detalleOrdenRepository;

    @Autowired
    private DetalleOrdenMapper detalleOrdenMapper;
    
    @Autowired
    private DetalleOrdenService detalleOrdenService;

    @Autowired
    private ProcedimientoRepository procedimientoRepository;

    @Autowired
    private OrdenRepository ordenRepository;

    @Autowired
    private OrdenService ordenService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDetalleOrdenMockMvc;

    private DetalleOrden detalleOrden;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DetalleOrdenResource detalleOrdenResource = new DetalleOrdenResource(detalleOrdenService, procedimientoRepository, ordenRepository, ordenService);
        this.restDetalleOrdenMockMvc = MockMvcBuilders.standaloneSetup(detalleOrdenResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DetalleOrden createEntity(EntityManager em) {
        DetalleOrden detalleOrden = new DetalleOrden()
            .descuento(DEFAULT_DESCUENTO)
            .cantidad(DEFAULT_CANTIDAD)
            .total(DEFAULT_TOTAL);
        return detalleOrden;
    }

    @Before
    public void initTest() {
        detalleOrden = createEntity(em);
    }

    @Test
    @Transactional
    public void createDetalleOrden() throws Exception {
        int databaseSizeBeforeCreate = detalleOrdenRepository.findAll().size();

        // Create the DetalleOrden
        DetalleOrdenDTO detalleOrdenDTO = detalleOrdenMapper.toDto(detalleOrden);
        restDetalleOrdenMockMvc.perform(post("/api/detalle-ordens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalleOrdenDTO)))
            .andExpect(status().isCreated());

        // Validate the DetalleOrden in the database
        List<DetalleOrden> detalleOrdenList = detalleOrdenRepository.findAll();
        assertThat(detalleOrdenList).hasSize(databaseSizeBeforeCreate + 1);
        DetalleOrden testDetalleOrden = detalleOrdenList.get(detalleOrdenList.size() - 1);
        assertThat(testDetalleOrden.getDescuento()).isEqualTo(DEFAULT_DESCUENTO);
        assertThat(testDetalleOrden.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
        assertThat(testDetalleOrden.getTotal()).isEqualTo(DEFAULT_TOTAL);
    }

    @Test
    @Transactional
    public void createDetalleOrdenWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = detalleOrdenRepository.findAll().size();

        // Create the DetalleOrden with an existing ID
        detalleOrden.setId(1L);
        DetalleOrdenDTO detalleOrdenDTO = detalleOrdenMapper.toDto(detalleOrden);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDetalleOrdenMockMvc.perform(post("/api/detalle-ordens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalleOrdenDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DetalleOrden in the database
        List<DetalleOrden> detalleOrdenList = detalleOrdenRepository.findAll();
        assertThat(detalleOrdenList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDetalleOrdens() throws Exception {
        // Initialize the database
        detalleOrdenRepository.saveAndFlush(detalleOrden);

        // Get all the detalleOrdenList
        restDetalleOrdenMockMvc.perform(get("/api/detalle-ordens?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(detalleOrden.getId().intValue())))
            .andExpect(jsonPath("$.[*].descuento").value(hasItem(DEFAULT_DESCUENTO)))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD)))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getDetalleOrden() throws Exception {
        // Initialize the database
        detalleOrdenRepository.saveAndFlush(detalleOrden);

        // Get the detalleOrden
        restDetalleOrdenMockMvc.perform(get("/api/detalle-ordens/{id}", detalleOrden.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(detalleOrden.getId().intValue()))
            .andExpect(jsonPath("$.descuento").value(DEFAULT_DESCUENTO))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDetalleOrden() throws Exception {
        // Get the detalleOrden
        restDetalleOrdenMockMvc.perform(get("/api/detalle-ordens/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDetalleOrden() throws Exception {
        // Initialize the database
        detalleOrdenRepository.saveAndFlush(detalleOrden);

        int databaseSizeBeforeUpdate = detalleOrdenRepository.findAll().size();

        // Update the detalleOrden
        DetalleOrden updatedDetalleOrden = detalleOrdenRepository.findById(detalleOrden.getId()).get();
        // Disconnect from session so that the updates on updatedDetalleOrden are not directly saved in db
        em.detach(updatedDetalleOrden);
        updatedDetalleOrden
            .descuento(UPDATED_DESCUENTO)
            .cantidad(UPDATED_CANTIDAD)
            .total(UPDATED_TOTAL);
        DetalleOrdenDTO detalleOrdenDTO = detalleOrdenMapper.toDto(updatedDetalleOrden);

        restDetalleOrdenMockMvc.perform(put("/api/detalle-ordens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalleOrdenDTO)))
            .andExpect(status().isOk());

        // Validate the DetalleOrden in the database
        List<DetalleOrden> detalleOrdenList = detalleOrdenRepository.findAll();
        assertThat(detalleOrdenList).hasSize(databaseSizeBeforeUpdate);
        DetalleOrden testDetalleOrden = detalleOrdenList.get(detalleOrdenList.size() - 1);
        assertThat(testDetalleOrden.getDescuento()).isEqualTo(UPDATED_DESCUENTO);
        assertThat(testDetalleOrden.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testDetalleOrden.getTotal()).isEqualTo(UPDATED_TOTAL);
    }

    @Test
    @Transactional
    public void updateNonExistingDetalleOrden() throws Exception {
        int databaseSizeBeforeUpdate = detalleOrdenRepository.findAll().size();

        // Create the DetalleOrden
        DetalleOrdenDTO detalleOrdenDTO = detalleOrdenMapper.toDto(detalleOrden);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDetalleOrdenMockMvc.perform(put("/api/detalle-ordens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalleOrdenDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DetalleOrden in the database
        List<DetalleOrden> detalleOrdenList = detalleOrdenRepository.findAll();
        assertThat(detalleOrdenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDetalleOrden() throws Exception {
        // Initialize the database
        detalleOrdenRepository.saveAndFlush(detalleOrden);

        int databaseSizeBeforeDelete = detalleOrdenRepository.findAll().size();

        // Get the detalleOrden
        restDetalleOrdenMockMvc.perform(delete("/api/detalle-ordens/{id}", detalleOrden.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DetalleOrden> detalleOrdenList = detalleOrdenRepository.findAll();
        assertThat(detalleOrdenList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetalleOrden.class);
        DetalleOrden detalleOrden1 = new DetalleOrden();
        detalleOrden1.setId(1L);
        DetalleOrden detalleOrden2 = new DetalleOrden();
        detalleOrden2.setId(detalleOrden1.getId());
        assertThat(detalleOrden1).isEqualTo(detalleOrden2);
        detalleOrden2.setId(2L);
        assertThat(detalleOrden1).isNotEqualTo(detalleOrden2);
        detalleOrden1.setId(null);
        assertThat(detalleOrden1).isNotEqualTo(detalleOrden2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetalleOrdenDTO.class);
        DetalleOrdenDTO detalleOrdenDTO1 = new DetalleOrdenDTO();
        detalleOrdenDTO1.setId(1L);
        DetalleOrdenDTO detalleOrdenDTO2 = new DetalleOrdenDTO();
        assertThat(detalleOrdenDTO1).isNotEqualTo(detalleOrdenDTO2);
        detalleOrdenDTO2.setId(detalleOrdenDTO1.getId());
        assertThat(detalleOrdenDTO1).isEqualTo(detalleOrdenDTO2);
        detalleOrdenDTO2.setId(2L);
        assertThat(detalleOrdenDTO1).isNotEqualTo(detalleOrdenDTO2);
        detalleOrdenDTO1.setId(null);
        assertThat(detalleOrdenDTO1).isNotEqualTo(detalleOrdenDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(detalleOrdenMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(detalleOrdenMapper.fromId(null)).isNull();
    }
}
