package com.jbricx.communications;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.preference.IPreferenceStore;

import com.jbricx.communications.enums.ConnectionType;
import com.jbricx.communications.enums.Motor;
import com.jbricx.communications.enums.Sensor;
import com.jbricx.communications.enums.SensorMode;
import com.jbricx.communications.enums.SensorType;
import com.jbricx.communications.exceptions.NXTNotFoundException;
import com.jbricx.communications.exceptions.UnableToCreateNXTException;
import com.jbricx.pjo.FileExtensionConstants;

/**
 * @author Abhishek Shrestha
 * @author byktol
 */
public class NXTManager implements NXTConnectionManager, NXTGadgetManager {
  private static NXTManager nxtManager = null;

  private static final int sleepTime = 3000;

  private final Map<String, NXTConnection> connections = new HashMap<String, NXTConnection>();
  private final Map<String, Thread> threads = new HashMap<String, Thread>();
  private final Map<String, Boolean> runnings = new HashMap<String, Boolean>();

  private final ArrayList<NXTObserver> nxtObservers = new ArrayList<NXTObserver>();

  private final ProcessRunner processRunner = new ProcessRunner();
  private IPreferenceStore preferences;

  /*
   * FIXME: This variable is meant to be used help point the current connection,
   * but the methods it's been used in doesn't help it. I mean methods such as
   * connect(), which set the value of this variable but it's invoked many times
   * by a thread.
   */
  private String currentConnection;

  /**
   * Constructor for Singleton
   */
  private NXTManager() {
  }

  static {
    nxtManager = new NXTManager();
  }

  public static boolean isFantomDriverLoaded() {
    return NXTConnection.isFantomDriverLoaded();
  }

