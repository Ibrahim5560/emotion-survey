package com.isoft.emotion.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.isoft.emotion.web.rest.TestUtil;

public class SystemServicesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SystemServices.class);
        SystemServices systemServices1 = new SystemServices();
        systemServices1.setId(1L);
        SystemServices systemServices2 = new SystemServices();
        systemServices2.setId(systemServices1.getId());
        assertThat(systemServices1).isEqualTo(systemServices2);
        systemServices2.setId(2L);
        assertThat(systemServices1).isNotEqualTo(systemServices2);
        systemServices1.setId(null);
        assertThat(systemServices1).isNotEqualTo(systemServices2);
    }
}
