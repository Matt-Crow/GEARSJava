package gears.sidescroller.world.machines;

import gears.sidescroller.world.ObjectInWorld;
import static gears.sidescroller.world.tiles.AbstractTile.TILE_SIZE;
import java.awt.Graphics;

/**
 *
 * @author Matt
 */
public abstract class AbstractMachine extends ObjectInWorld {
    public AbstractMachine(int x, int y, int w, int h){
        super(x, y, w, h);
    }
    public AbstractMachine(int x, int y){
        this(x, y, TILE_SIZE, TILE_SIZE);
    }
    
    public abstract void update();
    
    public abstract void draw(Graphics g);
}
