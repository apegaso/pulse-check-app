package com.ncr.project.pulsecheck.web.rest;

import com.ncr.project.pulsecheck.PulseCheckApp;

import com.ncr.project.pulsecheck.domain.Email;
import com.ncr.project.pulsecheck.repository.EmailRepository;
import com.ncr.project.pulsecheck.service.EmailService;
import com.ncr.project.pulsecheck.web.rest.errors.ExceptionTranslator;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static com.ncr.project.pulsecheck.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the EmailResource REST controller.
 *
 * @see EmailResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PulseCheckApp.class)
public class EmailResourceIntTest {

    private static final String DEFAULT_FROM = "AAAAAAAAAA";
    private static final String UPDATED_FROM = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESSES = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESSES = "BBBBBBBBBB";

    private static final String DEFAULT_SUBJECT = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECT = "BBBBBBBBBB";

    private static final String DEFAULT_BODY = "AAAAAAAAAA";
    private static final String UPDATED_BODY = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_HTML = false;
    private static final Boolean UPDATED_IS_HTML = true;

    private static final Boolean DEFAULT_IS_SENT = false;
    private static final Boolean UPDATED_IS_SENT = true;

    private static final Instant DEFAULT_DATE_INSERT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_INSERT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_SENT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_SENT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private EmailRepository emailRepository;

    

    @Autowired
    private EmailService emailService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEmailMockMvc;

    private Email email;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmailResource emailResource = new EmailResource(emailService);
        this.restEmailMockMvc = MockMvcBuilders.standaloneSetup(emailResource)
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
    public static Email createEntity(EntityManager em) {
        Email email = new Email()
            .from(DEFAULT_FROM)
            .addresses(DEFAULT_ADDRESSES)
            .subject(DEFAULT_SUBJECT)
            .body(DEFAULT_BODY)
            .isHtml(DEFAULT_IS_HTML)
            .isSent(DEFAULT_IS_SENT)
            .dateInsert(DEFAULT_DATE_INSERT)
            .dateSent(DEFAULT_DATE_SENT);
        return email;
    }

    @Before
    public void initTest() {
        email = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmail() throws Exception {
        int databaseSizeBeforeCreate = emailRepository.findAll().size();

        // Create the Email
        restEmailMockMvc.perform(post("/api/emails")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(email)))
            .andExpect(status().isCreated());

        // Validate the Email in the database
        List<Email> emailList = emailRepository.findAll();
        assertThat(emailList).hasSize(databaseSizeBeforeCreate + 1);
        Email testEmail = emailList.get(emailList.size() - 1);
        assertThat(testEmail.getFrom()).isEqualTo(DEFAULT_FROM);
        assertThat(testEmail.getAddresses()).isEqualTo(DEFAULT_ADDRESSES);
        assertThat(testEmail.getSubject()).isEqualTo(DEFAULT_SUBJECT);
        assertThat(testEmail.getBody()).isEqualTo(DEFAULT_BODY);
        assertThat(testEmail.isIsHtml()).isEqualTo(DEFAULT_IS_HTML);
        assertThat(testEmail.isIsSent()).isEqualTo(DEFAULT_IS_SENT);
        assertThat(testEmail.getDateInsert()).isEqualTo(DEFAULT_DATE_INSERT);
        assertThat(testEmail.getDateSent()).isEqualTo(DEFAULT_DATE_SENT);
    }

    @Test
    @Transactional
    public void createEmailWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = emailRepository.findAll().size();

