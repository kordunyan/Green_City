package com.trash.green.city.repository;

import com.trash.green.city.domain.TrashExportation;
import com.trash.green.city.domain.TrashExportationReport;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.List;
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

    List<TrashExportation> findAllByOsbbIdOrderByDateDesc(Long id);

    @Query(
        nativeQuery = true,
        value = "SELECT SUM(t.weight) as weight,  o.name as name, o.address as address, t.trash_type as trash_type  FROM trash_exportation t " +
        "JOIN osbb o ON o.id = t.osbb_id " +
        "WHERE t.is_wash = false " +
        "GROUP BY o.name, o.address , t.trash_type"
    )
    @SuppressWarnings("unchecked")
    List<Object[]> findAllGr();
}
