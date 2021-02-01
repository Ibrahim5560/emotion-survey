package com.isoft.emotion.repository;

import com.isoft.emotion.domain.MessageFeedback;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MessageFeedback entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MessageFeedbackRepository extends JpaRepository<MessageFeedback, Long>, JpaSpecificationExecutor<MessageFeedback> {
}
