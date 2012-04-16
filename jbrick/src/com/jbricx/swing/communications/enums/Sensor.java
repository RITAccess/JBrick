/**
 * 
 */
package com.jbricx.swing.communications.enums;

/**
 * @author Spencer Herzberg
 * @author byktol
 */
public enum Sensor {

  SENSOR_1((byte) 0x00, "Sensor 1"), SENSOR_2((byte) 0x01, "Sensor 2"), SENSOR_3(
      (byte) 0x02, "Sensor 3"), SENSOR_4((byte) 0x03, "Sensor 4");
  private byte port;
  private String name;
  private SensorType type;
  private SensorMode mode;
  private boolean enabled;

  Sensor(byte port, String name) {
    this.port = port;
    this.name = name;
    this.type = SensorType.NONE;
    this.mode = SensorMode.BOOLEAN;
  }

  public String getName() {
    return name;
  }

  public byte getPort() {
    return port;
  }

  public byte getType() {
    return type.getType();
  }

  public byte getMode() {
    return mode.getMode();
  }

  public void setMode(SensorMode mode) {
    this.enabled = true;
    this.mode = mode;
  }

  public void setType(SensorType type) {
    if (type == SensorType.NONE) {
      this.enabled = false;
    } else {
      this.enabled = true;
    }
    this.type = type;
  }

  public boolean isEnabled() {
    return enabled;
  }
}
