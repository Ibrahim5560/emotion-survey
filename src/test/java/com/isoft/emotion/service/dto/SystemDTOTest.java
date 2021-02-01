package com.isoft.emotion.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.isoft.emotion.web.rest.TestUtil;

public class SystemDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SystemDTO.class);
        SystemDTO systemDTO1 = new SystemDTO();
        systemDTO1.setId(1L);
        SystemDTO systemDTO2 = new SystemDTO();
        assertThat(systemDTO1).isNotEqualTo(systemDTO2);
        systemDTO2.setId(systemDTO1.getId());
        assertThat(systemDTO1).isEqualTo(systemDTO2);
        systemDTO2.setId(2L);
        assertThat(systemDTO1).isNotEqualTo(systemDTO2);
        systemDTO1.setId(null);
        assertThat(systemDTO1).isNotEqualTo(systemDTO2);
    }
}
