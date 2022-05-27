package gears.sidescroller.gui.console.commands;

import gears.sidescroller.gui.console.ConsoleFrame;

/**
 *
 * @author Matt Crow <mattcrow19@gmail.com>
 */
public class Exit implements Command {
    private final ConsoleFrame frame;
    
    
    public Exit(ConsoleFrame frame){
        this.frame = frame;
    }
    
    @Override
    public String getName() {
        return "exit";
    }

    @Override
    public void execute() {
        frame.dispose();
    }
}
