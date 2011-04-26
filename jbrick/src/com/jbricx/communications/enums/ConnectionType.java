/**
 * 
 */
package com.jbricx.communications.enums;

/**
 * @author Spencer Herzberg
 * @author byktol
 */
public enum ConnectionType {

  USB, BTH;
 

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
      case BTH:
        port = "-BT";
        break;
      default:
        port = "";
    }

    return port;
  }
}
