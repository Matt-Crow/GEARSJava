package gears.sidescroller.world.structures;

import gears.sidescroller.util.Direction;
import gears.sidescroller.world.core.WorldObject;
import gears.sidescroller.world.tileMaps.TileMap;
import gears.sidescroller.world.tiles.TileSet;
import java.util.*;

/**
 * generates random rooms
 * 
 * @author Matt Crow 
 */
public class RoomGenerator implements GeneratesStructures {
    private final int maxWidth;
    private final int maxHeight;
    
    public RoomGenerator(int maxWidth, int maxHeight){
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
    }
    
    
    @Override
    public Structure generate(Random rng, int structX, int structY, TileSet tiles) {
        int width = rng.nextInt(maxWidth) + 1;
        int height = rng.nextInt(maxHeight) + 1;
        TileMap map = new TileMap(
            width,
            height
        );
        map.setKeyToVal(0, tiles.chooseRandomIntangibleTile(rng));
        map.setKeyToVal(1, tiles.chooseRandomTangibleTile(rng));
        
        // build the room's walls
        // top & bottom walls
        for(int x = 0; x < width; x++){
            map.set(x, 0, 1);
            map.set(x, height - 1, 1);
        }
        // left & right walls
        for(int y = 0; y < height; y++){
            map.set(0, y, 1);
            map.set(width - 1, y, 1);
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
                map.set(width / 2, 0, 0);
                break;
            case DOWN:
                map.set(width / 2, height - 1, 0);
                break;
            case LEFT:
                map.set(0, height / 2, 0);
                break;
            case RIGHT:
                map.set(width - 1, height / 2, 0);
                break;
        }
        
        Set<WorldObject> objs = new HashSet<>();
        
        return new Structure(structX, structY, map, objs);
    }
}
