package gears.sidescroller.world.structures;

import java.util.Random;

/**
 * Classes with this interface can randomly generate structures.
 * Thanks to dependency injection, you can inject your custom instances of this
 * interface into the constructor of StructureGenerator so it can produce them.
 * 
 * @author Matt Crow <mattcrow19@gmail.com>
 */
public interface GeneratesStructures {
    
    /**
     * Creates a new randomly generated structure.
     * 
     * @param x the x coordinate of the new structure's upper left corner
     * @param y the y coordinate of the new structure's upper left corner
     * @param rng the random number generator to use
     * 
     * @return a newly generated structure 
     */
    public Structure generate(int x, int y, Random rng);
}
