package com.trash.green.city.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.trash.green.city.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmptyTrashImagesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmptyTrashImagesDTO.class);
        EmptyTrashImagesDTO emptyTrashImagesDTO1 = new EmptyTrashImagesDTO();
        emptyTrashImagesDTO1.setId(1L);
        EmptyTrashImagesDTO emptyTrashImagesDTO2 = new EmptyTrashImagesDTO();
        assertThat(emptyTrashImagesDTO1).isNotEqualTo(emptyTrashImagesDTO2);
        emptyTrashImagesDTO2.setId(emptyTrashImagesDTO1.getId());
        assertThat(emptyTrashImagesDTO1).isEqualTo(emptyTrashImagesDTO2);
        emptyTrashImagesDTO2.setId(2L);
        assertThat(emptyTrashImagesDTO1).isNotEqualTo(emptyTrashImagesDTO2);
        emptyTrashImagesDTO1.setId(null);
        assertThat(emptyTrashImagesDTO1).isNotEqualTo(emptyTrashImagesDTO2);
    }
}
