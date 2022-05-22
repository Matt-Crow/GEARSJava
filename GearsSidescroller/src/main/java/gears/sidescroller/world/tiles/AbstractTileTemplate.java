package gears.sidescroller.world.tiles;

import gears.sidescroller.loader.JsonSerializable;
import java.awt.Graphics;

/**
 * Tiles are immobile, immutable squares
 * on a map.
 * 
 * Tiles are used as Flyweights by TileMap, so they needn't internalize their
 * coordinates, as such data is externalized in the TileMap.
 * 
 * @author Matt Crow
 */
public abstract class AbstractTileTemplate implements JsonSerializable {
    private final boolean isTangible;
    
    /**
     * TILE_SIZE is the ratio of pixels / tile,
     * and is used frequently throughout the program.
     */
    public static final int TILE_SIZE = 100;
    
    /**
     * 
     * @param tangible whether or not this should be considered
     * when checking for collisions
     */
    public AbstractTileTemplate(boolean tangible){
        this.isTangible = tangible;
    }
    
    /**
     * 
     * @return whether or not this tile
     * should be considered when checking
     * for collisions.
     */
    public final boolean getIsTangible(){
        return isTangible;
    }
    
    /**
     * Renders this AbstractTileTemplate at the given coordinates.
     * 
     * @param g the Graphics to render on
     * @param x the x-coordinate where this tile should be drawn, measured in
     * pixel-space.
     * @param y the y-coordinate where this tile should be drawn, measured in
     * pixel-space.
     * 
     * @return this, for chaining purposes.
     */
    public abstract AbstractTileTemplate drawAt(Graphics g, int x, int y);
}
