package com.isoft.emotion.service.impl;

import com.isoft.emotion.service.MessagesService;
import com.isoft.emotion.domain.Messages;
import com.isoft.emotion.repository.MessagesRepository;
import com.isoft.emotion.service.dto.MessagesDTO;
import com.isoft.emotion.service.mapper.MessagesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Messages}.
 */
@Service
@Transactional
public class MessagesServiceImpl implements MessagesService {

    private final Logger log = LoggerFactory.getLogger(MessagesServiceImpl.class);

    private final MessagesRepository messagesRepository;

    private final MessagesMapper messagesMapper;

    public MessagesServiceImpl(MessagesRepository messagesRepository, MessagesMapper messagesMapper) {
        this.messagesRepository = messagesRepository;
        this.messagesMapper = messagesMapper;
    }

    @Override
    public MessagesDTO save(MessagesDTO messagesDTO) {
        log.debug("Request to save Messages : {}", messagesDTO);
        Messages messages = messagesMapper.toEntity(messagesDTO);
        messages = messagesRepository.save(messages);
        return messagesMapper.toDto(messages);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MessagesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Messages");
        return messagesRepository.findAll(pageable)
            .map(messagesMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<MessagesDTO> findOne(Long id) {
        log.debug("Request to get Messages : {}", id);
        return messagesRepository.findById(id)
            .map(messagesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Messages : {}", id);
        messagesRepository.deleteById(id);
    }
}
