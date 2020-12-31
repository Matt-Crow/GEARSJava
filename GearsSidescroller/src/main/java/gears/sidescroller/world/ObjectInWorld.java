package gears.sidescroller.world;

import static gears.sidescroller.world.tiles.AbstractTile.TILE_SIZE;

/**
 * The ObjectInWorld class represents an object
 * which is physically present in the game world.
 * 
 * It contains attributes and methods used by several
 * otherwise unrelated classes.
 * 
 * @author Matt Crow
 */
public class ObjectInWorld {
    private int x;
    private int y;
    private final int width;
    private final int height;
    
    public ObjectInWorld(int x, int y, int w, int h){
        this.x = x;
        this.y = y;
        width = w;
        height = h;
    }
    
    public final ObjectInWorld setX(int x){
        this.x = x;
        return this;
    }
    public final ObjectInWorld setXIdx(int xIdx){
        this.x = xIdx * TILE_SIZE;
        return this;
    }
    public final ObjectInWorld setY(int y){
        this.y = y;
        return this;
    }
    public final ObjectInWorld setYIdx(int yIdx){
        this.y = yIdx * TILE_SIZE;
        return this;
    }
    
    /**
     * 
     * @return the x-coordinate of this object, in pixel-space 
     */
    public final int getX(){
        return x;
    }
    
    /**
     * 
     * @return the x-coordinate of this object, in index-space.
     */
    public final int getXIdx(){
        return x / TILE_SIZE;
    }
    
    /**
     * 
     * @return the y-coordinate of this object, in pixel-space 
     */
    public final int getY(){
        return y;
    }
    
    /**
     * 
     * @return the y-coordinate of this object, in index-space
     */
    public final int getYIdx(){
        return y / TILE_SIZE;
    }
    
    public final int getWidth(){
        return width;
    }
    public final int getWidthInTiles(){
        return width / TILE_SIZE;
    }
    
    public final int getHeight(){
        return height;
    }
    public final int getHeightInTiles(){
        return height / TILE_SIZE;
    }
}
