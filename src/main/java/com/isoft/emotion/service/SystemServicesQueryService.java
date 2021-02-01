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

import com.isoft.emotion.domain.SystemServices;
import com.isoft.emotion.domain.*; // for static metamodels
import com.isoft.emotion.repository.SystemServicesRepository;
import com.isoft.emotion.service.dto.SystemServicesCriteria;
import com.isoft.emotion.service.dto.SystemServicesDTO;
import com.isoft.emotion.service.mapper.SystemServicesMapper;

/**
 * Service for executing complex queries for {@link SystemServices} entities in the database.
 * The main input is a {@link SystemServicesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SystemServicesDTO} or a {@link Page} of {@link SystemServicesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SystemServicesQueryService extends QueryService<SystemServices> {

    private final Logger log = LoggerFactory.getLogger(SystemServicesQueryService.class);

    private final SystemServicesRepository systemServicesRepository;

    private final SystemServicesMapper systemServicesMapper;

    public SystemServicesQueryService(SystemServicesRepository systemServicesRepository, SystemServicesMapper systemServicesMapper) {
        this.systemServicesRepository = systemServicesRepository;
        this.systemServicesMapper = systemServicesMapper;
    }

    /**
     * Return a {@link List} of {@link SystemServicesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SystemServicesDTO> findByCriteria(SystemServicesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SystemServices> specification = createSpecification(criteria);
        return systemServicesMapper.toDto(systemServicesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SystemServicesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SystemServicesDTO> findByCriteria(SystemServicesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SystemServices> specification = createSpecification(criteria);
        return systemServicesRepository.findAll(specification, page)
            .map(systemServicesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SystemServicesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SystemServices> specification = createSpecification(criteria);
        return systemServicesRepository.count(specification);
    }

    /**
     * Function to convert {@link SystemServicesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SystemServices> createSpecification(SystemServicesCriteria criteria) {
        Specification<SystemServices> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SystemServices_.id));
            }
            if (criteria.getNameAr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNameAr(), SystemServices_.nameAr));
            }
            if (criteria.getNameEn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNameEn(), SystemServices_.nameEn));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), SystemServices_.code));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStatus(), SystemServices_.status));
            }
            if (criteria.getSystemServicesMessagesId() != null) {
                specification = specification.and(buildSpecification(criteria.getSystemServicesMessagesId(),
                    root -> root.join(SystemServices_.systemServicesMessages, JoinType.LEFT).get(Messages_.id)));
            }
            if (criteria.getSystemId() != null) {
                specification = specification.and(buildSpecification(criteria.getSystemId(),
                    root -> root.join(SystemServices_.system, JoinType.LEFT).get(System_.id)));
            }
        }
        return specification;
    }
}
