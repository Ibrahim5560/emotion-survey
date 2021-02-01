package com.isoft.emotion.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.isoft.emotion.web.rest.TestUtil;

public class MessageFeedbackDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MessageFeedbackDTO.class);
        MessageFeedbackDTO messageFeedbackDTO1 = new MessageFeedbackDTO();
        messageFeedbackDTO1.setId(1L);
        MessageFeedbackDTO messageFeedbackDTO2 = new MessageFeedbackDTO();
        assertThat(messageFeedbackDTO1).isNotEqualTo(messageFeedbackDTO2);
        messageFeedbackDTO2.setId(messageFeedbackDTO1.getId());
        assertThat(messageFeedbackDTO1).isEqualTo(messageFeedbackDTO2);
        messageFeedbackDTO2.setId(2L);
        assertThat(messageFeedbackDTO1).isNotEqualTo(messageFeedbackDTO2);
        messageFeedbackDTO1.setId(null);
        assertThat(messageFeedbackDTO1).isNotEqualTo(messageFeedbackDTO2);
    }
}
