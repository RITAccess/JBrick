package com.jbricx.ui.joystick.wii;

import java.io.IOException;

import com.jbricx.communications.exceptions.NotConnectedException;

public class SerialTest {

	/**
	 * @param args
	 */
	static SerialDriver s;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		s = new SerialDriver("COM14", 9600);

		while (true) {
			// System.out.println(s.readint());
			try {
				s.write((byte) 0x64);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NotConnectedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
