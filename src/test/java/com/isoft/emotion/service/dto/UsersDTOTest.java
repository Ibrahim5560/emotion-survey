package com.isoft.emotion.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.isoft.emotion.web.rest.TestUtil;

public class UsersDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UsersDTO.class);
        UsersDTO usersDTO1 = new UsersDTO();
        usersDTO1.setId(1L);
        UsersDTO usersDTO2 = new UsersDTO();
        assertThat(usersDTO1).isNotEqualTo(usersDTO2);
        usersDTO2.setId(usersDTO1.getId());
        assertThat(usersDTO1).isEqualTo(usersDTO2);
        usersDTO2.setId(2L);
        assertThat(usersDTO1).isNotEqualTo(usersDTO2);
        usersDTO1.setId(null);
        assertThat(usersDTO1).isNotEqualTo(usersDTO2);
    }
}
