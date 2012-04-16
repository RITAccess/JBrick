package com.jbricx.swing.communications;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.SwingUtilities;

import com.jbricx.communications.enums.ConnectionType;
import com.jbricx.communications.enums.Motor;
import com.jbricx.communications.enums.Sensor;
import com.jbricx.communications.enums.SensorMode;
import com.jbricx.communications.enums.SensorType;

/**
 * This class works as a Facade, its interface contains all the methods related
 * to the Brick operations. It's also a singleton.
 * 
 * @author Abhishek Shrestha
 * @author byktol
 * @author Emma Nelson
 */
public class NXTManager implements NXTConnectionManager, NXTGadgetManager {
	private static NXTManager nxtManager = null;

	/**
	 * The current connections.
	 */
	private final Map<String, NXTBrickConnector> connections = new HashMap<String, NXTBrickConnector>();
	private final List<NXTObserver> nxtObservers = new ArrayList<NXTObserver>();

	private final CompilerRunner compilerRunner = new CompilerRunner();

	private String currentConnection;

	/**
	 * Constructor for Singleton
	 */
	private NXTManager() {
	}

	static {
		nxtManager = new NXTManager();
	}

	/**
	 * @return whether the Fantom driver is installed.
	 */
	public static boolean isFantomDriverLoaded() {
		// This is done to avoid accessing this class method from outside the
		// Facade
		return NXTBrickConnector.isFantomDriverLoaded();
	}

	/**
	 * @return the instance
	 */
	public static NXTManager getInstance() {
		return nxtManager;
	}

	public NXTManager connect(final ConnectionType connectionType) {
		currentConnection = connectionType.toString();

		if (!(connections.containsKey(currentConnection) && connections.get(
				currentConnection).isRunning())) {
			NXTBrickConnector c = new NXTBrickConnector();
			boolean isConnected = c.connect(connectionType);

			notifyAllObservers(isConnected);
			if (isConnected) {
				connections.put(currentConnection, c);
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
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (connections.containsKey(name)) {
					connections.remove(name).disconnect();
					verifyLastDisconnect();
				}
			}
		});
	}

	/**
	 * Register an observer
	 * 
	 * @param observer
	 */
	public void register(final NXTObserver observer) {
		nxtObservers.add(observer);
	}

	public void unregister(NXTObserver observer) {
		nxtObservers.remove(observer);
	}

	public void notifyAllObservers(boolean isConnected) {
		List<NXTObserver> copiedObservers = new ArrayList<NXTObserver>();

		// CONCURRENCY IDIOM: copy then iterate
		copiedObservers.addAll(nxtObservers);

		for (NXTObserver nxtObserver : copiedObservers) {
			nxtObserver.update(isConnected);
		}
	}

	@Override
	public ExitStatus compile(final String filename) {
		return compilerRunner.compile(filename);
	}

	@Override
	public ExitStatus deleteFile(String filename) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Downloads a file to the brick. It should read uploads a file from your
	 * computer to the brick, but who knows what were they thinking.
	 */
	@Override
	public ExitStatus downloadFile(final String filename) {
		if (connections.containsKey(currentConnection) && isConnected()) {

			NXTBrickConnector proc = connections.get(currentConnection);

			// This is how it works: disconnect the brick, transfer file,
			// re-connect.
			disconnect();
			ExitStatus status = compilerRunner.download(filename, proc
					.getConnection().getConnectionType().toPort());
			connect(proc.getConnection().getConnectionType());

			return status;
		}

		// Since we know we're using the same tool to compile and download,
		// let's
		// try to compile the file if no connection is available.
		return compile(filename);
	}

	@Override
	public ExitStatus getRunningProgram() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ExitStatus playTone(int frequency, int duration) {
		if (connections != null && currentConnection != null)
			connections.get(currentConnection).getConnection()
					.playSound(frequency, duration);
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
			return connections.get(currentConnection).getConnection()
					.isConnected();
		} catch (NullPointerException ne) {
			return false;
		}
	}

	@Override
	public int getBatteryLevel() {
		return connections.get(currentConnection).getConnection().getBattery();
	}

	@Override
	public byte getRawSensorValue(String name) {
		return connections.get(currentConnection).getConnection()
				.getRawSensorValue(name);
	}

	@Override
	public byte getRawSensorValue(Sensor sensor) {
		return connections.get(currentConnection).getConnection()
				.getRawSensorValue(sensor.getName());
	}

	@Override
	public byte[] getSensorValues(String name) {
		return connections.get(currentConnection).getConnection()
				.getSensorValues(name);
	}

	@Override
	public byte[] getSensorValues(Sensor sensor) {
		return connections.get(currentConnection).getConnection()
				.getSensorValues(sensor.getName());
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
		connections.get(currentConnection).getConnection()
				.stopMotor(Motor.valueOf(motorName).getPort());
	}

	@Override
	public void motorOff(Motor motor) {
		connections.get(currentConnection).getConnection()
				.stopMotor(motor.getPort());
	}

	@Override
	public void motorOn(String motorName, int speed) {
		connections.get(currentConnection).getConnection()
				.runMotor(Motor.valueOf(motorName).getPort(), speed);
	}

	@Override
	public void motorOn(Motor motor, int speed) {
		connections.get(currentConnection).getConnection()
				.runMotor(motor.getPort(), speed);
	}

	@Override
	public void motorReset(String motorName) {
		connections.get(currentConnection).getConnection()
				.resetMotor(Motor.valueOf(motorName).getPort());

	}

	@Override
	public void motorReset(Motor motor) {
		connections.get(currentConnection).getConnection()
				.resetMotor(motor.getPort());
	}

	@Override
	public void setSensorMode(String name, SensorMode mode) {
		connections.get(currentConnection).getConnection()
				.setSensorMode(name, mode);
	}

	@Override
	public void setSensorMode(Sensor sensor, SensorMode mode) {
		connections.get(currentConnection).getConnection()
				.setSensorMode(sensor.getName(), mode);
	}

	@Override
	public void setSensorType(String name, SensorType type) {
		connections.get(currentConnection).getConnection()
				.setSensorType(name, type);
	}

	@Override
	public void setSensorType(Sensor sensor, SensorType type) {
		connections.get(currentConnection).getConnection()
				.setSensorType(sensor.getName(), type);
	}

	@Override
	public int getConvertedSensorData(Sensor name) {
		return connections.get(currentConnection).getConnection()
				.getConvertedSensorData(name.getName(), name.getMode());
	}

	/**
	 * Stops all the threads used for polling the connection.
	 */
	public void stopPolling() {
		for (String name : connections.keySet()) {
			connections.get(name).disconnect();
		}
	}

	/**
	 * Upon disconnection of a brick, sets the selected connection to the next
	 * active one. If the last brick is being disconnected, then notify the
	 * observers to disable the required features.
	 */
	public void verifyLastDisconnect() {
		// Why, yes, go through all the trouble just to find the first
		// connection.
		for (String connection : connections.keySet()) {
			currentConnection = connection;
			break;
		}
		// If the last active connection is removed, then notify the observers,
		// otherwise, we don't want to disable the features while there might be
		// one brick connected
		if (connections.isEmpty()) {
			notifyAllObservers(false);
		}
	}

	/**
	 * @return The name of the currently selected connection.
	 */
	public String getCurrentConnection() {
		return currentConnection;
	}
}
