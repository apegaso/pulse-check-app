package com.ncr.project.pulsecheck.repository;

import com.ncr.project.pulsecheck.domain.ClientLead;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the ClientLead entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientLeadRepository extends JpaRepository<ClientLead, Long> {

    @Query(value = "select distinct client_lead from ClientLead client_lead left join fetch client_lead.events",
        countQuery = "select count(distinct client_lead) from ClientLead client_lead")
    Page<ClientLead> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct client_lead from ClientLead client_lead left join fetch client_lead.events")
    List<ClientLead> findAllWithEagerRelationships();

    @Query("select client_lead from ClientLead client_lead left join fetch client_lead.events where client_lead.id =:id")
    Optional<ClientLead> findOneWithEagerRelationships(@Param("id") Long id);

    @Query(value = "select distinct client_lead from ClientLead client_lead left join fetch client_lead.events events where events.id =:id")
	List<ClientLead> findAllByEventId(@Param("id") Long id);

}
