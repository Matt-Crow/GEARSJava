package gears.sidescroller.util;

import gears.sidescroller.world.tiles.AbstractTile;

/**
 * current holding place for global
 * variables
 * 
 * @author Matt Crow
 */
public class PhysicsConstants {
    public static final int FRAMES_PER_SECOND = 20;
    public static final int GRAVITY = 3 * AbstractTile.TILE_SIZE / FRAMES_PER_SECOND; // 3 tiles per second
}
