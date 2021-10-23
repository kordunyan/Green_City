package com.trash.green.city.service;

import com.trash.green.city.domain.TrashCompany;
import com.trash.green.city.repository.TrashCompanyRepository;
import com.trash.green.city.service.dto.TrashCompanyDTO;
import com.trash.green.city.service.mapper.TrashCompanyMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TrashCompany}.
 */
@Service
@Transactional
public class TrashCompanyService {

    private final Logger log = LoggerFactory.getLogger(TrashCompanyService.class);

    private final TrashCompanyRepository trashCompanyRepository;

    private final TrashCompanyMapper trashCompanyMapper;

    public TrashCompanyService(TrashCompanyRepository trashCompanyRepository, TrashCompanyMapper trashCompanyMapper) {
        this.trashCompanyRepository = trashCompanyRepository;
        this.trashCompanyMapper = trashCompanyMapper;
    }

    /**
     * Save a trashCompany.
     *
     * @param trashCompanyDTO the entity to save.
     * @return the persisted entity.
     */
    public TrashCompanyDTO save(TrashCompanyDTO trashCompanyDTO) {
        log.debug("Request to save TrashCompany : {}", trashCompanyDTO);
        TrashCompany trashCompany = trashCompanyMapper.toEntity(trashCompanyDTO);
        trashCompany = trashCompanyRepository.save(trashCompany);
        return trashCompanyMapper.toDto(trashCompany);
    }

    /**
     * Partially update a trashCompany.
     *
     * @param trashCompanyDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TrashCompanyDTO> partialUpdate(TrashCompanyDTO trashCompanyDTO) {
        log.debug("Request to partially update TrashCompany : {}", trashCompanyDTO);

        return trashCompanyRepository
            .findById(trashCompanyDTO.getId())
            .map(existingTrashCompany -> {
                trashCompanyMapper.partialUpdate(existingTrashCompany, trashCompanyDTO);

                return existingTrashCompany;
            })
            .map(trashCompanyRepository::save)
            .map(trashCompanyMapper::toDto);
    }

    /**
     * Get all the trashCompanies.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TrashCompanyDTO> findAll() {
        log.debug("Request to get all TrashCompanies");
        return trashCompanyRepository.findAll().stream().map(trashCompanyMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one trashCompany by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TrashCompanyDTO> findOne(Long id) {
        log.debug("Request to get TrashCompany : {}", id);
        return trashCompanyRepository.findById(id).map(trashCompanyMapper::toDto);
    }

    /**
     * Delete the trashCompany by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TrashCompany : {}", id);
        trashCompanyRepository.deleteById(id);
    }
}
