package communcation;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class OperatingSystemCommand {

 

   /**

    * Runs a given command string and returns the result of the command.

    * @return String containing the results of the command.

    */

   public String runCommand(String[] cmd) throws IOException{

      StringBuffer result = new StringBuffer("");

      String s = "";

      // Run the command

      Process p = Runtime.getRuntime().exec(cmd);

      // Query the results of the process

      BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));

      while ((s = in.readLine()) != null){

         result.append(s);

      }

      in.close();

      return result.toString();

   }

 

   /**

    * Create a Windows batch file.

    */

   public void createWindowsBatchFile(String nameOfFile , String[] cmd) throws IOException{

      // Create the file new every time.

      FileWriter toFile = new FileWriter(nameOfFile + ".bat", false);

      for(int i=0;i<cmd.length;i++){

         toFile.write(cmd[i] + System.getProperty("line.separator"));

      }

      toFile.close();

  }

 

 

   public static void main(String[] args) {

      try{

         OperatingSystemCommand osc = new OperatingSystemCommand();

         String os = System.getProperty("os.name");

        System.out.println("The OS: " + os);

         if(os.indexOf("indow") >= 0){

            // Illustrate using a batch file to execute OS commands, tested on Windows NT.

            // Just creating a batch file of OS level commands

            String[] cmd={"c:","PATH"};

            osc.createWindowsBatchFile("myBatchFile",cmd);

            //Run the command, then display results,

            String[] command={"cmd","/c","myBatchFile"};

            System.out.println("RESULT: " + osc.runCommand(command));

       } else {

            // Will display $PATH on Unix, tested on SunOS.

            // Run the command, then display results

            String[] command={"/bin/sh","-c","echo $PATH"};

            System.out.println("RESULT: " + osc.runCommand(command));

         }

      } catch (Exception e){

         System.out.println("Error running OperatingSystemCommand: " + e);

      }

   }

 

}