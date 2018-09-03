package com.ncr.project.pulsecheck.service.mapper;

import com.ncr.project.pulsecheck.domain.*;
import com.ncr.project.pulsecheck.service.dto.QuestionCategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity QuestionCategory and its DTO QuestionCategoryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface QuestionCategoryMapper extends EntityMapper<QuestionCategoryDTO, QuestionCategory> {

    @Mapping(source = "father.id", target = "fatherId")
    @Mapping(source = "father.id", target = "fatherId")
    QuestionCategoryDTO toDto(QuestionCategory questionCategory);

    @Mapping(target = "soons", ignore = true)
    @Mapping(source = "fatherId", target = "father")
    @Mapping(source = "fatherId", target = "father")
    @Mapping(target = "soons", ignore = true)
    @Mapping(target = "questions", ignore = true)
    QuestionCategory toEntity(QuestionCategoryDTO questionCategoryDTO);

    default QuestionCategory fromId(Long id) {
        if (id == null) {
            return null;
        }
        QuestionCategory questionCategory = new QuestionCategory();
        questionCategory.setId(id);
        return questionCategory;
    }
}
