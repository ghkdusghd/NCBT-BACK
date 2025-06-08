package kr.kh.backend.common.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public class ApiResponse {

   public static <T> ResponseEntity<ApiResult<T>> OK(T data) {
      ApiResult<T> apiResult = new ApiResult<>(data, HttpStatus.OK, "success", LocalDateTime.now());
      return ResponseEntity.status(200).body(apiResult);
   }

   public static <T> ResponseEntity<ApiResult<T>> NO_CONTENT() {
      ApiResult<T> apiResult = new ApiResult<>(null, HttpStatus.OK, "success", LocalDateTime.now());
      return ResponseEntity.status(200).body(apiResult);
   }

   public static <T> ResponseEntity<ApiResult<T>> CREATED(T data) {
      ApiResult<T> apiResult = new ApiResult<>(data, HttpStatus.CREATED, "created", LocalDateTime.now());
      return ResponseEntity.status(201).body(apiResult);
   }

   public static <T> ResponseEntity<ApiResult<T>> UPDATE_BOOKMARK(String message) {
      ApiResult<T> apiResult = new ApiResult<>(null, HttpStatus.CREATED, message, LocalDateTime.now());
      return ResponseEntity.status(200).body(apiResult);
   }

}