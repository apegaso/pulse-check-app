package com.ncr.project.pulsecheck.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the UserExt entity.
 */
public class UserExtWRelationsDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @Size(max = 255)
    private String jobRole;

    @NotNull
    private String email;

    private Long userId;

    private Long organizationId;

    private String organizationName;

    private ClientLeadDTO clientLead;
    
    private OrgAdminDTO orgAdmin;
    
    private ParticipantDTO participant;

    public Long getId() {
        return id;
    }

    /**
     * @return the participant
     */
    public ParticipantDTO getParticipant() {
        return participant;
    }

    /**
     * @param participant the participant to set
     */
    public void setParticipant(ParticipantDTO participant) {
        this.participant = participant;
    }

    /**
     * @return the orgAdmin
     */
    public OrgAdminDTO getOrgAdmin() {
        return orgAdmin;
    }

    /**
     * @param orgAdmin the orgAdmin to set
     */
    public void setOrgAdmin(OrgAdminDTO orgAdmin) {
        this.orgAdmin = orgAdmin;
    }

    /**
     * @return the clientLead
     */
    public ClientLeadDTO getClientLead() {
        return clientLead;
    }

    /**
     * @param clientLead the clientLead to set
     */
    public void setClientLead(ClientLeadDTO clientLead) {
        this.clientLead = clientLead;
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

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
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
