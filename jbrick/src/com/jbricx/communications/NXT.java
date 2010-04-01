package com.jbricx.communications;
import com.sun.jna.Pointer;
import java.nio.ByteBuffer;
//import Fantom;
//import treelaws.fantom.Status;
import java.util.Enumeration;
import java.util.HashMap;


/**
 * 
 * @author Spencer Herzberg
 */



public class NXT {

	public boolean isConnected = false;
	
	public enum Motor{
		MOTOR_A ((byte)0x00, "Motor A"),
		MOTOR_B ((byte)0x01, "Motor B"),
		MOTOR_C ((byte)0x02, "Motor C");
		
		private byte port;
		private String name;
		Motor(byte port, String name){
			this.port = port;
			this.name = name;
		}
		public String getName(){return name;}
		public byte getPort(){return port;}
		
		public static Motor getMotorByName(String name){
			for (Motor m: values()){
				if (m.getName().equals(name)){
					return m;
				}
			}
			return null;
			
		}
	}
	
	public static final HashMap<String, Motor> MOTORS = new HashMap<String, Motor>();
	static{
		for (Motor m : Motor.values()){
			MOTORS.put(m.getName(), m);
		}
	}
	

	public enum Sensor{
		SENSOR_1 ((byte)0x00, "Sensor 1"),
		SENSOR_2 ((byte)0x01, "Sensor 2"),
		SENSOR_3 ((byte)0x02, "Sensor 3"),
		SENSOR_4 ((byte)0x03, "Sensor 4");
		
		private byte port;
		private String name;
		private SensorType type;
		private SensorMode mode;
		private boolean enabled;
		
		Sensor(byte port, String name){
			this.port = port;
			this.name = name;
			this.type = SensorType.NONE;
			this.mode = SensorMode.BOOLEAN;
		}
		public String getName(){return name;}
		public byte getPort(){return port;}
		public byte getType(){return type.getType();}
		public byte getMode(){return mode.getMode();}
		
		public void setMode(SensorMode mode){
			this.enabled = true;
			this.mode = mode;
		}
		public void setType(SensorType type){
			if (type == SensorType.NONE){
				this.enabled = false;
			}
			else{this.enabled = true;}
			this.type = type;
		}
		public boolean isEnabled(){return enabled;}
	}
	
	public static final HashMap<String, Sensor> SENSORS = new HashMap<String, Sensor>();
	static{
		for (Sensor s : Sensor.values()){
			SENSORS.put(s.getName(), s);
		}
	}
	
	public enum SensorType{
		NONE ((byte)0x00, "None", SensorMode.RAW),
		SWITCH ((byte)0x01, "Switch", SensorMode.BOOLEAN),
		SOUNDDB ((byte)0x07, "Sound DB", SensorMode.RAW),
		REFLECTION ((byte)0x03, "Reflection", SensorMode.RAW),
		LOWSPEED9V ((byte)0x0B, "Low Speed 9V", SensorMode.RAW),
		TEMPERATURE ((byte)0x02, "Temperature", SensorMode.FAHRENHEIT),
		ANGLE ((byte)0x04, "Angle", SensorMode.RAW),
		LIGHTACTIVE ((byte)0x05, "Light Active", SensorMode.RAW),
		LIGHTINACTIVE ((byte)0x06, "Light Inactive", SensorMode.RAW),
		SOUNDDBA ((byte)0x08, "Sound DBA", SensorMode.RAW),
		CUSTOM ((byte)0x09, "Custom", SensorMode.RAW),
		LOWSPEED ((byte)0x0A, "Low Speed", SensorMode.RAW),
		TOUCH ((byte)0x0C, "Touch", SensorMode.RAW);
		
		private byte type;
		private String name;
		private SensorMode defaultMode;
		
		SensorType(byte type, String name, SensorMode mode){
			this.type = type;
			this.name = name;
			this.defaultMode = mode;
		}
		public String getName(){return name;}
		public byte getType(){return type;}
		public SensorMode getMode(){return defaultMode;}
		
		public static SensorType getTypeByName(String name){
			for (SensorType s: values()){
				if (s.getName().equals(name)){
					return s;
				}
			}
			return null;
			
		}
	}
	
