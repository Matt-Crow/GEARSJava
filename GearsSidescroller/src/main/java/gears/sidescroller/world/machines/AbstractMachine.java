package gears.sidescroller.world.machines;

import gears.sidescroller.world.core.MobileWorldObject;
import gears.sidescroller.world.core.WorldObject;
import static gears.sidescroller.world.tiles.AbstractTileTemplate.TILE_SIZE;
import java.awt.Graphics;
import javax.json.JsonObjectBuilder;

/**
 * Machines are interactable elements in the game world. Machines
 * must be powered to update, though this power may be applied either
 * externally via the PowerGrid, or internally, by setting the Machine's
 * isSelfPowering property to true.
 * 
 * @author Matt Crow
 */
public abstract class AbstractMachine extends WorldObject {
    private final boolean isSelfPowering;
    private boolean isExternallyPowered;
    
    /**
     * 
     * @param x the x-coordinate of this machine, measured in pixel-space
     * @param y the y-coordinate of this machine, measured in pixel-space
     * @param w the width of this machine, measured in pixel-space
     * @param h the height of this machine, measured in pixel-space
     * @param selfPowering whether or not this machine should provide power to itself
     */
    public AbstractMachine(int x, int y, int w, int h, boolean selfPowering){
        super(x, y, w, h);
        isSelfPowering = selfPowering;
        isExternallyPowered = false;
    }
    
    /**
     * 
     * @param x the x-coordinate of this machine, measured in pixel-space
     * @param y the y-coordinate of this machine, measured in pixel-space
     * @param selfPowering whether or not this machine should provide power to itself
     */
    public AbstractMachine(int x, int y, boolean selfPowering){
        this(x, y, TILE_SIZE, TILE_SIZE, selfPowering);
    }
    
    /**
     * @param x the x-coordinate of this machine, measured in pixel-space
     * @param y the y-coordinate of this machine, measured in pixel-space
     */
    public AbstractMachine(int x, int y){
        this(x, y, true);
    }
    
    /**
     * This method will be invoked by the PowerGrid when
     * this machine is being externally powered.
     * 
     * @param b whether or not this machine is powered by the PowerGrid
     */
    public final void setExternallyPowered(boolean b){
        this.isExternallyPowered = b;
    }
    
    /**
     * 
     * @return whether or not this machine is being powered,
     * and therefore should update
     */
    public final boolean isPowered(){
        return isSelfPowering || isExternallyPowered;
    }
    
    @Override
    public void update(int fps){
        if(isPowered()){
            machineUpdate();
        }
    }
    
    /**
     * Updates the machine. Note that
     * this method is only invoked when
     * the isPowered method returns true,
     * so you needn't check for yourself.
     * Machines can use this method to update
     * animation frames, if you want.
     */
    public abstract void machineUpdate();
    
    /**
     * Renders this machine on the given
     * Graphics context.
     * 
     * @param g the Graphics object to render this on
     */
    @Override
    public abstract void draw(Graphics g);
    
    @Override
    public abstract void collideWith(MobileWorldObject e);
    
    /**
     * Subclasses should still override this method.
     * Don't forget to call super!
     * 
     * @param builder 
     */
    @Override
    protected void attachJsonProperties(JsonObjectBuilder builder){
        builder.add("isSelfPowering", isSelfPowering);
    }
}
