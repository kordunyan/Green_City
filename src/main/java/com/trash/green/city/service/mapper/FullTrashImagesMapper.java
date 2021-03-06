package com.trash.green.city.service.mapper;

import com.trash.green.city.domain.FullTrashImages;
import com.trash.green.city.service.dto.FullTrashImagesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FullTrashImages} and its DTO {@link FullTrashImagesDTO}.
 */
@Mapper(componentModel = "spring", uses = { TrashExportationMapper.class })
public interface FullTrashImagesMapper extends EntityMapper<FullTrashImagesDTO, FullTrashImages> {
    @Mapping(target = "trashExportation", source = "trashExportation", qualifiedByName = "id")
    FullTrashImagesDTO toDto(FullTrashImages s);
}
