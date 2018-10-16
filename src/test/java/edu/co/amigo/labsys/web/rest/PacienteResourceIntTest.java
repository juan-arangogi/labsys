package edu.co.amigo.labsys.web.rest;

import edu.co.amigo.labsys.LabsysApp;

import edu.co.amigo.labsys.domain.Paciente;
import edu.co.amigo.labsys.repository.PacienteRepository;
import edu.co.amigo.labsys.service.PacienteService;
import edu.co.amigo.labsys.service.dto.PacienteDTO;
import edu.co.amigo.labsys.service.mapper.PacienteMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static edu.co.amigo.labsys.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import edu.co.amigo.labsys.domain.enumeration.TipoDocumento;
import edu.co.amigo.labsys.domain.enumeration.Genero;
/**
 * Test class for the PacienteResource REST controller.
 *
 * @see PacienteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LabsysApp.class)
public class PacienteResourceIntTest {

    private static final TipoDocumento DEFAULT_TIPO_DOCUMENTO = TipoDocumento.ASI;
    private static final TipoDocumento UPDATED_TIPO_DOCUMENTO = TipoDocumento.MSI;

    private static final String DEFAULT_DOCUMENTO = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRES = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRES = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDOS = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDOS = "BBBBBBBBBB";

    private static final Genero DEFAULT_GENERO = Genero.MASCULINO;
    private static final Genero UPDATED_GENERO = Genero.FEMENINO;

    private static final LocalDate DEFAULT_FECHA_NACIMIENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_NACIMIENTO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

    private static final String DEFAULT_CELULAR = "AAAAAAAAAA";
    private static final String UPDATED_CELULAR = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION = "BBBBBBBBBB";

    private static final String DEFAULT_CIUDAD = "AAAAAAAAAA";
    private static final String UPDATED_CIUDAD = "BBBBBBBBBB";

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private PacienteMapper pacienteMapper;
    
    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPacienteMockMvc;

    private Paciente paciente;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PacienteResource pacienteResource = new PacienteResource(pacienteService);
        this.restPacienteMockMvc = MockMvcBuilders.standaloneSetup(pacienteResource)
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
    public static Paciente createEntity(EntityManager em) {
        Paciente paciente = new Paciente()
            .tipoDocumento(DEFAULT_TIPO_DOCUMENTO)
            .documento(DEFAULT_DOCUMENTO)
            .nombres(DEFAULT_NOMBRES)
            .apellidos(DEFAULT_APELLIDOS)
            .genero(DEFAULT_GENERO)
            .fechaNacimiento(DEFAULT_FECHA_NACIMIENTO)
            .telefono(DEFAULT_TELEFONO)
            .celular(DEFAULT_CELULAR)
            .email(DEFAULT_EMAIL)
            .direccion(DEFAULT_DIRECCION)
            .ciudad(DEFAULT_CIUDAD);
        return paciente;
    }

    @Before
    public void initTest() {
        paciente = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaciente() throws Exception {
        int databaseSizeBeforeCreate = pacienteRepository.findAll().size();

        // Create the Paciente
        PacienteDTO pacienteDTO = pacienteMapper.toDto(paciente);
        restPacienteMockMvc.perform(post("/api/pacientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pacienteDTO)))
            .andExpect(status().isCreated());

        // Validate the Paciente in the database
        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeCreate + 1);
        Paciente testPaciente = pacienteList.get(pacienteList.size() - 1);
        assertThat(testPaciente.getTipoDocumento()).isEqualTo(DEFAULT_TIPO_DOCUMENTO);
        assertThat(testPaciente.getDocumento()).isEqualTo(DEFAULT_DOCUMENTO);
        assertThat(testPaciente.getNombres()).isEqualTo(DEFAULT_NOMBRES);
        assertThat(testPaciente.getApellidos()).isEqualTo(DEFAULT_APELLIDOS);
        assertThat(testPaciente.getGenero()).isEqualTo(DEFAULT_GENERO);
        assertThat(testPaciente.getFechaNacimiento()).isEqualTo(DEFAULT_FECHA_NACIMIENTO);
        assertThat(testPaciente.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testPaciente.getCelular()).isEqualTo(DEFAULT_CELULAR);
        assertThat(testPaciente.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testPaciente.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
        assertThat(testPaciente.getCiudad()).isEqualTo(DEFAULT_CIUDAD);
    }

    @Test
    @Transactional
    public void createPacienteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pacienteRepository.findAll().size();

        // Create the Paciente with an existing ID
        paciente.setId(1L);
        PacienteDTO pacienteDTO = pacienteMapper.toDto(paciente);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPacienteMockMvc.perform(post("/api/pacientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pacienteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Paciente in the database
        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPacientes() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList
        restPacienteMockMvc.perform(get("/api/pacientes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paciente.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoDocumento").value(hasItem(DEFAULT_TIPO_DOCUMENTO.toString())))
            .andExpect(jsonPath("$.[*].documento").value(hasItem(DEFAULT_DOCUMENTO.toString())))
            .andExpect(jsonPath("$.[*].nombres").value(hasItem(DEFAULT_NOMBRES.toString())))
            .andExpect(jsonPath("$.[*].apellidos").value(hasItem(DEFAULT_APELLIDOS.toString())))
            .andExpect(jsonPath("$.[*].genero").value(hasItem(DEFAULT_GENERO.toString())))
            .andExpect(jsonPath("$.[*].fechaNacimiento").value(hasItem(DEFAULT_FECHA_NACIMIENTO.toString())))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO.toString())))
            .andExpect(jsonPath("$.[*].celular").value(hasItem(DEFAULT_CELULAR.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION.toString())))
            .andExpect(jsonPath("$.[*].ciudad").value(hasItem(DEFAULT_CIUDAD.toString())));
    }
    
    @Test
    @Transactional
    public void getPaciente() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get the paciente
        restPacienteMockMvc.perform(get("/api/pacientes/{id}", paciente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(paciente.getId().intValue()))
            .andExpect(jsonPath("$.tipoDocumento").value(DEFAULT_TIPO_DOCUMENTO.toString()))
            .andExpect(jsonPath("$.documento").value(DEFAULT_DOCUMENTO.toString()))
            .andExpect(jsonPath("$.nombres").value(DEFAULT_NOMBRES.toString()))
            .andExpect(jsonPath("$.apellidos").value(DEFAULT_APELLIDOS.toString()))
            .andExpect(jsonPath("$.genero").value(DEFAULT_GENERO.toString()))
            .andExpect(jsonPath("$.fechaNacimiento").value(DEFAULT_FECHA_NACIMIENTO.toString()))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO.toString()))
            .andExpect(jsonPath("$.celular").value(DEFAULT_CELULAR.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION.toString()))
            .andExpect(jsonPath("$.ciudad").value(DEFAULT_CIUDAD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPaciente() throws Exception {
        // Get the paciente
        restPacienteMockMvc.perform(get("/api/pacientes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaciente() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        int databaseSizeBeforeUpdate = pacienteRepository.findAll().size();

        // Update the paciente
        Paciente updatedPaciente = pacienteRepository.findById(paciente.getId()).get();
        // Disconnect from session so that the updates on updatedPaciente are not directly saved in db
        em.detach(updatedPaciente);
        updatedPaciente
            .tipoDocumento(UPDATED_TIPO_DOCUMENTO)
            .documento(UPDATED_DOCUMENTO)
            .nombres(UPDATED_NOMBRES)
            .apellidos(UPDATED_APELLIDOS)
            .genero(UPDATED_GENERO)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
            .telefono(UPDATED_TELEFONO)
            .celular(UPDATED_CELULAR)
            .email(UPDATED_EMAIL)
            .direccion(UPDATED_DIRECCION)
            .ciudad(UPDATED_CIUDAD);
        PacienteDTO pacienteDTO = pacienteMapper.toDto(updatedPaciente);

        restPacienteMockMvc.perform(put("/api/pacientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pacienteDTO)))
            .andExpect(status().isOk());

        // Validate the Paciente in the database
        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeUpdate);
        Paciente testPaciente = pacienteList.get(pacienteList.size() - 1);
        assertThat(testPaciente.getTipoDocumento()).isEqualTo(UPDATED_TIPO_DOCUMENTO);
        assertThat(testPaciente.getDocumento()).isEqualTo(UPDATED_DOCUMENTO);
        assertThat(testPaciente.getNombres()).isEqualTo(UPDATED_NOMBRES);
        assertThat(testPaciente.getApellidos()).isEqualTo(UPDATED_APELLIDOS);
        assertThat(testPaciente.getGenero()).isEqualTo(UPDATED_GENERO);
        assertThat(testPaciente.getFechaNacimiento()).isEqualTo(UPDATED_FECHA_NACIMIENTO);
        assertThat(testPaciente.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testPaciente.getCelular()).isEqualTo(UPDATED_CELULAR);
        assertThat(testPaciente.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPaciente.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testPaciente.getCiudad()).isEqualTo(UPDATED_CIUDAD);
    }

    @Test
    @Transactional
    public void updateNonExistingPaciente() throws Exception {
        int databaseSizeBeforeUpdate = pacienteRepository.findAll().size();

        // Create the Paciente
        PacienteDTO pacienteDTO = pacienteMapper.toDto(paciente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPacienteMockMvc.perform(put("/api/pacientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pacienteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Paciente in the database
        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePaciente() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        int databaseSizeBeforeDelete = pacienteRepository.findAll().size();

        // Get the paciente
        restPacienteMockMvc.perform(delete("/api/pacientes/{id}", paciente.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Paciente.class);
        Paciente paciente1 = new Paciente();
        paciente1.setId(1L);
        Paciente paciente2 = new Paciente();
        paciente2.setId(paciente1.getId());
        assertThat(paciente1).isEqualTo(paciente2);
        paciente2.setId(2L);
        assertThat(paciente1).isNotEqualTo(paciente2);
        paciente1.setId(null);
        assertThat(paciente1).isNotEqualTo(paciente2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PacienteDTO.class);
        PacienteDTO pacienteDTO1 = new PacienteDTO();
        pacienteDTO1.setId(1L);
        PacienteDTO pacienteDTO2 = new PacienteDTO();
        assertThat(pacienteDTO1).isNotEqualTo(pacienteDTO2);
        pacienteDTO2.setId(pacienteDTO1.getId());
        assertThat(pacienteDTO1).isEqualTo(pacienteDTO2);
        pacienteDTO2.setId(2L);
        assertThat(pacienteDTO1).isNotEqualTo(pacienteDTO2);
        pacienteDTO1.setId(null);
        assertThat(pacienteDTO1).isNotEqualTo(pacienteDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pacienteMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pacienteMapper.fromId(null)).isNull();
    }
}
