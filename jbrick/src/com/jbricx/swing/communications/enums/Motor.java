/**
 * 
 */
package com.jbricx.swing.communications.enums;

/**
 * @author Spencer Herzberg
 * @author byktol
 */
public enum Motor {

  MOTOR_A((byte) 0x00, "Motor A"), MOTOR_B((byte) 0x01, "Motor B"), MOTOR_C(
      (byte) 0x02, "Motor C");
  private byte port;
  private String name;

  Motor(byte port, String name) {
    this.port = port;
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public byte getPort() {
    return port;
  }

  public static Motor getMotorByName(String name) {
    for (Motor m : values()) {
      if (m.getName().equals(name)) {
        return m;
      }
    }
    return null;
  }
}