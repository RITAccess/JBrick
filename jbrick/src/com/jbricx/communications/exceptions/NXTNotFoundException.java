package com.jbricx.communications.exceptions;

public class NXTNotFoundException extends Exception {

  private static final long serialVersionUID = -6159895016080042043L;

  public NXTNotFoundException() {
    super("No NXT brick found when trying to initialize connection.");
  }

  public NXTNotFoundException(final String message) {
    super(message);
  }
}