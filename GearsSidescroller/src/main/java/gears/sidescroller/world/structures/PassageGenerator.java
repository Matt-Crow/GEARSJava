package gears.sidescroller.world.structures;

import gears.sidescroller.world.core.WorldObject;
import gears.sidescroller.world.tileMaps.TileMap;
import gears.sidescroller.world.tiles.TileSet;
import java.util.*;

/**
 * generates random passages
 * 
 * @author Matt Crow 
 */
public class PassageGenerator implements GeneratesStructures {
    private final int maxLength;
    
    public PassageGenerator(int maxLength){
        this.maxLength = maxLength;
    }
    
    @Override
    public Structure generate(Random rng, int x, int y, TileSet tiles) {
        int minorLength = rng.nextInt(Math.max(maxLength - 3, 1)) + 1;
        int majorLength = minorLength + 3;
        
        TileMap t;
        Structure s;
        if(rng.nextBoolean()){
            // longer in the y-direction
            t = new TileMap(minorLength, majorLength);
            for(int i = 0; i < majorLength; ++i){
                t.set(0, i, 1);
                t.set(minorLength - 1, i, 1);
            }
        } else {
            // longer in the x-direction
            t = new TileMap(majorLength, minorLength);
            for(int i = 0; i < majorLength; ++i){
                t.set(i, 0, 1);
                t.set(i, minorLength - 1, 1);
            }
        }
        
        t.setKeyToVal(0, tiles.chooseRandomIntangibleTile(rng));
        t.setKeyToVal(1, tiles.chooseRandomTangibleTile(rng));
        
        Set<WorldObject> objs = new HashSet<>();
        
        s = new Structure(x, y, t, objs);
        
        return s;
    }
}
