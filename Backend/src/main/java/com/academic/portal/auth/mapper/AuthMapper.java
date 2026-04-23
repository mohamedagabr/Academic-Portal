package com.academic.portal.auth.mapper;

import com.academic.portal.auth.dto.AuthResponse;
import com.academic.portal.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface AuthMapper {

    AuthResponse toAuthResponse(User user);


}
