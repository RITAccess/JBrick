package com.jbricx.communications;

import java.nio.Buffer;
import com.sun.jna.Pointer;
import com.sun.jna.win32.StdCallLibrary;

/**
 * Provides access to the Lego Mindstorm Fantom Library For more methods realted
 * to Fantom driver see <a
 * href="http://nxtpp.clustur.com/projectdocs/i_n_x_t_8h.html"> here </a>
 * 
 * @author Emmanuel Pirsch
 */
public interface Fantom extends StdCallLibrary {
  // Fantom INSTANCE = (Fantom) Native.loadLibrary("fantom", Fantom.class);

  /**
   * 
   * @param searchBluetooth
   *          indicate if we also search bluetooth devices.
   * @param bluetoothSearchTimeout
   *          the timeout in second when searching bluetooth devices.
   * @param status
   *          OUT
   * @return
   */
  Pointer nFANTOM100_createNXTIterator(boolean searchBluetooth,
      int bluetoothSearchTimeout, Status status);

  Pointer nFANTOM100_createNXT(String resourceString, Status status,
      boolean checkFirmwareVersion);

  // nFANTOM100_kExport nFANTOM100_iNXT _VI_FUNCC nFANTOM100_createNXT(
  // ViConstString resourceString,
  // ViStatus* status,
  // ViBoolean checkFirmwareVersion );

  /**
   * 
   * @param iNXTIterator
   * @param resourceName
   *          length must be at least 256.
   * @param status
   */
  void nFANTOM100_iNXTIterator_getName(Pointer iNXTIterator,
      byte[] resourceName, Status status);

  void nFANTOM100_iNXTIterator_advance(Pointer iNXTIterator, Status status);

  Pointer nFANTOM100_iNXTIterator_getNXT(Pointer iNXTIterator, Status status);

  void nFANTOM100_destroyNXT(Pointer nxtPtr, Status status);

  void nFANTOM100_destroyNXTIterator(Pointer iNXTIterator, Status status);

  int nFANTOM100_iNXT_sendDirectCommand(Pointer iNXT, boolean requireResponse,
      Buffer commandBuffer, int commandBufferLength, byte[] responseBuffer,
      int responseBufferLength, Status status);
}