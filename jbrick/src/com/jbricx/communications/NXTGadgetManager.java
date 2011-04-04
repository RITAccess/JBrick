/**
 * 
 */
package com.jbricx.communications;

import com.jbricx.communications.enums.Motor;
import com.jbricx.communications.enums.Sensor;
import com.jbricx.communications.enums.SensorMode;
import com.jbricx.communications.enums.SensorType;

/**
 * Defines the methods related to the different gadgets that can be attached to
 * a Lego Brick.
 * 
 * @author byktol
 */
public interface NXTGadgetManager extends NXTConnectionManager {
  
  ExitStatus playTone(int frequency, int duration);

  ExitStatus playTone(int frequency, int duration, boolean loop);

  int getBatteryLevel();

  void motorOn(String motorName, int speed);

  void motorOn(Motor motor, int speed);

  void motorOff(String motorName);

  void motorOff(Motor motor);

  void motorReset(String motorName);

  void motorReset(Motor motor);

  void setSensorType(String name, SensorType type);

  void setSensorType(Sensor sensor, SensorType type);

  void setSensorMode(String name, SensorMode mode);

  void setSensorMode(Sensor sensor, SensorMode mode);

  byte[] getSensorValues(String name);

  byte[] getSensorValues(Sensor name);

  int getConvertedSensorData(Sensor name);

  byte getRawSensorValue(String name);

  byte getRawSensorValue(Sensor name);

  Sensor[] listSensors();

  SensorType[] listSensorTypes();

  SensorMode[] listSensorModes();
}
