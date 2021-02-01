package com.isoft.emotion.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.isoft.emotion.web.rest.TestUtil;

public class SystemServicesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SystemServicesDTO.class);
        SystemServicesDTO systemServicesDTO1 = new SystemServicesDTO();
        systemServicesDTO1.setId(1L);
        SystemServicesDTO systemServicesDTO2 = new SystemServicesDTO();
        assertThat(systemServicesDTO1).isNotEqualTo(systemServicesDTO2);
        systemServicesDTO2.setId(systemServicesDTO1.getId());
        assertThat(systemServicesDTO1).isEqualTo(systemServicesDTO2);
        systemServicesDTO2.setId(2L);
        assertThat(systemServicesDTO1).isNotEqualTo(systemServicesDTO2);
        systemServicesDTO1.setId(null);
        assertThat(systemServicesDTO1).isNotEqualTo(systemServicesDTO2);
    }
}
