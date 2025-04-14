package com.teste.estagio.api.exception;

public class AddressNotFoundException extends RuntimeException {
  public AddressNotFoundException(String message) {
    super(message);
  }
}
