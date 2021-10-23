package com.trash.green.city.service.mapper;

import com.trash.green.city.domain.TrashCompany;
import com.trash.green.city.service.dto.TrashCompanyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TrashCompany} and its DTO {@link TrashCompanyDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TrashCompanyMapper extends EntityMapper<TrashCompanyDTO, TrashCompany> {
    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    TrashCompanyDTO toDtoName(TrashCompany trashCompany);
}
