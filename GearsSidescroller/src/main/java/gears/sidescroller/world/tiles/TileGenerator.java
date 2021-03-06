package gears.sidescroller.world.tiles;

import java.awt.Color;
import java.util.Random;

/**
 * The TileGenerator class is used to create randomly colored BasicColorTiles.
 * This is used in random TileMap generation.
 * 
 * @author Matt Crow
 */
public class TileGenerator {
    /**
     * Creates a new, randomly colored BasicColorTile.
     * 
     * @param isTangible whether or not the tile this produces should be tangible.
     * 
     * @return the randomly generated tile. 
     */
    public final BasicColorTile generateRandom(boolean isTangible){
        Random rng = new Random();
        int r = rng.nextInt(256);
        int g = rng.nextInt(256);
        int b = rng.nextInt(256);
        Color inner = new Color(r, g, b);
        r = rng.nextInt(256);
        g = rng.nextInt(256);
        b = rng.nextInt(256);
        Color outer = new Color(r, g, b);
        
        return new BasicColorTile(isTangible, outer, inner);
    }
}
