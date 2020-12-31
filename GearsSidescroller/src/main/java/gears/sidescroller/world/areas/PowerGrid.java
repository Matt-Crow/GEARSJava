package gears.sidescroller.world.areas;

import gears.sidescroller.util.Matrix;
import static gears.sidescroller.world.tiles.AbstractTile.TILE_SIZE;
import java.awt.Color;
import java.awt.Graphics;

/**
 * The PowerGrid is used by an Area to denote which
 * tiles are being supplied electrical power, and which
 * are not.
 * 
 * @author Matt Crow
 */
public class PowerGrid extends Matrix<Boolean> {
    private static double alpha = 0;
    
    public PowerGrid(int width, int height) {
        super(width, height);
        this.setKeyToVal(0, Boolean.FALSE);
        this.setKeyToVal(1, Boolean.TRUE);
    }
    
    public final void draw(Graphics g){
        Color c = new Color(255, 255, 0, (int) (100*(1 + Math.sin(alpha)))); // transparent yellow
        alpha = (alpha + 0.1) % (2*Math.PI);
        g.setColor(c);
        this.forEachCell((isPowered, xIdx, yIdx)->{
            if(isPowered){
                g.fillRect(xIdx * TILE_SIZE, yIdx * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        });
    }
}
