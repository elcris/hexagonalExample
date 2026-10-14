package com.example.pocagentjava.infrastructure.common.exception;

import java.time.Instant;
import java.util.List;

public record ApiError(
    Instant timestamp,
    int status,
    String error,
    String code,
    String message,
    String path,
    List<ValidationDetail> details) {

  public ApiError {
    details = List.copyOf(details);
  }

  public record ValidationDetail(String field, String message) {
  }
}
