package com.isoft.emotion.web.rest;

import com.isoft.emotion.EmotionSurveyApp;
import com.isoft.emotion.domain.SystemServices;
import com.isoft.emotion.domain.Messages;
import com.isoft.emotion.domain.System;
import com.isoft.emotion.repository.SystemServicesRepository;
import com.isoft.emotion.service.SystemServicesService;
import com.isoft.emotion.service.dto.SystemServicesDTO;
import com.isoft.emotion.service.mapper.SystemServicesMapper;
import com.isoft.emotion.service.dto.SystemServicesCriteria;
import com.isoft.emotion.service.SystemServicesQueryService;

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
 * Integration tests for the {@link SystemServicesResource} REST controller.
 */
@SpringBootTest(classes = EmotionSurveyApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SystemServicesResourceIT {

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
    private SystemServicesRepository systemServicesRepository;

    @Autowired
    private SystemServicesMapper systemServicesMapper;

    @Autowired
    private SystemServicesService systemServicesService;

    @Autowired
    private SystemServicesQueryService systemServicesQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSystemServicesMockMvc;

    private SystemServices systemServices;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SystemServices createEntity(EntityManager em) {
        SystemServices systemServices = new SystemServices()
            .nameAr(DEFAULT_NAME_AR)
            .nameEn(DEFAULT_NAME_EN)
            .code(DEFAULT_CODE)
            .status(DEFAULT_STATUS);
        return systemServices;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SystemServices createUpdatedEntity(EntityManager em) {
        SystemServices systemServices = new SystemServices()
            .nameAr(UPDATED_NAME_AR)
            .nameEn(UPDATED_NAME_EN)
            .code(UPDATED_CODE)
            .status(UPDATED_STATUS);
        return systemServices;
    }

    @BeforeEach
    public void initTest() {
        systemServices = createEntity(em);
    }

    @Test
    @Transactional
    public void createSystemServices() throws Exception {
        int databaseSizeBeforeCreate = systemServicesRepository.findAll().size();
        // Create the SystemServices
        SystemServicesDTO systemServicesDTO = systemServicesMapper.toDto(systemServices);
        restSystemServicesMockMvc.perform(post("/api/system-services")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(systemServicesDTO)))
            .andExpect(status().isCreated());

        // Validate the SystemServices in the database
        List<SystemServices> systemServicesList = systemServicesRepository.findAll();
        assertThat(systemServicesList).hasSize(databaseSizeBeforeCreate + 1);
        SystemServices testSystemServices = systemServicesList.get(systemServicesList.size() - 1);
        assertThat(testSystemServices.getNameAr()).isEqualTo(DEFAULT_NAME_AR);
        assertThat(testSystemServices.getNameEn()).isEqualTo(DEFAULT_NAME_EN);
        assertThat(testSystemServices.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSystemServices.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createSystemServicesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = systemServicesRepository.findAll().size();

        // Create the SystemServices with an existing ID
        systemServices.setId(1L);
        SystemServicesDTO systemServicesDTO = systemServicesMapper.toDto(systemServices);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSystemServicesMockMvc.perform(post("/api/system-services")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(systemServicesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SystemServices in the database
        List<SystemServices> systemServicesList = systemServicesRepository.findAll();
        assertThat(systemServicesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSystemServices() throws Exception {
        // Initialize the database
        systemServicesRepository.saveAndFlush(systemServices);

        // Get all the systemServicesList
        restSystemServicesMockMvc.perform(get("/api/system-services?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(systemServices.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameAr").value(hasItem(DEFAULT_NAME_AR)))
            .andExpect(jsonPath("$.[*].nameEn").value(hasItem(DEFAULT_NAME_EN)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }
    
    @Test
    @Transactional
    public void getSystemServices() throws Exception {
        // Initialize the database
        systemServicesRepository.saveAndFlush(systemServices);

        // Get the systemServices
        restSystemServicesMockMvc.perform(get("/api/system-services/{id}", systemServices.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(systemServices.getId().intValue()))
            .andExpect(jsonPath("$.nameAr").value(DEFAULT_NAME_AR))
            .andExpect(jsonPath("$.nameEn").value(DEFAULT_NAME_EN))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }


    @Test
    @Transactional
    public void getSystemServicesByIdFiltering() throws Exception {
        // Initialize the database
        systemServicesRepository.saveAndFlush(systemServices);

        Long id = systemServices.getId();

        defaultSystemServicesShouldBeFound("id.equals=" + id);
        defaultSystemServicesShouldNotBeFound("id.notEquals=" + id);

        defaultSystemServicesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSystemServicesShouldNotBeFound("id.greaterThan=" + id);

        defaultSystemServicesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSystemServicesShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllSystemServicesByNameArIsEqualToSomething() throws Exception {
        // Initialize the database
        systemServicesRepository.saveAndFlush(systemServices);

        // Get all the systemServicesList where nameAr equals to DEFAULT_NAME_AR
        defaultSystemServicesShouldBeFound("nameAr.equals=" + DEFAULT_NAME_AR);

        // Get all the systemServicesList where nameAr equals to UPDATED_NAME_AR
        defaultSystemServicesShouldNotBeFound("nameAr.equals=" + UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    public void getAllSystemServicesByNameArIsNotEqualToSomething() throws Exception {
        // Initialize the database
        systemServicesRepository.saveAndFlush(systemServices);

        // Get all the systemServicesList where nameAr not equals to DEFAULT_NAME_AR
        defaultSystemServicesShouldNotBeFound("nameAr.notEquals=" + DEFAULT_NAME_AR);

        // Get all the systemServicesList where nameAr not equals to UPDATED_NAME_AR
        defaultSystemServicesShouldBeFound("nameAr.notEquals=" + UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    public void getAllSystemServicesByNameArIsInShouldWork() throws Exception {
        // Initialize the database
        systemServicesRepository.saveAndFlush(systemServices);

        // Get all the systemServicesList where nameAr in DEFAULT_NAME_AR or UPDATED_NAME_AR
        defaultSystemServicesShouldBeFound("nameAr.in=" + DEFAULT_NAME_AR + "," + UPDATED_NAME_AR);

        // Get all the systemServicesList where nameAr equals to UPDATED_NAME_AR
        defaultSystemServicesShouldNotBeFound("nameAr.in=" + UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    public void getAllSystemServicesByNameArIsNullOrNotNull() throws Exception {
        // Initialize the database
        systemServicesRepository.saveAndFlush(systemServices);

        // Get all the systemServicesList where nameAr is not null
        defaultSystemServicesShouldBeFound("nameAr.specified=true");

        // Get all the systemServicesList where nameAr is null
        defaultSystemServicesShouldNotBeFound("nameAr.specified=false");
    }
                @Test
    @Transactional
    public void getAllSystemServicesByNameArContainsSomething() throws Exception {
        // Initialize the database
        systemServicesRepository.saveAndFlush(systemServices);

        // Get all the systemServicesList where nameAr contains DEFAULT_NAME_AR
        defaultSystemServicesShouldBeFound("nameAr.contains=" + DEFAULT_NAME_AR);

        // Get all the systemServicesList where nameAr contains UPDATED_NAME_AR
        defaultSystemServicesShouldNotBeFound("nameAr.contains=" + UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    public void getAllSystemServicesByNameArNotContainsSomething() throws Exception {
        // Initialize the database
        systemServicesRepository.saveAndFlush(systemServices);

        // Get all the systemServicesList where nameAr does not contain DEFAULT_NAME_AR
        defaultSystemServicesShouldNotBeFound("nameAr.doesNotContain=" + DEFAULT_NAME_AR);

        // Get all the systemServicesList where nameAr does not contain UPDATED_NAME_AR
        defaultSystemServicesShouldBeFound("nameAr.doesNotContain=" + UPDATED_NAME_AR);
    }


    @Test
    @Transactional
    public void getAllSystemServicesByNameEnIsEqualToSomething() throws Exception {
        // Initialize the database
        systemServicesRepository.saveAndFlush(systemServices);

        // Get all the systemServicesList where nameEn equals to DEFAULT_NAME_EN
        defaultSystemServicesShouldBeFound("nameEn.equals=" + DEFAULT_NAME_EN);

        // Get all the systemServicesList where nameEn equals to UPDATED_NAME_EN
        defaultSystemServicesShouldNotBeFound("nameEn.equals=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    public void getAllSystemServicesByNameEnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        systemServicesRepository.saveAndFlush(systemServices);

        // Get all the systemServicesList where nameEn not equals to DEFAULT_NAME_EN
        defaultSystemServicesShouldNotBeFound("nameEn.notEquals=" + DEFAULT_NAME_EN);

        // Get all the systemServicesList where nameEn not equals to UPDATED_NAME_EN
        defaultSystemServicesShouldBeFound("nameEn.notEquals=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    public void getAllSystemServicesByNameEnIsInShouldWork() throws Exception {
        // Initialize the database
        systemServicesRepository.saveAndFlush(systemServices);

        // Get all the systemServicesList where nameEn in DEFAULT_NAME_EN or UPDATED_NAME_EN
        defaultSystemServicesShouldBeFound("nameEn.in=" + DEFAULT_NAME_EN + "," + UPDATED_NAME_EN);

        // Get all the systemServicesList where nameEn equals to UPDATED_NAME_EN
        defaultSystemServicesShouldNotBeFound("nameEn.in=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    public void getAllSystemServicesByNameEnIsNullOrNotNull() throws Exception {
        // Initialize the database
        systemServicesRepository.saveAndFlush(systemServices);

        // Get all the systemServicesList where nameEn is not null
        defaultSystemServicesShouldBeFound("nameEn.specified=true");

        // Get all the systemServicesList where nameEn is null
        defaultSystemServicesShouldNotBeFound("nameEn.specified=false");
    }
                @Test
    @Transactional
    public void getAllSystemServicesByNameEnContainsSomething() throws Exception {
        // Initialize the database
        systemServicesRepository.saveAndFlush(systemServices);

        // Get all the systemServicesList where nameEn contains DEFAULT_NAME_EN
        defaultSystemServicesShouldBeFound("nameEn.contains=" + DEFAULT_NAME_EN);

        // Get all the systemServicesList where nameEn contains UPDATED_NAME_EN
        defaultSystemServicesShouldNotBeFound("nameEn.contains=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    public void getAllSystemServicesByNameEnNotContainsSomething() throws Exception {
        // Initialize the database
        systemServicesRepository.saveAndFlush(systemServices);

        // Get all the systemServicesList where nameEn does not contain DEFAULT_NAME_EN
        defaultSystemServicesShouldNotBeFound("nameEn.doesNotContain=" + DEFAULT_NAME_EN);

        // Get all the systemServicesList where nameEn does not contain UPDATED_NAME_EN
        defaultSystemServicesShouldBeFound("nameEn.doesNotContain=" + UPDATED_NAME_EN);
    }


    @Test
    @Transactional
    public void getAllSystemServicesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        systemServicesRepository.saveAndFlush(systemServices);

        // Get all the systemServicesList where code equals to DEFAULT_CODE
        defaultSystemServicesShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the systemServicesList where code equals to UPDATED_CODE
        defaultSystemServicesShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllSystemServicesByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        systemServicesRepository.saveAndFlush(systemServices);

        // Get all the systemServicesList where code not equals to DEFAULT_CODE
        defaultSystemServicesShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the systemServicesList where code not equals to UPDATED_CODE
        defaultSystemServicesShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllSystemServicesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        systemServicesRepository.saveAndFlush(systemServices);

        // Get all the systemServicesList where code in DEFAULT_CODE or UPDATED_CODE
        defaultSystemServicesShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the systemServicesList where code equals to UPDATED_CODE
        defaultSystemServicesShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllSystemServicesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        systemServicesRepository.saveAndFlush(systemServices);

        // Get all the systemServicesList where code is not null
        defaultSystemServicesShouldBeFound("code.specified=true");

        // Get all the systemServicesList where code is null
        defaultSystemServicesShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllSystemServicesByCodeContainsSomething() throws Exception {
        // Initialize the database
        systemServicesRepository.saveAndFlush(systemServices);

        // Get all the systemServicesList where code contains DEFAULT_CODE
        defaultSystemServicesShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the systemServicesList where code contains UPDATED_CODE
        defaultSystemServicesShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllSystemServicesByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        systemServicesRepository.saveAndFlush(systemServices);

        // Get all the systemServicesList where code does not contain DEFAULT_CODE
        defaultSystemServicesShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the systemServicesList where code does not contain UPDATED_CODE
        defaultSystemServicesShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllSystemServicesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        systemServicesRepository.saveAndFlush(systemServices);

        // Get all the systemServicesList where status equals to DEFAULT_STATUS
        defaultSystemServicesShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the systemServicesList where status equals to UPDATED_STATUS
        defaultSystemServicesShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllSystemServicesByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        systemServicesRepository.saveAndFlush(systemServices);

        // Get all the systemServicesList where status not equals to DEFAULT_STATUS
        defaultSystemServicesShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the systemServicesList where status not equals to UPDATED_STATUS
        defaultSystemServicesShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllSystemServicesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        systemServicesRepository.saveAndFlush(systemServices);

        // Get all the systemServicesList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultSystemServicesShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the systemServicesList where status equals to UPDATED_STATUS
        defaultSystemServicesShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllSystemServicesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        systemServicesRepository.saveAndFlush(systemServices);

        // Get all the systemServicesList where status is not null
        defaultSystemServicesShouldBeFound("status.specified=true");

        // Get all the systemServicesList where status is null
        defaultSystemServicesShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllSystemServicesByStatusIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        systemServicesRepository.saveAndFlush(systemServices);

        // Get all the systemServicesList where status is greater than or equal to DEFAULT_STATUS
        defaultSystemServicesShouldBeFound("status.greaterThanOrEqual=" + DEFAULT_STATUS);

        // Get all the systemServicesList where status is greater than or equal to UPDATED_STATUS
        defaultSystemServicesShouldNotBeFound("status.greaterThanOrEqual=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllSystemServicesByStatusIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        systemServicesRepository.saveAndFlush(systemServices);

        // Get all the systemServicesList where status is less than or equal to DEFAULT_STATUS
        defaultSystemServicesShouldBeFound("status.lessThanOrEqual=" + DEFAULT_STATUS);

        // Get all the systemServicesList where status is less than or equal to SMALLER_STATUS
        defaultSystemServicesShouldNotBeFound("status.lessThanOrEqual=" + SMALLER_STATUS);
    }

    @Test
    @Transactional
    public void getAllSystemServicesByStatusIsLessThanSomething() throws Exception {
        // Initialize the database
        systemServicesRepository.saveAndFlush(systemServices);

        // Get all the systemServicesList where status is less than DEFAULT_STATUS
        defaultSystemServicesShouldNotBeFound("status.lessThan=" + DEFAULT_STATUS);

        // Get all the systemServicesList where status is less than UPDATED_STATUS
        defaultSystemServicesShouldBeFound("status.lessThan=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllSystemServicesByStatusIsGreaterThanSomething() throws Exception {
        // Initialize the database
        systemServicesRepository.saveAndFlush(systemServices);

        // Get all the systemServicesList where status is greater than DEFAULT_STATUS
        defaultSystemServicesShouldNotBeFound("status.greaterThan=" + DEFAULT_STATUS);

        // Get all the systemServicesList where status is greater than SMALLER_STATUS
        defaultSystemServicesShouldBeFound("status.greaterThan=" + SMALLER_STATUS);
    }


    @Test
    @Transactional
    public void getAllSystemServicesBySystemServicesMessagesIsEqualToSomething() throws Exception {
        // Initialize the database
        systemServicesRepository.saveAndFlush(systemServices);
        Messages systemServicesMessages = MessagesResourceIT.createEntity(em);
        em.persist(systemServicesMessages);
        em.flush();
        systemServices.addSystemServicesMessages(systemServicesMessages);
        systemServicesRepository.saveAndFlush(systemServices);
        Long systemServicesMessagesId = systemServicesMessages.getId();

        // Get all the systemServicesList where systemServicesMessages equals to systemServicesMessagesId
        defaultSystemServicesShouldBeFound("systemServicesMessagesId.equals=" + systemServicesMessagesId);

        // Get all the systemServicesList where systemServicesMessages equals to systemServicesMessagesId + 1
        defaultSystemServicesShouldNotBeFound("systemServicesMessagesId.equals=" + (systemServicesMessagesId + 1));
    }


    @Test
    @Transactional
    public void getAllSystemServicesBySystemIsEqualToSomething() throws Exception {
        // Initialize the database
        systemServicesRepository.saveAndFlush(systemServices);
        System system = SystemResourceIT.createEntity(em);
        em.persist(system);
        em.flush();
        systemServices.setSystem(system);
        systemServicesRepository.saveAndFlush(systemServices);
        Long systemId = system.getId();

        // Get all the systemServicesList where system equals to systemId
        defaultSystemServicesShouldBeFound("systemId.equals=" + systemId);

        // Get all the systemServicesList where system equals to systemId + 1
        defaultSystemServicesShouldNotBeFound("systemId.equals=" + (systemId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSystemServicesShouldBeFound(String filter) throws Exception {
        restSystemServicesMockMvc.perform(get("/api/system-services?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(systemServices.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameAr").value(hasItem(DEFAULT_NAME_AR)))
            .andExpect(jsonPath("$.[*].nameEn").value(hasItem(DEFAULT_NAME_EN)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));

        // Check, that the count call also returns 1
        restSystemServicesMockMvc.perform(get("/api/system-services/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSystemServicesShouldNotBeFound(String filter) throws Exception {
        restSystemServicesMockMvc.perform(get("/api/system-services?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSystemServicesMockMvc.perform(get("/api/system-services/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingSystemServices() throws Exception {
        // Get the systemServices
        restSystemServicesMockMvc.perform(get("/api/system-services/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSystemServices() throws Exception {
        // Initialize the database
        systemServicesRepository.saveAndFlush(systemServices);

        int databaseSizeBeforeUpdate = systemServicesRepository.findAll().size();

        // Update the systemServices
        SystemServices updatedSystemServices = systemServicesRepository.findById(systemServices.getId()).get();
        // Disconnect from session so that the updates on updatedSystemServices are not directly saved in db
        em.detach(updatedSystemServices);
        updatedSystemServices
            .nameAr(UPDATED_NAME_AR)
            .nameEn(UPDATED_NAME_EN)
            .code(UPDATED_CODE)
            .status(UPDATED_STATUS);
        SystemServicesDTO systemServicesDTO = systemServicesMapper.toDto(updatedSystemServices);

        restSystemServicesMockMvc.perform(put("/api/system-services")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(systemServicesDTO)))
            .andExpect(status().isOk());

        // Validate the SystemServices in the database
        List<SystemServices> systemServicesList = systemServicesRepository.findAll();
        assertThat(systemServicesList).hasSize(databaseSizeBeforeUpdate);
        SystemServices testSystemServices = systemServicesList.get(systemServicesList.size() - 1);
        assertThat(testSystemServices.getNameAr()).isEqualTo(UPDATED_NAME_AR);
        assertThat(testSystemServices.getNameEn()).isEqualTo(UPDATED_NAME_EN);
        assertThat(testSystemServices.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSystemServices.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingSystemServices() throws Exception {
        int databaseSizeBeforeUpdate = systemServicesRepository.findAll().size();

        // Create the SystemServices
        SystemServicesDTO systemServicesDTO = systemServicesMapper.toDto(systemServices);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSystemServicesMockMvc.perform(put("/api/system-services")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(systemServicesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SystemServices in the database
        List<SystemServices> systemServicesList = systemServicesRepository.findAll();
        assertThat(systemServicesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSystemServices() throws Exception {
        // Initialize the database
        systemServicesRepository.saveAndFlush(systemServices);

        int databaseSizeBeforeDelete = systemServicesRepository.findAll().size();

        // Delete the systemServices
        restSystemServicesMockMvc.perform(delete("/api/system-services/{id}", systemServices.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SystemServices> systemServicesList = systemServicesRepository.findAll();
        assertThat(systemServicesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
