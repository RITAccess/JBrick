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
}
