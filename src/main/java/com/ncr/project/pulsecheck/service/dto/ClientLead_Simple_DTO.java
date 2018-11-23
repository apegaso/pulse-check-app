package com.ncr.project.pulsecheck.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the ClientLead entity.
 */
public class ClientLead_Simple_DTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long userExtId;

    private Set<EventIdDTO> events = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserExtId() {
        return userExtId;
    }

    public void setUserExtId(Long userExtId) {
        this.userExtId = userExtId;
    }

    public Set<EventIdDTO> getEvents() {
        return events;
    }

    public void setEvents(Set<EventIdDTO> events) {
        this.events = events;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClientLead_Simple_DTO clientLeadDTO = (ClientLead_Simple_DTO) o;
        if (clientLeadDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clientLeadDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClientLeadDTO{" +
            "id=" + getId() +
            ", userExt=" + getUserExtId() +
            "}";
    }
}
