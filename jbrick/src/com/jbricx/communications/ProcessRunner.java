/**
 * 
 */
package com.jbricx.communications;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * How not to do things: Apparently, this class is used to send a command to the
 * operating system. The command's results/errors are printed to the console and
 * an exit status variable is used to give feedback to the user. Now think for
 * a moment what could possible go wrong with this.
 * 
 * Done? Well, for starters the feedback is hard-coded, the (useful) results are
 * printed to the console with no chance of actually figuring out what's going
 * on.
 * 
 * I take no responsibility over this code, since this is how we found it and
 * I'm trying to improve it, but it isn't an easy task.
 *  
 * @author byktol
 * @see ExitStatus
 */
public class ProcessRunner {

  public ExitStatus run(final List<String> command) {
    return run(command.toArray(new String[command.size()]));
  }

  public ExitStatus run(final String... command) {
    Process p;
    ProcessBuilder pb = new ProcessBuilder(command);

    try {
      p = pb.start();

      InputStream is = p.getInputStream();
      InputStreamReader isr = new InputStreamReader(is);
      BufferedReader br = new BufferedReader(isr);

      InputStream eis = p.getErrorStream();
      InputStreamReader eisr = new InputStreamReader(eis);
      BufferedReader ebr = new BufferedReader(eisr);

      String line = "";

      while ((line = br.readLine()) != null) {
        System.out.println(line);
      }

      while ((line = ebr.readLine()) != null) {
        System.out.println(line);
      }
    } catch (Exception e) {
      System.out.println("ProcessRunner.main()" + e.getMessage());
    }

    return new ExitStatus(ExitStatus.OK,
      "If you got this error something terrible happened. Good luck finding out what happend. Let the force be with you.");
  }
}
