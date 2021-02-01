package com.isoft.emotion.web.rest;

import com.isoft.emotion.EmotionSurveyApp;
import com.isoft.emotion.domain.Users;
import com.isoft.emotion.domain.Messages;
import com.isoft.emotion.repository.UsersRepository;
import com.isoft.emotion.service.UsersService;
import com.isoft.emotion.service.dto.UsersDTO;
import com.isoft.emotion.service.mapper.UsersMapper;
import com.isoft.emotion.service.dto.UsersCriteria;
import com.isoft.emotion.service.UsersQueryService;

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
 * Integration tests for the {@link UsersResource} REST controller.
 */
@SpringBootTest(classes = EmotionSurveyApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class UsersResourceIT {

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
    private UsersRepository usersRepository;

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private UsersService usersService;

    @Autowired
    private UsersQueryService usersQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUsersMockMvc;

    private Users users;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Users createEntity(EntityManager em) {
        Users users = new Users()
            .nameAr(DEFAULT_NAME_AR)
            .nameEn(DEFAULT_NAME_EN)
            .code(DEFAULT_CODE)
            .status(DEFAULT_STATUS);
        return users;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Users createUpdatedEntity(EntityManager em) {
        Users users = new Users()
            .nameAr(UPDATED_NAME_AR)
            .nameEn(UPDATED_NAME_EN)
            .code(UPDATED_CODE)
            .status(UPDATED_STATUS);
        return users;
    }

    @BeforeEach
    public void initTest() {
        users = createEntity(em);
    }

    @Test
    @Transactional
    public void createUsers() throws Exception {
        int databaseSizeBeforeCreate = usersRepository.findAll().size();
        // Create the Users
        UsersDTO usersDTO = usersMapper.toDto(users);
        restUsersMockMvc.perform(post("/api/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usersDTO)))
            .andExpect(status().isCreated());

        // Validate the Users in the database
        List<Users> usersList = usersRepository.findAll();
        assertThat(usersList).hasSize(databaseSizeBeforeCreate + 1);
        Users testUsers = usersList.get(usersList.size() - 1);
        assertThat(testUsers.getNameAr()).isEqualTo(DEFAULT_NAME_AR);
        assertThat(testUsers.getNameEn()).isEqualTo(DEFAULT_NAME_EN);
        assertThat(testUsers.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testUsers.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createUsersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = usersRepository.findAll().size();

        // Create the Users with an existing ID
        users.setId(1L);
        UsersDTO usersDTO = usersMapper.toDto(users);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUsersMockMvc.perform(post("/api/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Users in the database
        List<Users> usersList = usersRepository.findAll();
        assertThat(usersList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUsers() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList
        restUsersMockMvc.perform(get("/api/users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(users.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameAr").value(hasItem(DEFAULT_NAME_AR)))
            .andExpect(jsonPath("$.[*].nameEn").value(hasItem(DEFAULT_NAME_EN)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }
    
    @Test
    @Transactional
    public void getUsers() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get the users
        restUsersMockMvc.perform(get("/api/users/{id}", users.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(users.getId().intValue()))
            .andExpect(jsonPath("$.nameAr").value(DEFAULT_NAME_AR))
            .andExpect(jsonPath("$.nameEn").value(DEFAULT_NAME_EN))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }


    @Test
    @Transactional
    public void getUsersByIdFiltering() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        Long id = users.getId();

        defaultUsersShouldBeFound("id.equals=" + id);
        defaultUsersShouldNotBeFound("id.notEquals=" + id);

        defaultUsersShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultUsersShouldNotBeFound("id.greaterThan=" + id);

        defaultUsersShouldBeFound("id.lessThanOrEqual=" + id);
        defaultUsersShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllUsersByNameArIsEqualToSomething() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where nameAr equals to DEFAULT_NAME_AR
        defaultUsersShouldBeFound("nameAr.equals=" + DEFAULT_NAME_AR);

        // Get all the usersList where nameAr equals to UPDATED_NAME_AR
        defaultUsersShouldNotBeFound("nameAr.equals=" + UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    public void getAllUsersByNameArIsNotEqualToSomething() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where nameAr not equals to DEFAULT_NAME_AR
        defaultUsersShouldNotBeFound("nameAr.notEquals=" + DEFAULT_NAME_AR);

        // Get all the usersList where nameAr not equals to UPDATED_NAME_AR
        defaultUsersShouldBeFound("nameAr.notEquals=" + UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    public void getAllUsersByNameArIsInShouldWork() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where nameAr in DEFAULT_NAME_AR or UPDATED_NAME_AR
        defaultUsersShouldBeFound("nameAr.in=" + DEFAULT_NAME_AR + "," + UPDATED_NAME_AR);

        // Get all the usersList where nameAr equals to UPDATED_NAME_AR
        defaultUsersShouldNotBeFound("nameAr.in=" + UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    public void getAllUsersByNameArIsNullOrNotNull() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where nameAr is not null
        defaultUsersShouldBeFound("nameAr.specified=true");

        // Get all the usersList where nameAr is null
        defaultUsersShouldNotBeFound("nameAr.specified=false");
    }
                @Test
    @Transactional
    public void getAllUsersByNameArContainsSomething() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where nameAr contains DEFAULT_NAME_AR
        defaultUsersShouldBeFound("nameAr.contains=" + DEFAULT_NAME_AR);

        // Get all the usersList where nameAr contains UPDATED_NAME_AR
        defaultUsersShouldNotBeFound("nameAr.contains=" + UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    public void getAllUsersByNameArNotContainsSomething() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where nameAr does not contain DEFAULT_NAME_AR
        defaultUsersShouldNotBeFound("nameAr.doesNotContain=" + DEFAULT_NAME_AR);

        // Get all the usersList where nameAr does not contain UPDATED_NAME_AR
        defaultUsersShouldBeFound("nameAr.doesNotContain=" + UPDATED_NAME_AR);
    }


    @Test
    @Transactional
    public void getAllUsersByNameEnIsEqualToSomething() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where nameEn equals to DEFAULT_NAME_EN
        defaultUsersShouldBeFound("nameEn.equals=" + DEFAULT_NAME_EN);

        // Get all the usersList where nameEn equals to UPDATED_NAME_EN
        defaultUsersShouldNotBeFound("nameEn.equals=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    public void getAllUsersByNameEnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where nameEn not equals to DEFAULT_NAME_EN
        defaultUsersShouldNotBeFound("nameEn.notEquals=" + DEFAULT_NAME_EN);

        // Get all the usersList where nameEn not equals to UPDATED_NAME_EN
        defaultUsersShouldBeFound("nameEn.notEquals=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    public void getAllUsersByNameEnIsInShouldWork() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where nameEn in DEFAULT_NAME_EN or UPDATED_NAME_EN
        defaultUsersShouldBeFound("nameEn.in=" + DEFAULT_NAME_EN + "," + UPDATED_NAME_EN);

        // Get all the usersList where nameEn equals to UPDATED_NAME_EN
        defaultUsersShouldNotBeFound("nameEn.in=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    public void getAllUsersByNameEnIsNullOrNotNull() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where nameEn is not null
        defaultUsersShouldBeFound("nameEn.specified=true");

        // Get all the usersList where nameEn is null
        defaultUsersShouldNotBeFound("nameEn.specified=false");
    }
                @Test
    @Transactional
    public void getAllUsersByNameEnContainsSomething() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where nameEn contains DEFAULT_NAME_EN
        defaultUsersShouldBeFound("nameEn.contains=" + DEFAULT_NAME_EN);

        // Get all the usersList where nameEn contains UPDATED_NAME_EN
        defaultUsersShouldNotBeFound("nameEn.contains=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    public void getAllUsersByNameEnNotContainsSomething() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where nameEn does not contain DEFAULT_NAME_EN
        defaultUsersShouldNotBeFound("nameEn.doesNotContain=" + DEFAULT_NAME_EN);

        // Get all the usersList where nameEn does not contain UPDATED_NAME_EN
        defaultUsersShouldBeFound("nameEn.doesNotContain=" + UPDATED_NAME_EN);
    }


    @Test
    @Transactional
    public void getAllUsersByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where code equals to DEFAULT_CODE
        defaultUsersShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the usersList where code equals to UPDATED_CODE
        defaultUsersShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllUsersByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where code not equals to DEFAULT_CODE
        defaultUsersShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the usersList where code not equals to UPDATED_CODE
        defaultUsersShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllUsersByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where code in DEFAULT_CODE or UPDATED_CODE
        defaultUsersShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the usersList where code equals to UPDATED_CODE
        defaultUsersShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllUsersByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where code is not null
        defaultUsersShouldBeFound("code.specified=true");

        // Get all the usersList where code is null
        defaultUsersShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllUsersByCodeContainsSomething() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where code contains DEFAULT_CODE
        defaultUsersShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the usersList where code contains UPDATED_CODE
        defaultUsersShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllUsersByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where code does not contain DEFAULT_CODE
        defaultUsersShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the usersList where code does not contain UPDATED_CODE
        defaultUsersShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllUsersByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where status equals to DEFAULT_STATUS
        defaultUsersShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the usersList where status equals to UPDATED_STATUS
        defaultUsersShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllUsersByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where status not equals to DEFAULT_STATUS
        defaultUsersShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the usersList where status not equals to UPDATED_STATUS
        defaultUsersShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllUsersByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultUsersShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the usersList where status equals to UPDATED_STATUS
        defaultUsersShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllUsersByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where status is not null
        defaultUsersShouldBeFound("status.specified=true");

        // Get all the usersList where status is null
        defaultUsersShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllUsersByStatusIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where status is greater than or equal to DEFAULT_STATUS
        defaultUsersShouldBeFound("status.greaterThanOrEqual=" + DEFAULT_STATUS);

        // Get all the usersList where status is greater than or equal to UPDATED_STATUS
        defaultUsersShouldNotBeFound("status.greaterThanOrEqual=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllUsersByStatusIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where status is less than or equal to DEFAULT_STATUS
        defaultUsersShouldBeFound("status.lessThanOrEqual=" + DEFAULT_STATUS);

        // Get all the usersList where status is less than or equal to SMALLER_STATUS
        defaultUsersShouldNotBeFound("status.lessThanOrEqual=" + SMALLER_STATUS);
    }

    @Test
    @Transactional
    public void getAllUsersByStatusIsLessThanSomething() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where status is less than DEFAULT_STATUS
        defaultUsersShouldNotBeFound("status.lessThan=" + DEFAULT_STATUS);

        // Get all the usersList where status is less than UPDATED_STATUS
        defaultUsersShouldBeFound("status.lessThan=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllUsersByStatusIsGreaterThanSomething() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where status is greater than DEFAULT_STATUS
        defaultUsersShouldNotBeFound("status.greaterThan=" + DEFAULT_STATUS);

        // Get all the usersList where status is greater than SMALLER_STATUS
        defaultUsersShouldBeFound("status.greaterThan=" + SMALLER_STATUS);
    }


    @Test
    @Transactional
    public void getAllUsersByUsersMessagesIsEqualToSomething() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);
        Messages usersMessages = MessagesResourceIT.createEntity(em);
        em.persist(usersMessages);
        em.flush();
        users.addUsersMessages(usersMessages);
        usersRepository.saveAndFlush(users);
        Long usersMessagesId = usersMessages.getId();

        // Get all the usersList where usersMessages equals to usersMessagesId
        defaultUsersShouldBeFound("usersMessagesId.equals=" + usersMessagesId);

        // Get all the usersList where usersMessages equals to usersMessagesId + 1
        defaultUsersShouldNotBeFound("usersMessagesId.equals=" + (usersMessagesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUsersShouldBeFound(String filter) throws Exception {
        restUsersMockMvc.perform(get("/api/users?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(users.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameAr").value(hasItem(DEFAULT_NAME_AR)))
            .andExpect(jsonPath("$.[*].nameEn").value(hasItem(DEFAULT_NAME_EN)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));

        // Check, that the count call also returns 1
        restUsersMockMvc.perform(get("/api/users/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUsersShouldNotBeFound(String filter) throws Exception {
        restUsersMockMvc.perform(get("/api/users?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUsersMockMvc.perform(get("/api/users/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingUsers() throws Exception {
        // Get the users
        restUsersMockMvc.perform(get("/api/users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUsers() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        int databaseSizeBeforeUpdate = usersRepository.findAll().size();

        // Update the users
        Users updatedUsers = usersRepository.findById(users.getId()).get();
        // Disconnect from session so that the updates on updatedUsers are not directly saved in db
        em.detach(updatedUsers);
        updatedUsers
            .nameAr(UPDATED_NAME_AR)
            .nameEn(UPDATED_NAME_EN)
            .code(UPDATED_CODE)
            .status(UPDATED_STATUS);
        UsersDTO usersDTO = usersMapper.toDto(updatedUsers);

        restUsersMockMvc.perform(put("/api/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usersDTO)))
            .andExpect(status().isOk());

        // Validate the Users in the database
        List<Users> usersList = usersRepository.findAll();
        assertThat(usersList).hasSize(databaseSizeBeforeUpdate);
        Users testUsers = usersList.get(usersList.size() - 1);
        assertThat(testUsers.getNameAr()).isEqualTo(UPDATED_NAME_AR);
        assertThat(testUsers.getNameEn()).isEqualTo(UPDATED_NAME_EN);
        assertThat(testUsers.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testUsers.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingUsers() throws Exception {
        int databaseSizeBeforeUpdate = usersRepository.findAll().size();

        // Create the Users
        UsersDTO usersDTO = usersMapper.toDto(users);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUsersMockMvc.perform(put("/api/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Users in the database
        List<Users> usersList = usersRepository.findAll();
        assertThat(usersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUsers() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        int databaseSizeBeforeDelete = usersRepository.findAll().size();

        // Delete the users
        restUsersMockMvc.perform(delete("/api/users/{id}", users.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Users> usersList = usersRepository.findAll();
        assertThat(usersList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
