package com.trash.green.city.service;

import com.trash.green.city.constants.ConvertationRate;
import com.trash.green.city.domain.EmptyTrashImages;
import com.trash.green.city.domain.FullTrashImages;
import com.trash.green.city.domain.TrashExportation;
import com.trash.green.city.repository.EmptyTrashImagesRepository;
import com.trash.green.city.repository.FullTrashImagesRepository;
import com.trash.green.city.repository.TrashExportationRepository;
import com.trash.green.city.service.dto.TrashExportationDTO;
import com.trash.green.city.service.dto.TrashExportationWithImagesDTO;
import com.trash.green.city.service.exportation.ExportTrashDto;
import com.trash.green.city.service.mapper.TrashExportationMapper;
import java.util.*;
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
    private final EmptyTrashImagesRepository emptyTrashImagesRepository;
    private final FullTrashImagesRepository fullTrashImagesRepository;

    private final TrashExportationMapper trashExportationMapper;

    private final ImageService imageService;

    public TrashExportationService(
        EmptyTrashImagesRepository emptyTrashImagesRepository,
        FullTrashImagesRepository fullTrashImagesRepository,
        TrashExportationRepository trashExportationRepository,
        TrashExportationMapper trashExportationMapper,
        ImageService imageService
    ) {
        this.trashExportationRepository = trashExportationRepository;
        this.trashExportationMapper = trashExportationMapper;
        this.imageService = imageService;
        this.emptyTrashImagesRepository = emptyTrashImagesRepository;
        this.fullTrashImagesRepository = fullTrashImagesRepository;
    }

    public void exportTrash(TrashExportationDTO exportationDTO, ExportTrashDto dto) {
        List<String> emptyTrashIMageNames = imageService.saveImages(dto.getEmptyTrashImages());
        List<String> fullTrashIMageNames = imageService.saveImages(dto.getFullTrashImages());

        Set<FullTrashImages> fullTrashImages = fullTrashIMageNames.stream().map(this::createFullTrashImages).collect(Collectors.toSet());

        Set<EmptyTrashImages> emptyTrashImages = emptyTrashIMageNames
            .stream()
            .map(this::createEmptyTrashImages)
            .collect(Collectors.toSet());

        TrashExportation trashExportation = trashExportationMapper.toEntity(exportationDTO);
        trashExportationRepository.save(trashExportation);
        trashExportation.setEmptyTrashImages(emptyTrashImages);
        trashExportation.setFullTrashImages(fullTrashImages);
        emptyTrashImagesRepository.saveAll(emptyTrashImages);
        fullTrashImagesRepository.saveAll(fullTrashImages);
    }

    private EmptyTrashImages createEmptyTrashImages(String imageName) {
        EmptyTrashImages trashImages = new EmptyTrashImages();
        trashImages.setPath(imageName);
        return trashImages;
    }

    private FullTrashImages createFullTrashImages(String imageName) {
        FullTrashImages trashImages = new FullTrashImages();
        trashImages.setPath(imageName);
        return trashImages;
    }

    public Integer calculateWeight(Integer containerCount, String trashType) {
        ConvertationRate convertationRate = ConvertationRate.getByType(trashType);
        return convertationRate.getRate() * containerCount;
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

    public List<TrashExportationWithImagesDTO> getByOsbbId(Long id) {
        List<TrashExportation> allByOsbbId = trashExportationRepository.findAllByOsbbId(id);
        List<TrashExportationWithImagesDTO> result = new ArrayList<>();
        for (TrashExportation trashExportation : allByOsbbId) {
            TrashExportationDTO exportationDTO = trashExportationMapper.toDto(trashExportation);
            TrashExportationWithImagesDTO dto = new TrashExportationWithImagesDTO();
            dto.setOsbb(exportationDTO.getOsbb());
            dto.setTrash_type(exportationDTO.getTrash_type());
            dto.setDate(exportationDTO.getDate());
            dto.setIs_wash(exportationDTO.getIs_wash());
            dto.setWeight(exportationDTO.getWeight());
            dto.setId(exportationDTO.getId());

            List<String> emptyTrash = trashExportation
                .getEmptyTrashImages()
                .stream()
                .map(EmptyTrashImages::getPath)
                .collect(Collectors.toList());

            List<String> fullTrash = trashExportation
                .getFullTrashImages()
                .stream()
                .map(FullTrashImages::getPath)
                .collect(Collectors.toList());

            dto.setEmptyTrashImages(emptyTrash);
            dto.setFullTrashImages(fullTrash);
            result.add(dto);
        }
        return result;
    }
}
