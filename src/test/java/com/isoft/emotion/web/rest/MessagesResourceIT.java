package com.isoft.emotion.web.rest;

import com.isoft.emotion.EmotionSurveyApp;
import com.isoft.emotion.domain.Messages;
import com.isoft.emotion.domain.Center;
import com.isoft.emotion.domain.System;
import com.isoft.emotion.domain.SystemServices;
import com.isoft.emotion.domain.Users;
import com.isoft.emotion.repository.MessagesRepository;
import com.isoft.emotion.service.MessagesService;
import com.isoft.emotion.service.dto.MessagesDTO;
import com.isoft.emotion.service.mapper.MessagesMapper;
import com.isoft.emotion.service.dto.MessagesCriteria;
import com.isoft.emotion.service.MessagesQueryService;

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
 * Integration tests for the {@link MessagesResource} REST controller.
 */
@SpringBootTest(classes = EmotionSurveyApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class MessagesResourceIT {

    private static final Long DEFAULT_COUNTER = 1L;
    private static final Long UPDATED_COUNTER = 2L;
    private static final Long SMALLER_COUNTER = 1L - 1L;

    private static final Long DEFAULT_TRS_ID = 1L;
    private static final Long UPDATED_TRS_ID = 2L;
    private static final Long SMALLER_TRS_ID = 1L - 1L;

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;
    private static final Long SMALLER_USER_ID = 1L - 1L;

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;
    private static final Integer SMALLER_STATUS = 1 - 1;

    private static final String DEFAULT_APPLICANT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_APPLICANT_NAME = "BBBBBBBBBB";

    @Autowired
    private MessagesRepository messagesRepository;

    @Autowired
    private MessagesMapper messagesMapper;

    @Autowired
    private MessagesService messagesService;

    @Autowired
    private MessagesQueryService messagesQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMessagesMockMvc;

    private Messages messages;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Messages createEntity(EntityManager em) {
        Messages messages = new Messages()
            .counter(DEFAULT_COUNTER)
            .trsId(DEFAULT_TRS_ID)
            .userId(DEFAULT_USER_ID)
            .message(DEFAULT_MESSAGE)
            .status(DEFAULT_STATUS)
            .applicantName(DEFAULT_APPLICANT_NAME);
        return messages;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Messages createUpdatedEntity(EntityManager em) {
        Messages messages = new Messages()
            .counter(UPDATED_COUNTER)
            .trsId(UPDATED_TRS_ID)
            .userId(UPDATED_USER_ID)
            .message(UPDATED_MESSAGE)
            .status(UPDATED_STATUS)
            .applicantName(UPDATED_APPLICANT_NAME);
        return messages;
    }

    @BeforeEach
    public void initTest() {
        messages = createEntity(em);
    }

    @Test
    @Transactional
    public void createMessages() throws Exception {
        int databaseSizeBeforeCreate = messagesRepository.findAll().size();
        // Create the Messages
        MessagesDTO messagesDTO = messagesMapper.toDto(messages);
        restMessagesMockMvc.perform(post("/api/messages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(messagesDTO)))
            .andExpect(status().isCreated());

        // Validate the Messages in the database
        List<Messages> messagesList = messagesRepository.findAll();
        assertThat(messagesList).hasSize(databaseSizeBeforeCreate + 1);
        Messages testMessages = messagesList.get(messagesList.size() - 1);
        assertThat(testMessages.getCounter()).isEqualTo(DEFAULT_COUNTER);
        assertThat(testMessages.getTrsId()).isEqualTo(DEFAULT_TRS_ID);
        assertThat(testMessages.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testMessages.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testMessages.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMessages.getApplicantName()).isEqualTo(DEFAULT_APPLICANT_NAME);
    }

    @Test
    @Transactional
    public void createMessagesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = messagesRepository.findAll().size();

        // Create the Messages with an existing ID
        messages.setId(1L);
        MessagesDTO messagesDTO = messagesMapper.toDto(messages);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMessagesMockMvc.perform(post("/api/messages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(messagesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Messages in the database
        List<Messages> messagesList = messagesRepository.findAll();
        assertThat(messagesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMessages() throws Exception {
        // Initialize the database
        messagesRepository.saveAndFlush(messages);

        // Get all the messagesList
        restMessagesMockMvc.perform(get("/api/messages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(messages.getId().intValue())))
            .andExpect(jsonPath("$.[*].counter").value(hasItem(DEFAULT_COUNTER.intValue())))
            .andExpect(jsonPath("$.[*].trsId").value(hasItem(DEFAULT_TRS_ID.intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].applicantName").value(hasItem(DEFAULT_APPLICANT_NAME)));
    }
    
    @Test
    @Transactional
    public void getMessages() throws Exception {
        // Initialize the database
        messagesRepository.saveAndFlush(messages);

        // Get the messages
        restMessagesMockMvc.perform(get("/api/messages/{id}", messages.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(messages.getId().intValue()))
            .andExpect(jsonPath("$.counter").value(DEFAULT_COUNTER.intValue()))
            .andExpect(jsonPath("$.trsId").value(DEFAULT_TRS_ID.intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.applicantName").value(DEFAULT_APPLICANT_NAME));
    }


    @Test
    @Transactional
    public void getMessagesByIdFiltering() throws Exception {
        // Initialize the database
        messagesRepository.saveAndFlush(messages);

        Long id = messages.getId();

        defaultMessagesShouldBeFound("id.equals=" + id);
        defaultMessagesShouldNotBeFound("id.notEquals=" + id);

        defaultMessagesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMessagesShouldNotBeFound("id.greaterThan=" + id);

        defaultMessagesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMessagesShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMessagesByCounterIsEqualToSomething() throws Exception {
        // Initialize the database
        messagesRepository.saveAndFlush(messages);

        // Get all the messagesList where counter equals to DEFAULT_COUNTER
        defaultMessagesShouldBeFound("counter.equals=" + DEFAULT_COUNTER);

        // Get all the messagesList where counter equals to UPDATED_COUNTER
        defaultMessagesShouldNotBeFound("counter.equals=" + UPDATED_COUNTER);
    }

    @Test
    @Transactional
    public void getAllMessagesByCounterIsNotEqualToSomething() throws Exception {
        // Initialize the database
        messagesRepository.saveAndFlush(messages);

        // Get all the messagesList where counter not equals to DEFAULT_COUNTER
        defaultMessagesShouldNotBeFound("counter.notEquals=" + DEFAULT_COUNTER);

        // Get all the messagesList where counter not equals to UPDATED_COUNTER
        defaultMessagesShouldBeFound("counter.notEquals=" + UPDATED_COUNTER);
    }

    @Test
    @Transactional
    public void getAllMessagesByCounterIsInShouldWork() throws Exception {
        // Initialize the database
        messagesRepository.saveAndFlush(messages);

        // Get all the messagesList where counter in DEFAULT_COUNTER or UPDATED_COUNTER
        defaultMessagesShouldBeFound("counter.in=" + DEFAULT_COUNTER + "," + UPDATED_COUNTER);

        // Get all the messagesList where counter equals to UPDATED_COUNTER
        defaultMessagesShouldNotBeFound("counter.in=" + UPDATED_COUNTER);
    }

    @Test
    @Transactional
    public void getAllMessagesByCounterIsNullOrNotNull() throws Exception {
        // Initialize the database
        messagesRepository.saveAndFlush(messages);

        // Get all the messagesList where counter is not null
        defaultMessagesShouldBeFound("counter.specified=true");

        // Get all the messagesList where counter is null
        defaultMessagesShouldNotBeFound("counter.specified=false");
    }

    @Test
    @Transactional
    public void getAllMessagesByCounterIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        messagesRepository.saveAndFlush(messages);

        // Get all the messagesList where counter is greater than or equal to DEFAULT_COUNTER
        defaultMessagesShouldBeFound("counter.greaterThanOrEqual=" + DEFAULT_COUNTER);

        // Get all the messagesList where counter is greater than or equal to UPDATED_COUNTER
        defaultMessagesShouldNotBeFound("counter.greaterThanOrEqual=" + UPDATED_COUNTER);
    }

    @Test
    @Transactional
    public void getAllMessagesByCounterIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        messagesRepository.saveAndFlush(messages);

        // Get all the messagesList where counter is less than or equal to DEFAULT_COUNTER
        defaultMessagesShouldBeFound("counter.lessThanOrEqual=" + DEFAULT_COUNTER);

        // Get all the messagesList where counter is less than or equal to SMALLER_COUNTER
        defaultMessagesShouldNotBeFound("counter.lessThanOrEqual=" + SMALLER_COUNTER);
    }

    @Test
    @Transactional
    public void getAllMessagesByCounterIsLessThanSomething() throws Exception {
        // Initialize the database
        messagesRepository.saveAndFlush(messages);

        // Get all the messagesList where counter is less than DEFAULT_COUNTER
        defaultMessagesShouldNotBeFound("counter.lessThan=" + DEFAULT_COUNTER);

        // Get all the messagesList where counter is less than UPDATED_COUNTER
        defaultMessagesShouldBeFound("counter.lessThan=" + UPDATED_COUNTER);
    }

    @Test
    @Transactional
    public void getAllMessagesByCounterIsGreaterThanSomething() throws Exception {
        // Initialize the database
        messagesRepository.saveAndFlush(messages);

        // Get all the messagesList where counter is greater than DEFAULT_COUNTER
        defaultMessagesShouldNotBeFound("counter.greaterThan=" + DEFAULT_COUNTER);

        // Get all the messagesList where counter is greater than SMALLER_COUNTER
        defaultMessagesShouldBeFound("counter.greaterThan=" + SMALLER_COUNTER);
    }


    @Test
    @Transactional
    public void getAllMessagesByTrsIdIsEqualToSomething() throws Exception {
        // Initialize the database
        messagesRepository.saveAndFlush(messages);

        // Get all the messagesList where trsId equals to DEFAULT_TRS_ID
        defaultMessagesShouldBeFound("trsId.equals=" + DEFAULT_TRS_ID);

        // Get all the messagesList where trsId equals to UPDATED_TRS_ID
        defaultMessagesShouldNotBeFound("trsId.equals=" + UPDATED_TRS_ID);
    }

    @Test
    @Transactional
    public void getAllMessagesByTrsIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        messagesRepository.saveAndFlush(messages);

        // Get all the messagesList where trsId not equals to DEFAULT_TRS_ID
        defaultMessagesShouldNotBeFound("trsId.notEquals=" + DEFAULT_TRS_ID);

        // Get all the messagesList where trsId not equals to UPDATED_TRS_ID
        defaultMessagesShouldBeFound("trsId.notEquals=" + UPDATED_TRS_ID);
    }

    @Test
    @Transactional
    public void getAllMessagesByTrsIdIsInShouldWork() throws Exception {
        // Initialize the database
        messagesRepository.saveAndFlush(messages);

        // Get all the messagesList where trsId in DEFAULT_TRS_ID or UPDATED_TRS_ID
        defaultMessagesShouldBeFound("trsId.in=" + DEFAULT_TRS_ID + "," + UPDATED_TRS_ID);

        // Get all the messagesList where trsId equals to UPDATED_TRS_ID
        defaultMessagesShouldNotBeFound("trsId.in=" + UPDATED_TRS_ID);
    }

    @Test
    @Transactional
    public void getAllMessagesByTrsIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        messagesRepository.saveAndFlush(messages);

        // Get all the messagesList where trsId is not null
        defaultMessagesShouldBeFound("trsId.specified=true");

        // Get all the messagesList where trsId is null
        defaultMessagesShouldNotBeFound("trsId.specified=false");
    }

    @Test
    @Transactional
    public void getAllMessagesByTrsIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        messagesRepository.saveAndFlush(messages);

        // Get all the messagesList where trsId is greater than or equal to DEFAULT_TRS_ID
        defaultMessagesShouldBeFound("trsId.greaterThanOrEqual=" + DEFAULT_TRS_ID);

        // Get all the messagesList where trsId is greater than or equal to UPDATED_TRS_ID
        defaultMessagesShouldNotBeFound("trsId.greaterThanOrEqual=" + UPDATED_TRS_ID);
    }

    @Test
    @Transactional
    public void getAllMessagesByTrsIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        messagesRepository.saveAndFlush(messages);

        // Get all the messagesList where trsId is less than or equal to DEFAULT_TRS_ID
        defaultMessagesShouldBeFound("trsId.lessThanOrEqual=" + DEFAULT_TRS_ID);

        // Get all the messagesList where trsId is less than or equal to SMALLER_TRS_ID
        defaultMessagesShouldNotBeFound("trsId.lessThanOrEqual=" + SMALLER_TRS_ID);
    }

    @Test
    @Transactional
    public void getAllMessagesByTrsIdIsLessThanSomething() throws Exception {
        // Initialize the database
        messagesRepository.saveAndFlush(messages);

        // Get all the messagesList where trsId is less than DEFAULT_TRS_ID
        defaultMessagesShouldNotBeFound("trsId.lessThan=" + DEFAULT_TRS_ID);

        // Get all the messagesList where trsId is less than UPDATED_TRS_ID
        defaultMessagesShouldBeFound("trsId.lessThan=" + UPDATED_TRS_ID);
    }

    @Test
    @Transactional
    public void getAllMessagesByTrsIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        messagesRepository.saveAndFlush(messages);

        // Get all the messagesList where trsId is greater than DEFAULT_TRS_ID
        defaultMessagesShouldNotBeFound("trsId.greaterThan=" + DEFAULT_TRS_ID);

        // Get all the messagesList where trsId is greater than SMALLER_TRS_ID
        defaultMessagesShouldBeFound("trsId.greaterThan=" + SMALLER_TRS_ID);
    }


    @Test
    @Transactional
    public void getAllMessagesByUserIdIsEqualToSomething() throws Exception {
        // Initialize the database
        messagesRepository.saveAndFlush(messages);

        // Get all the messagesList where userId equals to DEFAULT_USER_ID
        defaultMessagesShouldBeFound("userId.equals=" + DEFAULT_USER_ID);

        // Get all the messagesList where userId equals to UPDATED_USER_ID
        defaultMessagesShouldNotBeFound("userId.equals=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllMessagesByUserIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        messagesRepository.saveAndFlush(messages);

        // Get all the messagesList where userId not equals to DEFAULT_USER_ID
        defaultMessagesShouldNotBeFound("userId.notEquals=" + DEFAULT_USER_ID);

        // Get all the messagesList where userId not equals to UPDATED_USER_ID
        defaultMessagesShouldBeFound("userId.notEquals=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllMessagesByUserIdIsInShouldWork() throws Exception {
        // Initialize the database
        messagesRepository.saveAndFlush(messages);

        // Get all the messagesList where userId in DEFAULT_USER_ID or UPDATED_USER_ID
        defaultMessagesShouldBeFound("userId.in=" + DEFAULT_USER_ID + "," + UPDATED_USER_ID);

        // Get all the messagesList where userId equals to UPDATED_USER_ID
        defaultMessagesShouldNotBeFound("userId.in=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllMessagesByUserIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        messagesRepository.saveAndFlush(messages);

        // Get all the messagesList where userId is not null
        defaultMessagesShouldBeFound("userId.specified=true");

        // Get all the messagesList where userId is null
        defaultMessagesShouldNotBeFound("userId.specified=false");
    }

    @Test
    @Transactional
    public void getAllMessagesByUserIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        messagesRepository.saveAndFlush(messages);

        // Get all the messagesList where userId is greater than or equal to DEFAULT_USER_ID
        defaultMessagesShouldBeFound("userId.greaterThanOrEqual=" + DEFAULT_USER_ID);

        // Get all the messagesList where userId is greater than or equal to UPDATED_USER_ID
        defaultMessagesShouldNotBeFound("userId.greaterThanOrEqual=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllMessagesByUserIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        messagesRepository.saveAndFlush(messages);

        // Get all the messagesList where userId is less than or equal to DEFAULT_USER_ID
        defaultMessagesShouldBeFound("userId.lessThanOrEqual=" + DEFAULT_USER_ID);

        // Get all the messagesList where userId is less than or equal to SMALLER_USER_ID
        defaultMessagesShouldNotBeFound("userId.lessThanOrEqual=" + SMALLER_USER_ID);
    }

    @Test
    @Transactional
    public void getAllMessagesByUserIdIsLessThanSomething() throws Exception {
        // Initialize the database
        messagesRepository.saveAndFlush(messages);

        // Get all the messagesList where userId is less than DEFAULT_USER_ID
        defaultMessagesShouldNotBeFound("userId.lessThan=" + DEFAULT_USER_ID);

        // Get all the messagesList where userId is less than UPDATED_USER_ID
        defaultMessagesShouldBeFound("userId.lessThan=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllMessagesByUserIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        messagesRepository.saveAndFlush(messages);

        // Get all the messagesList where userId is greater than DEFAULT_USER_ID
        defaultMessagesShouldNotBeFound("userId.greaterThan=" + DEFAULT_USER_ID);

        // Get all the messagesList where userId is greater than SMALLER_USER_ID
        defaultMessagesShouldBeFound("userId.greaterThan=" + SMALLER_USER_ID);
    }


    @Test
    @Transactional
    public void getAllMessagesByMessageIsEqualToSomething() throws Exception {
        // Initialize the database
        messagesRepository.saveAndFlush(messages);

        // Get all the messagesList where message equals to DEFAULT_MESSAGE
        defaultMessagesShouldBeFound("message.equals=" + DEFAULT_MESSAGE);

        // Get all the messagesList where message equals to UPDATED_MESSAGE
        defaultMessagesShouldNotBeFound("message.equals=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    public void getAllMessagesByMessageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        messagesRepository.saveAndFlush(messages);

        // Get all the messagesList where message not equals to DEFAULT_MESSAGE
        defaultMessagesShouldNotBeFound("message.notEquals=" + DEFAULT_MESSAGE);

        // Get all the messagesList where message not equals to UPDATED_MESSAGE
        defaultMessagesShouldBeFound("message.notEquals=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    public void getAllMessagesByMessageIsInShouldWork() throws Exception {
        // Initialize the database
        messagesRepository.saveAndFlush(messages);

        // Get all the messagesList where message in DEFAULT_MESSAGE or UPDATED_MESSAGE
        defaultMessagesShouldBeFound("message.in=" + DEFAULT_MESSAGE + "," + UPDATED_MESSAGE);

        // Get all the messagesList where message equals to UPDATED_MESSAGE
        defaultMessagesShouldNotBeFound("message.in=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    public void getAllMessagesByMessageIsNullOrNotNull() throws Exception {
        // Initialize the database
        messagesRepository.saveAndFlush(messages);

        // Get all the messagesList where message is not null
        defaultMessagesShouldBeFound("message.specified=true");

        // Get all the messagesList where message is null
        defaultMessagesShouldNotBeFound("message.specified=false");
    }
                @Test
    @Transactional
    public void getAllMessagesByMessageContainsSomething() throws Exception {
        // Initialize the database
        messagesRepository.saveAndFlush(messages);

        // Get all the messagesList where message contains DEFAULT_MESSAGE
        defaultMessagesShouldBeFound("message.contains=" + DEFAULT_MESSAGE);

        // Get all the messagesList where message contains UPDATED_MESSAGE
        defaultMessagesShouldNotBeFound("message.contains=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    public void getAllMessagesByMessageNotContainsSomething() throws Exception {
        // Initialize the database
        messagesRepository.saveAndFlush(messages);

        // Get all the messagesList where message does not contain DEFAULT_MESSAGE
        defaultMessagesShouldNotBeFound("message.doesNotContain=" + DEFAULT_MESSAGE);

        // Get all the messagesList where message does not contain UPDATED_MESSAGE
        defaultMessagesShouldBeFound("message.doesNotContain=" + UPDATED_MESSAGE);
    }


    @Test
    @Transactional
    public void getAllMessagesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        messagesRepository.saveAndFlush(messages);

        // Get all the messagesList where status equals to DEFAULT_STATUS
        defaultMessagesShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the messagesList where status equals to UPDATED_STATUS
        defaultMessagesShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllMessagesByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        messagesRepository.saveAndFlush(messages);

        // Get all the messagesList where status not equals to DEFAULT_STATUS
        defaultMessagesShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the messagesList where status not equals to UPDATED_STATUS
        defaultMessagesShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllMessagesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        messagesRepository.saveAndFlush(messages);

        // Get all the messagesList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultMessagesShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the messagesList where status equals to UPDATED_STATUS
        defaultMessagesShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllMessagesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        messagesRepository.saveAndFlush(messages);

        // Get all the messagesList where status is not null
        defaultMessagesShouldBeFound("status.specified=true");

        // Get all the messagesList where status is null
        defaultMessagesShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllMessagesByStatusIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        messagesRepository.saveAndFlush(messages);

        // Get all the messagesList where status is greater than or equal to DEFAULT_STATUS
        defaultMessagesShouldBeFound("status.greaterThanOrEqual=" + DEFAULT_STATUS);

        // Get all the messagesList where status is greater than or equal to UPDATED_STATUS
        defaultMessagesShouldNotBeFound("status.greaterThanOrEqual=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllMessagesByStatusIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        messagesRepository.saveAndFlush(messages);

        // Get all the messagesList where status is less than or equal to DEFAULT_STATUS
        defaultMessagesShouldBeFound("status.lessThanOrEqual=" + DEFAULT_STATUS);

        // Get all the messagesList where status is less than or equal to SMALLER_STATUS
        defaultMessagesShouldNotBeFound("status.lessThanOrEqual=" + SMALLER_STATUS);
    }

    @Test
    @Transactional
    public void getAllMessagesByStatusIsLessThanSomething() throws Exception {
        // Initialize the database
        messagesRepository.saveAndFlush(messages);

        // Get all the messagesList where status is less than DEFAULT_STATUS
        defaultMessagesShouldNotBeFound("status.lessThan=" + DEFAULT_STATUS);

        // Get all the messagesList where status is less than UPDATED_STATUS
        defaultMessagesShouldBeFound("status.lessThan=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllMessagesByStatusIsGreaterThanSomething() throws Exception {
        // Initialize the database
        messagesRepository.saveAndFlush(messages);

        // Get all the messagesList where status is greater than DEFAULT_STATUS
        defaultMessagesShouldNotBeFound("status.greaterThan=" + DEFAULT_STATUS);

        // Get all the messagesList where status is greater than SMALLER_STATUS
        defaultMessagesShouldBeFound("status.greaterThan=" + SMALLER_STATUS);
    }


    @Test
    @Transactional
    public void getAllMessagesByApplicantNameIsEqualToSomething() throws Exception {
        // Initialize the database
        messagesRepository.saveAndFlush(messages);

        // Get all the messagesList where applicantName equals to DEFAULT_APPLICANT_NAME
        defaultMessagesShouldBeFound("applicantName.equals=" + DEFAULT_APPLICANT_NAME);

        // Get all the messagesList where applicantName equals to UPDATED_APPLICANT_NAME
        defaultMessagesShouldNotBeFound("applicantName.equals=" + UPDATED_APPLICANT_NAME);
    }

    @Test
    @Transactional
    public void getAllMessagesByApplicantNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        messagesRepository.saveAndFlush(messages);

        // Get all the messagesList where applicantName not equals to DEFAULT_APPLICANT_NAME
        defaultMessagesShouldNotBeFound("applicantName.notEquals=" + DEFAULT_APPLICANT_NAME);

        // Get all the messagesList where applicantName not equals to UPDATED_APPLICANT_NAME
        defaultMessagesShouldBeFound("applicantName.notEquals=" + UPDATED_APPLICANT_NAME);
    }

    @Test
    @Transactional
    public void getAllMessagesByApplicantNameIsInShouldWork() throws Exception {
        // Initialize the database
        messagesRepository.saveAndFlush(messages);

        // Get all the messagesList where applicantName in DEFAULT_APPLICANT_NAME or UPDATED_APPLICANT_NAME
        defaultMessagesShouldBeFound("applicantName.in=" + DEFAULT_APPLICANT_NAME + "," + UPDATED_APPLICANT_NAME);

        // Get all the messagesList where applicantName equals to UPDATED_APPLICANT_NAME
        defaultMessagesShouldNotBeFound("applicantName.in=" + UPDATED_APPLICANT_NAME);
    }

    @Test
    @Transactional
    public void getAllMessagesByApplicantNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        messagesRepository.saveAndFlush(messages);

        // Get all the messagesList where applicantName is not null
        defaultMessagesShouldBeFound("applicantName.specified=true");

        // Get all the messagesList where applicantName is null
        defaultMessagesShouldNotBeFound("applicantName.specified=false");
    }
                @Test
    @Transactional
    public void getAllMessagesByApplicantNameContainsSomething() throws Exception {
        // Initialize the database
        messagesRepository.saveAndFlush(messages);

        // Get all the messagesList where applicantName contains DEFAULT_APPLICANT_NAME
        defaultMessagesShouldBeFound("applicantName.contains=" + DEFAULT_APPLICANT_NAME);

        // Get all the messagesList where applicantName contains UPDATED_APPLICANT_NAME
        defaultMessagesShouldNotBeFound("applicantName.contains=" + UPDATED_APPLICANT_NAME);
    }

    @Test
    @Transactional
    public void getAllMessagesByApplicantNameNotContainsSomething() throws Exception {
        // Initialize the database
        messagesRepository.saveAndFlush(messages);

        // Get all the messagesList where applicantName does not contain DEFAULT_APPLICANT_NAME
        defaultMessagesShouldNotBeFound("applicantName.doesNotContain=" + DEFAULT_APPLICANT_NAME);

        // Get all the messagesList where applicantName does not contain UPDATED_APPLICANT_NAME
        defaultMessagesShouldBeFound("applicantName.doesNotContain=" + UPDATED_APPLICANT_NAME);
    }


    @Test
    @Transactional
    public void getAllMessagesByCenterIsEqualToSomething() throws Exception {
        // Initialize the database
        messagesRepository.saveAndFlush(messages);
        Center center = CenterResourceIT.createEntity(em);
        em.persist(center);
        em.flush();
        messages.setCenter(center);
        messagesRepository.saveAndFlush(messages);
        Long centerId = center.getId();

        // Get all the messagesList where center equals to centerId
        defaultMessagesShouldBeFound("centerId.equals=" + centerId);

        // Get all the messagesList where center equals to centerId + 1
        defaultMessagesShouldNotBeFound("centerId.equals=" + (centerId + 1));
    }


    @Test
    @Transactional
    public void getAllMessagesBySystemIsEqualToSomething() throws Exception {
        // Initialize the database
        messagesRepository.saveAndFlush(messages);
        System system = SystemResourceIT.createEntity(em);
        em.persist(system);
        em.flush();
        messages.setSystem(system);
        messagesRepository.saveAndFlush(messages);
        Long systemId = system.getId();

        // Get all the messagesList where system equals to systemId
        defaultMessagesShouldBeFound("systemId.equals=" + systemId);

        // Get all the messagesList where system equals to systemId + 1
        defaultMessagesShouldNotBeFound("systemId.equals=" + (systemId + 1));
    }


    @Test
    @Transactional
    public void getAllMessagesBySystemServicesIsEqualToSomething() throws Exception {
        // Initialize the database
        messagesRepository.saveAndFlush(messages);
        SystemServices systemServices = SystemServicesResourceIT.createEntity(em);
        em.persist(systemServices);
        em.flush();
        messages.setSystemServices(systemServices);
        messagesRepository.saveAndFlush(messages);
        Long systemServicesId = systemServices.getId();

        // Get all the messagesList where systemServices equals to systemServicesId
        defaultMessagesShouldBeFound("systemServicesId.equals=" + systemServicesId);

        // Get all the messagesList where systemServices equals to systemServicesId + 1
        defaultMessagesShouldNotBeFound("systemServicesId.equals=" + (systemServicesId + 1));
    }


    @Test
    @Transactional
    public void getAllMessagesByUsersIsEqualToSomething() throws Exception {
        // Initialize the database
        messagesRepository.saveAndFlush(messages);
        Users users = UsersResourceIT.createEntity(em);
        em.persist(users);
        em.flush();
        messages.setUsers(users);
        messagesRepository.saveAndFlush(messages);
        Long usersId = users.getId();

        // Get all the messagesList where users equals to usersId
        defaultMessagesShouldBeFound("usersId.equals=" + usersId);

        // Get all the messagesList where users equals to usersId + 1
        defaultMessagesShouldNotBeFound("usersId.equals=" + (usersId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMessagesShouldBeFound(String filter) throws Exception {
        restMessagesMockMvc.perform(get("/api/messages?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(messages.getId().intValue())))
            .andExpect(jsonPath("$.[*].counter").value(hasItem(DEFAULT_COUNTER.intValue())))
            .andExpect(jsonPath("$.[*].trsId").value(hasItem(DEFAULT_TRS_ID.intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].applicantName").value(hasItem(DEFAULT_APPLICANT_NAME)));

        // Check, that the count call also returns 1
        restMessagesMockMvc.perform(get("/api/messages/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMessagesShouldNotBeFound(String filter) throws Exception {
        restMessagesMockMvc.perform(get("/api/messages?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMessagesMockMvc.perform(get("/api/messages/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingMessages() throws Exception {
        // Get the messages
        restMessagesMockMvc.perform(get("/api/messages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMessages() throws Exception {
        // Initialize the database
        messagesRepository.saveAndFlush(messages);

        int databaseSizeBeforeUpdate = messagesRepository.findAll().size();

        // Update the messages
        Messages updatedMessages = messagesRepository.findById(messages.getId()).get();
        // Disconnect from session so that the updates on updatedMessages are not directly saved in db
        em.detach(updatedMessages);
        updatedMessages
            .counter(UPDATED_COUNTER)
            .trsId(UPDATED_TRS_ID)
            .userId(UPDATED_USER_ID)
            .message(UPDATED_MESSAGE)
            .status(UPDATED_STATUS)
            .applicantName(UPDATED_APPLICANT_NAME);
        MessagesDTO messagesDTO = messagesMapper.toDto(updatedMessages);

        restMessagesMockMvc.perform(put("/api/messages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(messagesDTO)))
            .andExpect(status().isOk());

        // Validate the Messages in the database
        List<Messages> messagesList = messagesRepository.findAll();
        assertThat(messagesList).hasSize(databaseSizeBeforeUpdate);
        Messages testMessages = messagesList.get(messagesList.size() - 1);
        assertThat(testMessages.getCounter()).isEqualTo(UPDATED_COUNTER);
        assertThat(testMessages.getTrsId()).isEqualTo(UPDATED_TRS_ID);
        assertThat(testMessages.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testMessages.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testMessages.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMessages.getApplicantName()).isEqualTo(UPDATED_APPLICANT_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingMessages() throws Exception {
        int databaseSizeBeforeUpdate = messagesRepository.findAll().size();

        // Create the Messages
        MessagesDTO messagesDTO = messagesMapper.toDto(messages);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMessagesMockMvc.perform(put("/api/messages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(messagesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Messages in the database
        List<Messages> messagesList = messagesRepository.findAll();
        assertThat(messagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMessages() throws Exception {
        // Initialize the database
        messagesRepository.saveAndFlush(messages);

        int databaseSizeBeforeDelete = messagesRepository.findAll().size();

        // Delete the messages
        restMessagesMockMvc.perform(delete("/api/messages/{id}", messages.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Messages> messagesList = messagesRepository.findAll();
        assertThat(messagesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
