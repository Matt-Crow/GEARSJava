package gears.sidescroller.world;

import java.util.Random;

/**
 *
 * @author Matt
 */
public class AreaGenerator {
    public final Area generateRandom(){
        Random rng = new Random();
        int max = 10;
        
        Area ret = new Area(new TileMapGenerator(
            rng.nextInt(max - 1) + 1, // from 1-max 
            rng.nextInt(max - 1) + 1, 
            rng.nextInt(max - 1) + 1, 
            rng.nextInt(max - 1) + 1
        ).generateTileMap(20, 20));
        
        return ret;
    }
}
