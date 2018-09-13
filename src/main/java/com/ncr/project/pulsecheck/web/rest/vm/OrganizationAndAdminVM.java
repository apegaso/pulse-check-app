package com.ncr.project.pulsecheck.web.rest.vm;

import java.util.List;

import com.ncr.project.pulsecheck.service.dto.OrganizationDTO;

/**
 * View Model object for storing a Organization details including admins
 * details.
 */
public class OrganizationAndAdminVM extends OrganizationDTO {

    private static final long serialVersionUID = 1L;
 
    private List<UserEmailVM> admins;
    public OrganizationAndAdminVM(){}
    public OrganizationAndAdminVM(OrganizationDTO dto) {
        this.setOrganizationName(dto.getOrganizationName());
        this.setId(dto.getId());
	}

    /**
     * @return the admins
     */
    public List<UserEmailVM> getAdmins() {
        return admins;
    }

    /**
     * @param admins the admins to set
     */
    public void setAdmins(List<UserEmailVM> admins) {
        this.admins = admins;
    }

    @Override
    public String toString() {
        return "OrganizationAndAdminVM{" +
            "id=" + getId() +
            ", organizationName='" + getOrganizationName() + "'" +
            ", admins=" + getAdmins() + "" +
            "}";
    }
}