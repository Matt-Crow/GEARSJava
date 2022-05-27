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
    private int speed; // measured in pixels / second
    private final HashMap<Direction, Boolean> isMoveInDir; 
    // allows for moving diaganally using just cardinal directions,
    // which a single Direction wouldn't allow
    
    
    public AbstractEntity(){
        super(0, 0, TILE_SIZE, TILE_SIZE);
        speed = 0;
        isMoveInDir = new HashMap<>();
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
    public int getSpeed(){
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
        isMoveInDir.put(dir, isMoving);
        return this;
    }  
    
    @Override
    public void update(int fps){
        doUpdate();
        super.update(fps);
    }
    
    @Override
    public void move(int fps) {
        isMoveInDir.forEach((dir, isMove)->{
            if(isMove){ // uses getSpeed() to allow subclasses to override it
                setX(getX() + getSpeed() * dir.getXMod() / fps);
                setY(getY() + getSpeed() * dir.getYMod() / fps);
            }
        });
    }
    
    /**
     * Subclasses should override this method to perform any end-of-frame updates
     * prior to movement.
     * 
     * @return this, for chaining purposes 
     */
    public abstract AbstractEntity doUpdate();
    
    @Override
    public abstract void draw(Graphics g);
}