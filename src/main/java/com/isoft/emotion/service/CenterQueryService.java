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

import com.isoft.emotion.domain.Center;
import com.isoft.emotion.domain.*; // for static metamodels
import com.isoft.emotion.repository.CenterRepository;
import com.isoft.emotion.service.dto.CenterCriteria;
import com.isoft.emotion.service.dto.CenterDTO;
import com.isoft.emotion.service.mapper.CenterMapper;

/**
 * Service for executing complex queries for {@link Center} entities in the database.
 * The main input is a {@link CenterCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CenterDTO} or a {@link Page} of {@link CenterDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CenterQueryService extends QueryService<Center> {

    private final Logger log = LoggerFactory.getLogger(CenterQueryService.class);

    private final CenterRepository centerRepository;

    private final CenterMapper centerMapper;

    public CenterQueryService(CenterRepository centerRepository, CenterMapper centerMapper) {
        this.centerRepository = centerRepository;
        this.centerMapper = centerMapper;
    }

    /**
     * Return a {@link List} of {@link CenterDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CenterDTO> findByCriteria(CenterCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Center> specification = createSpecification(criteria);
        return centerMapper.toDto(centerRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CenterDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CenterDTO> findByCriteria(CenterCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Center> specification = createSpecification(criteria);
        return centerRepository.findAll(specification, page)
            .map(centerMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CenterCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Center> specification = createSpecification(criteria);
        return centerRepository.count(specification);
    }

    /**
     * Function to convert {@link CenterCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Center> createSpecification(CenterCriteria criteria) {
        Specification<Center> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Center_.id));
            }
            if (criteria.getNameAr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNameAr(), Center_.nameAr));
            }
            if (criteria.getNameEn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNameEn(), Center_.nameEn));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), Center_.code));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStatus(), Center_.status));
            }
            if (criteria.getCenterMessagesId() != null) {
                specification = specification.and(buildSpecification(criteria.getCenterMessagesId(),
                    root -> root.join(Center_.centerMessages, JoinType.LEFT).get(Messages_.id)));
            }
        }
        return specification;
    }
}
