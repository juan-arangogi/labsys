package edu.co.amigo.labsys.web.rest;

import edu.co.amigo.labsys.LabsysApp;

import edu.co.amigo.labsys.domain.Recipiente;
import edu.co.amigo.labsys.repository.RecipienteRepository;
import edu.co.amigo.labsys.service.RecipienteService;
import edu.co.amigo.labsys.service.dto.RecipienteDTO;
import edu.co.amigo.labsys.service.mapper.RecipienteMapper;
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
 * Test class for the RecipienteResource REST controller.
 *
 * @see RecipienteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LabsysApp.class)
public class RecipienteResourceIntTest {

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Autowired
    private RecipienteRepository recipienteRepository;

    @Autowired
    private RecipienteMapper recipienteMapper;
    
    @Autowired
    private RecipienteService recipienteService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRecipienteMockMvc;

    private Recipiente recipiente;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RecipienteResource recipienteResource = new RecipienteResource(recipienteService);
        this.restRecipienteMockMvc = MockMvcBuilders.standaloneSetup(recipienteResource)
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
    public static Recipiente createEntity(EntityManager em) {
        Recipiente recipiente = new Recipiente()
            .descripcion(DEFAULT_DESCRIPCION);
        return recipiente;
    }

    @Before
    public void initTest() {
        recipiente = createEntity(em);
    }

    @Test
    @Transactional
    public void createRecipiente() throws Exception {
        int databaseSizeBeforeCreate = recipienteRepository.findAll().size();

        // Create the Recipiente
        RecipienteDTO recipienteDTO = recipienteMapper.toDto(recipiente);
        restRecipienteMockMvc.perform(post("/api/recipientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipienteDTO)))
            .andExpect(status().isCreated());

        // Validate the Recipiente in the database
        List<Recipiente> recipienteList = recipienteRepository.findAll();
        assertThat(recipienteList).hasSize(databaseSizeBeforeCreate + 1);
        Recipiente testRecipiente = recipienteList.get(recipienteList.size() - 1);
        assertThat(testRecipiente.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    public void createRecipienteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = recipienteRepository.findAll().size();

        // Create the Recipiente with an existing ID
        recipiente.setId(1L);
        RecipienteDTO recipienteDTO = recipienteMapper.toDto(recipiente);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecipienteMockMvc.perform(post("/api/recipientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipienteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Recipiente in the database
        List<Recipiente> recipienteList = recipienteRepository.findAll();
        assertThat(recipienteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRecipientes() throws Exception {
        // Initialize the database
        recipienteRepository.saveAndFlush(recipiente);

        // Get all the recipienteList
        restRecipienteMockMvc.perform(get("/api/recipientes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recipiente.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())));
    }
    
    @Test
    @Transactional
    public void getRecipiente() throws Exception {
        // Initialize the database
        recipienteRepository.saveAndFlush(recipiente);

        // Get the recipiente
        restRecipienteMockMvc.perform(get("/api/recipientes/{id}", recipiente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(recipiente.getId().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRecipiente() throws Exception {
        // Get the recipiente
        restRecipienteMockMvc.perform(get("/api/recipientes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRecipiente() throws Exception {
        // Initialize the database
        recipienteRepository.saveAndFlush(recipiente);

        int databaseSizeBeforeUpdate = recipienteRepository.findAll().size();

        // Update the recipiente
        Recipiente updatedRecipiente = recipienteRepository.findById(recipiente.getId()).get();
        // Disconnect from session so that the updates on updatedRecipiente are not directly saved in db
        em.detach(updatedRecipiente);
        updatedRecipiente
            .descripcion(UPDATED_DESCRIPCION);
        RecipienteDTO recipienteDTO = recipienteMapper.toDto(updatedRecipiente);

        restRecipienteMockMvc.perform(put("/api/recipientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipienteDTO)))
            .andExpect(status().isOk());

        // Validate the Recipiente in the database
        List<Recipiente> recipienteList = recipienteRepository.findAll();
        assertThat(recipienteList).hasSize(databaseSizeBeforeUpdate);
        Recipiente testRecipiente = recipienteList.get(recipienteList.size() - 1);
        assertThat(testRecipiente.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void updateNonExistingRecipiente() throws Exception {
        int databaseSizeBeforeUpdate = recipienteRepository.findAll().size();

        // Create the Recipiente
        RecipienteDTO recipienteDTO = recipienteMapper.toDto(recipiente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecipienteMockMvc.perform(put("/api/recipientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipienteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Recipiente in the database
        List<Recipiente> recipienteList = recipienteRepository.findAll();
        assertThat(recipienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRecipiente() throws Exception {
        // Initialize the database
        recipienteRepository.saveAndFlush(recipiente);

        int databaseSizeBeforeDelete = recipienteRepository.findAll().size();

        // Get the recipiente
        restRecipienteMockMvc.perform(delete("/api/recipientes/{id}", recipiente.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Recipiente> recipienteList = recipienteRepository.findAll();
        assertThat(recipienteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Recipiente.class);
        Recipiente recipiente1 = new Recipiente();
        recipiente1.setId(1L);
        Recipiente recipiente2 = new Recipiente();
        recipiente2.setId(recipiente1.getId());
        assertThat(recipiente1).isEqualTo(recipiente2);
        recipiente2.setId(2L);
        assertThat(recipiente1).isNotEqualTo(recipiente2);
        recipiente1.setId(null);
        assertThat(recipiente1).isNotEqualTo(recipiente2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecipienteDTO.class);
        RecipienteDTO recipienteDTO1 = new RecipienteDTO();
        recipienteDTO1.setId(1L);
        RecipienteDTO recipienteDTO2 = new RecipienteDTO();
        assertThat(recipienteDTO1).isNotEqualTo(recipienteDTO2);
        recipienteDTO2.setId(recipienteDTO1.getId());
        assertThat(recipienteDTO1).isEqualTo(recipienteDTO2);
        recipienteDTO2.setId(2L);
        assertThat(recipienteDTO1).isNotEqualTo(recipienteDTO2);
        recipienteDTO1.setId(null);
        assertThat(recipienteDTO1).isNotEqualTo(recipienteDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(recipienteMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(recipienteMapper.fromId(null)).isNull();
    }
}
