package com.ncr.project.pulsecheck.service.mapper;

import com.ncr.project.pulsecheck.domain.*;
import com.ncr.project.pulsecheck.service.dto.QuestionCategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity QuestionCategory and its DTO QuestionCategoryDTO.
 */
@Mapper(componentModel = "spring", uses = {QuestionMapper.class})
public interface QuestionCategoryMapper extends EntityMapper<QuestionCategoryDTO, QuestionCategory> {

    @Mapping(source = "soons.id", target = "soonsId")
    @Mapping(source = "questions.id", target = "questionsId")
    QuestionCategoryDTO toDto(QuestionCategory questionCategory);

    @Mapping(source = "soonsId", target = "soons")
    @Mapping(source = "questionsId", target = "questions")
    @Mapping(target = "fathers", ignore = true)
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
