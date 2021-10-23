package com.trash.green.city.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.trash.green.city.IntegrationTest;
import com.trash.green.city.domain.TrashCompany;
import com.trash.green.city.repository.TrashCompanyRepository;
import com.trash.green.city.service.dto.TrashCompanyDTO;
import com.trash.green.city.service.mapper.TrashCompanyMapper;
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
 * Integration tests for the {@link TrashCompanyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TrashCompanyResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/trash-companies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TrashCompanyRepository trashCompanyRepository;

    @Autowired
    private TrashCompanyMapper trashCompanyMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTrashCompanyMockMvc;

    private TrashCompany trashCompany;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TrashCompany createEntity(EntityManager em) {
        TrashCompany trashCompany = new TrashCompany().name(DEFAULT_NAME).phone(DEFAULT_PHONE);
        return trashCompany;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TrashCompany createUpdatedEntity(EntityManager em) {
        TrashCompany trashCompany = new TrashCompany().name(UPDATED_NAME).phone(UPDATED_PHONE);
        return trashCompany;
    }

    @BeforeEach
    public void initTest() {
        trashCompany = createEntity(em);
    }

    @Test
    @Transactional
    void createTrashCompany() throws Exception {
        int databaseSizeBeforeCreate = trashCompanyRepository.findAll().size();
        // Create the TrashCompany
        TrashCompanyDTO trashCompanyDTO = trashCompanyMapper.toDto(trashCompany);
        restTrashCompanyMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trashCompanyDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TrashCompany in the database
        List<TrashCompany> trashCompanyList = trashCompanyRepository.findAll();
        assertThat(trashCompanyList).hasSize(databaseSizeBeforeCreate + 1);
        TrashCompany testTrashCompany = trashCompanyList.get(trashCompanyList.size() - 1);
        assertThat(testTrashCompany.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTrashCompany.getPhone()).isEqualTo(DEFAULT_PHONE);
    }

    @Test
    @Transactional
    void createTrashCompanyWithExistingId() throws Exception {
        // Create the TrashCompany with an existing ID
        trashCompany.setId(1L);
        TrashCompanyDTO trashCompanyDTO = trashCompanyMapper.toDto(trashCompany);

        int databaseSizeBeforeCreate = trashCompanyRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrashCompanyMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trashCompanyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrashCompany in the database
        List<TrashCompany> trashCompanyList = trashCompanyRepository.findAll();
        assertThat(trashCompanyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTrashCompanies() throws Exception {
        // Initialize the database
        trashCompanyRepository.saveAndFlush(trashCompany);

        // Get all the trashCompanyList
        restTrashCompanyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trashCompany.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)));
    }

    @Test
    @Transactional
    void getTrashCompany() throws Exception {
        // Initialize the database
        trashCompanyRepository.saveAndFlush(trashCompany);

        // Get the trashCompany
        restTrashCompanyMockMvc
            .perform(get(ENTITY_API_URL_ID, trashCompany.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(trashCompany.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE));
    }

    @Test
    @Transactional
    void getNonExistingTrashCompany() throws Exception {
        // Get the trashCompany
        restTrashCompanyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTrashCompany() throws Exception {
        // Initialize the database
        trashCompanyRepository.saveAndFlush(trashCompany);

        int databaseSizeBeforeUpdate = trashCompanyRepository.findAll().size();

        // Update the trashCompany
        TrashCompany updatedTrashCompany = trashCompanyRepository.findById(trashCompany.getId()).get();
        // Disconnect from session so that the updates on updatedTrashCompany are not directly saved in db
        em.detach(updatedTrashCompany);
        updatedTrashCompany.name(UPDATED_NAME).phone(UPDATED_PHONE);
        TrashCompanyDTO trashCompanyDTO = trashCompanyMapper.toDto(updatedTrashCompany);

        restTrashCompanyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, trashCompanyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trashCompanyDTO))
            )
            .andExpect(status().isOk());

        // Validate the TrashCompany in the database
        List<TrashCompany> trashCompanyList = trashCompanyRepository.findAll();
        assertThat(trashCompanyList).hasSize(databaseSizeBeforeUpdate);
        TrashCompany testTrashCompany = trashCompanyList.get(trashCompanyList.size() - 1);
        assertThat(testTrashCompany.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTrashCompany.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    @Transactional
    void putNonExistingTrashCompany() throws Exception {
        int databaseSizeBeforeUpdate = trashCompanyRepository.findAll().size();
        trashCompany.setId(count.incrementAndGet());

        // Create the TrashCompany
        TrashCompanyDTO trashCompanyDTO = trashCompanyMapper.toDto(trashCompany);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrashCompanyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, trashCompanyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trashCompanyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrashCompany in the database
        List<TrashCompany> trashCompanyList = trashCompanyRepository.findAll();
        assertThat(trashCompanyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTrashCompany() throws Exception {
        int databaseSizeBeforeUpdate = trashCompanyRepository.findAll().size();
        trashCompany.setId(count.incrementAndGet());

        // Create the TrashCompany
        TrashCompanyDTO trashCompanyDTO = trashCompanyMapper.toDto(trashCompany);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrashCompanyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trashCompanyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrashCompany in the database
        List<TrashCompany> trashCompanyList = trashCompanyRepository.findAll();
        assertThat(trashCompanyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTrashCompany() throws Exception {
        int databaseSizeBeforeUpdate = trashCompanyRepository.findAll().size();
        trashCompany.setId(count.incrementAndGet());

        // Create the TrashCompany
        TrashCompanyDTO trashCompanyDTO = trashCompanyMapper.toDto(trashCompany);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrashCompanyMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trashCompanyDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TrashCompany in the database
        List<TrashCompany> trashCompanyList = trashCompanyRepository.findAll();
        assertThat(trashCompanyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTrashCompanyWithPatch() throws Exception {
        // Initialize the database
        trashCompanyRepository.saveAndFlush(trashCompany);

        int databaseSizeBeforeUpdate = trashCompanyRepository.findAll().size();

        // Update the trashCompany using partial update
        TrashCompany partialUpdatedTrashCompany = new TrashCompany();
        partialUpdatedTrashCompany.setId(trashCompany.getId());

        partialUpdatedTrashCompany.phone(UPDATED_PHONE);

        restTrashCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTrashCompany.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTrashCompany))
            )
            .andExpect(status().isOk());

        // Validate the TrashCompany in the database
        List<TrashCompany> trashCompanyList = trashCompanyRepository.findAll();
        assertThat(trashCompanyList).hasSize(databaseSizeBeforeUpdate);
        TrashCompany testTrashCompany = trashCompanyList.get(trashCompanyList.size() - 1);
        assertThat(testTrashCompany.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTrashCompany.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    @Transactional
    void fullUpdateTrashCompanyWithPatch() throws Exception {
        // Initialize the database
        trashCompanyRepository.saveAndFlush(trashCompany);

        int databaseSizeBeforeUpdate = trashCompanyRepository.findAll().size();

        // Update the trashCompany using partial update
        TrashCompany partialUpdatedTrashCompany = new TrashCompany();
        partialUpdatedTrashCompany.setId(trashCompany.getId());

        partialUpdatedTrashCompany.name(UPDATED_NAME).phone(UPDATED_PHONE);

        restTrashCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTrashCompany.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTrashCompany))
            )
            .andExpect(status().isOk());

        // Validate the TrashCompany in the database
        List<TrashCompany> trashCompanyList = trashCompanyRepository.findAll();
        assertThat(trashCompanyList).hasSize(databaseSizeBeforeUpdate);
        TrashCompany testTrashCompany = trashCompanyList.get(trashCompanyList.size() - 1);
        assertThat(testTrashCompany.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTrashCompany.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    @Transactional
    void patchNonExistingTrashCompany() throws Exception {
        int databaseSizeBeforeUpdate = trashCompanyRepository.findAll().size();
        trashCompany.setId(count.incrementAndGet());

        // Create the TrashCompany
        TrashCompanyDTO trashCompanyDTO = trashCompanyMapper.toDto(trashCompany);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrashCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, trashCompanyDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(trashCompanyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrashCompany in the database
        List<TrashCompany> trashCompanyList = trashCompanyRepository.findAll();
        assertThat(trashCompanyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTrashCompany() throws Exception {
        int databaseSizeBeforeUpdate = trashCompanyRepository.findAll().size();
        trashCompany.setId(count.incrementAndGet());

        // Create the TrashCompany
        TrashCompanyDTO trashCompanyDTO = trashCompanyMapper.toDto(trashCompany);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrashCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(trashCompanyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrashCompany in the database
        List<TrashCompany> trashCompanyList = trashCompanyRepository.findAll();
        assertThat(trashCompanyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTrashCompany() throws Exception {
        int databaseSizeBeforeUpdate = trashCompanyRepository.findAll().size();
        trashCompany.setId(count.incrementAndGet());

        // Create the TrashCompany
        TrashCompanyDTO trashCompanyDTO = trashCompanyMapper.toDto(trashCompany);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrashCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(trashCompanyDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TrashCompany in the database
        List<TrashCompany> trashCompanyList = trashCompanyRepository.findAll();
        assertThat(trashCompanyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTrashCompany() throws Exception {
        // Initialize the database
        trashCompanyRepository.saveAndFlush(trashCompany);

        int databaseSizeBeforeDelete = trashCompanyRepository.findAll().size();

        // Delete the trashCompany
        restTrashCompanyMockMvc
            .perform(delete(ENTITY_API_URL_ID, trashCompany.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TrashCompany> trashCompanyList = trashCompanyRepository.findAll();
        assertThat(trashCompanyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
