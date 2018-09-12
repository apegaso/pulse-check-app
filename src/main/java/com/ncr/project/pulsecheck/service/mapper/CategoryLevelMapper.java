package com.ncr.project.pulsecheck.service.mapper;

import com.ncr.project.pulsecheck.domain.*;
import com.ncr.project.pulsecheck.service.dto.CategoryLevelDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CategoryLevel and its DTO CategoryLevelDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CategoryLevelMapper extends EntityMapper<CategoryLevelDTO, CategoryLevel> {


    @Mapping(target = "categories", ignore = true)
    CategoryLevel toEntity(CategoryLevelDTO categoryLevelDTO);

    default CategoryLevel fromId(Long id) {
        if (id == null) {
            return null;
        }
        CategoryLevel categoryLevel = new CategoryLevel();
        categoryLevel.setId(id);
        return categoryLevel;
    }
}
