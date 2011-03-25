/**
 * 
 */
package com.jbricx.communications.enums;

/**
 * @author Spencer Herzberg
 * @author byktol
 */
public enum ConnectionType {

  USB("USB"), BLUETOOTH("BTH");
  private String name;

  ConnectionType(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  /**
   * No matter what you believe, I think there's a better way of doing this.
   * @return the port for use with the NBC compiler.
   */
  public String toPort() {
    String port;
    switch (this) {
      case USB:
        port = "-S=usb";
        break;
      case BLUETOOTH:
        port = "-BT";
        break;
      default:
        port = "";
    }

    return port;
  }
}
