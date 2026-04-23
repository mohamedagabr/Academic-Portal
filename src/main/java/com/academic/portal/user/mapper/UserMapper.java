package com.academic.portal.user.mapper;

import com.academic.portal.user.dto.UserResponseDto;
import com.academic.portal.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {


    UserResponseDto toDto(User user);
    List<UserResponseDto> toDtoList(List<User> users);

}
