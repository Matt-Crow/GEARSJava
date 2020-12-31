package gears.sidescroller.world.Machines;

import static gears.sidescroller.world.tiles.AbstractTile.TILE_SIZE;
import java.awt.Graphics;

/**
 *
 * @author Matt
 */
public abstract class AbstractMachine {
    private int x;
    private int y;
    private int width;
    private int height;
    
    public AbstractMachine(int x, int y){
        this.x = x;
        this.y = y;
        width = TILE_SIZE;
        height = TILE_SIZE;
    }
    
    public final int getX(){
        return x;
    }
    public final int getXIdx(){
        return x / TILE_SIZE;
    }
    
    public final int getY(){
        return y;
    }
    public final int getYIdx(){
        return y / TILE_SIZE;
    }
    
    public final int getWidthInPixels(){
        return width;
    }
    public final int getWidthInTiles(){
        return width / TILE_SIZE;
    }
    public final int getHeightInPixels(){
        return height;
    }
    public final int getHeightInTiles(){
        return height / TILE_SIZE;
    }
    
    public abstract void update();
    
    public abstract void draw(Graphics g);
}
