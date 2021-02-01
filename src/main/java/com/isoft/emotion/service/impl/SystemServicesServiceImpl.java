package com.isoft.emotion.service.impl;

import com.isoft.emotion.service.SystemServicesService;
import com.isoft.emotion.domain.SystemServices;
import com.isoft.emotion.repository.SystemServicesRepository;
import com.isoft.emotion.service.dto.SystemServicesDTO;
import com.isoft.emotion.service.mapper.SystemServicesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SystemServices}.
 */
@Service
@Transactional
public class SystemServicesServiceImpl implements SystemServicesService {

    private final Logger log = LoggerFactory.getLogger(SystemServicesServiceImpl.class);

    private final SystemServicesRepository systemServicesRepository;

    private final SystemServicesMapper systemServicesMapper;

    public SystemServicesServiceImpl(SystemServicesRepository systemServicesRepository, SystemServicesMapper systemServicesMapper) {
        this.systemServicesRepository = systemServicesRepository;
        this.systemServicesMapper = systemServicesMapper;
    }

    @Override
    public SystemServicesDTO save(SystemServicesDTO systemServicesDTO) {
        log.debug("Request to save SystemServices : {}", systemServicesDTO);
        SystemServices systemServices = systemServicesMapper.toEntity(systemServicesDTO);
        systemServices = systemServicesRepository.save(systemServices);
        return systemServicesMapper.toDto(systemServices);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SystemServicesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SystemServices");
        return systemServicesRepository.findAll(pageable)
            .map(systemServicesMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<SystemServicesDTO> findOne(Long id) {
        log.debug("Request to get SystemServices : {}", id);
        return systemServicesRepository.findById(id)
            .map(systemServicesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SystemServices : {}", id);
        systemServicesRepository.deleteById(id);
    }
}
