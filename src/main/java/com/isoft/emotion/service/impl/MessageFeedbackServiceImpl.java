package com.isoft.emotion.service.impl;

import com.isoft.emotion.service.MessageFeedbackService;
import com.isoft.emotion.domain.MessageFeedback;
import com.isoft.emotion.repository.MessageFeedbackRepository;
import com.isoft.emotion.service.dto.MessageFeedbackDTO;
import com.isoft.emotion.service.mapper.MessageFeedbackMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MessageFeedback}.
 */
@Service
@Transactional
public class MessageFeedbackServiceImpl implements MessageFeedbackService {

    private final Logger log = LoggerFactory.getLogger(MessageFeedbackServiceImpl.class);

    private final MessageFeedbackRepository messageFeedbackRepository;

    private final MessageFeedbackMapper messageFeedbackMapper;

    public MessageFeedbackServiceImpl(MessageFeedbackRepository messageFeedbackRepository, MessageFeedbackMapper messageFeedbackMapper) {
        this.messageFeedbackRepository = messageFeedbackRepository;
        this.messageFeedbackMapper = messageFeedbackMapper;
    }

    @Override
    public MessageFeedbackDTO save(MessageFeedbackDTO messageFeedbackDTO) {
        log.debug("Request to save MessageFeedback : {}", messageFeedbackDTO);
        MessageFeedback messageFeedback = messageFeedbackMapper.toEntity(messageFeedbackDTO);
        messageFeedback = messageFeedbackRepository.save(messageFeedback);
        return messageFeedbackMapper.toDto(messageFeedback);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MessageFeedbackDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MessageFeedbacks");
        return messageFeedbackRepository.findAll(pageable)
            .map(messageFeedbackMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<MessageFeedbackDTO> findOne(Long id) {
        log.debug("Request to get MessageFeedback : {}", id);
        return messageFeedbackRepository.findById(id)
            .map(messageFeedbackMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MessageFeedback : {}", id);
        messageFeedbackRepository.deleteById(id);
    }
}
