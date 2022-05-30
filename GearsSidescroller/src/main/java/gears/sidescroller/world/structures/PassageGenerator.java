package gears.sidescroller.world.structures;

import gears.sidescroller.world.core.WorldObject;
import gears.sidescroller.world.tileMaps.TileMap;
import gears.sidescroller.world.tiles.TileGenerator;
import java.util.*;

/**
 * generates random passages
 * 
 * @author Matt Crow 
 */
public class PassageGenerator implements GeneratesStructures {
    private final int maxLength;
    private final TileGenerator tileGenerator;
    
    public PassageGenerator(int maxLength, TileGenerator tileGenerator){
        this.maxLength = maxLength;
        this.tileGenerator = tileGenerator;
    }
    
    @Override
    public Structure generate(int structX, int structY, Random rng) {
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
        
        t.setKeyToVal(0, tileGenerator.generateRandom(false));
        t.setKeyToVal(1, tileGenerator.generateRandom(true));
        
        Set<WorldObject> objs = new HashSet<>();
        
        s = new Structure(structX, structY, t, objs);
        
        return s;
    }
}
