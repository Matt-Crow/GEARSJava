package gears.sidescroller.world.structures;

import gears.sidescroller.util.FlyweightMatrix;
import gears.sidescroller.world.tiles.AbstractTile;

/**
 *
 * @author Matt
 */
public class Structure extends FlyweightMatrix<AbstractTile> {
    
    public Structure(int width, int height) {
        super(width, height);
    }
    
}
