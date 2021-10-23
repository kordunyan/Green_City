package com.trash.green.city.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.trash.green.city.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmptyTrashImagesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmptyTrashImages.class);
        EmptyTrashImages emptyTrashImages1 = new EmptyTrashImages();
        emptyTrashImages1.setId(1L);
        EmptyTrashImages emptyTrashImages2 = new EmptyTrashImages();
        emptyTrashImages2.setId(emptyTrashImages1.getId());
        assertThat(emptyTrashImages1).isEqualTo(emptyTrashImages2);
        emptyTrashImages2.setId(2L);
        assertThat(emptyTrashImages1).isNotEqualTo(emptyTrashImages2);
        emptyTrashImages1.setId(null);
        assertThat(emptyTrashImages1).isNotEqualTo(emptyTrashImages2);
    }
}
