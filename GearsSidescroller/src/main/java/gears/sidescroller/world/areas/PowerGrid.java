package gears.sidescroller.world.areas;

import gears.sidescroller.util.Matrix;
import gears.sidescroller.world.machines.AbstractMachine;
import gears.sidescroller.world.machines.PowerProvidingMachine;
import static gears.sidescroller.world.tiles.AbstractTileTemplate.TILE_SIZE;
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
    private double alpha;
    
    public PowerGrid(int width, int height) {
        super(width, height);
        alpha = 0;
        setAllTo(false);
    }
    
    public final boolean applyPowerFrom(AbstractMachine machine){
        boolean machineProvidesPower = machine instanceof PowerProvidingMachine && machine.isPowered();
        return machineProvidesPower && generateSquareOfPower(machine.getCenterXIdx(), machine.getCenterYIdx(), ((PowerProvidingMachine)machine).getPowerAreaRadiusInTiles());
    }
    private boolean generateSquareOfPower(int centerXIdx, int centerYIdx, int radius){
        boolean newPowerAdded = false;
        int squareOfPowerXIdx = centerXIdx - radius;
        int squareOfPowerYIdx = centerYIdx - radius;
        for(int dx = 0; dx <= radius * 2; dx++){
            for(int dy = 0; dy <= radius * 2; dy++){
                if(
                    this.isValidIdx(squareOfPowerXIdx + dx, squareOfPowerYIdx + dy) 
                    && !this.get(squareOfPowerXIdx + dx, squareOfPowerYIdx + dy) // not powered yet
                ){
                    newPowerAdded = true;
                    this.set(squareOfPowerXIdx + dx, squareOfPowerYIdx + dy, true);
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
