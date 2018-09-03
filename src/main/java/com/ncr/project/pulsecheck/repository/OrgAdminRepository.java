package com.ncr.project.pulsecheck.repository;

import com.ncr.project.pulsecheck.domain.OrgAdmin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the OrgAdmin entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrgAdminRepository extends JpaRepository<OrgAdmin, Long> {

    @Query(value = "select distinct org_admin from OrgAdmin org_admin left join fetch org_admin.organizations",
        countQuery = "select count(distinct org_admin) from OrgAdmin org_admin")
    Page<OrgAdmin> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct org_admin from OrgAdmin org_admin left join fetch org_admin.organizations")
    List<OrgAdmin> findAllWithEagerRelationships();

    @Query("select org_admin from OrgAdmin org_admin left join fetch org_admin.organizations where org_admin.id =:id")
    Optional<OrgAdmin> findOneWithEagerRelationships(@Param("id") Long id);

}
