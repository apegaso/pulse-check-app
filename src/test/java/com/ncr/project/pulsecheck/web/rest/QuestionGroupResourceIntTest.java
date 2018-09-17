package com.ncr.project.pulsecheck.web.rest;

import com.ncr.project.pulsecheck.PulseCheckApp;

import com.ncr.project.pulsecheck.domain.QuestionGroup;
import com.ncr.project.pulsecheck.repository.QuestionGroupRepository;
import com.ncr.project.pulsecheck.service.QuestionGroupService;
import com.ncr.project.pulsecheck.service.dto.QuestionGroupDTO;
import com.ncr.project.pulsecheck.service.mapper.QuestionGroupMapper;
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
 * Test class for the QuestionGroupResource REST controller.
 *
 * @see QuestionGroupResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PulseCheckApp.class)
public class QuestionGroupResourceIntTest {

    private static final Integer DEFAULT_QUESTION_NUMBER = 1;
    private static final Integer UPDATED_QUESTION_NUMBER = 2;

    @Autowired
    private QuestionGroupRepository questionGroupRepository;


    @Autowired
    private QuestionGroupMapper questionGroupMapper;
    

    @Autowired
    private QuestionGroupService questionGroupService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restQuestionGroupMockMvc;

    private QuestionGroup questionGroup;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final QuestionGroupResource questionGroupResource = new QuestionGroupResource(questionGroupService);
        this.restQuestionGroupMockMvc = MockMvcBuilders.standaloneSetup(questionGroupResource)
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
    public static QuestionGroup createEntity(EntityManager em) {
        QuestionGroup questionGroup = new QuestionGroup()
            .questionNumber(DEFAULT_QUESTION_NUMBER);
        return questionGroup;
    }

