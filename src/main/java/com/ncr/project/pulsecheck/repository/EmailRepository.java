package com.ncr.project.pulsecheck.repository;

import java.util.Optional;

import javax.persistence.QueryHint;

import com.ncr.project.pulsecheck.domain.Email;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Email entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {
    @Query("select email from Email email where email.isSent =:isSent order by email.dateInsert desc")
    Optional<Email> findFistByIsSent(@Param("isSent") boolean isSent);
}
