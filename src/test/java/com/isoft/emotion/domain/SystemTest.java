package com.isoft.emotion.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.isoft.emotion.web.rest.TestUtil;

public class SystemTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(System.class);
        System system1 = new System();
        system1.setId(1L);
        System system2 = new System();
        system2.setId(system1.getId());
        assertThat(system1).isEqualTo(system2);
        system2.setId(2L);
        assertThat(system1).isNotEqualTo(system2);
        system1.setId(null);
        assertThat(system1).isNotEqualTo(system2);
    }
}
