package com.isoft.emotion.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.isoft.emotion.web.rest.TestUtil;

public class MessagesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Messages.class);
        Messages messages1 = new Messages();
        messages1.setId(1L);
        Messages messages2 = new Messages();
        messages2.setId(messages1.getId());
        assertThat(messages1).isEqualTo(messages2);
        messages2.setId(2L);
        assertThat(messages1).isNotEqualTo(messages2);
        messages1.setId(null);
        assertThat(messages1).isNotEqualTo(messages2);
    }
}
