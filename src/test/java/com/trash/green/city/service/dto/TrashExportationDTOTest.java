package com.trash.green.city.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.trash.green.city.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TrashExportationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrashExportationDTO.class);
        TrashExportationDTO trashExportationDTO1 = new TrashExportationDTO();
        trashExportationDTO1.setId(1L);
        TrashExportationDTO trashExportationDTO2 = new TrashExportationDTO();
        assertThat(trashExportationDTO1).isNotEqualTo(trashExportationDTO2);
        trashExportationDTO2.setId(trashExportationDTO1.getId());
        assertThat(trashExportationDTO1).isEqualTo(trashExportationDTO2);
        trashExportationDTO2.setId(2L);
        assertThat(trashExportationDTO1).isNotEqualTo(trashExportationDTO2);
        trashExportationDTO1.setId(null);
        assertThat(trashExportationDTO1).isNotEqualTo(trashExportationDTO2);
    }
}
