
package utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author neo
 */
public class CommandProcessorUtil {
    private static Runtime runtime;
    static {
        CommandProcessorUtil.runtime = Runtime.getRuntime();
    }
    
    public static String processCommand(final String command) {
        String response=null;
        try {
            Process process=CommandProcessorUtil.runtime.exec(command);
            response=IOUtils.toString(process.getInputStream());
            if(response!=null && response.length()==0) {
                response = "Your command did not produce any output.";
            }
            
        } catch (IOException ex) {
            response = "Something went wrong! "+ex.getMessage();
//            ex.printStackTrace();
        }
        return response;
    }
    
//    public static void main(String[] args) {
//        System.out.println(CommandProcessorUtil.processCommand(""));
//    }
    
}
