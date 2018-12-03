package com.ncr.project.pulsecheck.web.rest;

import com.ncr.project.pulsecheck.PulseCheckApp;

import com.ncr.project.pulsecheck.domain.ClientLead;
import com.ncr.project.pulsecheck.repository.ClientLeadRepository;
import com.ncr.project.pulsecheck.service.ClientLeadService;
import com.ncr.project.pulsecheck.service.EventService;
import com.ncr.project.pulsecheck.service.UserExtService;
import com.ncr.project.pulsecheck.service.UserService;
import com.ncr.project.pulsecheck.service.dto.ClientLeadDTO;
import com.ncr.project.pulsecheck.service.mapper.ClientLeadMapper;
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
 * Test class for the ClientLeadResource REST controller.
 *
 * @see ClientLeadResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PulseCheckApp.class)
public class ClientLeadResourceIntTest {

    @Autowired
    private ClientLeadRepository clientLeadRepository;
    @Mock
    private ClientLeadRepository clientLeadRepositoryMock;

    @Autowired
    private ClientLeadMapper clientLeadMapper;
    
    @Mock
    private ClientLeadService clientLeadServiceMock;

    @Autowired
    private ClientLeadService clientLeadService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restClientLeadMockMvc;

    private ClientLead clientLead;

    @Autowired
    UserService userService; 
    @Autowired
    UserExtService userExtService; 
    @Autowired
    EventService eventService;


    @Mock
    UserService userServiceMock; 
    @Mock
    UserExtService userExtServiceMock; 
    @Mock
    EventService eventServiceMock;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClientLeadResource clientLeadResource = new ClientLeadResource(clientLeadService, userService,userExtService, eventService);
        this.restClientLeadMockMvc = MockMvcBuilders.standaloneSetup(clientLeadResource)
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
    public static ClientLead createEntity(EntityManager em) {
        ClientLead clientLead = new ClientLead();
        return clientLead;
    }

    @Before
    public void initTest() {
        clientLead = createEntity(em);
    }

