package com.jbricx.communications;

import java.util.List;

import com.jbricx.communications.NXT.ConnectionType;
import com.jbricx.communications.NXT.Motor;
import com.jbricx.communications.NXT.Sensor;
import com.jbricx.communications.NXT.SensorMode;
import com.jbricx.communications.NXT.SensorType;
import com.jbricx.communications.exceptions.NXTNotFoundException;
import com.jbricx.communications.exceptions.UnableToCreateNXTException;

public abstract class AbstractNXTBrick {

  private String joinToString(List<String> lst) {
    String str = "";

    for (String s : lst) {
      try {
        if (s.split(" ").length > 1) {
          str += "\"" + s + "\"";
        } else {
          str += s;
        }
        str += " ";
      } catch (NullPointerException ne) {
      }
    }

    return str;
  }

  public ExitStatus run(List<String> command) {
    System.out.println(joinToString(command));
    return (new ProcessRunner()).run(command);
  }

  public abstract ExitStatus compile(String filename);

  public abstract ExitStatus playTone(int frequency, int duration);

  public abstract ExitStatus playTone(int frequency, int duration, boolean loop);

  public abstract ExitStatus run(String filename);

  public abstract ExitStatus getRunningProgram();

  public abstract ExitStatus downloadFile(String filename);

  public abstract ExitStatus deleteFile(String filename);

  public abstract void NXTConnect(ConnectionType type) throws NXTNotFoundException, UnableToCreateNXTException;

  public abstract boolean isConnected();

  public abstract int getBatteryLevel();

  public abstract void motorOn(String motorName, int speed);

  public abstract void motorOn(Motor motor, int speed);

  public abstract void motorOff(String motorName);

  public abstract void motorOff(Motor motor);

  public abstract void motorReset(String motorName);

  public abstract void motorReset(Motor motor);

  public abstract void setSensorType(String name, SensorType type);

  public abstract void setSensorType(Sensor sensor, SensorType type);

  public abstract void setSensorMode(String name, SensorMode mode);

  public abstract void setSensorMode(Sensor sensor, SensorMode mode);

  public abstract byte[] getSensorValues(String name);

  public abstract byte[] getSensorValues(Sensor name);

  //public abstract int getConvertedSensorData(String name, byte mode);
  public abstract int getConvertedSensorData(Sensor name);

  public abstract byte getRawSensorValue(String name);

  public abstract byte getRawSensorValue(Sensor name);

  public abstract Sensor[] listSensors();

  public abstract SensorType[] listSensorTypes();

  public abstract SensorMode[] listSensorModes();

  public abstract boolean connect();
}
