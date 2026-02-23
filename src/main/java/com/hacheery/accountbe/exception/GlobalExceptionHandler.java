package com.hacheery.accountbe.exception;

import com.hacheery.accountbe.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = org.springframework.security.core.AuthenticationException.class)
    ResponseEntity<ApiResponse> handlingAuthenticationException(org.springframework.security.core.AuthenticationException exception) {
        ApiResponse apiResponse = new ApiResponse();
        
        // Return 400 for authentication failures as requested
        apiResponse.setCode(ErrorCode.WRONG_PASSWORD.getCode());
        apiResponse.setMessage(exception.getMessage());
        
        return ResponseEntity
                .status(ErrorCode.WRONG_PASSWORD.getStatusCode())
                .body(apiResponse);
    }

    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ApiResponse> handlingRuntimeException(RuntimeException exception) {
        ApiResponse apiResponse = new ApiResponse();
        
        apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());

        return ResponseEntity
                .status(ErrorCode.UNCATEGORIZED_EXCEPTION.getStatusCode())
                .body(apiResponse);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handlingAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());

        return ResponseEntity
                .status(errorCode.getStatusCode())
                .body(apiResponse);
    }
}
