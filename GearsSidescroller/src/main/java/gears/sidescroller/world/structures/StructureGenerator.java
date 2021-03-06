package gears.sidescroller.world.structures;

import gears.sidescroller.util.Direction;
import gears.sidescroller.world.tiles.TileGenerator;
import java.util.Random;

/**
 * The StructureGenerator is used to randomly generate Structures for random Area
 * generation. Currently, this just generates simple rectangular rooms. Future
 * versions will be able to generate more complex Structures, such as ones loaded
 * from files.
 * 
 * @author Matt Crow
 */
public class StructureGenerator {
    private final int maxWidth;
    private final int maxHeight;
    
    /**
     * Creates a new StructureGenerator.
     * 
     * @param maxWidth the maximum width of rooms this can generate, measured in tiles
     * @param maxHeight the maximum height of rooms this can generate, measured in tiles
     */
    public StructureGenerator(int maxWidth, int maxHeight){
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
    }
    
    /**
     * Creates a new hovel based on the specifications provided to the constructor.
     * 
     * @return a rectangular Structure; not the best place to live in. 
     */
    public final Structure generateRoom(){
        Random rng = new Random();
        int width = rng.nextInt(maxWidth) + 1;
        int height = rng.nextInt(maxHeight) + 1;
        Structure newStruct = new Structure(
            width,
            height
        );
        newStruct.setKeyToVal(0, new TileGenerator().generateRandom(false));
        newStruct.setKeyToVal(1, new TileGenerator().generateRandom(true));
        
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
