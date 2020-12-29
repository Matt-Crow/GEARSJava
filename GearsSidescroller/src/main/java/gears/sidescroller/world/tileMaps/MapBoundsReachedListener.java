package gears.sidescroller.world.tileMaps;

import gears.sidescroller.util.Direction;

/**
 *
 * @author Matt
 */

public interface MapBoundsReachedListener {
    public void boundReached(TileMap whatMap, Direction whatSide);
}
