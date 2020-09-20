package gears.sidescroller.entities;

import gears.sidescroller.util.Direction;
import gears.sidescroller.world.tiles.AbstractTile;
import java.awt.Graphics;
import java.util.HashMap;

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
    private final HashMap<Direction, Boolean> isMoveInDir;
    private final long id;
    
    private static long NEXT_ID = 0;
    
    public AbstractEntity(){
        x = 0;
        y = 0;
        speed = 0;
        width = AbstractTile.TILE_SIZE;
        height = AbstractTile.TILE_SIZE;
        isMoveInDir = new HashMap<>();
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
    
    public AbstractEntity updateMovement(){
        isMoveInDir.forEach((dir, isMove)->{
            if(isMove){
                x += speed * dir.getXMod();
                y += speed * dir.getYMod();
            }
        });
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