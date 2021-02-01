package com.isoft.emotion.service.mapper;


import com.isoft.emotion.domain.*;
import com.isoft.emotion.service.dto.MessagesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Messages} and its DTO {@link MessagesDTO}.
 */
@Mapper(componentModel = "spring", uses = {CenterMapper.class, SystemMapper.class, SystemServicesMapper.class, UsersMapper.class})
public interface MessagesMapper extends EntityMapper<MessagesDTO, Messages> {

    @Mapping(source = "center.id", target = "centerId")
    @Mapping(source = "system.id", target = "systemId")
    @Mapping(source = "systemServices.id", target = "systemServicesId")
    @Mapping(source = "users.id", target = "usersId")
    MessagesDTO toDto(Messages messages);

    @Mapping(source = "centerId", target = "center")
    @Mapping(source = "systemId", target = "system")
    @Mapping(source = "systemServicesId", target = "systemServices")
    @Mapping(source = "usersId", target = "users")
    Messages toEntity(MessagesDTO messagesDTO);

    default Messages fromId(Long id) {
        if (id == null) {
            return null;
        }
        Messages messages = new Messages();
        messages.setId(id);
        return messages;
    }
}
