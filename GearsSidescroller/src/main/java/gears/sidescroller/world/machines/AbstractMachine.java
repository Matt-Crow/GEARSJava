package gears.sidescroller.world.machines;

import gears.sidescroller.world.entities.AbstractEntity;
import gears.sidescroller.world.ObjectInWorld;
import static gears.sidescroller.world.tiles.AbstractTile.TILE_SIZE;
import java.awt.Graphics;

/**
 *
 * @author Matt
 */
public abstract class AbstractMachine extends ObjectInWorld {
    private final boolean isSelfPowering;
    private boolean isExternallyPowered;
    
    public AbstractMachine(int x, int y, int w, int h, boolean selfPowering){
        super(x, y, w, h);
        isSelfPowering = selfPowering;
        isExternallyPowered = false;
    }
    
    public AbstractMachine(int x, int y, boolean selfPowering){
        this(x, y, TILE_SIZE, TILE_SIZE, selfPowering);
    }
    
    public AbstractMachine(int x, int y){
        this(x, y, true);
    }
    
    public final void setExternallyPowered(boolean b){
        this.isExternallyPowered = b;
    }
    
    public final boolean isPowered(){
        return isSelfPowering || isExternallyPowered;
    }
    
    public abstract void update();
    
    public abstract void draw(Graphics g);
    
    public abstract boolean checkForCollisions(AbstractEntity e);
}
