package gears.sidescroller.world.structures;

import gears.sidescroller.util.Direction;
import gears.sidescroller.world.tiles.ImageTile;
import gears.sidescroller.world.tiles.TileGenerator;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 *
 * @author Matt
 */
public class StructureGenerator {
    private final int maxWidth;
    private final int maxHeight;
    
    public StructureGenerator(int maxWidth, int maxHeight){
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
    }
    
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
        try {
            System.out.append("TODO: change StructureGenerator to not use image tiles");
            newStruct.setKeyToVal(1, new ImageTile(0, 0, true, ImageIO.read(StructureGenerator.class.getResourceAsStream("/images/jarLogo.png"))));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
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
