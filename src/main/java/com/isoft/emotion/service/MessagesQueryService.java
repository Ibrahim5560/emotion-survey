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

import com.isoft.emotion.domain.Messages;
import com.isoft.emotion.domain.*; // for static metamodels
import com.isoft.emotion.repository.MessagesRepository;
import com.isoft.emotion.service.dto.MessagesCriteria;
import com.isoft.emotion.service.dto.MessagesDTO;
import com.isoft.emotion.service.mapper.MessagesMapper;

/**
 * Service for executing complex queries for {@link Messages} entities in the database.
 * The main input is a {@link MessagesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MessagesDTO} or a {@link Page} of {@link MessagesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MessagesQueryService extends QueryService<Messages> {

    private final Logger log = LoggerFactory.getLogger(MessagesQueryService.class);

    private final MessagesRepository messagesRepository;

    private final MessagesMapper messagesMapper;

    public MessagesQueryService(MessagesRepository messagesRepository, MessagesMapper messagesMapper) {
        this.messagesRepository = messagesRepository;
        this.messagesMapper = messagesMapper;
    }

    /**
     * Return a {@link List} of {@link MessagesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MessagesDTO> findByCriteria(MessagesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Messages> specification = createSpecification(criteria);
        return messagesMapper.toDto(messagesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MessagesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MessagesDTO> findByCriteria(MessagesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Messages> specification = createSpecification(criteria);
        return messagesRepository.findAll(specification, page)
            .map(messagesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MessagesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Messages> specification = createSpecification(criteria);
        return messagesRepository.count(specification);
    }

    /**
     * Function to convert {@link MessagesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Messages> createSpecification(MessagesCriteria criteria) {
        Specification<Messages> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Messages_.id));
            }
            if (criteria.getCounter() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCounter(), Messages_.counter));
            }
            if (criteria.getTrsId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTrsId(), Messages_.trsId));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUserId(), Messages_.userId));
            }
            if (criteria.getMessage() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMessage(), Messages_.message));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStatus(), Messages_.status));
            }
            if (criteria.getApplicantName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getApplicantName(), Messages_.applicantName));
            }
            if (criteria.getCenterId() != null) {
                specification = specification.and(buildSpecification(criteria.getCenterId(),
                    root -> root.join(Messages_.center, JoinType.LEFT).get(Center_.id)));
            }
            if (criteria.getSystemId() != null) {
                specification = specification.and(buildSpecification(criteria.getSystemId(),
                    root -> root.join(Messages_.system, JoinType.LEFT).get(System_.id)));
            }
            if (criteria.getSystemServicesId() != null) {
                specification = specification.and(buildSpecification(criteria.getSystemServicesId(),
                    root -> root.join(Messages_.systemServices, JoinType.LEFT).get(SystemServices_.id)));
            }
            if (criteria.getUsersId() != null) {
                specification = specification.and(buildSpecification(criteria.getUsersId(),
                    root -> root.join(Messages_.users, JoinType.LEFT).get(Users_.id)));
            }
        }
        return specification;
    }
}
