package com.isoft.emotion.service;

import com.isoft.emotion.service.dto.MessageFeedbackDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.isoft.emotion.domain.MessageFeedback}.
 */
public interface MessageFeedbackService {

    /**
     * Save a messageFeedback.
     *
     * @param messageFeedbackDTO the entity to save.
     * @return the persisted entity.
     */
    MessageFeedbackDTO save(MessageFeedbackDTO messageFeedbackDTO);

    /**
     * Get all the messageFeedbacks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MessageFeedbackDTO> findAll(Pageable pageable);


    /**
     * Get the "id" messageFeedback.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MessageFeedbackDTO> findOne(Long id);

    /**
     * Delete the "id" messageFeedback.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
