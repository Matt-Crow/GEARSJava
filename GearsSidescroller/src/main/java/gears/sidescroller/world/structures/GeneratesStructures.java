package gears.sidescroller.world.structures;

import gears.sidescroller.world.tiles.TileSet;
import java.util.Random;

/**
 * Classes with this interface can randomly generate structures.
 * Thanks to dependency injection, you can inject your custom instances of this
 * interface into the constructor of StructureGenerator so it can produce them.
 * 
 * @author Matt Crow 
 */
public interface GeneratesStructures {
    
    /**
     * Creates a new randomly generated structure.
     * 
     * @param rng the random number generator to use
     * @param x the x coordinate of the new structure's upper left corner
     * @param y the y coordinate of the new structure's upper left corner
     * @param tiles the tiles to choose from when generating the structure
     * 
     * @return a newly generated structure 
     */
    public Structure generate(Random rng, int x, int y, TileSet tiles);
}