  /**
   * 
   * @param name
   * @return
   */
  private Thread createConnectionPollingThread(final String name) {
    return new Thread() {
      @Override
      public void run() {
        runnings.put(name, true);
        while (runnings.get(name)) {
          try {
            sleep(sleepTime);
            connect(connections.get(name).getConnectionType());
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
        connections.remove(name);
        threads.remove(name);
        runnings.remove(name);
      }
    };
  }

  /**
   * FIXME: This doesn't look good. Create a process runner and return a method
   * call's result.
   */
  public ExitStatus run(String... command) {
    return processRunner.run(Arrays.asList(command));
  }

  /**
   * @return the instance
   */
  public static NXTManager getInstance() {
    return nxtManager;
  }

  public NXTGadgetManager connect(ConnectionType type) {
    try {
      currentConnection = type.getName();
      System.out.println("NXTManager.connect()" + currentConnection);
      NXTConnection nxtConn = new NXTConnection(type.getName());
      connections.put(type.getName(), nxtConn);
      // playTone(2000, 200);
      notifyAllObservers(nxtConn.isConnected());

      Thread t = createConnectionPollingThread(type.getName());
      if (threads.get(type.getName()) != null
          && !threads.get(type.getName()).isAlive()) {
        threads.put(type.getName(), t).start();
      } else {
        threads.put(type.getName(), t);
        t.start();
      }
      playTone(1000, 300);

    } catch (NXTNotFoundException e) {
      // JOptionPane.showMessageDialog(null, "No bricks found...");
      System.out.println("NXTManager.java :: Trying to connect to "
          + type.getName() + " but failed!");
      // stop polling once the brick has been disconnected
      runnings.put(type.getName(), false);

      notifyAllObservers(false);
    } catch (UnableToCreateNXTException e) {
      System.out.println("NXTManager.java :: Unable to create NXT...");
      notifyAllObservers(false);
      // stop polling once the brick has been disconnected

      try {
        threads.get(type.getName()).interrupt();
      } catch (NullPointerException ne) {
      }
    }
    return this;
  }

  @Override
  public void disconnect() {
    disconnect(currentConnection);
  }

  @Override
  public void disconnect(final String name) {
    connections.remove(name).disconnect();
    runnings.remove(name);

    if (threads.get(name) != null) {
      threads.remove(name).interrupt();
    }
    System.currentTimeMillis();
  }

  /**
   * Register an observer
   * 
   * @param observer
   */
  public void register(final NXTObserver observer) {
    nxtObservers.add(observer);
  }

  public void notifyAllObservers(boolean isConnected) {
    for (NXTObserver nxtObserver : nxtObservers) {
      nxtObserver.update(isConnected);
    }
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
    final String[] command = {
        preferences.getString(FileExtensionConstants.NBCTOOL),
        // "-help",
        // "-S", //+where);
        // "usb",
        // "-d",
        filename, };
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
    final String[] command = { preferences
        .getString(FileExtensionConstants.NBCTOOL),
    // "-S=usb", // +where);
    // "-d",
    // filename,
    };
    System.out.println("Downloading...");

    if (isConnected()) {
      disconnect();
      return run(command);
    } else {
      // return new ExitStatus(ExitStatus.ERROR,
      // "No Brick Connected. Please connect and try again.");
      disconnect(ConnectionType.USB.toString());
      return run(command);
    }
  }

  @Override
  public ExitStatus getRunningProgram() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ExitStatus playTone(int frequency, int duration) {
    connections.get(currentConnection).playSound(frequency, duration);
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

  public boolean isConnected() {
    try {
      return connections.get(currentConnection).isConnected();
    } catch (NullPointerException ne) {
      return true;
    }
  }

  @Override
  public int getBatteryLevel() {
    return connections.get(currentConnection).getBattery();
  }

  @Override
  public byte getRawSensorValue(String name) {
    return connections.get(currentConnection).getRawSensorValue(name);
  }

  @Override
  public byte getRawSensorValue(Sensor sensor) {
    return connections.get(currentConnection).getRawSensorValue(
        sensor.getName());
  }

  @Override
  public byte[] getSensorValues(String name) {
    return connections.get(currentConnection).getSensorValues(name);
  }

  @Override
  public byte[] getSensorValues(Sensor sensor) {
    return connections.get(currentConnection).getSensorValues(sensor.getName());
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
    connections.get(currentConnection).stopMotor(
        Motor.valueOf(motorName).getPort());
  }

  @Override
  public void motorOff(Motor motor) {
    connections.get(currentConnection).stopMotor(motor.getPort());

  }

  @Override
  public void motorOn(String motorName, int speed) {
    connections.get(currentConnection).runMotor(
        Motor.valueOf(motorName).getPort(), speed);
  }

  @Override
  public void motorOn(Motor motor, int speed) {
    connections.get(currentConnection).runMotor(motor.getPort(), speed);
  }

  @Override
  public void motorReset(String motorName) {
    connections.get(currentConnection).resetMotor(
        Motor.valueOf(motorName).getPort());

  }

  @Override
  public void motorReset(Motor motor) {
    connections.get(currentConnection).resetMotor(motor.getPort());
  }

  @Override
  public void setSensorMode(String name, SensorMode mode) {
    connections.get(currentConnection).setSensorMode(name, mode);
  }

  @Override
  public void setSensorMode(Sensor sensor, SensorMode mode) {
    connections.get(currentConnection).setSensorMode(sensor.getName(), mode);
  }

  @Override
  public void setSensorType(String name, SensorType type) {
    connections.get(currentConnection).setSensorType(name, type);
  }

  @Override
  public void setSensorType(Sensor sensor, SensorType type) {
    connections.get(currentConnection).setSensorType(sensor.getName(), type);
  }

  @Override
  public int getConvertedSensorData(Sensor name) {
    return connections.get(currentConnection).getConvertedSensorData(
        name.getName(), name.getMode());
  }

  // FIXME
  public void stopPolling() {
    // running = false;
  }

  public void setPreferences(final IPreferenceStore preferences) {
    this.preferences = preferences;
  }
}
