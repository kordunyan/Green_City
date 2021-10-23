package com.trash.green.city.repository;

import com.trash.green.city.domain.TrashExportation;
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

    @Query(
        value = "SELECT t, o.name FROM trash_exportation t " +
        "JOIN osbb o on o.id = t.osbb_id" +
        " WHERE  t.createdDate < greaterThan AND t.createdDate > lessThen GROUP BY t.osbb_id",
        nativeQuery = true
    )
    List<TrashExportation> findAllByDuration(String lessThen, String greaterThan);

    @Query(
        value = "SELECT t, o.name FROM trash_exportation t " +
        "JOIN osbb o on o.id = t.osbb_id" +
        " WHERE  t.createdDate < greaterThan AND t.createdDate > lessThen AND t.osbb_id = id",
        nativeQuery = true
    )
    List<TrashExportation> findAllByDurationAndOsbbId(String lessThen, String greaterThan, int id);
}
