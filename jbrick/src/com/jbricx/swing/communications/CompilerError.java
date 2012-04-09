/**
 * 
 */
package com.jbricx.swing.communications;

/**
 * Contains the output of an NBC error message.
 * 
 * This is hopefully a worthy example of an error output:
  <tt>    # Error: <error message>
  File "<path to file.nxc>" ; line <line number>
  #   <code on the specific line>
  #----------------------------------------------------------</tt>
 * Based on this, I assume errors will show up in this way. Special cases may
 * exist.
 *  
 * @author byktol
 */
public class CompilerError {
  private String message;
  private String file;
  private String lineNumber;
  private String line;

  public void setFileLine(final String fileline) {
    try {
    file = fileline.substring(
            fileline.indexOf("File \"") + 6,
            fileline.indexOf("\" ; ")
           );
    } catch (StringIndexOutOfBoundsException e) {
      e.printStackTrace();
    }
    lineNumber = fileline.substring( fileline.indexOf("line ") + 5);
  }

  public void setMessageLine(final String messageline) {
    // We don't know if there are warnings;
    int i = messageline.indexOf("Error: ");
    message = messageline.substring(i + 7);
  }

  public void setLine(final String line) {
    this.line = line;
  }

  /**
   * @return the message
   */
  public String getMessage() {
    return message;
  }

  /**
   * @return the file
   */
  public String getFile() {
    return file;
  }

  /**
   * @return the lineNumber
   */
  public String getLineNumber() {
    return lineNumber;
  }

  /**
   * @return the line
   */
  public String getLine() {
    return line;
  }

  @Override
  public String toString() {
    return String.format("Error on line %s: %s", lineNumber, message);
  }
}