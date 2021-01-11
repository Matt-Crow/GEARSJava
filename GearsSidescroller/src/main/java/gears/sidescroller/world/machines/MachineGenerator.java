package gears.sidescroller.world.machines;

import gears.sidescroller.util.Direction;
import gears.sidescroller.world.areas.Area;
import static gears.sidescroller.world.tiles.AbstractTile.TILE_SIZE;
import java.util.Random;

/**
 *
 * @author Matt
 */
public class MachineGenerator {
    
    public final AbstractMachine createRandomMachineAt(Area inThisArea, int xIdx, int yIdx){
        AbstractMachine ret = null;
        Random rng = new Random();
        switch(rng.nextInt(3)){
            case 0:
                ret = new PowerPlant(xIdx * TILE_SIZE, yIdx * TILE_SIZE);
                break;
            case 1:
                ret = new GearMachine(xIdx * TILE_SIZE, yIdx * TILE_SIZE);
                break;
            case 2:
                // change to spawn complete conveyor belt
                Direction[] possibleDirs = Direction.getCardinalDirections();
                ret = new ConveyorBeltSegment(
                    xIdx * TILE_SIZE, 
                    yIdx * TILE_SIZE,
                    true,
                    10,
                    possibleDirs[rng.nextInt(possibleDirs.length)]
                );
                break;
        }
        
        return ret;
    }
}
