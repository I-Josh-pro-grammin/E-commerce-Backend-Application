package com.josh.store.Mappers;

import com.josh.store.dtos.RegisterUserRequest;
import com.josh.store.dtos.UpdateUserRequest;
import com.josh.store.dtos.UserDto;
import com.josh.store.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    UserDto toUser(User user);

    User toEntity(RegisterUserRequest request);

    void updateUser(UpdateUserRequest request, @MappingTarget User user);
}
