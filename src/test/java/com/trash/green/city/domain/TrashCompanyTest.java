package com.trash.green.city.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.trash.green.city.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TrashCompanyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrashCompany.class);
        TrashCompany trashCompany1 = new TrashCompany();
        trashCompany1.setId(1L);
        TrashCompany trashCompany2 = new TrashCompany();
        trashCompany2.setId(trashCompany1.getId());
        assertThat(trashCompany1).isEqualTo(trashCompany2);
        trashCompany2.setId(2L);
        assertThat(trashCompany1).isNotEqualTo(trashCompany2);
        trashCompany1.setId(null);
        assertThat(trashCompany1).isNotEqualTo(trashCompany2);
    }
}
