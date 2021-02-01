package com.isoft.emotion.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.isoft.emotion.web.rest.TestUtil;

public class MessagesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MessagesDTO.class);
        MessagesDTO messagesDTO1 = new MessagesDTO();
        messagesDTO1.setId(1L);
        MessagesDTO messagesDTO2 = new MessagesDTO();
        assertThat(messagesDTO1).isNotEqualTo(messagesDTO2);
        messagesDTO2.setId(messagesDTO1.getId());
        assertThat(messagesDTO1).isEqualTo(messagesDTO2);
        messagesDTO2.setId(2L);
        assertThat(messagesDTO1).isNotEqualTo(messagesDTO2);
        messagesDTO1.setId(null);
        assertThat(messagesDTO1).isNotEqualTo(messagesDTO2);
    }
}
