
package model;

/**
 *
 * @author neo
 */
public class Command {
    private String command;
    private String response;

    public Command(String command, String response) {
        this.command = command;
        this.response = response;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
    
    
    
}