	public enum SensorMode{
		RAW ((byte)0x00, "Raw"),
		BOOLEAN ((byte)0x20, "Boolean"),
		TRANSITIONCNT ((byte)0x40, "Transition CNT"),
		PERIODCOUNTER ((byte)0x60, "Period Counter"),
		PCTFULLSCALE ((byte)0x80, "PCT Full Scale"),
		CELSIUS ((byte)0xA0, "Celsius"),
		FAHRENHEIT ((byte)0xC0, "Fahrenheit"),
		ANGLESTEP ((byte)0xC0, "Angle Step"),
		SLOPEMASK ((byte)0xE0, "Slope Mask"),
		MASK ((byte)0xE0, "Mask");
		
		private byte mode;
		private String name;
		
		SensorMode(byte mode, String name){
			this.mode = mode;
			this.name = name;
		}
		public String getName(){return name;}
		public byte getMode(){return mode;}
		
		public static SensorMode getTypeByName(String name){
			for (SensorMode m: values()){
				if (m.getName().equals(name)){
					return m;
				}
			}
			return null;
			
		}
		
	}
	
	// TODO Change this to an enumeration?
	public enum ConnectionType{
		USB ("USB"),
		BLUETOOTH ("BTH");
		
		private String name;
		ConnectionType(String name){
			this.name = name;
		}
		public String getName(){return this.name;}
	}
	
    private static Fantom fantom= Fantom.INSTANCE;
    
    private String name;
    private Pointer nxtPointer;

    public NXT(String name) throws NXTNotFoundException, UnableToCreateNXTException {
        this.name= name;
        this.nxtPointer= connect(name);
    }
    
    public NXT() throws NXTNotFoundException, UnableToCreateNXTException {
        this.name= "dc";
        this.nxtPointer= directConnect();
    }
    
    public Pointer getPointer(){
    	return this.nxtPointer;
    }
    
    public ExitStatus download(String filename){
    	System.out.println("lodingdown");
    	return new ExitStatus(0,"Download failed");
    }
    
    public void startProgram(String filename) {
        Status status= new Status();
        byte[] filenameBytes= filename.getBytes();
        ByteBuffer command= ByteBuffer.allocate(filenameBytes.length+1+1);
        command.put((byte)0x00);
        command.put(filenameBytes);
        command.put((byte)0x00);
        fantom.nFANTOM100_iNXT_sendDirectCommand(nxtPointer, true, command, command.capacity(), null, 0, status);
        System.out.println(status.getStatus().toString());
    }
    
    public void stopProgram() {
        ByteBuffer command= ByteBuffer.allocate(1);
        command.put((byte)0x01);
        fantom.nFANTOM100_iNXT_sendDirectCommand(nxtPointer, true, command, 1, null, 0, new Status());
    }

    private Pointer connect(String name) throws NXTNotFoundException, UnableToCreateNXTException {
        Status status= new Status();
        Pointer iNXTIterator= fantom.nFANTOM100_createNXTIterator(true, 5, status);
        try {
            while(!Status.Statuses.NO_MORE_ITEMS_FOUND.equals(status.getStatus())) {
                byte[] resourceName= FantomUtils.newResourceName();
                fantom.nFANTOM100_iNXTIterator_getName(iNXTIterator, resourceName, status);
                System.out.println(FantomUtils.asString(resourceName));
                if (FantomUtils.asString(resourceName).contains(name)) {
                    Pointer iNXT= fantom.nFANTOM100_iNXTIterator_getNXT(iNXTIterator, status);
                    if (Status.Statuses.SUCCESS.equals(status.getStatus())) {
                    	this.isConnected = true;
                        return iNXT;
                    } else {
                        throw new UnableToCreateNXTException(" not able to create connection");
                    }
                }
                fantom.nFANTOM100_iNXTIterator_advance(iNXTIterator, status);
            }
        } finally {
            if (iNXTIterator != null) {
                fantom.nFANTOM100_destroyNXTIterator(iNXTIterator, status);
            }
        }
        throw new NXTNotFoundException(" no nxt found");
    }
    
    
    private Pointer directConnect()throws UnableToCreateNXTException
    {
    	Status status= new Status();
    	Pointer iNXT = fantom.nFANTOM100_createNXT("BTH::NXT::00:16:53:09:96:B4::3", status, false);
    	
    	if (Status.Statuses.SUCCESS.equals(status.getStatus())) {
    		return iNXT;
    	} else {
    		throw new UnableToCreateNXTException( " not able to create connection" );
    	}
    }
    
    
    public int getBattery()
    {
	    Status status= new Status();
	    ByteBuffer command= ByteBuffer.allocate(1);
	
	    command.put((byte)0x0B);//direct command get battery
	
	    byte[] response = new byte[4]; //this 4 bytes are vital to get the following readings right

	    fantom.nFANTOM100_iNXT_sendDirectCommand(nxtPointer, true, command, command.capacity(), response, response.length, status);
	    return response[0];
    }
    
