package kr.kh.backend.common.exception;

import kr.kh.backend.common.exception.custom.UserNotFoundException;
import kr.kh.backend.common.response.ApiResponse;
import kr.kh.backend.common.response.ApiResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleProductNotFound(UserNotFoundException ex) {
        ApiResult response = new ApiResult(null, ex.getHttpStatus(), ex.getMessage(), ex.getLocalDateTime());
        return ResponseEntity.status(ex.getHttpStatus()).body(response);
    }

}
