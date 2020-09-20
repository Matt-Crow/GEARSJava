package gears.sidescroller.world.tiles;

import java.awt.Color;
import java.util.Random;

/**
 *
 * @author Matt
 */
public class TileGenerator {
    public BasicColorTile generateRandom(boolean isTangible){
        Random rng = new Random();
        int r = rng.nextInt(256);
        int g = rng.nextInt(256);
        int b = rng.nextInt(256);
        Color inner = new Color(r, g, b);
        r = rng.nextInt(256);
        g = rng.nextInt(256);
        b = rng.nextInt(256);
        Color outer = new Color(r, g, b);
        
        return new BasicColorTile(0, 0, isTangible, outer, inner);
    }
}
