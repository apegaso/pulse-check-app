package com.ncr.project.pulsecheck.repository;

import com.ncr.project.pulsecheck.domain.Organization;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Organization entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
	Optional<Organization> findByOrganizationName(String organizationName);
}
