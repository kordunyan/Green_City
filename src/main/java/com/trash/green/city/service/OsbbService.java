package com.trash.green.city.service;

import com.trash.green.city.domain.Osbb;
import com.trash.green.city.repository.OsbbRepository;
import com.trash.green.city.service.dto.OsbbDTO;
import com.trash.green.city.service.mapper.OsbbMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Osbb}.
 */
@Service
@Transactional
public class OsbbService {

    private final Logger log = LoggerFactory.getLogger(OsbbService.class);

    private final OsbbRepository osbbRepository;

    private final OsbbMapper osbbMapper;

    public OsbbService(OsbbRepository osbbRepository, OsbbMapper osbbMapper) {
        this.osbbRepository = osbbRepository;
        this.osbbMapper = osbbMapper;
    }

    /**
     * Save a osbb.
     *
     * @param osbbDTO the entity to save.
     * @return the persisted entity.
     */
    public OsbbDTO save(OsbbDTO osbbDTO) {
        log.debug("Request to save Osbb : {}", osbbDTO);
        Osbb osbb = osbbMapper.toEntity(osbbDTO);
        osbb = osbbRepository.save(osbb);
        return osbbMapper.toDto(osbb);
    }

    /**
     * Partially update a osbb.
     *
     * @param osbbDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<OsbbDTO> partialUpdate(OsbbDTO osbbDTO) {
        log.debug("Request to partially update Osbb : {}", osbbDTO);

        return osbbRepository
            .findById(osbbDTO.getId())
            .map(existingOsbb -> {
                osbbMapper.partialUpdate(existingOsbb, osbbDTO);

                return existingOsbb;
            })
            .map(osbbRepository::save)
            .map(osbbMapper::toDto);
    }

    /**
     * Get all the osbbs.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<OsbbDTO> findAll() {
        log.debug("Request to get all Osbbs");
        return osbbRepository.findAll().stream().map(osbbMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one osbb by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OsbbDTO> findOne(Long id) {
        log.debug("Request to get Osbb : {}", id);
        return osbbRepository.findById(id).map(osbbMapper::toDto);
    }

    /**
     * Delete the osbb by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Osbb : {}", id);
        osbbRepository.deleteById(id);
    }
}
