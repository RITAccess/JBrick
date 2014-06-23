package com.jbricx.communication;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.usb4java.Context;
import org.usb4java.Device;
import org.usb4java.DeviceDescriptor;
import org.usb4java.DeviceHandle;
import org.usb4java.DeviceList;
import org.usb4java.LibUsb;
import org.usb4java.LibUsbException;

/**
 * Used to connect to the NXT brick remotely (eventually will be programmed if possible with the ev3)
 * @author Ethan Jurman
 */
public class USBConnection
{
	// TODO remove MSCommand
	enum MSCommand {
		NO_RESPONSE((byte) 0x80),
		RESPONSE((byte) 0x01),
		;
		private byte code;
		MSCommand(byte code){
			this.code = code;
		}
		public byte getValue() {
			return code;
		}
	}
	
	/*
	 * NXT -- VENDOR : 0x0694, PRODUCT : 0x0002, INTERFACE : 0, ENDPOINT_OUT : 0x01, ENDPOINT_IN : 0x82
	 * EV3 -- VENDOR : 0x1684, PRODUCT : 0x0005 
	 */
	enum MindStormDevice{
		NXT((short) 0x0694, (short) 0x0002, 0, (byte) 0x01, (byte) 0x82),
		//EV3((short) 0x0694, (short) 0x0005, 0, (byte) 0x01, (byte) 0x81), // TODO unsure about the interface and endpoints for EV3 model
		;

		public short vendor;
		public short product;
		public int usbInterface;
		public byte endOut;
		public byte endIn;

		/**
		 * Sets up defaults for each mindstorm device to work with the application
		 * 
		 * @param vendor - Vendor Id for usb connection
		 * @param product - Product Id for usb connection
		 * @param usbInterface - Interface number for usb connection
		 * @param endOut - endpoint out for usb connection
		 * @param endIn - endpoint in for usb connection
		 */
		MindStormDevice(short vendor, short product, int usbInterface, byte endOut, byte endIn){
			this.vendor = vendor;
			this.product = product;
			this.usbInterface = usbInterface;
			this.endOut = endOut;
			this.endIn = endIn;
		}
	}

	private static Device findDevice(short vendorId, short productId)
	{
	    // Read the USB device list
	    DeviceList list = new DeviceList();
	    Context context = new Context();
	    LibUsb.init(context);
	    int result = LibUsb.getDeviceList(context, list);
	    if (result < 0) throw new LibUsbException("Unable to get device list", result);

	    try
	    {
	        // Iterate over all devices and scan for the right one
	        for (Device device: list)
	        {
	            DeviceDescriptor descriptor = new DeviceDescriptor();
	            result = LibUsb.getDeviceDescriptor(device, descriptor);
	            if (result != LibUsb.SUCCESS) throw new LibUsbException("Unable to read device descriptor", result);
	            if (descriptor.idVendor() == vendorId && descriptor.idProduct() == productId) return device;
	        }
	    }
	    finally
	    {
	        // Ensure the allocated device list is freed
	        LibUsb.freeDeviceList(list, true);
	    }

	    // Device not found
	    return null;
	}
	
	public static boolean isConnected(){
		return isConnected(MindStormDevice.NXT);
	}
	
	public static boolean isConnected(MindStormDevice mindStormDevice){
		LibUsb.init(null);
        Device device = findDevice(mindStormDevice.vendor, mindStormDevice.product);
        return device != null;
	}

	public static int Connect(MindStormDevice mindStormDevice, ByteBuffer commandBuffer) {
    	int result = LibUsb.init(null);
    	if (result != LibUsb.SUCCESS){
    		throw new LibUsbException("unable to initialize libusb", result);
    	}
    	
        Device device = findDevice(mindStormDevice.vendor, mindStormDevice.product);
        DeviceHandle dh = new DeviceHandle();
        LibUsb.open(device, dh);
        int usbInterface = mindStormDevice.usbInterface;
        LibUsb.claimInterface(dh, usbInterface); // handle, Interface
        if (result != LibUsb.SUCCESS)
        {
            throw new LibUsbException("Unable to claim interface", result);
        }
        
        // Check if kernel driver must be detached
        boolean detach = LibUsb.hasCapability(LibUsb.CAP_SUPPORTS_DETACH_KERNEL_DRIVER);

        // Detach the kernel driver
        if (detach)
        {
            result = LibUsb.detachKernelDriver(dh, usbInterface);
            if (result != LibUsb.SUCCESS) throw new LibUsbException("Unable to detach kernel driver", result);
        }
        
        int value = LibUsb.bulkTransfer(
        		dh, // handle - A handle for the device to communicate with.
        		mindStormDevice.endOut, // endpoint - The address of a valid endpoint to communicate with.
        		commandBuffer, // data - A suitably-sized data buffer for either input or output (depending on endpoint).
        		IntBuffer.allocate(1), // transferred - Output location for the number of bytes actually transferred.
        		5000 // timeout - timeout (in millseconds) that this function should wait before giving up due to no response being received. For an unlimited timeout, use value 0.
        );
        if (value < 0){
        	// if there is an error, produce the error name and value
            System.err.print(LibUsb.errorName(value));
            System.err.println(", " + value);
        }
        LibUsb.close(dh);
        LibUsb.exit(null);
        return value;
	}

    /**
     * Main method.
     * 
     * @param args
     *            Command-line arguments (Ignored)
     */
    public static void main(String[] args)
    {
    	if (USBConnection.isConnected()){
	    	
	    	ByteBuffer buffer = ByteBuffer.allocateDirect(6);
	        buffer.put(new byte[]{(byte) 0x80, (byte) 0x03, (byte) 0xf0, (byte) 0x01, (byte) 0xf4, (byte) 0x01});
	    	USBConnection.Connect(USBConnection.MindStormDevice.NXT, buffer);
	    	
    	}
    	
    }
}