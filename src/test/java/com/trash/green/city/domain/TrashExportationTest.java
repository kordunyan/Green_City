package com.trash.green.city.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.trash.green.city.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TrashExportationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrashExportation.class);
        TrashExportation trashExportation1 = new TrashExportation();
        trashExportation1.setId(1L);
        TrashExportation trashExportation2 = new TrashExportation();
        trashExportation2.setId(trashExportation1.getId());
        assertThat(trashExportation1).isEqualTo(trashExportation2);
        trashExportation2.setId(2L);
        assertThat(trashExportation1).isNotEqualTo(trashExportation2);
        trashExportation1.setId(null);
        assertThat(trashExportation1).isNotEqualTo(trashExportation2);
    }
}
