package com.isoft.emotion.web.rest;

import com.isoft.emotion.service.SystemServicesService;
import com.isoft.emotion.web.rest.errors.BadRequestAlertException;
import com.isoft.emotion.service.dto.SystemServicesDTO;
import com.isoft.emotion.service.dto.SystemServicesCriteria;
import com.isoft.emotion.service.SystemServicesQueryService;

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
 * REST controller for managing {@link com.isoft.emotion.domain.SystemServices}.
 */
@RestController
@RequestMapping("/api")
public class SystemServicesResource {

    private final Logger log = LoggerFactory.getLogger(SystemServicesResource.class);

    private static final String ENTITY_NAME = "systemServices";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SystemServicesService systemServicesService;

    private final SystemServicesQueryService systemServicesQueryService;

    public SystemServicesResource(SystemServicesService systemServicesService, SystemServicesQueryService systemServicesQueryService) {
        this.systemServicesService = systemServicesService;
        this.systemServicesQueryService = systemServicesQueryService;
    }

    /**
     * {@code POST  /system-services} : Create a new systemServices.
     *
     * @param systemServicesDTO the systemServicesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new systemServicesDTO, or with status {@code 400 (Bad Request)} if the systemServices has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/system-services")
    public ResponseEntity<SystemServicesDTO> createSystemServices(@RequestBody SystemServicesDTO systemServicesDTO) throws URISyntaxException {
        log.debug("REST request to save SystemServices : {}", systemServicesDTO);
        if (systemServicesDTO.getId() != null) {
            throw new BadRequestAlertException("A new systemServices cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SystemServicesDTO result = systemServicesService.save(systemServicesDTO);
        return ResponseEntity.created(new URI("/api/system-services/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /system-services} : Updates an existing systemServices.
     *
     * @param systemServicesDTO the systemServicesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated systemServicesDTO,
     * or with status {@code 400 (Bad Request)} if the systemServicesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the systemServicesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/system-services")
    public ResponseEntity<SystemServicesDTO> updateSystemServices(@RequestBody SystemServicesDTO systemServicesDTO) throws URISyntaxException {
        log.debug("REST request to update SystemServices : {}", systemServicesDTO);
        if (systemServicesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SystemServicesDTO result = systemServicesService.save(systemServicesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, systemServicesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /system-services} : get all the systemServices.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of systemServices in body.
     */
    @GetMapping("/system-services")
    public ResponseEntity<List<SystemServicesDTO>> getAllSystemServices(SystemServicesCriteria criteria, Pageable pageable) {
        log.debug("REST request to get SystemServices by criteria: {}", criteria);
        Page<SystemServicesDTO> page = systemServicesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /system-services/count} : count all the systemServices.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/system-services/count")
    public ResponseEntity<Long> countSystemServices(SystemServicesCriteria criteria) {
        log.debug("REST request to count SystemServices by criteria: {}", criteria);
        return ResponseEntity.ok().body(systemServicesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /system-services/:id} : get the "id" systemServices.
     *
     * @param id the id of the systemServicesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the systemServicesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/system-services/{id}")
    public ResponseEntity<SystemServicesDTO> getSystemServices(@PathVariable Long id) {
        log.debug("REST request to get SystemServices : {}", id);
        Optional<SystemServicesDTO> systemServicesDTO = systemServicesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(systemServicesDTO);
    }

    /**
     * {@code DELETE  /system-services/:id} : delete the "id" systemServices.
     *
     * @param id the id of the systemServicesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/system-services/{id}")
    public ResponseEntity<Void> deleteSystemServices(@PathVariable Long id) {
        log.debug("REST request to delete SystemServices : {}", id);
        systemServicesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
