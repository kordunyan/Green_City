package com.trash.green.city.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.trash.green.city.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OsbbDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OsbbDTO.class);
        OsbbDTO osbbDTO1 = new OsbbDTO();
        osbbDTO1.setId(1L);
        OsbbDTO osbbDTO2 = new OsbbDTO();
        assertThat(osbbDTO1).isNotEqualTo(osbbDTO2);
        osbbDTO2.setId(osbbDTO1.getId());
        assertThat(osbbDTO1).isEqualTo(osbbDTO2);
        osbbDTO2.setId(2L);
        assertThat(osbbDTO1).isNotEqualTo(osbbDTO2);
        osbbDTO1.setId(null);
        assertThat(osbbDTO1).isNotEqualTo(osbbDTO2);
    }
}
