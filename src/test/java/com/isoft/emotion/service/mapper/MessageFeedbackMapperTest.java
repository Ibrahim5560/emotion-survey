package com.isoft.emotion.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MessageFeedbackMapperTest {

    private MessageFeedbackMapper messageFeedbackMapper;

    @BeforeEach
    public void setUp() {
        messageFeedbackMapper = new MessageFeedbackMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(messageFeedbackMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(messageFeedbackMapper.fromId(null)).isNull();
    }
}
