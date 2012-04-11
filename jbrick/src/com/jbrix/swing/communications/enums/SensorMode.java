/**
 * 
 */
package com.jbrix.swing.communications.enums;

/**
 * @author Spencer Herzberg
 * @author byktol
 */
public enum SensorMode {

  RAW((byte) 0x00, "Raw"), BOOLEAN((byte) 0x20, "Boolean"), TRANSITIONCNT(
      (byte) 0x40, "Transition CNT"), PERIODCOUNTER((byte) 0x60,
      "Period Counter"), PCTFULLSCALE((byte) 0x80, "PCT Full Scale"), CELSIUS(
      (byte) 0xA0, "Celsius"), FAHRENHEIT((byte) 0xC0, "Fahrenheit"), ANGLESTEP(
      (byte) 0xC0, "Angle Step"), SLOPEMASK((byte) 0xE0, "Slope Mask"), MASK(
      (byte) 0xE0, "Mask");
  private byte mode;
  private String name;

  SensorMode(byte mode, String name) {
    this.mode = mode;
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public byte getMode() {
    return mode;
  }

  public static SensorMode getTypeByName(String name) {
    for (SensorMode m : values()) {
      if (m.getName().equals(name)) {
        return m;
      }
    }
    return null;
  }
}