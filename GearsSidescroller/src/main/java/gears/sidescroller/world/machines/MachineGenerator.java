package gears.sidescroller.world.machines;

import gears.sidescroller.util.Direction;
import gears.sidescroller.world.areas.Area;
import static gears.sidescroller.world.tiles.AbstractTile.TILE_SIZE;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author Matt
 */
public class MachineGenerator {
    private LinkedList<ConveyorBeltSegment> createConveyorBelt(Area inThisArea, int startXIdx, int startYIdx){
        int maxLength = 20;
        int currLength = 0;
        LinkedList<ConveyorBeltSegment> ret = new LinkedList<>();
        Random rng = new Random();
        int currXIdx = startXIdx;
        int currYIdx = startYIdx;
        Direction[] possibleDirs = Direction.getCardinalDirections();
        Direction currDir = possibleDirs[rng.nextInt(possibleDirs.length)];
        int speed = rng.nextInt(TILE_SIZE / 5);
        int shouldTurnLeftRightOrGoStrait = -1;
        while(currLength < maxLength && inThisArea.getTileMap().isTileOpen(currXIdx, currYIdx)){
            ret.add(new ConveyorBeltSegment(
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
    
    public final LinkedList<AbstractMachine> createRandomMachineAt(Area inThisArea, int xIdx, int yIdx){
        LinkedList<AbstractMachine> ret = new LinkedList<>();
        Random rng = new Random();
        switch(rng.nextInt(3)){
            case 0:
                ret.add(new PowerPlant(xIdx * TILE_SIZE, yIdx * TILE_SIZE));
                break;
            case 1:
                ret.add(new GearMachine(xIdx * TILE_SIZE, yIdx * TILE_SIZE));
                break;
            case 2:
                ret.addAll(createConveyorBelt(inThisArea, xIdx, yIdx));
                break;
        }
        
        return ret;
    }
}
