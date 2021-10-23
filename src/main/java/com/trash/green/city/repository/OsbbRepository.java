package com.trash.green.city.repository;

import com.trash.green.city.domain.Osbb;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Osbb entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OsbbRepository extends JpaRepository<Osbb, Long> {}
