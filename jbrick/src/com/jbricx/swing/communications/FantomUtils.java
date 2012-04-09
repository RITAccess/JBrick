package com.jbricx.swing.communications;

import com.sun.jna.Native;

/**
 *
 * @author Emmanuel Pirsch
 */
public class FantomUtils {
    static int DEFAULT_RESOURCE_NAME_ALLOCATION_SIZE = 256;
    
    public static final byte[] newResourceName() {
        return new byte[DEFAULT_RESOURCE_NAME_ALLOCATION_SIZE];
    }
    
    public static final String asString(byte[] s) {
        return Native.toString(s);
    }
}