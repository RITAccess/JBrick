package com.jbricx.ui.joystick.wii;

import com.jbricx.communications.NXTGadgetManager;
import com.jbricx.communications.enums.Motor;
/**
 * @author Mike Goldstein
 */
public class WiiMain implements WiiPacketEvent, Runnable {
	NunchuckConnection NCC;
	static NXTGadgetManager nxt;
	boolean running = true;
	private static final boolean debug=true;
public WiiMain(NXTGadgetManager x){
	nxt=x;
	running = true;
}
	/**
	 * @param args
	 */
	// static SerialDriver s;

/*private static void connectNXT(){
		
		String brickname = "brick2";
		try {
			nxt = NXTManager.connect(brickname, ConnectionType.BLUETOOTH);
			nxt.playTone(2000, 300);
			System.out.println("Joystick: Brick Connected!");
			nxt.playTone(3000, 300);
		} catch (AlreadyConnectedException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Joystick already Connected");
			try {
				nxt = NXTManager.getBrick(brickname);
				nxt.playTone(2000, 300);
				System.out.println("Joystick: Brick Connected!");
			} catch (NXTNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.out.println("Could not find brick");
			}
		} catch (NXTNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnableToCreateNXTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}*/
static Motor Motor_1_ID = Motor.MOTOR_A;
static Motor Motor_2_ID = Motor.MOTOR_C;
static int Motor_1_DIR = 1;
static int Motor_2_DIR = 1;



	static short getUnsignedByte(byte b) {
		return (short) ((short) (b) & 0x00FF);
	}
//	public static void main(String[] args) {
//		/* test for getUnsignedByte();
//		byte b = (byte) 0xFF;
//		System.out.println("b as a byte: " + b + "; b as a uByte: "+ getUnsignedByte(b));
//		*/
//		// TODO Auto-generated method stub
//		//new Thread(sendToLego).start();
//		connectNXT();
//		new WiiMain().run2();
//		
//
//		
//	}
		public void killWiiThreads(){
			running=false;
		}
/**
 * This method will sent 0xac to the arduino once every 100ms to request updated data
 *It also starts the connection object...
 * 
 */
	@Override
	public void run(){
		
		System.out.println("Nunchuck RUn Command started");
		NCC = new NunchuckConnection(this);

		while (running) {
			
			NCC.sendByte((byte) 0xAC);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		NCC.killSerial();
		NCC=null;

	};

	/** this method will be called by a lower layer
	*    when we get a new packet
	*    It translates the data in the packet to values the brick expects
	*    After the data is transformed, it sends it off to the brick
	**/
	@Override
	public void onNewData(WiiPacket packet) {
		// TODO Auto-generated method stub
		
		int aSpeed=0, cSpeed =0;
		if(packet.yPos>=10){
			aSpeed=(int)( 50.0 + (double)(packet.yPos) *0.5);
			cSpeed=(int)( 50.0 + (double)(packet.yPos) *0.5);
		}
		else if (packet.yPos <= -10){
			aSpeed=((int)( - 50.0 + (double)(packet.yPos) * 0.5));
			cSpeed=((int)( - 50.0 + (double)(packet.yPos) * 0.5));
		}
		
		if(packet.xPos > 10){
			if(packet.yPos >= 10)
				cSpeed += (int)( (double)(packet.xPos) * 0.5);
			else if (packet.yPos <= -10)
				cSpeed -= (int)( (double)(packet.xPos) * 0.5);
			else
				cSpeed=packet.xPos;
		}else if (packet.xPos <= -10){
			if (packet.yPos <= -10)
				aSpeed += (int)( (double)(packet.xPos) * 0.5);
			else if (packet.yPos >= 10)
				aSpeed -= (int)( (double)(packet.xPos) * 0.5);
			else
				aSpeed=-packet.xPos;
		}
		
		
		nxt.motorOn(Motor.MOTOR_A, aSpeed);
		nxt.motorOn(Motor.MOTOR_C, cSpeed);
		
		if(debug)
			System.out.println("xPos: " + packet.xPos + "; \t yPos: " + packet.yPos
				+ "; \tcState: " + packet.cState + ";\t zState: " + packet.zState + ";\t MaSpeed: "+aSpeed+";\t McSpeed: " + cSpeed+";");
		
		if(packet.cState==true){
			nxt.playTone(1500, 500);
		}
		else if (packet.zState==true){
			nxt.playTone(1200, 500);
		}
		else{
			nxt.playTone(0, 0);
		}
		
	}
	
	
	
}
