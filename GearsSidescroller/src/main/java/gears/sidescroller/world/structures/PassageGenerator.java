package gears.sidescroller.world.structures;

import gears.sidescroller.world.tiles.TileGenerator;
import java.util.Random;

/**
 * generates random passages
 * 
 * @author Matt Crow <mattcrow19@gmail.com>
 */
public class PassageGenerator implements GeneratesStructures {
    private final int maxLength;
    private final TileGenerator tileGenerator;
    
    public PassageGenerator(int maxLength, TileGenerator tileGenerator){
        this.maxLength = maxLength;
        this.tileGenerator = tileGenerator;
    }
    
    @Override
    public Structure generate(Random rng) {
        int minorLength = rng.nextInt(Math.max(maxLength - 3, 1)) + 1;
        int majorLength = minorLength + 3;
        
        Structure s;
        if(rng.nextBoolean()){
            // longer in the y-direction
            s = new Structure(minorLength, majorLength);
            for(int i = 0; i < majorLength; ++i){
                s.set(0, i, 1);
                s.set(minorLength - 1, i, 1);
            }
        } else {
            // longer in the x-direction
            s = new Structure(majorLength, minorLength);
            for(int i = 0; i < majorLength; ++i){
                s.set(i, 0, 1);
                s.set(i, minorLength - 1, 1);
            }
        }
        s.setKeyToVal(0, tileGenerator.generateRandom(false));
        s.setKeyToVal(1, tileGenerator.generateRandom(true));
        
        return s;
    }
}
