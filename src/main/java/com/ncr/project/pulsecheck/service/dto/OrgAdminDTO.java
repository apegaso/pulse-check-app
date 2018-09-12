package com.ncr.project.pulsecheck.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the OrgAdmin entity.
 */
public class OrgAdminDTO implements Serializable {

    private Long id;

    private Long userExtId;

    private Set<OrganizationDTO> organizations = new HashSet<>();

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

    public Set<OrganizationDTO> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(Set<OrganizationDTO> organizations) {
        this.organizations = organizations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrgAdminDTO orgAdminDTO = (OrgAdminDTO) o;
        if (orgAdminDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orgAdminDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrgAdminDTO{" +
            "id=" + getId() +
            ", userExt=" + getUserExtId() +
            "}";
    }
}
