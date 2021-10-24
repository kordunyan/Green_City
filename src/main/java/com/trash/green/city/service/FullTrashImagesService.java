package com.trash.green.city.service;

import com.trash.green.city.domain.FullTrashImages;
import com.trash.green.city.repository.FullTrashImagesRepository;
import com.trash.green.city.service.dto.FullTrashImagesDTO;
import com.trash.green.city.service.mapper.FullTrashImagesMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FullTrashImages}.
 */
@Service
@Transactional
public class FullTrashImagesService {

    private final Logger log = LoggerFactory.getLogger(FullTrashImagesService.class);

    private final FullTrashImagesRepository fullTrashImagesRepository;

    private final FullTrashImagesMapper fullTrashImagesMapper;

    public FullTrashImagesService(FullTrashImagesRepository fullTrashImagesRepository, FullTrashImagesMapper fullTrashImagesMapper) {
        this.fullTrashImagesRepository = fullTrashImagesRepository;
        this.fullTrashImagesMapper = fullTrashImagesMapper;
    }

    /**
     * Save a fullTrashImages.
     *
     * @param fullTrashImagesDTO the entity to save.
     * @return the persisted entity.
     */
    public FullTrashImagesDTO save(FullTrashImagesDTO fullTrashImagesDTO) {
        log.debug("Request to save Report : {}", fullTrashImagesDTO);
        FullTrashImages fullTrashImages = fullTrashImagesMapper.toEntity(fullTrashImagesDTO);
        fullTrashImages = fullTrashImagesRepository.save(fullTrashImages);
        return fullTrashImagesMapper.toDto(fullTrashImages);
    }

    /**
     * Partially update a fullTrashImages.
     *
     * @param fullTrashImagesDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FullTrashImagesDTO> partialUpdate(FullTrashImagesDTO fullTrashImagesDTO) {
        log.debug("Request to partially update Report : {}", fullTrashImagesDTO);

        return fullTrashImagesRepository
            .findById(fullTrashImagesDTO.getId())
            .map(existingFullTrashImages -> {
                fullTrashImagesMapper.partialUpdate(existingFullTrashImages, fullTrashImagesDTO);

                return existingFullTrashImages;
            })
            .map(fullTrashImagesRepository::save)
            .map(fullTrashImagesMapper::toDto);
    }

    /**
     * Get all the fullTrashImages.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<FullTrashImagesDTO> findAll() {
        log.debug("Request to get all Report");
        return fullTrashImagesRepository
            .findAll()
            .stream()
            .map(fullTrashImagesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one fullTrashImages by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FullTrashImagesDTO> findOne(Long id) {
        log.debug("Request to get Report : {}", id);
        return fullTrashImagesRepository.findById(id).map(fullTrashImagesMapper::toDto);
    }

    /**
     * Delete the fullTrashImages by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Report : {}", id);
        fullTrashImagesRepository.deleteById(id);
    }
}
