package com.ncr.project.pulsecheck.service.impl;

import com.ncr.project.pulsecheck.service.QuestionCategoryService;
import com.ncr.project.pulsecheck.domain.QuestionCategory;
import com.ncr.project.pulsecheck.repository.QuestionCategoryRepository;
import com.ncr.project.pulsecheck.service.dto.QuestionCategoryDTO;
import com.ncr.project.pulsecheck.service.mapper.QuestionCategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing QuestionCategory.
 */
@Service
@Transactional
public class QuestionCategoryServiceImpl implements QuestionCategoryService {

    private final Logger log = LoggerFactory.getLogger(QuestionCategoryServiceImpl.class);

    private final QuestionCategoryRepository questionCategoryRepository;

    private final QuestionCategoryMapper questionCategoryMapper;

    public QuestionCategoryServiceImpl(QuestionCategoryRepository questionCategoryRepository, QuestionCategoryMapper questionCategoryMapper) {
        this.questionCategoryRepository = questionCategoryRepository;
        this.questionCategoryMapper = questionCategoryMapper;
    }

    /**
     * Save a questionCategory.
     *
     * @param questionCategoryDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public QuestionCategoryDTO save(QuestionCategoryDTO questionCategoryDTO) {
        log.debug("Request to save QuestionCategory : {}", questionCategoryDTO);
        QuestionCategory questionCategory = questionCategoryMapper.toEntity(questionCategoryDTO);
        questionCategory = questionCategoryRepository.save(questionCategory);
        return questionCategoryMapper.toDto(questionCategory);
    }

    /**
     * Get all the questionCategories.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<QuestionCategoryDTO> findAll() {
        log.debug("Request to get all QuestionCategories");
        return questionCategoryRepository.findAll().stream()
            .map(questionCategoryMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one questionCategory by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<QuestionCategoryDTO> findOne(Long id) {
        log.debug("Request to get QuestionCategory : {}", id);
        return questionCategoryRepository.findById(id)
            .map(questionCategoryMapper::toDto);
    }

    /**
     * Delete the questionCategory by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete QuestionCategory : {}", id);
        questionCategoryRepository.deleteById(id);
    }
}
