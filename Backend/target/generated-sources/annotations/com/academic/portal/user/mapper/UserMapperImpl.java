package com.academic.portal.user.mapper;

import com.academic.portal.entity.User;
import com.academic.portal.user.dto.UserResponseDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-23T10:40:42+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.7 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserResponseDto toDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponseDto.UserResponseDtoBuilder userResponseDto = UserResponseDto.builder();

        userResponseDto.userId( user.getUserId() );
        userResponseDto.username( user.getUsername() );
        userResponseDto.firstName( user.getFirstName() );
        userResponseDto.lastName( user.getLastName() );
        userResponseDto.mobileNumber( user.getMobileNumber() );
        userResponseDto.email( user.getEmail() );
        userResponseDto.role( user.getRole() );
        userResponseDto.isActive( user.getIsActive() );

        return userResponseDto.build();
    }

    @Override
    public List<UserResponseDto> toDtoList(List<User> users) {
        if ( users == null ) {
            return null;
        }

        List<UserResponseDto> list = new ArrayList<UserResponseDto>( users.size() );
        for ( User user : users ) {
            list.add( toDto( user ) );
        }

        return list;
    }
}
