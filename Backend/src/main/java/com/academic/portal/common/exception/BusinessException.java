package com.academic.portal.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException{
    private final ErrorCode code;
    public BusinessException(ErrorCode code)
    {
        super(code.name());
        this.code = code ;
    }
    public HttpStatus getStatus() {
        return code.getStatus();
    }
}
