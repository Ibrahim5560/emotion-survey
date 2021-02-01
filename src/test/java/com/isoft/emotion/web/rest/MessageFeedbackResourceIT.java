package com.isoft.emotion.web.rest;

import com.isoft.emotion.EmotionSurveyApp;
import com.isoft.emotion.domain.MessageFeedback;
import com.isoft.emotion.repository.MessageFeedbackRepository;
import com.isoft.emotion.service.MessageFeedbackService;
import com.isoft.emotion.service.dto.MessageFeedbackDTO;
import com.isoft.emotion.service.mapper.MessageFeedbackMapper;
import com.isoft.emotion.service.dto.MessageFeedbackCriteria;
import com.isoft.emotion.service.MessageFeedbackQueryService;

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
 * Integration tests for the {@link MessageFeedbackResource} REST controller.
 */
@SpringBootTest(classes = EmotionSurveyApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class MessageFeedbackResourceIT {

    private static final Long DEFAULT_SYSTEM_ID = 1L;
    private static final Long UPDATED_SYSTEM_ID = 2L;
    private static final Long SMALLER_SYSTEM_ID = 1L - 1L;

    private static final Long DEFAULT_CENTER_ID = 1L;
    private static final Long UPDATED_CENTER_ID = 2L;
    private static final Long SMALLER_CENTER_ID = 1L - 1L;

    private static final Long DEFAULT_SYSTEM_SERVICES_ID = 1L;
    private static final Long UPDATED_SYSTEM_SERVICES_ID = 2L;
    private static final Long SMALLER_SYSTEM_SERVICES_ID = 1L - 1L;

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

    private static final String DEFAULT_FEEDBACK = "AAAAAAAAAA";
    private static final String UPDATED_FEEDBACK = "BBBBBBBBBB";

    private static final String DEFAULT_APPLICANT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_APPLICANT_NAME = "BBBBBBBBBB";

    @Autowired
    private MessageFeedbackRepository messageFeedbackRepository;

    @Autowired
    private MessageFeedbackMapper messageFeedbackMapper;

    @Autowired
    private MessageFeedbackService messageFeedbackService;

    @Autowired
    private MessageFeedbackQueryService messageFeedbackQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMessageFeedbackMockMvc;

    private MessageFeedback messageFeedback;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MessageFeedback createEntity(EntityManager em) {
        MessageFeedback messageFeedback = new MessageFeedback()
            .systemId(DEFAULT_SYSTEM_ID)
            .centerId(DEFAULT_CENTER_ID)
            .systemServicesId(DEFAULT_SYSTEM_SERVICES_ID)
            .counter(DEFAULT_COUNTER)
            .trsId(DEFAULT_TRS_ID)
            .userId(DEFAULT_USER_ID)
            .message(DEFAULT_MESSAGE)
            .status(DEFAULT_STATUS)
            .feedback(DEFAULT_FEEDBACK)
            .applicantName(DEFAULT_APPLICANT_NAME);
        return messageFeedback;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MessageFeedback createUpdatedEntity(EntityManager em) {
        MessageFeedback messageFeedback = new MessageFeedback()
            .systemId(UPDATED_SYSTEM_ID)
            .centerId(UPDATED_CENTER_ID)
            .systemServicesId(UPDATED_SYSTEM_SERVICES_ID)
            .counter(UPDATED_COUNTER)
            .trsId(UPDATED_TRS_ID)
            .userId(UPDATED_USER_ID)
            .message(UPDATED_MESSAGE)
            .status(UPDATED_STATUS)
            .feedback(UPDATED_FEEDBACK)
            .applicantName(UPDATED_APPLICANT_NAME);
        return messageFeedback;
    }

    @BeforeEach
    public void initTest() {
        messageFeedback = createEntity(em);
    }

    @Test
    @Transactional
    public void createMessageFeedback() throws Exception {
        int databaseSizeBeforeCreate = messageFeedbackRepository.findAll().size();
        // Create the MessageFeedback
        MessageFeedbackDTO messageFeedbackDTO = messageFeedbackMapper.toDto(messageFeedback);
        restMessageFeedbackMockMvc.perform(post("/api/message-feedbacks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(messageFeedbackDTO)))
            .andExpect(status().isCreated());

        // Validate the MessageFeedback in the database
        List<MessageFeedback> messageFeedbackList = messageFeedbackRepository.findAll();
        assertThat(messageFeedbackList).hasSize(databaseSizeBeforeCreate + 1);
        MessageFeedback testMessageFeedback = messageFeedbackList.get(messageFeedbackList.size() - 1);
        assertThat(testMessageFeedback.getSystemId()).isEqualTo(DEFAULT_SYSTEM_ID);
        assertThat(testMessageFeedback.getCenterId()).isEqualTo(DEFAULT_CENTER_ID);
        assertThat(testMessageFeedback.getSystemServicesId()).isEqualTo(DEFAULT_SYSTEM_SERVICES_ID);
        assertThat(testMessageFeedback.getCounter()).isEqualTo(DEFAULT_COUNTER);
        assertThat(testMessageFeedback.getTrsId()).isEqualTo(DEFAULT_TRS_ID);
        assertThat(testMessageFeedback.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testMessageFeedback.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testMessageFeedback.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMessageFeedback.getFeedback()).isEqualTo(DEFAULT_FEEDBACK);
        assertThat(testMessageFeedback.getApplicantName()).isEqualTo(DEFAULT_APPLICANT_NAME);
    }

    @Test
    @Transactional
    public void createMessageFeedbackWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = messageFeedbackRepository.findAll().size();

        // Create the MessageFeedback with an existing ID
        messageFeedback.setId(1L);
        MessageFeedbackDTO messageFeedbackDTO = messageFeedbackMapper.toDto(messageFeedback);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMessageFeedbackMockMvc.perform(post("/api/message-feedbacks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(messageFeedbackDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MessageFeedback in the database
        List<MessageFeedback> messageFeedbackList = messageFeedbackRepository.findAll();
        assertThat(messageFeedbackList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMessageFeedbacks() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList
        restMessageFeedbackMockMvc.perform(get("/api/message-feedbacks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(messageFeedback.getId().intValue())))
            .andExpect(jsonPath("$.[*].systemId").value(hasItem(DEFAULT_SYSTEM_ID.intValue())))
            .andExpect(jsonPath("$.[*].centerId").value(hasItem(DEFAULT_CENTER_ID.intValue())))
            .andExpect(jsonPath("$.[*].systemServicesId").value(hasItem(DEFAULT_SYSTEM_SERVICES_ID.intValue())))
            .andExpect(jsonPath("$.[*].counter").value(hasItem(DEFAULT_COUNTER.intValue())))
            .andExpect(jsonPath("$.[*].trsId").value(hasItem(DEFAULT_TRS_ID.intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].feedback").value(hasItem(DEFAULT_FEEDBACK)))
            .andExpect(jsonPath("$.[*].applicantName").value(hasItem(DEFAULT_APPLICANT_NAME)));
    }
    
    @Test
    @Transactional
    public void getMessageFeedback() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get the messageFeedback
        restMessageFeedbackMockMvc.perform(get("/api/message-feedbacks/{id}", messageFeedback.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(messageFeedback.getId().intValue()))
            .andExpect(jsonPath("$.systemId").value(DEFAULT_SYSTEM_ID.intValue()))
            .andExpect(jsonPath("$.centerId").value(DEFAULT_CENTER_ID.intValue()))
            .andExpect(jsonPath("$.systemServicesId").value(DEFAULT_SYSTEM_SERVICES_ID.intValue()))
            .andExpect(jsonPath("$.counter").value(DEFAULT_COUNTER.intValue()))
            .andExpect(jsonPath("$.trsId").value(DEFAULT_TRS_ID.intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.feedback").value(DEFAULT_FEEDBACK))
            .andExpect(jsonPath("$.applicantName").value(DEFAULT_APPLICANT_NAME));
    }


    @Test
    @Transactional
    public void getMessageFeedbacksByIdFiltering() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        Long id = messageFeedback.getId();

        defaultMessageFeedbackShouldBeFound("id.equals=" + id);
        defaultMessageFeedbackShouldNotBeFound("id.notEquals=" + id);

        defaultMessageFeedbackShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMessageFeedbackShouldNotBeFound("id.greaterThan=" + id);

        defaultMessageFeedbackShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMessageFeedbackShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMessageFeedbacksBySystemIdIsEqualToSomething() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where systemId equals to DEFAULT_SYSTEM_ID
        defaultMessageFeedbackShouldBeFound("systemId.equals=" + DEFAULT_SYSTEM_ID);

        // Get all the messageFeedbackList where systemId equals to UPDATED_SYSTEM_ID
        defaultMessageFeedbackShouldNotBeFound("systemId.equals=" + UPDATED_SYSTEM_ID);
    }

    @Test
    @Transactional
    public void getAllMessageFeedbacksBySystemIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where systemId not equals to DEFAULT_SYSTEM_ID
        defaultMessageFeedbackShouldNotBeFound("systemId.notEquals=" + DEFAULT_SYSTEM_ID);

        // Get all the messageFeedbackList where systemId not equals to UPDATED_SYSTEM_ID
        defaultMessageFeedbackShouldBeFound("systemId.notEquals=" + UPDATED_SYSTEM_ID);
    }

    @Test
    @Transactional
    public void getAllMessageFeedbacksBySystemIdIsInShouldWork() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where systemId in DEFAULT_SYSTEM_ID or UPDATED_SYSTEM_ID
        defaultMessageFeedbackShouldBeFound("systemId.in=" + DEFAULT_SYSTEM_ID + "," + UPDATED_SYSTEM_ID);

        // Get all the messageFeedbackList where systemId equals to UPDATED_SYSTEM_ID
        defaultMessageFeedbackShouldNotBeFound("systemId.in=" + UPDATED_SYSTEM_ID);
    }

    @Test
    @Transactional
    public void getAllMessageFeedbacksBySystemIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where systemId is not null
        defaultMessageFeedbackShouldBeFound("systemId.specified=true");

        // Get all the messageFeedbackList where systemId is null
        defaultMessageFeedbackShouldNotBeFound("systemId.specified=false");
    }

    @Test
    @Transactional
    public void getAllMessageFeedbacksBySystemIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where systemId is greater than or equal to DEFAULT_SYSTEM_ID
        defaultMessageFeedbackShouldBeFound("systemId.greaterThanOrEqual=" + DEFAULT_SYSTEM_ID);

        // Get all the messageFeedbackList where systemId is greater than or equal to UPDATED_SYSTEM_ID
        defaultMessageFeedbackShouldNotBeFound("systemId.greaterThanOrEqual=" + UPDATED_SYSTEM_ID);
    }

    @Test
    @Transactional
    public void getAllMessageFeedbacksBySystemIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where systemId is less than or equal to DEFAULT_SYSTEM_ID
        defaultMessageFeedbackShouldBeFound("systemId.lessThanOrEqual=" + DEFAULT_SYSTEM_ID);

        // Get all the messageFeedbackList where systemId is less than or equal to SMALLER_SYSTEM_ID
        defaultMessageFeedbackShouldNotBeFound("systemId.lessThanOrEqual=" + SMALLER_SYSTEM_ID);
    }

    @Test
    @Transactional
    public void getAllMessageFeedbacksBySystemIdIsLessThanSomething() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where systemId is less than DEFAULT_SYSTEM_ID
        defaultMessageFeedbackShouldNotBeFound("systemId.lessThan=" + DEFAULT_SYSTEM_ID);

        // Get all the messageFeedbackList where systemId is less than UPDATED_SYSTEM_ID
        defaultMessageFeedbackShouldBeFound("systemId.lessThan=" + UPDATED_SYSTEM_ID);
    }

    @Test
    @Transactional
    public void getAllMessageFeedbacksBySystemIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where systemId is greater than DEFAULT_SYSTEM_ID
        defaultMessageFeedbackShouldNotBeFound("systemId.greaterThan=" + DEFAULT_SYSTEM_ID);

        // Get all the messageFeedbackList where systemId is greater than SMALLER_SYSTEM_ID
        defaultMessageFeedbackShouldBeFound("systemId.greaterThan=" + SMALLER_SYSTEM_ID);
    }


    @Test
    @Transactional
    public void getAllMessageFeedbacksByCenterIdIsEqualToSomething() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where centerId equals to DEFAULT_CENTER_ID
        defaultMessageFeedbackShouldBeFound("centerId.equals=" + DEFAULT_CENTER_ID);

        // Get all the messageFeedbackList where centerId equals to UPDATED_CENTER_ID
        defaultMessageFeedbackShouldNotBeFound("centerId.equals=" + UPDATED_CENTER_ID);
    }

    @Test
    @Transactional
    public void getAllMessageFeedbacksByCenterIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where centerId not equals to DEFAULT_CENTER_ID
        defaultMessageFeedbackShouldNotBeFound("centerId.notEquals=" + DEFAULT_CENTER_ID);

        // Get all the messageFeedbackList where centerId not equals to UPDATED_CENTER_ID
        defaultMessageFeedbackShouldBeFound("centerId.notEquals=" + UPDATED_CENTER_ID);
    }

    @Test
    @Transactional
    public void getAllMessageFeedbacksByCenterIdIsInShouldWork() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where centerId in DEFAULT_CENTER_ID or UPDATED_CENTER_ID
        defaultMessageFeedbackShouldBeFound("centerId.in=" + DEFAULT_CENTER_ID + "," + UPDATED_CENTER_ID);

        // Get all the messageFeedbackList where centerId equals to UPDATED_CENTER_ID
        defaultMessageFeedbackShouldNotBeFound("centerId.in=" + UPDATED_CENTER_ID);
    }

    @Test
    @Transactional
    public void getAllMessageFeedbacksByCenterIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where centerId is not null
        defaultMessageFeedbackShouldBeFound("centerId.specified=true");

        // Get all the messageFeedbackList where centerId is null
        defaultMessageFeedbackShouldNotBeFound("centerId.specified=false");
    }

    @Test
    @Transactional
    public void getAllMessageFeedbacksByCenterIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where centerId is greater than or equal to DEFAULT_CENTER_ID
        defaultMessageFeedbackShouldBeFound("centerId.greaterThanOrEqual=" + DEFAULT_CENTER_ID);

        // Get all the messageFeedbackList where centerId is greater than or equal to UPDATED_CENTER_ID
        defaultMessageFeedbackShouldNotBeFound("centerId.greaterThanOrEqual=" + UPDATED_CENTER_ID);
    }

    @Test
    @Transactional
    public void getAllMessageFeedbacksByCenterIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where centerId is less than or equal to DEFAULT_CENTER_ID
        defaultMessageFeedbackShouldBeFound("centerId.lessThanOrEqual=" + DEFAULT_CENTER_ID);

        // Get all the messageFeedbackList where centerId is less than or equal to SMALLER_CENTER_ID
        defaultMessageFeedbackShouldNotBeFound("centerId.lessThanOrEqual=" + SMALLER_CENTER_ID);
    }

    @Test
    @Transactional
    public void getAllMessageFeedbacksByCenterIdIsLessThanSomething() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where centerId is less than DEFAULT_CENTER_ID
        defaultMessageFeedbackShouldNotBeFound("centerId.lessThan=" + DEFAULT_CENTER_ID);

        // Get all the messageFeedbackList where centerId is less than UPDATED_CENTER_ID
        defaultMessageFeedbackShouldBeFound("centerId.lessThan=" + UPDATED_CENTER_ID);
    }

    @Test
    @Transactional
    public void getAllMessageFeedbacksByCenterIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where centerId is greater than DEFAULT_CENTER_ID
        defaultMessageFeedbackShouldNotBeFound("centerId.greaterThan=" + DEFAULT_CENTER_ID);

        // Get all the messageFeedbackList where centerId is greater than SMALLER_CENTER_ID
        defaultMessageFeedbackShouldBeFound("centerId.greaterThan=" + SMALLER_CENTER_ID);
    }


    @Test
    @Transactional
    public void getAllMessageFeedbacksBySystemServicesIdIsEqualToSomething() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where systemServicesId equals to DEFAULT_SYSTEM_SERVICES_ID
        defaultMessageFeedbackShouldBeFound("systemServicesId.equals=" + DEFAULT_SYSTEM_SERVICES_ID);

        // Get all the messageFeedbackList where systemServicesId equals to UPDATED_SYSTEM_SERVICES_ID
        defaultMessageFeedbackShouldNotBeFound("systemServicesId.equals=" + UPDATED_SYSTEM_SERVICES_ID);
    }

    @Test
    @Transactional
    public void getAllMessageFeedbacksBySystemServicesIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where systemServicesId not equals to DEFAULT_SYSTEM_SERVICES_ID
        defaultMessageFeedbackShouldNotBeFound("systemServicesId.notEquals=" + DEFAULT_SYSTEM_SERVICES_ID);

        // Get all the messageFeedbackList where systemServicesId not equals to UPDATED_SYSTEM_SERVICES_ID
        defaultMessageFeedbackShouldBeFound("systemServicesId.notEquals=" + UPDATED_SYSTEM_SERVICES_ID);
    }

    @Test
    @Transactional
    public void getAllMessageFeedbacksBySystemServicesIdIsInShouldWork() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where systemServicesId in DEFAULT_SYSTEM_SERVICES_ID or UPDATED_SYSTEM_SERVICES_ID
        defaultMessageFeedbackShouldBeFound("systemServicesId.in=" + DEFAULT_SYSTEM_SERVICES_ID + "," + UPDATED_SYSTEM_SERVICES_ID);

        // Get all the messageFeedbackList where systemServicesId equals to UPDATED_SYSTEM_SERVICES_ID
        defaultMessageFeedbackShouldNotBeFound("systemServicesId.in=" + UPDATED_SYSTEM_SERVICES_ID);
    }

    @Test
    @Transactional
    public void getAllMessageFeedbacksBySystemServicesIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where systemServicesId is not null
        defaultMessageFeedbackShouldBeFound("systemServicesId.specified=true");

        // Get all the messageFeedbackList where systemServicesId is null
        defaultMessageFeedbackShouldNotBeFound("systemServicesId.specified=false");
    }

    @Test
    @Transactional
    public void getAllMessageFeedbacksBySystemServicesIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where systemServicesId is greater than or equal to DEFAULT_SYSTEM_SERVICES_ID
        defaultMessageFeedbackShouldBeFound("systemServicesId.greaterThanOrEqual=" + DEFAULT_SYSTEM_SERVICES_ID);

        // Get all the messageFeedbackList where systemServicesId is greater than or equal to UPDATED_SYSTEM_SERVICES_ID
        defaultMessageFeedbackShouldNotBeFound("systemServicesId.greaterThanOrEqual=" + UPDATED_SYSTEM_SERVICES_ID);
    }

    @Test
    @Transactional
    public void getAllMessageFeedbacksBySystemServicesIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where systemServicesId is less than or equal to DEFAULT_SYSTEM_SERVICES_ID
        defaultMessageFeedbackShouldBeFound("systemServicesId.lessThanOrEqual=" + DEFAULT_SYSTEM_SERVICES_ID);

        // Get all the messageFeedbackList where systemServicesId is less than or equal to SMALLER_SYSTEM_SERVICES_ID
        defaultMessageFeedbackShouldNotBeFound("systemServicesId.lessThanOrEqual=" + SMALLER_SYSTEM_SERVICES_ID);
    }

    @Test
    @Transactional
    public void getAllMessageFeedbacksBySystemServicesIdIsLessThanSomething() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where systemServicesId is less than DEFAULT_SYSTEM_SERVICES_ID
        defaultMessageFeedbackShouldNotBeFound("systemServicesId.lessThan=" + DEFAULT_SYSTEM_SERVICES_ID);

        // Get all the messageFeedbackList where systemServicesId is less than UPDATED_SYSTEM_SERVICES_ID
        defaultMessageFeedbackShouldBeFound("systemServicesId.lessThan=" + UPDATED_SYSTEM_SERVICES_ID);
    }

    @Test
    @Transactional
    public void getAllMessageFeedbacksBySystemServicesIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where systemServicesId is greater than DEFAULT_SYSTEM_SERVICES_ID
        defaultMessageFeedbackShouldNotBeFound("systemServicesId.greaterThan=" + DEFAULT_SYSTEM_SERVICES_ID);

        // Get all the messageFeedbackList where systemServicesId is greater than SMALLER_SYSTEM_SERVICES_ID
        defaultMessageFeedbackShouldBeFound("systemServicesId.greaterThan=" + SMALLER_SYSTEM_SERVICES_ID);
    }


    @Test
    @Transactional
    public void getAllMessageFeedbacksByCounterIsEqualToSomething() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where counter equals to DEFAULT_COUNTER
        defaultMessageFeedbackShouldBeFound("counter.equals=" + DEFAULT_COUNTER);

        // Get all the messageFeedbackList where counter equals to UPDATED_COUNTER
        defaultMessageFeedbackShouldNotBeFound("counter.equals=" + UPDATED_COUNTER);
    }

    @Test
    @Transactional
    public void getAllMessageFeedbacksByCounterIsNotEqualToSomething() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where counter not equals to DEFAULT_COUNTER
        defaultMessageFeedbackShouldNotBeFound("counter.notEquals=" + DEFAULT_COUNTER);

        // Get all the messageFeedbackList where counter not equals to UPDATED_COUNTER
        defaultMessageFeedbackShouldBeFound("counter.notEquals=" + UPDATED_COUNTER);
    }

    @Test
    @Transactional
    public void getAllMessageFeedbacksByCounterIsInShouldWork() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where counter in DEFAULT_COUNTER or UPDATED_COUNTER
        defaultMessageFeedbackShouldBeFound("counter.in=" + DEFAULT_COUNTER + "," + UPDATED_COUNTER);

        // Get all the messageFeedbackList where counter equals to UPDATED_COUNTER
        defaultMessageFeedbackShouldNotBeFound("counter.in=" + UPDATED_COUNTER);
    }

    @Test
    @Transactional
    public void getAllMessageFeedbacksByCounterIsNullOrNotNull() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where counter is not null
        defaultMessageFeedbackShouldBeFound("counter.specified=true");

        // Get all the messageFeedbackList where counter is null
        defaultMessageFeedbackShouldNotBeFound("counter.specified=false");
    }

    @Test
    @Transactional
    public void getAllMessageFeedbacksByCounterIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where counter is greater than or equal to DEFAULT_COUNTER
        defaultMessageFeedbackShouldBeFound("counter.greaterThanOrEqual=" + DEFAULT_COUNTER);

        // Get all the messageFeedbackList where counter is greater than or equal to UPDATED_COUNTER
        defaultMessageFeedbackShouldNotBeFound("counter.greaterThanOrEqual=" + UPDATED_COUNTER);
    }

    @Test
    @Transactional
    public void getAllMessageFeedbacksByCounterIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where counter is less than or equal to DEFAULT_COUNTER
        defaultMessageFeedbackShouldBeFound("counter.lessThanOrEqual=" + DEFAULT_COUNTER);

        // Get all the messageFeedbackList where counter is less than or equal to SMALLER_COUNTER
        defaultMessageFeedbackShouldNotBeFound("counter.lessThanOrEqual=" + SMALLER_COUNTER);
    }

    @Test
    @Transactional
    public void getAllMessageFeedbacksByCounterIsLessThanSomething() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where counter is less than DEFAULT_COUNTER
        defaultMessageFeedbackShouldNotBeFound("counter.lessThan=" + DEFAULT_COUNTER);

        // Get all the messageFeedbackList where counter is less than UPDATED_COUNTER
        defaultMessageFeedbackShouldBeFound("counter.lessThan=" + UPDATED_COUNTER);
    }

    @Test
    @Transactional
    public void getAllMessageFeedbacksByCounterIsGreaterThanSomething() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where counter is greater than DEFAULT_COUNTER
        defaultMessageFeedbackShouldNotBeFound("counter.greaterThan=" + DEFAULT_COUNTER);

        // Get all the messageFeedbackList where counter is greater than SMALLER_COUNTER
        defaultMessageFeedbackShouldBeFound("counter.greaterThan=" + SMALLER_COUNTER);
    }


    @Test
    @Transactional
    public void getAllMessageFeedbacksByTrsIdIsEqualToSomething() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where trsId equals to DEFAULT_TRS_ID
        defaultMessageFeedbackShouldBeFound("trsId.equals=" + DEFAULT_TRS_ID);

        // Get all the messageFeedbackList where trsId equals to UPDATED_TRS_ID
        defaultMessageFeedbackShouldNotBeFound("trsId.equals=" + UPDATED_TRS_ID);
    }

    @Test
    @Transactional
    public void getAllMessageFeedbacksByTrsIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where trsId not equals to DEFAULT_TRS_ID
        defaultMessageFeedbackShouldNotBeFound("trsId.notEquals=" + DEFAULT_TRS_ID);

        // Get all the messageFeedbackList where trsId not equals to UPDATED_TRS_ID
        defaultMessageFeedbackShouldBeFound("trsId.notEquals=" + UPDATED_TRS_ID);
    }

    @Test
    @Transactional
    public void getAllMessageFeedbacksByTrsIdIsInShouldWork() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where trsId in DEFAULT_TRS_ID or UPDATED_TRS_ID
        defaultMessageFeedbackShouldBeFound("trsId.in=" + DEFAULT_TRS_ID + "," + UPDATED_TRS_ID);

        // Get all the messageFeedbackList where trsId equals to UPDATED_TRS_ID
        defaultMessageFeedbackShouldNotBeFound("trsId.in=" + UPDATED_TRS_ID);
    }

    @Test
    @Transactional
    public void getAllMessageFeedbacksByTrsIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where trsId is not null
        defaultMessageFeedbackShouldBeFound("trsId.specified=true");

        // Get all the messageFeedbackList where trsId is null
        defaultMessageFeedbackShouldNotBeFound("trsId.specified=false");
    }

    @Test
    @Transactional
    public void getAllMessageFeedbacksByTrsIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where trsId is greater than or equal to DEFAULT_TRS_ID
        defaultMessageFeedbackShouldBeFound("trsId.greaterThanOrEqual=" + DEFAULT_TRS_ID);

        // Get all the messageFeedbackList where trsId is greater than or equal to UPDATED_TRS_ID
        defaultMessageFeedbackShouldNotBeFound("trsId.greaterThanOrEqual=" + UPDATED_TRS_ID);
    }

    @Test
    @Transactional
    public void getAllMessageFeedbacksByTrsIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where trsId is less than or equal to DEFAULT_TRS_ID
        defaultMessageFeedbackShouldBeFound("trsId.lessThanOrEqual=" + DEFAULT_TRS_ID);

        // Get all the messageFeedbackList where trsId is less than or equal to SMALLER_TRS_ID
        defaultMessageFeedbackShouldNotBeFound("trsId.lessThanOrEqual=" + SMALLER_TRS_ID);
    }

    @Test
    @Transactional
    public void getAllMessageFeedbacksByTrsIdIsLessThanSomething() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where trsId is less than DEFAULT_TRS_ID
        defaultMessageFeedbackShouldNotBeFound("trsId.lessThan=" + DEFAULT_TRS_ID);

        // Get all the messageFeedbackList where trsId is less than UPDATED_TRS_ID
        defaultMessageFeedbackShouldBeFound("trsId.lessThan=" + UPDATED_TRS_ID);
    }

    @Test
    @Transactional
    public void getAllMessageFeedbacksByTrsIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where trsId is greater than DEFAULT_TRS_ID
        defaultMessageFeedbackShouldNotBeFound("trsId.greaterThan=" + DEFAULT_TRS_ID);

        // Get all the messageFeedbackList where trsId is greater than SMALLER_TRS_ID
        defaultMessageFeedbackShouldBeFound("trsId.greaterThan=" + SMALLER_TRS_ID);
    }


    @Test
    @Transactional
    public void getAllMessageFeedbacksByUserIdIsEqualToSomething() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where userId equals to DEFAULT_USER_ID
        defaultMessageFeedbackShouldBeFound("userId.equals=" + DEFAULT_USER_ID);

        // Get all the messageFeedbackList where userId equals to UPDATED_USER_ID
        defaultMessageFeedbackShouldNotBeFound("userId.equals=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllMessageFeedbacksByUserIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where userId not equals to DEFAULT_USER_ID
        defaultMessageFeedbackShouldNotBeFound("userId.notEquals=" + DEFAULT_USER_ID);

        // Get all the messageFeedbackList where userId not equals to UPDATED_USER_ID
        defaultMessageFeedbackShouldBeFound("userId.notEquals=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllMessageFeedbacksByUserIdIsInShouldWork() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where userId in DEFAULT_USER_ID or UPDATED_USER_ID
        defaultMessageFeedbackShouldBeFound("userId.in=" + DEFAULT_USER_ID + "," + UPDATED_USER_ID);

        // Get all the messageFeedbackList where userId equals to UPDATED_USER_ID
        defaultMessageFeedbackShouldNotBeFound("userId.in=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllMessageFeedbacksByUserIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where userId is not null
        defaultMessageFeedbackShouldBeFound("userId.specified=true");

        // Get all the messageFeedbackList where userId is null
        defaultMessageFeedbackShouldNotBeFound("userId.specified=false");
    }

    @Test
    @Transactional
    public void getAllMessageFeedbacksByUserIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where userId is greater than or equal to DEFAULT_USER_ID
        defaultMessageFeedbackShouldBeFound("userId.greaterThanOrEqual=" + DEFAULT_USER_ID);

        // Get all the messageFeedbackList where userId is greater than or equal to UPDATED_USER_ID
        defaultMessageFeedbackShouldNotBeFound("userId.greaterThanOrEqual=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllMessageFeedbacksByUserIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where userId is less than or equal to DEFAULT_USER_ID
        defaultMessageFeedbackShouldBeFound("userId.lessThanOrEqual=" + DEFAULT_USER_ID);

        // Get all the messageFeedbackList where userId is less than or equal to SMALLER_USER_ID
        defaultMessageFeedbackShouldNotBeFound("userId.lessThanOrEqual=" + SMALLER_USER_ID);
    }

    @Test
    @Transactional
    public void getAllMessageFeedbacksByUserIdIsLessThanSomething() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where userId is less than DEFAULT_USER_ID
        defaultMessageFeedbackShouldNotBeFound("userId.lessThan=" + DEFAULT_USER_ID);

        // Get all the messageFeedbackList where userId is less than UPDATED_USER_ID
        defaultMessageFeedbackShouldBeFound("userId.lessThan=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllMessageFeedbacksByUserIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where userId is greater than DEFAULT_USER_ID
        defaultMessageFeedbackShouldNotBeFound("userId.greaterThan=" + DEFAULT_USER_ID);

        // Get all the messageFeedbackList where userId is greater than SMALLER_USER_ID
        defaultMessageFeedbackShouldBeFound("userId.greaterThan=" + SMALLER_USER_ID);
    }


    @Test
    @Transactional
    public void getAllMessageFeedbacksByMessageIsEqualToSomething() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where message equals to DEFAULT_MESSAGE
        defaultMessageFeedbackShouldBeFound("message.equals=" + DEFAULT_MESSAGE);

        // Get all the messageFeedbackList where message equals to UPDATED_MESSAGE
        defaultMessageFeedbackShouldNotBeFound("message.equals=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    public void getAllMessageFeedbacksByMessageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where message not equals to DEFAULT_MESSAGE
        defaultMessageFeedbackShouldNotBeFound("message.notEquals=" + DEFAULT_MESSAGE);

        // Get all the messageFeedbackList where message not equals to UPDATED_MESSAGE
        defaultMessageFeedbackShouldBeFound("message.notEquals=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    public void getAllMessageFeedbacksByMessageIsInShouldWork() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where message in DEFAULT_MESSAGE or UPDATED_MESSAGE
        defaultMessageFeedbackShouldBeFound("message.in=" + DEFAULT_MESSAGE + "," + UPDATED_MESSAGE);

        // Get all the messageFeedbackList where message equals to UPDATED_MESSAGE
        defaultMessageFeedbackShouldNotBeFound("message.in=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    public void getAllMessageFeedbacksByMessageIsNullOrNotNull() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where message is not null
        defaultMessageFeedbackShouldBeFound("message.specified=true");

        // Get all the messageFeedbackList where message is null
        defaultMessageFeedbackShouldNotBeFound("message.specified=false");
    }
                @Test
    @Transactional
    public void getAllMessageFeedbacksByMessageContainsSomething() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where message contains DEFAULT_MESSAGE
        defaultMessageFeedbackShouldBeFound("message.contains=" + DEFAULT_MESSAGE);

        // Get all the messageFeedbackList where message contains UPDATED_MESSAGE
        defaultMessageFeedbackShouldNotBeFound("message.contains=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    public void getAllMessageFeedbacksByMessageNotContainsSomething() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where message does not contain DEFAULT_MESSAGE
        defaultMessageFeedbackShouldNotBeFound("message.doesNotContain=" + DEFAULT_MESSAGE);

        // Get all the messageFeedbackList where message does not contain UPDATED_MESSAGE
        defaultMessageFeedbackShouldBeFound("message.doesNotContain=" + UPDATED_MESSAGE);
    }


    @Test
    @Transactional
    public void getAllMessageFeedbacksByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where status equals to DEFAULT_STATUS
        defaultMessageFeedbackShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the messageFeedbackList where status equals to UPDATED_STATUS
        defaultMessageFeedbackShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllMessageFeedbacksByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where status not equals to DEFAULT_STATUS
        defaultMessageFeedbackShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the messageFeedbackList where status not equals to UPDATED_STATUS
        defaultMessageFeedbackShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllMessageFeedbacksByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultMessageFeedbackShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the messageFeedbackList where status equals to UPDATED_STATUS
        defaultMessageFeedbackShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllMessageFeedbacksByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where status is not null
        defaultMessageFeedbackShouldBeFound("status.specified=true");

        // Get all the messageFeedbackList where status is null
        defaultMessageFeedbackShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllMessageFeedbacksByStatusIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where status is greater than or equal to DEFAULT_STATUS
        defaultMessageFeedbackShouldBeFound("status.greaterThanOrEqual=" + DEFAULT_STATUS);

        // Get all the messageFeedbackList where status is greater than or equal to UPDATED_STATUS
        defaultMessageFeedbackShouldNotBeFound("status.greaterThanOrEqual=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllMessageFeedbacksByStatusIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where status is less than or equal to DEFAULT_STATUS
        defaultMessageFeedbackShouldBeFound("status.lessThanOrEqual=" + DEFAULT_STATUS);

        // Get all the messageFeedbackList where status is less than or equal to SMALLER_STATUS
        defaultMessageFeedbackShouldNotBeFound("status.lessThanOrEqual=" + SMALLER_STATUS);
    }

    @Test
    @Transactional
    public void getAllMessageFeedbacksByStatusIsLessThanSomething() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where status is less than DEFAULT_STATUS
        defaultMessageFeedbackShouldNotBeFound("status.lessThan=" + DEFAULT_STATUS);

        // Get all the messageFeedbackList where status is less than UPDATED_STATUS
        defaultMessageFeedbackShouldBeFound("status.lessThan=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllMessageFeedbacksByStatusIsGreaterThanSomething() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where status is greater than DEFAULT_STATUS
        defaultMessageFeedbackShouldNotBeFound("status.greaterThan=" + DEFAULT_STATUS);

        // Get all the messageFeedbackList where status is greater than SMALLER_STATUS
        defaultMessageFeedbackShouldBeFound("status.greaterThan=" + SMALLER_STATUS);
    }


    @Test
    @Transactional
    public void getAllMessageFeedbacksByFeedbackIsEqualToSomething() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where feedback equals to DEFAULT_FEEDBACK
        defaultMessageFeedbackShouldBeFound("feedback.equals=" + DEFAULT_FEEDBACK);

        // Get all the messageFeedbackList where feedback equals to UPDATED_FEEDBACK
        defaultMessageFeedbackShouldNotBeFound("feedback.equals=" + UPDATED_FEEDBACK);
    }

    @Test
    @Transactional
    public void getAllMessageFeedbacksByFeedbackIsNotEqualToSomething() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where feedback not equals to DEFAULT_FEEDBACK
        defaultMessageFeedbackShouldNotBeFound("feedback.notEquals=" + DEFAULT_FEEDBACK);

        // Get all the messageFeedbackList where feedback not equals to UPDATED_FEEDBACK
        defaultMessageFeedbackShouldBeFound("feedback.notEquals=" + UPDATED_FEEDBACK);
    }

    @Test
    @Transactional
    public void getAllMessageFeedbacksByFeedbackIsInShouldWork() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where feedback in DEFAULT_FEEDBACK or UPDATED_FEEDBACK
        defaultMessageFeedbackShouldBeFound("feedback.in=" + DEFAULT_FEEDBACK + "," + UPDATED_FEEDBACK);

        // Get all the messageFeedbackList where feedback equals to UPDATED_FEEDBACK
        defaultMessageFeedbackShouldNotBeFound("feedback.in=" + UPDATED_FEEDBACK);
    }

    @Test
    @Transactional
    public void getAllMessageFeedbacksByFeedbackIsNullOrNotNull() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where feedback is not null
        defaultMessageFeedbackShouldBeFound("feedback.specified=true");

        // Get all the messageFeedbackList where feedback is null
        defaultMessageFeedbackShouldNotBeFound("feedback.specified=false");
    }
                @Test
    @Transactional
    public void getAllMessageFeedbacksByFeedbackContainsSomething() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where feedback contains DEFAULT_FEEDBACK
        defaultMessageFeedbackShouldBeFound("feedback.contains=" + DEFAULT_FEEDBACK);

        // Get all the messageFeedbackList where feedback contains UPDATED_FEEDBACK
        defaultMessageFeedbackShouldNotBeFound("feedback.contains=" + UPDATED_FEEDBACK);
    }

    @Test
    @Transactional
    public void getAllMessageFeedbacksByFeedbackNotContainsSomething() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where feedback does not contain DEFAULT_FEEDBACK
        defaultMessageFeedbackShouldNotBeFound("feedback.doesNotContain=" + DEFAULT_FEEDBACK);

        // Get all the messageFeedbackList where feedback does not contain UPDATED_FEEDBACK
        defaultMessageFeedbackShouldBeFound("feedback.doesNotContain=" + UPDATED_FEEDBACK);
    }


    @Test
    @Transactional
    public void getAllMessageFeedbacksByApplicantNameIsEqualToSomething() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where applicantName equals to DEFAULT_APPLICANT_NAME
        defaultMessageFeedbackShouldBeFound("applicantName.equals=" + DEFAULT_APPLICANT_NAME);

        // Get all the messageFeedbackList where applicantName equals to UPDATED_APPLICANT_NAME
        defaultMessageFeedbackShouldNotBeFound("applicantName.equals=" + UPDATED_APPLICANT_NAME);
    }

    @Test
    @Transactional
    public void getAllMessageFeedbacksByApplicantNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where applicantName not equals to DEFAULT_APPLICANT_NAME
        defaultMessageFeedbackShouldNotBeFound("applicantName.notEquals=" + DEFAULT_APPLICANT_NAME);

        // Get all the messageFeedbackList where applicantName not equals to UPDATED_APPLICANT_NAME
        defaultMessageFeedbackShouldBeFound("applicantName.notEquals=" + UPDATED_APPLICANT_NAME);
    }

    @Test
    @Transactional
    public void getAllMessageFeedbacksByApplicantNameIsInShouldWork() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where applicantName in DEFAULT_APPLICANT_NAME or UPDATED_APPLICANT_NAME
        defaultMessageFeedbackShouldBeFound("applicantName.in=" + DEFAULT_APPLICANT_NAME + "," + UPDATED_APPLICANT_NAME);

        // Get all the messageFeedbackList where applicantName equals to UPDATED_APPLICANT_NAME
        defaultMessageFeedbackShouldNotBeFound("applicantName.in=" + UPDATED_APPLICANT_NAME);
    }

    @Test
    @Transactional
    public void getAllMessageFeedbacksByApplicantNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where applicantName is not null
        defaultMessageFeedbackShouldBeFound("applicantName.specified=true");

        // Get all the messageFeedbackList where applicantName is null
        defaultMessageFeedbackShouldNotBeFound("applicantName.specified=false");
    }
                @Test
    @Transactional
    public void getAllMessageFeedbacksByApplicantNameContainsSomething() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where applicantName contains DEFAULT_APPLICANT_NAME
        defaultMessageFeedbackShouldBeFound("applicantName.contains=" + DEFAULT_APPLICANT_NAME);

        // Get all the messageFeedbackList where applicantName contains UPDATED_APPLICANT_NAME
        defaultMessageFeedbackShouldNotBeFound("applicantName.contains=" + UPDATED_APPLICANT_NAME);
    }

    @Test
    @Transactional
    public void getAllMessageFeedbacksByApplicantNameNotContainsSomething() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        // Get all the messageFeedbackList where applicantName does not contain DEFAULT_APPLICANT_NAME
        defaultMessageFeedbackShouldNotBeFound("applicantName.doesNotContain=" + DEFAULT_APPLICANT_NAME);

        // Get all the messageFeedbackList where applicantName does not contain UPDATED_APPLICANT_NAME
        defaultMessageFeedbackShouldBeFound("applicantName.doesNotContain=" + UPDATED_APPLICANT_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMessageFeedbackShouldBeFound(String filter) throws Exception {
        restMessageFeedbackMockMvc.perform(get("/api/message-feedbacks?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(messageFeedback.getId().intValue())))
            .andExpect(jsonPath("$.[*].systemId").value(hasItem(DEFAULT_SYSTEM_ID.intValue())))
            .andExpect(jsonPath("$.[*].centerId").value(hasItem(DEFAULT_CENTER_ID.intValue())))
            .andExpect(jsonPath("$.[*].systemServicesId").value(hasItem(DEFAULT_SYSTEM_SERVICES_ID.intValue())))
            .andExpect(jsonPath("$.[*].counter").value(hasItem(DEFAULT_COUNTER.intValue())))
            .andExpect(jsonPath("$.[*].trsId").value(hasItem(DEFAULT_TRS_ID.intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].feedback").value(hasItem(DEFAULT_FEEDBACK)))
            .andExpect(jsonPath("$.[*].applicantName").value(hasItem(DEFAULT_APPLICANT_NAME)));

        // Check, that the count call also returns 1
        restMessageFeedbackMockMvc.perform(get("/api/message-feedbacks/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMessageFeedbackShouldNotBeFound(String filter) throws Exception {
        restMessageFeedbackMockMvc.perform(get("/api/message-feedbacks?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMessageFeedbackMockMvc.perform(get("/api/message-feedbacks/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingMessageFeedback() throws Exception {
        // Get the messageFeedback
        restMessageFeedbackMockMvc.perform(get("/api/message-feedbacks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMessageFeedback() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        int databaseSizeBeforeUpdate = messageFeedbackRepository.findAll().size();

        // Update the messageFeedback
        MessageFeedback updatedMessageFeedback = messageFeedbackRepository.findById(messageFeedback.getId()).get();
        // Disconnect from session so that the updates on updatedMessageFeedback are not directly saved in db
        em.detach(updatedMessageFeedback);
        updatedMessageFeedback
            .systemId(UPDATED_SYSTEM_ID)
            .centerId(UPDATED_CENTER_ID)
            .systemServicesId(UPDATED_SYSTEM_SERVICES_ID)
            .counter(UPDATED_COUNTER)
            .trsId(UPDATED_TRS_ID)
            .userId(UPDATED_USER_ID)
            .message(UPDATED_MESSAGE)
            .status(UPDATED_STATUS)
            .feedback(UPDATED_FEEDBACK)
            .applicantName(UPDATED_APPLICANT_NAME);
        MessageFeedbackDTO messageFeedbackDTO = messageFeedbackMapper.toDto(updatedMessageFeedback);

        restMessageFeedbackMockMvc.perform(put("/api/message-feedbacks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(messageFeedbackDTO)))
            .andExpect(status().isOk());

        // Validate the MessageFeedback in the database
        List<MessageFeedback> messageFeedbackList = messageFeedbackRepository.findAll();
        assertThat(messageFeedbackList).hasSize(databaseSizeBeforeUpdate);
        MessageFeedback testMessageFeedback = messageFeedbackList.get(messageFeedbackList.size() - 1);
        assertThat(testMessageFeedback.getSystemId()).isEqualTo(UPDATED_SYSTEM_ID);
        assertThat(testMessageFeedback.getCenterId()).isEqualTo(UPDATED_CENTER_ID);
        assertThat(testMessageFeedback.getSystemServicesId()).isEqualTo(UPDATED_SYSTEM_SERVICES_ID);
        assertThat(testMessageFeedback.getCounter()).isEqualTo(UPDATED_COUNTER);
        assertThat(testMessageFeedback.getTrsId()).isEqualTo(UPDATED_TRS_ID);
        assertThat(testMessageFeedback.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testMessageFeedback.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testMessageFeedback.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMessageFeedback.getFeedback()).isEqualTo(UPDATED_FEEDBACK);
        assertThat(testMessageFeedback.getApplicantName()).isEqualTo(UPDATED_APPLICANT_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingMessageFeedback() throws Exception {
        int databaseSizeBeforeUpdate = messageFeedbackRepository.findAll().size();

        // Create the MessageFeedback
        MessageFeedbackDTO messageFeedbackDTO = messageFeedbackMapper.toDto(messageFeedback);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMessageFeedbackMockMvc.perform(put("/api/message-feedbacks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(messageFeedbackDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MessageFeedback in the database
        List<MessageFeedback> messageFeedbackList = messageFeedbackRepository.findAll();
        assertThat(messageFeedbackList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMessageFeedback() throws Exception {
        // Initialize the database
        messageFeedbackRepository.saveAndFlush(messageFeedback);

        int databaseSizeBeforeDelete = messageFeedbackRepository.findAll().size();

        // Delete the messageFeedback
        restMessageFeedbackMockMvc.perform(delete("/api/message-feedbacks/{id}", messageFeedback.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MessageFeedback> messageFeedbackList = messageFeedbackRepository.findAll();
        assertThat(messageFeedbackList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
