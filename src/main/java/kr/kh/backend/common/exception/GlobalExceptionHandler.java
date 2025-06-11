package kr.kh.backend.common.exception;

import kr.kh.backend.common.exception.custom.EmailFailureException;
import kr.kh.backend.common.exception.custom.ExistComplaintException;
import kr.kh.backend.common.exception.custom.RecordFailureException;
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

    @ExceptionHandler(ExistComplaintException.class)
    public ResponseEntity<?> handleExistComplaint(ExistComplaintException ex) {
        ApiResult response = new ApiResult(null, ex.getHttpStatus(), ex.getMessage(), ex.getLocalDateTime());
        return ResponseEntity.status(ex.getHttpStatus()).body(response);
    }

    @ExceptionHandler(EmailFailureException.class)
    public ResponseEntity<?> handleEmailFailure(EmailFailureException ex) {
        ApiResult response = new ApiResult(null, ex.getHttpStatus(), ex.getMessage(), ex.getLocalDateTime());
        return ResponseEntity.status(ex.getHttpStatus()).body(response);
    }

    @ExceptionHandler(RecordFailureException.class)
    public ResponseEntity<?> handleRecordFailure(RecordFailureException ex) {
        ApiResult response = new ApiResult(null, ex.getHttpStatus(), ex.getMessage(), ex.getLocalDateTime());
        return ResponseEntity.status(ex.getHttpStatus()).body(response);
    }

}