    @Test
    @Transactional
    public void createClientLead() throws Exception {
        int databaseSizeBeforeCreate = clientLeadRepository.findAll().size();

        // Create the ClientLead
        ClientLeadDTO clientLeadDTO = clientLeadMapper.toDto(clientLead);
        restClientLeadMockMvc.perform(post("/api/client-leads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientLeadDTO)))
            .andExpect(status().isCreated());

        // Validate the ClientLead in the database
        List<ClientLead> clientLeadList = clientLeadRepository.findAll();
        assertThat(clientLeadList).hasSize(databaseSizeBeforeCreate + 1);
        ClientLead testClientLead = clientLeadList.get(clientLeadList.size() - 1);
    }

    @Test
    @Transactional
    public void createClientLeadWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clientLeadRepository.findAll().size();

        // Create the ClientLead with an existing ID
        clientLead.setId(1L);
        ClientLeadDTO clientLeadDTO = clientLeadMapper.toDto(clientLead);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientLeadMockMvc.perform(post("/api/client-leads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientLeadDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClientLead in the database
        List<ClientLead> clientLeadList = clientLeadRepository.findAll();
        assertThat(clientLeadList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllClientLeads() throws Exception {
        // Initialize the database
        clientLeadRepository.saveAndFlush(clientLead);

        // Get all the clientLeadList
        restClientLeadMockMvc.perform(get("/api/client-leads?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clientLead.getId().intValue())));
    }
    
    public void getAllClientLeadsWithEagerRelationshipsIsEnabled() throws Exception {
        ClientLeadResource clientLeadResource = new ClientLeadResource(clientLeadServiceMock, userServiceMock,userExtServiceMock, eventServiceMock);
        when(clientLeadServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restClientLeadMockMvc = MockMvcBuilders.standaloneSetup(clientLeadResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restClientLeadMockMvc.perform(get("/api/client-leads?eagerload=true"))
        .andExpect(status().isOk());

        verify(clientLeadServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllClientLeadsWithEagerRelationshipsIsNotEnabled() throws Exception {
        ClientLeadResource clientLeadResource = new ClientLeadResource(clientLeadServiceMock, userServiceMock,userExtServiceMock, eventServiceMock);
            when(clientLeadServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restClientLeadMockMvc = MockMvcBuilders.standaloneSetup(clientLeadResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restClientLeadMockMvc.perform(get("/api/client-leads?eagerload=true"))
        .andExpect(status().isOk());

            verify(clientLeadServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getClientLead() throws Exception {
        // Initialize the database
        clientLeadRepository.saveAndFlush(clientLead);

        // Get the clientLead
        restClientLeadMockMvc.perform(get("/api/client-leads/{id}", clientLead.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(clientLead.getId().intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingClientLead() throws Exception {
        // Get the clientLead
        restClientLeadMockMvc.perform(get("/api/client-leads/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClientLead() throws Exception {
        // Initialize the database
        clientLeadRepository.saveAndFlush(clientLead);

        int databaseSizeBeforeUpdate = clientLeadRepository.findAll().size();

        // Update the clientLead
        ClientLead updatedClientLead = clientLeadRepository.findById(clientLead.getId()).get();
        // Disconnect from session so that the updates on updatedClientLead are not directly saved in db
        em.detach(updatedClientLead);
        ClientLeadDTO clientLeadDTO = clientLeadMapper.toDto(updatedClientLead);

        restClientLeadMockMvc.perform(put("/api/client-leads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientLeadDTO)))
            .andExpect(status().isOk());

        // Validate the ClientLead in the database
        List<ClientLead> clientLeadList = clientLeadRepository.findAll();
        assertThat(clientLeadList).hasSize(databaseSizeBeforeUpdate);
        ClientLead testClientLead = clientLeadList.get(clientLeadList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingClientLead() throws Exception {
        int databaseSizeBeforeUpdate = clientLeadRepository.findAll().size();

        // Create the ClientLead
        ClientLeadDTO clientLeadDTO = clientLeadMapper.toDto(clientLead);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restClientLeadMockMvc.perform(put("/api/client-leads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientLeadDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClientLead in the database
        List<ClientLead> clientLeadList = clientLeadRepository.findAll();
        assertThat(clientLeadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClientLead() throws Exception {
        // Initialize the database
        clientLeadRepository.saveAndFlush(clientLead);

        int databaseSizeBeforeDelete = clientLeadRepository.findAll().size();

        // Get the clientLead
        restClientLeadMockMvc.perform(delete("/api/client-leads/{id}", clientLead.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ClientLead> clientLeadList = clientLeadRepository.findAll();
        assertThat(clientLeadList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClientLead.class);
        ClientLead clientLead1 = new ClientLead();
        clientLead1.setId(1L);
        ClientLead clientLead2 = new ClientLead();
        clientLead2.setId(clientLead1.getId());
        assertThat(clientLead1).isEqualTo(clientLead2);
        clientLead2.setId(2L);
        assertThat(clientLead1).isNotEqualTo(clientLead2);
        clientLead1.setId(null);
        assertThat(clientLead1).isNotEqualTo(clientLead2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClientLeadDTO.class);
        ClientLeadDTO clientLeadDTO1 = new ClientLeadDTO();
        clientLeadDTO1.setId(1L);
        ClientLeadDTO clientLeadDTO2 = new ClientLeadDTO();
        assertThat(clientLeadDTO1).isNotEqualTo(clientLeadDTO2);
        clientLeadDTO2.setId(clientLeadDTO1.getId());
        assertThat(clientLeadDTO1).isEqualTo(clientLeadDTO2);
        clientLeadDTO2.setId(2L);
        assertThat(clientLeadDTO1).isNotEqualTo(clientLeadDTO2);
        clientLeadDTO1.setId(null);
        assertThat(clientLeadDTO1).isNotEqualTo(clientLeadDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(clientLeadMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(clientLeadMapper.fromId(null)).isNull();
    }
}
