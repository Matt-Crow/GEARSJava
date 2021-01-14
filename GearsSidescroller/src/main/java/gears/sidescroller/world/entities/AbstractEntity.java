package gears.sidescroller.world.entities;

import gears.sidescroller.util.Direction;
import gears.sidescroller.world.core.MobileWorldObject;
import static gears.sidescroller.world.tiles.AbstractTileTemplate.TILE_SIZE;
import java.awt.Graphics;
import java.util.HashMap;

/**
 * The AbstractEntity is a basic implementation of MobileWorldObject. It represent
 * any more-or-less living being in the game world.
 * 
 * @author Matt Crow
 */
public abstract class AbstractEntity extends MobileWorldObject {
    private int speed;
    private final HashMap<Direction, Boolean> isMoveInDir; 
    // allows for moving diaganally using just cardinal directions,
    // which a single Direction wouldn't allow
    
    private final long id;
    
    private static long NEXT_ID = 0;
    
    public AbstractEntity(){
        super(0, 0, TILE_SIZE, TILE_SIZE);
        speed = 0;
        isMoveInDir = new HashMap<>();
        id = NEXT_ID;
        NEXT_ID++;
    }
    
    /**
     * Sets the movement speed of this AbstractEntity.
     * 
     * @param s the speed of this AbstractEntity, measured in pixels per frame.
     * 
     * @return this, for chaining purposes. 
     */
    public final AbstractEntity setSpeed(int s){
        speed = s;
        return this;
    }
    
    /**
     * @return the speed of this AbstractEntity, measured in pixels per frame.
     */
    public final int getSpeed(){
        return speed;
    }
    
    /**
     * Sets whether or not this AbstractEntity is moving of its own accord in 
     * the given direction, not accounting for external forces. This is used by 
     * key controls.
     * 
     * @param dir the Direction to set whether or not this is moving in
     * @param isMoving whether or not this is moving in the given direction
     * 
     * @return this, for chaining purposes. 
     */
    public final AbstractEntity setMovingInDir(Direction dir, boolean isMoving){
        this.isMoveInDir.put(dir, isMoving);
        return this;
    }
    
    /**
     * 
     * @return a unique identifier 
     * for this entity
     */
    public final long getId(){
        return id;
    }
    
    /**
     * Updates the coordinates of this AbstractEntity based on the Directions it
     * is moving in.
     * 
     * @return this, for chaining purposes 
     */
    protected AbstractEntity updateMovement(){
        isMoveInDir.forEach((dir, isMove)->{
            if(isMove){
                setX(getX() + speed * dir.getXMod());
                setY(getY() + speed * dir.getYMod());
            }
        });
        return this;
    }
    
    /**
     * Updates this, invoked at the end of each frame.
     * 
     * @return this, for chaining purposes. 
     */
    public final AbstractEntity update(){
        doUpdate();
        updateMovement();
        return this;
    } 
    
    /**
     * Subclasses should override this method to perform any end-of-frame updates
     * prior to movement.
     * 
     * @return this, for chaining purposes 
     */
    public abstract AbstractEntity doUpdate();
    
    public abstract void draw(Graphics g);
}