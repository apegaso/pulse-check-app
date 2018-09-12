package com.ncr.project.pulsecheck.web.rest.vm;

import java.util.List;

import com.ncr.project.pulsecheck.service.dto.EventDTO;

/**
 * View Model object for storing a user's events.
 */
public class UserEventsVM {

    private List<UserEventVM> participant;
    private List<EventDTO> lead;
    /**
     * @param participant the participant to set
     */
    public void setParticipant(List<UserEventVM> participant) {
        this.participant = participant;
    }
    
    /**
     * @return the participant
     */
    public List<UserEventVM> getParticipant() {
        return participant;
    }
    

    /**
     * @return the lead
     */
    public List<EventDTO> getLead() {
        return lead;
    }

    /**
     * @param lead the lead to set
     */
    public void setLead(List<EventDTO> lead) {
        this.lead = lead;
    }
    
}
