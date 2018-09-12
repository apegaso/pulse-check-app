package com.ncr.project.pulsecheck.repository;

import com.ncr.project.pulsecheck.domain.UserExt;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the UserExt entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserExtRepository extends JpaRepository<UserExt, Long> {
	Optional<UserExt> findByEmail(String email1);
}
