package com.trash.green.city.service.mapper;

import com.trash.green.city.domain.Osbb;
import com.trash.green.city.service.dto.OsbbDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Osbb} and its DTO {@link OsbbDTO}.
 */
@Mapper(componentModel = "spring", uses = { TrashCompanyMapper.class })
public interface OsbbMapper extends EntityMapper<OsbbDTO, Osbb> {
    @Mapping(target = "trashCompany", source = "trashCompany", qualifiedByName = "name")
    OsbbDTO toDto(Osbb s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OsbbDTO toDtoId(Osbb osbb);
}
