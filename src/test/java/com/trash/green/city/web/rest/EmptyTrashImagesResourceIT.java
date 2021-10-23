package com.trash.green.city.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.trash.green.city.IntegrationTest;
import com.trash.green.city.domain.EmptyTrashImages;
import com.trash.green.city.repository.EmptyTrashImagesRepository;
import com.trash.green.city.service.dto.EmptyTrashImagesDTO;
import com.trash.green.city.service.mapper.EmptyTrashImagesMapper;
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
 * Integration tests for the {@link EmptyTrashImagesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmptyTrashImagesResourceIT {

    private static final String DEFAULT_PATH = "AAAAAAAAAA";
    private static final String UPDATED_PATH = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/empty-trash-images";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmptyTrashImagesRepository emptyTrashImagesRepository;

    @Autowired
    private EmptyTrashImagesMapper emptyTrashImagesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmptyTrashImagesMockMvc;

    private EmptyTrashImages emptyTrashImages;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmptyTrashImages createEntity(EntityManager em) {
        EmptyTrashImages emptyTrashImages = new EmptyTrashImages().path(DEFAULT_PATH);
        return emptyTrashImages;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmptyTrashImages createUpdatedEntity(EntityManager em) {
        EmptyTrashImages emptyTrashImages = new EmptyTrashImages().path(UPDATED_PATH);
        return emptyTrashImages;
    }

    @BeforeEach
    public void initTest() {
        emptyTrashImages = createEntity(em);
    }

    @Test
    @Transactional
    void createEmptyTrashImages() throws Exception {
        int databaseSizeBeforeCreate = emptyTrashImagesRepository.findAll().size();
        // Create the EmptyTrashImages
        EmptyTrashImagesDTO emptyTrashImagesDTO = emptyTrashImagesMapper.toDto(emptyTrashImages);
        restEmptyTrashImagesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emptyTrashImagesDTO))
            )
            .andExpect(status().isCreated());

        // Validate the EmptyTrashImages in the database
        List<EmptyTrashImages> emptyTrashImagesList = emptyTrashImagesRepository.findAll();
        assertThat(emptyTrashImagesList).hasSize(databaseSizeBeforeCreate + 1);
        EmptyTrashImages testEmptyTrashImages = emptyTrashImagesList.get(emptyTrashImagesList.size() - 1);
        assertThat(testEmptyTrashImages.getPath()).isEqualTo(DEFAULT_PATH);
    }

    @Test
    @Transactional
    void createEmptyTrashImagesWithExistingId() throws Exception {
        // Create the EmptyTrashImages with an existing ID
        emptyTrashImages.setId(1L);
        EmptyTrashImagesDTO emptyTrashImagesDTO = emptyTrashImagesMapper.toDto(emptyTrashImages);

        int databaseSizeBeforeCreate = emptyTrashImagesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmptyTrashImagesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emptyTrashImagesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmptyTrashImages in the database
        List<EmptyTrashImages> emptyTrashImagesList = emptyTrashImagesRepository.findAll();
        assertThat(emptyTrashImagesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEmptyTrashImages() throws Exception {
        // Initialize the database
        emptyTrashImagesRepository.saveAndFlush(emptyTrashImages);

        // Get all the emptyTrashImagesList
        restEmptyTrashImagesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emptyTrashImages.getId().intValue())))
            .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH)));
    }

    @Test
    @Transactional
    void getEmptyTrashImages() throws Exception {
        // Initialize the database
        emptyTrashImagesRepository.saveAndFlush(emptyTrashImages);

        // Get the emptyTrashImages
        restEmptyTrashImagesMockMvc
            .perform(get(ENTITY_API_URL_ID, emptyTrashImages.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(emptyTrashImages.getId().intValue()))
            .andExpect(jsonPath("$.path").value(DEFAULT_PATH));
    }

    @Test
    @Transactional
    void getNonExistingEmptyTrashImages() throws Exception {
        // Get the emptyTrashImages
        restEmptyTrashImagesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEmptyTrashImages() throws Exception {
        // Initialize the database
        emptyTrashImagesRepository.saveAndFlush(emptyTrashImages);

        int databaseSizeBeforeUpdate = emptyTrashImagesRepository.findAll().size();

        // Update the emptyTrashImages
        EmptyTrashImages updatedEmptyTrashImages = emptyTrashImagesRepository.findById(emptyTrashImages.getId()).get();
        // Disconnect from session so that the updates on updatedEmptyTrashImages are not directly saved in db
        em.detach(updatedEmptyTrashImages);
        updatedEmptyTrashImages.path(UPDATED_PATH);
        EmptyTrashImagesDTO emptyTrashImagesDTO = emptyTrashImagesMapper.toDto(updatedEmptyTrashImages);

        restEmptyTrashImagesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, emptyTrashImagesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(emptyTrashImagesDTO))
            )
            .andExpect(status().isOk());

        // Validate the EmptyTrashImages in the database
        List<EmptyTrashImages> emptyTrashImagesList = emptyTrashImagesRepository.findAll();
        assertThat(emptyTrashImagesList).hasSize(databaseSizeBeforeUpdate);
        EmptyTrashImages testEmptyTrashImages = emptyTrashImagesList.get(emptyTrashImagesList.size() - 1);
        assertThat(testEmptyTrashImages.getPath()).isEqualTo(UPDATED_PATH);
    }

    @Test
    @Transactional
    void putNonExistingEmptyTrashImages() throws Exception {
        int databaseSizeBeforeUpdate = emptyTrashImagesRepository.findAll().size();
        emptyTrashImages.setId(count.incrementAndGet());

        // Create the EmptyTrashImages
        EmptyTrashImagesDTO emptyTrashImagesDTO = emptyTrashImagesMapper.toDto(emptyTrashImages);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmptyTrashImagesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, emptyTrashImagesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(emptyTrashImagesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmptyTrashImages in the database
        List<EmptyTrashImages> emptyTrashImagesList = emptyTrashImagesRepository.findAll();
        assertThat(emptyTrashImagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmptyTrashImages() throws Exception {
        int databaseSizeBeforeUpdate = emptyTrashImagesRepository.findAll().size();
        emptyTrashImages.setId(count.incrementAndGet());

        // Create the EmptyTrashImages
        EmptyTrashImagesDTO emptyTrashImagesDTO = emptyTrashImagesMapper.toDto(emptyTrashImages);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmptyTrashImagesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(emptyTrashImagesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmptyTrashImages in the database
        List<EmptyTrashImages> emptyTrashImagesList = emptyTrashImagesRepository.findAll();
        assertThat(emptyTrashImagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmptyTrashImages() throws Exception {
        int databaseSizeBeforeUpdate = emptyTrashImagesRepository.findAll().size();
        emptyTrashImages.setId(count.incrementAndGet());

        // Create the EmptyTrashImages
        EmptyTrashImagesDTO emptyTrashImagesDTO = emptyTrashImagesMapper.toDto(emptyTrashImages);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmptyTrashImagesMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emptyTrashImagesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmptyTrashImages in the database
        List<EmptyTrashImages> emptyTrashImagesList = emptyTrashImagesRepository.findAll();
        assertThat(emptyTrashImagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmptyTrashImagesWithPatch() throws Exception {
        // Initialize the database
        emptyTrashImagesRepository.saveAndFlush(emptyTrashImages);

        int databaseSizeBeforeUpdate = emptyTrashImagesRepository.findAll().size();

        // Update the emptyTrashImages using partial update
        EmptyTrashImages partialUpdatedEmptyTrashImages = new EmptyTrashImages();
        partialUpdatedEmptyTrashImages.setId(emptyTrashImages.getId());

        restEmptyTrashImagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmptyTrashImages.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmptyTrashImages))
            )
            .andExpect(status().isOk());

        // Validate the EmptyTrashImages in the database
        List<EmptyTrashImages> emptyTrashImagesList = emptyTrashImagesRepository.findAll();
        assertThat(emptyTrashImagesList).hasSize(databaseSizeBeforeUpdate);
        EmptyTrashImages testEmptyTrashImages = emptyTrashImagesList.get(emptyTrashImagesList.size() - 1);
        assertThat(testEmptyTrashImages.getPath()).isEqualTo(DEFAULT_PATH);
    }

    @Test
    @Transactional
    void fullUpdateEmptyTrashImagesWithPatch() throws Exception {
        // Initialize the database
        emptyTrashImagesRepository.saveAndFlush(emptyTrashImages);

        int databaseSizeBeforeUpdate = emptyTrashImagesRepository.findAll().size();

        // Update the emptyTrashImages using partial update
        EmptyTrashImages partialUpdatedEmptyTrashImages = new EmptyTrashImages();
        partialUpdatedEmptyTrashImages.setId(emptyTrashImages.getId());

        partialUpdatedEmptyTrashImages.path(UPDATED_PATH);

        restEmptyTrashImagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmptyTrashImages.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmptyTrashImages))
            )
            .andExpect(status().isOk());

        // Validate the EmptyTrashImages in the database
        List<EmptyTrashImages> emptyTrashImagesList = emptyTrashImagesRepository.findAll();
        assertThat(emptyTrashImagesList).hasSize(databaseSizeBeforeUpdate);
        EmptyTrashImages testEmptyTrashImages = emptyTrashImagesList.get(emptyTrashImagesList.size() - 1);
        assertThat(testEmptyTrashImages.getPath()).isEqualTo(UPDATED_PATH);
    }

    @Test
    @Transactional
    void patchNonExistingEmptyTrashImages() throws Exception {
        int databaseSizeBeforeUpdate = emptyTrashImagesRepository.findAll().size();
        emptyTrashImages.setId(count.incrementAndGet());

        // Create the EmptyTrashImages
        EmptyTrashImagesDTO emptyTrashImagesDTO = emptyTrashImagesMapper.toDto(emptyTrashImages);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmptyTrashImagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, emptyTrashImagesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(emptyTrashImagesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmptyTrashImages in the database
        List<EmptyTrashImages> emptyTrashImagesList = emptyTrashImagesRepository.findAll();
        assertThat(emptyTrashImagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmptyTrashImages() throws Exception {
        int databaseSizeBeforeUpdate = emptyTrashImagesRepository.findAll().size();
        emptyTrashImages.setId(count.incrementAndGet());

        // Create the EmptyTrashImages
        EmptyTrashImagesDTO emptyTrashImagesDTO = emptyTrashImagesMapper.toDto(emptyTrashImages);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmptyTrashImagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(emptyTrashImagesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmptyTrashImages in the database
        List<EmptyTrashImages> emptyTrashImagesList = emptyTrashImagesRepository.findAll();
        assertThat(emptyTrashImagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmptyTrashImages() throws Exception {
        int databaseSizeBeforeUpdate = emptyTrashImagesRepository.findAll().size();
        emptyTrashImages.setId(count.incrementAndGet());

        // Create the EmptyTrashImages
        EmptyTrashImagesDTO emptyTrashImagesDTO = emptyTrashImagesMapper.toDto(emptyTrashImages);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmptyTrashImagesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(emptyTrashImagesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmptyTrashImages in the database
        List<EmptyTrashImages> emptyTrashImagesList = emptyTrashImagesRepository.findAll();
        assertThat(emptyTrashImagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmptyTrashImages() throws Exception {
        // Initialize the database
        emptyTrashImagesRepository.saveAndFlush(emptyTrashImages);

        int databaseSizeBeforeDelete = emptyTrashImagesRepository.findAll().size();

        // Delete the emptyTrashImages
        restEmptyTrashImagesMockMvc
            .perform(delete(ENTITY_API_URL_ID, emptyTrashImages.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmptyTrashImages> emptyTrashImagesList = emptyTrashImagesRepository.findAll();
        assertThat(emptyTrashImagesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
