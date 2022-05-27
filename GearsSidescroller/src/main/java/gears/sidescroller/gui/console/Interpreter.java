package gears.sidescroller.gui.console;

import java.util.Set;

/**
 *
 * @author Matt Crow <mattcrow19@gmail.com>
 */
public interface Interpreter {
    /**
     * attempts to execute the given command
     * @param command the command to execute
     * @param args arguments provided to the command
     * @throws InvalidCommandException if the given command is not supported
     * @throws IllegalArgumentException if the given args are incompatible with
     *  the given command
     */
    public void interpret(String command, String[] args) throws InvalidCommandException, IllegalArgumentException;

    public Set<String> getSupportedCommands();
}
