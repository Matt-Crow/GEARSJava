package gears.sidescroller.gui.console.commands;

import gears.sidescroller.gui.console.OutputWriter;
import gears.sidescroller.world.levels.Level;

/**
 *
 * @author Matt Crow 
 */
public class Where implements Command {
    private final Level level;
    private final OutputWriter out;
    
    public Where(Level level, OutputWriter out){
        this.level = level;
        this.out = out;
    }
    
    @Override
    public String getName() {
        return "where";
    }

    @Override
    public void execute() {
        out.writeLine(String.format("player coordinates: %s", level.getPlayerCoordinateString()));
    }
}
