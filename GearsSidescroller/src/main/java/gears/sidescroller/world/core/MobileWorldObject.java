package gears.sidescroller.world.core;

import static gears.sidescroller.world.tiles.AbstractTile.TILE_SIZE;

/**
 * The MobileWorldObject class adds support for changing
 * the coordinates of an ObjectInWorld. I may need to change
 * this class in the future to support classes such as mobile 
 * machines.
 * 
 * @author Matt Crow
 */
public abstract class MobileWorldObject extends ObjectInWorld {

    public MobileWorldObject(int x, int y, int w, int h) {
        super(x, y, w, h);
    }
    
    public final MobileWorldObject setX(int x){
        this.x = x;
        return this;
    }
    public final MobileWorldObject setXIdx(int xIdx){
        this.x = xIdx * TILE_SIZE;
        return this;
    }
    public final MobileWorldObject setY(int y){
        this.y = y;
        return this;
    }
    public final MobileWorldObject setYIdx(int yIdx){
        this.y = yIdx * TILE_SIZE;
        return this;
    }
}
