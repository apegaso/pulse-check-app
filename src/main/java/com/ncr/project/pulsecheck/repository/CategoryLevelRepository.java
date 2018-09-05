package com.ncr.project.pulsecheck.repository;

import com.ncr.project.pulsecheck.domain.CategoryLevel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CategoryLevel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategoryLevelRepository extends JpaRepository<CategoryLevel, Long> {

}
