package com.ncr.project.pulsecheck.service.impl;

import com.ncr.project.pulsecheck.service.CategoryLevelService;
import com.google.common.collect.Lists;
import com.ncr.project.pulsecheck.domain.Category;
import com.ncr.project.pulsecheck.domain.CategoryLevel;
import com.ncr.project.pulsecheck.repository.CategoryLevelRepository;
import com.ncr.project.pulsecheck.repository.CategoryRepository;
import com.ncr.project.pulsecheck.service.dto.CategoryDTO;
import com.ncr.project.pulsecheck.service.dto.CategoryLevelDTO;
import com.ncr.project.pulsecheck.service.dto.CategoryLevelDetailsVM;
import com.ncr.project.pulsecheck.service.mapper.CategoryLevelMapper;
import com.ncr.project.pulsecheck.service.mapper.CategoryMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing CategoryLevel.
 */
@Service
@Transactional
public class CategoryLevelServiceImpl implements CategoryLevelService {

    private final Logger log = LoggerFactory.getLogger(CategoryLevelServiceImpl.class);

    private final CategoryLevelRepository categoryLevelRepository;
    private final CategoryRepository categoryRepository;

    private final CategoryLevelMapper categoryLevelMapper;
    private final CategoryMapper categoryMapper;

    public CategoryLevelServiceImpl(CategoryLevelRepository categoryLevelRepository, CategoryLevelMapper categoryLevelMapper, CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryLevelRepository = categoryLevelRepository;
        this.categoryLevelMapper = categoryLevelMapper;
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    /**
     * Save a categoryLevel.
     *
     * @param categoryLevelDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CategoryLevelDTO save(CategoryLevelDTO categoryLevelDTO) {
        log.debug("Request to save CategoryLevel : {}", categoryLevelDTO);
        CategoryLevel categoryLevel = categoryLevelMapper.toEntity(categoryLevelDTO);
        categoryLevel = categoryLevelRepository.save(categoryLevel);
        return categoryLevelMapper.toDto(categoryLevel);
    }

    /**
     * Get all the categoryLevels.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CategoryLevelDTO> findAll() {
        log.debug("Request to get all CategoryLevels");
        return categoryLevelRepository.findAll().stream()
            .map(categoryLevelMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one categoryLevel by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CategoryLevelDTO> findOne(Long id) {
        log.debug("Request to get CategoryLevel : {}", id);
        return categoryLevelRepository.findById(id)
            .map(categoryLevelMapper::toDto);
    }

    /**
     * Delete the categoryLevel by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CategoryLevel : {}", id);
        categoryLevelRepository.deleteById(id);
    }

    @Override
    public Optional<CategoryLevelDetailsVM> findOneWithDetails(Long id) {
        CategoryLevelDetailsVM retDTO = null;
        Optional<CategoryLevel> findById = categoryLevelRepository.findById(id);
        if(findById.isPresent()){
            CategoryLevel categoryLevel = findById.get();
            Set<Category> categories = categoryLevel.getCategories();
            List<CategoryDTO> ret = Lists.newArrayList();
            for(Category c : categories) {
                CategoryDTO dto = categoryMapper.toDto(c);
                ret.add(dto);
            }
            retDTO = new CategoryLevelDetailsVM();
            retDTO.setCategories(ret);
            retDTO.setId(categoryLevel.getId());
            retDTO.setLabel(categoryLevel.getLabel());
        }
        return Optional.ofNullable(retDTO);
	}
}
