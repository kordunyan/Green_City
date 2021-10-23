package com.trash.green.city.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.trash.green.city.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TrashCompanyDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrashCompanyDTO.class);
        TrashCompanyDTO trashCompanyDTO1 = new TrashCompanyDTO();
        trashCompanyDTO1.setId(1L);
        TrashCompanyDTO trashCompanyDTO2 = new TrashCompanyDTO();
        assertThat(trashCompanyDTO1).isNotEqualTo(trashCompanyDTO2);
        trashCompanyDTO2.setId(trashCompanyDTO1.getId());
        assertThat(trashCompanyDTO1).isEqualTo(trashCompanyDTO2);
        trashCompanyDTO2.setId(2L);
        assertThat(trashCompanyDTO1).isNotEqualTo(trashCompanyDTO2);
        trashCompanyDTO1.setId(null);
        assertThat(trashCompanyDTO1).isNotEqualTo(trashCompanyDTO2);
    }
}
