package gears.sidescroller.world.core;

import gears.sidescroller.loader.JsonSerializable;
import gears.sidescroller.world.areas.Area;
import static gears.sidescroller.world.tiles.AbstractTileTemplate.TILE_SIZE;
import java.awt.Graphics;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 * The ObjectInWorld class represents an object
 * which is physically present in the game world.
 * 
 * It contains attributes and methods used by several
 * otherwise unrelated classes.
 * 
 * Note that this class does not support changing the
 * object's coordinates: a class must inherit from MobileWorldObject
 * to do so.
 * 
 * @author Matt Crow
 * @see gears.sidescroller.world.core.MobileWorldObject
 */
public abstract class ObjectInWorld implements Collidable, JsonSerializable {
    protected int x;
    protected int y;
    private final int width;
    private final int height;
    private Area inArea;
    
    public ObjectInWorld(int x, int y, int w, int h){
        this.x = x;
        this.y = y;
        width = w;
        height = h;
        inArea = null;
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
    
    public final int getCenterX(){
        return x + width / 2;
    }
    
    public final int getCenterXIdx(){
        return getCenterX() / TILE_SIZE;
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
    
    public final int getCenterY(){
        return y + height / 2;
    }
    
    public final int getCenterYIdx(){
        return getCenterY() / TILE_SIZE;
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

    public final ObjectInWorld setArea(Area a){
        this.inArea = a;
        return this;
    }
    
    public final Area getArea(){
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
     * @returna unique identifier for this subclass of ObjectInWorld 
     */
    public abstract String getJsonType();
}
