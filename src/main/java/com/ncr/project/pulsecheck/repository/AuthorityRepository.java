package com.ncr.project.pulsecheck.repository;

import java.util.List;

import com.ncr.project.pulsecheck.domain.Authority;
import com.ncr.project.pulsecheck.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {

  
}
