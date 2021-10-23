package com.trash.green.city.repository;

import com.trash.green.city.domain.TrashExportation;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TrashExportation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrashExportationRepository extends JpaRepository<TrashExportation, Long> {
    List<TrashExportation> findAllByOsbbId(Long id);
}
