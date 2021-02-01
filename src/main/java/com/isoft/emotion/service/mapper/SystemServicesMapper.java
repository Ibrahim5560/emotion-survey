package com.isoft.emotion.service.mapper;


import com.isoft.emotion.domain.*;
import com.isoft.emotion.service.dto.SystemServicesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SystemServices} and its DTO {@link SystemServicesDTO}.
 */
@Mapper(componentModel = "spring", uses = {SystemMapper.class})
public interface SystemServicesMapper extends EntityMapper<SystemServicesDTO, SystemServices> {

    @Mapping(source = "system.id", target = "systemId")
    SystemServicesDTO toDto(SystemServices systemServices);

    @Mapping(target = "systemServicesMessages", ignore = true)
    @Mapping(target = "removeSystemServicesMessages", ignore = true)
    @Mapping(source = "systemId", target = "system")
    SystemServices toEntity(SystemServicesDTO systemServicesDTO);

    default SystemServices fromId(Long id) {
        if (id == null) {
            return null;
        }
        SystemServices systemServices = new SystemServices();
        systemServices.setId(id);
        return systemServices;
    }
}
