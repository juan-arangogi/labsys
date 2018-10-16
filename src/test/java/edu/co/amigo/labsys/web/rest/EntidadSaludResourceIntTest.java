package edu.co.amigo.labsys.web.rest;

import edu.co.amigo.labsys.LabsysApp;

import edu.co.amigo.labsys.domain.EntidadSalud;
import edu.co.amigo.labsys.repository.EntidadSaludRepository;
import edu.co.amigo.labsys.service.EntidadSaludService;
import edu.co.amigo.labsys.service.dto.EntidadSaludDTO;
import edu.co.amigo.labsys.service.mapper.EntidadSaludMapper;
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

import edu.co.amigo.labsys.domain.enumeration.TipoDocumento;
/**
 * Test class for the EntidadSaludResource REST controller.
 *
 * @see EntidadSaludResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LabsysApp.class)
public class EntidadSaludResourceIntTest {

    private static final TipoDocumento DEFAULT_TIPO_DOCUMENTO = TipoDocumento.ASI;
    private static final TipoDocumento UPDATED_TIPO_DOCUMENTO = TipoDocumento.MSI;

    private static final String DEFAULT_DOCUMENTO = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_RAZON_SOCIAL = "AAAAAAAAAA";
    private static final String UPDATED_RAZON_SOCIAL = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

    @Autowired
    private EntidadSaludRepository entidadSaludRepository;

    @Autowired
    private EntidadSaludMapper entidadSaludMapper;
    
    @Autowired
    private EntidadSaludService entidadSaludService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEntidadSaludMockMvc;

    private EntidadSalud entidadSalud;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EntidadSaludResource entidadSaludResource = new EntidadSaludResource(entidadSaludService);
        this.restEntidadSaludMockMvc = MockMvcBuilders.standaloneSetup(entidadSaludResource)
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
    public static EntidadSalud createEntity(EntityManager em) {
        EntidadSalud entidadSalud = new EntidadSalud()
            .tipoDocumento(DEFAULT_TIPO_DOCUMENTO)
            .documento(DEFAULT_DOCUMENTO)
            .razonSocial(DEFAULT_RAZON_SOCIAL)
            .direccion(DEFAULT_DIRECCION)
            .telefono(DEFAULT_TELEFONO);
        return entidadSalud;
    }

    @Before
    public void initTest() {
        entidadSalud = createEntity(em);
    }

    @Test
    @Transactional
    public void createEntidadSalud() throws Exception {
        int databaseSizeBeforeCreate = entidadSaludRepository.findAll().size();

        // Create the EntidadSalud
        EntidadSaludDTO entidadSaludDTO = entidadSaludMapper.toDto(entidadSalud);
        restEntidadSaludMockMvc.perform(post("/api/entidad-saluds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entidadSaludDTO)))
            .andExpect(status().isCreated());

        // Validate the EntidadSalud in the database
        List<EntidadSalud> entidadSaludList = entidadSaludRepository.findAll();
        assertThat(entidadSaludList).hasSize(databaseSizeBeforeCreate + 1);
        EntidadSalud testEntidadSalud = entidadSaludList.get(entidadSaludList.size() - 1);
        assertThat(testEntidadSalud.getTipoDocumento()).isEqualTo(DEFAULT_TIPO_DOCUMENTO);
        assertThat(testEntidadSalud.getDocumento()).isEqualTo(DEFAULT_DOCUMENTO);
        assertThat(testEntidadSalud.getRazonSocial()).isEqualTo(DEFAULT_RAZON_SOCIAL);
        assertThat(testEntidadSalud.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
        assertThat(testEntidadSalud.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
    }

    @Test
    @Transactional
    public void createEntidadSaludWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = entidadSaludRepository.findAll().size();

        // Create the EntidadSalud with an existing ID
        entidadSalud.setId(1L);
        EntidadSaludDTO entidadSaludDTO = entidadSaludMapper.toDto(entidadSalud);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntidadSaludMockMvc.perform(post("/api/entidad-saluds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entidadSaludDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EntidadSalud in the database
        List<EntidadSalud> entidadSaludList = entidadSaludRepository.findAll();
        assertThat(entidadSaludList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEntidadSaluds() throws Exception {
        // Initialize the database
        entidadSaludRepository.saveAndFlush(entidadSalud);

        // Get all the entidadSaludList
        restEntidadSaludMockMvc.perform(get("/api/entidad-saluds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entidadSalud.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoDocumento").value(hasItem(DEFAULT_TIPO_DOCUMENTO.toString())))
            .andExpect(jsonPath("$.[*].documento").value(hasItem(DEFAULT_DOCUMENTO.toString())))
            .andExpect(jsonPath("$.[*].razonSocial").value(hasItem(DEFAULT_RAZON_SOCIAL.toString())))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION.toString())))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO.toString())));
    }
    
    @Test
    @Transactional
    public void getEntidadSalud() throws Exception {
        // Initialize the database
        entidadSaludRepository.saveAndFlush(entidadSalud);

        // Get the entidadSalud
        restEntidadSaludMockMvc.perform(get("/api/entidad-saluds/{id}", entidadSalud.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(entidadSalud.getId().intValue()))
            .andExpect(jsonPath("$.tipoDocumento").value(DEFAULT_TIPO_DOCUMENTO.toString()))
            .andExpect(jsonPath("$.documento").value(DEFAULT_DOCUMENTO.toString()))
            .andExpect(jsonPath("$.razonSocial").value(DEFAULT_RAZON_SOCIAL.toString()))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION.toString()))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEntidadSalud() throws Exception {
        // Get the entidadSalud
        restEntidadSaludMockMvc.perform(get("/api/entidad-saluds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEntidadSalud() throws Exception {
        // Initialize the database
        entidadSaludRepository.saveAndFlush(entidadSalud);

        int databaseSizeBeforeUpdate = entidadSaludRepository.findAll().size();

        // Update the entidadSalud
        EntidadSalud updatedEntidadSalud = entidadSaludRepository.findById(entidadSalud.getId()).get();
        // Disconnect from session so that the updates on updatedEntidadSalud are not directly saved in db
        em.detach(updatedEntidadSalud);
        updatedEntidadSalud
            .tipoDocumento(UPDATED_TIPO_DOCUMENTO)
            .documento(UPDATED_DOCUMENTO)
            .razonSocial(UPDATED_RAZON_SOCIAL)
            .direccion(UPDATED_DIRECCION)
            .telefono(UPDATED_TELEFONO);
        EntidadSaludDTO entidadSaludDTO = entidadSaludMapper.toDto(updatedEntidadSalud);

        restEntidadSaludMockMvc.perform(put("/api/entidad-saluds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entidadSaludDTO)))
            .andExpect(status().isOk());

        // Validate the EntidadSalud in the database
        List<EntidadSalud> entidadSaludList = entidadSaludRepository.findAll();
        assertThat(entidadSaludList).hasSize(databaseSizeBeforeUpdate);
        EntidadSalud testEntidadSalud = entidadSaludList.get(entidadSaludList.size() - 1);
        assertThat(testEntidadSalud.getTipoDocumento()).isEqualTo(UPDATED_TIPO_DOCUMENTO);
        assertThat(testEntidadSalud.getDocumento()).isEqualTo(UPDATED_DOCUMENTO);
        assertThat(testEntidadSalud.getRazonSocial()).isEqualTo(UPDATED_RAZON_SOCIAL);
        assertThat(testEntidadSalud.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testEntidadSalud.getTelefono()).isEqualTo(UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    public void updateNonExistingEntidadSalud() throws Exception {
        int databaseSizeBeforeUpdate = entidadSaludRepository.findAll().size();

        // Create the EntidadSalud
        EntidadSaludDTO entidadSaludDTO = entidadSaludMapper.toDto(entidadSalud);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntidadSaludMockMvc.perform(put("/api/entidad-saluds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entidadSaludDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EntidadSalud in the database
        List<EntidadSalud> entidadSaludList = entidadSaludRepository.findAll();
        assertThat(entidadSaludList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEntidadSalud() throws Exception {
        // Initialize the database
        entidadSaludRepository.saveAndFlush(entidadSalud);

        int databaseSizeBeforeDelete = entidadSaludRepository.findAll().size();

        // Get the entidadSalud
        restEntidadSaludMockMvc.perform(delete("/api/entidad-saluds/{id}", entidadSalud.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EntidadSalud> entidadSaludList = entidadSaludRepository.findAll();
        assertThat(entidadSaludList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EntidadSalud.class);
        EntidadSalud entidadSalud1 = new EntidadSalud();
        entidadSalud1.setId(1L);
        EntidadSalud entidadSalud2 = new EntidadSalud();
        entidadSalud2.setId(entidadSalud1.getId());
        assertThat(entidadSalud1).isEqualTo(entidadSalud2);
        entidadSalud2.setId(2L);
        assertThat(entidadSalud1).isNotEqualTo(entidadSalud2);
        entidadSalud1.setId(null);
        assertThat(entidadSalud1).isNotEqualTo(entidadSalud2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EntidadSaludDTO.class);
        EntidadSaludDTO entidadSaludDTO1 = new EntidadSaludDTO();
        entidadSaludDTO1.setId(1L);
        EntidadSaludDTO entidadSaludDTO2 = new EntidadSaludDTO();
        assertThat(entidadSaludDTO1).isNotEqualTo(entidadSaludDTO2);
        entidadSaludDTO2.setId(entidadSaludDTO1.getId());
        assertThat(entidadSaludDTO1).isEqualTo(entidadSaludDTO2);
        entidadSaludDTO2.setId(2L);
        assertThat(entidadSaludDTO1).isNotEqualTo(entidadSaludDTO2);
        entidadSaludDTO1.setId(null);
        assertThat(entidadSaludDTO1).isNotEqualTo(entidadSaludDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(entidadSaludMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(entidadSaludMapper.fromId(null)).isNull();
    }
}
