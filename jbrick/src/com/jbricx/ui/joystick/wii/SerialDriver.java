package com.jbricx.ui.joystick.wii;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.TooManyListenersException;

import javax.comm.CommPortIdentifier;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.SerialPortEvent;
import javax.comm.UnsupportedCommOperationException;

import com.jbricx.communications.exceptions.NotConnectedException;

/**
 * @author Mike Goldstein
 */
public class SerialDriver implements javax.comm.SerialPortEventListener {

    private static final boolean debug = false;
    InputStream inStr;
    OutputStream outStr;
    // byte buffer[] = new byte[32768];
    int bufferIndex;
    int bufferLast;
    SerialPort port;
    int rate;
    int parity;
    int databits;
    int stopbits;
    String com;
    int numTimesInPortConnect = 0;
    boolean monitor = false;
    public boolean connected = false;
    volatile LinkedList<Byte> buffer;
    Thread thread;
    Serial_Event se;

    public SerialDriver(String com, int rate) {
        sInit(com, rate);
    }

    public SerialDriver(Serial_Event se, String com, int rate) {
        this.se = se;
        sInit(com, rate);

    }
    //closes the port

    public void killSerial() {
        port.close();
    }

    private void sInit(String com, int rate) {
        this.com = com;
        this.rate = rate;
        this.parity = SerialPort.PARITY_NONE;
        this.databits = 8;
        stopbits = SerialPort.STOPBITS_1;
        buffer = new LinkedList<Byte>();
        try {
            port = null;
            //get a list of com ports from the dll, then check which ones are serial
            //if the predefined com-port is on the list then we can connect to it
            Enumeration portList = javax.comm.CommPortIdentifier.getPortIdentifiers();
            while (portList.hasMoreElements()) {
                CommPortIdentifier portId = (CommPortIdentifier) portList.nextElement();

                if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                    System.out.println(portId.getName());
                    // System.out.println("found " + portId.getName());
                    if (portId.getName().equals(com)) {
                        // System.out.println("looking for "+iname);
                        portConnect(portId);
                        // System.out.println("opening, ready to roll");
                    }
                }
            }
        } catch (PortInUseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (port == null) {
            System.out.println("Port null");
        } else {
            System.out.println("INIT FINISHED");
            connected = true;
        }
    }

    /**
     * collect to the port in question and add listeners to this object
     * @param portId
     * @throws PortInUseException
     * @throws IOException
     * @throws UnsupportedCommOperationException
     * @throws TooManyListenersException
     */
    private void portConnect(CommPortIdentifier portId)
            throws PortInUseException, IOException,
            UnsupportedCommOperationException, TooManyListenersException {
        port = null;
        // portId.removePortOwnershipListener("Serializationlol");
        // portId.
        port = (SerialPort) portId.open("Serializationlol",
                2000 + numTimesInPortConnect);
        inStr = port.getInputStream();
        outStr = port.getOutputStream();
        port.setSerialPortParams(rate, databits, stopbits, parity);
        port.addEventListener(this);
        port.notifyOnDataAvailable(true);
        numTimesInPortConnect++;
    }
    /**
     * reconnect thread - if you disconnect the arduino from the PC while its connected it
     * should wait in this thread until it finds our arduino...then it will reconnect
     * and continue
     */
    private Runnable reconnect = new Runnable() {

        public void run() {
            System.out.println("in reconnect");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            while (true) {
                System.out.println("scanning inputs");
                Enumeration portList = CommPortIdentifier.getPortIdentifiers();
                while (portList.hasMoreElements()) {
                    CommPortIdentifier portId = (CommPortIdentifier) portList.nextElement();
                    if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                        String x = portId.getName();
                        if (x.equals(com)) {
                            // reconnected.
                            try {
                                portConnect(portId);
                                thread.destroy();
                            } catch (PortInUseException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            } catch (UnsupportedCommOperationException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            } catch (TooManyListenersException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                        System.out.println(x);
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }

        }
    };

    /**
     * this is the method the dll will call when there is a serial event - synchronized because
     * it will call this method every single time a byte is recieved,
     * and it cannot run through the code fast enough - was causing problems with out it
     */
    @Override
    synchronized public void serialEvent(SerialPortEvent serialEvent) {
        boolean error = false;
        //this is just checking if the objects still exist - the serial driver is crap
        //if calling this method returns an error, do not proceed
        //kill the port and enter the reconnect routine
        //the driver spams this method if you unplug the car without this try/catch
        try {
            inStr.available();
        } catch (IOException e) {
            System.out.println("IO EXCEPTION - Disconnected from arduino");
            error = true;
            thread = new Thread(reconnect);
            thread.start();
            port.close();
            System.out.println("AFTER CLOSE");
        }
        //is error==false dead code???
        if (error == false) {
            //since the only type of event set to call this method is data_av
            //this might not be needed
            if (serialEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
                if (debug) {
                    System.out.println("");
                    System.out.print("SerialPort DataEvent");
                }
                //read in all the values and save them to the byte buffer
                try {
                    int av = inStr.available();
                    if (debug) {
                        System.out.print("Num AV: " + av + "; ");
                    }
                    while (av > 0) {
                        //do not want to be writing to this while something else is reading
                        synchronized (buffer) {
                            byte b = (byte) inStr.read();
                            if (debug) {
                                System.out.print("; Val: " + ((int) b & 0x000000ff));
                            }
                            buffer.addLast(b);
                            av = inStr.available();
                        }
                    }
                    //if listener is enabled and we've gotten 5+ bytes in the buffer...
                    if (se != null && buffer.size() > 4) {
                        //call serial listener
                        se.serialListener();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Some other type of event: "
                        + serialEvent.getEventType());
            }
        }

    }

    /**
     *
     * @return number of bytes waiting
     */
    public int available() {
        return buffer.size();
    }

    /**
     * bool version of above - true if it isnt empty
     * @return
     */
    public boolean availableBool() {
        boolean temp = false;
        if (buffer.size() > 0) {
            temp = true;
        }
        return temp;
    }

    /**
     * return an integer value of whatever is in the front of the byte buffer
     * @return
     */
    public int readInt() {
        // if (bufferIndex == bufferLast) return -1;
        // int outgoing=0;
        int val = -1;
        synchronized (buffer) {
            if (buffer.size() > 0) {
                val = (int) (buffer.pop()) & 0x000000ff;
            }
        }
        return val;
    }

    /**
     * Returns the next byte in the buffer as a char. Returns -1, or 0xffff, if
     * nothing is there.  (might be dead code)
     */
    public char readChar() {
        if (availableBool()) {
            return (char) ((byte) readInt());
        }
        return (char) (-1);
    }

    /**
     * write a byte to the outgoing buffer if we are connected
     * @param b
     * @throws IOException
     * @throws NotConnectedException
     */
    public void write(byte b) throws IOException, NotConnectedException {
        if (connected) {
            byte[] bb = new byte[1];
            bb[0] = b;
            outStr.write(bb);
            //	System.out.println("Sent byte");
        } else {
            throw new NotConnectedException("Not connected to arduino...");
        }
    }
}
