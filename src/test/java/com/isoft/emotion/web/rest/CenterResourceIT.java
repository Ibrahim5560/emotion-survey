package com.isoft.emotion.web.rest;

import com.isoft.emotion.EmotionSurveyApp;
import com.isoft.emotion.domain.Center;
import com.isoft.emotion.domain.Messages;
import com.isoft.emotion.repository.CenterRepository;
import com.isoft.emotion.service.CenterService;
import com.isoft.emotion.service.dto.CenterDTO;
import com.isoft.emotion.service.mapper.CenterMapper;
import com.isoft.emotion.service.dto.CenterCriteria;
import com.isoft.emotion.service.CenterQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CenterResource} REST controller.
 */
@SpringBootTest(classes = EmotionSurveyApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CenterResourceIT {

    private static final String DEFAULT_NAME_AR = "AAAAAAAAAA";
    private static final String UPDATED_NAME_AR = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_EN = "AAAAAAAAAA";
    private static final String UPDATED_NAME_EN = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;
    private static final Integer SMALLER_STATUS = 1 - 1;

    @Autowired
    private CenterRepository centerRepository;

    @Autowired
    private CenterMapper centerMapper;

    @Autowired
    private CenterService centerService;

    @Autowired
    private CenterQueryService centerQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCenterMockMvc;

    private Center center;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Center createEntity(EntityManager em) {
        Center center = new Center()
            .nameAr(DEFAULT_NAME_AR)
            .nameEn(DEFAULT_NAME_EN)
            .code(DEFAULT_CODE)
            .status(DEFAULT_STATUS);
        return center;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Center createUpdatedEntity(EntityManager em) {
        Center center = new Center()
            .nameAr(UPDATED_NAME_AR)
            .nameEn(UPDATED_NAME_EN)
            .code(UPDATED_CODE)
            .status(UPDATED_STATUS);
        return center;
    }

    @BeforeEach
    public void initTest() {
        center = createEntity(em);
    }

    @Test
    @Transactional
    public void createCenter() throws Exception {
        int databaseSizeBeforeCreate = centerRepository.findAll().size();
        // Create the Center
        CenterDTO centerDTO = centerMapper.toDto(center);
        restCenterMockMvc.perform(post("/api/centers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(centerDTO)))
            .andExpect(status().isCreated());

        // Validate the Center in the database
        List<Center> centerList = centerRepository.findAll();
        assertThat(centerList).hasSize(databaseSizeBeforeCreate + 1);
        Center testCenter = centerList.get(centerList.size() - 1);
        assertThat(testCenter.getNameAr()).isEqualTo(DEFAULT_NAME_AR);
        assertThat(testCenter.getNameEn()).isEqualTo(DEFAULT_NAME_EN);
        assertThat(testCenter.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCenter.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createCenterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = centerRepository.findAll().size();

        // Create the Center with an existing ID
        center.setId(1L);
        CenterDTO centerDTO = centerMapper.toDto(center);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCenterMockMvc.perform(post("/api/centers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(centerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Center in the database
        List<Center> centerList = centerRepository.findAll();
        assertThat(centerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCenters() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList
        restCenterMockMvc.perform(get("/api/centers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(center.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameAr").value(hasItem(DEFAULT_NAME_AR)))
            .andExpect(jsonPath("$.[*].nameEn").value(hasItem(DEFAULT_NAME_EN)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }
    
    @Test
    @Transactional
    public void getCenter() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get the center
        restCenterMockMvc.perform(get("/api/centers/{id}", center.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(center.getId().intValue()))
            .andExpect(jsonPath("$.nameAr").value(DEFAULT_NAME_AR))
            .andExpect(jsonPath("$.nameEn").value(DEFAULT_NAME_EN))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }


    @Test
    @Transactional
    public void getCentersByIdFiltering() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        Long id = center.getId();

        defaultCenterShouldBeFound("id.equals=" + id);
        defaultCenterShouldNotBeFound("id.notEquals=" + id);

        defaultCenterShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCenterShouldNotBeFound("id.greaterThan=" + id);

        defaultCenterShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCenterShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCentersByNameArIsEqualToSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where nameAr equals to DEFAULT_NAME_AR
        defaultCenterShouldBeFound("nameAr.equals=" + DEFAULT_NAME_AR);

        // Get all the centerList where nameAr equals to UPDATED_NAME_AR
        defaultCenterShouldNotBeFound("nameAr.equals=" + UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    public void getAllCentersByNameArIsNotEqualToSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where nameAr not equals to DEFAULT_NAME_AR
        defaultCenterShouldNotBeFound("nameAr.notEquals=" + DEFAULT_NAME_AR);

        // Get all the centerList where nameAr not equals to UPDATED_NAME_AR
        defaultCenterShouldBeFound("nameAr.notEquals=" + UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    public void getAllCentersByNameArIsInShouldWork() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where nameAr in DEFAULT_NAME_AR or UPDATED_NAME_AR
        defaultCenterShouldBeFound("nameAr.in=" + DEFAULT_NAME_AR + "," + UPDATED_NAME_AR);

        // Get all the centerList where nameAr equals to UPDATED_NAME_AR
        defaultCenterShouldNotBeFound("nameAr.in=" + UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    public void getAllCentersByNameArIsNullOrNotNull() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where nameAr is not null
        defaultCenterShouldBeFound("nameAr.specified=true");

        // Get all the centerList where nameAr is null
        defaultCenterShouldNotBeFound("nameAr.specified=false");
    }
                @Test
    @Transactional
    public void getAllCentersByNameArContainsSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where nameAr contains DEFAULT_NAME_AR
        defaultCenterShouldBeFound("nameAr.contains=" + DEFAULT_NAME_AR);

        // Get all the centerList where nameAr contains UPDATED_NAME_AR
        defaultCenterShouldNotBeFound("nameAr.contains=" + UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    public void getAllCentersByNameArNotContainsSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where nameAr does not contain DEFAULT_NAME_AR
        defaultCenterShouldNotBeFound("nameAr.doesNotContain=" + DEFAULT_NAME_AR);

        // Get all the centerList where nameAr does not contain UPDATED_NAME_AR
        defaultCenterShouldBeFound("nameAr.doesNotContain=" + UPDATED_NAME_AR);
    }


    @Test
    @Transactional
    public void getAllCentersByNameEnIsEqualToSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where nameEn equals to DEFAULT_NAME_EN
        defaultCenterShouldBeFound("nameEn.equals=" + DEFAULT_NAME_EN);

        // Get all the centerList where nameEn equals to UPDATED_NAME_EN
        defaultCenterShouldNotBeFound("nameEn.equals=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    public void getAllCentersByNameEnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where nameEn not equals to DEFAULT_NAME_EN
        defaultCenterShouldNotBeFound("nameEn.notEquals=" + DEFAULT_NAME_EN);

        // Get all the centerList where nameEn not equals to UPDATED_NAME_EN
        defaultCenterShouldBeFound("nameEn.notEquals=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    public void getAllCentersByNameEnIsInShouldWork() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where nameEn in DEFAULT_NAME_EN or UPDATED_NAME_EN
        defaultCenterShouldBeFound("nameEn.in=" + DEFAULT_NAME_EN + "," + UPDATED_NAME_EN);

        // Get all the centerList where nameEn equals to UPDATED_NAME_EN
        defaultCenterShouldNotBeFound("nameEn.in=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    public void getAllCentersByNameEnIsNullOrNotNull() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where nameEn is not null
        defaultCenterShouldBeFound("nameEn.specified=true");

        // Get all the centerList where nameEn is null
        defaultCenterShouldNotBeFound("nameEn.specified=false");
    }
                @Test
    @Transactional
    public void getAllCentersByNameEnContainsSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where nameEn contains DEFAULT_NAME_EN
        defaultCenterShouldBeFound("nameEn.contains=" + DEFAULT_NAME_EN);

        // Get all the centerList where nameEn contains UPDATED_NAME_EN
        defaultCenterShouldNotBeFound("nameEn.contains=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    public void getAllCentersByNameEnNotContainsSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where nameEn does not contain DEFAULT_NAME_EN
        defaultCenterShouldNotBeFound("nameEn.doesNotContain=" + DEFAULT_NAME_EN);

        // Get all the centerList where nameEn does not contain UPDATED_NAME_EN
        defaultCenterShouldBeFound("nameEn.doesNotContain=" + UPDATED_NAME_EN);
    }


    @Test
    @Transactional
    public void getAllCentersByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where code equals to DEFAULT_CODE
        defaultCenterShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the centerList where code equals to UPDATED_CODE
        defaultCenterShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCentersByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where code not equals to DEFAULT_CODE
        defaultCenterShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the centerList where code not equals to UPDATED_CODE
        defaultCenterShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCentersByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where code in DEFAULT_CODE or UPDATED_CODE
        defaultCenterShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the centerList where code equals to UPDATED_CODE
        defaultCenterShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCentersByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where code is not null
        defaultCenterShouldBeFound("code.specified=true");

        // Get all the centerList where code is null
        defaultCenterShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllCentersByCodeContainsSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where code contains DEFAULT_CODE
        defaultCenterShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the centerList where code contains UPDATED_CODE
        defaultCenterShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCentersByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where code does not contain DEFAULT_CODE
        defaultCenterShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the centerList where code does not contain UPDATED_CODE
        defaultCenterShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllCentersByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where status equals to DEFAULT_STATUS
        defaultCenterShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the centerList where status equals to UPDATED_STATUS
        defaultCenterShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllCentersByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where status not equals to DEFAULT_STATUS
        defaultCenterShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the centerList where status not equals to UPDATED_STATUS
        defaultCenterShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllCentersByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultCenterShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the centerList where status equals to UPDATED_STATUS
        defaultCenterShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllCentersByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where status is not null
        defaultCenterShouldBeFound("status.specified=true");

        // Get all the centerList where status is null
        defaultCenterShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllCentersByStatusIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where status is greater than or equal to DEFAULT_STATUS
        defaultCenterShouldBeFound("status.greaterThanOrEqual=" + DEFAULT_STATUS);

        // Get all the centerList where status is greater than or equal to UPDATED_STATUS
        defaultCenterShouldNotBeFound("status.greaterThanOrEqual=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllCentersByStatusIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where status is less than or equal to DEFAULT_STATUS
        defaultCenterShouldBeFound("status.lessThanOrEqual=" + DEFAULT_STATUS);

        // Get all the centerList where status is less than or equal to SMALLER_STATUS
        defaultCenterShouldNotBeFound("status.lessThanOrEqual=" + SMALLER_STATUS);
    }

    @Test
    @Transactional
    public void getAllCentersByStatusIsLessThanSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where status is less than DEFAULT_STATUS
        defaultCenterShouldNotBeFound("status.lessThan=" + DEFAULT_STATUS);

        // Get all the centerList where status is less than UPDATED_STATUS
        defaultCenterShouldBeFound("status.lessThan=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllCentersByStatusIsGreaterThanSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where status is greater than DEFAULT_STATUS
        defaultCenterShouldNotBeFound("status.greaterThan=" + DEFAULT_STATUS);

        // Get all the centerList where status is greater than SMALLER_STATUS
        defaultCenterShouldBeFound("status.greaterThan=" + SMALLER_STATUS);
    }


    @Test
    @Transactional
    public void getAllCentersByCenterMessagesIsEqualToSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);
        Messages centerMessages = MessagesResourceIT.createEntity(em);
        em.persist(centerMessages);
        em.flush();
        center.addCenterMessages(centerMessages);
        centerRepository.saveAndFlush(center);
        Long centerMessagesId = centerMessages.getId();

        // Get all the centerList where centerMessages equals to centerMessagesId
        defaultCenterShouldBeFound("centerMessagesId.equals=" + centerMessagesId);

        // Get all the centerList where centerMessages equals to centerMessagesId + 1
        defaultCenterShouldNotBeFound("centerMessagesId.equals=" + (centerMessagesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCenterShouldBeFound(String filter) throws Exception {
        restCenterMockMvc.perform(get("/api/centers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(center.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameAr").value(hasItem(DEFAULT_NAME_AR)))
            .andExpect(jsonPath("$.[*].nameEn").value(hasItem(DEFAULT_NAME_EN)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));

        // Check, that the count call also returns 1
        restCenterMockMvc.perform(get("/api/centers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCenterShouldNotBeFound(String filter) throws Exception {
        restCenterMockMvc.perform(get("/api/centers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCenterMockMvc.perform(get("/api/centers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCenter() throws Exception {
        // Get the center
        restCenterMockMvc.perform(get("/api/centers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCenter() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        int databaseSizeBeforeUpdate = centerRepository.findAll().size();

        // Update the center
        Center updatedCenter = centerRepository.findById(center.getId()).get();
        // Disconnect from session so that the updates on updatedCenter are not directly saved in db
        em.detach(updatedCenter);
        updatedCenter
            .nameAr(UPDATED_NAME_AR)
            .nameEn(UPDATED_NAME_EN)
            .code(UPDATED_CODE)
            .status(UPDATED_STATUS);
        CenterDTO centerDTO = centerMapper.toDto(updatedCenter);

        restCenterMockMvc.perform(put("/api/centers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(centerDTO)))
            .andExpect(status().isOk());

        // Validate the Center in the database
        List<Center> centerList = centerRepository.findAll();
        assertThat(centerList).hasSize(databaseSizeBeforeUpdate);
        Center testCenter = centerList.get(centerList.size() - 1);
        assertThat(testCenter.getNameAr()).isEqualTo(UPDATED_NAME_AR);
        assertThat(testCenter.getNameEn()).isEqualTo(UPDATED_NAME_EN);
        assertThat(testCenter.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCenter.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingCenter() throws Exception {
        int databaseSizeBeforeUpdate = centerRepository.findAll().size();

        // Create the Center
        CenterDTO centerDTO = centerMapper.toDto(center);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCenterMockMvc.perform(put("/api/centers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(centerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Center in the database
        List<Center> centerList = centerRepository.findAll();
        assertThat(centerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCenter() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        int databaseSizeBeforeDelete = centerRepository.findAll().size();

        // Delete the center
        restCenterMockMvc.perform(delete("/api/centers/{id}", center.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Center> centerList = centerRepository.findAll();
        assertThat(centerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
