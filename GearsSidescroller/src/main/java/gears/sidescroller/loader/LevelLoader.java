package gears.sidescroller.loader;

import gears.sidescroller.world.levels.Level;

/**
 * This defines the minimum required functionality for a class responsible for
 * loading and saving levels to persistent memory
 * 
 * @author Matt Crow <mattcrow19@gmail.com>
 */
public interface LevelLoader {
    
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
     * @param level the Level to save
     * @throws LevelLoadingException if the given Level cannot be saved
     */
    public void save(Level level) throws LevelLoadingException;
}