        // Create the Email with an existing ID
        email.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmailMockMvc.perform(post("/api/emails")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(email)))
            .andExpect(status().isBadRequest());

        // Validate the Email in the database
        List<Email> emailList = emailRepository.findAll();
        assertThat(emailList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEmails() throws Exception {
        // Initialize the database
        emailRepository.saveAndFlush(email);

        // Get all the emailList
        restEmailMockMvc.perform(get("/api/emails?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(email.getId().intValue())))
            .andExpect(jsonPath("$.[*].from").value(hasItem(DEFAULT_FROM.toString())))
            .andExpect(jsonPath("$.[*].addresses").value(hasItem(DEFAULT_ADDRESSES.toString())))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT.toString())))
            .andExpect(jsonPath("$.[*].body").value(hasItem(DEFAULT_BODY.toString())))
            .andExpect(jsonPath("$.[*].isHtml").value(hasItem(DEFAULT_IS_HTML.booleanValue())))
            .andExpect(jsonPath("$.[*].isSent").value(hasItem(DEFAULT_IS_SENT.booleanValue())))
            .andExpect(jsonPath("$.[*].dateInsert").value(hasItem(DEFAULT_DATE_INSERT.toString())))
            .andExpect(jsonPath("$.[*].dateSent").value(hasItem(DEFAULT_DATE_SENT.toString())));
    }
    

    @Test
    @Transactional
    public void getEmail() throws Exception {
        // Initialize the database
        emailRepository.saveAndFlush(email);

        // Get the email
        restEmailMockMvc.perform(get("/api/emails/{id}", email.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(email.getId().intValue()))
            .andExpect(jsonPath("$.from").value(DEFAULT_FROM.toString()))
            .andExpect(jsonPath("$.addresses").value(DEFAULT_ADDRESSES.toString()))
            .andExpect(jsonPath("$.subject").value(DEFAULT_SUBJECT.toString()))
            .andExpect(jsonPath("$.body").value(DEFAULT_BODY.toString()))
            .andExpect(jsonPath("$.isHtml").value(DEFAULT_IS_HTML.booleanValue()))
            .andExpect(jsonPath("$.isSent").value(DEFAULT_IS_SENT.booleanValue()))
            .andExpect(jsonPath("$.dateInsert").value(DEFAULT_DATE_INSERT.toString()))
            .andExpect(jsonPath("$.dateSent").value(DEFAULT_DATE_SENT.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingEmail() throws Exception {
        // Get the email
        restEmailMockMvc.perform(get("/api/emails/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmail() throws Exception {
        // Initialize the database
        emailService.save(email);

        int databaseSizeBeforeUpdate = emailRepository.findAll().size();

        // Update the email
        Email updatedEmail = emailRepository.findById(email.getId()).get();
        // Disconnect from session so that the updates on updatedEmail are not directly saved in db
        em.detach(updatedEmail);
        updatedEmail
            .from(UPDATED_FROM)
            .addresses(UPDATED_ADDRESSES)
            .subject(UPDATED_SUBJECT)
            .body(UPDATED_BODY)
            .isHtml(UPDATED_IS_HTML)
            .isSent(UPDATED_IS_SENT)
            .dateInsert(UPDATED_DATE_INSERT)
            .dateSent(UPDATED_DATE_SENT);

        restEmailMockMvc.perform(put("/api/emails")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmail)))
            .andExpect(status().isOk());

        // Validate the Email in the database
        List<Email> emailList = emailRepository.findAll();
        assertThat(emailList).hasSize(databaseSizeBeforeUpdate);
        Email testEmail = emailList.get(emailList.size() - 1);
        assertThat(testEmail.getFrom()).isEqualTo(UPDATED_FROM);
        assertThat(testEmail.getAddresses()).isEqualTo(UPDATED_ADDRESSES);
        assertThat(testEmail.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testEmail.getBody()).isEqualTo(UPDATED_BODY);
        assertThat(testEmail.isIsHtml()).isEqualTo(UPDATED_IS_HTML);
        assertThat(testEmail.isIsSent()).isEqualTo(UPDATED_IS_SENT);
        assertThat(testEmail.getDateInsert()).isEqualTo(UPDATED_DATE_INSERT);
        assertThat(testEmail.getDateSent()).isEqualTo(UPDATED_DATE_SENT);
    }

    @Test
    @Transactional
    public void updateNonExistingEmail() throws Exception {
        int databaseSizeBeforeUpdate = emailRepository.findAll().size();

        // Create the Email

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restEmailMockMvc.perform(put("/api/emails")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(email)))
            .andExpect(status().isBadRequest());

        // Validate the Email in the database
        List<Email> emailList = emailRepository.findAll();
        assertThat(emailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmail() throws Exception {
        // Initialize the database
        emailService.save(email);

        int databaseSizeBeforeDelete = emailRepository.findAll().size();

        // Get the email
        restEmailMockMvc.perform(delete("/api/emails/{id}", email.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Email> emailList = emailRepository.findAll();
        assertThat(emailList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Email.class);
        Email email1 = new Email();
        email1.setId(1L);
        Email email2 = new Email();
        email2.setId(email1.getId());
        assertThat(email1).isEqualTo(email2);
        email2.setId(2L);
        assertThat(email1).isNotEqualTo(email2);
        email1.setId(null);
        assertThat(email1).isNotEqualTo(email2);
    }
}
