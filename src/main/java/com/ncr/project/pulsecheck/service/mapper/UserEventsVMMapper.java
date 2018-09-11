package com.ncr.project.pulsecheck.service.mapper;

import java.util.Optional;
import java.util.stream.Collectors;


import com.ncr.project.pulsecheck.domain.UserExt;
import com.ncr.project.pulsecheck.web.rest.vm.UserEventsVM;

import org.springframework.stereotype.Service;


/**
 * Mapper for the entity User and its DTO called UserDTO.
 *
 * Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct
 * support is still in beta, and requires a manual step with an IDE.
 */
@Service
public class UserEventsVMMapper {

    private final EventMapper eventMapper;
    private final UserEventVMMapper userEventVMMapper;

    public UserEventsVMMapper(EventMapper eventMapper, UserEventVMMapper userEventVMMapper){
        this.eventMapper = eventMapper;
        this.userEventVMMapper = userEventVMMapper;
    }

    public UserEventsVM userToUserEventsVM(UserExt user) {
        UserEventsVM ret = new UserEventsVM();
        Optional.ofNullable(user.getParticipant()).ifPresent(c -> ret.setParticipant(c.getEvents().stream().map(e -> {
            return userEventVMMapper.userAndEventToUserEventVM(c, e);
        }).collect(Collectors.toList())));
        Optional.ofNullable(user.getClientLead()).ifPresent(c -> ret.setLead(c.getEvents().stream().map(eventMapper::toDto).collect(Collectors.toList())));
        
        return ret;
    }
    

}
