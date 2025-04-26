package com.guilherme.usuario.infra.exception;

public class ResourceNotFoundExcetion extends RuntimeException {
  public ResourceNotFoundExcetion(String message) {
    super(message);
  }
}
