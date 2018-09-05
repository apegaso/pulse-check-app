package com.ncr.project.pulsecheck.web.rest;

import com.ncr.project.pulsecheck.PulseCheckApp;

import com.ncr.project.pulsecheck.domain.QuestionnaireAnswer;
import com.ncr.project.pulsecheck.repository.QuestionnaireAnswerRepository;
import com.ncr.project.pulsecheck.service.QuestionnaireAnswerService;
import com.ncr.project.pulsecheck.service.dto.QuestionnaireAnswerDTO;
import com.ncr.project.pulsecheck.service.mapper.QuestionnaireAnswerMapper;
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
import java.util.List;


import static com.ncr.project.pulsecheck.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the QuestionnaireAnswerResource REST controller.
 *
 * @see QuestionnaireAnswerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PulseCheckApp.class)
public class QuestionnaireAnswerResourceIntTest {

    private static final Double DEFAULT_IMPORTANCE = 1D;
    private static final Double UPDATED_IMPORTANCE = 2D;

    private static final Double DEFAULT_PERFORMANCE = 1D;
    private static final Double UPDATED_PERFORMANCE = 2D;

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    @Autowired
    private QuestionnaireAnswerRepository questionnaireAnswerRepository;


    @Autowired
    private QuestionnaireAnswerMapper questionnaireAnswerMapper;
    

    @Autowired
    private QuestionnaireAnswerService questionnaireAnswerService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restQuestionnaireAnswerMockMvc;

    private QuestionnaireAnswer questionnaireAnswer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final QuestionnaireAnswerResource questionnaireAnswerResource = new QuestionnaireAnswerResource(questionnaireAnswerService);
        this.restQuestionnaireAnswerMockMvc = MockMvcBuilders.standaloneSetup(questionnaireAnswerResource)
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
    public static QuestionnaireAnswer createEntity(EntityManager em) {
        QuestionnaireAnswer questionnaireAnswer = new QuestionnaireAnswer()
            .importance(DEFAULT_IMPORTANCE)
            .performance(DEFAULT_PERFORMANCE)
            .note(DEFAULT_NOTE);
        return questionnaireAnswer;
    }

