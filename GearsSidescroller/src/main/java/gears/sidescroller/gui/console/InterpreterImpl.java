package gears.sidescroller.gui.console;

import gears.sidescroller.gui.console.commands.Command;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Matt Crow <mattcrow19@gmail.com>
 */
public class InterpreterImpl implements Interpreter {
    private final HashMap<String, Command> commands;
    
    public InterpreterImpl(){
        commands = new HashMap<>();
    }
    
    public void addCommand(Command cmd){
        commands.put(cmd.getName(), cmd);
    }
    
    @Override
    public Set<String> getSupportedCommands(){
        return new HashSet<>(commands.keySet());
    }
    
    @Override
    public void interpret(String command, String[] args) throws InvalidCommandException, IllegalArgumentException {
        if(!commands.containsKey(command)){
            throw new InvalidCommandException(command);
        }
        commands.get(command).execute();
    }
}
