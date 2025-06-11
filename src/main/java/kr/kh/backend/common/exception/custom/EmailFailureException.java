package kr.kh.backend.common.exception.custom;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter @Setter
public class EmailFailureException extends RuntimeException {
    private String message;
    private LocalDateTime localDateTime;
    private HttpStatus httpStatus;

    @Override
    public String getMessage() {
        return message;
    }

    public EmailFailureException(String message, LocalDateTime localDateTime, HttpStatus httpStatus) {
        this.message = message;
        this.localDateTime = localDateTime;
        this.httpStatus = httpStatus;
    }
}
