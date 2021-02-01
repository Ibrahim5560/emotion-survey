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

import com.isoft.emotion.domain.System;
import com.isoft.emotion.domain.*; // for static metamodels
import com.isoft.emotion.repository.SystemRepository;
import com.isoft.emotion.service.dto.SystemCriteria;
import com.isoft.emotion.service.dto.SystemDTO;
import com.isoft.emotion.service.mapper.SystemMapper;

/**
 * Service for executing complex queries for {@link System} entities in the database.
 * The main input is a {@link SystemCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SystemDTO} or a {@link Page} of {@link SystemDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SystemQueryService extends QueryService<System> {

    private final Logger log = LoggerFactory.getLogger(SystemQueryService.class);

    private final SystemRepository systemRepository;

    private final SystemMapper systemMapper;

    public SystemQueryService(SystemRepository systemRepository, SystemMapper systemMapper) {
        this.systemRepository = systemRepository;
        this.systemMapper = systemMapper;
    }

    /**
     * Return a {@link List} of {@link SystemDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SystemDTO> findByCriteria(SystemCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<System> specification = createSpecification(criteria);
        return systemMapper.toDto(systemRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SystemDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SystemDTO> findByCriteria(SystemCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<System> specification = createSpecification(criteria);
        return systemRepository.findAll(specification, page)
            .map(systemMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SystemCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<System> specification = createSpecification(criteria);
        return systemRepository.count(specification);
    }

    /**
     * Function to convert {@link SystemCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<System> createSpecification(SystemCriteria criteria) {
        Specification<System> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), System_.id));
            }
            if (criteria.getNameAr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNameAr(), System_.nameAr));
            }
            if (criteria.getNameEn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNameEn(), System_.nameEn));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), System_.code));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStatus(), System_.status));
            }
            if (criteria.getSystemMessagesId() != null) {
                specification = specification.and(buildSpecification(criteria.getSystemMessagesId(),
                    root -> root.join(System_.systemMessages, JoinType.LEFT).get(Messages_.id)));
            }
            if (criteria.getSystemServicesId() != null) {
                specification = specification.and(buildSpecification(criteria.getSystemServicesId(),
                    root -> root.join(System_.systemServices, JoinType.LEFT).get(SystemServices_.id)));
            }
        }
        return specification;
    }
}
