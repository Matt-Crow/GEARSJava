package gears.sidescroller.entities;

import gears.sidescroller.util.Collidable;
import gears.sidescroller.util.CollisionBox;
import gears.sidescroller.util.Direction;
import gears.sidescroller.world.ObjectInWorld;
import gears.sidescroller.util.dataStructures.Removable;
import gears.sidescroller.util.dataStructures.RemovalListener;
import static gears.sidescroller.world.tiles.AbstractTile.TILE_SIZE;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.function.Consumer;

/**
 *
 * @author Matt Crow
 */
public abstract class AbstractEntity extends ObjectInWorld implements Collidable, Removable{
    private int speed;
    private final HashMap<Direction, Boolean> isMoveInDir;
    private final long id;
    
    private static long NEXT_ID = 0;
    
    protected final LinkedList<RemovalListener> removalListeners;
    
    public AbstractEntity(){
        super(0, 0, TILE_SIZE, TILE_SIZE);
        this.removalListeners = new LinkedList<>();
        speed = 0;
        isMoveInDir = new HashMap<>();
        id = NEXT_ID;
        NEXT_ID++;
    }
    
    public final AbstractEntity setSpeed(int s){
        speed = s;
        return this;
    }
    
    public final int getSpeed(){
        return speed;
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
                setX(getX() + speed * dir.getXMod());
                setY(getY() + speed * dir.getYMod());
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
    
    @Override
    public CollisionBox getCollisionBox(){
        return new CollisionBox(this);
    }
    @Override
    public void addRemovalListener(RemovalListener listener) {
        this.removalListeners.add(listener);
    }

    @Override
    public void forEachRemovalListener(Consumer<RemovalListener> doThis) {
        this.removalListeners.forEach(doThis);
    }
}