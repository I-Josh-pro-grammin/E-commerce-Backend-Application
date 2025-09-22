package com.josh.store.Mappers;

import com.josh.store.dtos.UserDto;
import com.josh.store.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toUser(User user);
}
