package com.ncr.project.pulsecheck.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A OrgAdmin.
 */
@Entity
@Table(name = "org_admin")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OrgAdmin implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "org_admin_organizations",
               joinColumns = @JoinColumn(name = "org_admins_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "organizations_id", referencedColumnName = "id"))
    private Set<Organization> organizations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Organization> getOrganizations() {
        return organizations;
    }

    public OrgAdmin organizations(Set<Organization> organizations) {
        this.organizations = organizations;
        return this;
    }

    public OrgAdmin addOrganizations(Organization organization) {
        this.organizations.add(organization);
        organization.getAdmins().add(this);
        return this;
    }

    public OrgAdmin removeOrganizations(Organization organization) {
        this.organizations.remove(organization);
        organization.getAdmins().remove(this);
        return this;
    }

    public void setOrganizations(Set<Organization> organizations) {
        this.organizations = organizations;
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
        OrgAdmin orgAdmin = (OrgAdmin) o;
        if (orgAdmin.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orgAdmin.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrgAdmin{" +
            "id=" + getId() +
            "}";
    }
}
