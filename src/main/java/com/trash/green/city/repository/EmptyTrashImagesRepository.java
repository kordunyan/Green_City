package com.trash.green.city.repository;

import com.trash.green.city.domain.EmptyTrashImages;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the EmptyTrashImages entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmptyTrashImagesRepository extends JpaRepository<EmptyTrashImages, Long> {}
