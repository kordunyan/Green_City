package com.trash.green.city.repository;

import com.trash.green.city.domain.TrashCompany;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TrashCompany entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrashCompanyRepository extends JpaRepository<TrashCompany, Long> {}
