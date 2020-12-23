package gears.sidescroller.world.areas;

import gears.sidescroller.world.tileMaps.TileMapGenerator;
import gears.sidescroller.world.areas.Area;
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
        ).generateTileMap((byte)20, (byte)20));
        
        return ret;
    }
}
