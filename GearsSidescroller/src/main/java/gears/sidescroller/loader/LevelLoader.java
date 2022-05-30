package gears.sidescroller.loader;

import gears.sidescroller.world.levels.Level;
import java.util.Collection;

/**
 * This defines the minimum required functionality for a class responsible for
 * loading and saving levels
 * 
 * Note that this is not required to save the player's progress within a level,
 * just the level data itself
 * 
 * @author Matt Crow 
 */
public interface LevelLoader {
    
    /**
     * @return a list of identifiers this LevelLoader can perform loads on
     */
    public Collection<String> getAvailableLevelName();
    
    /**
     * @param identifier the name of the Level to load
     * @return the Level identified by the given parameter
     * @throws LevelLoadingException if the Level with the given identifier
     *  cannot be loaded
     */
    public Level load(String identifier) throws LevelLoadingException;
    
    /**
     * attempts to save the given Level to this LevelLoader's persistent storage
     * area, such as a file or database
     * 
     * @param identifier the identifier to give the given Level when it is saved
     * @param level the Level to save
     * @throws LevelLoadingException if the given Level cannot be saved
     */
    public void save(String identifier, Level level) throws LevelLoadingException;
}
