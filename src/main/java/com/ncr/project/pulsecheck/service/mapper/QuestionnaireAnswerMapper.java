package com.ncr.project.pulsecheck.service.mapper;

import com.ncr.project.pulsecheck.domain.*;
import com.ncr.project.pulsecheck.service.dto.QuestionnaireAnswerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity QuestionnaireAnswer and its DTO QuestionnaireAnswerDTO.
 */
@Mapper(componentModel = "spring", uses = {QuestionnaireMapper.class, QuestionMapper.class})
public interface QuestionnaireAnswerMapper extends EntityMapper<QuestionnaireAnswerDTO, QuestionnaireAnswer> {

    @Mapping(source = "questionnaire.id", target = "questionnaireId")
    @Mapping(source = "question.id", target = "questionId")
    QuestionnaireAnswerDTO toDto(QuestionnaireAnswer questionnaireAnswer);

    @Mapping(source = "questionnaireId", target = "questionnaire")
    @Mapping(source = "questionId", target = "question")
    QuestionnaireAnswer toEntity(QuestionnaireAnswerDTO questionnaireAnswerDTO);

    default QuestionnaireAnswer fromId(Long id) {
        if (id == null) {
            return null;
        }
        QuestionnaireAnswer questionnaireAnswer = new QuestionnaireAnswer();
        questionnaireAnswer.setId(id);
        return questionnaireAnswer;
    }
}
