package com.trash.green.city.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.trash.green.city.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FullTrashImagesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FullTrashImages.class);
        FullTrashImages fullTrashImages1 = new FullTrashImages();
        fullTrashImages1.setId(1L);
        FullTrashImages fullTrashImages2 = new FullTrashImages();
        fullTrashImages2.setId(fullTrashImages1.getId());
        assertThat(fullTrashImages1).isEqualTo(fullTrashImages2);
        fullTrashImages2.setId(2L);
        assertThat(fullTrashImages1).isNotEqualTo(fullTrashImages2);
        fullTrashImages1.setId(null);
        assertThat(fullTrashImages1).isNotEqualTo(fullTrashImages2);
    }
}
