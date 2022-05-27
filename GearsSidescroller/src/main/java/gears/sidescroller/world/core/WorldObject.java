package gears.sidescroller.world.core;

import gears.sidescroller.loader.JsonSerializable;
import gears.sidescroller.world.areas.Area;
import static gears.sidescroller.world.tiles.AbstractTileTemplate.TILE_SIZE;
import java.awt.Graphics;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 * The ObjectInWorld class represents an object physically present in an Area.
 * It contains attributes and methods used by several otherwise unrelated 
 * classes.
 * 
 * One primary point of interest with this class is the differences between
 * index-space and world-space: The index-space coordinate of an object is the 
 * cell in a tile map that contains the upper-left corner of the object. Whereas 
 * the world-space coordinate of an object are the exact coordinates of its
 * upper-left corner.
 * 
 * @author Matt Crow
 */
public abstract class WorldObject implements Collidable, JsonSerializable {
    /*
    these 2 must be stored as decimals so they can be changed during high frame 
    rates, where they may change by less than 1
    */
    private double x;
    private double y;
    
    private final int width;
    private final int height;
    private Area inArea;
    
    public WorldObject(int x, int y, int w, int h){
        this.x = x;
        this.y = y;
        width = w;
        height = h;
        inArea = null;
    }
    
    /**
     * @param x the x-coordinate of this object, in pixel-space
     */
    public void setX(double x){
        this.x = x;
    }
    
    public double getX(){
        return x;
    }
    
    /**
     * @return the x-coordinate of this object, in pixel-space 
     */
    public int getXAsInt(){
        return (int) x;
    }
    
    /**
     * @return the x-coordinate of this object, in index-space.
     */
    public int getXIdx(){
        return getXAsInt() / TILE_SIZE;
    }
    
    /**
     * @param xIdx the tile column this should be moved to 
     */
    public void setXIdx(int xIdx){
        setX(xIdx * TILE_SIZE);
    }
    
    /**
     * @return the x-coordinate of the center of this object, in pixel-space
     */
    public int getCenterX(){
        return getXAsInt() + width / 2;
    }
    
    /**
     * @return the x-index of the center of this object, in index-space 
     */
    public int getCenterXIdx(){
        return getCenterX() / TILE_SIZE;
    }
    
    public void setY(double y){
        this.y = y;
    }
    
    public double getY(){
        return y;
    }
    
    /**
     * 
     * @return the y-coordinate of this object, in pixel-space 
     */
    public int getYAsInt(){
        return (int) y;
    }
    
    public void setYIdx(int yIdx){
        setY(yIdx * TILE_SIZE);
    }
    
    /**
     * 
     * @return the y-coordinate of this object, in index-space
     */
    public int getYIdx(){
        return getYAsInt() / TILE_SIZE;
    }
    
    public int getCenterY(){
        return getYAsInt() + height / 2;
    }
    
    public int getCenterYIdx(){
        return getCenterY() / TILE_SIZE;
    }
    
    public int getWidth(){
        return width;
    }
    public int getWidthInTiles(){
        return width / TILE_SIZE;
    }
    
    public int getHeight(){
        return height;
    }
    public int getHeightInTiles(){
        return height / TILE_SIZE;
    }

    public WorldObject setArea(Area a){
        this.inArea = a;
        return this;
    }
    
    public Area getArea(){
        return inArea;
    }
    
    /**
     * Creates a CollisionBox for the ObjectInWorld.
     * Note that you should not cache this CollisionBox 
     * if the coordinates or size of this object can change,
     * as the CollisionBox doesn't track changes to the object
     * it represents.
     * 
     * @return a hitbox for this.
     */
    @Override
    public CollisionBox getCollisionBox() {
        return new CollisionBox(this);
    }
    
    @Override
    public JsonObject toJson(){
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("type", getJsonType());
        builder.add("x", x);
        builder.add("y", y);
        builder.add("width", width);
        builder.add("height", height);
        
        attachJsonProperties(builder);
        
        return builder.build();
    }
    
    /**
     * @param fps the number of frames since the last update 
     */
    public abstract void update(int fps);
    
    public abstract void draw(Graphics g);
    
    /**
     * subclasses must override this method to add their properties to the given
     * JsonObjectBuilder.
     * 
     * @param builder the builder currently constructing a JSON representation
     *  of this object
     */
    protected abstract void attachJsonProperties(JsonObjectBuilder builder);
    
    /**
     * @return a unique identifier for this subclass of WorldObject 
     */
    public abstract String getJsonType();
}
