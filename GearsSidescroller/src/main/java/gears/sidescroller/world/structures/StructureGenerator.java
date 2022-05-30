package gears.sidescroller.world.structures;

import java.util.List;
import java.util.Random;

/**
 * The StructureGenerator is used to randomly generate Structures for random 
 * Area generation, choosing from among structure types it is provided.
 * 
 * @author Matt Crow
 */
public class StructureGenerator {
    private final Random rng;
    private final List<GeneratesStructures> generators;
    
    /**
     * Creates a new StructureGenerator.
     * 
     * @param rng the random number generator this will use to choose structures
     *  to generate
     * @param generators the generators this will use to generate structures
     */
    public StructureGenerator(Random rng, List<GeneratesStructures> generators){
        this.rng = rng;
        this.generators = generators;
    }
    
    /**
     * 
     * @param x the x-coordinate of the upper left corner of the new structure
     * @param y the y-coordinate of the upper left corner of the new structure
     * @return a new structure
     */
    public Structure generateRandom(int x, int y){
        int n = rng.nextInt(generators.size());
        Structure s =  generators.get(n).generate(x, y, rng);
        return s;
    }
}
