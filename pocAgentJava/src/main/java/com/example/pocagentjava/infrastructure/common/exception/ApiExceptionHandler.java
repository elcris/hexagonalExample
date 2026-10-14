package com.example.pocagentjava.infrastructure.common.exception;

import com.example.pocagentjava.domain.exceptions.InvalidBusinessRuleException;
import com.example.pocagentjava.domain.exceptions.ProductNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

  @ExceptionHandler(ProductNotFoundException.class)
  public ResponseEntity<ApiError> handleNotFound(
      ProductNotFoundException exception, HttpServletRequest request) {
    return response(HttpStatus.NOT_FOUND, exception.code(), exception.getMessage(),
        request.getRequestURI(), List.of());
  }

  @ExceptionHandler(InvalidBusinessRuleException.class)
  public ResponseEntity<ApiError> handleInvalidBusinessRule(
      InvalidBusinessRuleException exception, HttpServletRequest request) {
    return response(HttpStatus.UNPROCESSABLE_ENTITY, exception.code(), exception.getMessage(),
        request.getRequestURI(), List.of());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiError> handleValidation(
      MethodArgumentNotValidException exception, HttpServletRequest request) {
    List<ApiError.ValidationDetail> details = exception.getBindingResult().getFieldErrors().stream()
        .map(error -> new ApiError.ValidationDetail(
            error.getField(), String.valueOf(error.getDefaultMessage())))
        .toList();
    return response(HttpStatus.BAD_REQUEST, "VALIDATION_ERROR",
        "La solicitud no es válida", request.getRequestURI(), details);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ApiError> handleUnreadableRequest(HttpServletRequest request) {
    return response(HttpStatus.BAD_REQUEST, "INVALID_REQUEST",
        "El cuerpo de la solicitud no es válido", request.getRequestURI(), List.of());
  }

  private ResponseEntity<ApiError> response(
      HttpStatus status,
      String code,
      String message,
      String path,
      List<ApiError.ValidationDetail> details) {
    ApiError body = new ApiError(
        Instant.now(), status.value(), status.getReasonPhrase(), code, message, path, details);
    return ResponseEntity.status(status).body(body);
  }
}
