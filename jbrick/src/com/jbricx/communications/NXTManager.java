package com.jbricx.communications;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;

import com.jbricx.communications.NXT.ConnectionType;
import com.jbricx.communications.NXT.Motor;
import com.jbricx.communications.NXT.Sensor;
import com.jbricx.communications.NXT.SensorMode;
import com.jbricx.communications.NXT.SensorType;
import com.jbricx.communications.exceptions.NXTNotFoundException;
import com.jbricx.communications.exceptions.UnableToCreateNXTException;
import com.jbricx.pjo.FileExtensionConstants;
import com.jbricx.ui.findbrick.FindBrickFileIO;

public class NXTManager extends AbstractNXTBrick {
  private static NXTManager nxtManager = null;
  private static ArrayList<NXTObserver> nxtObservers = new ArrayList<NXTObserver>();

  private static boolean running = false;
  private ConnectionType ct;
  private static final int WAITTIME = 3000;
  private static final String COM = "/COM=usb";// USB0::0X0694::0X0002::0016530996B4::RAW";
  // these must be on the build path. we will want to have
  // this in preferences eventually.
  private static NXT nxt;
  private static Fantom fantom;

  private Thread connectedRunnable = pollingCreator();
  private IPreferenceStore preferences;

  private Thread pollingCreator() {
    return new Thread() {
      @Override
      public void run() {
        running = true;
        while (running) {
          try {
            Thread.sleep(WAITTIME);
            connect(FindBrickFileIO.getCT());
          } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
          }
        }
      }
    };
  }

  private NXTManager() {

  }

  static {
    nxtManager = new NXTManager();
  }

  public static NXTManager getInstance() {
    return nxtManager;
  }

  /*
   * public Fantom getFantom() throws FantomDriverNotFoundException { if (fantom
   * == null) { throw new
   * FantomDriverNotFoundException("Fantom driver not found!"); } else { return
   * fantom; } }
   */

  public AbstractNXTBrick connect(ConnectionType type) {
    try {
      ct = type;
      nxt = new NXT(type.getName());
      // playTone(2000, 200);

      notifyAllObservers(nxt.isConnected());
      if (!connectedRunnable.isAlive()) {
        connectedRunnable = pollingCreator();
        connectedRunnable.start();
      }
      nxt.setConnected(true);

    } catch (NXTNotFoundException e) {
      // JOptionPane.showMessageDialog(null, "No bricks found...");
      System.out.println("NXTManager.java :: Trying to connect to "
          + type.getName() + " but failed!");
      // stop polling once the brick has been disconnected
      running = false;
      try {
        nxt.setConnected(false);
      } catch (NullPointerException ne) {

      }
      notifyAllObservers(false);
    } catch (UnableToCreateNXTException e) {
      System.out.println("NXTManager.java :: Unable to create NXT...");
      notifyAllObservers(false);
      // stop polling once the brick has been disconnected

      try {
        nxt.setConnected(false);
        connectedRunnable.interrupt();
      } catch (NullPointerException ne) {

      }
    }
    return this;
  }

  public void notifyObserver(NXTObserver o, boolean isConnected) {
    o.update(isConnected);
  }

  public void notifyAllObservers(boolean isConnected) {
    try {
      nxt.setConnected(isConnected);
    } catch (NullPointerException e) {

    }
    for (NXTObserver nxtObserver : nxtObservers) {
      nxtObserver.update(isConnected);
    }
  }

  public void register(NXTObserver observer) {
    nxtObservers.add(observer);
  }

  public ConnectionType getConnectionType() {
    return ct;
  }

  /*
   * public void notifyFantomListener(FantomListener fl, boolean
   * isDriverAvailable) { fl.update(isDriverAvailable); }
   * 
   * public void notifyAllFantomListeners(boolean isDriverAvailable) { for
   * (FantomListener fl : fantonListeners) { fl.update(isDriverAvailable); } }
   */

  @Override
  public ExitStatus compile(String filename) {
    System.out.println("Trying to compile: " + filename);
    List<String> command = new ArrayList<String>();
    command.add(preferences.getString(FileExtensionConstants.NBCTOOL));
    // command.add("-help");
    // command.add("-S");//+where);
    // command.add("usb");
    // command.add("-d");
    command.add(filename);

    return run(command);
  }

  @Override
  public ExitStatus deleteFile(String filename) {
    // TODO Auto-generated method stub
    return null;
  }

  // public boolean connect() {
  // List<String> command = new ArrayList<String>();
  // command.add(NEXTTOOL);
  // command.add("/COM=usb");
  // command.add("-battery");
  // ExitStatus e = run(command);
  //
  // if (e.isOk()) {
  // if (e.getMesage().split(" ").length == 0) {
  // return false;
  // } else {
  // return true;
  // }
  // } else {
  // return false;
  // }
  // }

  @Override
  public ExitStatus downloadFile(String filename) {
    List<String> command = new ArrayList<String>();
    command.add(preferences.getString(FileExtensionConstants.NBCTOOL));
    command.add("-S=usb");// +where);
    command.add("-d");
    command.add(filename);
    // System.out.println("Command:"+command.toString());
    // return run(command);
    System.out.println("Downloading...");
    
    if (isConnected()) {
      // System.out.println("connectd");
      nxt.download(filename);
      return run(command);
    } else {
      return new ExitStatus(ExitStatus.ERROR,
          "No Brick Connected. Please connect and try again.");
    }
  }

  @Override
  public ExitStatus getRunningProgram() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ExitStatus playTone(int frequency, int duration) {
    nxt.playSound(frequency, duration);
    return null;
  }

  @Override
  public ExitStatus playTone(int frequency, int duration, boolean loop) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ExitStatus run(String filename) {
    // TODO Auto-generated method stub
    return null;
  }

  public void setConnected(boolean isConnected) {
    nxt.setConnected(isConnected);
  }

  public boolean isConnected() {
    try {
      return nxt.isConnected();
    } catch (NullPointerException ne) {
      return false;
    }
  }

  @Override
  public int getBatteryLevel() {
    return nxt.getBattery();
  }

  @Override
  public byte getRawSensorValue(String name) {
    return nxt.getRawSensorValue(name);
  }

  @Override
  public byte getRawSensorValue(Sensor sensor) {
    return nxt.getRawSensorValue(sensor.getName());
  }

  @Override
  public byte[] getSensorValues(String name) {
    return nxt.getSensorValues(name);
  }

  @Override
  public byte[] getSensorValues(Sensor sensor) {
    return nxt.getSensorValues(sensor.getName());
  }

  @Override
  public SensorMode[] listSensorModes() {
    return SensorMode.values();
  }

  @Override
  public SensorType[] listSensorTypes() {
    return SensorType.values();
  }

  @Override
  public Sensor[] listSensors() {
    return Sensor.values();
  }

  @Override
  public void motorOff(String motorName) {
    nxt.stopMotor(Motor.valueOf(motorName).getPort());

  }

  @Override
  public void motorOff(Motor motor) {
    nxt.stopMotor(motor.getPort());

  }

  @Override
  public void motorOn(String motorName, int speed) {
    nxt.runMotor(Motor.valueOf(motorName).getPort(), speed);

  }

  @Override
  public void motorOn(Motor motor, int speed) {
    nxt.runMotor(motor.getPort(), speed);
  }

  @Override
  public void motorReset(String motorName) {
    nxt.resetMotor(Motor.valueOf(motorName).getPort());

  }

  @Override
  public void motorReset(Motor motor) {
    nxt.resetMotor(motor.getPort());

  }

  @Override
  public void setSensorMode(String name, SensorMode mode) {
    nxt.setSensorMode(name, mode);

  }

  @Override
  public void setSensorMode(Sensor sensor, SensorMode mode) {
    nxt.setSensorMode(sensor.getName(), mode);

  }

  @Override
  public void setSensorType(String name, SensorType type) {
    nxt.setSensorType(name, type);

  }

  @Override
  public void setSensorType(Sensor sensor, SensorType type) {
    nxt.setSensorType(sensor.getName(), type);

  }

  @Override
  public int getConvertedSensorData(Sensor name) {
    return nxt.getConvertedSensorData(name.getName(), name.getMode());
  }

  public void stopPolling() {
    running = false;
  }

  public void setPreferences(final IPreferenceStore preferences) {
    this.preferences = preferences;
  }
}
