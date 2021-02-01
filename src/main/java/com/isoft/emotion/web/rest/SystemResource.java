package com.isoft.emotion.web.rest;

import com.isoft.emotion.service.SystemService;
import com.isoft.emotion.web.rest.errors.BadRequestAlertException;
import com.isoft.emotion.service.dto.SystemDTO;
import com.isoft.emotion.service.dto.SystemCriteria;
import com.isoft.emotion.service.SystemQueryService;

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
 * REST controller for managing {@link com.isoft.emotion.domain.System}.
 */
@RestController
@RequestMapping("/api")
public class SystemResource {

    private final Logger log = LoggerFactory.getLogger(SystemResource.class);

    private static final String ENTITY_NAME = "system";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SystemService systemService;

    private final SystemQueryService systemQueryService;

    public SystemResource(SystemService systemService, SystemQueryService systemQueryService) {
        this.systemService = systemService;
        this.systemQueryService = systemQueryService;
    }

    /**
     * {@code POST  /systems} : Create a new system.
     *
     * @param systemDTO the systemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new systemDTO, or with status {@code 400 (Bad Request)} if the system has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/systems")
    public ResponseEntity<SystemDTO> createSystem(@RequestBody SystemDTO systemDTO) throws URISyntaxException {
        log.debug("REST request to save System : {}", systemDTO);
        if (systemDTO.getId() != null) {
            throw new BadRequestAlertException("A new system cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SystemDTO result = systemService.save(systemDTO);
        return ResponseEntity.created(new URI("/api/systems/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /systems} : Updates an existing system.
     *
     * @param systemDTO the systemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated systemDTO,
     * or with status {@code 400 (Bad Request)} if the systemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the systemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/systems")
    public ResponseEntity<SystemDTO> updateSystem(@RequestBody SystemDTO systemDTO) throws URISyntaxException {
        log.debug("REST request to update System : {}", systemDTO);
        if (systemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SystemDTO result = systemService.save(systemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, systemDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /systems} : get all the systems.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of systems in body.
     */
    @GetMapping("/systems")
    public ResponseEntity<List<SystemDTO>> getAllSystems(SystemCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Systems by criteria: {}", criteria);
        Page<SystemDTO> page = systemQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /systems/count} : count all the systems.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/systems/count")
    public ResponseEntity<Long> countSystems(SystemCriteria criteria) {
        log.debug("REST request to count Systems by criteria: {}", criteria);
        return ResponseEntity.ok().body(systemQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /systems/:id} : get the "id" system.
     *
     * @param id the id of the systemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the systemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/systems/{id}")
    public ResponseEntity<SystemDTO> getSystem(@PathVariable Long id) {
        log.debug("REST request to get System : {}", id);
        Optional<SystemDTO> systemDTO = systemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(systemDTO);
    }

    /**
     * {@code DELETE  /systems/:id} : delete the "id" system.
     *
     * @param id the id of the systemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/systems/{id}")
    public ResponseEntity<Void> deleteSystem(@PathVariable Long id) {
        log.debug("REST request to delete System : {}", id);
        systemService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
