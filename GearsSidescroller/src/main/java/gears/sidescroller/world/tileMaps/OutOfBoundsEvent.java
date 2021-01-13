package gears.sidescroller.world.tileMaps;

import gears.sidescroller.util.Direction;
import gears.sidescroller.world.core.MobileWorldObject;

/**
 * An OutOfBoundsEvent is fired whenever a MobileWorldObject passes the bounds of
 * a TileMap. This is used by Level to detect when the Player leaves their Area,
 * and should therefore transition to the next one. I may later change this to
 * where Areas are aware of the Level they occupy, and can therefore handle 
 * transitions without listeners.
 * 
 * @author Matt Crow
 */
public class OutOfBoundsEvent {
    private final TileMap map;
    private final MobileWorldObject outOfBoundsEntity;
    private final Direction fromDir;
    
    /**
     * 
     * @param map the map whose bounds something just went outside
     * @param outOfBoundsEntity the thing that just went out of bounds
     * @param fromDir the side of the map the given entity went out of bounds through
     */
    public OutOfBoundsEvent(TileMap map, MobileWorldObject outOfBoundsEntity, Direction fromDir){
        this.map = map;
        this.outOfBoundsEntity = outOfBoundsEntity;
        this.fromDir = fromDir;
    }
    
    /**
     * @return the map whose bounds something just went outside
     */
    public final TileMap getMap(){
        return map;
    }
    
    /**
     * @return the thing that just went out of bounds
     */
    public final MobileWorldObject getOutOfBoundsEntity(){
        return outOfBoundsEntity;
    }
    
    /**
     * @return the side of the map the given entity went out of bounds through 
     */
    public final Direction getDirection(){
        return fromDir;
    }
}
