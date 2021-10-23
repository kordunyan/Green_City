package com.trash.green.city.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.trash.green.city.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OsbbTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Osbb.class);
        Osbb osbb1 = new Osbb();
        osbb1.setId(1L);
        Osbb osbb2 = new Osbb();
        osbb2.setId(osbb1.getId());
        assertThat(osbb1).isEqualTo(osbb2);
        osbb2.setId(2L);
        assertThat(osbb1).isNotEqualTo(osbb2);
        osbb1.setId(null);
        assertThat(osbb1).isNotEqualTo(osbb2);
    }
}
