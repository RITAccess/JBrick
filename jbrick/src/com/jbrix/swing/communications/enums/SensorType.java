/**
 * 
 */
package com.jbrix.swing.communications.enums;

/**
 * @author Spencer Herzberg
 * @author byktol
 */
public enum SensorType {

  NONE((byte) 0x00, "None", SensorMode.RAW), SWITCH((byte) 0x01, "Switch",
      SensorMode.BOOLEAN), SOUNDDB((byte) 0x07, "Sound DB", SensorMode.RAW), REFLECTION(
      (byte) 0x03, "Reflection", SensorMode.RAW), LOWSPEED9V((byte) 0x0B,
      "Low Speed 9V", SensorMode.RAW), TEMPERATURE((byte) 0x02,
      "Temperature", SensorMode.FAHRENHEIT), ANGLE((byte) 0x04, "Angle",
      SensorMode.RAW), LIGHTACTIVE((byte) 0x05, "Light Active",
      SensorMode.RAW), LIGHTINACTIVE((byte) 0x06, "Light Inactive",
      SensorMode.RAW), SOUNDDBA((byte) 0x08, "Sound DBA", SensorMode.RAW), CUSTOM(
      (byte) 0x09, "Custom", SensorMode.RAW), LOWSPEED((byte) 0x0A,
      "Low Speed", SensorMode.RAW), TOUCH((byte) 0x0C, "Touch",
      SensorMode.RAW);
  private byte type;
  private String name;
  private SensorMode defaultMode;

  SensorType(byte type, String name, SensorMode mode) {
    this.type = type;
    this.name = name;
    this.defaultMode = mode;
  }

  public String getName() {
    return name;
  }

  public byte getType() {
    return type;
  }

  public SensorMode getMode() {
    return defaultMode;
  }

  public static SensorType getTypeByName(String name) {
    for (SensorType s : values()) {
      if (s.getName().equals(name)) {
        return s;
      }
    }
    return null;

  }
}
