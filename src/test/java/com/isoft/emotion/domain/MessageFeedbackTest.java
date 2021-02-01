package com.isoft.emotion.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.isoft.emotion.web.rest.TestUtil;

public class MessageFeedbackTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MessageFeedback.class);
        MessageFeedback messageFeedback1 = new MessageFeedback();
        messageFeedback1.setId(1L);
        MessageFeedback messageFeedback2 = new MessageFeedback();
        messageFeedback2.setId(messageFeedback1.getId());
        assertThat(messageFeedback1).isEqualTo(messageFeedback2);
        messageFeedback2.setId(2L);
        assertThat(messageFeedback1).isNotEqualTo(messageFeedback2);
        messageFeedback1.setId(null);
        assertThat(messageFeedback1).isNotEqualTo(messageFeedback2);
    }
}
