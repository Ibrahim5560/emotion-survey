package com.isoft.emotion.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MessagesMapperTest {

    private MessagesMapper messagesMapper;

    @BeforeEach
    public void setUp() {
        messagesMapper = new MessagesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(messagesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(messagesMapper.fromId(null)).isNull();
    }
}
