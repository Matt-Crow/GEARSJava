package gears.sidescroller.world.structures;

import gears.sidescroller.util.Matrix;
import gears.sidescroller.world.tiles.AbstractTile;

/**
 *
 * @author Matt
 */
public class Structure extends Matrix<AbstractTile> {
    
    public Structure(int width, int height) {
        super(width, height);
    }
    
}
