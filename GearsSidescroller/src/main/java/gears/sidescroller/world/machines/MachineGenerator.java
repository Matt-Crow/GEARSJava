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
    private ConveyorBeltComposite createConveyorBelt(Area inThisArea, int startXIdx, int startYIdx){
        int maxLength = 20;
        int currLength = 0;
        ConveyorBeltComposite ret = new ConveyorBeltComposite(startXIdx, startYIdx);
        Random rng = new Random();
        int currXIdx = startXIdx;
        int currYIdx = startYIdx;
        Direction[] possibleDirs = Direction.getCardinalDirections();
        Direction currDir = possibleDirs[rng.nextInt(possibleDirs.length)];
        int speed = rng.nextInt(TILE_SIZE / 5);
        int shouldTurnLeftRightOrGoStrait = -1;
        while(currLength < maxLength && inThisArea.getTileMap().isTileOpen(currXIdx, currYIdx)){
            ret.addSegment(new ConveyorBeltSegment(
                currXIdx * TILE_SIZE,
                currYIdx * TILE_SIZE,
                true,
                speed,
                currDir
            ));
            currXIdx += currDir.getXMod();
            currYIdx += currDir.getYMod();
            
            shouldTurnLeftRightOrGoStrait = rng.nextInt(3);
            if(shouldTurnLeftRightOrGoStrait == 0){
                // turn left
                currDir = Direction.rotateCounterClockWise(currDir);
            } else if(shouldTurnLeftRightOrGoStrait == 1){
                // turn right
                currDir = Direction.rotateClockWise(currDir);
            } // else, keep going strait
            currLength++;
        }
        return ret;
    }
    
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
                ret = createConveyorBelt(inThisArea, xIdx, yIdx);
                break;
        }
        
        return ret;
    }
}
