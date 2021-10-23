package com.trash.green.city.web.rest;

import com.trash.green.city.repository.EmptyTrashImagesRepository;
import com.trash.green.city.service.EmptyTrashImagesService;
import com.trash.green.city.service.dto.EmptyTrashImagesDTO;
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
 * REST controller for managing {@link com.trash.green.city.domain.EmptyTrashImages}.
 */
@RestController
@RequestMapping("/api")
public class EmptyTrashImagesResource {

    private final Logger log = LoggerFactory.getLogger(EmptyTrashImagesResource.class);

    private static final String ENTITY_NAME = "emptyTrashImages";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmptyTrashImagesService emptyTrashImagesService;

    private final EmptyTrashImagesRepository emptyTrashImagesRepository;

    public EmptyTrashImagesResource(
        EmptyTrashImagesService emptyTrashImagesService,
        EmptyTrashImagesRepository emptyTrashImagesRepository
    ) {
        this.emptyTrashImagesService = emptyTrashImagesService;
        this.emptyTrashImagesRepository = emptyTrashImagesRepository;
    }

    /**
     * {@code POST  /empty-trash-images} : Create a new emptyTrashImages.
     *
     * @param emptyTrashImagesDTO the emptyTrashImagesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new emptyTrashImagesDTO, or with status {@code 400 (Bad Request)} if the emptyTrashImages has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/empty-trash-images")
    public ResponseEntity<EmptyTrashImagesDTO> createEmptyTrashImages(@RequestBody EmptyTrashImagesDTO emptyTrashImagesDTO)
        throws URISyntaxException {
        log.debug("REST request to save EmptyTrashImages : {}", emptyTrashImagesDTO);
        if (emptyTrashImagesDTO.getId() != null) {
            throw new BadRequestAlertException("A new emptyTrashImages cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmptyTrashImagesDTO result = emptyTrashImagesService.save(emptyTrashImagesDTO);
        return ResponseEntity
            .created(new URI("/api/empty-trash-images/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /empty-trash-images/:id} : Updates an existing emptyTrashImages.
     *
     * @param id the id of the emptyTrashImagesDTO to save.
     * @param emptyTrashImagesDTO the emptyTrashImagesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated emptyTrashImagesDTO,
     * or with status {@code 400 (Bad Request)} if the emptyTrashImagesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the emptyTrashImagesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/empty-trash-images/{id}")
    public ResponseEntity<EmptyTrashImagesDTO> updateEmptyTrashImages(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EmptyTrashImagesDTO emptyTrashImagesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update EmptyTrashImages : {}, {}", id, emptyTrashImagesDTO);
        if (emptyTrashImagesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, emptyTrashImagesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!emptyTrashImagesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EmptyTrashImagesDTO result = emptyTrashImagesService.save(emptyTrashImagesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, emptyTrashImagesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /empty-trash-images/:id} : Partial updates given fields of an existing emptyTrashImages, field will ignore if it is null
     *
     * @param id the id of the emptyTrashImagesDTO to save.
     * @param emptyTrashImagesDTO the emptyTrashImagesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated emptyTrashImagesDTO,
     * or with status {@code 400 (Bad Request)} if the emptyTrashImagesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the emptyTrashImagesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the emptyTrashImagesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/empty-trash-images/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EmptyTrashImagesDTO> partialUpdateEmptyTrashImages(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EmptyTrashImagesDTO emptyTrashImagesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update EmptyTrashImages partially : {}, {}", id, emptyTrashImagesDTO);
        if (emptyTrashImagesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, emptyTrashImagesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!emptyTrashImagesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EmptyTrashImagesDTO> result = emptyTrashImagesService.partialUpdate(emptyTrashImagesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, emptyTrashImagesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /empty-trash-images} : get all the emptyTrashImages.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of emptyTrashImages in body.
     */
    @GetMapping("/empty-trash-images")
    public List<EmptyTrashImagesDTO> getAllEmptyTrashImages() {
        log.debug("REST request to get all EmptyTrashImages");
        return emptyTrashImagesService.findAll();
    }

    /**
     * {@code GET  /empty-trash-images/:id} : get the "id" emptyTrashImages.
     *
     * @param id the id of the emptyTrashImagesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the emptyTrashImagesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/empty-trash-images/{id}")
    public ResponseEntity<EmptyTrashImagesDTO> getEmptyTrashImages(@PathVariable Long id) {
        log.debug("REST request to get EmptyTrashImages : {}", id);
        Optional<EmptyTrashImagesDTO> emptyTrashImagesDTO = emptyTrashImagesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(emptyTrashImagesDTO);
    }

    /**
     * {@code DELETE  /empty-trash-images/:id} : delete the "id" emptyTrashImages.
     *
     * @param id the id of the emptyTrashImagesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/empty-trash-images/{id}")
    public ResponseEntity<Void> deleteEmptyTrashImages(@PathVariable Long id) {
        log.debug("REST request to delete EmptyTrashImages : {}", id);
        emptyTrashImagesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
