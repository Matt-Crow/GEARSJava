package gears.sidescroller.gui.console;

/**
 *
 * @author Matt Crow 
 */
public class InvalidCommandException extends RuntimeException {
    public InvalidCommandException(String command){
        super(String.format("Invalid command: \"%s\"", command));
    }
}
