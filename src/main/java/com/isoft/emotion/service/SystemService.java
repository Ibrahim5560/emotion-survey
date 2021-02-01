package com.isoft.emotion.service;

import com.isoft.emotion.service.dto.SystemDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.isoft.emotion.domain.System}.
 */
public interface SystemService {

    /**
     * Save a system.
     *
     * @param systemDTO the entity to save.
     * @return the persisted entity.
     */
    SystemDTO save(SystemDTO systemDTO);

    /**
     * Get all the systems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SystemDTO> findAll(Pageable pageable);


    /**
     * Get the "id" system.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SystemDTO> findOne(Long id);

    /**
     * Delete the "id" system.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
