package com.jbricx.communications;

import java.util.HashMap;

import com.jbricx.communications.NXT.*;

public class NXTManager {


	private static HashMap<String,AbstractNXTBrick> nxtstore = new HashMap<String,AbstractNXTBrick>();
	private static HashMap<String,Boolean> nxtenabled = new HashMap<String,Boolean>();
	private static HashMap<String,Boolean> nxtconnected = new HashMap<String,Boolean>();
	private static HashMap<String,NXTObserver> nxtobservers = new HashMap<String,NXTObserver>();
	
	private static boolean running = false;
	private static final int WAITTIME = 5000;
	
	private static Thread connectedRunnable= new Thread() {
		
		@Override
		public void run() {
			running = true;
			while(running){
				try {
					Thread.sleep(WAITTIME);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				checkBricks();
			}
		}
	};
	
	public static AbstractNXTBrick connect(String name, ConnectionType type) throws NXTNotFoundException, UnableToCreateNXTException{
		AbstractNXTBrick nxt = BrickCreator.createBrick();
		
		nxt.NXTConnect(ConnectionType.USB);
		
//		nxt.playTone(2700, 1000);
		
		nxtstore.put(name, nxt);
		nxtenabled.put(name, true);
		nxtconnected.put(name, nxt.isConnected());
		
		if(!connectedRunnable.isAlive()){
			System.out.println("Starting thread");
			connectedRunnable.start();
		}
		return nxt;
	}
	
	public static AbstractNXTBrick getBrick(String name) throws NXTNotFoundException{
		
		if (nxtstore.containsKey(name)){
			if(!connectedRunnable.isAlive()){
				System.out.println("Starting thread");
				connectedRunnable.start();
			}
			return nxtstore.get(name);
		}
		else{
			throw new NXTNotFoundException("No NXT with name: "+name);
		}
		
	}
	
	public static boolean isConnected(String name) throws NXTNotFoundException{
		AbstractNXTBrick nxt = getBrick(name);
		return nxt.isConnected();
	}
	
	private static void checkBricks(){
		for (String name: nxtstore.keySet()){
			System.out.println("checking: "+name);
			if(!nxtstore.get(name).isConnected()){
				nxtobservers.get(name).nxtDisconnected(name, nxtstore.get(name));
				nxtconnected.put(name,false);
				running = false;
			}
			else{
				if (!nxtconnected.get(name)){
					if(!connectedRunnable.isAlive()){
						connectedRunnable.start();
					}
					nxtconnected.put(name,true);
					nxtobservers.get(name).nxtConnected(name, nxtstore.get(name));
				}
			}
		}
	}

	public static void register(String name, NXTObserver observer){
		System.out.println(observer + " registered for : "+name);
		nxtobservers.put(name, observer);
	}
	
	public static void main(String args[]){
		try{
			String brickName = "one";
			
			class Test implements NXTObserver,Runnable{
				boolean connected = false;
				boolean started = false;
				@Override
				public void nxtConnected(String name, AbstractNXTBrick nxt) {
					System.out.println("\tConnected: "+name);
				}

				@Override
				public void nxtDisconnected(String name, AbstractNXTBrick nxt) {
					System.out.println("\t--Disconnected: "+name);
					connected = false;
				}
				
				public void go(String name){
					try {
						AbstractNXTBrick nxt;
						if(!started){
							 nxt = NXTManager.connect(name, ConnectionType.USB);
							started=true;
						}
						else{
							System.out.println("\tgetting brick");
							nxt = NXTManager.getBrick(name);
						}
						connected = true;
						System.out.println(nxt.getBatteryLevel());
						Thread.sleep(2000);
						System.out.println("Done Sleeping");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						System.out.println("Couldn't connect");
					}
				}

				@Override
				public void run() {
					while(true){
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						if(!connected){
							System.out.println("\t--notconnected");
							go("one");
						}
					}
					
				}
			}
			
			String name = "one";
			Test t = new Test();
			NXTManager.register(name, t);
			t.go(name);
			(new Thread(t)).start();
			System.out.println("main done");
			
			
			
			
//			AbstractNXTBrick nxt = connect(brickName, ConnectionType.USB);
//			System.out.println(nxt.getBatteryLevel());
//			nxt.playTone(2000, 500);
//			System.out.println(nxt.isConnected());
//			
//			nxt.motorOn(Motor.MOTOR_A, 100);
//			Thread.sleep(1000);
//			nxt.motorOff(Motor.MOTOR_A);
//			
//			System.out.println(nxt);
//			Thread.sleep(100);
//			AbstractNXTBrick nxt2;
//			nxt2 = getBrick(brickName);
//			System.out.println("Connected: "+nxt.isConnected());
//			nxt2.motorOff(NXT.Motor.MOTOR_A);
//			System.out.println("DONE");
//			
//			System.out.println(nxt == nxt2);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	
}