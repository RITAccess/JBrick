package com.jbricx.communications;
import java.util.EnumSet;
import java.util.HashMap;

/**
 *
 * @author Emmanuel Pirsch
 */
public class Status extends Structure {
    public int code;
    public byte[] filename= new byte[MAX_FILENAME_LENGTH];
    public int lineNumber;
    
    public Statuses getStatus() {
        return Statuses.fromCode(code);
    }
    
    static int MAX_FILENAME_LENGTH= 101;
    
    public enum Statuses {
        SUCCESS,
        FIRST(0),
        PAIRING_FAILED(5),
        BLUETOOTH_SEARCH_FAILED(6),
        SYSTEM_LIBRARY_NOT_FOUND(7),
        UNPAIRING_FAILED(8),
        INVALID_FILENAME(9),
        INVALID_ITERATOR_DEREFERENCE(10),
        LOCK_OPERATION_FAILED(11),
        SIZE_UNKNOWN(12),
        DUPLICATE_OPEN(13),
        EMPTY_FILE(14),
        FIRMWARE_DOWNLOAD_FAILED(15),
        PORT_NOT_FOUND(16),
        NO_MORE_ITEMS_FOUND(17),
        TOO_MANY_UNCONFIGURED_DEVICES(18),
        COMMAND_MISMATCH(19),
        ILLEGAL_OPERATION(20),
        BLUETOOTH_CACHE_UPDATE_FAILED(21),
        NON_NXT_DEVICE_SELECTED(22),
        RETRY_CONNECTION(23),
        POWER_CYCLE_NXT(24),
        FEATURE_NOT_IMPLEMENTED(99),
        FW_ILLEGAL_HANDLE(189),
        FW_ILLEGAL_FILENAME(190),
        FW_OUT_OF_BOUNT(191),
        FW_MODULE_NOT_FOUND(192),
        FW_FILE_EXISTS(193),
        FW_FILE_IS_FULL(194),
        FW_APPEND_NOT_POSSIBLE(195),
        FW_NO_WRITE_BUFFERS(196),
        FW_FILE_IS_BUSY(197),
        FW_UNDEFINED_ERROR(198),
        NO_LINEAR_SPACE(199),
        FW_HANDLE_ALREADY_CLOSED(200),
        FW_FILE_NOT_FOUND(201),
        FW_NOT_LINEAR_FILE(202),
        FW_END_OF_FILE(203),
        FW_END_OF_FILE_EXPECTED(204),
        FW_NO_MORE_FILES(205),
        FW_NO_SPACE(206),
        FW_NO_MORE_HANDLES(207),
        FW_UNKNOWN_ERROR_CODE(208),
        LAST(999);
        
        private int code;
        private static HashMap<Integer, Statuses> code_map= new HashMap<Integer, Statuses>();
        static {
            for(Statuses s : EnumSet.allOf(Statuses.class)) {
               code_map.put(s.code, s);
            }
        }
        Statuses() {
            this.code = 0;
        }
        Statuses(int code_offset) {
            this.code= -142000 - code_offset;
        }
        
        static Statuses fromCode(int code) {
            return code_map.get(code);
        }
    }
}