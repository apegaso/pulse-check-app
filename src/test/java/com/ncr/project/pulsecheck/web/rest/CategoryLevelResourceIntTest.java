package com.ncr.project.pulsecheck.web.rest;

import com.ncr.project.pulsecheck.PulseCheckApp;

import com.ncr.project.pulsecheck.domain.CategoryLevel;
import com.ncr.project.pulsecheck.repository.CategoryLevelRepository;
import com.ncr.project.pulsecheck.service.CategoryLevelService;
import com.ncr.project.pulsecheck.service.dto.CategoryLevelDTO;
import com.ncr.project.pulsecheck.service.mapper.CategoryLevelMapper;
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
 * Test class for the CategoryLevelResource REST controller.
 *
 * @see CategoryLevelResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PulseCheckApp.class)
public class CategoryLevelResourceIntTest {

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    @Autowired
    private CategoryLevelRepository categoryLevelRepository;


    @Autowired
    private CategoryLevelMapper categoryLevelMapper;
    

    @Autowired
    private CategoryLevelService categoryLevelService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCategoryLevelMockMvc;

    private CategoryLevel categoryLevel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CategoryLevelResource categoryLevelResource = new CategoryLevelResource(categoryLevelService);
        this.restCategoryLevelMockMvc = MockMvcBuilders.standaloneSetup(categoryLevelResource)
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
    public static CategoryLevel createEntity(EntityManager em) {
        CategoryLevel categoryLevel = new CategoryLevel()
            .label(DEFAULT_LABEL);
        return categoryLevel;
    }

    @Before
    public void initTest() {
        categoryLevel = createEntity(em);
    }