    public void checkConnection(){
    	Status status= new Status();
	    ByteBuffer command= ByteBuffer.allocate(1);
	
	    command.put((byte)0x0B);//direct command get battery
	
	    byte[] response = new byte[4]; //this 4 bytes are vital to get the following readings right
	    
	    fantom.nFANTOM100_iNXT_sendDirectCommand(nxtPointer, true, command, command.capacity(), response, response.length, status);
	    
	    boolean result;
	    if ((int)response[0] == 0){
	    	result = false;
	    }
	    else{
	    	result = true;
	    }
	    System.out.println("Done Checking"+ result);
	    this.isConnected = result;
    }
    
    public boolean isConnected(){
    	this.checkConnection();
    	return this.isConnected;
    }
    
    public void playSound(int freq, int duration)
    {
	    Status status= new Status();
	    ByteBuffer command= ByteBuffer.allocate(6);
	
	    command.put((byte)0x03);//direct command play tone
	    command.put((byte)freq);
	    command.put((byte)(freq >>8));
	    command.put((byte)duration);
	    command.put((byte)(duration>>8));
	    command.put((byte)0x00);
		    
	    fantom.nFANTOM100_iNXT_sendDirectCommand(nxtPointer, true, command, command.capacity(), null, 0, status);
    }
    
    public void runMotor(byte motorName, int speed)
    {
    	//Restrict Speed to 100
    	if (speed > 100){
    		speed = 100;
    	}else if(speed < -100){
    		speed= -100;
    	}

		Status status= new Status();
		ByteBuffer command= ByteBuffer.allocate(12);

		command.put((byte)0x04);//Motor Control, 4
		command.put((byte)motorName);//Motor Number 0,1,2, 2
		command.put((byte)speed);//Speed, 75
		command.put((byte)0x01);//Mode, 5 - Changed from 5 to 1 to de-reg
		command.put((byte)0x00);//Reg mode, 1 - Changed from 1 to 0 
		command.put((byte)0x00);//Turn Ratio, 0
		command.put((byte)0x20);//Run State, 32 - Changed from duration to be 0x20 = running
		command.put((byte)0x00);//Tacholimit = 0>> 8, 1
		command.put((byte)0x00);//Tacholimit = 0>> 16, 1
		command.put((byte)0x00);//Tacholimit = 0>> 24, 0
		command.put((byte)0x00);//Tacholimit = 0>> 32, 0
		command.put((byte)0x00);//Tacholimit = 0>> 40, 0
 	   
 	    

		try{
 	    	fantom.nFANTOM100_iNXT_sendDirectCommand(nxtPointer, false, command, command.capacity(), null, 0, status);
		}
		catch(NullPointerException e){
			this.isConnected = false;
		}

    	
    }
    
    
    
    public void stopMotor(byte motorName)
	{
		Status status= new Status();
		ByteBuffer command= ByteBuffer.allocate(12);
		
		command.put((byte)0x04);//LSREAD
		command.put((byte)motorName);//port
		command.put((byte)0x00);//power -100 - 100
		command.put( (byte)0x00);//mode
		command.put( (byte)0x01);//regulation
		command.put( (byte)0x00);//turnratio
		command.put( (byte)0x00);//runstate
		command.put( (byte)0x00);//tacholimit = 0
		command.put( (byte)0x00);//tacholimit = 0
		command.put( (byte)0x00);//tacholimit = 0
		command.put( (byte)0x00);//tacholimit = 0
		command.put( (byte)0x00);//tacholimit = 0
		
		try{
			fantom.nFANTOM100_iNXT_sendDirectCommand(nxtPointer, false, command, command.capacity(), null, 0, status);
		}
		catch(NullPointerException e){
			this.isConnected = false;
		}
	}
    
    
    public void resetMotor(byte motorName)
	{
		Status status= new Status();
		ByteBuffer command= ByteBuffer.allocate(2);
		
		command.put((byte)0x0A);//reset
		command.put((byte)motorName);//port
		
		try{
			fantom.nFANTOM100_iNXT_sendDirectCommand(nxtPointer, false, command, command.capacity(), null, 0, status);
		}
		catch(NullPointerException e){
			this.isConnected = false;
		}
	}

    
    
