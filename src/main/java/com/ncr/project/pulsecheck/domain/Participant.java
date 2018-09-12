package com.ncr.project.pulsecheck.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Participant.
 */
@Entity
@Table(name = "participant")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Participant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @OneToOne
    @JoinColumn(unique = true)
    private UserExt userExt;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "participant_events",
               joinColumns = @JoinColumn(name = "participants_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "events_id", referencedColumnName = "id"))
    private Set<Event> events = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserExt getUserExt() {
        return userExt;
    }

    public Participant userExt(UserExt userExt) {
        this.userExt = userExt;
        return this;
    }

    public void setUserExt(UserExt userExt) {
        this.userExt = userExt;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public Participant events(Set<Event> events) {
        this.events = events;
        return this;
    }

    public Participant addEvents(Event event) {
        this.events.add(event);
        event.getParticipants().add(this);
        return this;
    }

    public Participant removeEvents(Event event) {
        this.events.remove(event);
        event.getParticipants().remove(this);
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
        Participant participant = (Participant) o;
        if (participant.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), participant.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Participant{" +
            "id=" + getId() +
            "}";
    }
}
