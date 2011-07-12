package com.jbricx.ui.joystick;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

/**
 * Records the all the NXT movements, made in the Joystick dialog window 
 * and pastes them to the clipboard.
 * 
 * @author Khalid
 *
 */
public class RecordJoystick {

	// The string buffer that will hold all the recorded NXC code
	private StringBuffer nxcCode = new StringBuffer();
	
	public RecordJoystick(){
		nxcCode.append("\ntask main(){\n");
	}

	/**
	 * Records the forward movement for NXT.
	 * 
	 * @param leftMotor The letter for the left motor.
	 * @param rightMotor The letter for the right motor.
	 * @param speed The movement speed.
	 */
	public void recordForward(char leftMotor, char rightMotor, int speed){
		this.nxcCode.append(" // Move Forward\n");
		this.nxcCode.append(" OnFwd(OUT_" + leftMotor + ", " + Math.abs(speed) + ");\n");
		this.nxcCode.append(" OnFwd(OUT_" + rightMotor + ", " + Math.abs(speed) + ");\n");
	}

	/**
	 * Records the forward turns for NXT that are counter-clockwise.
	 * 
	 * @param leftMotor The letter for the left motor.
	 * @param rightMotor The letter for the right motor.
	 * @param speed The movement speed.
	 */
	public void recordTurnFwCCW(char leftMotor, char rightMotor, int speed){
		this.nxcCode.append(" // Turn Forward, Counter-clockwise\n");
		this.nxcCode.append(" OnFwd(OUT_" + leftMotor + ", " + 0 + ")\n;");
		this.nxcCode.append(" OnFwd(OUT_" + rightMotor + ", " + Math.abs(speed) + ");\n");
	}

	/**
	 * Records the forward turns for NXT that are clockwise.
	 * 
	 * @param leftMotor The letter for the left motor.
	 * @param rightMotor The letter for the right motor.
	 * @param speed The movement speed.
	 */
	public void recordTurnFwCW(char leftMotor, char rightMotor, int speed){
		this.nxcCode.append(" // Turn Forward, clockwise\n");
		this.nxcCode.append(" OnFwd(OUT_" + leftMotor + ", " + Math.abs(speed) + ");\n");
		this.nxcCode.append(" OnFwd(OUT_" + rightMotor + ", " + 0 + ");\n");
	}

	/**
	 * Records the backward movement for NXT.
	 * 
	 * @param leftMotor The letter for the left motor.
	 * @param rightMotor The letter for the right motor.
	 * @param speed The movement speed.
	 */
	public void recordBackward(char leftMotor, char rightMotor, int speed){
		this.nxcCode.append(" // Move Backward\n");
		this.nxcCode.append(" OnRev(OUT_" + leftMotor + ", " + Math.abs(speed) + ");\n");
		this.nxcCode.append(" OnRev(OUT_" + rightMotor + ", " + Math.abs(speed) + ");\n");
	}

	/**
	 * Records the backward turns for NXT that are counter-clockwise.
	 * 
	 * @param leftMotor The letter for the left motor.
	 * @param rightMotor The letter for the right motor.
	 * @param speed The movement speed.
	 */
	public void recordTurnBwCCW(char leftMotor, char rightMotor, int speed){
		this.nxcCode.append(" // Turn Backward, Counter-clockwise\n");
		this.nxcCode.append(" OnRev(OUT_" + leftMotor + ", " + Math.abs(speed) + ");\n");
		this.nxcCode.append(" OnRev(OUT_" + rightMotor + ", " + 0 + ");\n");
	}

	/**
	 * Records the backward turns for NXT that are clockwise.
	 * 
	 * @param leftMotor The letter for the left motor.
	 * @param rightMotor The letter for the right motor.
	 * @param speed The movement speed.
	 */
	public void recordTurnBwCW(char leftMotor, char rightMotor, int speed){
		this.nxcCode.append(" // Turn Backward, clockwise\n");
		this.nxcCode.append(" OnRev(OUT_" + leftMotor + ", " + 0 + ");\n");
		this.nxcCode.append(" OnRev(OUT_" + rightMotor + ", " + Math.abs(speed) + ");\n");
	}

	/**
	 * Records the counter-clockwise rotation for NXT.
	 * 
	 * @param leftMotor The letter for the left motor.
	 * @param rightMotor The letter for the right motor.
	 * @param speed The movement speed.
	 */
	public void recordRotateCCW(char leftMotor, char rightMotor, int speed){
		this.nxcCode.append(" // Rotate Counter-clockwise\n");
		this.nxcCode.append(" OnRev(OUT_" + leftMotor + ", " + Math.abs(speed) + ");\n");
		this.nxcCode.append(" OnFwd(OUT_" + rightMotor + ", " + Math.abs(speed) + ");\n");
	}

	/**
	 * Records the clockwise rotation for NXT.
	 * 
	 * @param leftMotor The letter for the left motor.
	 * @param rightMotor The letter for the right motor.
	 * @param speed The movement speed.
	 */
	public void recordRotateCW(char leftMotor, char rightMotor, int speed){
		this.nxcCode.append(" // Rotate clockwise\n");
		this.nxcCode.append(" OnFwd(OUT_" + leftMotor + ", " + Math.abs(speed) + ");\n");
		this.nxcCode.append(" OnRev(OUT_" + rightMotor + ", " + Math.abs(speed) + ");\n");
	}

	/**
	 * Records the wait time for each movement in millis.
	 * @param wait the length of time in millis, that NXT keeps moving.
	 */
	public void recordWait(long wait){
		this.nxcCode.append(" Wait(" + wait + ");\n\n");
	}

	/**
	 * Writes that recorded NXC code to clicpboard.
	 */
	public void codeToClipboard(){
		this.nxcCode.append("}");
		StringSelection stringSelection = new StringSelection(nxcCode.toString());
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, null);
		this.nxcCode.deleteCharAt(this.nxcCode.lastIndexOf("}"));
	}
}
