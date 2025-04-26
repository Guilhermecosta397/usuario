package com.guilherme.usuario.infra.exception;

public class conflictException extends RuntimeException {
  public conflictException(String message) {
    super(message);
  }
}
