package com.isoft.emotion.service.mapper;


import com.isoft.emotion.domain.*;
import com.isoft.emotion.service.dto.CenterDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Center} and its DTO {@link CenterDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CenterMapper extends EntityMapper<CenterDTO, Center> {


    @Mapping(target = "centerMessages", ignore = true)
    @Mapping(target = "removeCenterMessages", ignore = true)
    Center toEntity(CenterDTO centerDTO);

    default Center fromId(Long id) {
        if (id == null) {
            return null;
        }
        Center center = new Center();
        center.setId(id);
        return center;
    }
}
