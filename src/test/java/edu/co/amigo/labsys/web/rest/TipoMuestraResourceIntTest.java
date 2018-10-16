package edu.co.amigo.labsys.web.rest;

import edu.co.amigo.labsys.LabsysApp;

import edu.co.amigo.labsys.domain.TipoMuestra;
import edu.co.amigo.labsys.repository.TipoMuestraRepository;
import edu.co.amigo.labsys.service.TipoMuestraService;
import edu.co.amigo.labsys.service.dto.TipoMuestraDTO;
import edu.co.amigo.labsys.service.mapper.TipoMuestraMapper;
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
 * Test class for the TipoMuestraResource REST controller.
 *
 * @see TipoMuestraResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LabsysApp.class)
public class TipoMuestraResourceIntTest {

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Autowired
    private TipoMuestraRepository tipoMuestraRepository;

    @Autowired
    private TipoMuestraMapper tipoMuestraMapper;
    
    @Autowired
    private TipoMuestraService tipoMuestraService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTipoMuestraMockMvc;

    private TipoMuestra tipoMuestra;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoMuestraResource tipoMuestraResource = new TipoMuestraResource(tipoMuestraService);
        this.restTipoMuestraMockMvc = MockMvcBuilders.standaloneSetup(tipoMuestraResource)
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
    public static TipoMuestra createEntity(EntityManager em) {
        TipoMuestra tipoMuestra = new TipoMuestra()
            .descripcion(DEFAULT_DESCRIPCION);
        return tipoMuestra;
    }

    @Before
    public void initTest() {
        tipoMuestra = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoMuestra() throws Exception {
        int databaseSizeBeforeCreate = tipoMuestraRepository.findAll().size();

        // Create the TipoMuestra
        TipoMuestraDTO tipoMuestraDTO = tipoMuestraMapper.toDto(tipoMuestra);
        restTipoMuestraMockMvc.perform(post("/api/tipo-muestras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoMuestraDTO)))
            .andExpect(status().isCreated());

        // Validate the TipoMuestra in the database
        List<TipoMuestra> tipoMuestraList = tipoMuestraRepository.findAll();
        assertThat(tipoMuestraList).hasSize(databaseSizeBeforeCreate + 1);
        TipoMuestra testTipoMuestra = tipoMuestraList.get(tipoMuestraList.size() - 1);
        assertThat(testTipoMuestra.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    public void createTipoMuestraWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoMuestraRepository.findAll().size();

        // Create the TipoMuestra with an existing ID
        tipoMuestra.setId(1L);
        TipoMuestraDTO tipoMuestraDTO = tipoMuestraMapper.toDto(tipoMuestra);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoMuestraMockMvc.perform(post("/api/tipo-muestras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoMuestraDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoMuestra in the database
        List<TipoMuestra> tipoMuestraList = tipoMuestraRepository.findAll();
        assertThat(tipoMuestraList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTipoMuestras() throws Exception {
        // Initialize the database
        tipoMuestraRepository.saveAndFlush(tipoMuestra);

        // Get all the tipoMuestraList
        restTipoMuestraMockMvc.perform(get("/api/tipo-muestras?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoMuestra.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())));
    }
    
    @Test
    @Transactional
    public void getTipoMuestra() throws Exception {
        // Initialize the database
        tipoMuestraRepository.saveAndFlush(tipoMuestra);

        // Get the tipoMuestra
        restTipoMuestraMockMvc.perform(get("/api/tipo-muestras/{id}", tipoMuestra.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoMuestra.getId().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTipoMuestra() throws Exception {
        // Get the tipoMuestra
        restTipoMuestraMockMvc.perform(get("/api/tipo-muestras/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoMuestra() throws Exception {
        // Initialize the database
        tipoMuestraRepository.saveAndFlush(tipoMuestra);

        int databaseSizeBeforeUpdate = tipoMuestraRepository.findAll().size();

        // Update the tipoMuestra
        TipoMuestra updatedTipoMuestra = tipoMuestraRepository.findById(tipoMuestra.getId()).get();
        // Disconnect from session so that the updates on updatedTipoMuestra are not directly saved in db
        em.detach(updatedTipoMuestra);
        updatedTipoMuestra
            .descripcion(UPDATED_DESCRIPCION);
        TipoMuestraDTO tipoMuestraDTO = tipoMuestraMapper.toDto(updatedTipoMuestra);

        restTipoMuestraMockMvc.perform(put("/api/tipo-muestras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoMuestraDTO)))
            .andExpect(status().isOk());

        // Validate the TipoMuestra in the database
        List<TipoMuestra> tipoMuestraList = tipoMuestraRepository.findAll();
        assertThat(tipoMuestraList).hasSize(databaseSizeBeforeUpdate);
        TipoMuestra testTipoMuestra = tipoMuestraList.get(tipoMuestraList.size() - 1);
        assertThat(testTipoMuestra.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoMuestra() throws Exception {
        int databaseSizeBeforeUpdate = tipoMuestraRepository.findAll().size();

        // Create the TipoMuestra
        TipoMuestraDTO tipoMuestraDTO = tipoMuestraMapper.toDto(tipoMuestra);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoMuestraMockMvc.perform(put("/api/tipo-muestras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoMuestraDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoMuestra in the database
        List<TipoMuestra> tipoMuestraList = tipoMuestraRepository.findAll();
        assertThat(tipoMuestraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTipoMuestra() throws Exception {
        // Initialize the database
        tipoMuestraRepository.saveAndFlush(tipoMuestra);

        int databaseSizeBeforeDelete = tipoMuestraRepository.findAll().size();

        // Get the tipoMuestra
        restTipoMuestraMockMvc.perform(delete("/api/tipo-muestras/{id}", tipoMuestra.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TipoMuestra> tipoMuestraList = tipoMuestraRepository.findAll();
        assertThat(tipoMuestraList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoMuestra.class);
        TipoMuestra tipoMuestra1 = new TipoMuestra();
        tipoMuestra1.setId(1L);
        TipoMuestra tipoMuestra2 = new TipoMuestra();
        tipoMuestra2.setId(tipoMuestra1.getId());
        assertThat(tipoMuestra1).isEqualTo(tipoMuestra2);
        tipoMuestra2.setId(2L);
        assertThat(tipoMuestra1).isNotEqualTo(tipoMuestra2);
        tipoMuestra1.setId(null);
        assertThat(tipoMuestra1).isNotEqualTo(tipoMuestra2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoMuestraDTO.class);
        TipoMuestraDTO tipoMuestraDTO1 = new TipoMuestraDTO();
        tipoMuestraDTO1.setId(1L);
        TipoMuestraDTO tipoMuestraDTO2 = new TipoMuestraDTO();
        assertThat(tipoMuestraDTO1).isNotEqualTo(tipoMuestraDTO2);
        tipoMuestraDTO2.setId(tipoMuestraDTO1.getId());
        assertThat(tipoMuestraDTO1).isEqualTo(tipoMuestraDTO2);
        tipoMuestraDTO2.setId(2L);
        assertThat(tipoMuestraDTO1).isNotEqualTo(tipoMuestraDTO2);
        tipoMuestraDTO1.setId(null);
        assertThat(tipoMuestraDTO1).isNotEqualTo(tipoMuestraDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tipoMuestraMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tipoMuestraMapper.fromId(null)).isNull();
    }
}
