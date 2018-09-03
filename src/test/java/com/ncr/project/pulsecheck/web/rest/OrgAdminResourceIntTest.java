package com.ncr.project.pulsecheck.web.rest;

import com.ncr.project.pulsecheck.PulseCheckApp;

import com.ncr.project.pulsecheck.domain.OrgAdmin;
import com.ncr.project.pulsecheck.repository.OrgAdminRepository;
import com.ncr.project.pulsecheck.service.OrgAdminService;
import com.ncr.project.pulsecheck.service.dto.OrgAdminDTO;
import com.ncr.project.pulsecheck.service.mapper.OrgAdminMapper;
import com.ncr.project.pulsecheck.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


import static com.ncr.project.pulsecheck.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the OrgAdminResource REST controller.
 *
 * @see OrgAdminResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PulseCheckApp.class)
public class OrgAdminResourceIntTest {

    @Autowired
    private OrgAdminRepository orgAdminRepository;
    @Mock
    private OrgAdminRepository orgAdminRepositoryMock;

    @Autowired
    private OrgAdminMapper orgAdminMapper;
    
    @Mock
    private OrgAdminService orgAdminServiceMock;

    @Autowired
    private OrgAdminService orgAdminService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOrgAdminMockMvc;

    private OrgAdmin orgAdmin;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrgAdminResource orgAdminResource = new OrgAdminResource(orgAdminService);
        this.restOrgAdminMockMvc = MockMvcBuilders.standaloneSetup(orgAdminResource)
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
    public static OrgAdmin createEntity(EntityManager em) {
        OrgAdmin orgAdmin = new OrgAdmin();
        return orgAdmin;
    }

