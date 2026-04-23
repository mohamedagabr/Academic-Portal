package com.academic.portal.common.response;

import com.academic.portal.enums.ResponseCode;
import com.academic.portal.enums.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse <T>{
    private String status;
    private String message;
    private T data;

    public static <T> ApiResponse<T> success(ResponseCode code, T data) {
        return ApiResponse.<T>builder()
                .status(ResponseStatus.SUCCESS.name())
                .message(code.name())
                .data(data)
                .build();
    }
    public static <T> ApiResponse<T> error(String message) {
        return ApiResponse.<T>builder()
                .status(ResponseStatus.ERROR.name())
                .message(message)
                .build();
    }

}