    @Before
    public void initTest() {
        questionnaireAnswer = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuestionnaireAnswer() throws Exception {
        int databaseSizeBeforeCreate = questionnaireAnswerRepository.findAll().size();

        // Create the QuestionnaireAnswer
        QuestionnaireAnswerDTO questionnaireAnswerDTO = questionnaireAnswerMapper.toDto(questionnaireAnswer);
        restQuestionnaireAnswerMockMvc.perform(post("/api/questionnaire-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionnaireAnswerDTO)))
            .andExpect(status().isCreated());

        // Validate the QuestionnaireAnswer in the database
        List<QuestionnaireAnswer> questionnaireAnswerList = questionnaireAnswerRepository.findAll();
        assertThat(questionnaireAnswerList).hasSize(databaseSizeBeforeCreate + 1);
        QuestionnaireAnswer testQuestionnaireAnswer = questionnaireAnswerList.get(questionnaireAnswerList.size() - 1);
        assertThat(testQuestionnaireAnswer.getImportance()).isEqualTo(DEFAULT_IMPORTANCE);
        assertThat(testQuestionnaireAnswer.getPerformance()).isEqualTo(DEFAULT_PERFORMANCE);
        assertThat(testQuestionnaireAnswer.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    public void createQuestionnaireAnswerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = questionnaireAnswerRepository.findAll().size();

        // Create the QuestionnaireAnswer with an existing ID
        questionnaireAnswer.setId(1L);
        QuestionnaireAnswerDTO questionnaireAnswerDTO = questionnaireAnswerMapper.toDto(questionnaireAnswer);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuestionnaireAnswerMockMvc.perform(post("/api/questionnaire-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionnaireAnswerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the QuestionnaireAnswer in the database
        List<QuestionnaireAnswer> questionnaireAnswerList = questionnaireAnswerRepository.findAll();
        assertThat(questionnaireAnswerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllQuestionnaireAnswers() throws Exception {
        // Initialize the database
        questionnaireAnswerRepository.saveAndFlush(questionnaireAnswer);

        // Get all the questionnaireAnswerList
        restQuestionnaireAnswerMockMvc.perform(get("/api/questionnaire-answers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questionnaireAnswer.getId().intValue())))
            .andExpect(jsonPath("$.[*].importance").value(hasItem(DEFAULT_IMPORTANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].performance").value(hasItem(DEFAULT_PERFORMANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())));
    }
    

    @Test
    @Transactional
    public void getQuestionnaireAnswer() throws Exception {
        // Initialize the database
        questionnaireAnswerRepository.saveAndFlush(questionnaireAnswer);

        // Get the questionnaireAnswer
        restQuestionnaireAnswerMockMvc.perform(get("/api/questionnaire-answers/{id}", questionnaireAnswer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(questionnaireAnswer.getId().intValue()))
            .andExpect(jsonPath("$.importance").value(DEFAULT_IMPORTANCE.doubleValue()))
            .andExpect(jsonPath("$.performance").value(DEFAULT_PERFORMANCE.doubleValue()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingQuestionnaireAnswer() throws Exception {
        // Get the questionnaireAnswer
        restQuestionnaireAnswerMockMvc.perform(get("/api/questionnaire-answers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuestionnaireAnswer() throws Exception {
        // Initialize the database
        questionnaireAnswerRepository.saveAndFlush(questionnaireAnswer);

        int databaseSizeBeforeUpdate = questionnaireAnswerRepository.findAll().size();

        // Update the questionnaireAnswer
        QuestionnaireAnswer updatedQuestionnaireAnswer = questionnaireAnswerRepository.findById(questionnaireAnswer.getId()).get();
        // Disconnect from session so that the updates on updatedQuestionnaireAnswer are not directly saved in db
        em.detach(updatedQuestionnaireAnswer);
        updatedQuestionnaireAnswer
            .importance(UPDATED_IMPORTANCE)
            .performance(UPDATED_PERFORMANCE)
            .note(UPDATED_NOTE);
        QuestionnaireAnswerDTO questionnaireAnswerDTO = questionnaireAnswerMapper.toDto(updatedQuestionnaireAnswer);

        restQuestionnaireAnswerMockMvc.perform(put("/api/questionnaire-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionnaireAnswerDTO)))
            .andExpect(status().isOk());

        // Validate the QuestionnaireAnswer in the database
        List<QuestionnaireAnswer> questionnaireAnswerList = questionnaireAnswerRepository.findAll();
        assertThat(questionnaireAnswerList).hasSize(databaseSizeBeforeUpdate);
        QuestionnaireAnswer testQuestionnaireAnswer = questionnaireAnswerList.get(questionnaireAnswerList.size() - 1);
        assertThat(testQuestionnaireAnswer.getImportance()).isEqualTo(UPDATED_IMPORTANCE);
        assertThat(testQuestionnaireAnswer.getPerformance()).isEqualTo(UPDATED_PERFORMANCE);
        assertThat(testQuestionnaireAnswer.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void updateNonExistingQuestionnaireAnswer() throws Exception {
        int databaseSizeBeforeUpdate = questionnaireAnswerRepository.findAll().size();

        // Create the QuestionnaireAnswer
        QuestionnaireAnswerDTO questionnaireAnswerDTO = questionnaireAnswerMapper.toDto(questionnaireAnswer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restQuestionnaireAnswerMockMvc.perform(put("/api/questionnaire-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionnaireAnswerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the QuestionnaireAnswer in the database
        List<QuestionnaireAnswer> questionnaireAnswerList = questionnaireAnswerRepository.findAll();
        assertThat(questionnaireAnswerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteQuestionnaireAnswer() throws Exception {
        // Initialize the database
        questionnaireAnswerRepository.saveAndFlush(questionnaireAnswer);

        int databaseSizeBeforeDelete = questionnaireAnswerRepository.findAll().size();

        // Get the questionnaireAnswer
        restQuestionnaireAnswerMockMvc.perform(delete("/api/questionnaire-answers/{id}", questionnaireAnswer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<QuestionnaireAnswer> questionnaireAnswerList = questionnaireAnswerRepository.findAll();
        assertThat(questionnaireAnswerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuestionnaireAnswer.class);
        QuestionnaireAnswer questionnaireAnswer1 = new QuestionnaireAnswer();
        questionnaireAnswer1.setId(1L);
        QuestionnaireAnswer questionnaireAnswer2 = new QuestionnaireAnswer();
        questionnaireAnswer2.setId(questionnaireAnswer1.getId());
        assertThat(questionnaireAnswer1).isEqualTo(questionnaireAnswer2);
        questionnaireAnswer2.setId(2L);
        assertThat(questionnaireAnswer1).isNotEqualTo(questionnaireAnswer2);
        questionnaireAnswer1.setId(null);
        assertThat(questionnaireAnswer1).isNotEqualTo(questionnaireAnswer2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuestionnaireAnswerDTO.class);
        QuestionnaireAnswerDTO questionnaireAnswerDTO1 = new QuestionnaireAnswerDTO();
        questionnaireAnswerDTO1.setId(1L);
        QuestionnaireAnswerDTO questionnaireAnswerDTO2 = new QuestionnaireAnswerDTO();
        assertThat(questionnaireAnswerDTO1).isNotEqualTo(questionnaireAnswerDTO2);
        questionnaireAnswerDTO2.setId(questionnaireAnswerDTO1.getId());
        assertThat(questionnaireAnswerDTO1).isEqualTo(questionnaireAnswerDTO2);
        questionnaireAnswerDTO2.setId(2L);
        assertThat(questionnaireAnswerDTO1).isNotEqualTo(questionnaireAnswerDTO2);
        questionnaireAnswerDTO1.setId(null);
        assertThat(questionnaireAnswerDTO1).isNotEqualTo(questionnaireAnswerDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(questionnaireAnswerMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(questionnaireAnswerMapper.fromId(null)).isNull();
    }
}
