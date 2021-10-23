package com.trash.green.city.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.trash.green.city.IntegrationTest;
import com.trash.green.city.domain.FullTrashImages;
import com.trash.green.city.repository.FullTrashImagesRepository;
import com.trash.green.city.service.dto.FullTrashImagesDTO;
import com.trash.green.city.service.mapper.FullTrashImagesMapper;
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
 * Integration tests for the {@link FullTrashImagesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FullTrashImagesResourceIT {

    private static final String DEFAULT_PATH = "AAAAAAAAAA";
    private static final String UPDATED_PATH = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/full-trash-images";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FullTrashImagesRepository fullTrashImagesRepository;

    @Autowired
    private FullTrashImagesMapper fullTrashImagesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFullTrashImagesMockMvc;

    private FullTrashImages fullTrashImages;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FullTrashImages createEntity(EntityManager em) {
        FullTrashImages fullTrashImages = new FullTrashImages().path(DEFAULT_PATH);
        return fullTrashImages;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FullTrashImages createUpdatedEntity(EntityManager em) {
        FullTrashImages fullTrashImages = new FullTrashImages().path(UPDATED_PATH);
        return fullTrashImages;
    }

    @BeforeEach
    public void initTest() {
        fullTrashImages = createEntity(em);
    }

    @Test
    @Transactional
    void createFullTrashImages() throws Exception {
        int databaseSizeBeforeCreate = fullTrashImagesRepository.findAll().size();
        // Create the FullTrashImages
        FullTrashImagesDTO fullTrashImagesDTO = fullTrashImagesMapper.toDto(fullTrashImages);
        restFullTrashImagesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fullTrashImagesDTO))
            )
            .andExpect(status().isCreated());

        // Validate the FullTrashImages in the database
        List<FullTrashImages> fullTrashImagesList = fullTrashImagesRepository.findAll();
        assertThat(fullTrashImagesList).hasSize(databaseSizeBeforeCreate + 1);
        FullTrashImages testFullTrashImages = fullTrashImagesList.get(fullTrashImagesList.size() - 1);
        assertThat(testFullTrashImages.getPath()).isEqualTo(DEFAULT_PATH);
    }

    @Test
    @Transactional
    void createFullTrashImagesWithExistingId() throws Exception {
        // Create the FullTrashImages with an existing ID
        fullTrashImages.setId(1L);
        FullTrashImagesDTO fullTrashImagesDTO = fullTrashImagesMapper.toDto(fullTrashImages);

        int databaseSizeBeforeCreate = fullTrashImagesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFullTrashImagesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fullTrashImagesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FullTrashImages in the database
        List<FullTrashImages> fullTrashImagesList = fullTrashImagesRepository.findAll();
        assertThat(fullTrashImagesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFullTrashImages() throws Exception {
        // Initialize the database
        fullTrashImagesRepository.saveAndFlush(fullTrashImages);

        // Get all the fullTrashImagesList
        restFullTrashImagesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fullTrashImages.getId().intValue())))
            .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH)));
    }

    @Test
    @Transactional
    void getFullTrashImages() throws Exception {
        // Initialize the database
        fullTrashImagesRepository.saveAndFlush(fullTrashImages);

        // Get the fullTrashImages
        restFullTrashImagesMockMvc
            .perform(get(ENTITY_API_URL_ID, fullTrashImages.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fullTrashImages.getId().intValue()))
            .andExpect(jsonPath("$.path").value(DEFAULT_PATH));
    }

    @Test
    @Transactional
    void getNonExistingFullTrashImages() throws Exception {
        // Get the fullTrashImages
        restFullTrashImagesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFullTrashImages() throws Exception {
        // Initialize the database
        fullTrashImagesRepository.saveAndFlush(fullTrashImages);

        int databaseSizeBeforeUpdate = fullTrashImagesRepository.findAll().size();

        // Update the fullTrashImages
        FullTrashImages updatedFullTrashImages = fullTrashImagesRepository.findById(fullTrashImages.getId()).get();
        // Disconnect from session so that the updates on updatedFullTrashImages are not directly saved in db
        em.detach(updatedFullTrashImages);
        updatedFullTrashImages.path(UPDATED_PATH);
        FullTrashImagesDTO fullTrashImagesDTO = fullTrashImagesMapper.toDto(updatedFullTrashImages);

        restFullTrashImagesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fullTrashImagesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fullTrashImagesDTO))
            )
            .andExpect(status().isOk());

        // Validate the FullTrashImages in the database
        List<FullTrashImages> fullTrashImagesList = fullTrashImagesRepository.findAll();
        assertThat(fullTrashImagesList).hasSize(databaseSizeBeforeUpdate);
        FullTrashImages testFullTrashImages = fullTrashImagesList.get(fullTrashImagesList.size() - 1);
        assertThat(testFullTrashImages.getPath()).isEqualTo(UPDATED_PATH);
    }

    @Test
    @Transactional
    void putNonExistingFullTrashImages() throws Exception {
        int databaseSizeBeforeUpdate = fullTrashImagesRepository.findAll().size();
        fullTrashImages.setId(count.incrementAndGet());

        // Create the FullTrashImages
        FullTrashImagesDTO fullTrashImagesDTO = fullTrashImagesMapper.toDto(fullTrashImages);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFullTrashImagesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fullTrashImagesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fullTrashImagesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FullTrashImages in the database
        List<FullTrashImages> fullTrashImagesList = fullTrashImagesRepository.findAll();
        assertThat(fullTrashImagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFullTrashImages() throws Exception {
        int databaseSizeBeforeUpdate = fullTrashImagesRepository.findAll().size();
        fullTrashImages.setId(count.incrementAndGet());

        // Create the FullTrashImages
        FullTrashImagesDTO fullTrashImagesDTO = fullTrashImagesMapper.toDto(fullTrashImages);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFullTrashImagesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fullTrashImagesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FullTrashImages in the database
        List<FullTrashImages> fullTrashImagesList = fullTrashImagesRepository.findAll();
        assertThat(fullTrashImagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFullTrashImages() throws Exception {
        int databaseSizeBeforeUpdate = fullTrashImagesRepository.findAll().size();
        fullTrashImages.setId(count.incrementAndGet());

        // Create the FullTrashImages
        FullTrashImagesDTO fullTrashImagesDTO = fullTrashImagesMapper.toDto(fullTrashImages);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFullTrashImagesMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fullTrashImagesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FullTrashImages in the database
        List<FullTrashImages> fullTrashImagesList = fullTrashImagesRepository.findAll();
        assertThat(fullTrashImagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFullTrashImagesWithPatch() throws Exception {
        // Initialize the database
        fullTrashImagesRepository.saveAndFlush(fullTrashImages);

        int databaseSizeBeforeUpdate = fullTrashImagesRepository.findAll().size();

        // Update the fullTrashImages using partial update
        FullTrashImages partialUpdatedFullTrashImages = new FullTrashImages();
        partialUpdatedFullTrashImages.setId(fullTrashImages.getId());

        restFullTrashImagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFullTrashImages.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFullTrashImages))
            )
            .andExpect(status().isOk());

        // Validate the FullTrashImages in the database
        List<FullTrashImages> fullTrashImagesList = fullTrashImagesRepository.findAll();
        assertThat(fullTrashImagesList).hasSize(databaseSizeBeforeUpdate);
        FullTrashImages testFullTrashImages = fullTrashImagesList.get(fullTrashImagesList.size() - 1);
        assertThat(testFullTrashImages.getPath()).isEqualTo(DEFAULT_PATH);
    }

    @Test
    @Transactional
    void fullUpdateFullTrashImagesWithPatch() throws Exception {
        // Initialize the database
        fullTrashImagesRepository.saveAndFlush(fullTrashImages);

        int databaseSizeBeforeUpdate = fullTrashImagesRepository.findAll().size();

        // Update the fullTrashImages using partial update
        FullTrashImages partialUpdatedFullTrashImages = new FullTrashImages();
        partialUpdatedFullTrashImages.setId(fullTrashImages.getId());

        partialUpdatedFullTrashImages.path(UPDATED_PATH);

        restFullTrashImagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFullTrashImages.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFullTrashImages))
            )
            .andExpect(status().isOk());

        // Validate the FullTrashImages in the database
        List<FullTrashImages> fullTrashImagesList = fullTrashImagesRepository.findAll();
        assertThat(fullTrashImagesList).hasSize(databaseSizeBeforeUpdate);
        FullTrashImages testFullTrashImages = fullTrashImagesList.get(fullTrashImagesList.size() - 1);
        assertThat(testFullTrashImages.getPath()).isEqualTo(UPDATED_PATH);
    }

    @Test
    @Transactional
    void patchNonExistingFullTrashImages() throws Exception {
        int databaseSizeBeforeUpdate = fullTrashImagesRepository.findAll().size();
        fullTrashImages.setId(count.incrementAndGet());

        // Create the FullTrashImages
        FullTrashImagesDTO fullTrashImagesDTO = fullTrashImagesMapper.toDto(fullTrashImages);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFullTrashImagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fullTrashImagesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fullTrashImagesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FullTrashImages in the database
        List<FullTrashImages> fullTrashImagesList = fullTrashImagesRepository.findAll();
        assertThat(fullTrashImagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFullTrashImages() throws Exception {
        int databaseSizeBeforeUpdate = fullTrashImagesRepository.findAll().size();
        fullTrashImages.setId(count.incrementAndGet());

        // Create the FullTrashImages
        FullTrashImagesDTO fullTrashImagesDTO = fullTrashImagesMapper.toDto(fullTrashImages);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFullTrashImagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fullTrashImagesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FullTrashImages in the database
        List<FullTrashImages> fullTrashImagesList = fullTrashImagesRepository.findAll();
        assertThat(fullTrashImagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFullTrashImages() throws Exception {
        int databaseSizeBeforeUpdate = fullTrashImagesRepository.findAll().size();
        fullTrashImages.setId(count.incrementAndGet());

        // Create the FullTrashImages
        FullTrashImagesDTO fullTrashImagesDTO = fullTrashImagesMapper.toDto(fullTrashImages);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFullTrashImagesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fullTrashImagesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FullTrashImages in the database
        List<FullTrashImages> fullTrashImagesList = fullTrashImagesRepository.findAll();
        assertThat(fullTrashImagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFullTrashImages() throws Exception {
        // Initialize the database
        fullTrashImagesRepository.saveAndFlush(fullTrashImages);

        int databaseSizeBeforeDelete = fullTrashImagesRepository.findAll().size();

        // Delete the fullTrashImages
        restFullTrashImagesMockMvc
            .perform(delete(ENTITY_API_URL_ID, fullTrashImages.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FullTrashImages> fullTrashImagesList = fullTrashImagesRepository.findAll();
        assertThat(fullTrashImagesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
