package com.isoft.emotion.web.rest;

import com.isoft.emotion.service.MessagesService;
import com.isoft.emotion.web.rest.errors.BadRequestAlertException;
import com.isoft.emotion.service.dto.MessagesDTO;
import com.isoft.emotion.service.dto.MessagesCriteria;
import com.isoft.emotion.service.MessagesQueryService;

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
 * REST controller for managing {@link com.isoft.emotion.domain.Messages}.
 */
@RestController
@RequestMapping("/api")
public class MessagesResource {

    private final Logger log = LoggerFactory.getLogger(MessagesResource.class);

    private static final String ENTITY_NAME = "messages";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MessagesService messagesService;

    private final MessagesQueryService messagesQueryService;

    public MessagesResource(MessagesService messagesService, MessagesQueryService messagesQueryService) {
        this.messagesService = messagesService;
        this.messagesQueryService = messagesQueryService;
    }

    /**
     * {@code POST  /messages} : Create a new messages.
     *
     * @param messagesDTO the messagesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new messagesDTO, or with status {@code 400 (Bad Request)} if the messages has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/messages")
    public ResponseEntity<MessagesDTO> createMessages(@RequestBody MessagesDTO messagesDTO) throws URISyntaxException {
        log.debug("REST request to save Messages : {}", messagesDTO);
        if (messagesDTO.getId() != null) {
            throw new BadRequestAlertException("A new messages cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MessagesDTO result = messagesService.save(messagesDTO);
        return ResponseEntity.created(new URI("/api/messages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /messages} : Updates an existing messages.
     *
     * @param messagesDTO the messagesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated messagesDTO,
     * or with status {@code 400 (Bad Request)} if the messagesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the messagesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/messages")
    public ResponseEntity<MessagesDTO> updateMessages(@RequestBody MessagesDTO messagesDTO) throws URISyntaxException {
        log.debug("REST request to update Messages : {}", messagesDTO);
        if (messagesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MessagesDTO result = messagesService.save(messagesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, messagesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /messages} : get all the messages.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of messages in body.
     */
    @GetMapping("/messages")
    public ResponseEntity<List<MessagesDTO>> getAllMessages(MessagesCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Messages by criteria: {}", criteria);
        Page<MessagesDTO> page = messagesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /messages/count} : count all the messages.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/messages/count")
    public ResponseEntity<Long> countMessages(MessagesCriteria criteria) {
        log.debug("REST request to count Messages by criteria: {}", criteria);
        return ResponseEntity.ok().body(messagesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /messages/:id} : get the "id" messages.
     *
     * @param id the id of the messagesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the messagesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/messages/{id}")
    public ResponseEntity<MessagesDTO> getMessages(@PathVariable Long id) {
        log.debug("REST request to get Messages : {}", id);
        Optional<MessagesDTO> messagesDTO = messagesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(messagesDTO);
    }

    /**
     * {@code DELETE  /messages/:id} : delete the "id" messages.
     *
     * @param id the id of the messagesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/messages/{id}")
    public ResponseEntity<Void> deleteMessages(@PathVariable Long id) {
        log.debug("REST request to delete Messages : {}", id);
        messagesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
