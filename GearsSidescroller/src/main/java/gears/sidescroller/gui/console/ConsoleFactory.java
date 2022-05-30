package gears.sidescroller.gui.console;

import gears.sidescroller.gui.console.commands.*;
import gears.sidescroller.world.levels.Level;

/**
 *
 * @author Matt Crow 
 */
public class ConsoleFactory {
    
    /**
     * creates a console to act on the given level
     * 
     * @param level the level the console should act on
     * @return the frame containing the new console
     */
    public ConsoleFrame makeDefaultConsole(Level level){
        InterpreterImpl interpreter = new InterpreterImpl();
        
        CommandParser parser = new CommandParser(interpreter);
        ConsoleComponent component = new ConsoleComponent(parser);
        ConsoleFrame frame = new ConsoleFrame(component);
        
        interpreter.addCommand(new Exit(frame));
        interpreter.addCommand(new Help(interpreter, component));
        interpreter.addCommand(new Where(level, component));
        
        return frame;
    }
}
