package com.ncr.project.pulsecheck.service.dto;

import java.util.Set;

/**
 * A DTO for the Event entity.
 */
public class EventExtendedDTO extends EventDTO {

    private static final long serialVersionUID = 1L;
    private Set<UserExtDTO> clientLeads;
    private Set<UserExtDTO> participants;

    public EventExtendedDTO(EventDTO eventDtoNew) {
        super();
        this.setEventDate(eventDtoNew.getEventDate());
        this.setClosed(eventDtoNew.isClosed());
        this.setEventDescription(eventDtoNew.getEventDescription());
        this.setEventName(eventDtoNew.getEventName());
        this.setId(eventDtoNew.getId());
        this.setOrganizationId(eventDtoNew.getOrganizationId());
        this.setOrganizationOrganizationName(eventDtoNew.getOrganizationOrganizationName());
    }
    public EventExtendedDTO() {
        super();
	}

	/**
     * @return the participants
     */
    public Set<UserExtDTO> getParticipants() {
        return participants;
    }

    /**
     * @param participants the participants to set
     */
    public void setParticipants(Set<UserExtDTO> participants) {
        this.participants = participants;
    }

    /**
     * @return the clientLeads
     */
    public Set<UserExtDTO> getClientLeads() {
        return clientLeads;
    }

    /**
     * @param clientLeads the clientLeads to set
     */
    public void setClientLeads(Set<UserExtDTO> clientLeads) {
        this.clientLeads = clientLeads;
    }

    @Override
    public String toString() {
        return "EventExtendedDTO{" +
            "EventDTO=" + super.toString() +
            ", clientLeads='" + getClientLeads() + "'" +
            ", participants='" + getParticipants() + "'" +
            "}";
    }
}
