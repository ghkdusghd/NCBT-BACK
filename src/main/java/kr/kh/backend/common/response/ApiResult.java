package kr.kh.backend.common.response;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter @Setter
public class ApiResult<T> {
   private T data;
   private HttpStatus status;
   private String message;
   private LocalDateTime dateTime;

   public ApiResult(T data, HttpStatus status, String message, LocalDateTime dateTime) {
      this.data = data;
      this.status = status;
      this.message = message;
      this.dateTime = dateTime;
   }

}