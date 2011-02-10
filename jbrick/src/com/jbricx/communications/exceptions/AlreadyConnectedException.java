package com.jbricx.communications.exceptions;

public class AlreadyConnectedException extends Exception {

  private static final long serialVersionUID = 2900371572418060574L;

  public AlreadyConnectedException(final String message) {
    super(message);
  }
}