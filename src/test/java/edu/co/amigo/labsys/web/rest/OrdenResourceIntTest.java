package edu.co.amigo.labsys.web.rest;

import edu.co.amigo.labsys.LabsysApp;

import edu.co.amigo.labsys.domain.Orden;
import edu.co.amigo.labsys.repository.OrdenRepository;
import edu.co.amigo.labsys.service.OrdenService;
import edu.co.amigo.labsys.service.dto.OrdenDTO;
import edu.co.amigo.labsys.service.mapper.OrdenMapper;
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

import edu.co.amigo.labsys.domain.enumeration.EstadoOrden;
/**
 * Test class for the OrdenResource REST controller.
 *
 * @see OrdenResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LabsysApp.class)
public class OrdenResourceIntTest {

    private static final Double DEFAULT_TOTAL = 1D;
    private static final Double UPDATED_TOTAL = 2D;

    private static final EstadoOrden DEFAULT_ESTADO = EstadoOrden.REGISTRADA;
    private static final EstadoOrden UPDATED_ESTADO = EstadoOrden.EN_PROCESO;

    @Autowired
    private OrdenRepository ordenRepository;

    @Autowired
    private OrdenMapper ordenMapper;
    
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

    private MockMvc restOrdenMockMvc;

    private Orden orden;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrdenResource ordenResource = new OrdenResource(ordenService);
        this.restOrdenMockMvc = MockMvcBuilders.standaloneSetup(ordenResource)
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
    public static Orden createEntity(EntityManager em) {
        Orden orden = new Orden()
            .total(DEFAULT_TOTAL)
            .estado(DEFAULT_ESTADO);
        return orden;
    }

    @Before
    public void initTest() {
        orden = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrden() throws Exception {
        int databaseSizeBeforeCreate = ordenRepository.findAll().size();

        // Create the Orden
        OrdenDTO ordenDTO = ordenMapper.toDto(orden);
        restOrdenMockMvc.perform(post("/api/ordens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordenDTO)))
            .andExpect(status().isCreated());

        // Validate the Orden in the database
        List<Orden> ordenList = ordenRepository.findAll();
        assertThat(ordenList).hasSize(databaseSizeBeforeCreate + 1);
        Orden testOrden = ordenList.get(ordenList.size() - 1);
        assertThat(testOrden.getTotal()).isEqualTo(DEFAULT_TOTAL);
        assertThat(testOrden.getEstado()).isEqualTo(DEFAULT_ESTADO);
    }

    @Test
    @Transactional
    public void createOrdenWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ordenRepository.findAll().size();

        // Create the Orden with an existing ID
        orden.setId(1L);
        OrdenDTO ordenDTO = ordenMapper.toDto(orden);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrdenMockMvc.perform(post("/api/ordens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordenDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Orden in the database
        List<Orden> ordenList = ordenRepository.findAll();
        assertThat(ordenList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOrdens() throws Exception {
        // Initialize the database
        ordenRepository.saveAndFlush(orden);

        // Get all the ordenList
        restOrdenMockMvc.perform(get("/api/ordens?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orden.getId().intValue())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())));
    }
    
    @Test
    @Transactional
    public void getOrden() throws Exception {
        // Initialize the database
        ordenRepository.saveAndFlush(orden);

        // Get the orden
        restOrdenMockMvc.perform(get("/api/ordens/{id}", orden.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(orden.getId().intValue()))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL.doubleValue()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOrden() throws Exception {
        // Get the orden
        restOrdenMockMvc.perform(get("/api/ordens/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrden() throws Exception {
        // Initialize the database
        ordenRepository.saveAndFlush(orden);

        int databaseSizeBeforeUpdate = ordenRepository.findAll().size();

        // Update the orden
        Orden updatedOrden = ordenRepository.findById(orden.getId()).get();
        // Disconnect from session so that the updates on updatedOrden are not directly saved in db
        em.detach(updatedOrden);
        updatedOrden
            .total(UPDATED_TOTAL)
            .estado(UPDATED_ESTADO);
        OrdenDTO ordenDTO = ordenMapper.toDto(updatedOrden);

        restOrdenMockMvc.perform(put("/api/ordens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordenDTO)))
            .andExpect(status().isOk());

        // Validate the Orden in the database
        List<Orden> ordenList = ordenRepository.findAll();
        assertThat(ordenList).hasSize(databaseSizeBeforeUpdate);
        Orden testOrden = ordenList.get(ordenList.size() - 1);
        assertThat(testOrden.getTotal()).isEqualTo(UPDATED_TOTAL);
        assertThat(testOrden.getEstado()).isEqualTo(UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void updateNonExistingOrden() throws Exception {
        int databaseSizeBeforeUpdate = ordenRepository.findAll().size();

        // Create the Orden
        OrdenDTO ordenDTO = ordenMapper.toDto(orden);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdenMockMvc.perform(put("/api/ordens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordenDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Orden in the database
        List<Orden> ordenList = ordenRepository.findAll();
        assertThat(ordenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrden() throws Exception {
        // Initialize the database
        ordenRepository.saveAndFlush(orden);

        int databaseSizeBeforeDelete = ordenRepository.findAll().size();

        // Get the orden
        restOrdenMockMvc.perform(delete("/api/ordens/{id}", orden.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Orden> ordenList = ordenRepository.findAll();
        assertThat(ordenList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Orden.class);
        Orden orden1 = new Orden();
        orden1.setId(1L);
        Orden orden2 = new Orden();
        orden2.setId(orden1.getId());
        assertThat(orden1).isEqualTo(orden2);
        orden2.setId(2L);
        assertThat(orden1).isNotEqualTo(orden2);
        orden1.setId(null);
        assertThat(orden1).isNotEqualTo(orden2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdenDTO.class);
        OrdenDTO ordenDTO1 = new OrdenDTO();
        ordenDTO1.setId(1L);
        OrdenDTO ordenDTO2 = new OrdenDTO();
        assertThat(ordenDTO1).isNotEqualTo(ordenDTO2);
        ordenDTO2.setId(ordenDTO1.getId());
        assertThat(ordenDTO1).isEqualTo(ordenDTO2);
        ordenDTO2.setId(2L);
        assertThat(ordenDTO1).isNotEqualTo(ordenDTO2);
        ordenDTO1.setId(null);
        assertThat(ordenDTO1).isNotEqualTo(ordenDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(ordenMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(ordenMapper.fromId(null)).isNull();
    }
}
