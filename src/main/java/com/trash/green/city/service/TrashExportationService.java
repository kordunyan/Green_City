package com.trash.green.city.service;

import com.trash.green.city.domain.TrashExportation;
import com.trash.green.city.repository.TrashExportationRepository;
import com.trash.green.city.service.dto.TrashExportationDTO;
import com.trash.green.city.service.mapper.TrashExportationMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TrashExportation}.
 */
@Service
@Transactional
public class TrashExportationService {

    private final Logger log = LoggerFactory.getLogger(TrashExportationService.class);

    private final TrashExportationRepository trashExportationRepository;

    private final TrashExportationMapper trashExportationMapper;

    public TrashExportationService(TrashExportationRepository trashExportationRepository, TrashExportationMapper trashExportationMapper) {
        this.trashExportationRepository = trashExportationRepository;
        this.trashExportationMapper = trashExportationMapper;
    }

    /**
     * Save a trashExportation.
     *
     * @param trashExportationDTO the entity to save.
     * @return the persisted entity.
     */
    public TrashExportationDTO save(TrashExportationDTO trashExportationDTO) {
        log.debug("Request to save TrashExportation : {}", trashExportationDTO);
        TrashExportation trashExportation = trashExportationMapper.toEntity(trashExportationDTO);
        trashExportation = trashExportationRepository.save(trashExportation);
        return trashExportationMapper.toDto(trashExportation);
    }

    /**
     * Partially update a trashExportation.
     *
     * @param trashExportationDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TrashExportationDTO> partialUpdate(TrashExportationDTO trashExportationDTO) {
        log.debug("Request to partially update TrashExportation : {}", trashExportationDTO);

        return trashExportationRepository
            .findById(trashExportationDTO.getId())
            .map(existingTrashExportation -> {
                trashExportationMapper.partialUpdate(existingTrashExportation, trashExportationDTO);

                return existingTrashExportation;
            })
            .map(trashExportationRepository::save)
            .map(trashExportationMapper::toDto);
    }

    /**
     * Get all the trashExportations.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TrashExportationDTO> findAll() {
        log.debug("Request to get all TrashExportations");
        return trashExportationRepository
            .findAll()
            .stream()
            .map(trashExportationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one trashExportation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TrashExportationDTO> findOne(Long id) {
        log.debug("Request to get TrashExportation : {}", id);
        return trashExportationRepository.findById(id).map(trashExportationMapper::toDto);
    }

    /**
     * Delete the trashExportation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TrashExportation : {}", id);
        trashExportationRepository.deleteById(id);
    }
}
