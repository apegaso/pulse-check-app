package com.ncr.project.pulsecheck.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Event.
 */
@Entity
@Table(name = "event")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "event_name")
    private String eventName;

    @Column(name = "event_date")
    private Instant eventDate;

    @OneToMany(mappedBy = "events")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Organization> organizations = new HashSet<>();

    @ManyToMany(mappedBy = "events")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Participant> participants = new HashSet<>();

    @ManyToMany(mappedBy = "events")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ClientLead> leads = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public Event eventName(String eventName) {
        this.eventName = eventName;
        return this;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Instant getEventDate() {
        return eventDate;
    }

    public Event eventDate(Instant eventDate) {
        this.eventDate = eventDate;
        return this;
    }

    public void setEventDate(Instant eventDate) {
        this.eventDate = eventDate;
    }

    public Set<Organization> getOrganizations() {
        return organizations;
    }

    public Event organizations(Set<Organization> organizations) {
        this.organizations = organizations;
        return this;
    }

    public Event addOrganization(Organization organization) {
        this.organizations.add(organization);
        organization.setEvents(this);
        return this;
    }

    public Event removeOrganization(Organization organization) {
        this.organizations.remove(organization);
        organization.setEvents(null);
        return this;
    }

    public void setOrganizations(Set<Organization> organizations) {
        this.organizations = organizations;
    }

    public Set<Participant> getParticipants() {
        return participants;
    }

    public Event participants(Set<Participant> participants) {
        this.participants = participants;
        return this;
    }

    public Event addParticipants(Participant participant) {
        this.participants.add(participant);
        participant.getEvents().add(this);
        return this;
    }

    public Event removeParticipants(Participant participant) {
        this.participants.remove(participant);
        participant.getEvents().remove(this);
        return this;
    }

    public void setParticipants(Set<Participant> participants) {
        this.participants = participants;
    }

    public Set<ClientLead> getLeads() {
        return leads;
    }

    public Event leads(Set<ClientLead> clientLeads) {
        this.leads = clientLeads;
        return this;
    }

    public Event addLeads(ClientLead clientLead) {
        this.leads.add(clientLead);
        clientLead.getEvents().add(this);
        return this;
    }

    public Event removeLeads(ClientLead clientLead) {
        this.leads.remove(clientLead);
        clientLead.getEvents().remove(this);
        return this;
    }

    public void setLeads(Set<ClientLead> clientLeads) {
        this.leads = clientLeads;
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
        Event event = (Event) o;
        if (event.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), event.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Event{" +
            "id=" + getId() +
            ", eventName='" + getEventName() + "'" +
            ", eventDate='" + getEventDate() + "'" +
            "}";
    }
}