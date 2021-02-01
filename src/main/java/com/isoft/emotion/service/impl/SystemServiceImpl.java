package com.isoft.emotion.service.impl;

import com.isoft.emotion.service.SystemService;
import com.isoft.emotion.domain.System;
import com.isoft.emotion.repository.SystemRepository;
import com.isoft.emotion.service.dto.SystemDTO;
import com.isoft.emotion.service.mapper.SystemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link System}.
 */
@Service
@Transactional
public class SystemServiceImpl implements SystemService {

    private final Logger log = LoggerFactory.getLogger(SystemServiceImpl.class);

    private final SystemRepository systemRepository;

    private final SystemMapper systemMapper;

    public SystemServiceImpl(SystemRepository systemRepository, SystemMapper systemMapper) {
        this.systemRepository = systemRepository;
        this.systemMapper = systemMapper;
    }

    @Override
    public SystemDTO save(SystemDTO systemDTO) {
        log.debug("Request to save System : {}", systemDTO);
        System system = systemMapper.toEntity(systemDTO);
        system = systemRepository.save(system);
        return systemMapper.toDto(system);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SystemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Systems");
        return systemRepository.findAll(pageable)
            .map(systemMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<SystemDTO> findOne(Long id) {
        log.debug("Request to get System : {}", id);
        return systemRepository.findById(id)
            .map(systemMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete System : {}", id);
        systemRepository.deleteById(id);
    }
}
