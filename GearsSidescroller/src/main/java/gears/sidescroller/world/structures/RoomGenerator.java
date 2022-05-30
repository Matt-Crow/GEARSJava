package gears.sidescroller.world.structures;

import gears.sidescroller.util.Direction;
import gears.sidescroller.world.tiles.TileGenerator;
import java.util.Random;

/**
 * generates random rooms
 * 
 * @author Matt Crow <mattcrow19@gmail.com>
 */
public class RoomGenerator implements GeneratesStructures {
    private final int maxWidth;
    private final int maxHeight;
    private final TileGenerator tileGenerator;
    
    public RoomGenerator(int maxWidth, int maxHeight, TileGenerator tileGenerator){
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.tileGenerator = tileGenerator;
    }
    
    
    @Override
    public Structure generate(Random rng) {
        int width = rng.nextInt(maxWidth) + 1;
        int height = rng.nextInt(maxHeight) + 1;
        Structure newStruct = new Structure(
            width,
            height
        );
        newStruct.setKeyToVal(0, tileGenerator.generateRandom(false));
        newStruct.setKeyToVal(1, tileGenerator.generateRandom(true));
        
        // build the room's walls
        // top & bottom walls
        for(int x = 0; x < width; x++){
            newStruct.set(x, 0, 1);
            newStruct.set(x, height - 1, 1);
        }
        // left & right walls
        for(int y = 0; y < height; y++){
            newStruct.set(0, y, 1);
            newStruct.set(width - 1, y, 1);
        }
        
        // choose where to put the door
        Direction[] dirs = new Direction[]{
            Direction.UP,
            Direction.DOWN,
            Direction.LEFT,
            Direction.RIGHT
        };
        Direction putAHoleHere = dirs[rng.nextInt(dirs.length)];
        switch(putAHoleHere){
            case UP:
                newStruct.set(width / 2, 0, 0);
                break;
            case DOWN:
                newStruct.set(width / 2, height - 1, 0);
                break;
            case LEFT:
                newStruct.set(0, height / 2, 0);
                break;
            case RIGHT:
                newStruct.set(width - 1, height / 2, 0);
                break;
        }
        
        return newStruct;
    }
}
