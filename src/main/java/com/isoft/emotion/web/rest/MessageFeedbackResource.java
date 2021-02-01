package com.isoft.emotion.web.rest;

import com.isoft.emotion.service.MessageFeedbackService;
import com.isoft.emotion.web.rest.errors.BadRequestAlertException;
import com.isoft.emotion.service.dto.MessageFeedbackDTO;
import com.isoft.emotion.service.dto.MessageFeedbackCriteria;
import com.isoft.emotion.service.MessageFeedbackQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.isoft.emotion.domain.MessageFeedback}.
 */
@RestController
@RequestMapping("/api")
public class MessageFeedbackResource {

    private final Logger log = LoggerFactory.getLogger(MessageFeedbackResource.class);

    private static final String ENTITY_NAME = "messageFeedback";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MessageFeedbackService messageFeedbackService;

    private final MessageFeedbackQueryService messageFeedbackQueryService;

    public MessageFeedbackResource(MessageFeedbackService messageFeedbackService, MessageFeedbackQueryService messageFeedbackQueryService) {
        this.messageFeedbackService = messageFeedbackService;
        this.messageFeedbackQueryService = messageFeedbackQueryService;
    }

    /**
     * {@code POST  /message-feedbacks} : Create a new messageFeedback.
     *
     * @param messageFeedbackDTO the messageFeedbackDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new messageFeedbackDTO, or with status {@code 400 (Bad Request)} if the messageFeedback has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/message-feedbacks")
    public ResponseEntity<MessageFeedbackDTO> createMessageFeedback(@RequestBody MessageFeedbackDTO messageFeedbackDTO) throws URISyntaxException {
        log.debug("REST request to save MessageFeedback : {}", messageFeedbackDTO);
        if (messageFeedbackDTO.getId() != null) {
            throw new BadRequestAlertException("A new messageFeedback cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MessageFeedbackDTO result = messageFeedbackService.save(messageFeedbackDTO);
        return ResponseEntity.created(new URI("/api/message-feedbacks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /message-feedbacks} : Updates an existing messageFeedback.
     *
     * @param messageFeedbackDTO the messageFeedbackDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated messageFeedbackDTO,
     * or with status {@code 400 (Bad Request)} if the messageFeedbackDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the messageFeedbackDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/message-feedbacks")
    public ResponseEntity<MessageFeedbackDTO> updateMessageFeedback(@RequestBody MessageFeedbackDTO messageFeedbackDTO) throws URISyntaxException {
        log.debug("REST request to update MessageFeedback : {}", messageFeedbackDTO);
        if (messageFeedbackDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MessageFeedbackDTO result = messageFeedbackService.save(messageFeedbackDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, messageFeedbackDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /message-feedbacks} : get all the messageFeedbacks.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of messageFeedbacks in body.
     */
    @GetMapping("/message-feedbacks")
    public ResponseEntity<List<MessageFeedbackDTO>> getAllMessageFeedbacks(MessageFeedbackCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MessageFeedbacks by criteria: {}", criteria);
        Page<MessageFeedbackDTO> page = messageFeedbackQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /message-feedbacks/count} : count all the messageFeedbacks.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/message-feedbacks/count")
    public ResponseEntity<Long> countMessageFeedbacks(MessageFeedbackCriteria criteria) {
        log.debug("REST request to count MessageFeedbacks by criteria: {}", criteria);
        return ResponseEntity.ok().body(messageFeedbackQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /message-feedbacks/:id} : get the "id" messageFeedback.
     *
     * @param id the id of the messageFeedbackDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the messageFeedbackDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/message-feedbacks/{id}")
    public ResponseEntity<MessageFeedbackDTO> getMessageFeedback(@PathVariable Long id) {
        log.debug("REST request to get MessageFeedback : {}", id);
        Optional<MessageFeedbackDTO> messageFeedbackDTO = messageFeedbackService.findOne(id);
        return ResponseUtil.wrapOrNotFound(messageFeedbackDTO);
    }

    /**
     * {@code DELETE  /message-feedbacks/:id} : delete the "id" messageFeedback.
     *
     * @param id the id of the messageFeedbackDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/message-feedbacks/{id}")
    public ResponseEntity<Void> deleteMessageFeedback(@PathVariable Long id) {
        log.debug("REST request to delete MessageFeedback : {}", id);
        messageFeedbackService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
