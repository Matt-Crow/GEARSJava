package gears.sidescroller.gui.console;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 *
 * @author Matt Crow <mattcrow19@gmail.com>
 */
public class InterpreterImpl implements Interpreter {
    private final HashMap<String, Consumer<String[]>> commands;
    
    public InterpreterImpl(){
        commands = new HashMap<>();
    }
    
    public void addCommand(String cmd, Consumer<String[]> doThis){
        commands.put(cmd, doThis);
    }
    
    public Set<String> getCommands(){
        return new HashSet<>(commands.keySet());
    }
    
    @Override
    public void interpret(String command, String[] args) throws InvalidCommandException, IllegalArgumentException {
        if(!commands.containsKey(command)){
            throw new InvalidCommandException(command);
        }
        commands.get(command).accept(args);
    }
}
