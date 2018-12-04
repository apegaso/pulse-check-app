package com.ncr.project.pulsecheck.service.impl;

import com.ncr.project.pulsecheck.service.CategoryService;
import com.google.common.collect.Sets;
import com.ncr.project.pulsecheck.domain.Category;
import com.ncr.project.pulsecheck.domain.Question;
import com.ncr.project.pulsecheck.domain.QuestionGroup;
import com.ncr.project.pulsecheck.repository.CategoryRepository;
import com.ncr.project.pulsecheck.service.dto.CategoryDTO;
import com.ncr.project.pulsecheck.service.dto.QuestionDTO;
import com.ncr.project.pulsecheck.service.dto.QuestionGroupDTO;
import com.ncr.project.pulsecheck.service.mapper.CategoryMapper;
import com.ncr.project.pulsecheck.service.mapper.QuestionGroupMapper;
import com.ncr.project.pulsecheck.service.mapper.QuestionMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Service Implementation for managing Category.
 * 
 */
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    private final QuestionMapper questionMapper;
    
    private final QuestionGroupMapper groupQuestionMapper;
    
    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper, QuestionMapper questionMapper, QuestionGroupMapper groupQuestionMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.questionMapper = questionMapper;
        this.groupQuestionMapper = groupQuestionMapper;
    }

    /**
     * Save a category.
     *
     * @param categoryDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CategoryDTO save(CategoryDTO categoryDTO) {
        log.debug("Request to save Category : {}", categoryDTO);
        Category category = categoryMapper.toEntity(categoryDTO);
        category = categoryRepository.save(category);
        return categoryMapper.toDto(category);
    }

    /**
     * Get all the categories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CategoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Categories");
        return categoryRepository.findAll(pageable)
            .map(categoryMapper::toDto);
    }


    /**
     * Get one category by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CategoryDTO> findOne(Long id) {
        log.debug("Request to get Category : {}", id);
        return categoryRepository.findById(id)
            .map(categoryMapper::toDto);
    }

    /**
     * Delete the category by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Category : {}", id);
        categoryRepository.deleteById(id);
    }

    @Override
    public Optional<Set<QuestionDTO>> findQuestionsById(Long id) {
        log.debug("Request to get Category Questions : {}", id);
        Optional<Category> categoryOpt = categoryRepository.findById(id);
        Set<QuestionDTO> ret = null;
        if(categoryOpt.isPresent()){
            Category category = categoryOpt.get();
            ret = category.getQuestions().stream().map(questionMapper::toDto).collect(Collectors.toSet());
            ret.addAll(getSonsQuestions(category));
            
        }
        return Optional.of(ret);
	}

	private Set<QuestionDTO> getSonsQuestions(Category category) {
        Set<Category> sons = category.getSons();
		Set<QuestionDTO> ret = sons
		.stream()
		.map(c -> {
		    log.debug("map -> {}",c);
                    List<Question> collect = c.getQuestions().stream().collect(Collectors.toList());
                    return collect;
            }).map(questionMapper::toDto).collect(HashSet::new, HashSet::addAll, HashSet::addAll);
            
        sons.forEach(c -> {ret.addAll(getSonsQuestions(c));});
        return ret;
    }

    @Override
    public Optional<List<QuestionGroupDTO>> findQuestionGroupsById(Long id) {
        log.debug("Request to get Category Question Groups : {}", id);
        Optional<Category> categoryOpt = categoryRepository.findById(id);
        Set<QuestionGroup> ret = Sets.newHashSet();
        if(categoryOpt.isPresent()){
            Category category = categoryOpt.get();
            Set<Question> questions = category.getQuestions();
            for(Question q : questions){
                ret.add(q.getGroup());
            }
        }
        
        List<QuestionGroupDTO> collect = ret.stream().map(groupQuestionMapper::toDto).collect(Collectors.toList());
        collect.sort(new Comparator<QuestionGroupDTO>(){

            @Override
            public int compare(QuestionGroupDTO o1, QuestionGroupDTO o2) {
                return o1.getQuestionNumber().compareTo(o2.getQuestionNumber());
            }
        });
        return Optional.ofNullable(collect);
	}
}
