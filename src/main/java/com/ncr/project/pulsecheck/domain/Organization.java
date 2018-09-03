package com.ncr.project.pulsecheck.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Organization.
 */
@Entity
@Table(name = "organization")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Organization implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "organization_name")
    private String organizationName;

    @ManyToOne
    @JsonIgnoreProperties("organizations")
    private Event events;

    @ManyToMany(mappedBy = "organizations")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<OrgAdmin> admins = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public Organization organizationName(String organizationName) {
        this.organizationName = organizationName;
        return this;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public Event getEvents() {
        return events;
    }

    public Organization events(Event event) {
        this.events = event;
        return this;
    }

    public void setEvents(Event event) {
        this.events = event;
    }

    public Set<OrgAdmin> getAdmins() {
        return admins;
    }

    public Organization admins(Set<OrgAdmin> orgAdmins) {
        this.admins = orgAdmins;
        return this;
    }

    public Organization addAdmins(OrgAdmin orgAdmin) {
        this.admins.add(orgAdmin);
        orgAdmin.getOrganizations().add(this);
        return this;
    }

    public Organization removeAdmins(OrgAdmin orgAdmin) {
        this.admins.remove(orgAdmin);
        orgAdmin.getOrganizations().remove(this);
        return this;
    }

    public void setAdmins(Set<OrgAdmin> orgAdmins) {
        this.admins = orgAdmins;
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
        Organization organization = (Organization) o;
        if (organization.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), organization.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Organization{" +
            "id=" + getId() +
            ", organizationName='" + getOrganizationName() + "'" +
            "}";
    }
}
