package com.ncr.project.pulsecheck.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the UserExt entity.
 */
public class UserExtDTO implements Serializable {

    private Long id;

    @Size(max = 255)
    private String jobRole;

    @NotNull
    private String email;

    private Long userId;

    private Long organizationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJobRole() {
        return jobRole;
    }

    public void setJobRole(String jobRole) {
        this.jobRole = jobRole;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserExtDTO userExtDTO = (UserExtDTO) o;
        if (userExtDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userExtDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserExtDTO{" +
            "id=" + getId() +
            ", jobRole='" + getJobRole() + "'" +
            ", email='" + getEmail() + "'" +
            ", user=" + getUserId() +
            ", organization=" + getOrganizationId() +
            "}";
    }
}
