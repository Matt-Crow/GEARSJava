package gears.sidescroller.world.areas;

import gears.sidescroller.util.Matrix;
import gears.sidescroller.util.dataStructures.VolatileLinkedList;
import gears.sidescroller.world.machines.AbstractMachine;
import gears.sidescroller.world.machines.PowerProvidingMachine;
import static gears.sidescroller.world.tiles.AbstractTileTemplate.TILE_SIZE;
import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

/**
 * The PowerGrid is used by an Area to denote which
 * tiles are being supplied electrical power, and which
 * are not.
 * 
 * @author Matt Crow
 */
public class PowerGrid extends Matrix<Boolean> {
    private double alpha;
    
    /**
     * 
     * @param width the width of this PowerGrid, measured in tiles
     * @param height the height of this PowerGrid, measured in tiles
     */
    public PowerGrid(int width, int height) {
        super(width, height);
        alpha = 0;
        setAllTo(false);
    }
    
    /**
     * Applies power from the given machine to the PowerGrid, if it provides any power.
     * 
     * @param machine the machine to apply power from
     * 
     * @return whether new power was provided by the given machine 
     */
    private boolean applyPowerFrom(AbstractMachine machine){
        boolean machineProvidesPower = machine instanceof PowerProvidingMachine && machine.isPowered();
        return machineProvidesPower && generateSquareOfPower(machine);
    }
    
    /**
     * Updates this PowerGrid by creating a square of powered tiles centered on
     * the given machine, assuming it is a PowerProvidingMachine.
     * 
     * @param machine the machine which will produce a square of power
     * 
     * @return whether new powered tiles were added by the machine's square of
     * power.
     */
    private boolean generateSquareOfPower(AbstractMachine machine){
        boolean newPowerAdded = false;
        int radius = (machine instanceof PowerProvidingMachine) ? ((PowerProvidingMachine)machine).getPowerAreaRadiusInTiles() : 0;
        int squareOfPowerXIdx = machine.getCenterXIdx() - radius;
        int squareOfPowerYIdx = machine.getCenterYIdx() - radius;
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
    
    /**
     * Updates the PowerGrid with the given list of machines, supplying external
     * power where relevant, and updating which of this' indeces are powered.
     * 
     * @param machines the list of machines to apply power from and apply external
     * power to.
     */
    public final void update(VolatileLinkedList<AbstractMachine> machines){
        setAllTo(false);
        LinkedList<AbstractMachine> currentSet = new LinkedList<>();
        LinkedList<AbstractMachine> nextSet = new LinkedList<>();
        machines.forEach((m)->{
            if(m instanceof PowerProvidingMachine){
                currentSet.add(m);
            }
        });
        boolean newPowerWasProvided = false;
        do {
            newPowerWasProvided = false;
            for(AbstractMachine m : currentSet){
                m.setExternallyPowered(get(m.getXIdx(), m.getYIdx()));
                if(m.isPowered()){
                    newPowerWasProvided |= applyPowerFrom(m);
                    // "Or equals" prevents true from being replaced with false
                } else {
                    nextSet.add(m);
                }
            }
            currentSet.clear();
            currentSet.addAll(nextSet);
            nextSet.clear();
        } while(newPowerWasProvided);
        
        /*
        keep updating until no new PowerProvidingMachines are powered
        This helps catch cases where a machine earlier in the list is originally unpowered,
        but gets power by a later machine in the list.
        */
        
        // lastly, set all Machine's powered status
        machines.forEach((machine)->{
            machine.setExternallyPowered(get(machine.getXIdx(), machine.getYIdx()));
        });
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
