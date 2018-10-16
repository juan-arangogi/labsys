package edu.co.amigo.labsys.web.rest;

import edu.co.amigo.labsys.LabsysApp;

import edu.co.amigo.labsys.domain.Medico;
import edu.co.amigo.labsys.repository.MedicoRepository;
import edu.co.amigo.labsys.service.MedicoService;
import edu.co.amigo.labsys.service.dto.MedicoDTO;
import edu.co.amigo.labsys.service.mapper.MedicoMapper;
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
import edu.co.amigo.labsys.domain.enumeration.Especialidad;
/**
 * Test class for the MedicoResource REST controller.
 *
 * @see MedicoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LabsysApp.class)
public class MedicoResourceIntTest {

    private static final TipoDocumento DEFAULT_TIPO_DOCUMENTO = TipoDocumento.ASI;
    private static final TipoDocumento UPDATED_TIPO_DOCUMENTO = TipoDocumento.MSI;

    private static final String DEFAULT_DOCUMENTO = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRES = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRES = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDOS = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDOS = "BBBBBBBBBB";

    private static final Especialidad DEFAULT_ESPECIALIDAD = Especialidad.MEDICINA_GENERAL;
    private static final Especialidad UPDATED_ESPECIALIDAD = Especialidad.CARDIOLOGIA;

    private static final String DEFAULT_TARJETA_PROFESIONAL = "AAAAAAAAAA";
    private static final String UPDATED_TARJETA_PROFESIONAL = "BBBBBBBBBB";

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private MedicoMapper medicoMapper;
    
    @Autowired
    private MedicoService medicoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMedicoMockMvc;

    private Medico medico;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MedicoResource medicoResource = new MedicoResource(medicoService);
        this.restMedicoMockMvc = MockMvcBuilders.standaloneSetup(medicoResource)
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
    public static Medico createEntity(EntityManager em) {
        Medico medico = new Medico()
            .tipoDocumento(DEFAULT_TIPO_DOCUMENTO)
            .documento(DEFAULT_DOCUMENTO)
            .nombres(DEFAULT_NOMBRES)
            .apellidos(DEFAULT_APELLIDOS)
            .especialidad(DEFAULT_ESPECIALIDAD)
            .tarjetaProfesional(DEFAULT_TARJETA_PROFESIONAL);
        return medico;
    }

    @Before
    public void initTest() {
        medico = createEntity(em);
    }

    @Test
    @Transactional
    public void createMedico() throws Exception {
        int databaseSizeBeforeCreate = medicoRepository.findAll().size();

        // Create the Medico
        MedicoDTO medicoDTO = medicoMapper.toDto(medico);
        restMedicoMockMvc.perform(post("/api/medicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicoDTO)))
            .andExpect(status().isCreated());

        // Validate the Medico in the database
        List<Medico> medicoList = medicoRepository.findAll();
        assertThat(medicoList).hasSize(databaseSizeBeforeCreate + 1);
        Medico testMedico = medicoList.get(medicoList.size() - 1);
        assertThat(testMedico.getTipoDocumento()).isEqualTo(DEFAULT_TIPO_DOCUMENTO);
        assertThat(testMedico.getDocumento()).isEqualTo(DEFAULT_DOCUMENTO);
        assertThat(testMedico.getNombres()).isEqualTo(DEFAULT_NOMBRES);
        assertThat(testMedico.getApellidos()).isEqualTo(DEFAULT_APELLIDOS);
        assertThat(testMedico.getEspecialidad()).isEqualTo(DEFAULT_ESPECIALIDAD);
        assertThat(testMedico.getTarjetaProfesional()).isEqualTo(DEFAULT_TARJETA_PROFESIONAL);
    }

    @Test
    @Transactional
    public void createMedicoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = medicoRepository.findAll().size();

        // Create the Medico with an existing ID
        medico.setId(1L);
        MedicoDTO medicoDTO = medicoMapper.toDto(medico);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedicoMockMvc.perform(post("/api/medicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Medico in the database
        List<Medico> medicoList = medicoRepository.findAll();
        assertThat(medicoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMedicos() throws Exception {
        // Initialize the database
        medicoRepository.saveAndFlush(medico);

        // Get all the medicoList
        restMedicoMockMvc.perform(get("/api/medicos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medico.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoDocumento").value(hasItem(DEFAULT_TIPO_DOCUMENTO.toString())))
            .andExpect(jsonPath("$.[*].documento").value(hasItem(DEFAULT_DOCUMENTO.toString())))
            .andExpect(jsonPath("$.[*].nombres").value(hasItem(DEFAULT_NOMBRES.toString())))
            .andExpect(jsonPath("$.[*].apellidos").value(hasItem(DEFAULT_APELLIDOS.toString())))
            .andExpect(jsonPath("$.[*].especialidad").value(hasItem(DEFAULT_ESPECIALIDAD.toString())))
            .andExpect(jsonPath("$.[*].tarjetaProfesional").value(hasItem(DEFAULT_TARJETA_PROFESIONAL.toString())));
    }
    
    @Test
    @Transactional
    public void getMedico() throws Exception {
        // Initialize the database
        medicoRepository.saveAndFlush(medico);

        // Get the medico
        restMedicoMockMvc.perform(get("/api/medicos/{id}", medico.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(medico.getId().intValue()))
            .andExpect(jsonPath("$.tipoDocumento").value(DEFAULT_TIPO_DOCUMENTO.toString()))
            .andExpect(jsonPath("$.documento").value(DEFAULT_DOCUMENTO.toString()))
            .andExpect(jsonPath("$.nombres").value(DEFAULT_NOMBRES.toString()))
            .andExpect(jsonPath("$.apellidos").value(DEFAULT_APELLIDOS.toString()))
            .andExpect(jsonPath("$.especialidad").value(DEFAULT_ESPECIALIDAD.toString()))
            .andExpect(jsonPath("$.tarjetaProfesional").value(DEFAULT_TARJETA_PROFESIONAL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMedico() throws Exception {
        // Get the medico
        restMedicoMockMvc.perform(get("/api/medicos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMedico() throws Exception {
        // Initialize the database
        medicoRepository.saveAndFlush(medico);

        int databaseSizeBeforeUpdate = medicoRepository.findAll().size();

        // Update the medico
        Medico updatedMedico = medicoRepository.findById(medico.getId()).get();
        // Disconnect from session so that the updates on updatedMedico are not directly saved in db
        em.detach(updatedMedico);
        updatedMedico
            .tipoDocumento(UPDATED_TIPO_DOCUMENTO)
            .documento(UPDATED_DOCUMENTO)
            .nombres(UPDATED_NOMBRES)
            .apellidos(UPDATED_APELLIDOS)
            .especialidad(UPDATED_ESPECIALIDAD)
            .tarjetaProfesional(UPDATED_TARJETA_PROFESIONAL);
        MedicoDTO medicoDTO = medicoMapper.toDto(updatedMedico);

        restMedicoMockMvc.perform(put("/api/medicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicoDTO)))
            .andExpect(status().isOk());

        // Validate the Medico in the database
        List<Medico> medicoList = medicoRepository.findAll();
        assertThat(medicoList).hasSize(databaseSizeBeforeUpdate);
        Medico testMedico = medicoList.get(medicoList.size() - 1);
        assertThat(testMedico.getTipoDocumento()).isEqualTo(UPDATED_TIPO_DOCUMENTO);
        assertThat(testMedico.getDocumento()).isEqualTo(UPDATED_DOCUMENTO);
        assertThat(testMedico.getNombres()).isEqualTo(UPDATED_NOMBRES);
        assertThat(testMedico.getApellidos()).isEqualTo(UPDATED_APELLIDOS);
        assertThat(testMedico.getEspecialidad()).isEqualTo(UPDATED_ESPECIALIDAD);
        assertThat(testMedico.getTarjetaProfesional()).isEqualTo(UPDATED_TARJETA_PROFESIONAL);
    }

    @Test
    @Transactional
    public void updateNonExistingMedico() throws Exception {
        int databaseSizeBeforeUpdate = medicoRepository.findAll().size();

        // Create the Medico
        MedicoDTO medicoDTO = medicoMapper.toDto(medico);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMedicoMockMvc.perform(put("/api/medicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Medico in the database
        List<Medico> medicoList = medicoRepository.findAll();
        assertThat(medicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMedico() throws Exception {
        // Initialize the database
        medicoRepository.saveAndFlush(medico);

        int databaseSizeBeforeDelete = medicoRepository.findAll().size();

        // Get the medico
        restMedicoMockMvc.perform(delete("/api/medicos/{id}", medico.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Medico> medicoList = medicoRepository.findAll();
        assertThat(medicoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Medico.class);
        Medico medico1 = new Medico();
        medico1.setId(1L);
        Medico medico2 = new Medico();
        medico2.setId(medico1.getId());
        assertThat(medico1).isEqualTo(medico2);
        medico2.setId(2L);
        assertThat(medico1).isNotEqualTo(medico2);
        medico1.setId(null);
        assertThat(medico1).isNotEqualTo(medico2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MedicoDTO.class);
        MedicoDTO medicoDTO1 = new MedicoDTO();
        medicoDTO1.setId(1L);
        MedicoDTO medicoDTO2 = new MedicoDTO();
        assertThat(medicoDTO1).isNotEqualTo(medicoDTO2);
        medicoDTO2.setId(medicoDTO1.getId());
        assertThat(medicoDTO1).isEqualTo(medicoDTO2);
        medicoDTO2.setId(2L);
        assertThat(medicoDTO1).isNotEqualTo(medicoDTO2);
        medicoDTO1.setId(null);
        assertThat(medicoDTO1).isNotEqualTo(medicoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(medicoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(medicoMapper.fromId(null)).isNull();
    }
}
