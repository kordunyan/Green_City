package com.trash.green.city.repository;

import com.trash.green.city.domain.FullTrashImages;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the FullTrashImages entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FullTrashImagesRepository extends JpaRepository<FullTrashImages, Long> {}
