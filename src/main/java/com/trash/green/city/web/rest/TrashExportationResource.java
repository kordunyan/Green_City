package com.trash.green.city.web.rest;

import com.trash.green.city.repository.TrashExportationRepository;
import com.trash.green.city.service.TrashExportationService;
import com.trash.green.city.service.dto.TrashExportationDTO;
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
 * REST controller for managing {@link com.trash.green.city.domain.TrashExportation}.
 */
@RestController
@RequestMapping("/api")
public class TrashExportationResource {

    private final Logger log = LoggerFactory.getLogger(TrashExportationResource.class);

    private static final String ENTITY_NAME = "trashExportation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TrashExportationService trashExportationService;

    private final TrashExportationRepository trashExportationRepository;

    public TrashExportationResource(
        TrashExportationService trashExportationService,
        TrashExportationRepository trashExportationRepository
    ) {
        this.trashExportationService = trashExportationService;
        this.trashExportationRepository = trashExportationRepository;
    }

    /**
     * {@code POST  /trash-exportations} : Create a new trashExportation.
     *
     * @param trashExportationDTO the trashExportationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new trashExportationDTO, or with status {@code 400 (Bad Request)} if the trashExportation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/trash-exportations")
    public ResponseEntity<TrashExportationDTO> createTrashExportation(@RequestBody TrashExportationDTO trashExportationDTO)
        throws URISyntaxException {
        log.debug("REST request to save TrashExportation : {}", trashExportationDTO);
        if (trashExportationDTO.getId() != null) {
            throw new BadRequestAlertException("A new trashExportation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TrashExportationDTO result = trashExportationService.save(trashExportationDTO);
        return ResponseEntity
            .created(new URI("/api/trash-exportations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /trash-exportations/:id} : Updates an existing trashExportation.
     *
     * @param id the id of the trashExportationDTO to save.
     * @param trashExportationDTO the trashExportationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trashExportationDTO,
     * or with status {@code 400 (Bad Request)} if the trashExportationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the trashExportationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/trash-exportations/{id}")
    public ResponseEntity<TrashExportationDTO> updateTrashExportation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TrashExportationDTO trashExportationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TrashExportation : {}, {}", id, trashExportationDTO);
        if (trashExportationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, trashExportationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!trashExportationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TrashExportationDTO result = trashExportationService.save(trashExportationDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, trashExportationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /trash-exportations/:id} : Partial updates given fields of an existing trashExportation, field will ignore if it is null
     *
     * @param id the id of the trashExportationDTO to save.
     * @param trashExportationDTO the trashExportationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trashExportationDTO,
     * or with status {@code 400 (Bad Request)} if the trashExportationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the trashExportationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the trashExportationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/trash-exportations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TrashExportationDTO> partialUpdateTrashExportation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TrashExportationDTO trashExportationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TrashExportation partially : {}, {}", id, trashExportationDTO);
        if (trashExportationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, trashExportationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!trashExportationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TrashExportationDTO> result = trashExportationService.partialUpdate(trashExportationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, trashExportationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /trash-exportations} : get all the trashExportations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of trashExportations in body.
     */
    @GetMapping("/trash-exportations")
    public List<TrashExportationDTO> getAllTrashExportations() {
        log.debug("REST request to get all TrashExportations");
        return trashExportationService.findAll();
    }

    /**
     * {@code GET  /trash-exportations/:id} : get the "id" trashExportation.
     *
     * @param id the id of the trashExportationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the trashExportationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/trash-exportations/{id}")
    public ResponseEntity<TrashExportationDTO> getTrashExportation(@PathVariable Long id) {
        log.debug("REST request to get TrashExportation : {}", id);
        Optional<TrashExportationDTO> trashExportationDTO = trashExportationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(trashExportationDTO);
    }

    /**
     * {@code DELETE  /trash-exportations/:id} : delete the "id" trashExportation.
     *
     * @param id the id of the trashExportationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/trash-exportations/{id}")
    public ResponseEntity<Void> deleteTrashExportation(@PathVariable Long id) {
        log.debug("REST request to delete TrashExportation : {}", id);
        trashExportationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
