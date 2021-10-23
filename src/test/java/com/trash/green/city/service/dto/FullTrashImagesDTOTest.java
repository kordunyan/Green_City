package com.trash.green.city.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.trash.green.city.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FullTrashImagesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FullTrashImagesDTO.class);
        FullTrashImagesDTO fullTrashImagesDTO1 = new FullTrashImagesDTO();
        fullTrashImagesDTO1.setId(1L);
        FullTrashImagesDTO fullTrashImagesDTO2 = new FullTrashImagesDTO();
        assertThat(fullTrashImagesDTO1).isNotEqualTo(fullTrashImagesDTO2);
        fullTrashImagesDTO2.setId(fullTrashImagesDTO1.getId());
        assertThat(fullTrashImagesDTO1).isEqualTo(fullTrashImagesDTO2);
        fullTrashImagesDTO2.setId(2L);
        assertThat(fullTrashImagesDTO1).isNotEqualTo(fullTrashImagesDTO2);
        fullTrashImagesDTO1.setId(null);
        assertThat(fullTrashImagesDTO1).isNotEqualTo(fullTrashImagesDTO2);
    }
}
