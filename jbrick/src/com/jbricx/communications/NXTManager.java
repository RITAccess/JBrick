package com.jbricx.communications;

import java.util.HashMap;

import com.jbricx.communications.NXT.ConnectionType;
import com.jbricx.communications.exceptions.AlreadyConnectedException;
import com.jbricx.communications.exceptions.NXTNotFoundException;
import com.jbricx.communications.exceptions.UnableToCreateNXTException;
import com.jbricx.ui.findbrick.FindBrickFileIO;
import java.util.Iterator;

public class NXTManager {

  private static HashMap<String, AbstractNXTBrick> nxtstore = new HashMap<String, AbstractNXTBrick>();
//	private static HashMap<String,Boolean> nxtenabled = new HashMap<String,Boolean>();
  private static HashMap<String, Boolean> nxtstatus = new HashMap<String, Boolean>();
  private static HashMap<String, NXTObserver> nxtobservers = new HashMap<String, NXTObserver>();
  private static boolean running = false;
  private static final int WAITTIME = 500;
  private static Thread connectedRunnable = new Thread() {

    @Override
    public void run() {
      running = true;
      while (running) {
        try {
          Thread.sleep(WAITTIME);
//					System.out.println("Sleep");
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        checkBricks();
      }
    }
  };

  public static AbstractNXTBrick connect(String name, ConnectionType type) throws AlreadyConnectedException {
    AbstractNXTBrick nxt = null;
    try {
      if (nxtstore.containsKey(name)) {
        throw new AlreadyConnectedException("NXT Brick '" + name + "' is already connected");
      }
      nxt = new WindowsNXTBrick(); //BrickCreator.createBrick();
      //System.out.println("Manager Connected: " + name + " type: " + type.getName());

      nxt.NXTConnect(type);
      nxt.playTone(2000, 200);

      nxtstore.put(name, nxt);
      nxtstatus.put(name, nxt.isConnected());

      if (!connectedRunnable.isAlive()) {
        connectedRunnable.start();
      }
    } catch (NXTNotFoundException e) {
      //JOptionPane.showMessageDialog(null, "No bricks found...");
      System.out.println("NXTManager.java@63 :: Trying to connect to " + type.getName() + " but failed!");
    } catch (UnableToCreateNXTException e) {
      System.out.println("NXTManager.java@63 :: Unable to create NXT...");
    }
    NXTManager.notifyAllObservers(nxt.isConnected());
    //NXTManager.notifyAllObservers(true);
    return nxt;
  }

  public static void notifyObserver(NXTObserver o, boolean isConnected) {
    System.out.println("notified");
    o.update(isConnected);
  }

  public static void notifyAllObservers(boolean nxt) {
    Iterator observers = nxtobservers.values().iterator();
    NXTObserver o;
    while (observers.hasNext()) {
      o = (NXTObserver) observers.next();
      o.update(nxt);
    }
  }

  public static AbstractNXTBrick getBrick(String name) throws NXTNotFoundException {

    if (nxtstore.containsKey(name)) {
      if (!connectedRunnable.isAlive()) {
        System.out.println("Starting thread");
        connectedRunnable.start();
      }
      AbstractNXTBrick nxt = nxtstore.get(name);
//			nxtstatus.put(name, nxt.isConnected());
      return nxt;
    } else {
      throw new NXTNotFoundException("No NXT with name: " + name);
    }
  }

  public static boolean isConnected(String name) throws NXTNotFoundException {
    AbstractNXTBrick nxt = getBrick(name);
    return nxt.isConnected();
  }

  public static void checkBricks() {
//    for (String name : nxtobservers.keySet()) {
//       System.out.println("NXTManager.java@106::checkBricks():" + name);
//    }
    //System.out.println("d:" + nxtobservers.size());
    for (String name : nxtobservers.keySet()) {
      AbstractNXTBrick nxt = nxtstore.get(name);
      System.out.println("NXTManager.java@106::checkBricks():" + name);
      notifyObserver(nxtobservers.get(name), nxt.isConnected());
      if (!nxt.isConnected()) {//not connected
        // System.out.println("checkBricks() disconnected: "+name);
        //nxtobservers.get(name).nxtDisconnected(name, nxt);
        nxtstatus.put(name, false);
        //notifyObserver(nxtobservers.get(name), nxt);
        // running = false;
      } else {//connected
        if (!nxtstatus.get(name)) {
          // System.out.println("Connected! "+name);
          if (!connectedRunnable.isAlive()) {
            connectedRunnable.start();
          }
          nxtstatus.put(name, true);
          //nxtobservers.get(name).nxtConnected(name, nxtstore.get(name));
        }
      }
    }
  }

  public static void register(String name, NXTObserver observer) {
    //System.out.println(observer + " registered for : " + name);
    nxtobservers.put(name, observer);
  }

  public static void main(String args[]) {
    try {
      final String brickName = "myBrick";

      class Test implements Runnable {

        boolean connected = false;
        boolean started = false;

        /*@Override
        public void nxtConnected(String name, AbstractNXTBrick nxt) {
        System.out.println("\tConnected: " + name);
        }

        @Override
        public void nxtDisconnected(String name, AbstractNXTBrick nxt) {
        System.out.println("\t--Disconnected: " + name);
        connected = false;
        }*/
        public void go(String name) {
          try {
            AbstractNXTBrick nxt;
            if (!started) {
              nxt = NXTManager.connect(name, FindBrickFileIO.getCT());
              started = true;
            } else {
              System.out.println("\tgetting brick");
              nxt = NXTManager.getBrick(name);
            }
            connected = true;
            System.out.println(nxt.getBatteryLevel());
            Thread.sleep(2000);
            System.out.println("\tDone Sleeping");
          } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println("\tCouldn't connect");
          }
        }

        @Override
        public void run() {
          while (true) {
            try {
              Thread.sleep(3000);
            } catch (InterruptedException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }

            if (!connected) {
              System.out.println("\t--notconnected");
              go(brickName);
            }
          }

        }
      }

//			String name = "one";
      Test t = new Test();
      //NXTManager.register(brickName, t);
      t.go(brickName);
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
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
