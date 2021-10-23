package com.trash.green.city.web.rest;

import static com.trash.green.city.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.trash.green.city.IntegrationTest;
import com.trash.green.city.domain.TrashExportation;
import com.trash.green.city.repository.TrashExportationRepository;
import com.trash.green.city.service.dto.TrashExportationDTO;
import com.trash.green.city.service.mapper.TrashExportationMapper;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TrashExportationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TrashExportationResourceIT {

    private static final Integer DEFAULT_WEIGHT = 1;
    private static final Integer UPDATED_WEIGHT = 2;

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_TRASH_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TRASH_TYPE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_WASH = false;
    private static final Boolean UPDATED_IS_WASH = true;

    private static final String ENTITY_API_URL = "/api/trash-exportations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TrashExportationRepository trashExportationRepository;

    @Autowired
    private TrashExportationMapper trashExportationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTrashExportationMockMvc;

    private TrashExportation trashExportation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TrashExportation createEntity(EntityManager em) {
        TrashExportation trashExportation = new TrashExportation()
            .weight(DEFAULT_WEIGHT)
            .date(DEFAULT_DATE)
            .trash_type(DEFAULT_TRASH_TYPE)
            .is_wash(DEFAULT_IS_WASH);
        return trashExportation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TrashExportation createUpdatedEntity(EntityManager em) {
        TrashExportation trashExportation = new TrashExportation()
            .weight(UPDATED_WEIGHT)
            .date(UPDATED_DATE)
            .trash_type(UPDATED_TRASH_TYPE)
            .is_wash(UPDATED_IS_WASH);
        return trashExportation;
    }

    @BeforeEach
    public void initTest() {
        trashExportation = createEntity(em);
    }

    @Test
    @Transactional
    void createTrashExportation() throws Exception {
        int databaseSizeBeforeCreate = trashExportationRepository.findAll().size();
        // Create the TrashExportation
        TrashExportationDTO trashExportationDTO = trashExportationMapper.toDto(trashExportation);
        restTrashExportationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trashExportationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TrashExportation in the database
        List<TrashExportation> trashExportationList = trashExportationRepository.findAll();
        assertThat(trashExportationList).hasSize(databaseSizeBeforeCreate + 1);
        TrashExportation testTrashExportation = trashExportationList.get(trashExportationList.size() - 1);
        assertThat(testTrashExportation.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testTrashExportation.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testTrashExportation.getTrash_type()).isEqualTo(DEFAULT_TRASH_TYPE);
        assertThat(testTrashExportation.getIs_wash()).isEqualTo(DEFAULT_IS_WASH);
    }

    @Test
    @Transactional
    void createTrashExportationWithExistingId() throws Exception {
        // Create the TrashExportation with an existing ID
        trashExportation.setId(1L);
        TrashExportationDTO trashExportationDTO = trashExportationMapper.toDto(trashExportation);

        int databaseSizeBeforeCreate = trashExportationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrashExportationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trashExportationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrashExportation in the database
        List<TrashExportation> trashExportationList = trashExportationRepository.findAll();
        assertThat(trashExportationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTrashExportations() throws Exception {
        // Initialize the database
        trashExportationRepository.saveAndFlush(trashExportation);

        // Get all the trashExportationList
        restTrashExportationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trashExportation.getId().intValue())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].trash_type").value(hasItem(DEFAULT_TRASH_TYPE)))
            .andExpect(jsonPath("$.[*].is_wash").value(hasItem(DEFAULT_IS_WASH.booleanValue())));
    }

    @Test
    @Transactional
    void getTrashExportation() throws Exception {
        // Initialize the database
        trashExportationRepository.saveAndFlush(trashExportation);

        // Get the trashExportation
        restTrashExportationMockMvc
            .perform(get(ENTITY_API_URL_ID, trashExportation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(trashExportation.getId().intValue()))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)))
            .andExpect(jsonPath("$.trash_type").value(DEFAULT_TRASH_TYPE))
            .andExpect(jsonPath("$.is_wash").value(DEFAULT_IS_WASH.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingTrashExportation() throws Exception {
        // Get the trashExportation
        restTrashExportationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTrashExportation() throws Exception {
        // Initialize the database
        trashExportationRepository.saveAndFlush(trashExportation);

        int databaseSizeBeforeUpdate = trashExportationRepository.findAll().size();

        // Update the trashExportation
        TrashExportation updatedTrashExportation = trashExportationRepository.findById(trashExportation.getId()).get();
        // Disconnect from session so that the updates on updatedTrashExportation are not directly saved in db
        em.detach(updatedTrashExportation);
        updatedTrashExportation.weight(UPDATED_WEIGHT).date(UPDATED_DATE).trash_type(UPDATED_TRASH_TYPE).is_wash(UPDATED_IS_WASH);
        TrashExportationDTO trashExportationDTO = trashExportationMapper.toDto(updatedTrashExportation);

        restTrashExportationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, trashExportationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trashExportationDTO))
            )
            .andExpect(status().isOk());

        // Validate the TrashExportation in the database
        List<TrashExportation> trashExportationList = trashExportationRepository.findAll();
        assertThat(trashExportationList).hasSize(databaseSizeBeforeUpdate);
        TrashExportation testTrashExportation = trashExportationList.get(trashExportationList.size() - 1);
        assertThat(testTrashExportation.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testTrashExportation.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testTrashExportation.getTrash_type()).isEqualTo(UPDATED_TRASH_TYPE);
        assertThat(testTrashExportation.getIs_wash()).isEqualTo(UPDATED_IS_WASH);
    }

    @Test
    @Transactional
    void putNonExistingTrashExportation() throws Exception {
        int databaseSizeBeforeUpdate = trashExportationRepository.findAll().size();
        trashExportation.setId(count.incrementAndGet());

        // Create the TrashExportation
        TrashExportationDTO trashExportationDTO = trashExportationMapper.toDto(trashExportation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrashExportationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, trashExportationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trashExportationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrashExportation in the database
        List<TrashExportation> trashExportationList = trashExportationRepository.findAll();
        assertThat(trashExportationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTrashExportation() throws Exception {
        int databaseSizeBeforeUpdate = trashExportationRepository.findAll().size();
        trashExportation.setId(count.incrementAndGet());

        // Create the TrashExportation
        TrashExportationDTO trashExportationDTO = trashExportationMapper.toDto(trashExportation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrashExportationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trashExportationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrashExportation in the database
        List<TrashExportation> trashExportationList = trashExportationRepository.findAll();
        assertThat(trashExportationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTrashExportation() throws Exception {
        int databaseSizeBeforeUpdate = trashExportationRepository.findAll().size();
        trashExportation.setId(count.incrementAndGet());

        // Create the TrashExportation
        TrashExportationDTO trashExportationDTO = trashExportationMapper.toDto(trashExportation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrashExportationMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trashExportationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TrashExportation in the database
        List<TrashExportation> trashExportationList = trashExportationRepository.findAll();
        assertThat(trashExportationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTrashExportationWithPatch() throws Exception {
        // Initialize the database
        trashExportationRepository.saveAndFlush(trashExportation);

        int databaseSizeBeforeUpdate = trashExportationRepository.findAll().size();

        // Update the trashExportation using partial update
        TrashExportation partialUpdatedTrashExportation = new TrashExportation();
        partialUpdatedTrashExportation.setId(trashExportation.getId());

        partialUpdatedTrashExportation.weight(UPDATED_WEIGHT);

        restTrashExportationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTrashExportation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTrashExportation))
            )
            .andExpect(status().isOk());

        // Validate the TrashExportation in the database
        List<TrashExportation> trashExportationList = trashExportationRepository.findAll();
        assertThat(trashExportationList).hasSize(databaseSizeBeforeUpdate);
        TrashExportation testTrashExportation = trashExportationList.get(trashExportationList.size() - 1);
        assertThat(testTrashExportation.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testTrashExportation.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testTrashExportation.getTrash_type()).isEqualTo(DEFAULT_TRASH_TYPE);
        assertThat(testTrashExportation.getIs_wash()).isEqualTo(DEFAULT_IS_WASH);
    }

    @Test
    @Transactional
    void fullUpdateTrashExportationWithPatch() throws Exception {
        // Initialize the database
        trashExportationRepository.saveAndFlush(trashExportation);

        int databaseSizeBeforeUpdate = trashExportationRepository.findAll().size();

        // Update the trashExportation using partial update
        TrashExportation partialUpdatedTrashExportation = new TrashExportation();
        partialUpdatedTrashExportation.setId(trashExportation.getId());

        partialUpdatedTrashExportation.weight(UPDATED_WEIGHT).date(UPDATED_DATE).trash_type(UPDATED_TRASH_TYPE).is_wash(UPDATED_IS_WASH);

        restTrashExportationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTrashExportation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTrashExportation))
            )
            .andExpect(status().isOk());

        // Validate the TrashExportation in the database
        List<TrashExportation> trashExportationList = trashExportationRepository.findAll();
        assertThat(trashExportationList).hasSize(databaseSizeBeforeUpdate);
        TrashExportation testTrashExportation = trashExportationList.get(trashExportationList.size() - 1);
        assertThat(testTrashExportation.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testTrashExportation.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testTrashExportation.getTrash_type()).isEqualTo(UPDATED_TRASH_TYPE);
        assertThat(testTrashExportation.getIs_wash()).isEqualTo(UPDATED_IS_WASH);
    }

    @Test
    @Transactional
    void patchNonExistingTrashExportation() throws Exception {
        int databaseSizeBeforeUpdate = trashExportationRepository.findAll().size();
        trashExportation.setId(count.incrementAndGet());

        // Create the TrashExportation
        TrashExportationDTO trashExportationDTO = trashExportationMapper.toDto(trashExportation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrashExportationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, trashExportationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(trashExportationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrashExportation in the database
        List<TrashExportation> trashExportationList = trashExportationRepository.findAll();
        assertThat(trashExportationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTrashExportation() throws Exception {
        int databaseSizeBeforeUpdate = trashExportationRepository.findAll().size();
        trashExportation.setId(count.incrementAndGet());

        // Create the TrashExportation
        TrashExportationDTO trashExportationDTO = trashExportationMapper.toDto(trashExportation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrashExportationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(trashExportationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrashExportation in the database
        List<TrashExportation> trashExportationList = trashExportationRepository.findAll();
        assertThat(trashExportationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTrashExportation() throws Exception {
        int databaseSizeBeforeUpdate = trashExportationRepository.findAll().size();
        trashExportation.setId(count.incrementAndGet());

        // Create the TrashExportation
        TrashExportationDTO trashExportationDTO = trashExportationMapper.toDto(trashExportation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrashExportationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(trashExportationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TrashExportation in the database
        List<TrashExportation> trashExportationList = trashExportationRepository.findAll();
        assertThat(trashExportationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTrashExportation() throws Exception {
        // Initialize the database
        trashExportationRepository.saveAndFlush(trashExportation);

        int databaseSizeBeforeDelete = trashExportationRepository.findAll().size();

        // Delete the trashExportation
        restTrashExportationMockMvc
            .perform(delete(ENTITY_API_URL_ID, trashExportation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TrashExportation> trashExportationList = trashExportationRepository.findAll();
        assertThat(trashExportationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
