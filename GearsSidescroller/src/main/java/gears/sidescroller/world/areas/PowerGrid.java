package gears.sidescroller.world.areas;

import gears.sidescroller.util.Matrix;
import gears.sidescroller.world.Machines.AbstractMachine;
import gears.sidescroller.world.Machines.PowerProvidingMachine;
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
    
    public final boolean applyPowerFrom(PowerProvidingMachine machine){
        boolean newPowerAdded = false;
        int radius = machine.getPowerAreaRadiusInTiles();
        AbstractMachine asMachine = (AbstractMachine)machine;
        //                      this gets the center of the machine                       and this goes to the left side of the power square
        int squareOfPowerXIdx = (asMachine.getXIdx() + asMachine.getWidthInTiles() / 2) - radius;
        int squareOfPowerYIdx = (asMachine.getYIdx() + asMachine.getHeightInTiles() / 2) - radius;
        
        for(int dx = 0; dx <= radius * 2; dx++){
            for(int dy = 0; dy <= radius * 2; dy++){
                if(
                    this.isValidIdx(squareOfPowerXIdx + dx, squareOfPowerYIdx + dy) 
                    && !this.get(squareOfPowerXIdx + dx, squareOfPowerYIdx + dy) // not powered yet
                ){
                    newPowerAdded = true;
                    this.set(squareOfPowerXIdx + dx, squareOfPowerYIdx + dy, 1);
                }
            }
        }
        
        return newPowerAdded;
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
