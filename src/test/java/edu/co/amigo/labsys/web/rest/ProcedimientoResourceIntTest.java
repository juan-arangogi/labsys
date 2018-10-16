package edu.co.amigo.labsys.web.rest;

import edu.co.amigo.labsys.LabsysApp;

import edu.co.amigo.labsys.domain.Procedimiento;
import edu.co.amigo.labsys.repository.ProcedimientoRepository;
import edu.co.amigo.labsys.service.ProcedimientoService;
import edu.co.amigo.labsys.service.dto.ProcedimientoDTO;
import edu.co.amigo.labsys.service.mapper.ProcedimientoMapper;
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
 * Test class for the ProcedimientoResource REST controller.
 *
 * @see ProcedimientoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LabsysApp.class)
public class ProcedimientoResourceIntTest {

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Double DEFAULT_PRECIO_UNITARIO = 1D;
    private static final Double UPDATED_PRECIO_UNITARIO = 2D;

    @Autowired
    private ProcedimientoRepository procedimientoRepository;

    @Autowired
    private ProcedimientoMapper procedimientoMapper;
    
    @Autowired
    private ProcedimientoService procedimientoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProcedimientoMockMvc;

    private Procedimiento procedimiento;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProcedimientoResource procedimientoResource = new ProcedimientoResource(procedimientoService);
        this.restProcedimientoMockMvc = MockMvcBuilders.standaloneSetup(procedimientoResource)
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
    public static Procedimiento createEntity(EntityManager em) {
        Procedimiento procedimiento = new Procedimiento()
            .descripcion(DEFAULT_DESCRIPCION)
            .precioUnitario(DEFAULT_PRECIO_UNITARIO);
        return procedimiento;
    }

    @Before
    public void initTest() {
        procedimiento = createEntity(em);
    }

    @Test
    @Transactional
    public void createProcedimiento() throws Exception {
        int databaseSizeBeforeCreate = procedimientoRepository.findAll().size();

        // Create the Procedimiento
        ProcedimientoDTO procedimientoDTO = procedimientoMapper.toDto(procedimiento);
        restProcedimientoMockMvc.perform(post("/api/procedimientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(procedimientoDTO)))
            .andExpect(status().isCreated());

        // Validate the Procedimiento in the database
        List<Procedimiento> procedimientoList = procedimientoRepository.findAll();
        assertThat(procedimientoList).hasSize(databaseSizeBeforeCreate + 1);
        Procedimiento testProcedimiento = procedimientoList.get(procedimientoList.size() - 1);
        assertThat(testProcedimiento.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testProcedimiento.getPrecioUnitario()).isEqualTo(DEFAULT_PRECIO_UNITARIO);
    }

    @Test
    @Transactional
    public void createProcedimientoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = procedimientoRepository.findAll().size();

        // Create the Procedimiento with an existing ID
        procedimiento.setId(1L);
        ProcedimientoDTO procedimientoDTO = procedimientoMapper.toDto(procedimiento);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProcedimientoMockMvc.perform(post("/api/procedimientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(procedimientoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Procedimiento in the database
        List<Procedimiento> procedimientoList = procedimientoRepository.findAll();
        assertThat(procedimientoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllProcedimientos() throws Exception {
        // Initialize the database
        procedimientoRepository.saveAndFlush(procedimiento);

        // Get all the procedimientoList
        restProcedimientoMockMvc.perform(get("/api/procedimientos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(procedimiento.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].precioUnitario").value(hasItem(DEFAULT_PRECIO_UNITARIO.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getProcedimiento() throws Exception {
        // Initialize the database
        procedimientoRepository.saveAndFlush(procedimiento);

        // Get the procedimiento
        restProcedimientoMockMvc.perform(get("/api/procedimientos/{id}", procedimiento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(procedimiento.getId().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.precioUnitario").value(DEFAULT_PRECIO_UNITARIO.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingProcedimiento() throws Exception {
        // Get the procedimiento
        restProcedimientoMockMvc.perform(get("/api/procedimientos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProcedimiento() throws Exception {
        // Initialize the database
        procedimientoRepository.saveAndFlush(procedimiento);

        int databaseSizeBeforeUpdate = procedimientoRepository.findAll().size();

        // Update the procedimiento
        Procedimiento updatedProcedimiento = procedimientoRepository.findById(procedimiento.getId()).get();
        // Disconnect from session so that the updates on updatedProcedimiento are not directly saved in db
        em.detach(updatedProcedimiento);
        updatedProcedimiento
            .descripcion(UPDATED_DESCRIPCION)
            .precioUnitario(UPDATED_PRECIO_UNITARIO);
        ProcedimientoDTO procedimientoDTO = procedimientoMapper.toDto(updatedProcedimiento);

        restProcedimientoMockMvc.perform(put("/api/procedimientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(procedimientoDTO)))
            .andExpect(status().isOk());

        // Validate the Procedimiento in the database
        List<Procedimiento> procedimientoList = procedimientoRepository.findAll();
        assertThat(procedimientoList).hasSize(databaseSizeBeforeUpdate);
        Procedimiento testProcedimiento = procedimientoList.get(procedimientoList.size() - 1);
        assertThat(testProcedimiento.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testProcedimiento.getPrecioUnitario()).isEqualTo(UPDATED_PRECIO_UNITARIO);
    }

    @Test
    @Transactional
    public void updateNonExistingProcedimiento() throws Exception {
        int databaseSizeBeforeUpdate = procedimientoRepository.findAll().size();

        // Create the Procedimiento
        ProcedimientoDTO procedimientoDTO = procedimientoMapper.toDto(procedimiento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProcedimientoMockMvc.perform(put("/api/procedimientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(procedimientoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Procedimiento in the database
        List<Procedimiento> procedimientoList = procedimientoRepository.findAll();
        assertThat(procedimientoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProcedimiento() throws Exception {
        // Initialize the database
        procedimientoRepository.saveAndFlush(procedimiento);

        int databaseSizeBeforeDelete = procedimientoRepository.findAll().size();

        // Get the procedimiento
        restProcedimientoMockMvc.perform(delete("/api/procedimientos/{id}", procedimiento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Procedimiento> procedimientoList = procedimientoRepository.findAll();
        assertThat(procedimientoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Procedimiento.class);
        Procedimiento procedimiento1 = new Procedimiento();
        procedimiento1.setId(1L);
        Procedimiento procedimiento2 = new Procedimiento();
        procedimiento2.setId(procedimiento1.getId());
        assertThat(procedimiento1).isEqualTo(procedimiento2);
        procedimiento2.setId(2L);
        assertThat(procedimiento1).isNotEqualTo(procedimiento2);
        procedimiento1.setId(null);
        assertThat(procedimiento1).isNotEqualTo(procedimiento2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProcedimientoDTO.class);
        ProcedimientoDTO procedimientoDTO1 = new ProcedimientoDTO();
        procedimientoDTO1.setId(1L);
        ProcedimientoDTO procedimientoDTO2 = new ProcedimientoDTO();
        assertThat(procedimientoDTO1).isNotEqualTo(procedimientoDTO2);
        procedimientoDTO2.setId(procedimientoDTO1.getId());
        assertThat(procedimientoDTO1).isEqualTo(procedimientoDTO2);
        procedimientoDTO2.setId(2L);
        assertThat(procedimientoDTO1).isNotEqualTo(procedimientoDTO2);
        procedimientoDTO1.setId(null);
        assertThat(procedimientoDTO1).isNotEqualTo(procedimientoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(procedimientoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(procedimientoMapper.fromId(null)).isNull();
    }
}
