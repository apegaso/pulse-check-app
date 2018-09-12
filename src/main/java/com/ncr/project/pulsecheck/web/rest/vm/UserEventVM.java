package com.ncr.project.pulsecheck.web.rest.vm;

import com.ncr.project.pulsecheck.domain.EventStatus;
import com.ncr.project.pulsecheck.service.dto.EventDTO;

/**
 * View Model object for storing a user's events details.
 */
public class UserEventVM extends EventDTO {
    private static final long serialVersionUID = 1L;
    EventStatus status;

    public UserEventVM(EventDTO dto) {
        this.setClosed(dto.isClosed());
        this.setEventDate(dto.getEventDate());
        this.setEventDescription(dto.getEventDescription());
        this.setEventName(dto.getEventDescription());
        this.setOrganizationOrganizationName(dto.getOrganizationOrganizationName());
        this.setOrganizationId(dto.getOrganizationId());
        this.setId(dto.getId());
	}
	/**
     * @param status the status to set
     */
    public void setStatus(EventStatus status) {
        this.status = status;
    }
    /**
     * @return the status
     */
    public EventStatus getStatus() {
        return status;
    }
    
}
