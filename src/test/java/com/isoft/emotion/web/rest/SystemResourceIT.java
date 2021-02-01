package com.isoft.emotion.web.rest;

import com.isoft.emotion.EmotionSurveyApp;
import com.isoft.emotion.domain.System;
import com.isoft.emotion.domain.Messages;
import com.isoft.emotion.domain.SystemServices;
import com.isoft.emotion.repository.SystemRepository;
import com.isoft.emotion.service.SystemService;
import com.isoft.emotion.service.dto.SystemDTO;
import com.isoft.emotion.service.mapper.SystemMapper;
import com.isoft.emotion.service.dto.SystemCriteria;
import com.isoft.emotion.service.SystemQueryService;

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
 * Integration tests for the {@link SystemResource} REST controller.
 */
@SpringBootTest(classes = EmotionSurveyApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SystemResourceIT {

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
    private SystemRepository systemRepository;

    @Autowired
    private SystemMapper systemMapper;

    @Autowired
    private SystemService systemService;

    @Autowired
    private SystemQueryService systemQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSystemMockMvc;

    private System system;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static System createEntity(EntityManager em) {
        System system = new System()
            .nameAr(DEFAULT_NAME_AR)
            .nameEn(DEFAULT_NAME_EN)
            .code(DEFAULT_CODE)
            .status(DEFAULT_STATUS);
        return system;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static System createUpdatedEntity(EntityManager em) {
        System system = new System()
            .nameAr(UPDATED_NAME_AR)
            .nameEn(UPDATED_NAME_EN)
            .code(UPDATED_CODE)
            .status(UPDATED_STATUS);
        return system;
    }

    @BeforeEach
    public void initTest() {
        system = createEntity(em);
    }

    @Test
    @Transactional
    public void createSystem() throws Exception {
        int databaseSizeBeforeCreate = systemRepository.findAll().size();
        // Create the System
        SystemDTO systemDTO = systemMapper.toDto(system);
        restSystemMockMvc.perform(post("/api/systems")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(systemDTO)))
            .andExpect(status().isCreated());

        // Validate the System in the database
        List<System> systemList = systemRepository.findAll();
        assertThat(systemList).hasSize(databaseSizeBeforeCreate + 1);
        System testSystem = systemList.get(systemList.size() - 1);
        assertThat(testSystem.getNameAr()).isEqualTo(DEFAULT_NAME_AR);
        assertThat(testSystem.getNameEn()).isEqualTo(DEFAULT_NAME_EN);
        assertThat(testSystem.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSystem.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createSystemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = systemRepository.findAll().size();

        // Create the System with an existing ID
        system.setId(1L);
        SystemDTO systemDTO = systemMapper.toDto(system);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSystemMockMvc.perform(post("/api/systems")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(systemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the System in the database
        List<System> systemList = systemRepository.findAll();
        assertThat(systemList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSystems() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        // Get all the systemList
        restSystemMockMvc.perform(get("/api/systems?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(system.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameAr").value(hasItem(DEFAULT_NAME_AR)))
            .andExpect(jsonPath("$.[*].nameEn").value(hasItem(DEFAULT_NAME_EN)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }
    
    @Test
    @Transactional
    public void getSystem() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        // Get the system
        restSystemMockMvc.perform(get("/api/systems/{id}", system.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(system.getId().intValue()))
            .andExpect(jsonPath("$.nameAr").value(DEFAULT_NAME_AR))
            .andExpect(jsonPath("$.nameEn").value(DEFAULT_NAME_EN))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }


    @Test
    @Transactional
    public void getSystemsByIdFiltering() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        Long id = system.getId();

        defaultSystemShouldBeFound("id.equals=" + id);
        defaultSystemShouldNotBeFound("id.notEquals=" + id);

        defaultSystemShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSystemShouldNotBeFound("id.greaterThan=" + id);

        defaultSystemShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSystemShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllSystemsByNameArIsEqualToSomething() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        // Get all the systemList where nameAr equals to DEFAULT_NAME_AR
        defaultSystemShouldBeFound("nameAr.equals=" + DEFAULT_NAME_AR);

        // Get all the systemList where nameAr equals to UPDATED_NAME_AR
        defaultSystemShouldNotBeFound("nameAr.equals=" + UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    public void getAllSystemsByNameArIsNotEqualToSomething() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        // Get all the systemList where nameAr not equals to DEFAULT_NAME_AR
        defaultSystemShouldNotBeFound("nameAr.notEquals=" + DEFAULT_NAME_AR);

        // Get all the systemList where nameAr not equals to UPDATED_NAME_AR
        defaultSystemShouldBeFound("nameAr.notEquals=" + UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    public void getAllSystemsByNameArIsInShouldWork() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        // Get all the systemList where nameAr in DEFAULT_NAME_AR or UPDATED_NAME_AR
        defaultSystemShouldBeFound("nameAr.in=" + DEFAULT_NAME_AR + "," + UPDATED_NAME_AR);

        // Get all the systemList where nameAr equals to UPDATED_NAME_AR
        defaultSystemShouldNotBeFound("nameAr.in=" + UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    public void getAllSystemsByNameArIsNullOrNotNull() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        // Get all the systemList where nameAr is not null
        defaultSystemShouldBeFound("nameAr.specified=true");

        // Get all the systemList where nameAr is null
        defaultSystemShouldNotBeFound("nameAr.specified=false");
    }
                @Test
    @Transactional
    public void getAllSystemsByNameArContainsSomething() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        // Get all the systemList where nameAr contains DEFAULT_NAME_AR
        defaultSystemShouldBeFound("nameAr.contains=" + DEFAULT_NAME_AR);

        // Get all the systemList where nameAr contains UPDATED_NAME_AR
        defaultSystemShouldNotBeFound("nameAr.contains=" + UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    public void getAllSystemsByNameArNotContainsSomething() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        // Get all the systemList where nameAr does not contain DEFAULT_NAME_AR
        defaultSystemShouldNotBeFound("nameAr.doesNotContain=" + DEFAULT_NAME_AR);

        // Get all the systemList where nameAr does not contain UPDATED_NAME_AR
        defaultSystemShouldBeFound("nameAr.doesNotContain=" + UPDATED_NAME_AR);
    }


    @Test
    @Transactional
    public void getAllSystemsByNameEnIsEqualToSomething() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        // Get all the systemList where nameEn equals to DEFAULT_NAME_EN
        defaultSystemShouldBeFound("nameEn.equals=" + DEFAULT_NAME_EN);

        // Get all the systemList where nameEn equals to UPDATED_NAME_EN
        defaultSystemShouldNotBeFound("nameEn.equals=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    public void getAllSystemsByNameEnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        // Get all the systemList where nameEn not equals to DEFAULT_NAME_EN
        defaultSystemShouldNotBeFound("nameEn.notEquals=" + DEFAULT_NAME_EN);

        // Get all the systemList where nameEn not equals to UPDATED_NAME_EN
        defaultSystemShouldBeFound("nameEn.notEquals=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    public void getAllSystemsByNameEnIsInShouldWork() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        // Get all the systemList where nameEn in DEFAULT_NAME_EN or UPDATED_NAME_EN
        defaultSystemShouldBeFound("nameEn.in=" + DEFAULT_NAME_EN + "," + UPDATED_NAME_EN);

        // Get all the systemList where nameEn equals to UPDATED_NAME_EN
        defaultSystemShouldNotBeFound("nameEn.in=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    public void getAllSystemsByNameEnIsNullOrNotNull() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        // Get all the systemList where nameEn is not null
        defaultSystemShouldBeFound("nameEn.specified=true");

        // Get all the systemList where nameEn is null
        defaultSystemShouldNotBeFound("nameEn.specified=false");
    }
                @Test
    @Transactional
    public void getAllSystemsByNameEnContainsSomething() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        // Get all the systemList where nameEn contains DEFAULT_NAME_EN
        defaultSystemShouldBeFound("nameEn.contains=" + DEFAULT_NAME_EN);

        // Get all the systemList where nameEn contains UPDATED_NAME_EN
        defaultSystemShouldNotBeFound("nameEn.contains=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    public void getAllSystemsByNameEnNotContainsSomething() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        // Get all the systemList where nameEn does not contain DEFAULT_NAME_EN
        defaultSystemShouldNotBeFound("nameEn.doesNotContain=" + DEFAULT_NAME_EN);

        // Get all the systemList where nameEn does not contain UPDATED_NAME_EN
        defaultSystemShouldBeFound("nameEn.doesNotContain=" + UPDATED_NAME_EN);
    }


    @Test
    @Transactional
    public void getAllSystemsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        // Get all the systemList where code equals to DEFAULT_CODE
        defaultSystemShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the systemList where code equals to UPDATED_CODE
        defaultSystemShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllSystemsByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        // Get all the systemList where code not equals to DEFAULT_CODE
        defaultSystemShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the systemList where code not equals to UPDATED_CODE
        defaultSystemShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllSystemsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        // Get all the systemList where code in DEFAULT_CODE or UPDATED_CODE
        defaultSystemShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the systemList where code equals to UPDATED_CODE
        defaultSystemShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllSystemsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        // Get all the systemList where code is not null
        defaultSystemShouldBeFound("code.specified=true");

        // Get all the systemList where code is null
        defaultSystemShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllSystemsByCodeContainsSomething() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        // Get all the systemList where code contains DEFAULT_CODE
        defaultSystemShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the systemList where code contains UPDATED_CODE
        defaultSystemShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllSystemsByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        // Get all the systemList where code does not contain DEFAULT_CODE
        defaultSystemShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the systemList where code does not contain UPDATED_CODE
        defaultSystemShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllSystemsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        // Get all the systemList where status equals to DEFAULT_STATUS
        defaultSystemShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the systemList where status equals to UPDATED_STATUS
        defaultSystemShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllSystemsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        // Get all the systemList where status not equals to DEFAULT_STATUS
        defaultSystemShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the systemList where status not equals to UPDATED_STATUS
        defaultSystemShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllSystemsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        // Get all the systemList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultSystemShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the systemList where status equals to UPDATED_STATUS
        defaultSystemShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllSystemsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        // Get all the systemList where status is not null
        defaultSystemShouldBeFound("status.specified=true");

        // Get all the systemList where status is null
        defaultSystemShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllSystemsByStatusIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        // Get all the systemList where status is greater than or equal to DEFAULT_STATUS
        defaultSystemShouldBeFound("status.greaterThanOrEqual=" + DEFAULT_STATUS);

        // Get all the systemList where status is greater than or equal to UPDATED_STATUS
        defaultSystemShouldNotBeFound("status.greaterThanOrEqual=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllSystemsByStatusIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        // Get all the systemList where status is less than or equal to DEFAULT_STATUS
        defaultSystemShouldBeFound("status.lessThanOrEqual=" + DEFAULT_STATUS);

        // Get all the systemList where status is less than or equal to SMALLER_STATUS
        defaultSystemShouldNotBeFound("status.lessThanOrEqual=" + SMALLER_STATUS);
    }

    @Test
    @Transactional
    public void getAllSystemsByStatusIsLessThanSomething() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        // Get all the systemList where status is less than DEFAULT_STATUS
        defaultSystemShouldNotBeFound("status.lessThan=" + DEFAULT_STATUS);

        // Get all the systemList where status is less than UPDATED_STATUS
        defaultSystemShouldBeFound("status.lessThan=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllSystemsByStatusIsGreaterThanSomething() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        // Get all the systemList where status is greater than DEFAULT_STATUS
        defaultSystemShouldNotBeFound("status.greaterThan=" + DEFAULT_STATUS);

        // Get all the systemList where status is greater than SMALLER_STATUS
        defaultSystemShouldBeFound("status.greaterThan=" + SMALLER_STATUS);
    }


    @Test
    @Transactional
    public void getAllSystemsBySystemMessagesIsEqualToSomething() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);
        Messages systemMessages = MessagesResourceIT.createEntity(em);
        em.persist(systemMessages);
        em.flush();
        system.addSystemMessages(systemMessages);
        systemRepository.saveAndFlush(system);
        Long systemMessagesId = systemMessages.getId();

        // Get all the systemList where systemMessages equals to systemMessagesId
        defaultSystemShouldBeFound("systemMessagesId.equals=" + systemMessagesId);

        // Get all the systemList where systemMessages equals to systemMessagesId + 1
        defaultSystemShouldNotBeFound("systemMessagesId.equals=" + (systemMessagesId + 1));
    }


    @Test
    @Transactional
    public void getAllSystemsBySystemServicesIsEqualToSomething() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);
        SystemServices systemServices = SystemServicesResourceIT.createEntity(em);
        em.persist(systemServices);
        em.flush();
        system.addSystemServices(systemServices);
        systemRepository.saveAndFlush(system);
        Long systemServicesId = systemServices.getId();

        // Get all the systemList where systemServices equals to systemServicesId
        defaultSystemShouldBeFound("systemServicesId.equals=" + systemServicesId);

        // Get all the systemList where systemServices equals to systemServicesId + 1
        defaultSystemShouldNotBeFound("systemServicesId.equals=" + (systemServicesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSystemShouldBeFound(String filter) throws Exception {
        restSystemMockMvc.perform(get("/api/systems?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(system.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameAr").value(hasItem(DEFAULT_NAME_AR)))
            .andExpect(jsonPath("$.[*].nameEn").value(hasItem(DEFAULT_NAME_EN)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));

        // Check, that the count call also returns 1
        restSystemMockMvc.perform(get("/api/systems/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSystemShouldNotBeFound(String filter) throws Exception {
        restSystemMockMvc.perform(get("/api/systems?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSystemMockMvc.perform(get("/api/systems/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingSystem() throws Exception {
        // Get the system
        restSystemMockMvc.perform(get("/api/systems/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSystem() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        int databaseSizeBeforeUpdate = systemRepository.findAll().size();

        // Update the system
        System updatedSystem = systemRepository.findById(system.getId()).get();
        // Disconnect from session so that the updates on updatedSystem are not directly saved in db
        em.detach(updatedSystem);
        updatedSystem
            .nameAr(UPDATED_NAME_AR)
            .nameEn(UPDATED_NAME_EN)
            .code(UPDATED_CODE)
            .status(UPDATED_STATUS);
        SystemDTO systemDTO = systemMapper.toDto(updatedSystem);

        restSystemMockMvc.perform(put("/api/systems")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(systemDTO)))
            .andExpect(status().isOk());

        // Validate the System in the database
        List<System> systemList = systemRepository.findAll();
        assertThat(systemList).hasSize(databaseSizeBeforeUpdate);
        System testSystem = systemList.get(systemList.size() - 1);
        assertThat(testSystem.getNameAr()).isEqualTo(UPDATED_NAME_AR);
        assertThat(testSystem.getNameEn()).isEqualTo(UPDATED_NAME_EN);
        assertThat(testSystem.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSystem.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingSystem() throws Exception {
        int databaseSizeBeforeUpdate = systemRepository.findAll().size();

        // Create the System
        SystemDTO systemDTO = systemMapper.toDto(system);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSystemMockMvc.perform(put("/api/systems")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(systemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the System in the database
        List<System> systemList = systemRepository.findAll();
        assertThat(systemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSystem() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        int databaseSizeBeforeDelete = systemRepository.findAll().size();

        // Delete the system
        restSystemMockMvc.perform(delete("/api/systems/{id}", system.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<System> systemList = systemRepository.findAll();
        assertThat(systemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
