package com.academic.portal.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // User
    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT),
    USERNAME_ALREADY_EXISTS(HttpStatus.CONFLICT),
    MOBILE_NUMBER_ALREADY_EXISTS(HttpStatus.CONFLICT),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND),
    USER_NOT_ACTIVE(HttpStatus.UNAUTHORIZED),
    USER_IS_DELETED(HttpStatus.UNAUTHORIZED),


    // Token

    REFRESH_TOKEN_REQUIRED(HttpStatus.UNAUTHORIZED),
    REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED),
    ACCESS_DENIED(HttpStatus.FORBIDDEN),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED),

    // Students
    STUDENT_NOT_FOUND(HttpStatus.NOT_FOUND),
    INVALID_NATIONAL_ID(HttpStatus.BAD_REQUEST),
    INVALID_PASSPORT_NUMBER(HttpStatus.BAD_REQUEST),


    // Courses
    COURSE_NOT_FOUND(HttpStatus.NOT_FOUND),

    // Course Registration
    COURSE_ALREADY_REGISTERED(HttpStatus.BAD_REQUEST),
    COURSE_REGISTRATION_NOT_FOUND(HttpStatus.NOT_FOUND),
    COURSE_REGISTRATION_NOT_ACTIVE(HttpStatus.BAD_REQUEST),
    COURSE_REGISTRATION_ALREADY_COMPLETED(HttpStatus.BAD_REQUEST),
    COURSE_FULL(HttpStatus.BAD_REQUEST),

    REGISTRATION_NOT_FOUND(HttpStatus.NOT_FOUND),
    ALREADY_CANCELED(HttpStatus.BAD_REQUEST),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR);




    private final HttpStatus status;

    ErrorCode(HttpStatus status) {
        this.status = status;
    }




}
