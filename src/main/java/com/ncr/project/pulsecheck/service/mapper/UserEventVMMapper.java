package com.ncr.project.pulsecheck.service.mapper;

import java.util.Optional;

import com.ncr.project.pulsecheck.domain.Event;
import com.ncr.project.pulsecheck.domain.EventStatus;
import com.ncr.project.pulsecheck.domain.Participant;
import com.ncr.project.pulsecheck.domain.UserExt;
import com.ncr.project.pulsecheck.service.QuestionnaireService;
import com.ncr.project.pulsecheck.service.dto.QuestionnaireDTO;
import com.ncr.project.pulsecheck.web.rest.vm.UserEventVM;

import org.springframework.stereotype.Service;


/**
 * Mapper for the entity User and its DTO called UserDTO.
 *
 * Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct
 * support is still in beta, and requires a manual step with an IDE.
 */
@Service
public class UserEventVMMapper {

    private final EventMapper eventMapper;
    private final QuestionnaireService questionnaireService;

    public UserEventVMMapper(EventMapper eventMapper, QuestionnaireService questionnaireService){
        this.eventMapper = eventMapper;
        this.questionnaireService = questionnaireService;
    }

    public UserEventVM userAndEventToUserEventVM(Participant user, Event event) {
        UserEventVM ret = new UserEventVM(eventMapper.toDto(event));
        Optional<QuestionnaireDTO> findOneByUserExtAndEvent = questionnaireService.findOneByUserExtAndEvent(user.getId(), event.getId());
        findOneByUserExtAndEvent.ifPresent(q -> {
            if(q.getDateStart() == null) {
                ret.setStatus(EventStatus.NOTSTARTED);
            }else if(q.getDateEnd() == null) {
                ret.setStatus(EventStatus.INPROGRESS);
            }else{
                ret.setStatus(EventStatus.COMPLETED);
            }
        });
        

        //Optional.ofNullable(user.getClientLead()).ifPresent(c -> ret.setLead(c.getEvents().stream().map(eventMapper::toDto).collect(Collectors.toList())));
        
        return ret;
    }
    

}
