package gears.sidescroller.entities;

import gears.sidescroller.util.Direction;
import gears.sidescroller.util.PhysicsConstants;
import gears.sidescroller.world.tiles.AbstractTile;
import java.awt.Graphics;

/**
 *
 * @author Matt Crow
 */
public abstract class AbstractEntity {
    private int x;
    private int y;
    private int speed;
    private int width;
    private int height;
    private Direction facing;
    private boolean moving;
    private final long id;
    
    private static long NEXT_ID = 0;
    
    public AbstractEntity(){
        x = 0;
        y = 0;
        speed = 0;
        width = AbstractTile.TILE_SIZE;
        height = AbstractTile.TILE_SIZE;
        facing = Direction.RIGHT;
        moving = false;
        id = NEXT_ID;
        NEXT_ID++;
    }
    
    public final AbstractEntity setX(int newX){
        x = newX;
        return this;
    }
    public final AbstractEntity setY(int newY){
        y = newY;
        return this;
    }
    public final AbstractEntity setSpeed(int s){
        speed = s;
        return this;
    }
    public final AbstractEntity setWidth(int w){
        width = w;
        return this;
    }
    public final AbstractEntity setHeight(int h){
        height = h;
        return this;
    }
    public final AbstractEntity setFacing(Direction newDir){
        facing = newDir;
        return this;
    }
    public final AbstractEntity setMoving(boolean isMoving){
        moving = isMoving;
        return this;
    }
    
    public final int getX(){
        return x;
    }
    public final int getY(){
        return y;
    }
    public final int getSpeed(){
        return speed;
    }
    public final int getWidth(){
        return width;
    }
    public final int getHeight(){
        return height;
    }
    public final Direction getFacing(){
        return facing;
    }
    public final boolean isMoving(){
        return moving;
    }
    
    /**
     * 
     * @return a unique identifier 
     * for this entity
     */
    public final long getId(){
        return id;
    }
    
    //Player will have to override this for jumping
    public AbstractEntity updateMovement(){
        if(moving){
            x += speed * facing.getXMod();
            y += PhysicsConstants.GRAVITY * Direction.DOWN.getYMod();
        }
        return this;
    }
    public final AbstractEntity update(){
        doUpdate();
        updateMovement();
        return this;
    } 
    
    public abstract AbstractEntity doUpdate();
    public abstract void draw(Graphics g);
}