    @Test
    @Transactional
    public void createCategoryLevel() throws Exception {
        int databaseSizeBeforeCreate = categoryLevelRepository.findAll().size();

        // Create the CategoryLevel
        CategoryLevelDTO categoryLevelDTO = categoryLevelMapper.toDto(categoryLevel);
        restCategoryLevelMockMvc.perform(post("/api/category-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoryLevelDTO)))
            .andExpect(status().isCreated());

        // Validate the CategoryLevel in the database
        List<CategoryLevel> categoryLevelList = categoryLevelRepository.findAll();
        assertThat(categoryLevelList).hasSize(databaseSizeBeforeCreate + 1);
        CategoryLevel testCategoryLevel = categoryLevelList.get(categoryLevelList.size() - 1);
        assertThat(testCategoryLevel.getLabel()).isEqualTo(DEFAULT_LABEL);
    }

    @Test
    @Transactional
    public void createCategoryLevelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = categoryLevelRepository.findAll().size();

        // Create the CategoryLevel with an existing ID
        categoryLevel.setId(1L);
        CategoryLevelDTO categoryLevelDTO = categoryLevelMapper.toDto(categoryLevel);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategoryLevelMockMvc.perform(post("/api/category-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoryLevelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CategoryLevel in the database
        List<CategoryLevel> categoryLevelList = categoryLevelRepository.findAll();
        assertThat(categoryLevelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoryLevelRepository.findAll().size();
        // set the field null
        categoryLevel.setLabel(null);

        // Create the CategoryLevel, which fails.
        CategoryLevelDTO categoryLevelDTO = categoryLevelMapper.toDto(categoryLevel);

        restCategoryLevelMockMvc.perform(post("/api/category-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoryLevelDTO)))
            .andExpect(status().isBadRequest());

        List<CategoryLevel> categoryLevelList = categoryLevelRepository.findAll();
        assertThat(categoryLevelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCategoryLevels() throws Exception {
        // Initialize the database
        categoryLevelRepository.saveAndFlush(categoryLevel);

        // Get all the categoryLevelList
        restCategoryLevelMockMvc.perform(get("/api/category-levels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categoryLevel.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())));
    }
    

    @Test
    @Transactional
    public void getCategoryLevel() throws Exception {
        // Initialize the database
        categoryLevelRepository.saveAndFlush(categoryLevel);

        // Get the categoryLevel
        restCategoryLevelMockMvc.perform(get("/api/category-levels/{id}", categoryLevel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(categoryLevel.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingCategoryLevel() throws Exception {
        // Get the categoryLevel
        restCategoryLevelMockMvc.perform(get("/api/category-levels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCategoryLevel() throws Exception {
        // Initialize the database
        categoryLevelRepository.saveAndFlush(categoryLevel);

        int databaseSizeBeforeUpdate = categoryLevelRepository.findAll().size();

        // Update the categoryLevel
        CategoryLevel updatedCategoryLevel = categoryLevelRepository.findById(categoryLevel.getId()).get();
        // Disconnect from session so that the updates on updatedCategoryLevel are not directly saved in db
        em.detach(updatedCategoryLevel);
        updatedCategoryLevel
            .label(UPDATED_LABEL);
        CategoryLevelDTO categoryLevelDTO = categoryLevelMapper.toDto(updatedCategoryLevel);

        restCategoryLevelMockMvc.perform(put("/api/category-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoryLevelDTO)))
            .andExpect(status().isOk());

        // Validate the CategoryLevel in the database
        List<CategoryLevel> categoryLevelList = categoryLevelRepository.findAll();
        assertThat(categoryLevelList).hasSize(databaseSizeBeforeUpdate);
        CategoryLevel testCategoryLevel = categoryLevelList.get(categoryLevelList.size() - 1);
        assertThat(testCategoryLevel.getLabel()).isEqualTo(UPDATED_LABEL);
    }

    @Test
    @Transactional
    public void updateNonExistingCategoryLevel() throws Exception {
        int databaseSizeBeforeUpdate = categoryLevelRepository.findAll().size();

        // Create the CategoryLevel
        CategoryLevelDTO categoryLevelDTO = categoryLevelMapper.toDto(categoryLevel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restCategoryLevelMockMvc.perform(put("/api/category-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoryLevelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CategoryLevel in the database
        List<CategoryLevel> categoryLevelList = categoryLevelRepository.findAll();
        assertThat(categoryLevelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCategoryLevel() throws Exception {
        // Initialize the database
        categoryLevelRepository.saveAndFlush(categoryLevel);

        int databaseSizeBeforeDelete = categoryLevelRepository.findAll().size();

        // Get the categoryLevel
        restCategoryLevelMockMvc.perform(delete("/api/category-levels/{id}", categoryLevel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CategoryLevel> categoryLevelList = categoryLevelRepository.findAll();
        assertThat(categoryLevelList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoryLevel.class);
        CategoryLevel categoryLevel1 = new CategoryLevel();
        categoryLevel1.setId(1L);
        CategoryLevel categoryLevel2 = new CategoryLevel();
        categoryLevel2.setId(categoryLevel1.getId());
        assertThat(categoryLevel1).isEqualTo(categoryLevel2);
        categoryLevel2.setId(2L);
        assertThat(categoryLevel1).isNotEqualTo(categoryLevel2);
        categoryLevel1.setId(null);
        assertThat(categoryLevel1).isNotEqualTo(categoryLevel2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoryLevelDTO.class);
        CategoryLevelDTO categoryLevelDTO1 = new CategoryLevelDTO();
        categoryLevelDTO1.setId(1L);
        CategoryLevelDTO categoryLevelDTO2 = new CategoryLevelDTO();
        assertThat(categoryLevelDTO1).isNotEqualTo(categoryLevelDTO2);
        categoryLevelDTO2.setId(categoryLevelDTO1.getId());
        assertThat(categoryLevelDTO1).isEqualTo(categoryLevelDTO2);
        categoryLevelDTO2.setId(2L);
        assertThat(categoryLevelDTO1).isNotEqualTo(categoryLevelDTO2);
        categoryLevelDTO1.setId(null);
        assertThat(categoryLevelDTO1).isNotEqualTo(categoryLevelDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(categoryLevelMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(categoryLevelMapper.fromId(null)).isNull();
    }
}
