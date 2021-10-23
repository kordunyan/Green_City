package com.trash.green.city.service.mapper;

import com.trash.green.city.domain.TrashExportation;
import com.trash.green.city.service.dto.TrashExportationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TrashExportation} and its DTO {@link TrashExportationDTO}.
 */
@Mapper(componentModel = "spring", uses = { OsbbMapper.class })
public interface TrashExportationMapper extends EntityMapper<TrashExportationDTO, TrashExportation> {
    @Mapping(target = "osbb", source = "osbb", qualifiedByName = "name")
    TrashExportationDTO toDto(TrashExportation s);
}
