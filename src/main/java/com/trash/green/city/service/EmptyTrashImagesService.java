package com.trash.green.city.service;

import com.trash.green.city.domain.EmptyTrashImages;
import com.trash.green.city.repository.EmptyTrashImagesRepository;
import com.trash.green.city.service.dto.EmptyTrashImagesDTO;
import com.trash.green.city.service.mapper.EmptyTrashImagesMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EmptyTrashImages}.
 */
@Service
@Transactional
public class EmptyTrashImagesService {

    private final Logger log = LoggerFactory.getLogger(EmptyTrashImagesService.class);

    private final EmptyTrashImagesRepository emptyTrashImagesRepository;

    private final EmptyTrashImagesMapper emptyTrashImagesMapper;

    public EmptyTrashImagesService(EmptyTrashImagesRepository emptyTrashImagesRepository, EmptyTrashImagesMapper emptyTrashImagesMapper) {
        this.emptyTrashImagesRepository = emptyTrashImagesRepository;
        this.emptyTrashImagesMapper = emptyTrashImagesMapper;
    }

    /**
     * Save a emptyTrashImages.
     *
     * @param emptyTrashImagesDTO the entity to save.
     * @return the persisted entity.
     */
    public EmptyTrashImagesDTO save(EmptyTrashImagesDTO emptyTrashImagesDTO) {
        log.debug("Request to save EmptyTrashImages : {}", emptyTrashImagesDTO);
        EmptyTrashImages emptyTrashImages = emptyTrashImagesMapper.toEntity(emptyTrashImagesDTO);
        emptyTrashImages = emptyTrashImagesRepository.save(emptyTrashImages);
        return emptyTrashImagesMapper.toDto(emptyTrashImages);
    }

    /**
     * Partially update a emptyTrashImages.
     *
     * @param emptyTrashImagesDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EmptyTrashImagesDTO> partialUpdate(EmptyTrashImagesDTO emptyTrashImagesDTO) {
        log.debug("Request to partially update EmptyTrashImages : {}", emptyTrashImagesDTO);

        return emptyTrashImagesRepository
            .findById(emptyTrashImagesDTO.getId())
            .map(existingEmptyTrashImages -> {
                emptyTrashImagesMapper.partialUpdate(existingEmptyTrashImages, emptyTrashImagesDTO);

                return existingEmptyTrashImages;
            })
            .map(emptyTrashImagesRepository::save)
            .map(emptyTrashImagesMapper::toDto);
    }

    /**
     * Get all the emptyTrashImages.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<EmptyTrashImagesDTO> findAll() {
        log.debug("Request to get all EmptyTrashImages");
        return emptyTrashImagesRepository
            .findAll()
            .stream()
            .map(emptyTrashImagesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one emptyTrashImages by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmptyTrashImagesDTO> findOne(Long id) {
        log.debug("Request to get EmptyTrashImages : {}", id);
        return emptyTrashImagesRepository.findById(id).map(emptyTrashImagesMapper::toDto);
    }

    /**
     * Delete the emptyTrashImages by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmptyTrashImages : {}", id);
        emptyTrashImagesRepository.deleteById(id);
    }
}
