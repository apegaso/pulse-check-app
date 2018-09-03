package com.ncr.project.pulsecheck.service.mapper;

import com.ncr.project.pulsecheck.domain.*;
import com.ncr.project.pulsecheck.service.dto.QuestionnaireDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Questionnaire and its DTO QuestionnaireDTO.
 */
@Mapper(componentModel = "spring", uses = {EventMapper.class, ParticipantMapper.class})
public interface QuestionnaireMapper extends EntityMapper<QuestionnaireDTO, Questionnaire> {

    @Mapping(source = "event.id", target = "eventId")
    @Mapping(source = "participant.id", target = "participantId")
    QuestionnaireDTO toDto(Questionnaire questionnaire);

    @Mapping(source = "eventId", target = "event")
    @Mapping(source = "participantId", target = "participant")
    Questionnaire toEntity(QuestionnaireDTO questionnaireDTO);

    default Questionnaire fromId(Long id) {
        if (id == null) {
            return null;
        }
        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setId(id);
        return questionnaire;
    }
}
