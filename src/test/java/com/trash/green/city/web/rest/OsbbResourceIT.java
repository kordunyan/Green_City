package com.trash.green.city.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.trash.green.city.IntegrationTest;
import com.trash.green.city.domain.Osbb;
import com.trash.green.city.repository.OsbbRepository;
import com.trash.green.city.service.dto.OsbbDTO;
import com.trash.green.city.service.mapper.OsbbMapper;
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
 * Integration tests for the {@link OsbbResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OsbbResourceIT {

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_GEO = "AAAAAAAAAA";
    private static final String UPDATED_GEO = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/osbbs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OsbbRepository osbbRepository;

    @Autowired
    private OsbbMapper osbbMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOsbbMockMvc;

    private Osbb osbb;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Osbb createEntity(EntityManager em) {
        Osbb osbb = new Osbb().address(DEFAULT_ADDRESS).geo(DEFAULT_GEO).name(DEFAULT_NAME);
        return osbb;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Osbb createUpdatedEntity(EntityManager em) {
        Osbb osbb = new Osbb().address(UPDATED_ADDRESS).geo(UPDATED_GEO).name(UPDATED_NAME);
        return osbb;
    }

    @BeforeEach
    public void initTest() {
        osbb = createEntity(em);
    }

    @Test
    @Transactional
    void createOsbb() throws Exception {
        int databaseSizeBeforeCreate = osbbRepository.findAll().size();
        // Create the Osbb
        OsbbDTO osbbDTO = osbbMapper.toDto(osbb);
        restOsbbMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(osbbDTO)))
            .andExpect(status().isCreated());

        // Validate the Osbb in the database
        List<Osbb> osbbList = osbbRepository.findAll();
        assertThat(osbbList).hasSize(databaseSizeBeforeCreate + 1);
        Osbb testOsbb = osbbList.get(osbbList.size() - 1);
        assertThat(testOsbb.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testOsbb.getGeo()).isEqualTo(DEFAULT_GEO);
        assertThat(testOsbb.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createOsbbWithExistingId() throws Exception {
        // Create the Osbb with an existing ID
        osbb.setId(1L);
        OsbbDTO osbbDTO = osbbMapper.toDto(osbb);

        int databaseSizeBeforeCreate = osbbRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOsbbMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(osbbDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Osbb in the database
        List<Osbb> osbbList = osbbRepository.findAll();
        assertThat(osbbList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOsbbs() throws Exception {
        // Initialize the database
        osbbRepository.saveAndFlush(osbb);

        // Get all the osbbList
        restOsbbMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(osbb.getId().intValue())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].geo").value(hasItem(DEFAULT_GEO)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getOsbb() throws Exception {
        // Initialize the database
        osbbRepository.saveAndFlush(osbb);

        // Get the osbb
        restOsbbMockMvc
            .perform(get(ENTITY_API_URL_ID, osbb.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(osbb.getId().intValue()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.geo").value(DEFAULT_GEO))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingOsbb() throws Exception {
        // Get the osbb
        restOsbbMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOsbb() throws Exception {
        // Initialize the database
        osbbRepository.saveAndFlush(osbb);

        int databaseSizeBeforeUpdate = osbbRepository.findAll().size();

        // Update the osbb
        Osbb updatedOsbb = osbbRepository.findById(osbb.getId()).get();
        // Disconnect from session so that the updates on updatedOsbb are not directly saved in db
        em.detach(updatedOsbb);
        updatedOsbb.address(UPDATED_ADDRESS).geo(UPDATED_GEO).name(UPDATED_NAME);
        OsbbDTO osbbDTO = osbbMapper.toDto(updatedOsbb);

        restOsbbMockMvc
            .perform(
                put(ENTITY_API_URL_ID, osbbDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(osbbDTO))
            )
            .andExpect(status().isOk());

        // Validate the Osbb in the database
        List<Osbb> osbbList = osbbRepository.findAll();
        assertThat(osbbList).hasSize(databaseSizeBeforeUpdate);
        Osbb testOsbb = osbbList.get(osbbList.size() - 1);
        assertThat(testOsbb.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testOsbb.getGeo()).isEqualTo(UPDATED_GEO);
        assertThat(testOsbb.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingOsbb() throws Exception {
        int databaseSizeBeforeUpdate = osbbRepository.findAll().size();
        osbb.setId(count.incrementAndGet());

        // Create the Osbb
        OsbbDTO osbbDTO = osbbMapper.toDto(osbb);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOsbbMockMvc
            .perform(
                put(ENTITY_API_URL_ID, osbbDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(osbbDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Osbb in the database
        List<Osbb> osbbList = osbbRepository.findAll();
        assertThat(osbbList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOsbb() throws Exception {
        int databaseSizeBeforeUpdate = osbbRepository.findAll().size();
        osbb.setId(count.incrementAndGet());

        // Create the Osbb
        OsbbDTO osbbDTO = osbbMapper.toDto(osbb);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOsbbMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(osbbDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Osbb in the database
        List<Osbb> osbbList = osbbRepository.findAll();
        assertThat(osbbList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOsbb() throws Exception {
        int databaseSizeBeforeUpdate = osbbRepository.findAll().size();
        osbb.setId(count.incrementAndGet());

        // Create the Osbb
        OsbbDTO osbbDTO = osbbMapper.toDto(osbb);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOsbbMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(osbbDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Osbb in the database
        List<Osbb> osbbList = osbbRepository.findAll();
        assertThat(osbbList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOsbbWithPatch() throws Exception {
        // Initialize the database
        osbbRepository.saveAndFlush(osbb);

        int databaseSizeBeforeUpdate = osbbRepository.findAll().size();

        // Update the osbb using partial update
        Osbb partialUpdatedOsbb = new Osbb();
        partialUpdatedOsbb.setId(osbb.getId());

        partialUpdatedOsbb.address(UPDATED_ADDRESS);

        restOsbbMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOsbb.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOsbb))
            )
            .andExpect(status().isOk());

        // Validate the Osbb in the database
        List<Osbb> osbbList = osbbRepository.findAll();
        assertThat(osbbList).hasSize(databaseSizeBeforeUpdate);
        Osbb testOsbb = osbbList.get(osbbList.size() - 1);
        assertThat(testOsbb.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testOsbb.getGeo()).isEqualTo(DEFAULT_GEO);
        assertThat(testOsbb.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateOsbbWithPatch() throws Exception {
        // Initialize the database
        osbbRepository.saveAndFlush(osbb);

        int databaseSizeBeforeUpdate = osbbRepository.findAll().size();

        // Update the osbb using partial update
        Osbb partialUpdatedOsbb = new Osbb();
        partialUpdatedOsbb.setId(osbb.getId());

        partialUpdatedOsbb.address(UPDATED_ADDRESS).geo(UPDATED_GEO).name(UPDATED_NAME);

        restOsbbMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOsbb.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOsbb))
            )
            .andExpect(status().isOk());

        // Validate the Osbb in the database
        List<Osbb> osbbList = osbbRepository.findAll();
        assertThat(osbbList).hasSize(databaseSizeBeforeUpdate);
        Osbb testOsbb = osbbList.get(osbbList.size() - 1);
        assertThat(testOsbb.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testOsbb.getGeo()).isEqualTo(UPDATED_GEO);
        assertThat(testOsbb.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingOsbb() throws Exception {
        int databaseSizeBeforeUpdate = osbbRepository.findAll().size();
        osbb.setId(count.incrementAndGet());

        // Create the Osbb
        OsbbDTO osbbDTO = osbbMapper.toDto(osbb);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOsbbMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, osbbDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(osbbDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Osbb in the database
        List<Osbb> osbbList = osbbRepository.findAll();
        assertThat(osbbList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOsbb() throws Exception {
        int databaseSizeBeforeUpdate = osbbRepository.findAll().size();
        osbb.setId(count.incrementAndGet());

        // Create the Osbb
        OsbbDTO osbbDTO = osbbMapper.toDto(osbb);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOsbbMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(osbbDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Osbb in the database
        List<Osbb> osbbList = osbbRepository.findAll();
        assertThat(osbbList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOsbb() throws Exception {
        int databaseSizeBeforeUpdate = osbbRepository.findAll().size();
        osbb.setId(count.incrementAndGet());

        // Create the Osbb
        OsbbDTO osbbDTO = osbbMapper.toDto(osbb);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOsbbMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(osbbDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Osbb in the database
        List<Osbb> osbbList = osbbRepository.findAll();
        assertThat(osbbList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOsbb() throws Exception {
        // Initialize the database
        osbbRepository.saveAndFlush(osbb);

        int databaseSizeBeforeDelete = osbbRepository.findAll().size();

        // Delete the osbb
        restOsbbMockMvc
            .perform(delete(ENTITY_API_URL_ID, osbb.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Osbb> osbbList = osbbRepository.findAll();
        assertThat(osbbList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
