package gears.sidescroller.gui.console;

/**
 *
 * @author Matt Crow <mattcrow19@gmail.com>
 */
public class InvalidCommandException extends RuntimeException {
    public InvalidCommandException(String command){
        super(String.format("Invalid command: \"%s\"", command));
    }
}
