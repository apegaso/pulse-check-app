package com.ncr.project.pulsecheck.service.mapper;

import com.ncr.project.pulsecheck.domain.*;
import com.ncr.project.pulsecheck.service.dto.QuestionGroupDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity QuestionGroup and its DTO QuestionGroupDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface QuestionGroupMapper extends EntityMapper<QuestionGroupDTO, QuestionGroup> {


        default QuestionGroup fromId(Long id) {
        if (id == null) {
            return null;
        }
        QuestionGroup questionGroup = new QuestionGroup();
        questionGroup.setId(id);
        return questionGroup;
    }
}
