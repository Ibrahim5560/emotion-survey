package com.isoft.emotion.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SystemMapperTest {

    private SystemMapper systemMapper;

    @BeforeEach
    public void setUp() {
        systemMapper = new SystemMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(systemMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(systemMapper.fromId(null)).isNull();
    }
}