    @Before
    public void initTest() {
        orgAdmin = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrgAdmin() throws Exception {
        int databaseSizeBeforeCreate = orgAdminRepository.findAll().size();

        // Create the OrgAdmin
        OrgAdminDTO orgAdminDTO = orgAdminMapper.toDto(orgAdmin);
        restOrgAdminMockMvc.perform(post("/api/org-admins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orgAdminDTO)))
            .andExpect(status().isCreated());

        // Validate the OrgAdmin in the database
        List<OrgAdmin> orgAdminList = orgAdminRepository.findAll();
        assertThat(orgAdminList).hasSize(databaseSizeBeforeCreate + 1);
        OrgAdmin testOrgAdmin = orgAdminList.get(orgAdminList.size() - 1);
    }

    @Test
    @Transactional
    public void createOrgAdminWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orgAdminRepository.findAll().size();

        // Create the OrgAdmin with an existing ID
        orgAdmin.setId(1L);
        OrgAdminDTO orgAdminDTO = orgAdminMapper.toDto(orgAdmin);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrgAdminMockMvc.perform(post("/api/org-admins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orgAdminDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrgAdmin in the database
        List<OrgAdmin> orgAdminList = orgAdminRepository.findAll();
        assertThat(orgAdminList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOrgAdmins() throws Exception {
        // Initialize the database
        orgAdminRepository.saveAndFlush(orgAdmin);

        // Get all the orgAdminList
        restOrgAdminMockMvc.perform(get("/api/org-admins?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orgAdmin.getId().intValue())));
    }
    
    public void getAllOrgAdminsWithEagerRelationshipsIsEnabled() throws Exception {
        OrgAdminResource orgAdminResource = new OrgAdminResource(orgAdminServiceMock);
        when(orgAdminServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restOrgAdminMockMvc = MockMvcBuilders.standaloneSetup(orgAdminResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restOrgAdminMockMvc.perform(get("/api/org-admins?eagerload=true"))
        .andExpect(status().isOk());

        verify(orgAdminServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllOrgAdminsWithEagerRelationshipsIsNotEnabled() throws Exception {
        OrgAdminResource orgAdminResource = new OrgAdminResource(orgAdminServiceMock);
            when(orgAdminServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restOrgAdminMockMvc = MockMvcBuilders.standaloneSetup(orgAdminResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restOrgAdminMockMvc.perform(get("/api/org-admins?eagerload=true"))
        .andExpect(status().isOk());

            verify(orgAdminServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getOrgAdmin() throws Exception {
        // Initialize the database
        orgAdminRepository.saveAndFlush(orgAdmin);

        // Get the orgAdmin
        restOrgAdminMockMvc.perform(get("/api/org-admins/{id}", orgAdmin.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(orgAdmin.getId().intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingOrgAdmin() throws Exception {
        // Get the orgAdmin
        restOrgAdminMockMvc.perform(get("/api/org-admins/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrgAdmin() throws Exception {
        // Initialize the database
        orgAdminRepository.saveAndFlush(orgAdmin);

        int databaseSizeBeforeUpdate = orgAdminRepository.findAll().size();

        // Update the orgAdmin
        OrgAdmin updatedOrgAdmin = orgAdminRepository.findById(orgAdmin.getId()).get();
        // Disconnect from session so that the updates on updatedOrgAdmin are not directly saved in db
        em.detach(updatedOrgAdmin);
        OrgAdminDTO orgAdminDTO = orgAdminMapper.toDto(updatedOrgAdmin);

        restOrgAdminMockMvc.perform(put("/api/org-admins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orgAdminDTO)))
            .andExpect(status().isOk());

        // Validate the OrgAdmin in the database
        List<OrgAdmin> orgAdminList = orgAdminRepository.findAll();
        assertThat(orgAdminList).hasSize(databaseSizeBeforeUpdate);
        OrgAdmin testOrgAdmin = orgAdminList.get(orgAdminList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingOrgAdmin() throws Exception {
        int databaseSizeBeforeUpdate = orgAdminRepository.findAll().size();

        // Create the OrgAdmin
        OrgAdminDTO orgAdminDTO = orgAdminMapper.toDto(orgAdmin);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restOrgAdminMockMvc.perform(put("/api/org-admins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orgAdminDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrgAdmin in the database
        List<OrgAdmin> orgAdminList = orgAdminRepository.findAll();
        assertThat(orgAdminList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrgAdmin() throws Exception {
        // Initialize the database
        orgAdminRepository.saveAndFlush(orgAdmin);

        int databaseSizeBeforeDelete = orgAdminRepository.findAll().size();

        // Get the orgAdmin
        restOrgAdminMockMvc.perform(delete("/api/org-admins/{id}", orgAdmin.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<OrgAdmin> orgAdminList = orgAdminRepository.findAll();
        assertThat(orgAdminList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrgAdmin.class);
        OrgAdmin orgAdmin1 = new OrgAdmin();
        orgAdmin1.setId(1L);
        OrgAdmin orgAdmin2 = new OrgAdmin();
        orgAdmin2.setId(orgAdmin1.getId());
        assertThat(orgAdmin1).isEqualTo(orgAdmin2);
        orgAdmin2.setId(2L);
        assertThat(orgAdmin1).isNotEqualTo(orgAdmin2);
        orgAdmin1.setId(null);
        assertThat(orgAdmin1).isNotEqualTo(orgAdmin2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrgAdminDTO.class);
        OrgAdminDTO orgAdminDTO1 = new OrgAdminDTO();
        orgAdminDTO1.setId(1L);
        OrgAdminDTO orgAdminDTO2 = new OrgAdminDTO();
        assertThat(orgAdminDTO1).isNotEqualTo(orgAdminDTO2);
        orgAdminDTO2.setId(orgAdminDTO1.getId());
        assertThat(orgAdminDTO1).isEqualTo(orgAdminDTO2);
        orgAdminDTO2.setId(2L);
        assertThat(orgAdminDTO1).isNotEqualTo(orgAdminDTO2);
        orgAdminDTO1.setId(null);
        assertThat(orgAdminDTO1).isNotEqualTo(orgAdminDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(orgAdminMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(orgAdminMapper.fromId(null)).isNull();
    }
}