    public void setSensorType(String name, SensorType type){
    	Sensor s = SENSORS.get(name);
    	s.setType(type);
    	
		resetSensor(s);
    }
    public void setSensorMode(String name, SensorMode mode){
    	Sensor s = SENSORS.get(name);
    	s.setMode(mode);
    	
    	resetSensor(s);
    }
    
    private void resetSensor(Sensor s){
    	Status status= new Status();
		ByteBuffer command= ByteBuffer.allocate(4);
	
		command.put((byte)0x05);//setinputmode //TODO what is this?
		command.put((byte)s.getPort());//port
		command.put((byte)s.getType());//sensortype LOWSPEED_9V - Changed to switch
		command.put((byte)s.getMode());//sensormode - Changed 
	
		byte[] response = new byte[2];

		fantom.nFANTOM100_iNXT_sendDirectCommand(nxtPointer, true, command, command.capacity(), response, response.length, status);

		// TODO FIX ME
		//return response[0]; //status
    }
    
    public byte getRawSensorValue(String sensorName){
    	return getSensorValues(sensorName)[11];
    }
    public int getConvertedSensorData(String sensorName, byte sensorMode){
    	int finalVal;
    	byte response[] = getSensorValues(sensorName);
    	
    	if(sensorMode != (byte)0x20){
	    	int buf[] = new int[2];
			buf[0]=unsignedByteToInt(response[9]);
			buf[1]=unsignedByteToInt(response[10]);
			finalVal = buf[0] + (buf[1] << 8);
    	}else{
    		finalVal = (int)getBoolVal(sensorName);
    	}
		return finalVal;
    }
    private int getBoolVal(String sensorName){
    	byte response[] = getSensorValues(sensorName);
    	int returnVal=0;
    	if(response[11] == 1){
    		returnVal=1;
    	}
    	return returnVal;
		
    	
    }
	public byte[] getSensorValues(String sensorName){
		Sensor s = SENSORS.get(sensorName);
		//used for the distance
		Status status= new Status();
		ByteBuffer command= ByteBuffer.allocate(2);
	
//		command.put((byte)0x00);//setinputmode// TODO not needed??
		command.put((byte)0x07);//setinputmode// TODO Why 7?
		command.put((byte)s.getPort());//port
	
		byte[] response = new byte[15];
	
		fantom.nFANTOM100_iNXT_sendDirectCommand(nxtPointer, true, command, command.capacity(), response, response.length, status);
		
		for (int i=0;i<15;i++){
			System.out.print(response[i]);
			System.out.print("\t");
		}
		int buf[] = new int[2];
		int finalVal;
		buf[0]=unsignedByteToInt(response[9]);
		buf[1]=unsignedByteToInt(response[10]);
		finalVal = buf[0] + (buf[1] << 8);
		System.out.println();
		System.out.println(" buf0: " + buf[0] + "; buf1: " + buf[1] + "; Final Value: " + finalVal);
		
		System.out.println();
		System.out.println(response[2]+ " "+response[1]);
		return response; //number of bytes ready to read
	}
	public int unsignedByteToInt(byte b) {
	    return (int) b & 0xFF;
	    }

    
/*    private Pointer directConnect()throws UnableToCreateNXTException {
	    Status status= new Status();
	    Pointer iNXT = fantom.nFANTOM100_createNXT("BTH::5brickXT::00:16:53:01:BA:74::8", status, false );

	    if (Status.Statuses.SUCCESS.equals(status.getStatus())) {
	    	return iNXT;
	    } else {
	    	throw new UnableToCreateNXTException( " not able to create connection" );
	    }
    }*/
    
	public static void main(String args[]){

		System.out.println("----");
		try {
			NXT nxt = new NXT("USB");
			System.out.println("----");
			for (int i=0;i<10;i++){
				System.out.println("i "+i);
				System.out.println(nxt.isConnected());
				
			}
			System.out.println("main done");
			
		} catch (NXTNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnableToCreateNXTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Totally Done");
		
	}
}





