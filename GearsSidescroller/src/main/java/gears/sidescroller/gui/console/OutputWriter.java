package gears.sidescroller.gui.console;

/**
 *
 * @author Matt Crow <mattcrow19@gmail.com>
 */
public interface OutputWriter {
    
    /**
     * outputs the given message, without a newline at the end
     * @param message the message to output
     */
    public void write(String message);
    
    /**
     * outputs the given message, adding a newline at the end
     * @param message the message to output
     */
    public void writeLine(String message);
}
