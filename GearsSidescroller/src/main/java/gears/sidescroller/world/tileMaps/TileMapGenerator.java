package gears.sidescroller.world.tileMaps;

import gears.sidescroller.world.tiles.*;
import java.util.Random;
import java.util.function.BiFunction;

/**
 * Use this class to randomly generate tile maps.
 * 
 * @author Matt Crow
 */
public class TileMapGenerator {
    private final Random rng;
    private final BiFunction<Integer, Integer, Integer> function;
    
    /**
     * @param rng the random number generator to use while constructing TileMaps
     * @param function a function which maps x- and y-coordinates in index-space to keys in the FlyweightMatrix
     * of tile designs in the Areas this generates. Note that the output of this function is automatically 
     * capped at 0 at the minimum. Level curves produced by this function will each contain a single tile type.
     */
    public TileMapGenerator(Random rng, BiFunction<Integer, Integer, Integer> function){
        this.rng = rng;
        this.function = function;
    }
    
    public TileMapGenerator(Random rng, int cosAmplitude, int sinAmplitude, int cosPeriod, int sinPeriod){
        this.rng = rng;
        this.function = (x, y)->{
            return (int)(cosAmplitude * Math.cos(x / cosPeriod) + sinAmplitude * Math.sin(y / sinPeriod));
        };
    }
    
    
    public TileMap generateTileMap(TileSet tiles, int w, int h){
        TileMap ret = new TileMap(w, h);
        
        ret.addToTileSet(0, tiles.chooseRandomIntangibleTile(rng));
        // should be able to walk on 0
        // since 0 will be the most common tile
        
        /*
        need to add a random value to the inputs of function, as otherwise the
        shape of the tilemap generated is not random.
        */
        int r;
        
        int newKey = -1;
        for(int i = 0; i < w; i++){
            for(int j = 0; j < h; j++){
                r = (int) Math.floor(Math.random() * 10);
                newKey = Math.max(0, function.apply(i + r, j + r));
                
                if(!ret.isKeySet(newKey)){
                    // only add to the tile set if a new key appears
                    ret.addToTileSet(newKey, tiles.chooseRandomTangibleTile(rng));
                }
                
                ret.setTile(i, j, newKey);
            }
        }
        
        return ret;
    }
}
