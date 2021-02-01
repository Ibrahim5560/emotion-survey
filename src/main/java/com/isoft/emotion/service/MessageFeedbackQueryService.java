package com.isoft.emotion.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.isoft.emotion.domain.MessageFeedback;
import com.isoft.emotion.domain.*; // for static metamodels
import com.isoft.emotion.repository.MessageFeedbackRepository;
import com.isoft.emotion.service.dto.MessageFeedbackCriteria;
import com.isoft.emotion.service.dto.MessageFeedbackDTO;
import com.isoft.emotion.service.mapper.MessageFeedbackMapper;

/**
 * Service for executing complex queries for {@link MessageFeedback} entities in the database.
 * The main input is a {@link MessageFeedbackCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MessageFeedbackDTO} or a {@link Page} of {@link MessageFeedbackDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MessageFeedbackQueryService extends QueryService<MessageFeedback> {

    private final Logger log = LoggerFactory.getLogger(MessageFeedbackQueryService.class);

    private final MessageFeedbackRepository messageFeedbackRepository;

    private final MessageFeedbackMapper messageFeedbackMapper;

    public MessageFeedbackQueryService(MessageFeedbackRepository messageFeedbackRepository, MessageFeedbackMapper messageFeedbackMapper) {
        this.messageFeedbackRepository = messageFeedbackRepository;
        this.messageFeedbackMapper = messageFeedbackMapper;
    }

    /**
     * Return a {@link List} of {@link MessageFeedbackDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MessageFeedbackDTO> findByCriteria(MessageFeedbackCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MessageFeedback> specification = createSpecification(criteria);
        return messageFeedbackMapper.toDto(messageFeedbackRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MessageFeedbackDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MessageFeedbackDTO> findByCriteria(MessageFeedbackCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MessageFeedback> specification = createSpecification(criteria);
        return messageFeedbackRepository.findAll(specification, page)
            .map(messageFeedbackMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MessageFeedbackCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MessageFeedback> specification = createSpecification(criteria);
        return messageFeedbackRepository.count(specification);
    }

    /**
     * Function to convert {@link MessageFeedbackCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MessageFeedback> createSpecification(MessageFeedbackCriteria criteria) {
        Specification<MessageFeedback> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MessageFeedback_.id));
            }
            if (criteria.getSystemId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSystemId(), MessageFeedback_.systemId));
            }
            if (criteria.getCenterId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCenterId(), MessageFeedback_.centerId));
            }
            if (criteria.getSystemServicesId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSystemServicesId(), MessageFeedback_.systemServicesId));
            }
            if (criteria.getCounter() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCounter(), MessageFeedback_.counter));
            }
            if (criteria.getTrsId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTrsId(), MessageFeedback_.trsId));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUserId(), MessageFeedback_.userId));
            }
            if (criteria.getMessage() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMessage(), MessageFeedback_.message));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStatus(), MessageFeedback_.status));
            }
            if (criteria.getFeedback() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFeedback(), MessageFeedback_.feedback));
            }
            if (criteria.getApplicantName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getApplicantName(), MessageFeedback_.applicantName));
            }
        }
        return specification;
    }
}
