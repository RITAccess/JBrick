package com.jbricx.swing.communications;

import com.jbricx.swing.communications.enums.ConnectionType;
import com.jbricx.swing.communications.enums.Sensor;
import com.jbricx.swing.communications.enums.SensorMode;
import com.jbricx.swing.communications.enums.SensorType;

/**
 * Defines the methods for the low-level connections to the Lego Brick.
 * 
 * @author byktol
 */
public interface NXTConnection {
  ConnectionType getConnectionType();

  void startProgram(String filename);

  public void stopProgram();

  /**
   * Disconnects the links to the connected brick.
   */
  void disconnect();

  int getBattery();

  boolean isConnected();

  void playSound(int freq, int duration);

  void runMotor(byte motorName, int speed);

  void stopMotor(byte motorName);

  void resetMotor(byte motorName);

  void setSensorMode(String name, SensorMode mode);

  void setSensorType(String name, SensorType type);

  void resetSensor(Sensor s);

  byte getRawSensorValue(String sensorName);

  int getConvertedSensorData(String sensorName, byte sensorMode);

  int getBoolVal(String sensorName);

  byte[] getSensorValues(String sensorName);

  int unsignedByteToInt(byte b);
}
