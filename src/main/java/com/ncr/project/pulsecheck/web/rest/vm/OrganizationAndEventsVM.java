package com.ncr.project.pulsecheck.web.rest.vm;

import java.util.List;

import com.ncr.project.pulsecheck.service.dto.EventDTO;
import com.ncr.project.pulsecheck.service.dto.OrganizationDTO;

/**
 * View Model object for storing a Organization details including events details.
 */
public class OrganizationAndEventsVM extends OrganizationDTO {
    private static final long serialVersionUID = 1L;
    private List<EventDTO> events;
    public OrganizationAndEventsVM(){}
    public OrganizationAndEventsVM(OrganizationDTO dto) {
        this.setOrganizationName(dto.getOrganizationName());
        this.setId(dto.getId());
	}
    
    /**
     * @param events the events to set
     */
    public void setEvents(List<EventDTO> events) {
        this.events = events;
    }
    /**
     * @return the events
     */
    public List<EventDTO> getEvents() {
        return events;
    }
    
}
