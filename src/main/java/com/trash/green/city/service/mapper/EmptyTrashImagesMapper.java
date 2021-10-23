package com.trash.green.city.service.mapper;

import com.trash.green.city.domain.EmptyTrashImages;
import com.trash.green.city.service.dto.EmptyTrashImagesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EmptyTrashImages} and its DTO {@link EmptyTrashImagesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EmptyTrashImagesMapper extends EntityMapper<EmptyTrashImagesDTO, EmptyTrashImages> {}
