package com.jbricx.swing.communications;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.usb4java.Context;
import org.usb4java.ControlSetup;
import org.usb4java.Device;
import org.usb4java.DeviceDescriptor;
import org.usb4java.DeviceHandle;
import org.usb4java.DeviceList;
import org.usb4java.LibUsb;
import org.usb4java.LibUsbException;

/**
 * Simply lists all available USB devices.
 * 
 * @author Klaus Reimer <k@ailis.de>
 */
public class USBConnection
{
	public static Device findDevice(short vendorId, short productId)
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
	
    /**
     * Main method.
     * 
     * @param args
     *            Command-line arguments (Ignored)
     */
    public static void main(String[] args)
    {
    	Context context = new Context();
    	int result = LibUsb.init(context);
    	if (result != LibUsb.SUCCESS){
    		throw new LibUsbException("unable to initialize libusb", result);
    	}
    	
    	
    	
        Device device = findDevice((short) 0x0694, (short) 0x0002);
        System.out.println(device);
        DeviceHandle dh = new DeviceHandle();
        LibUsb.open(device, dh);
        int usbInterface = 0;
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
            result = LibUsb.detachKernelDriver(dh,  usbInterface);
            if (result != LibUsb.SUCCESS) throw new LibUsbException("Unable to detach kernel driver", result);
        }
        
        ByteBuffer buffer = ByteBuffer.allocateDirect(6);
        buffer.put(new byte[]{(byte) 0x80, (byte) 0x03, (byte) 0xf4, (byte) 0x01, (byte) 0xf4, (byte) 0x01});
        /*
        int value = LibUsb.controlTransfer(
        		dh, // handle - A handle for the device to communicate with.
        		(byte) (LibUsb.RECIPIENT_DEVICE), // bmRequestType - The request type field for the setup packet.
        		(byte) LibUsb.REQUEST_TYPE_STANDARD, //bRequest - The request field for the setup packet.
        		(short) 2, // wValue - The value field for the setup packet.
        		(short) 1, // wIndex - The index field for the setup packet.
        		buffer, // data - A suitably-sized data buffer for either input or output (depending on direction bits within bmRequestType).
        		5000); // timeout - Timeout (in millseconds) that this function should wait before giving up due to no response being received. For an unlimited timeout, use value 0.
        */
        int value = LibUsb.bulkTransfer(
        		dh, // handle - A handle for the device to communicate with.
        		(byte) 0x01, // endpoint - The address of a valid endpoint to communicate with.
        		buffer, // data - A suitably-sized data buffer for either input or output (depending on endpoint).
        		IntBuffer.allocate(1), // transferred - Output location for the number of bytes actually transferred.
        		5000 // timeout - timeout (in millseconds) that this function should wait before giving up due to no response being received. For an unlimited timeout, use value 0.
        );
        System.out.print(LibUsb.errorName(value));
        System.out.println(", " + value);
        LibUsb.close(dh);
        LibUsb.exit(context);
    }
}
