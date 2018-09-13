package com.ncr.project.pulsecheck.service.mapper;

import java.util.stream.Collectors;

import com.ncr.project.pulsecheck.domain.*;
import com.ncr.project.pulsecheck.web.rest.vm.OrganizationAndAdminVM;
import com.ncr.project.pulsecheck.web.rest.vm.OrganizationAndEventsVM;
import com.ncr.project.pulsecheck.web.rest.vm.UserEmailVM;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for the entity Organization and its DTO OrganizationDTO.
 */
@Mapper(componentModel = "spring", uses = {EventMapper.class})
public interface OrganizationAndAdminsVMMapper extends EntityMapper<OrganizationAndAdminVM, Organization> {

    EventMapper eventMapper = Mappers.getMapper(EventMapper.class);
    OrganizationMapper organizationMapper = Mappers.getMapper(OrganizationMapper.class);

    @Mapping(target = "events", ignore = true)
    @Mapping(target = "users", ignore = true)
    @Mapping(target = "admins", ignore = true)
    Organization toEntity(OrganizationAndEventsVM organizationDTO);

 

    default OrganizationAndAdminVM toDto(Organization organization) {
        OrganizationAndAdminVM ret = new OrganizationAndAdminVM(organizationMapper.toDto(organization));
        ret.setAdmins(organization.getAdmins().stream().map(a->new UserEmailVM(a.getUserExt().getId(),a.getUserExt().getEmail())).collect(Collectors.toList()));
        return ret;
    }

}
