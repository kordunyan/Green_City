package com.trash.green.city.web.rest;

import com.trash.green.city.repository.OsbbRepository;
import com.trash.green.city.service.OsbbService;
import com.trash.green.city.service.dto.OsbbDTO;
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
 * REST controller for managing {@link com.trash.green.city.domain.Osbb}.
 */
@RestController
@RequestMapping("/api")
public class OsbbResource {

    private final Logger log = LoggerFactory.getLogger(OsbbResource.class);

    private static final String ENTITY_NAME = "osbb";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OsbbService osbbService;

    private final OsbbRepository osbbRepository;

    public OsbbResource(OsbbService osbbService, OsbbRepository osbbRepository) {
        this.osbbService = osbbService;
        this.osbbRepository = osbbRepository;
    }

    /**
     * {@code POST  /osbbs} : Create a new osbb.
     *
     * @param osbbDTO the osbbDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new osbbDTO, or with status {@code 400 (Bad Request)} if the osbb has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/osbbs")
    public ResponseEntity<OsbbDTO> createOsbb(@RequestBody OsbbDTO osbbDTO) throws URISyntaxException {
        log.debug("REST request to save Osbb : {}", osbbDTO);
        if (osbbDTO.getId() != null) {
            throw new BadRequestAlertException("A new osbb cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OsbbDTO result = osbbService.save(osbbDTO);
        return ResponseEntity
            .created(new URI("/api/osbbs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /osbbs/:id} : Updates an existing osbb.
     *
     * @param id the id of the osbbDTO to save.
     * @param osbbDTO the osbbDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated osbbDTO,
     * or with status {@code 400 (Bad Request)} if the osbbDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the osbbDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/osbbs/{id}")
    public ResponseEntity<OsbbDTO> updateOsbb(@PathVariable(value = "id", required = false) final Long id, @RequestBody OsbbDTO osbbDTO)
        throws URISyntaxException {
        log.debug("REST request to update Osbb : {}, {}", id, osbbDTO);
        if (osbbDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, osbbDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!osbbRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OsbbDTO result = osbbService.save(osbbDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, osbbDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /osbbs/:id} : Partial updates given fields of an existing osbb, field will ignore if it is null
     *
     * @param id the id of the osbbDTO to save.
     * @param osbbDTO the osbbDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated osbbDTO,
     * or with status {@code 400 (Bad Request)} if the osbbDTO is not valid,
     * or with status {@code 404 (Not Found)} if the osbbDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the osbbDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/osbbs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OsbbDTO> partialUpdateOsbb(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OsbbDTO osbbDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Osbb partially : {}, {}", id, osbbDTO);
        if (osbbDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, osbbDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!osbbRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OsbbDTO> result = osbbService.partialUpdate(osbbDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, osbbDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /osbbs} : get all the osbbs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of osbbs in body.
     */
    @GetMapping("/osbbs")
    public List<OsbbDTO> getAllOsbbs() {
        log.debug("REST request to get all Osbbs");
        return osbbService.findAll();
    }

    /**
     * {@code GET  /osbbs/:id} : get the "id" osbb.
     *
     * @param id the id of the osbbDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the osbbDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/osbbs/{id}")
    public ResponseEntity<OsbbDTO> getOsbb(@PathVariable Long id) {
        log.debug("REST request to get Osbb : {}", id);
        Optional<OsbbDTO> osbbDTO = osbbService.findOne(id);
        return ResponseUtil.wrapOrNotFound(osbbDTO);
    }

    /**
     * {@code DELETE  /osbbs/:id} : delete the "id" osbb.
     *
     * @param id the id of the osbbDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/osbbs/{id}")
    public ResponseEntity<Void> deleteOsbb(@PathVariable Long id) {
        log.debug("REST request to delete Osbb : {}", id);
        osbbService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
