package gears.sidescroller.world.tiles;

import java.awt.Graphics;

/**
 * Tiles are immobile, immutable squares
 * on a map.
 * 
 * @author Matt Crow
 */
public abstract class AbstractTile {
    private final int x;
    private final int y;
    
    public static final int TILE_SIZE = 100;
    
    /**
     * 
     * @param xIndex the x-index of this tile in the level
     * map array.
     * @param yIndex the y-index of this tile in the level
     * map array.
     */
    public AbstractTile(int xIndex, int yIndex){
        x = xIndex * TILE_SIZE;
        y = yIndex * TILE_SIZE;
    }
    
    /**
     * 
     * @return the x-coordinate 
     * of this tile in the world. 
     */
    public final int getXCoord(){
        return x;
    }
    
    /**
     * 
     * @return the y-coordinate
     * of this tile in the world.
     */
    public final int getYCoord(){
        return y;
    }
    
    /**
     * 
     * @return the x-index of this
     * tile in the world map array.
     */
    public final int getXIndex(){
        return x / TILE_SIZE;
    }
    
    /**
     * 
     * @return the y-index of this
     * tile in the world map array.
     */
    public final int getYIndex(){
        return y / TILE_SIZE;
    }
    
    /**
     * Renders this tile on the given graphics.
     * @param g the graphics context to draw this on.
     * @return this, for chaining purposes.
     */
    public abstract AbstractTile draw(Graphics g);
    
    /**
     * Creates a copy of this tile at the given coordinates.
     * 
     * @param xIndex the x-index of the new tile in the world map array
     * @param yIndex the y-index of the new tile in the world map array
     * @return the newly created tile
     */
    public abstract AbstractTile copyTo(int xIndex, int yIndex);
}
