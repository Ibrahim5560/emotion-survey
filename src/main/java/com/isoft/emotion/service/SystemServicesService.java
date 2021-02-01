package com.isoft.emotion.service;

import com.isoft.emotion.service.dto.SystemServicesDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.isoft.emotion.domain.SystemServices}.
 */
public interface SystemServicesService {

    /**
     * Save a systemServices.
     *
     * @param systemServicesDTO the entity to save.
     * @return the persisted entity.
     */
    SystemServicesDTO save(SystemServicesDTO systemServicesDTO);

    /**
     * Get all the systemServices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SystemServicesDTO> findAll(Pageable pageable);


    /**
     * Get the "id" systemServices.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SystemServicesDTO> findOne(Long id);

    /**
     * Delete the "id" systemServices.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
