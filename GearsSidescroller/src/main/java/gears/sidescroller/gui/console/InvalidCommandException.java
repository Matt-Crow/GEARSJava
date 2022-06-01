package gears.sidescroller.gui.console;

/**
 *
 * @author Matt Crow 
 */
public class InvalidCommandException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    public InvalidCommandException(String command){
        super(String.format("Invalid command: \"%s\"", command));
    }
}
