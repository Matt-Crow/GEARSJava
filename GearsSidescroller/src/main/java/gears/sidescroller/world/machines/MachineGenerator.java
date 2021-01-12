package gears.sidescroller.world.machines;

import gears.sidescroller.util.Direction;
import gears.sidescroller.world.areas.Area;
import static gears.sidescroller.world.tiles.AbstractTile.TILE_SIZE;
import java.util.LinkedList;
import java.util.Random;

/**
 * The MachineGenerator is used to randomly generate machines for AreaGenerator.
 * 
 * @author Matt Crow
 */
public class MachineGenerator {
    /**
     * Creates a chain of ConveyorBeltSegments, where the nth segment will move 
     * AbstractEntities onto the (n+1)th segment.
     * 
     * @param inThisArea the Area these ConveyorBeltSegments will be added to. 
     * Used to check for valid tiles.
     * @param startXIdx the x-coordinate to start laying conveyor belts at, measured in index-space
     * @param startYIdx the y-coordinate to start laying conveyor belts at, measured in index-space
     * 
     * @return a list of ConveyorBeltSegments to add to the Area. 
     */
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
    
    /**
     * Creates one or more new machines to add to the given Area. Note that this method does not do the 
     * actual adding, and so the caller is responsible for doing so.
     * 
     * This method must return a list, as this may return a series of ConveyorBeltSegments
     * 
     * @param inThisArea the Area these machines should later be added to
     * @param xIdx the x-coordinate this new machine will be added to, measured in index-space
     * @param yIdx the y-coordinate this new machine will be added to, measured in index-space
     * @return 
     */
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
