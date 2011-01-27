package com.jbricx.communications;

import com.jbricx.communications.NXT.ConnectionType;
import com.jbricx.communications.exceptions.NXTNotFoundException;
import com.jbricx.communications.exceptions.UnableToCreateNXTException;
import com.jbricx.ui.findbrick.FindBrickFileIO;
import java.util.ArrayList;
import java.util.List;

public class NXTManager {

  //private static HashMap<String, NXTObserver> nxtobservers = new HashMap<String, NXTObserver>();
  private List<NXTObserver> nxtObservers;
  private static boolean running = false;
  private static final int WAITTIME = 500;
  private static NXTManager instance = null;
  private AbstractNXTBrick nxt = null;
  private Thread connectedRunnable = new Thread() {
    //@Override

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

  private NXTManager() {
    nxtObservers = new ArrayList<NXTObserver>();
  }

  public static NXTManager getInstance() {
    instance = instance != null ? instance : new NXTManager();
    return instance;
  }

  public AbstractNXTBrick connect(ConnectionType type) {
    try {
      nxt = new WindowsNXTBrick(); //BrickCreator.createBrick();
      //System.out.println("Manager Connected: " + name + " type: " + type.getName());

      nxt.NXTConnect(type);
      nxt.playTone(2000, 200);

      if (!connectedRunnable.isAlive()) {
        connectedRunnable.start();
      }
    } catch (NXTNotFoundException e) {
      //JOptionPane.showMessageDialog(null, "No bricks found...");
      System.out.println("NXTManager.connect() :: Trying to connect to " + type.getName() + " but failed!");
    } catch (UnableToCreateNXTException e) {
      System.out.println("NXTManager.connect() :: Unable to create NXT...");
    }
    notifyAllObservers(nxt.isConnected());

    return nxt;
  }

  public void notifyObserver(NXTObserver o, boolean isConnected) {
    o.update(isConnected);
  }

  public void notifyAllObservers(boolean isConnected) {
    for (NXTObserver nxtObserver : nxtObservers) {
      nxtObserver.update(isConnected);
    }
  }

  public AbstractNXTBrick getBrick() throws NXTNotFoundException {
    if (!nxt.isConnected()) {
      if (!connectedRunnable.isAlive()) {
        System.out.println("Starting thread");
        connectedRunnable.start();

        return nxt;
      } else {
        throw new NXTNotFoundException("NXT was not found!");
      }
    } else {
      return nxt;
    }
  }

  public boolean isBrickConnected() {
    return nxt.isConnected();
  }

  public void checkBricks() {
    if (!connectedRunnable.isAlive()) {
      connectedRunnable.start();
    }

    notifyAllObservers(nxt.isConnected());
  }

  public void register(String name, NXTObserver observer) {
    //System.out.println(observer + " registered for : " + name);
    nxtObservers.add(observer);
  }

  public static void main(String args[]) {
    try {
      final String brickName = "myBrick";

      class Test implements Runnable {

        boolean connected = false;
        boolean started = false;

        
        public void nxtConnected(String name, AbstractNXTBrick nxt) {
          System.out.println("\tConnected: " + name);
        }

        
        public void nxtDisconnected(String name, AbstractNXTBrick nxt) {
          System.out.println("\t--Disconnected: " + name);
          connected = false;
        }

        public void go(String name) {
          try {
            AbstractNXTBrick nxt;
            if (!started) {
              nxt = NXTManager.getInstance().connect(FindBrickFileIO.getCT());
              started = true;
            } else {
              System.out.println("\tgetting brick");
              nxt = NXTManager.getInstance().getBrick();
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

      			AbstractNXTBrick nxt = NXTManager.getInstance().connect(ConnectionType.USB);

            if(nxt.isConnected()){
      			System.out.println(nxt.getBatteryLevel());
      			nxt.playTone(2000, 500);
      			System.out.println(nxt.isConnected());
      
      			nxt.motorOn(NXT.Motor.MOTOR_A, 100);
      }
      			Thread.sleep(1000);
      			nxt.motorOff(NXT.Motor.MOTOR_A);
      
      			System.out.println(nxt);
      
      			Thread.sleep(100);
      			AbstractNXTBrick nxt2;
      
      			//nxt2 = getBrick(brickName);
      			//System.out.println("Connected: "+nxt.isConnected());
      			//nxt2.motorOff(NXT.Motor.MOTOR_A);
      			//System.out.println("DONE");
      //
      //			System.out.println(nxt == nxt2);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
