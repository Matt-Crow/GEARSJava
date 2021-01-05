package gears.sidescroller.world.tileMaps;

import gears.sidescroller.world.entities.AbstractEntity;
import gears.sidescroller.util.Direction;

/**
 *
 * @author Matt
 */
public class OutOfBoundsEvent {
    private final TileMap map;
    private final AbstractEntity outOfBoundsEntity;
    private final Direction fromDir;
    
    public OutOfBoundsEvent(TileMap map, AbstractEntity outOfBoundsEntity, Direction fromDir){
        this.map = map;
        this.outOfBoundsEntity = outOfBoundsEntity;
        this.fromDir = fromDir;
    }
    
    public final TileMap getMap(){
        return map;
    }
    
    public final AbstractEntity getOutOfBoundsEntity(){
        return outOfBoundsEntity;
    }
    
    public final Direction getDirection(){
        return fromDir;
    }
}
