package gears.sidescroller.world.tiles;


/**
 * This class generates random TileSets
 * 
 * @author Matt Crow
 */
public class TileSetGenerator {
    private final TileGenerator tileGenerator;
    
    
    /**
     * creates a TileSetGenerator that will use the given dependencies to create
     * random TileSets.
     * 
     * @param tileGenerator provides designs 
     */
    public TileSetGenerator(TileGenerator tileGenerator){
        this.tileGenerator = tileGenerator;
    }
    
    
    /**
     * Creates a new random TileSet with the given number of both tangible and 
     * intangible tile designs.
     * 
     * @param numDesigns the number of designs of each type to include in the 
     *  TileSet
     * @return the random TileSet 
     */
    public TileSet generateRandom(int numDesigns){
        if(numDesigns <= 0){
            throw new IllegalArgumentException(String.format("Must generate a positive number of designs, not %d", numDesigns));
        }
        TileSet t = new TileSet();
        
        for(int i = 0; i < numDesigns; ++i){
            t.add(tileGenerator.generateRandom(false));
            t.add(tileGenerator.generateRandom(true));
        }
        
        return t;
    }
}
