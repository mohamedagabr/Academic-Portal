package com.academic.portal.auth.mapper;

import com.academic.portal.auth.dto.AuthResponse;
import com.academic.portal.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-23T10:40:42+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.7 (Oracle Corporation)"
)
@Component
public class AuthMapperImpl implements AuthMapper {

    @Override
    public AuthResponse toAuthResponse(User user) {
        if ( user == null ) {
            return null;
        }

        AuthResponse.AuthResponseBuilder authResponse = AuthResponse.builder();

        if ( user.getUserId() != null ) {
            authResponse.userId( user.getUserId() );
        }
        if ( user.getUsername() != null ) {
            authResponse.username( user.getUsername() );
        }
        if ( user.getFirstName() != null ) {
            authResponse.firstName( user.getFirstName() );
        }
        if ( user.getLastName() != null ) {
            authResponse.lastName( user.getLastName() );
        }
        if ( user.getMobileNumber() != null ) {
            authResponse.mobileNumber( user.getMobileNumber() );
        }
        if ( user.getEmail() != null ) {
            authResponse.email( user.getEmail() );
        }
        if ( user.getRole() != null ) {
            authResponse.role( user.getRole() );
        }

        return authResponse.build();
    }
}
