package com.ncr.project.pulsecheck.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the ClientLead entity.
 */
public class ClientLeadDTO implements Serializable {

    private Long id;

    private Set<EventDTO> events = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<EventDTO> getEvents() {
        return events;
    }

    public void setEvents(Set<EventDTO> events) {
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

        ClientLeadDTO clientLeadDTO = (ClientLeadDTO) o;
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
            "}";
    }
}
