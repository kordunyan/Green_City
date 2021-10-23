package com.trash.green.city.web.rest;

import com.trash.green.city.repository.TrashCompanyRepository;
import com.trash.green.city.service.TrashCompanyService;
import com.trash.green.city.service.dto.TrashCompanyDTO;
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
 * REST controller for managing {@link com.trash.green.city.domain.TrashCompany}.
 */
@RestController
@RequestMapping("/api")
public class TrashCompanyResource {

    private final Logger log = LoggerFactory.getLogger(TrashCompanyResource.class);

    private static final String ENTITY_NAME = "trashCompany";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TrashCompanyService trashCompanyService;

    private final TrashCompanyRepository trashCompanyRepository;

    public TrashCompanyResource(TrashCompanyService trashCompanyService, TrashCompanyRepository trashCompanyRepository) {
        this.trashCompanyService = trashCompanyService;
        this.trashCompanyRepository = trashCompanyRepository;
    }

    /**
     * {@code POST  /trash-companies} : Create a new trashCompany.
     *
     * @param trashCompanyDTO the trashCompanyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new trashCompanyDTO, or with status {@code 400 (Bad Request)} if the trashCompany has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/trash-companies")
    public ResponseEntity<TrashCompanyDTO> createTrashCompany(@RequestBody TrashCompanyDTO trashCompanyDTO) throws URISyntaxException {
        log.debug("REST request to save TrashCompany : {}", trashCompanyDTO);
        if (trashCompanyDTO.getId() != null) {
            throw new BadRequestAlertException("A new trashCompany cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TrashCompanyDTO result = trashCompanyService.save(trashCompanyDTO);
        return ResponseEntity
            .created(new URI("/api/trash-companies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /trash-companies/:id} : Updates an existing trashCompany.
     *
     * @param id the id of the trashCompanyDTO to save.
     * @param trashCompanyDTO the trashCompanyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trashCompanyDTO,
     * or with status {@code 400 (Bad Request)} if the trashCompanyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the trashCompanyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/trash-companies/{id}")
    public ResponseEntity<TrashCompanyDTO> updateTrashCompany(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TrashCompanyDTO trashCompanyDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TrashCompany : {}, {}", id, trashCompanyDTO);
        if (trashCompanyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, trashCompanyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!trashCompanyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TrashCompanyDTO result = trashCompanyService.save(trashCompanyDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, trashCompanyDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /trash-companies/:id} : Partial updates given fields of an existing trashCompany, field will ignore if it is null
     *
     * @param id the id of the trashCompanyDTO to save.
     * @param trashCompanyDTO the trashCompanyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trashCompanyDTO,
     * or with status {@code 400 (Bad Request)} if the trashCompanyDTO is not valid,
     * or with status {@code 404 (Not Found)} if the trashCompanyDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the trashCompanyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/trash-companies/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TrashCompanyDTO> partialUpdateTrashCompany(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TrashCompanyDTO trashCompanyDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TrashCompany partially : {}, {}", id, trashCompanyDTO);
        if (trashCompanyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, trashCompanyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!trashCompanyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TrashCompanyDTO> result = trashCompanyService.partialUpdate(trashCompanyDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, trashCompanyDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /trash-companies} : get all the trashCompanies.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of trashCompanies in body.
     */
    @GetMapping("/trash-companies")
    public List<TrashCompanyDTO> getAllTrashCompanies() {
        log.debug("REST request to get all TrashCompanies");
        return trashCompanyService.findAll();
    }

    /**
     * {@code GET  /trash-companies/:id} : get the "id" trashCompany.
     *
     * @param id the id of the trashCompanyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the trashCompanyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/trash-companies/{id}")
    public ResponseEntity<TrashCompanyDTO> getTrashCompany(@PathVariable Long id) {
        log.debug("REST request to get TrashCompany : {}", id);
        Optional<TrashCompanyDTO> trashCompanyDTO = trashCompanyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(trashCompanyDTO);
    }

    /**
     * {@code DELETE  /trash-companies/:id} : delete the "id" trashCompany.
     *
     * @param id the id of the trashCompanyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/trash-companies/{id}")
    public ResponseEntity<Void> deleteTrashCompany(@PathVariable Long id) {
        log.debug("REST request to delete TrashCompany : {}", id);
        trashCompanyService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
