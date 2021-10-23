package com.trash.green.city.service;

import com.trash.green.city.repository.TrashExportationRepository;
import com.trash.green.city.service.dto.TrashExportationDTO;
import com.trash.green.city.service.mapper.TrashExportationMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CSVService {

    private final Logger log = LoggerFactory.getLogger(TrashExportationService.class);

    private final TrashExportationRepository trashExportationRepository;

    private final TrashExportationMapper trashExportationMapper;

    public CSVService(TrashExportationRepository trashExportationRepository, TrashExportationMapper trashExportationMapper) {
        this.trashExportationRepository = trashExportationRepository;
        this.trashExportationMapper = trashExportationMapper;
    }

    @Transactional(readOnly = true)
    public List<TrashExportationDTO> findAllByOsbb(String lessThan, String greaterThan, Integer id) {
        log.debug("Request to get findAllByOsbb");
        return trashExportationRepository
            .findAllByDurationAndOsbbId(lessThan, greaterThan, id)
            .stream()
            .map(trashExportationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Transactional(readOnly = true)
    public List<TrashExportationDTO> findAllBroupedByOsbbId(String lessThan, String endTime) {
        log.debug("Request to findAllBroupedByOsbbId ");
        return trashExportationRepository
            .findAllByDuration(lessThan, endTime)
            .stream()
            .map(trashExportationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
}
