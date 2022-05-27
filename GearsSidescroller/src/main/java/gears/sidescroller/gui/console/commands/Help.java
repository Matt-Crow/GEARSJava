package gears.sidescroller.gui.console.commands;

import gears.sidescroller.gui.console.Interpreter;
import gears.sidescroller.gui.console.InterpreterImpl;
import gears.sidescroller.gui.console.OutputWriter;

/**
 *
 * @author Matt Crow <mattcrow19@gmail.com>
 */
public class Help implements Command {
    private final Interpreter interpreter;
    private final OutputWriter out;
    
    public Help(Interpreter interpreter, OutputWriter out){
        this.interpreter = interpreter;
        this.out = out;
    }
    
    @Override
    public String getName() {
        return "help";
    }

    @Override
    public void execute() {
        out.writeLine("Commands:");
        interpreter.getSupportedCommands().forEach((name)->{
            out.writeLine(String.format("* %s", name));
        });
    }
}
