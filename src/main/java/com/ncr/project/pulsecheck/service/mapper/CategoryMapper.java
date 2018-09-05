package com.ncr.project.pulsecheck.service.mapper;

import com.ncr.project.pulsecheck.domain.*;
import com.ncr.project.pulsecheck.service.dto.CategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Category and its DTO CategoryDTO.
 */
@Mapper(componentModel = "spring", uses = {CategoryLevelMapper.class})
public interface CategoryMapper extends EntityMapper<CategoryDTO, Category> {

    @Mapping(source = "father.id", target = "fatherId")
    @Mapping(source = "father.label", target = "fatherLabel")
    @Mapping(source = "level.id", target = "levelId")
    @Mapping(source = "level.label", target = "levelLabel")
    CategoryDTO toDto(Category category);

    @Mapping(source = "fatherId", target = "father")
    @Mapping(source = "levelId", target = "level")
    @Mapping(target = "sons", ignore = true)
    @Mapping(target = "questions", ignore = true)
    Category toEntity(CategoryDTO categoryDTO);

    default Category fromId(Long id) {
        if (id == null) {
            return null;
        }
        Category category = new Category();
        category.setId(id);
        return category;
    }
}
