package com.trash.green.city.web.rest;

import com.trash.green.city.repository.FullTrashImagesRepository;
import com.trash.green.city.service.FullTrashImagesService;
import com.trash.green.city.service.dto.FullTrashImagesDTO;
import com.trash.green.city.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.trash.green.city.domain.FullTrashImages}.
 */
@RestController
@RequestMapping("/api")
public class FullTrashImagesResource {

    private final Logger log = LoggerFactory.getLogger(FullTrashImagesResource.class);

    private static final String ENTITY_NAME = "fullTrashImages";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FullTrashImagesService fullTrashImagesService;

    private final FullTrashImagesRepository fullTrashImagesRepository;

    public FullTrashImagesResource(FullTrashImagesService fullTrashImagesService, FullTrashImagesRepository fullTrashImagesRepository) {
        this.fullTrashImagesService = fullTrashImagesService;
        this.fullTrashImagesRepository = fullTrashImagesRepository;
    }

    /**
     * {@code POST  /full-trash-images} : Create a new fullTrashImages.
     *
     * @param fullTrashImagesDTO the fullTrashImagesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fullTrashImagesDTO, or with status {@code 400 (Bad Request)} if the fullTrashImages has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/full-trash-images")
    public ResponseEntity<FullTrashImagesDTO> createFullTrashImages(@RequestBody FullTrashImagesDTO fullTrashImagesDTO)
        throws URISyntaxException {
        log.debug("REST request to save FullTrashImages : {}", fullTrashImagesDTO);
        if (fullTrashImagesDTO.getId() != null) {
            throw new BadRequestAlertException("A new fullTrashImages cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FullTrashImagesDTO result = fullTrashImagesService.save(fullTrashImagesDTO);
        return ResponseEntity
            .created(new URI("/api/full-trash-images/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /full-trash-images/:id} : Updates an existing fullTrashImages.
     *
     * @param id the id of the fullTrashImagesDTO to save.
     * @param fullTrashImagesDTO the fullTrashImagesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fullTrashImagesDTO,
     * or with status {@code 400 (Bad Request)} if the fullTrashImagesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fullTrashImagesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/full-trash-images/{id}")
    public ResponseEntity<FullTrashImagesDTO> updateFullTrashImages(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FullTrashImagesDTO fullTrashImagesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update FullTrashImages : {}, {}", id, fullTrashImagesDTO);
        if (fullTrashImagesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fullTrashImagesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fullTrashImagesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FullTrashImagesDTO result = fullTrashImagesService.save(fullTrashImagesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fullTrashImagesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /full-trash-images/:id} : Partial updates given fields of an existing fullTrashImages, field will ignore if it is null
     *
     * @param id the id of the fullTrashImagesDTO to save.
     * @param fullTrashImagesDTO the fullTrashImagesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fullTrashImagesDTO,
     * or with status {@code 400 (Bad Request)} if the fullTrashImagesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the fullTrashImagesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the fullTrashImagesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/full-trash-images/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FullTrashImagesDTO> partialUpdateFullTrashImages(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FullTrashImagesDTO fullTrashImagesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update FullTrashImages partially : {}, {}", id, fullTrashImagesDTO);
        if (fullTrashImagesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fullTrashImagesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fullTrashImagesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FullTrashImagesDTO> result = fullTrashImagesService.partialUpdate(fullTrashImagesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fullTrashImagesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /full-trash-images} : get all the fullTrashImages.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fullTrashImages in body.
     */
    @GetMapping("/full-trash-images")
    public List<FullTrashImagesDTO> getAllFullTrashImages() {
        log.debug("REST request to get all FullTrashImages");
        return fullTrashImagesService.findAll();
    }

    /**
     * {@code GET  /full-trash-images/:id} : get the "id" fullTrashImages.
     *
     * @param id the id of the fullTrashImagesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fullTrashImagesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/full-trash-images/{id}")
    public ResponseEntity<FullTrashImagesDTO> getFullTrashImages(@PathVariable Long id) {
        log.debug("REST request to get FullTrashImages : {}", id);
        Optional<FullTrashImagesDTO> fullTrashImagesDTO = fullTrashImagesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fullTrashImagesDTO);
    }

    /**
     * {@code DELETE  /full-trash-images/:id} : delete the "id" fullTrashImages.
     *
     * @param id the id of the fullTrashImagesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/full-trash-images/{id}")
    public ResponseEntity<Void> deleteFullTrashImages(@PathVariable Long id) {
        log.debug("REST request to delete FullTrashImages : {}", id);
        fullTrashImagesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
