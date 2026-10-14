package com.example.pocagentjava.domain.exceptions;

public final class InvalidBusinessRuleException extends BusinessException {

  public InvalidBusinessRuleException(String code, String message) {
    super(code, message);
  }
}
