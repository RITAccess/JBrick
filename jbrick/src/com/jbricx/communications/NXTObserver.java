package com.jbricx.communications;

public interface NXTObserver {

  //previous implmentations
  /*  public abstract void nxtConnected(String name, AbstractNXTBrick nxt);
  public abstract void nxtDisconnected(String name, AbstractNXTBrick nxt);
   */
  public void update(boolean isConnected);
}
