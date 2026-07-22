package org.lxrssdev.taquia.app.exceptions;

public class NoAvailableTables extends RuntimeException {
  public NoAvailableTables(String message) {
    super(message);
  }
}
