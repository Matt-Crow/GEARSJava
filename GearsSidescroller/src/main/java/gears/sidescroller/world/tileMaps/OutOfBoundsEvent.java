package gears.sidescroller.world.tileMaps;

import gears.sidescroller.world.entities.AbstractEntity;
import gears.sidescroller.util.Direction;
import gears.sidescroller.world.core.MobileWorldObject;

/**
 *
 * @author Matt
 */
public class OutOfBoundsEvent {
    private final TileMap map;
    private final MobileWorldObject outOfBoundsEntity;
    private final Direction fromDir;
    
    public OutOfBoundsEvent(TileMap map, MobileWorldObject outOfBoundsEntity, Direction fromDir){
        this.map = map;
        this.outOfBoundsEntity = outOfBoundsEntity;
        this.fromDir = fromDir;
    }
    
    public final TileMap getMap(){
        return map;
    }
    
    public final MobileWorldObject getOutOfBoundsEntity(){
        return outOfBoundsEntity;
    }
    
    public final Direction getDirection(){
        return fromDir;
    }
}
