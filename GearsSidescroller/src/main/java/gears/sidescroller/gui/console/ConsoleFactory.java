package gears.sidescroller.gui.console;

/**
 *
 * @author Matt Crow <mattcrow19@gmail.com>
 */
public class ConsoleFactory {
    public ConsoleFrame makeDefaultConsole(){
        InterpreterImpl interpreter = new InterpreterImpl();
        CommandParser parser = new CommandParser(interpreter);
        
        return new ConsoleFrame(new ConsoleComponent(parser));
    }
}
