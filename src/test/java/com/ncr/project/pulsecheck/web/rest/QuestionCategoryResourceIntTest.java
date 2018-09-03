package com.ncr.project.pulsecheck.web.rest;

import com.ncr.project.pulsecheck.PulseCheckApp;

import com.ncr.project.pulsecheck.domain.QuestionCategory;
import com.ncr.project.pulsecheck.repository.QuestionCategoryRepository;
import com.ncr.project.pulsecheck.service.QuestionCategoryService;
import com.ncr.project.pulsecheck.service.dto.QuestionCategoryDTO;
import com.ncr.project.pulsecheck.service.mapper.QuestionCategoryMapper;
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
 * Test class for the QuestionCategoryResource REST controller.
 *
 * @see QuestionCategoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PulseCheckApp.class)
public class QuestionCategoryResourceIntTest {

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final Integer DEFAULT_LEVEL = 1;
    private static final Integer UPDATED_LEVEL = 2;

    @Autowired
    private QuestionCategoryRepository questionCategoryRepository;


    @Autowired
    private QuestionCategoryMapper questionCategoryMapper;
    

    @Autowired
    private QuestionCategoryService questionCategoryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restQuestionCategoryMockMvc;

    private QuestionCategory questionCategory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final QuestionCategoryResource questionCategoryResource = new QuestionCategoryResource(questionCategoryService);
        this.restQuestionCategoryMockMvc = MockMvcBuilders.standaloneSetup(questionCategoryResource)
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
    public static QuestionCategory createEntity(EntityManager em) {
        QuestionCategory questionCategory = new QuestionCategory()
            .label(DEFAULT_LABEL)
            .level(DEFAULT_LEVEL);
        return questionCategory;
    }

    @Before
    public void initTest() {
        questionCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuestionCategory() throws Exception {
        int databaseSizeBeforeCreate = questionCategoryRepository.findAll().size();

        // Create the QuestionCategory
        QuestionCategoryDTO questionCategoryDTO = questionCategoryMapper.toDto(questionCategory);
        restQuestionCategoryMockMvc.perform(post("/api/question-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionCategoryDTO)))
            .andExpect(status().isCreated());

        // Validate the QuestionCategory in the database
        List<QuestionCategory> questionCategoryList = questionCategoryRepository.findAll();
        assertThat(questionCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        QuestionCategory testQuestionCategory = questionCategoryList.get(questionCategoryList.size() - 1);
        assertThat(testQuestionCategory.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testQuestionCategory.getLevel()).isEqualTo(DEFAULT_LEVEL);
    }

    @Test
    @Transactional
    public void createQuestionCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = questionCategoryRepository.findAll().size();

        // Create the QuestionCategory with an existing ID
        questionCategory.setId(1L);
        QuestionCategoryDTO questionCategoryDTO = questionCategoryMapper.toDto(questionCategory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuestionCategoryMockMvc.perform(post("/api/question-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the QuestionCategory in the database
        List<QuestionCategory> questionCategoryList = questionCategoryRepository.findAll();
        assertThat(questionCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllQuestionCategories() throws Exception {
        // Initialize the database
        questionCategoryRepository.saveAndFlush(questionCategory);

        // Get all the questionCategoryList
        restQuestionCategoryMockMvc.perform(get("/api/question-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questionCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL)));
    }
    

    @Test
    @Transactional
    public void getQuestionCategory() throws Exception {
        // Initialize the database
        questionCategoryRepository.saveAndFlush(questionCategory);

        // Get the questionCategory
        restQuestionCategoryMockMvc.perform(get("/api/question-categories/{id}", questionCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(questionCategory.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL));
    }
    @Test
    @Transactional
    public void getNonExistingQuestionCategory() throws Exception {
        // Get the questionCategory
        restQuestionCategoryMockMvc.perform(get("/api/question-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuestionCategory() throws Exception {
        // Initialize the database
        questionCategoryRepository.saveAndFlush(questionCategory);

        int databaseSizeBeforeUpdate = questionCategoryRepository.findAll().size();

        // Update the questionCategory
        QuestionCategory updatedQuestionCategory = questionCategoryRepository.findById(questionCategory.getId()).get();
        // Disconnect from session so that the updates on updatedQuestionCategory are not directly saved in db
        em.detach(updatedQuestionCategory);
        updatedQuestionCategory
            .label(UPDATED_LABEL)
            .level(UPDATED_LEVEL);
        QuestionCategoryDTO questionCategoryDTO = questionCategoryMapper.toDto(updatedQuestionCategory);

        restQuestionCategoryMockMvc.perform(put("/api/question-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionCategoryDTO)))
            .andExpect(status().isOk());

        // Validate the QuestionCategory in the database
        List<QuestionCategory> questionCategoryList = questionCategoryRepository.findAll();
        assertThat(questionCategoryList).hasSize(databaseSizeBeforeUpdate);
        QuestionCategory testQuestionCategory = questionCategoryList.get(questionCategoryList.size() - 1);
        assertThat(testQuestionCategory.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testQuestionCategory.getLevel()).isEqualTo(UPDATED_LEVEL);
    }

    @Test
    @Transactional
    public void updateNonExistingQuestionCategory() throws Exception {
        int databaseSizeBeforeUpdate = questionCategoryRepository.findAll().size();

        // Create the QuestionCategory
        QuestionCategoryDTO questionCategoryDTO = questionCategoryMapper.toDto(questionCategory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restQuestionCategoryMockMvc.perform(put("/api/question-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the QuestionCategory in the database
        List<QuestionCategory> questionCategoryList = questionCategoryRepository.findAll();
        assertThat(questionCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteQuestionCategory() throws Exception {
        // Initialize the database
        questionCategoryRepository.saveAndFlush(questionCategory);

        int databaseSizeBeforeDelete = questionCategoryRepository.findAll().size();

        // Get the questionCategory
        restQuestionCategoryMockMvc.perform(delete("/api/question-categories/{id}", questionCategory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<QuestionCategory> questionCategoryList = questionCategoryRepository.findAll();
        assertThat(questionCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuestionCategory.class);
        QuestionCategory questionCategory1 = new QuestionCategory();
        questionCategory1.setId(1L);
        QuestionCategory questionCategory2 = new QuestionCategory();
        questionCategory2.setId(questionCategory1.getId());
        assertThat(questionCategory1).isEqualTo(questionCategory2);
        questionCategory2.setId(2L);
        assertThat(questionCategory1).isNotEqualTo(questionCategory2);
        questionCategory1.setId(null);
        assertThat(questionCategory1).isNotEqualTo(questionCategory2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuestionCategoryDTO.class);
        QuestionCategoryDTO questionCategoryDTO1 = new QuestionCategoryDTO();
        questionCategoryDTO1.setId(1L);
        QuestionCategoryDTO questionCategoryDTO2 = new QuestionCategoryDTO();
        assertThat(questionCategoryDTO1).isNotEqualTo(questionCategoryDTO2);
        questionCategoryDTO2.setId(questionCategoryDTO1.getId());
        assertThat(questionCategoryDTO1).isEqualTo(questionCategoryDTO2);
        questionCategoryDTO2.setId(2L);
        assertThat(questionCategoryDTO1).isNotEqualTo(questionCategoryDTO2);
        questionCategoryDTO1.setId(null);
        assertThat(questionCategoryDTO1).isNotEqualTo(questionCategoryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(questionCategoryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(questionCategoryMapper.fromId(null)).isNull();
    }
}
