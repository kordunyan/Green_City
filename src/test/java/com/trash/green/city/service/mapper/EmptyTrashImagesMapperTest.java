package com.trash.green.city.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EmptyTrashImagesMapperTest {

    private EmptyTrashImagesMapper emptyTrashImagesMapper;

    @BeforeEach
    public void setUp() {
        emptyTrashImagesMapper = new EmptyTrashImagesMapperImpl();
    }
}
