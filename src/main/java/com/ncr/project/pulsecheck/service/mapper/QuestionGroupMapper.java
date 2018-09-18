package com.ncr.project.pulsecheck.service.mapper;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.ncr.project.pulsecheck.domain.*;
import com.ncr.project.pulsecheck.service.dto.QuestionGroupDTO;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for the entity QuestionGroup and its DTO QuestionGroupDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface QuestionGroupMapper extends EntityMapper<QuestionGroupDTO, QuestionGroup> {

    QuestionMapper questionMapper = Mappers.getMapper(QuestionMapper.class);

    @Mapping(target = "questions", ignore = true)
    QuestionGroup toEntity(QuestionGroupDTO questionGroupDTO);

    @Override
    default QuestionGroupDTO toDto(QuestionGroup entity) {
        QuestionGroupDTO ret =new QuestionGroupDTO();
        ret.setId(entity.getId());
        ret.setQuestionNumber(entity.getQuestionNumber());
        ret.setQuestions(entity.getQuestions().stream().map(questionMapper::toDto).collect(Collectors.toSet()));
        return ret;
    }

    default QuestionGroup fromId(Long id) {
        if (id == null) {
            return null;
        }
        QuestionGroup questionGroup = new QuestionGroup();
        questionGroup.setId(id);
        return questionGroup;
    }
}