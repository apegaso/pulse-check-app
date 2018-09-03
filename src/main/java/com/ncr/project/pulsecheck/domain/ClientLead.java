package com.ncr.project.pulsecheck.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ClientLead.
 */
@Entity
@Table(name = "client_lead")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ClientLead implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "client_lead_events",
               joinColumns = @JoinColumn(name = "client_leads_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "events_id", referencedColumnName = "id"))
    private Set<Event> events = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public ClientLead events(Set<Event> events) {
        this.events = events;
        return this;
    }

    public ClientLead addEvents(Event event) {
        this.events.add(event);
        event.getLeads().add(this);
        return this;
    }

    public ClientLead removeEvents(Event event) {
        this.events.remove(event);
        event.getLeads().remove(this);
        return this;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ClientLead clientLead = (ClientLead) o;
        if (clientLead.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clientLead.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClientLead{" +
            "id=" + getId() +
            "}";
    }
}