    @Before
    public void initTest() {
        questionGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuestionGroup() throws Exception {
        int databaseSizeBeforeCreate = questionGroupRepository.findAll().size();

        // Create the QuestionGroup
        QuestionGroupDTO questionGroupDTO = questionGroupMapper.toDto(questionGroup);
        restQuestionGroupMockMvc.perform(post("/api/question-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionGroupDTO)))
            .andExpect(status().isCreated());

        // Validate the QuestionGroup in the database
        List<QuestionGroup> questionGroupList = questionGroupRepository.findAll();
        assertThat(questionGroupList).hasSize(databaseSizeBeforeCreate + 1);
        QuestionGroup testQuestionGroup = questionGroupList.get(questionGroupList.size() - 1);
        assertThat(testQuestionGroup.getQuestionNumber()).isEqualTo(DEFAULT_QUESTION_NUMBER);
    }

    @Test
    @Transactional
    public void createQuestionGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = questionGroupRepository.findAll().size();

        // Create the QuestionGroup with an existing ID
        questionGroup.setId(1L);
        QuestionGroupDTO questionGroupDTO = questionGroupMapper.toDto(questionGroup);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuestionGroupMockMvc.perform(post("/api/question-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the QuestionGroup in the database
        List<QuestionGroup> questionGroupList = questionGroupRepository.findAll();
        assertThat(questionGroupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkQuestionNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = questionGroupRepository.findAll().size();
        // set the field null
        questionGroup.setQuestionNumber(null);

        // Create the QuestionGroup, which fails.
        QuestionGroupDTO questionGroupDTO = questionGroupMapper.toDto(questionGroup);

        restQuestionGroupMockMvc.perform(post("/api/question-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionGroupDTO)))
            .andExpect(status().isBadRequest());

        List<QuestionGroup> questionGroupList = questionGroupRepository.findAll();
        assertThat(questionGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllQuestionGroups() throws Exception {
        // Initialize the database
        questionGroupRepository.saveAndFlush(questionGroup);

        // Get all the questionGroupList
        restQuestionGroupMockMvc.perform(get("/api/question-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questionGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].questionNumber").value(hasItem(DEFAULT_QUESTION_NUMBER)));
    }
    

    @Test
    @Transactional
    public void getQuestionGroup() throws Exception {
        // Initialize the database
        questionGroupRepository.saveAndFlush(questionGroup);

        // Get the questionGroup
        restQuestionGroupMockMvc.perform(get("/api/question-groups/{id}", questionGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(questionGroup.getId().intValue()))
            .andExpect(jsonPath("$.questionNumber").value(DEFAULT_QUESTION_NUMBER));
    }
    @Test
    @Transactional
    public void getNonExistingQuestionGroup() throws Exception {
        // Get the questionGroup
        restQuestionGroupMockMvc.perform(get("/api/question-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuestionGroup() throws Exception {
        // Initialize the database
        questionGroupRepository.saveAndFlush(questionGroup);

        int databaseSizeBeforeUpdate = questionGroupRepository.findAll().size();

        // Update the questionGroup
        QuestionGroup updatedQuestionGroup = questionGroupRepository.findById(questionGroup.getId()).get();
        // Disconnect from session so that the updates on updatedQuestionGroup are not directly saved in db
        em.detach(updatedQuestionGroup);
        updatedQuestionGroup
            .questionNumber(UPDATED_QUESTION_NUMBER);
        QuestionGroupDTO questionGroupDTO = questionGroupMapper.toDto(updatedQuestionGroup);

        restQuestionGroupMockMvc.perform(put("/api/question-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionGroupDTO)))
            .andExpect(status().isOk());

        // Validate the QuestionGroup in the database
        List<QuestionGroup> questionGroupList = questionGroupRepository.findAll();
        assertThat(questionGroupList).hasSize(databaseSizeBeforeUpdate);
        QuestionGroup testQuestionGroup = questionGroupList.get(questionGroupList.size() - 1);
        assertThat(testQuestionGroup.getQuestionNumber()).isEqualTo(UPDATED_QUESTION_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingQuestionGroup() throws Exception {
        int databaseSizeBeforeUpdate = questionGroupRepository.findAll().size();

        // Create the QuestionGroup
        QuestionGroupDTO questionGroupDTO = questionGroupMapper.toDto(questionGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restQuestionGroupMockMvc.perform(put("/api/question-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the QuestionGroup in the database
        List<QuestionGroup> questionGroupList = questionGroupRepository.findAll();
        assertThat(questionGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteQuestionGroup() throws Exception {
        // Initialize the database
        questionGroupRepository.saveAndFlush(questionGroup);

        int databaseSizeBeforeDelete = questionGroupRepository.findAll().size();

        // Get the questionGroup
        restQuestionGroupMockMvc.perform(delete("/api/question-groups/{id}", questionGroup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<QuestionGroup> questionGroupList = questionGroupRepository.findAll();
        assertThat(questionGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuestionGroup.class);
        QuestionGroup questionGroup1 = new QuestionGroup();
        questionGroup1.setId(1L);
        QuestionGroup questionGroup2 = new QuestionGroup();
        questionGroup2.setId(questionGroup1.getId());
        assertThat(questionGroup1).isEqualTo(questionGroup2);
        questionGroup2.setId(2L);
        assertThat(questionGroup1).isNotEqualTo(questionGroup2);
        questionGroup1.setId(null);
        assertThat(questionGroup1).isNotEqualTo(questionGroup2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuestionGroupDTO.class);
        QuestionGroupDTO questionGroupDTO1 = new QuestionGroupDTO();
        questionGroupDTO1.setId(1L);
        QuestionGroupDTO questionGroupDTO2 = new QuestionGroupDTO();
        assertThat(questionGroupDTO1).isNotEqualTo(questionGroupDTO2);
        questionGroupDTO2.setId(questionGroupDTO1.getId());
        assertThat(questionGroupDTO1).isEqualTo(questionGroupDTO2);
        questionGroupDTO2.setId(2L);
        assertThat(questionGroupDTO1).isNotEqualTo(questionGroupDTO2);
        questionGroupDTO1.setId(null);
        assertThat(questionGroupDTO1).isNotEqualTo(questionGroupDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(questionGroupMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(questionGroupMapper.fromId(null)).isNull();
    }
}
