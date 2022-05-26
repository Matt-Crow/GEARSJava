package gears.sidescroller.gui.console;

import java.util.Arrays;

/**
 *
 * @author Matt Crow <mattcrow19@gmail.com>
 */
public class CommandParser {
    private final Interpreter interpreter;
    
    public CommandParser(Interpreter interpreter){
        this.interpreter = interpreter;
    }
    
    
    public void parse(String input){
        String[] split = input.split("\\s+");
        String cmd = split[0];
        String[] args = Arrays.copyOfRange(split, 1, split.length);
        
        interpreter.interpret(cmd, args);
    }
}
