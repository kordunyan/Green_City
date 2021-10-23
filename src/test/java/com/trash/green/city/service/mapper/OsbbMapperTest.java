package com.trash.green.city.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OsbbMapperTest {

    private OsbbMapper osbbMapper;

    @BeforeEach
    public void setUp() {
        osbbMapper = new OsbbMapperImpl();
    }
}
