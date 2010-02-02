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
		NONE ((byte)0x00, "None"),
		SWITCH ((byte)0x01, "Switch"),
		TEMPERATURE ((byte)0x02, "Temperature"),
		REFLECTION ((byte)0x03, "Reflection"),
		ANGLE ((byte)0x04, "Angle"),
		LIGHTACTIVE ((byte)0x05, "Light Active"),
		LIGHTINACTIVE ((byte)0x06, "Light Inactive"),
		SOUNDDB ((byte)0x07, "Sound DB"),
		SOUNDDBA ((byte)0x08, "Sound DBA"),
		CUSTOM ((byte)0x09, "Custom"),
		LOWSPEED ((byte)0x0A, "Low Speed"),
		LOWSPEED9V ((byte)0x0B, "Low Speed 9V"),
		TOUCH ((byte)0x0C, "Touch");
		
		private byte type;
		private String name;
		
		SensorType(byte type, String name){
			this.type = type;
			this.name = name;
		}
		public String getName(){return name;}
		public byte getType(){return type;}
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
    public void startProgram(String filename) {
        Status status= new Status();
        byte[] filenameBytes= filename.getBytes();
        ByteBuffer command= ByteBuffer.allocate(filenameBytes.length+1+1);
        command.put((byte)0x00);
        command.put(filenameBytes);
        command.put((byte)0x00);
        fantom.nFANTOM100_iNXT_sendDirectCommand(nxtPointer, false, command, command.capacity(), null, 0, status);
        System.out.println(status.getStatus().toString());
    }
    
    public void stopProgram() {
        ByteBuffer command= ByteBuffer.allocate(1);
        command.put((byte)0x01);
        fantom.nFANTOM100_iNXT_sendDirectCommand(nxtPointer, false, command, 1, null, 0, new Status());
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
	
	    for(int x=0; x<6; x++){
			System.out.println("Command: " + command.get(x));
		}
	    System.out.println(0x03);
	    System.out.println(freq + " " + (byte)freq);
	    
	    
	    fantom.nFANTOM100_iNXT_sendDirectCommand(nxtPointer, false, command, command.capacity(), null, 0, status);
	    System.out.println(status.getStatus().toString());
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
 	   
 	    
 	    //for(int x=0;x<20;x++){
 	    	fantom.nFANTOM100_iNXT_sendDirectCommand(nxtPointer, false, command, command.capacity(), null, 0, status);
 	    //}
 	    
 	    System.out.println(status.getStatus().toString());
    	
    	
//    	Motor mtr = new Motor(1, nxtPointer);
//    	mtr.reset();
//    	mtr.speedMotor(100, 100);
    	
    	//Motor mtr2 = new Motor(0x02, nxtPointer);
    	//mtr2.reset();
    	//mtr2.speedMotor(100, 0);
    	
    }
    
    
    
    public void stopMotor(byte motorName)
	{
		Status status= new Status();
		ByteBuffer command= ByteBuffer.allocate(12);
		
		command.put((byte)0x04);//LSREAD
		command.put((byte)motorName);//port
		command.put((byte)0x00);//power -100 - 100
		command.put( (byte)0x05);//mode
		command.put( (byte)0x01);//regulation
		command.put( (byte)0x00);//turnratio
		command.put( (byte)0x00);//runstate
		command.put( (byte)0x00);//tacholimit = 0
		command.put( (byte)0x00);//tacholimit = 0
		command.put( (byte)0x00);//tacholimit = 0
		command.put( (byte)0x00);//tacholimit = 0
		command.put( (byte)0x00);//tacholimit = 0
		
		fantom.nFANTOM100_iNXT_sendDirectCommand(nxtPointer, false, command, command.capacity(), null, 0, status);
	
	}
    
    
    public void resetMotor(byte motorName)
	{
		Status status= new Status();
		ByteBuffer command= ByteBuffer.allocate(2);
		
		command.put((byte)0x0A);//reset
		command.put((byte)motorName);//port
		
		fantom.nFANTOM100_iNXT_sendDirectCommand(nxtPointer, false, command, command.capacity(), null, 0, status);
		System.out.println("reset" + " " + status.code);
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
		
//		for (int i=0;i<15;i++){
//			System.out.print(response[i]);
//			System.out.print("\t");
//		}
//		System.out.println();
//		System.out.println(response[2]+ " "+response[1]);
		return response; //number of bytes ready to read
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
    
}





