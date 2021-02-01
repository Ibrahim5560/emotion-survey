package com.isoft.emotion.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SystemServicesMapperTest {

    private SystemServicesMapper systemServicesMapper;

    @BeforeEach
    public void setUp() {
        systemServicesMapper = new SystemServicesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(systemServicesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(systemServicesMapper.fromId(null)).isNull();
    }
}
